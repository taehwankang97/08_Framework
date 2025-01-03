package edu.kh.project.board.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//DTO (Data Transfer Object) : 계층간 데이터 전달용 객체 
// - 계층? Controller, service, DB 등을 구분 

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Board{
	
	// 행 번호
	private int rnum;
	
	
	// BOARD 테이블 컬럼과 매핑되는 필드 
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardWriteDate;
	private String boardUpdateDate; 
	private int  boardCount;
	private String boardDelFl;
	private int memberNo; 
	private int boardCode;

	// MEMBER 테이블 JOIN 컬럼
	private String memberNickname;
	
	// 목록 조회 시 댓글 / 조아요 수 상관 쿼리 결과 
	private int commentCount;
	private int likeCount;
	
	//----------------------------------------
  // (추가 작성 예정)
	
	private String thumbnail;
	private String profileImg;
	
	// 특정 게시글의 이미지 목록을 저장할 필드
	private List<BoardImg> imageList;
	
	// 특정 게시글의 댓글 목록을 저장할 필드
	private List<Comment> commentList;
	
	// 좋아요 체크 여부를 저장할 필드( 1 == 누른적 있음)
	private int likeCheck;
	
}

