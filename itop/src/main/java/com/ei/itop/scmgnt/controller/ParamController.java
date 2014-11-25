package com.ei.itop.scmgnt.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.dazzle.util.json.JSONUtils;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
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
	
	@RequestMapping("/valuelist")
	public void queryParamValues(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OpInfo oi = SessionUtil.getOpInfo();
		String kindCode = request.getParameter("kindCode");
		String locale = SessionUtil.getLocale().toString();
		List<ScParam> params = paramService.getParamValues(oi.getOrgId(),kindCode,locale);
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
	
	@RequestMapping("/modify")
	public void modifyParamValue(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OpInfo oi = SessionUtil.getOpInfo();
		long scParamId1 = VarTypeConvertUtils.string2Long(request.getParameter("scParamId1"));
		long scParamId2 = VarTypeConvertUtils.string2Long(request.getParameter("scParamId2"));
		String paramCode = request.getParameter("paramCode");
		String paramKind = request.getParameter("paramKind");
		String paramValueZh = request.getParameter("paramValueZh");
		String paramValueEn = request.getParameter("paramValueEn");
		List<ScParam> params = new ArrayList<ScParam>();
		ScParam p1 = new ScParam();
		p1.setScParamId(scParamId1);
		p1.setParamCode(paramCode);
		p1.setParamKind(paramKind);
		p1.setParamKindCode(paramCode);
		p1.setScOrgId(oi.getOrgId());
		p1.setParamValue(paramValueZh);
		p1.setLangFlag("zh_CN");
		ScParam p2 = new ScParam();
		p2.setScParamId(scParamId2);
		p2.setParamCode(paramCode);
		p2.setParamKind(paramKind);
		p2.setParamKindCode(paramCode);
		p2.setScOrgId(oi.getOrgId());
		p2.setParamValue(paramValueEn);
		p2.setLangFlag("en_US");
		params.add(p1);
		params.add(p2);
		paramService.addParams(params);
	}
	
	@RequestMapping("/add")
	public void addParamValue(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OpInfo oi = SessionUtil.getOpInfo();
		String paramCode = request.getParameter("paramCode");
		String paramKind = request.getParameter("paramKind");
		String paramValueZh = request.getParameter("paramValueZh");
		String paramValueEn = request.getParameter("paramValueEn");
		List<ScParam> params = new ArrayList<ScParam>();
		ScParam p1 = new ScParam();
		p1.setParamCode(paramCode);
		p1.setParamKind(paramKind);
		p1.setParamKindCode(paramCode);
		p1.setScOrgId(oi.getOrgId());
		p1.setParamValue(paramValueZh);
		p1.setLangFlag("zh_CN");
		ScParam p2 = new ScParam();
		p2.setParamCode(paramCode);
		p2.setParamKind(paramKind);
		p2.setParamKindCode(paramCode);
		p2.setScOrgId(oi.getOrgId());
		p2.setParamValue(paramValueEn);
		p2.setLangFlag("en_US");
		params.add(p1);
		params.add(p2);
		paramService.addParams(params);
	}
	
	@RequestMapping("/remove")
	public void removeParamValue(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String[] ids = request.getParameter("ids").split(",");
		List<Long> idList = new ArrayList<Long>();
		for(int i=0;i<ids.length;i++){
			idList.add(new Long(ids[i]));
		}
		paramService.removeParams(idList);
	}
}
