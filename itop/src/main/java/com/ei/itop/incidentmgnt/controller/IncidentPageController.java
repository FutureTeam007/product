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
import com.ei.itop.incidentmgnt.bean.IncidentCountInfoByState;
import com.ei.itop.incidentmgnt.bean.QCIncident;
import com.ei.itop.incidentmgnt.service.IncidentService;
import com.ei.itop.scmgnt.service.ParamService;

@Controller
@RequestMapping("/page/incidentmgnt")
public class IncidentPageController {

	@Autowired
	private ParamService paramService;
	@Autowired
	private IncidentService incidentService;
	
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
		//查询满意度数据
		List<ScParam> feedbackP = paramService.getParamList(oi.getOrgId(), "IC_FEEDBACK_CODE");
		//查询归档数据
		List<ScParam> stockP = paramService.getParamList(oi.getOrgId(), "IC_ARCHIVE_FLAG");
		//查询状态数据
		List<ScParam> statusP = paramService.getParamList(oi.getOrgId(), "IC_STATE");
		mav.addObject("affectP", affectP);
		mav.addObject("priorityP", priorityP);
		mav.addObject("feedbackP", feedbackP);
		mav.addObject("stockP", stockP);
		mav.addObject("statusP", statusP);
		mav.setViewName("page/incidentmgnt/incidentQry");
		return mav;
	}
	
	/**
	 * 进入新增或编辑页面
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
	
	/**
	 * 进入编辑页面
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/view")
	public ModelAndView mgntView(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OpInfo oi = SessionUtil.getOpInfo();
		ModelAndView mav = new ModelAndView();
		//查询影响度数据
		List<ScParam> affectP = paramService.getParamList(oi.getOrgId(), "IC_EFFECT");
		//查询优先级数据
		List<ScParam> priorityP = paramService.getParamList(oi.getOrgId(), "IC_PRIORITY");
		//查询复杂度数据
		List<ScParam> complexP = paramService.getParamList(oi.getOrgId(), "IC_COMPLEX");
		//查询完成结果数据
		List<ScParam> finishValP = paramService.getParamList(oi.getOrgId(), "IC_FINISH_CODE");
		mav.addObject("affectP", affectP);
		mav.addObject("priorityP", priorityP);
		mav.addObject("complexP", complexP);
		mav.addObject("finishValP", finishValP);
		mav.setViewName("page/incidentmgnt/incidentView");
		return mav;
	}
}
