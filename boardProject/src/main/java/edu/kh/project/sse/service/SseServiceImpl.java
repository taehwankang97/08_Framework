package edu.kh.project.sse.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.member.dto.Member;
import edu.kh.project.sse.dto.Notification;
import edu.kh.project.sse.mapper.SseMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SseServiceImpl implements SseService{

	private final SseMapper mapper;
	

	@Override
	public Map<String, Object> insertNotification(Notification notification) {
		
		// 매개변수 notification에 저장된 값
		// -> type, url, content, pkNo, sendMemberNo
		
		Map<String, Object> map = null;

		// 알림의 타입(종류)에 따라 다른 mapper 호출
	int result = mapper.insertNotification(notification);
		
	if(result > 0 ) {// 알림 삽입 성공시 
		
		// 알림을 받아야 하는 회원의 번호 + 안읽은 알람 개수 조회
		
		map = mapper.selectReceiveMember(notification.getNotificationNo());
		
	}
	
		return map;
	}
	/** 로그인한 회원의 알림 목록 조회
	 */
	@Override
	public List<Notification> selectNotificationList(int memberNo) {
		return mapper.selectNotificationList(memberNo);
	}

	/** 읽지 않은 알람 조회
	 */
	@Override
	public int notReadcheck(int memberNo) {
		
		
		return mapper.notReadCheck(memberNo);
	}
	
	@Override
	public void deleteNotification(int notificationNo) {
		
	  mapper.deleteNotification(notificationNo);
	}
/**
 * 알림 읽음 처리 
 */
	@Override
	public void updatNotification(int notificationNo) {

		mapper.updateNotification(notificationNo);
	}
	
}
