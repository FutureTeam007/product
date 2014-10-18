package com.ei.itop.scmgnt.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.dazzle.util.json.JSONUtils;
import com.ailk.dazzle.util.tree.TreeUtil;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.dbentity.ScModule;
import com.ei.itop.common.dbentity.ScProduct;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.scmgnt.bean.ModuleTreeNode;
import com.ei.itop.scmgnt.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	/**
	 * 查询产品列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/productList")
	public void queryProductList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		OpInfo oi = SessionUtil.getOpInfo();
		List<ScProduct> products = productService.queryProductList(oi.getOrgId());
		String jsonData = null;
		if(products!=null){
			jsonData = JSONUtils.toJSONString(products);
		}else{
			jsonData = "[]";
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	
	/**
	 * 查询服务目录
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/moduleTree")
	public void queryModuleTree(HttpServletRequest request,HttpServletResponse response) throws Exception{
		long productId = VarTypeConvertUtils.string2Long(request.getParameter("productId"));
		OpInfo oi = SessionUtil.getOpInfo();
		List<ScModule> modules = productService.queryModuleList(oi.getOrgId(),productId);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		if(modules!=null&&modules.size()!=0){
			List<ModuleTreeNode> nodes = new ArrayList<ModuleTreeNode>();
			for(ScModule sm:modules){
				ModuleTreeNode node = new ModuleTreeNode();
				node.setData(sm);
				nodes.add(node);
			}
			List<ModuleTreeNode> treeNodes = TreeUtil.buildAndSort(nodes);
			String treeJson = JSONUtils.toJSONString(treeNodes);
			response.getWriter().print(treeJson);
		}else{
			response.getWriter().print("[]");
		}
	}
}
