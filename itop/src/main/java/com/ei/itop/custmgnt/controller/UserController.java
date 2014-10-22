package com.ei.itop.custmgnt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.dazzle.util.json.JSONUtils;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ei.itop.common.bean.OpInfo;
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
	
	
}
