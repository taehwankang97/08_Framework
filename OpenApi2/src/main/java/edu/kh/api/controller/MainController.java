package edu.kh.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller // 컨트롤러 역할 명시 + Bean
@Slf4j // Logger log 필드 자동 생성(Lombok 제공)
@PropertySource("classpath:/config.properties")
public class MainController {
	
	// 서비스키 얻어오기
	@Value("${api.serviceKey.encoding}")
	private String encodingServiceKey;
	
	@Value("${api.serviceKey.decoding}")
	private String decodingServiceKey;
	

	@GetMapping("/")
	public String mainPage() {
		
		// classpath:/templates/main.html -> forward 
		// -> 해석된 HTML 코드가 클라이언트에게 전달(응답)
		return "main";
	}
	
	
	/* ResponseEntity : @ResponseBody + 응답 상태 코드
	 * 
	 * - HTTP 응답을 표현할 수 있는 객체
	 * - 비동기 응답 서비스(RESTful API)에서 유용하게 사용
	 */
	@PostMapping("getServiceKey")
	public ResponseEntity<?> getServiceKey(){
		
		try {
			return new ResponseEntity<String>(encodingServiceKey, HttpStatus.OK);
		
		}catch(Exception e) {
			return new ResponseEntity<String>("에러", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
}
