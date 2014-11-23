package com.ei.itop.scmgnt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.dazzle.util.json.JSONUtils;
import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.dbentity.ScParam;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.scmgnt.service.ParamService;

@Controller
@RequestMapping("/param")
public class ParamController {

	@Autowired
	private ParamService paramService;
	/**
	 * 查询列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public void queryParamList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OpInfo oi = SessionUtil.getOpInfo();
		String kindCode = request.getParameter("kindCode");
		List<ScParam> params = paramService.getParamList(oi.getOrgId(), kindCode);
		String jsonData = null;
		if(params!=null){
			jsonData = JSONUtils.toJSONString(params);
		}else{
			jsonData = "[]";
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	
	@RequestMapping("/select")
	public void queryParamSelect(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OpInfo oi = SessionUtil.getOpInfo();
		List<ScParam> params = paramService.getParamDistinct(oi.getOrgId());
		String jsonData = null;
		if(params!=null){
			jsonData = JSONUtils.toJSONString(params);
		}else{
			jsonData = "[]";
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
}
