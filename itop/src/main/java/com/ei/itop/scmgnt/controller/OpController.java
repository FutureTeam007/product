package com.ei.itop.scmgnt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.json.JSONUtils;
import com.ei.itop.common.dbentity.ScOp;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.scmgnt.service.OpService;

@Controller
@RequestMapping("/op")
public class OpController {

	@Autowired
	private OpService opService;
	/**
	 * 查询列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/changepwd")
	public void changePasswd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		long opId = SessionUtil.getOpInfo().getOpId();
		//旧密码
		String oldPassword = request.getParameter("oldPassword");
		//新密码
		String newPassword = request.getParameter("newPassword");
		//查出旧密码
		String oldPasswordStored = opService.queryScOp(opId).getLoginPasswd();
		if(!oldPasswordStored.equals(oldPassword)){
			throw new BizException("旧密码不正确，请重新输入");
		}
		opService.changeLoginPasswd(opId, newPassword);
	}
	
	/**
	 * 查询列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public void queryAllOp(HttpServletRequest request,HttpServletResponse response) throws Exception{
		long orgId = SessionUtil.getOpInfo().getOrgId();
		List<ScOp> opList = opService.queryAllOp(orgId);
		String jsonData = JSONUtils.toJSONString(opList);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
}
