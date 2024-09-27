package edu.kh.project.myPage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.project.member.dto.Member;

@Mapper // 상속 받은 클래스 구현 + Bean 등록
public interface MyPageMapper {

	int updateInfo(Member inputMember);

	int checkNickname(String input);

	/** 비밀번호 변경
	 * @param memberNo
	 * @param encPw
	 * @return result
	 */
	int changePw(
			@Param("memberNo")int memberNo,
			@Param("encPw") String encPw);

	int secession(int memberNo);

	/** 프로필 이미지 수정
	 * @param url
	 * @param memberNo
	 * @return
	 */
	int profile(
			@Param("url") String url, 
			@Param("memberNo") int memberNo);

	/*마이바티스 Mapper 인터페이스 메서드 호출 시 
	 * 별도의 어노테이션이 없다면 
	 * 첫번째 매개변수만 
	 * mapper.xml 파일에 전달되는 parameter로 인식된다
	 * [해결방법]
	 * 1. DTO, 컬렉션을 이용해 묶어서 전달 
	 * 2. @Param 어노테이션을 이용해 파라미터로 인식
	 * 
	 *  @param("key") 자료형 변수명 
	 *   - SQL 중 #{key} 자리에 들어가 값을 지정
	 * */
	
}
