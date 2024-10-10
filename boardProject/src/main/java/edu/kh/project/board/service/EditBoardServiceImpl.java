package edu.kh.project.board.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.board.dto.Board;
import edu.kh.project.board.dto.BoardImg;
import edu.kh.project.board.mapper.EditBoardMapper;
import edu.kh.project.common.exception.FileUploadFailException;
import edu.kh.project.common.util.FileUtil;
import edu.kh.project.member.dto.Member;
import jakarta.mail.Session;
import lombok.RequiredArgsConstructor;

@PropertySource("classpath:/config.properties")
@Service
@Transactional
@RequiredArgsConstructor
public class EditBoardServiceImpl implements EditBoardService{

	private final EditBoardMapper mapper;
	
	@Value("${my.board.web-path}")
	private String webPath;
	
	@Value("${my.board.folder-path}")
	private String folderPath;
	// 게시글 등록
	@Override
	public int boardInsert(Board inputBoard, List<MultipartFile> images) {
		
		//1) 게시글 부분 (제목, 내용, 작성자, 게시판 종류 ) INSERT
		int result = mapper.boardInsert(inputBoard);
		
		System.out.println(inputBoard.getBoardNo());
		
		// 삽입 실패시
		if(result == 0 ) return 0;
		
		/*삽입된 게시글 번호*/
		int boardNo = inputBoard.getBoardNo();
		
		// =========================================================
		
		// 2 실제로 업로드된 이미지만 모아두기
		
		List<BoardImg> uploadList = new ArrayList<>();		
		
		// images 리스트에서 실제 업로드된 파일만 골라내기
		for(int i = 0 ; i < images.size(); i++) {
			
			// 제출된 파일이 없을 경우 
			if(images.get(i).isEmpty()) continue;
			
			// 있을 경우
			// 파일 원본 명
			String originalName = images.get(i).getOriginalFilename();
			
			// 변경된 파일명 
			String rename = FileUtil.rename(originalName);
			
			// DB INSERT를 위한 BoardImg 객체 생성
			BoardImg img = BoardImg.builder()
					.imgOriginalName(originalName) // 원본명
					.imgRename(rename) //변경명
					.imgPath(webPath)// 웹접근 경로
					.boardNo(boardNo) // 이미지가 삽입된 게시글 번호
					.imgOrder(i) // 5개의 이미지 칸 중에서 제출된 칸의 순서
					.uploadFile(images.get(i)) // 실제 업로드된 이미지 데이터
					.build(); 
			
			// uploadList에 추가 
			uploadList.add(img);
		} // for문 끝
		
		// 제출된 이미지가 없다면 
		if(uploadList.isEmpty()) return boardNo;
		
		// -----------------------------------------------------------------------
		
		//3. DB에 uploadList에 저장된 값 모두 INSERT 
		// + transferTo() 수행해서 파일 저장
		
		/* [List에 저장된 내용 INSERT 하는 방법]
		 * 1. 1행을 삽입하는 mapper 메서드를 여러번 호출
		 * 2. 여러 행을 삽입하는 mapper 메서드 1회 호출
		 *    (복잡한 SQL + 동적 SQL) (선택!!)
		 * */ 
	
		// 여러행 한 번에 삽입 후 삽입된 행의 개수 반환
			int insertRows = mapper.insertUploadList(uploadList);
		
		// INSERT된 행의 개수와 uploadList의 개수가 같지 않은 경우
		
			if(insertRows != uploadList.size()) {
				
				throw new RuntimeException("이미지 INSERT실패");
				
				// 앞서 삽입한 게시글 부분도 ROLLBACK 되도록 예외 강제 발생
				// 사용자 정의 예외로 교체 예정
				
			}
			try {
				File folder = new File(folderPath);
				if(folder.exists() == false) { // 폴더가 없을 경우
					folder.mkdirs();
				}
			// 모두 삽입 성공시 
			// 임시 저장된 파일을 서버에 지정된 폴더 + 변경명으로 저장 
			for(BoardImg img : uploadList) {
				img.getUploadFile()
				.transferTo(new File(folderPath + img.getImgRename()));
			}
			}catch(Exception e) {
				e.printStackTrace();
				throw new FileUploadFailException();// 사용자 정의 예외 던짐
			}
			
		return boardNo;
	}
	
	@Override
	public int boardDelete(int boardNo, Member loginMember) {
		
		int memberNo = loginMember.getMemberNo();
		
		return mapper.boardDelete(boardNo, memberNo);
	}

	// 게시글 수정
@Override
	public int boardUpdate(Board inputBoard, List<MultipartFile> images, String deleteOrderList) {
 
	// 1. 게시글 부분 (제목/내용) 수정 
	int result = mapper.boardUpdate(inputBoard);
	
	if(result == 0) return 0; // 수정 실패시
	// 2. 기존에 존재했던 이미지중 
	// deleteOrderList에 존재하는 순서의 이미지 DELETE
	
	// deleteOrderList에 작성된 값이 있다면
	if(deleteOrderList != null && 
			deleteOrderList.equals("") == false) {
		
		result = mapper.deleteImage(deleteOrderList, inputBoard.getBoardNo());
		// 삭제된 행이 없을 경우
		// 예외를 발생시켜 전체 rollback 
		if(result == 0 ) {
			throw new RuntimeException("이미지 삭제 실패");
		}
	}
	
	// 3. 업로드된 이미지가 있을 경우 
	//   UPDATE 또는 INSERT + transterTo()
	
	// 실제 업로드된 이미지만 모아두는 리스트 생성
	List<BoardImg> uploadList = new ArrayList<>();
	
	for(int i =0 ; i < images.size(); i++) {
		
		// i번째 요소에 업로드된 파일이 없으면 다음으로 
		if(images.get(i).isEmpty()) continue;
		
		// 업로드된 파일이 있으면 
		String originalName = images.get(i).getOriginalFilename();
		String rename = FileUtil.rename(originalName);
		
		// 필요한 모든값을 저장한 DTO 생성
		BoardImg img = 
				BoardImg.builder()
				.imgOriginalName(originalName)
				.imgRename(rename)
				.imgPath(webPath)
				.boardNo(inputBoard.getBoardNo())
				.imgOrder(i)
				.uploadFile(images.get(i))
				.build();
		
		// 1행씩 update 수행
		result = mapper.updateImage(img);
		
		// 수정이 실패 == 기존에 이미지가 없었다 
		// == 새로운 이미지가 새 order번째 자리에 추가 되었다 
		if(result == 0) {
			
			result = mapper.insertImage(img);
			
		}
		// 수정, 삭제가 모두 실패한 경우 -> 말도 안되는 상황
		if(result == 0) {
			throw new RuntimeException("이미지 DB 추가 실패");
		}
		
		uploadList.add(img); // 업로드된 파일 리스트에 img 추가 
		
	} // for 문 끝
	
	// 새로운 이미지가 없는 경우
	if(uploadList.isEmpty()) return result;
	
	// 임시저장된 이미지 파일을 지정된 경로로 이동 (transfetTo())
	try {
		for(BoardImg img : uploadList) {
			img.getUploadFile()
			.transferTo(new File(folderPath + img.getImgRename()));
		}
	}catch (Exception e) {
		e.printStackTrace();
		throw new FileUploadFailException();
	}
	
	return result;
	}
}
