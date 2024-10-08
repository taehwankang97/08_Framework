package edu.kh.project.common.interceptor;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BoardNameInterceptor implements HandlerInterceptor{

	// 후처리
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		/* 게시판 관련된 요청/응답을 가로채서 
		 * 현재 어떤 게시판을 조회/삽입/수정 하려는지 알 수 있게
		 * 게시판 이름 끼워 넣기
		 * */
		
		// 1) application scope에서 "boardTypeList" 얻어오기
		ServletContext application = request.getServletContext();
		
		List<Map<String, String>> boardTypeList
			= (List<Map<String, String>>)application.getAttribute("boardTypeList");
		
		
		// 2) "/board", "/editBoard"로 시작하는 요청중
		// {boardCode} 부분인 2번째 코드 값 얻어오기
		// - URL http://localhost/editBoard/3/insert
		// - URI editBoard/3/insert
		String uri = request.getRequestURI();
		
		try {
			String code = uri.split("/")[2]; // boardCode만 잘라내기
			
			//boardTypeList에서 boardCode가 같은 경우에 boardName 찾기 
			for(Map<String, String> boardType : boardTypeList) {
				String boardCode =String.valueOf( boardType.get("boardCode"));
				
				if(boardCode.equals(code)) { // 같은 경우
					//request scope에 boardName 세팅
					request.setAttribute("boardName", boardType.get("boardName"));
					break;
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
}
