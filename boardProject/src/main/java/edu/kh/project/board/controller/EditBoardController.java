package edu.kh.project.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.board.dto.Board;
import edu.kh.project.board.service.EditBoardService;
import edu.kh.project.member.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("editBoard")
@Slf4j
public class EditBoardController {
	
	private final EditBoardService service;
	
	/*@ PathVariable 사용시 정규 표현식 적용가능
	 * 	{변수명 : 정규표현식}
	 * */
	
	
	/** 게시글 작성 화면 전환
	 * {boardCode : [0-9]+} : boardCode는 숫자 1글자 이상만 가능
	 * @return
	 */
	@GetMapping("{boardCode:[0-9]+}/insert")
	public String boardInsert(
			@PathVariable("boardCode") int boardCode) {
		//@PathVariable 지정 시 forward한 html 파일에서도 사용 가능
		
		return "board/boardWrite";
	}

	/** 게시글 등록
	 * @param boardCode : 게시판 종류 번호
	 * @param inputBoard : 제출된 값의 key값이 일치하는 필드에 값이 저장된 객체 
	 * 				(커멘트 객체 )
	 * @param loginMember : 로그인한 회원정보 (글쓴이 회원번호 필요)
	 * @param images : 제출된 file 타입 input 태그 테이터
	 * @param ra : 리다이렉트시 request scop로 값 전달 
	 * @return
	 */
	@PostMapping("{boardCode:[0-9]+}/insert")
	public String boardInsert(
			@PathVariable("boardCode") int boardCode,
			@ModelAttribute Board inputBoard,
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("images") List<MultipartFile> images,
			RedirectAttributes ra) {
		
		/*전달된 파라미처 확인 (debug 모드)
		 * 
		 * 제목 , 내용, boardCode -> inputBoard 커맨드 객체 
		 * 
		 * List<MultipartFile> images의 크기(size())
		 *  == 제출된 file 타입의 input태그 개수 == 5개
		 *  
		 *  선택된 파일이 없더라도 빈칸으로 제출 된다
		 * 
		 * ex) 0,2,4 인덱스만 선택
		 *  -> 0,2,4는 제출된 파일이 있고 
		 *  1,3은 빈칸으로 존재
		 * */
		
		//1. 작성자 회원 번호를 insertBoard에 세팅
		inputBoard.setMemberNo(loginMember.getMemberNo());
		
		//2) 서비스 호출 후 결과(작성된 게시글 번호) 반환 받기 
		int boardNo = service.boardInsert(inputBoard,images);
		
		//3) 서비스 결과에 따라 응답 제어
		String path = null;
		String message = null;
		
		if(boardNo == 0) { // 실패
			path = "insert";
			message = "게시글 작성 실패";
		}else {
			/*
			 * path = "/board/" + boardCode + "/" + boardNo; // 상세 조회
			 */	
			path = "/board/" + boardCode ; // 목록 조회 주소 (임시)
			message = "게시글 작성 되었습니다";
		}
		ra.addFlashAttribute("message",message);
		
		return "redirect:" + path; // 임시
		
	}
	
	
}
