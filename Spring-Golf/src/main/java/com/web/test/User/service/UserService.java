package com.web.test.User.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.test.User.mapper.UserMapper;
import com.web.test.User.vo.UserVo;

@Service
public class UserService {

	
	@Autowired	//LoginMapper에게 보낸다는 것이다.
	private UserMapper userMapper;

	public UserVo loginCheck(UserVo userVo) {
		return userMapper.loginCheck(userVo);
	}	
	
	public int idCheck(String user_id) throws Exception {
		return userMapper.idCheck(user_id);
	}

	public List<UserVo> getUserList() {
		return userMapper.getUserList();
	}
}
