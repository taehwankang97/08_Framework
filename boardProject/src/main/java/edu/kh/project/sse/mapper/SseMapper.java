package edu.kh.project.sse.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.dto.Member;
import edu.kh.project.sse.dto.Notification;

@Mapper
public interface SseMapper {

	/** 알림 삽입
	 * @param notification
	 * @return
	 */
	int insertNotification(Notification notification);

	/** 알림을 받아야하는 회원의 번호 + 안읽은 알람 개수 조회
	 * @param notificationNo
	 * @return map
	 */
	Map<String, Object> selectReceiveMember(int notificationNo);

	List<Notification> selectNotificationList(int memberNo);

	
	// 읽지 않은 알람 조회
	int notReadCheck(int memberNo);

	// 알람 삭제
	void deleteNotification(int notificationNo);

	// 알림 읽음 처리
	void updateNotification(int notificationNo);

	
	
	
}

