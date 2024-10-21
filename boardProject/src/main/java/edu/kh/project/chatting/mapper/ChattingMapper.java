package edu.kh.project.chatting.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.project.chatting.dto.ChattingRoom;
import edu.kh.project.chatting.dto.Message;
import edu.kh.project.member.dto.Member;

@Mapper
public interface ChattingMapper {

	/** 상대 검색
	 * @param query
	 * @param memberNo
	 * @return
	 */
	List<Member> selectTarget(@Param("query") String query, @Param("memberNo") int memberNo);

	/** 채팅방 입장
	 * @param targetNo
	 * @param memberNo
	 * @return
	 */
	int checkChattingRoom(
		  @Param("targetNo")	int targetNo,@Param("memberNo") int memberNo);

	/** 채팅방 테이블 삽입
	 * @param map
	 * @return
	 */
	int createChattingRoom(Map<String, Integer> map);

	/**로그인한 회원이 참여한 채팅방 목록 조회
	 * @return
	 */
	List<ChattingRoom> selectRoomList(int memberNo);

	// 특정 채팅방의 메시지 모두 조회 하기
	List<Message> selectMessage(int chattingNo);

	// 채팅 읽음 표시
	int updateReadFlag(@Param("chattingNo") int chattingNo,@Param("memberNo") int memberNo);

	/** 메시지 삽입
	 * @param msg
	 * @return
	 */
	int insertMessage(Message msg);


	
	
	
}
