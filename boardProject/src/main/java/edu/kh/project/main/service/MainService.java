package edu.kh.project.main.service;

import java.util.List;

import edu.kh.project.member.dto.Member;

public interface MainService {

	List<Member> selectMemberList();

	/**
	 * 빠른 로그인
	 * @param memberNo
	 * @return
	 */
	Member directLogin(int memberNo);

	int resetPw(int memberNo);

	int changeBtn(int memberNo);

	
}
