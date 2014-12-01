package com.ei.itop.scmgnt.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.dazzle.util.json.JSONUtils;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.scmgnt.bean.SloTime;
import com.ei.itop.scmgnt.service.ScOrgService;

@Controller
@RequestMapping("/scmgnt/slo")
public class SloController {

	@Autowired
	ScOrgService scOrgService;
	
	/**
	 * 获得时间设置
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getTimeConfig")
	public void getTimeConfig(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SloTime sloTime = scOrgService.querySloTimeConfig(SessionUtil.getOpInfo().getOrgId());
		String jsonData = JSONUtils.toJSONString(sloTime);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	
	/**
	 * 修改时间设置
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/modifyTimeConfig")
	public void modifyTimeConfig(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int timeType = VarTypeConvertUtils.string2Integer(request.getParameter("timeType"));
		long paramId = VarTypeConvertUtils.string2Long(request.getParameter("paramId"));
		String workStartTimeAm = request.getParameter("workStartTimeAm");
		String workEndTimeAm = request.getParameter("workEndTimeAm");
		String workStartTimePm = request.getParameter("workStartTimePm");
		String workEndTimePm = request.getParameter("workEndTimePm");
		SloTime sloTime = new SloTime();
		sloTime.setParamId(paramId);
		sloTime.setTimeType(timeType);
		sloTime.setWorkEndTimeAm(workEndTimeAm);
		sloTime.setWorkEndTimePm(workEndTimePm);
		sloTime.setWorkStartTimeAm(workStartTimeAm);
		sloTime.setWorkStartTimePm(workStartTimePm);
		scOrgService.modifySloTimeConfig(sloTime);
	}
	
}
