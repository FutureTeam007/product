package com.ei.itop.incidentmgnt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.dazzle.util.json.JSONUtils;
import com.ailk.dazzle.util.type.DateUtils;
import com.ailk.dazzle.util.type.StringUtils;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.dbentity.IcAttach;
import com.ei.itop.common.dbentity.IcIncident;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.incidentmgnt.bean.IncidentInfo;
import com.ei.itop.incidentmgnt.bean.QCIncident;
import com.ei.itop.incidentmgnt.service.IIncidentService;

@Controller
@RequestMapping("/incident")
public class IncidentController {

	@Autowired
	private IIncidentService incidentService;
	
	@RequestMapping(value="/list")
	public void queryIncidentList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OpInfo oi = SessionUtil.getOpInfo();
		//获取分页起始位置
		long startIndex = VarTypeConvertUtils.string2Long(request.getParameter("start"),0);
		//获取分页大小
		int pageSize = VarTypeConvertUtils.string2Integer(request.getParameter("pageSize"),0);
		//构建查询条件实体
		QCIncident qi = new QCIncident();
		//影响度
		String affectVal = request.getParameter("affectVal");
		if(!StringUtils.isEmpty(affectVal)){
			qi.setAffectVal(affectVal.split(","));
		}
		//简述
		String brief = request.getParameter("brief");
		if(!StringUtils.isEmpty(brief)){
			qi.setBrief(brief);
		}
		//类别
		String classVal = request.getParameter("classVal");
		if(!StringUtils.isEmpty(classVal)){
			qi.setClassVal(classVal.split(","));
		}
		//事件系列号
		String incidentCode = request.getParameter("incidentCode");
		if(!StringUtils.isEmpty(incidentCode)){
			qi.setIncidentCode(incidentCode);
		}
		//两个时间排序字段
		//TODO
		qi.setOrderByLastModifyTime("1");
		qi.setOrderByRegisterTime("1");
		//优先级
		String priorityVal = request.getParameter("priorityVal");
		if(!StringUtils.isEmpty(priorityVal)){
			qi.setPriorityVal(priorityVal.split(","));
		}
		//产品线
		String productId = request.getParameter("productId");
		if(!StringUtils.isEmpty(productId)){
			qi.setProductId(VarTypeConvertUtils.string2Long(productId,0));
		}
		//登记时间起始
		String registerTimeBegin = request.getParameter("registerTimeBegin");
		if(!StringUtils.isEmpty(registerTimeBegin)){
			qi.setRegisterTimeBegin(DateUtils.string2Date(registerTimeBegin, DateUtils.FORMATTYPE_yyyy_MM_dd));
		}
		//登记时间截止
		String registerTimeEnd = request.getParameter("registerTimeEnd");
		if(!StringUtils.isEmpty(registerTimeEnd)){
			qi.setRegisterTimeEnd(DateUtils.string2Date(registerTimeEnd, DateUtils.FORMATTYPE_yyyy_MM_dd));
		}
		//事件状态
		String stateVal = request.getParameter("stateVal");
		qi.setStateVal(stateVal);
		//调用查询获取总数据条数
		long count = incidentService.MBLQueryIncidentCount(qi, oi);
		//调用查询获取分页数据
		List<IcIncident> data = incidentService.MBLQueryIncident(qi, startIndex, pageSize, oi.getOpId());
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("total", count);
		result.put("rows", data);
		String jsonData = JSONUtils.toJSONString(result);
		response.getWriter().print(jsonData);
	}
	
	@RequestMapping(value="/add")
	public void addIncident(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setAttribute("notCommit", true);
		addIncidentAutoCommit(request,response);
	}
	
	@RequestMapping(value="/addc")
	public void addIncidentAutoCommit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OpInfo oi = SessionUtil.getOpInfo();
		IncidentInfo ii = new IncidentInfo();
		//产品线
		String scProductId = request.getParameter("scProductId");
		ii.setScProductId(VarTypeConvertUtils.string2Long(scProductId));
		//服务目录
		String scModuleId = request.getParameter("scModuleId");
		String moduleName = request.getParameter("moduleName");
		ii.setScModuleId(VarTypeConvertUtils.string2Long(scModuleId));
		ii.setModuleName(moduleName);
		//影响度
		String affectCodeUser = request.getParameter("affectCodeUser");
		String affectValUser = request.getParameter("affectValUser");
		ii.setAffectCodeUser(affectCodeUser);
		ii.setAffectValUser(affectValUser);
		//事件类别
		String classCodeUser = request.getParameter("classCodeUser");
		ii.setClassCodeUser(classCodeUser);
		//事件简述
		String brief = request.getParameter("brief");
		ii.setBrief(brief);
		//发生时间
		String happenTime = request.getParameter("happenTime");
		ii.setHappenTime(DateUtils.string2Date(happenTime, DateUtils.FORMATTYPE_yyyy_MM_dd));
		//详细描述
		String detail = request.getParameter("happenTime");
		ii.setDetail(detail);
		//抄送
		String ccList = request.getParameter("ccList");
		ii.setCcList(ccList);
		//附件
		String attachList = request.getParameter("attachList");
		List<IcAttach> attachs = JSONUtils.parseArray(attachList, IcAttach.class);
		ii.setAttachList(attachs);
		if(request.getAttribute("notCommit")!=null){
			incidentService.MBLAddIncidentAndAttach(ii, oi);
		}else{
			incidentService.MBLAddAndCommitIncidentAndAttach(ii, oi);
		}
	}
	
	@RequestMapping(value="/modify")
	public void modifyIncident(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setAttribute("notCommit", true);
		modifyIncidentAutoCommit(request,response);
	}
	
	@RequestMapping(value="/modifyc")
	public void modifyIncidentAutoCommit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OpInfo oi = SessionUtil.getOpInfo();
		IncidentInfo ii = new IncidentInfo();
		//事件ID
		long incidentId = VarTypeConvertUtils.string2Long(request.getParameter("incidentId"));
		//产品线
		String scProductId = request.getParameter("scProductId");
		ii.setScProductId(VarTypeConvertUtils.string2Long(scProductId));
		//服务目录
		String scModuleId = request.getParameter("scModuleId");
		String moduleName = request.getParameter("moduleName");
		ii.setScModuleId(VarTypeConvertUtils.string2Long(scModuleId));
		ii.setModuleName(moduleName);
		//影响度
		String affectCodeUser = request.getParameter("affectCodeUser");
		String affectValUser = request.getParameter("affectValUser");
		ii.setAffectCodeUser(affectCodeUser);
		ii.setAffectValUser(affectValUser);
		//事件类别
		String classCodeUser = request.getParameter("classCodeUser");
		ii.setClassCodeUser(classCodeUser);
		//事件简述
		String brief = request.getParameter("brief");
		ii.setBrief(brief);
		//发生时间
		String happenTime = request.getParameter("happenTime");
		ii.setHappenTime(DateUtils.string2Date(happenTime, DateUtils.FORMATTYPE_yyyy_MM_dd));
		//详细描述
		String detail = request.getParameter("happenTime");
		ii.setDetail(detail);
		//抄送
		String ccList = request.getParameter("ccList");
		ii.setCcList(ccList);
		//附件
		String attachList = request.getParameter("attachList");
		List<IcAttach> attachs = JSONUtils.parseArray(attachList, IcAttach.class);
		ii.setAttachList(attachs);
		if(request.getAttribute("notCommit")!=null){
			incidentService.MBLModifyIncidentAndAttach(incidentId, ii, oi);
		}else{
			incidentService.MBLModifyAndCommitIncidentAndAttach(incidentId, ii, oi);
		}
	}
	
	@RequestMapping(value="/query")
	public void queryIncidentInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OpInfo oi = SessionUtil.getOpInfo();
		long incidentId = VarTypeConvertUtils.string2Long(request.getParameter("incidentId"));
		IcIncident ii = incidentService.MBLQueryIncident(incidentId, oi);
		String jsonData = JSONUtils.toJSONString(ii);
		response.getWriter().print(jsonData);
	}
	
	@RequestMapping(value="/commit")
	public void commitIncident(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OpInfo oi = SessionUtil.getOpInfo();
		long incidentId = VarTypeConvertUtils.string2Long(request.getParameter("incidentId"));
		incidentService.MBLCommitIncident(incidentId, oi);
	}
	
	@RequestMapping(value="/feedback")
	public void feedBackIncident(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OpInfo oi = SessionUtil.getOpInfo();
		long incidentId = VarTypeConvertUtils.string2Long(request.getParameter("incidentId"));
		IcIncident ii = new IcIncident();
		String feedbackVal = request.getParameter("feedbackVal");
		String feedbackCode = request.getParameter("feedbackCode");
		ii.setFeedbackVal(feedbackVal);
		ii.setFeedbackCode(feedbackCode);
		incidentService.MBLUserSetFeedbackVal(incidentId, ii, oi);
	}
}
