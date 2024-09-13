package edu.kh.project.myPage.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.dto.Member;

@Mapper // 상속 받은 클래스 구현 + Bean 등록
public interface MyPageMapper {

	int updateInfo(Member inputMember);

	int checkNickname(String input);

	
}
