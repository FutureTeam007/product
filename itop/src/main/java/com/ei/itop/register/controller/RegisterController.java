package com.ei.itop.register.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.dazzle.util.json.JSONUtils;
import com.ailk.dazzle.util.tree.TreeUtil;
import com.ei.itop.common.dbentity.CcCust;
import com.ei.itop.custmgnt.service.CustMgntService;
import com.ei.itop.register.bean.CustTreeNode;
import com.ei.itop.register.service.RegisterService;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
	private RegisterService registerService;
	@Autowired
	private CustMgntService custMgntService;
	
	/**
	 * 注册时,根据域名查询公司
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/custlist/get")
	public void queryCustListByDomainName(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//获取域名
		String domainName = request.getParameter("domainName");
		//根据域名查询客户列表
		List<CcCust> custList = custMgntService.queryCustListByDomainName(domainName.toLowerCase());
		List<CustTreeNode> nodes = new ArrayList<CustTreeNode>();
		for(CcCust cust:custList){
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
	
}
