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
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ailk.dazzle.util.web.ActionUtils;
import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.dbentity.CcUser;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.custmgnt.bean.InChargeAdviser;
import com.ei.itop.custmgnt.service.CustMgntService;
import com.ei.itop.custmgnt.service.UserService;

@Controller
@RequestMapping("/custmgnt/op")
public class CustController {

	@Autowired
	private CustMgntService custMgntService;
	
	@RequestMapping("/list")
	public void queryInChargeAdviser(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OpInfo oi = SessionUtil.getOpInfo();
		long custId = VarTypeConvertUtils.string2Long(request.getParameter("custId"));
		long productId = VarTypeConvertUtils.string2Long(request.getParameter("productId"));
		String adviserName = ActionUtils.transParamDecode(request.getParameter("consultantName"),"UTF-8");
		int page = VarTypeConvertUtils.string2Integer(request.getParameter("page"),0);
		//获取分页大小
		int pageSize = VarTypeConvertUtils.string2Integer(request.getParameter("rows"),0);
		//获取分页起始位置
		long startIndex = (page-1)*pageSize+1;
		//查询数据
		List<InChargeAdviser> datas= custMgntService.queryInChargeAdviser(custId, productId, adviserName,oi.getOpId(), startIndex, pageSize);
		//查询数据条数
		long count = custMgntService.queryInChargeAdviserCount(custId, productId, adviserName,oi.getOpId());
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("total", count);
		result.put("rows", datas);
		String jsonData = JSONUtils.toJSONString(result);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	
	
}
