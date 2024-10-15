package edu.kh.project.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import edu.kh.project.board.dto.Board;
import edu.kh.project.board.dto.Comment;

@Mapper
public interface BoardMapper {

	int getListCount(int boardCode);

	/** 지정된 페이지 분랴으이 게시글 목록 조회
	 * @param boardCode
	 * @param rowBounds
	 * @return
	 */
	List<Board> selectBoardList(int boardCode, RowBounds rowBounds);

	/** 게시글 상세 조회
 	 * @param map
	 * @return board
	 */
	Board selectDetail(Map<String, Integer> map);

	/** 게시글 조회수 증가시키기
	 * @param boardNo
	 * @return
	 */
	int updateReadCount(int boardNo);

	/** 좋아요 누른적있어? 검사
	 * @param boardNo
	 * @param memberNo
	 * @return
	 */
	int checkBoardLike(@Param("boardNo") int boardNo ,@Param("memberNo") int memberNo);

	
	/** 좋아요 테이블 삽입
	 * @param boardNo
	 * @param memberNo
	 * @return result2
	 */
	int insertBoardLike(@Param("boardNo") int boardNo ,@Param("memberNo") int memberNo);

	/** 좋아요 테이블 삭제
	 * @param boardNo
	 * @param memberNo
	 * @return result2
	 */
	int deleteBoardLike(@Param("boardNo") int boardNo ,@Param("memberNo") int memberNo);

	/** 좋아요 갯수 조회
 	 * @param boardNo
	 * @return
	 */
	int getLikeCount(int boardNo);


	/** DB에서 모든 게시판 종류를 조회
	 * @return
	 */
	List<Map<String, String>> selectBoardTypeList();

	/** 댓글 목록 조회
	 * @param boardNo
	 * @return commentList
	 */
	List<Comment> selectCommentList(int boardNo);

	/** 검색 게시글 수 조회
	 * @param paramMap(key, query)
	 * @return count
	 */
	int getSearchCount(Map<String, Object> paramMap);

	/** 검색 목록 조회
	 * @param paramMap
	 * @param rowBounds
	 * @return
	 */
	List<Board> selectSearchList(Map<String, Object> paramMap, RowBounds rowBounds);

	/** 목록으로 이동
	 * @param paramMap
	 * @return
	 */
	int getCurrentPage(Map<String, Object> paramMap);



}
