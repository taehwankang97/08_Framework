package edu.kh.project.main.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.project.main.service.MainService;
import edu.kh.project.member.dto.Member;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;



@Controller // 요청,응답 제어하는 Controller 역활 명시 + Bean등록
@RequiredArgsConstructor
public class MainController {

	private final MainService service;
	
		@RequestMapping("/") // "/" 요청 매핑(method 가리지 않음)
		public String mainPage() {
			
			// 접두사 : classpath:/templates/
			// 접미사 : .html
			// -> forward 하려는 파일의 ThymeLeaf 
			//   접두사, 접미사를 제외한 경로 작성
			return "common/main";
		}
		
		@ResponseBody
		@GetMapping("selectMemberList")
		public List<Member> selectMemberList() {
			return  service.selectMemberList();
		}
		
		
}
