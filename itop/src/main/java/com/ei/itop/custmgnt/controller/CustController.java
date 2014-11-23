package com.ei.itop.custmgnt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ailk.dazzle.util.json.JSONUtils;
import com.ailk.dazzle.util.tree.TreeUtil;
import com.ailk.dazzle.util.type.StringUtils;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ailk.dazzle.util.web.ActionUtils;
import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.dbentity.CcCust;
import com.ei.itop.common.dbentity.CcSlo;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.custmgnt.bean.InChargeAdviser;
import com.ei.itop.custmgnt.service.CustMgntService;
import com.ei.itop.register.bean.CustTreeNode;

@Controller
@RequestMapping("/custmgnt")
public class CustController {

	@Autowired
	private CustMgntService custMgntService;

	@RequestMapping("/op/list")
	public void queryInChargeAdviser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		long custId = VarTypeConvertUtils.string2Long(request
				.getParameter("custId"));
		long productId = VarTypeConvertUtils.string2Long(request
				.getParameter("productId"));
		String adviserName = ActionUtils.transParamDecode(
				request.getParameter("consultantName"), "UTF-8");
		int page = VarTypeConvertUtils.string2Integer(
				request.getParameter("page"), 0);
		// 获取分页大小
		int pageSize = VarTypeConvertUtils.string2Integer(
				request.getParameter("rows"), 0);
		// 获取分页起始位置
		long startIndex = (page - 1) * pageSize + 1;
		// 查询数据
		List<InChargeAdviser> datas = custMgntService.queryInChargeAdviser(
				custId, productId, adviserName, oi.getOpId(), startIndex,
				pageSize);
		// 查询数据条数
		long count = custMgntService.queryInChargeAdviserCount(custId,
				productId, adviserName, oi.getOpId());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", datas);
		String jsonData = JSONUtils.toJSONString(result);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}

	@RequestMapping("/custinfo/get")
	public void queryCustInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long custId = VarTypeConvertUtils.string2Long(request
				.getParameter("custId"));
		// 查询客户信息
		CcCust cust = custMgntService.getCustInfo(custId);
		String jsonData = JSONUtils.toJSONString(cust);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}

	@RequestMapping("/custinfo/modify")
	public ModelAndView modifyCustInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		//设置前台参数
		CcCust cust = new CcCust();
		String custCode = request.getParameter("custCode").trim();
		cust.setCustCode(custCode);
		String custName = request.getParameter("custName").trim();
		cust.setCustName(custName);
		String custShortName = request.getParameter("custShortName").trim();
		cust.setShortName(custShortName);
		String custEnName = request.getParameter("custEnName").trim();
		cust.setLastName(custEnName);
		String domainName = request.getParameter("domainName").trim();
		cust.setDomainName(domainName);
		String supCustId = request.getParameter("supCustId");
		if(!StringUtils.isEmpty(supCustId)){
			if("-1".equals(supCustId)){
				cust.setSupCustId(null);
			}else{
				cust.setSupCustId(VarTypeConvertUtils.string2Long(supCustId));
			}
		}
		short status = VarTypeConvertUtils.string2Short(request.getParameter("status"));
		cust.setState(status);
		String notes = request.getParameter("notes");
		String editType = request.getParameter("editType");
		if(!StringUtils.isEmpty(notes)){
			cust.setNotes(notes);
		}
		OpInfo opInfo = SessionUtil.getOpInfo();
		//新增
		if("1".equals(editType)){
			long custId = custMgntService.MBLAddCustInfo(cust,opInfo);
			mav.addObject("newCustId", custId);
		}
		//修改
		else if("2".equals(editType)){
			String custId = request.getParameter("custId");
			if(!StringUtils.isEmpty(custId)){
				cust.setCcCustId(VarTypeConvertUtils.string2Long(custId));
			}
			custMgntService.MBLModifyCustInfo(cust,opInfo);
		}
		mav.addObject("msg", SessionUtil.getRequestContext().getMessage("i18n.custmgnt.custinfo.SaveSuccess"));
		mav.setViewName("/page/custmgnt/custInfoMaintence");
		return mav;
	}
	
	@RequestMapping("/custinfo/remove")
	public void removeCustInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//设置前台参数
		OpInfo opInfo = SessionUtil.getOpInfo();
		String custId = request.getParameter("custId");
		custMgntService.MBLRemoveCustInfo(VarTypeConvertUtils.string2Long(custId),opInfo);
	}
	
	@RequestMapping("/custinfo/tree")
	public void queryCustInfoTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 获取域名
		String domainName = request.getParameter("domainName");
		// 组织ID
		Long orgId = SessionUtil.getOpInfo() == null ? null : SessionUtil
				.getOpInfo().getOrgId();
		// 根据域名查询客户列表
		List<CcCust> custList = custMgntService.queryAllCustListByDomainName(
				orgId, domainName != null ? domainName.toLowerCase() : null);
		List<CustTreeNode> nodes = new ArrayList<CustTreeNode>();
		for (CcCust cust : custList) {
			CustTreeNode node = new CustTreeNode();
			node.setData(cust);
			nodes.add(node);
		}
		nodes = TreeUtil.buildAndSort(nodes);
		String jsonData = JSONUtils.toJSONString(nodes);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	
	@RequestMapping("/slo/list")
	public void querySloConfigList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long orgId = SessionUtil.getOpInfo().getOrgId();
		long custId = VarTypeConvertUtils.string2Long(request.getParameter("custId"));
		List<CcSlo> slos = custMgntService.querySloConfigList(orgId, custId);
		String jsonData = JSONUtils.toJSONString(slos);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	
	@RequestMapping("/slo/clear")
	public void clearSloRule(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long orgId = SessionUtil.getOpInfo().getOrgId();
		long custId = VarTypeConvertUtils.string2Long(request.getParameter("custId"));
		long productId = VarTypeConvertUtils.string2Long(request.getParameter("productId"));
		long priority = VarTypeConvertUtils.string2Long(request.getParameter("priority"));
		long complex = VarTypeConvertUtils.string2Long(request.getParameter("complex"));
		custMgntService.MBLRemoveSloRule(orgId, custId, productId, priority, complex);
	}
	
	@RequestMapping("/slo/modify")
	public void modifySloRule(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long orgId = SessionUtil.getOpInfo().getOrgId();
		String orgName =  SessionUtil.getOpInfo().getOrgName();
		long ccCustId= VarTypeConvertUtils.string2Long(request.getParameter("ccCustId"));
		long scProductId= VarTypeConvertUtils.string2Long(request.getParameter("scProductId"));
		String custName= request.getParameter("custName");
		String prodName= request.getParameter("prodName");
		String complexCode= request.getParameter("complexCode");
		String complexVal= request.getParameter("complexVal");
		String priorityCode= request.getParameter("priorityCode");
		String priorityVal= request.getParameter("priorityVal");
		long responseTime= VarTypeConvertUtils.string2Long(request.getParameter("responseTime"));
		long dealTime= VarTypeConvertUtils.string2Long(request.getParameter("dealTime"));
		CcSlo slo = new CcSlo();
		slo.setScOrgId(orgId);
		slo.setScOrgName(orgName);
		slo.setCcCustId(ccCustId);
		slo.setCustName(custName);
		slo.setScProductId(scProductId);
		slo.setProdName(prodName);
		slo.setComplexCode(complexCode);
		slo.setComplexVal(complexVal);
		slo.setPriorityCode(priorityCode);
		slo.setPriorityVal(priorityVal);
		slo.setResponseTime(responseTime);
		slo.setDealTime(dealTime);
		custMgntService.MBLModifySloRule(slo);
	}
}
