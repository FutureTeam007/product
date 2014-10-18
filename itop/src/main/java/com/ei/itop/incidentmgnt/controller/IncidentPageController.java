package com.ei.itop.incidentmgnt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.dbentity.ScParam;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.scmgnt.service.ParamService;

@Controller
@RequestMapping("/page/incidentmgnt")
public class IncidentPageController {

	@Autowired
	private ParamService paramService;
	
	/**
	 * 进入管理页面
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/main")
	public ModelAndView mgntMain(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OpInfo oi = SessionUtil.getOpInfo();
		ModelAndView mav = new ModelAndView();
		//查询影响度数据
		List<ScParam> affectP = paramService.getParamList(oi.getOrgId(), "IC_EFFECT");
		//查询优先级数据
		List<ScParam> priorityP = paramService.getParamList(oi.getOrgId(), "IC_PRIORITY");
		mav.addObject("affectP", affectP);
		mav.addObject("priorityP", priorityP);
		mav.setViewName("page/incidentmgnt/incidentQry");
		return mav;
	}
	
	/**
	 * 进入新增或查看页面
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/dtl")
	public ModelAndView mgntDtl(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OpInfo oi = SessionUtil.getOpInfo();
		ModelAndView mav = new ModelAndView();
		//查询影响度数据
		List<ScParam> affectP = paramService.getParamList(oi.getOrgId(), "IC_EFFECT");
		mav.addObject("affectP", affectP);
		mav.setViewName("page/incidentmgnt/incidentDtl");
		return mav;
	}
}
