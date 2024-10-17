package edu.kh.project.sse.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import edu.kh.project.member.dto.Member;
import edu.kh.project.sse.dto.Notification;
import edu.kh.project.sse.service.SseService;
import lombok.extern.slf4j.Slf4j;


@RestController // @Controller + @Responsebody 비동기 요청만 하는 Controller
@Slf4j
public class SseController {
	
	@Autowired
	private SseService service;

	//SseEmitter : 서버로 부터 메시지를 전달 받을 
	// 클라이언트 정보를 저장한 객체 == 연결된 클라이언트
	
	
	//ConcurrentHashMap : 멀티스테드 환경에서 동기화를 보장하는 Map
	// -> 한 번에 많은 요청이 있어도 차례대로 처리
	private final Map<String, SseEmitter> emitters
	 = new ConcurrentHashMap<>(); // == 연결된 클라이언트 대기 명단
	
	@GetMapping("sse/connect")
	public SseEmitter sseConnect(
			@SessionAttribute("loginMember") Member loginMember){
		// Map에 저장될 key 값으로 회원 번호 얻어오기
		
		String clientId = loginMember.getMemberNo() + "";
		
		// SseEmitter 객체 생성
		// -> 연결 대기 시간 10분 설정 (ms 단위)
		SseEmitter emitter = new SseEmitter(10 * 60* 1000L);
		
		// 클라이언트 정보를 Map에 추가 
		emitters.put(clientId, emitter);
		
		// 클라이언트 연결 종료 시 Map에서 제거
		emitter.onCompletion(()-> emitters.remove(clientId));
		
		// 클라이언트 타임 아웃시 Map에서 제거 
		emitter.onTimeout(() -> emitters.remove(clientId));
		
		
		
		
		return emitter;
	}
	
	/** 알림 메시지 전송*/
	@PostMapping("sse/send")
	public void sendNotification(
			@RequestBody Notification notification,
			@SessionAttribute("loginMember") Member loginMember ) {
		
		// 알림 보낸 회원 (현재 로그인한 회원) 번호 추가
		notification.setSendMemberNo(loginMember.getMemberNo());
		
		//전달 받은 데이터를DB에 저장하고 
		// 알림 받을 회원의 번호
		// + 해당 회원이 읽이 않은 알림 개수 반환 받는 서비스 호출 
		Map<String , Object> map
		 = service.insertNotification(notification);
		
		// 알림을 받을 클라이언트 id == 알림을 받을 회원 번호
		String clientId = map.get("receiveMemberNo").toString();
		
		// 연결된 클라이언트 대기 명단 (emitters)에서 
		// clientId 가 일치하는 클라이언트 찾기
		SseEmitter emitter = emitters.get(clientId);
		
		//client가 일치하는 클라이언트가 있을 경우 
		if(emitter != null){
      try{
        emitter.send( map );
      }catch(Exception e){
        emitters.remove(clientId);
      }
    }
	}	
	
	//-----------------------------------------------------------------------------------
	
	@GetMapping("notification")
	public List<Notification> selectNotificationList(
			@SessionAttribute("loginMember") Member loginMember){
		int memberNo = loginMember.getMemberNo();
		return service.selectNotificationList(memberNo);
	}
	
	
	
}