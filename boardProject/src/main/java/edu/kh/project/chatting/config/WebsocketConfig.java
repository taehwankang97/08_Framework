package edu.kh.project.chatting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import edu.kh.project.chatting.handler.ChattingWebsocketHadler;
import lombok.RequiredArgsConstructor;

@Configuration // 서버 실행 시 메서드 모두 수행
@EnableWebSocket // 웹소켓을 사용하겠다라는 설정(활성화)
@RequiredArgsConstructor // 생성자 방식 의존성 주입
public class WebsocketConfig implements WebSocketConfigurer{

	// SessionHandsasjeInterceptor 빈 의존성 주입 받기
	private final HandshakeInterceptor handshakeInterceptor;
	
	// 채팅 핸들러 의존성 주입 받기
	private final ChattingWebsocketHadler chattingWebsocketHandler;
	
	
	// 웹소켓 핸들러 등록하는 메서드
	@Override
		public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
			
	  registry
    // 핸들러 등록
    .addHandler(chattingWebsocketHandler, "/chattingSock")
    
    // 세션 가로채는 인터셉터 등록
    .addInterceptors(handshakeInterceptor)
    
    //
    .setAllowedOriginPatterns(
    		"http://localhost/",
    		"http://127.0.0.1/",
    		"http://192.168.10.91"
    		)
    // sockJS 지원 + 브라우저 호환성 증가
    .withSockJS();
		
		
		}
	
	
}
