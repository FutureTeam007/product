package com.ei.itop.custmgnt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.json.JSONUtils;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ei.itop.common.dbentity.CcUser;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.custmgnt.service.UserService;

@Controller
@RequestMapping("/custmgnt/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/get")
	public void queryUserInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		long userId = VarTypeConvertUtils.string2Long(request.getParameter("userId"));
		CcUser user = userService.queryUser(userId);
		String jsonData = JSONUtils.toJSONString(user);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	
	@RequestMapping("/changepwd")
	public void changePasswd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//取得用户ID
		long opId = SessionUtil.getOpInfo().getOpId();
		//旧密码
		String oldPassword = request.getParameter("oldPassword");
		//新密码
		String newPassword = request.getParameter("newPassword");
		//查出旧密码
		String oldPasswordStored = userService.queryUser(opId).getLoginPasswd();
		if(!oldPasswordStored.equals(oldPassword)){
			throw new BizException("旧密码不正确，请重新输入");
		}
		userService.changeLoginPasswd(opId, newPassword);
	}
	
	
}
