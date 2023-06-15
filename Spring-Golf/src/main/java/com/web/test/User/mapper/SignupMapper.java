package com.web.test.User.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.web.test.User.vo.UserVo;

@Mapper
public interface SignupMapper {

	void insertRegister(UserVo user);	//회원정보 DB에 삽입(회원가입)
	
	void selectById(String user_id);	//아이디 중복확인
	
	Map<String, Object>selectByIdWithResult(String user_id) throws Exception;	//아이디 중복확인 결과 반환 

	Map<String, Object> selectBynicknameWithResult(String user_nickname) throws Exception;
}
