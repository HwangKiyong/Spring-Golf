package com.web.test.User.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.test.User.service.SignupService;
import com.web.test.User.vo.UserVo;

@Controller
public class SignupController {
	
	@Autowired
	private SignupService signupService;
	
	@GetMapping("/signup")
	public String signup() {
		return "user/signup";
	}
	
	@PostMapping("/signupOk")
	public String signupOk(@ModelAttribute("loginVo") UserVo userVo, Model model) throws Exception{
		signupService.insertRegister(userVo);
		return "/user/success";		//INSERT 후 성공페이지로 이동
		
	}
	
	//아이디 중복확인
	@ResponseBody
	@RequestMapping(value = "/checkIdDuplicate", method = RequestMethod.POST)
	public Map<String, Object> checkIdDuplicate(@RequestParam("id") String user_id) {
		Map<String, Object> result = signupService.checkDuplicateId(user_id);
		if(result.get("result").equals("error")) {
			System.out.println("error occurred: " + result.get("message"));
		}else {
			System.out.println(result);
		}
		
		return result;
	}
	
	//닉네임 중복확인
		@ResponseBody
		@RequestMapping(value = "/checknicknameDuplicate", method = RequestMethod.POST)
		public Map<String, Object> checknicknameDuplicate(@RequestParam("nickname") String user_nickname) {
			Map<String, Object> result = signupService.checkDuplicateNickname(user_nickname);
			if(result.get("result").equals("error")) {
				System.out.println("error occurred: " + result.get("message"));
			}else {
				System.out.println(result);
			}
			
			return result;
		}
}
