package edu.kh.project.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.project.board.dto.Board;
import edu.kh.project.board.dto.Comment;

public interface BoardService {

	/** 게시글 목록 조회
	 * 
	 * @param boardCode
	 * @param cp
	 * @return map
	 */
	Map<String, Object> selectBoardList(int boardCode, int cp);

	/** 게시글 상세 조회
	 * @param map
	 * @return board
	 */
	Board selectDetail(Map<String, Integer> map);

	/** 조회수 1 증가 시키는 코드
	 * @param boardNo
	 * @return result
	 */
	int updateReadCount(int boardNo);

	/** 게시글 좋아요
	 * @param boardNo
	 * @param memberNo
	 * @return map
	 */
	Map<String, Object> boardLike(int boardNo, int memberNo);

	/** DB에서 모든 게시판 종류를 조회
	 * @return 
	 */
	List<Map<String, String>> selectBoardTypeList();

	/** 댓글 목록 조회
	 * @param boardNo
	 * @return commentList
	 */
		List<Comment> selectCommentList(int boardNo);

		/** 검색 목록 조회
		 * @param boardCode
		 * @param cp
		 * @param paramMap
		 * @return
		 */
	Map<String, Object> selectSearchList(int boardCode, int cp, Map<String, Object> paramMap);

	/** 목록으로 이동 페이지
	 * @param paramMap
	 * @return
	 */
		int getCurrentPage(Map<String, Object> paramMap);

	
		
}
