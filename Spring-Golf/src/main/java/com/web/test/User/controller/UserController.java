package com.web.test.User.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.test.User.service.UserService;
import com.web.test.User.vo.UserVo;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String login() {
		return "/user/login";
	}
	
	//로그인 체크
	@RequestMapping("/loginCheck")
	public String loginCheck(UserVo userVo, Model model, HttpSession session, HttpServletResponse response) throws Exception {
		
		UserVo user = userService.loginCheck(userVo);
		
		if(userVo == null) {
			System.out.println("아이디가 잘못됬습니다.");
			return "/user/login";
		}
		
		session.setAttribute("user", user);
		
		if(user != null && userVo.getUser_id().equals(user.getUser_id()) && userVo.getUser_pwd().equals(user.getUser_pwd())) {			
			if(user.getManager_yn().equalsIgnoreCase("y")) {
				System.out.println("로그인 성공");			
				session.setAttribute("user", user);
				System.out.println(user.getManager_yn());
				
				// 쿠키에 유저 정보 저장
	            Cookie cookie = new Cookie("user_id", user.getUser_id());
	            cookie.setMaxAge(60 * 60 * 24 * 7); // 1주일간 유지
	            response.addCookie(cookie);
	            
				return "redirect:/common/mainPage";
			} else {
				System.out.println("일반로그인 성공");
				session.setAttribute("user", user);
				System.out.println(user.getManager_yn());
				
				 // 쿠키에 유저 정보 저장
	            Cookie cookie = new Cookie("user_id", user.getUser_id());
	            cookie.setMaxAge(60 * 60 * 24 * 7); // 1주일간 유지
	            response.addCookie(cookie);
	            System.out.println(cookie.getName());
				return "/common/mainPageUser";
			}	
		}else {
			session.setAttribute("user", user);
			model.addAttribute("msg", "없는 정보입니다.");
			System.out.println(user);
			System.out.println("없는 정보입니다.");
			return "/user/login";
		}
	}
	
	@RequestMapping("/logOut")
	public String logout(HttpSession session) {	
		UserVo user = (UserVo) session.getAttribute("user");
	    String user_id = user.getUser_id();
		session.invalidate();
		System.out.println(user_id + "로그아웃");		
		return "redirect:/login";
	}
}
