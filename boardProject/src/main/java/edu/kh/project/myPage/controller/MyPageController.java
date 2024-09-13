package edu.kh.project.myPage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.dto.Member;
import edu.kh.project.myPage.service.MyPageService;

// @SessionAttribute"s" 용도 
// 1. Model을 이용하여 값을 request -> session으로 scope 변경
// 2. @SessionAttribute를 이용해 
//		@SessionAttribute"s"에 의해서 session에 등록된 값을 
//    얻어올 수 있음

@SessionAttributes({"loginMember"}) 
@Controller
@RequestMapping("myPage")
public class MyPageController {
	
	@Autowired // DI
	private MyPageService service;
	
	
	/** 마이페이지(내 정보) 전환 
	 * @return
	 */
	@GetMapping("info")
	public String info(
			@SessionAttribute("loginMember") Member loginMember,
			Model model	) { // Model 데이터 전달 객체 
		
		if(loginMember.getMemberAddress() != null) {
			
			// 주소를 , 기준으로 나눠서 배열형태(String)로 나눠라
			String[] arr = loginMember.getMemberAddress().split(",");
			// "04540,서울 중구 남대문로 120,2층"
			// -> {"04540", "서울 중구 남대문로 120", "2층"}
			
			model.addAttribute("postCode", arr[0]);
			model.addAttribute("address", arr[1]);
			model.addAttribute("detailAddress", arr[2]);
			
			
		}
		
		return "myPage/myPage-info";
	}
	
	
	/** 내 정보 수정
	 * @param inputMember : 수정할 닉네임, 전화번호, 주소
	 * @param loginMember : 현재 로그인된 회원 정보
	 * 	session에 저장된 Member 객체의 주소가 반환됨
	 *  == session에 저장된 Member 객체의 데이터를 수정할 수 있음
	 * 	
	 * @param ra : 리다이렉트 시 request scope로 값 전달
	 * @return
	 */
	@PostMapping("info")
	public String updateInfo(
		@ModelAttribute Member inputMember,
		@SessionAttribute("loginMember") Member loginMember,
		RedirectAttributes ra) {
		
		// @SessionAttribute("key")
		// - @SessionAttribute"s"를 통해 session에 올라간 값을 얻어오는
		//   어노테이션
		
		// - 사용 방법 
		// 1) 클래스 위에  @SessionAttribute"s" 어노테이션을 작성하고
		//    해당 클래스에서 꺼내서 사용할 값의 key를 작성
		//    --> 그럼 세션에서 값을 미리 얻어와 놓음
		
		// 2) 필요한 메서드 매개 변수에
		//    @SessionAttribute("key")를 작성하면
		//    해당 key와 일치하는 session 값을 얻어와서 대입
		
		
		// 1. inputMember에 로그인된 회원 번호를 추가
		int memberNo = loginMember.getMemberNo();
		inputMember.setMemberNo(memberNo);
		
		// 2. 회원 정보 수정 서비스 호출 후 결과 반환 받기
		int result = service.updateInfo(inputMember);
		
		// 3. 수정 결과에 따라 message 지정
		String message = null;
		if(result > 0) 	message = "수정 성공";
		else						message = "수정 실패";
		
		ra.addFlashAttribute("message", message);
		// -> footer.html 조각에서 alert() 수행
		
		//4. 수정 성공 시 
		// session 정장된 로그인 회원 정보를 
		// 수정 값으로 변경해서 DB와 같은 데이터를 가지게 함
		loginMember.setMemberNickname(inputMember.getMemberNickname());
		loginMember.setMemberTel(inputMember.getMemberTel());
		loginMember.setMemberAddress(inputMember.getMemberAddress());
		
		return "redirect:info"; // /myPage/info GET방식 요청
	}
	
	
	/** 닉네임 중복 검사 
	 * @param input
	 * @return 0: 중복X 1: 중복O
	 */
	@ResponseBody // 응답 본문(Ajax 코드)에 값 그대로 반환 
	@GetMapping("checkNickname")
	public int checkNickname(
			@RequestParam("input") String input
			) {
		
		return service.checkNickname(input);
	}
	
	
}
