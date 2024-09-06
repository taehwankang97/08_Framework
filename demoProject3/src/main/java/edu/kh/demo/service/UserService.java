package edu.kh.demo.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.demo.dto.UserDto;

public interface UserService {

	/** 사용자 이름 조회 
	 * @param userNo
	 * @return userName
	 */
	String selectUserName(int userNo);

	/** 사용자 전체 조회
	 * @return userList
	 */
	List<UserDto> selectAll();

	/** userNo가 일치하는 사용자 조회
	 * @param userNo
	 * @return user
	 */
	UserDto selectUser(int userNo);

	/** 사용자 정보 수정
	 * @param user
	 * @return result
	 */
	int updateUser(UserDto user);

	int deleteUser(int userNo);

	int insertUser(UserDto user);

	

}

