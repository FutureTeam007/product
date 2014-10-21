package com.ei.itop.common.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.dazzle.util.misc.VerifyCodeUtil;
import com.ei.itop.common.constants.SysConstants;

@Controller
@RequestMapping("/verifycode")

public class VerifyCodeController {

	@RequestMapping("/get")
	public void getVerifyCode(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		//设置Mime类型
		response.setContentType(VerifyCodeUtil.getMimeType());
		response.reset();
		//生成验证码
		String sRand = VerifyCodeUtil.createVerifyCode(response.getOutputStream()) ;
		//将生成的验证码放入session
		HttpSession session = request.getSession();
		session.setAttribute(SysConstants.SessionAttribute.LOGIN_VERIFY_CODE, sRand);
	}
}
