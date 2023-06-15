package com.web.test.User.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.web.test.User.vo.UserVo;

@Mapper
public interface UserMapper {

	UserVo loginCheck(UserVo userVo);
	
	public int idCheck(String user_id) throws Exception;

	List<UserVo> getUserList();
	
	String getPhoneNumberByGolfName(String user_id);

}
