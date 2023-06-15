package com.web.test.User.service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.test.User.mapper.SignupMapper;
import com.web.test.User.vo.UserVo;

@Service
public class SignupService {

	@Autowired
	private SignupMapper signupMapper;
	
	public void insertRegister(UserVo user) throws Exception {
		signupMapper.insertRegister(user);
	}
	
	//Map 타입으로 반환할것을 선언	,아이디중복확인
	public Map<String, Object>checkDuplicateId(String user_id) {
		try {
			Map<String, Object>result = signupMapper.selectByIdWithResult(user_id);
			if(result == null) {	
				result = new HashMap<>();
				result.put("result", "notExist");
			} else {
				result.put("result", "exist");
			}
			return result.entrySet().stream()
					.collect(Collectors.toMap(Map.Entry::getKey, e -> String.valueOf(e.getValue())));
		} catch(Exception e) {
			Map<String, Object> result = new HashMap<>();
			result.put("result", "error");
			result.put("message", e.getMessage());
			return result;
		}
	}

	public Map<String, Object> checkDuplicateNickname(String user_nickname) {
		try {
			Map<String, Object>result = signupMapper.selectBynicknameWithResult(user_nickname);
			if(result == null) {	
				result = new HashMap<>();
				result.put("result", "notExist");
			} else {
				result.put("result", "exist");
			}
			return result.entrySet().stream()
					.collect(Collectors.toMap(Map.Entry::getKey, e -> String.valueOf(e.getValue())));
		} catch(Exception e) {
			Map<String, Object> result = new HashMap<>();
			result.put("result", "error");
			result.put("message", e.getMessage());
			return result;
		}
	}
}
