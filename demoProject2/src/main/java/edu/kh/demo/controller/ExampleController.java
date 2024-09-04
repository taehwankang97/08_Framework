package edu.kh.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.demo.dto.Student;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j //log 필드 생성 및 초기화 자동완성 lombok 어노테이션
@RequestMapping("example")// example로 시작하는 요청을 매핑
@Controller // 컨트롤러임을 명시 + Bean 등록
public class ExampleController {
	
// Servlet/JSP 내장 객체 4 종류 데이터 유지 범위 (scope)
		
		// 1) page : 현재 페이지 까지 
		// 2) request : 요청 받은 곳 + 요청 위임 받은 곳 (forward)
		// 3) session : 세션이 만료 될 때까지
		//    (클라이언트가 서버 최초 연결 시 생성, 연결한브라우저 종료 세션시간 만료 까지 )
		// 4) appication : 서버 실행시 1개만 생성 
		//                 서버가 닫힐때 까지
	
	
	 /*Model
	  *  -org.springframwork.ui 패키지
	  *  
	  *  -spring에서 데이터를 전달하는 역활의 객체 
	  *  
	  *  - 데이터 유지 범위 (scope) : 기본 request
	  *  
	  *  - @sessionAttributes 와 함께 사용하면 session scope로 변경 
	  *  
	  *  [Model을 이용해서 값을 세팅 하는 방법 ]
	  *  Model.addAttribute("key", value);
	  * 
	  * 
	  * */
	@GetMapping("ex1")
	public String ex1(HttpServletRequest req, Model model) {
		
		// request scop에 값 세팅 
		req.setAttribute("test1", "HttpServletRequest로 세팅한값");
		
		// model을 이용해서 request scope 값 세팅 
		model.addAttribute("test2", "model로 세팅한 값asd");
		
		// 단일 값 세팅 (숫자 문자열)
		model.addAttribute("productName", "아이스 아메리카노");
		model.addAttribute("price", 2000);
		
		// 복수 값 세팅 (배열 ,List)
		
		List<String> fruitList = new ArrayList<>();
		 fruitList.add("복숭아");
		 fruitList.add("딸기");
		 fruitList.add("수박");
		 fruitList.add("바나나");
		 
		 model.addAttribute("fruitList", fruitList);
		
		 // DTO 객체를 만들어 Model에 세팅 + 빌더 패턴 사용 
		  Student std = Student.builder()
		  							.studentNo("05")
		  							.name("신노스케")
		  							.age(5)
		  							.build();
		 // 필드 전체가 아닌 일부 초기화 시 활용도가 좋음
		  
		  log.debug("std : {}", std);
		 
		  model.addAttribute("std",std);
		  
		  
		  
		  
		  // DTO 필드중 List가 포함되어 있는 경우 
		  List<String> hobbyList = new ArrayList<>();
		  hobbyList.add("축구");
		  hobbyList.add("독서");
		  hobbyList.add("코딩공부");
		  
		  Student std2 = Student.builder()
		  							.studentNo("2222")
		  							.name("맹구")
		  							.age(100)
		  							.hobbyList(hobbyList)
		  							.build();
		  
		  model.addAttribute("std2",std2);
		  
		  
		// classpath:/templates/ex/result1.html 파일로
		// forward(요청위임)
		return "ex/result1";
	}
	
	/**
	 * @param model: Spring에서 데이터를 전달하는 용도의 객체
	 * 									 (기본 scope : request)
	 * @return
	 */
	@PostMapping("ex2") // /example/ex2 POST 방식 요청 매핑 
	public String ex2(Model model) {
		
		model.addAttribute("str", "<h1>테스트중입니다 ... &times;</h1>");
		
		
		 // classpath:/templates/ex/result2.html 파일로 
		 // forward(요청 위임)
		return "ex/result2";
	}
	
	
	
	
}
