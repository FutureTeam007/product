package com.ei.itop.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("")
public class LoginController {

	@RequestMapping("/login")
	public String userLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return "page/login/userLogin";
	}
	
	@RequestMapping("/login-svc")
	public String consultantLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return "page/login/csLogin";
	}
	
	@RequestMapping("/doLogin")
	public String doLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return "page/login/userLogin";
	}
	
	@RequestMapping("/doLogout")
	public String doLogout(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return "page/login/userLogin";
	}
	
}
