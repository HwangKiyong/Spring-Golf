package com.web.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.test.User.vo.UserVo;

@Controller
@RequestMapping("/common/")
public class HomeController {
	@GetMapping("/mainPage")
	public String mainPage(UserVo userVo) {
		System.out.println("메인 컨트롤러");
		return "/common/mainPage";
	}
}
