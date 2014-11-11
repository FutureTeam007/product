package com.ei.itop.custmgnt.controller;

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
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.dbentity.CcCustProd;
import com.ei.itop.common.dbentity.CcCustProdOp;
import com.ei.itop.common.dbentity.ScProduct;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.custmgnt.bean.CustProductInfo;
import com.ei.itop.custmgnt.bean.CustProductOpInfo;
import com.ei.itop.custmgnt.service.CustProductService;
import com.ei.itop.scmgnt.service.ProductService;

@Controller
@RequestMapping("/custmgnt/product")
public class CustProductController {

	@Autowired
	private CustProductService custProductService;
	@Autowired
	private ProductService productService;

	@RequestMapping("/list")
	public void queryCustProdList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long orgId = SessionUtil.getOpInfo().getOrgId();
		long custId = VarTypeConvertUtils.string2Long(request
				.getParameter("custId"));
		List<CustProductInfo> data = custProductService.queryProductsServiceFor(orgId, custId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", data.size());
		result.put("rows", data);
		String jsonData = JSONUtils.toJSONString(result);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	
	@RequestMapping("/add")
	public void addProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long ccCustId = VarTypeConvertUtils.string2Long(request.getParameter("ccCustId"));
		long scProductId = VarTypeConvertUtils.string2Long(request.getParameter("scProductId"));
		String prodName = request.getParameter("prodName");
		String serviceStartDt = request.getParameter("serviceStartDt");
		String serviceEndDt = request.getParameter("serviceEndDt");
		CcCustProd ccp = new CcCustProd();
		ccp.setCcCustId(ccCustId);
		ccp.setScOrgId(SessionUtil.getOpInfo().getOrgId());
		ccp.setScOrgName(SessionUtil.getOpInfo().getOrgName());
		ccp.setProdName(prodName);
		ccp.setScProductId(scProductId);
		ccp.setServiceStartDt(DateUtils.string2Date(serviceStartDt, DateUtils.FORMATTYPE_yyyy_MM_dd));
		ccp.setServiceEndDt(DateUtils.string2Date(serviceEndDt, DateUtils.FORMATTYPE_yyyy_MM_dd));
		custProductService.addCustProduct(ccp);
	}
	
	@RequestMapping("/modify")
	public void modifyProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long ccCustId = VarTypeConvertUtils.string2Long(request.getParameter("ccCustId"));
		long scProductId = VarTypeConvertUtils.string2Long(request.getParameter("scProductId"));
		String serviceStartDt = request.getParameter("serviceStartDt");
		String serviceEndDt = request.getParameter("serviceEndDt");
		CcCustProd ccp = new CcCustProd();
		ccp.setScOrgId(SessionUtil.getOpInfo().getOrgId());
		ccp.setCcCustId(ccCustId);
		ccp.setScProductId(scProductId);
		ccp.setServiceStartDt(DateUtils.string2Date(serviceStartDt, DateUtils.FORMATTYPE_yyyy_MM_dd));
		ccp.setServiceEndDt(DateUtils.string2Date(serviceEndDt, DateUtils.FORMATTYPE_yyyy_MM_dd));
		custProductService.modifyCustProduct(ccp);
	}
	
	@RequestMapping("/list/select")
	public void queryProductList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		List<ScProduct> products = productService.queryProductList(oi.getOrgId(),
						null);
		String jsonData = null;
		if (products != null) {
			jsonData = JSONUtils.toJSONString(products);
		} else {
			jsonData = "[]";
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	
	@RequestMapping("/oplist")
	public void queryCustProdOpList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long orgId = SessionUtil.getOpInfo().getOrgId();
		long custId = VarTypeConvertUtils.string2Long(request
				.getParameter("custId"));
		long productId = VarTypeConvertUtils.string2Long(request
				.getParameter("productId"));
		List<CustProductOpInfo> data = custProductService.queryCustProductOpInfoList(orgId, custId,productId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", data.size());
		result.put("rows", data);
		String jsonData = JSONUtils.toJSONString(result);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	
	@RequestMapping("/remove")
	public void removeCustProd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long orgId = SessionUtil.getOpInfo().getOrgId();
		long custId = VarTypeConvertUtils.string2Long(request
				.getParameter("custId"));
		long productId = VarTypeConvertUtils.string2Long(request
				.getParameter("productId"));
		custProductService.removeCustProduct(orgId, custId,productId);
	}
	
	@RequestMapping("/opRemove")
	public void removeCustProdOp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long orgId = SessionUtil.getOpInfo().getOrgId();
		long custId = VarTypeConvertUtils.string2Long(request
				.getParameter("custId"));
		long productId = VarTypeConvertUtils.string2Long(request
				.getParameter("productId"));
		long opId = VarTypeConvertUtils.string2Long(request
				.getParameter("opId"));
		custProductService.removeCustProductOp(orgId, custId,productId,opId);
	}
	
	@RequestMapping("/opAdd")
	public void addOp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long ccCustId = VarTypeConvertUtils.string2Long(request.getParameter("ccCustId"));
		long scProductId = VarTypeConvertUtils.string2Long(request.getParameter("scProductId"));
		long scOpId = VarTypeConvertUtils.string2Long(request.getParameter("scOpId"));
		long scJobId = VarTypeConvertUtils.string2Long(request.getParameter("scJobId"));
		CcCustProdOp ccpo = new CcCustProdOp();
		ccpo.setCcCustId(ccCustId);
		ccpo.setScOrgId(SessionUtil.getOpInfo().getOrgId());
		ccpo.setScOrgName(SessionUtil.getOpInfo().getOrgName());
		ccpo.setScProductId(scProductId);
		ccpo.setScOpId(scOpId);
		ccpo.setScJobId(scJobId);
		custProductService.addCustProductOp(ccpo);
	}
	
	@RequestMapping("/opModify")
	public void modifyOp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long ccCustId = VarTypeConvertUtils.string2Long(request.getParameter("ccCustId"));
		long scProductId = VarTypeConvertUtils.string2Long(request.getParameter("scProductId"));
		long scOpId = VarTypeConvertUtils.string2Long(request.getParameter("scOpId"));
		long scJobId = VarTypeConvertUtils.string2Long(request.getParameter("scJobId"));
		CcCustProdOp ccpo = new CcCustProdOp();
		ccpo.setCcCustId(ccCustId);
		ccpo.setScOrgId(SessionUtil.getOpInfo().getOrgId());
		ccpo.setScProductId(scProductId);
		ccpo.setScOpId(scOpId);
		ccpo.setScJobId(scJobId);
		custProductService.modifyCustProductOp(ccpo);
	}
	
}
