package edu.kh.project.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.project.member.dto.Member;

@Mapper // 인터페이스 상속 클래스 생성 + 클래스 Bean등록
public interface MainMapper {

	List<Member> selectMemberList();

	Member directLogin(int memberNo);

	int resetPw(
			@Param("memberNo") int memberNo,
			@Param("encPw") String encPw);

	int changeBtn(int memberNo);

 

}
