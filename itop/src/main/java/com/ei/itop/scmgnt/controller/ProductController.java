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
import com.ailk.dazzle.util.type.StringUtils;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.constants.SysConstants;
import com.ei.itop.common.dbentity.ScModule;
import com.ei.itop.common.dbentity.ScModuleI18n;
import com.ei.itop.common.dbentity.ScProduct;
import com.ei.itop.common.dbentity.ScProductI18n;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.scmgnt.bean.ModuleInfo;
import com.ei.itop.scmgnt.bean.ModuleTreeMNode;
import com.ei.itop.scmgnt.bean.ModuleTreeNode;
import com.ei.itop.scmgnt.bean.ProductInfo;
import com.ei.itop.scmgnt.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	/**
	 * 查询产品列表（下拉列表）
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/productList")
	public void queryProductList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		List<ScProduct> products = null;
		if (SysConstants.OpAttribute.OP_ROLE_OP.equals(oi.getOpType())) {
			products = productService.queryProductList(oi.getOrgId(), null);
		} else {
			String custId = request.getParameter("custId");
			//如果传入custId为空，则取用户当前的custId
			if(StringUtils.isEmpty(custId)){
				products = productService.queryProductList(oi.getOrgId(),
						VarTypeConvertUtils.string2Long(oi.getCustId()));
			}else{
				products = productService.queryProductList(oi.getOrgId(),
						VarTypeConvertUtils.string2Long(custId));
			}
		}
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
	
	@RequestMapping("/infolist")
	public void queryProductInfoList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		List<ProductInfo> products = productService.queryProductInfoList(oi.getOrgId());
		String jsonData = JSONUtils.toJSONString(products);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	
	@RequestMapping("/remove")
	public void removeProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long productId = VarTypeConvertUtils.string2Long(request.getParameter("productId"));
		productService.removeProduct(productId);
	}
	
	@RequestMapping("/add")
	public void addProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String prodCode = request.getParameter("prodCode");
		String productNameZh = request.getParameter("productNameZh");
		String productNameEn = request.getParameter("productNameEn");
		//String productDescZh = request.getParameter("productDescZh");
		//String productDescEn = request.getParameter("productDescEn");
		Long state = VarTypeConvertUtils.string2Long(request.getParameter("state"));
		OpInfo oi = SessionUtil.getOpInfo();
		ScProduct product = new ScProduct();
		product.setProdCode(prodCode);
		//product.setProdDesc(productDescZh);
		product.setProdName(productNameZh);
		product.setScOrgId(oi.getOrgId());
		product.setState(state.shortValue());
		ScProductI18n i18nZh = new ScProductI18n();
		i18nZh.setProdName(productNameZh);
		//i18nZh.setProdDesc(productDescZh);
		i18nZh.setLangFlag("zh_CN");
		ScProductI18n i18nEn = new ScProductI18n();
		i18nEn.setProdName(productNameEn);
		//i18nEn.setProdDesc(productDescEn);
		i18nEn.setLangFlag("en_US");
		List<ScProductI18n> i18ns = new ArrayList<ScProductI18n>();
		i18ns.add(i18nZh);
		i18ns.add(i18nEn);
		productService.addProduct(product,i18ns);
	}
	
	@RequestMapping("/modify")
	public void modifyProduct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long scProductId = VarTypeConvertUtils.string2Long(request.getParameter("scProductId"));
		String prodCode = request.getParameter("prodCode");
		String productNameZh = request.getParameter("productNameZh");
		String productNameEn = request.getParameter("productNameEn");
		//String productDescZh = request.getParameter("productDescZh");
		//String productDescEn = request.getParameter("productDescEn");
		Long state = VarTypeConvertUtils.string2Long(request.getParameter("state"));
		OpInfo oi = SessionUtil.getOpInfo();
		ScProduct product = new ScProduct();
		product.setScProductId(scProductId);
		product.setProdCode(prodCode);
		//product.setProdDesc(productDescZh);
		product.setProdName(productNameZh);
		product.setScOrgId(oi.getOrgId());
		product.setState(state.shortValue());
		ScProductI18n i18nZh = new ScProductI18n();
		i18nZh.setScProductId(scProductId);
		i18nZh.setProdName(productNameZh);
		//i18nZh.setProdDesc(productDescZh);
		i18nZh.setLangFlag("zh_CN");
		ScProductI18n i18nEn = new ScProductI18n();
		i18nEn.setScProductId(scProductId);
		i18nEn.setProdName(productNameEn);
		//i18nEn.setProdDesc(productDescEn);
		i18nEn.setLangFlag("en_US");
		List<ScProductI18n> i18ns = new ArrayList<ScProductI18n>();
		i18ns.add(i18nZh);
		i18ns.add(i18nEn);
		productService.modifyProduct(product,i18ns);
	}
	
	/**
	 * 查询服务目录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/moduleTree")
	public void queryModuleTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long productId = VarTypeConvertUtils.string2Long(request
				.getParameter("productId"));
		OpInfo oi = SessionUtil.getOpInfo();
		List<ScModule> modules = productService.queryModuleList(oi.getOrgId(),
				productId);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		if (modules != null && modules.size() != 0) {
			List<ModuleTreeNode> nodes = new ArrayList<ModuleTreeNode>();
			for (ScModule sm : modules) {
				ModuleTreeNode node = new ModuleTreeNode();
				node.setData(sm);
				nodes.add(node);
			}
			List<ModuleTreeNode> treeNodes = TreeUtil.buildAndSort(nodes);
			String treeJson = JSONUtils.toJSONString(treeNodes);
			response.getWriter().print(treeJson);
		} else {
			response.getWriter().print("[]");
		}
	}
	/**
	 * 查询服务目录树,带失效节点
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/moduleTree/all")
	public void queryAllModuleTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long productId = VarTypeConvertUtils.string2Long(request
				.getParameter("productId"));
		List<ScModule> modules = productService.queryAllModuleList(productId);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		if (modules != null && modules.size() != 0) {
			List<ModuleTreeMNode> nodes = new ArrayList<ModuleTreeMNode>();
			for (ScModule sm : modules) {
				ModuleTreeMNode node = new ModuleTreeMNode();
				node.setData(sm);
				nodes.add(node);
			}
			List<ModuleTreeMNode> treeNodes = TreeUtil.buildAndSort(nodes);
			String treeJson = JSONUtils.toJSONString(treeNodes);
			response.getWriter().print(treeJson);
		} else {
			response.getWriter().print("[]");
		}
	}
	
	@RequestMapping("/module/get")
	public void queryModuleInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long moduleId = VarTypeConvertUtils.string2Long(request.getParameter("moduleId"));
		ModuleInfo module = productService.queryModuleInfo(moduleId);
		String jsonData = JSONUtils.toJSONString(module);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	
	@RequestMapping("/module/remove")
	public void removeModule(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long moduleId = VarTypeConvertUtils.string2Long(request.getParameter("moduleId"));
		productService.removeModule(moduleId);
	}
	
	@RequestMapping("/module/save")
	public void saveModule(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String editType = request.getParameter("editType");
		String moduleCode = request.getParameter("moduleCode");
		String moduleNameZh = request.getParameter("moduleNameZh");
		String moduleNameEn = request.getParameter("moduleNameEn");
		String moduleDescZh = request.getParameter("moduleDescZh");
		String moduleDescEn = request.getParameter("moduleDescEn");
		Long status = VarTypeConvertUtils.string2Long(request.getParameter("status"));
		Long scProductId = VarTypeConvertUtils.string2Long(request.getParameter("productId"));
		Long supModuleId = null;
		if(!StringUtils.isEmpty((request.getParameter("supModuleId")))){
			supModuleId = VarTypeConvertUtils.string2Long(request.getParameter("supModuleId"));
		}
		ScModule module = new ScModule();
		module.setModuleCode(moduleCode);
		module.setModuleName(moduleNameZh);
		module.setScOrgId(SessionUtil.getOpInfo().getOrgId());
		module.setScProductId(scProductId);
		module.setState(status.shortValue());
		module.setSupModuleId(supModuleId);
		module.setModuleDesc(moduleDescZh);
		ScModuleI18n i18nZh = new ScModuleI18n();
		i18nZh.setLangFlag("zh_CN");
		i18nZh.setModuleDesc(moduleDescZh);
		i18nZh.setModuleName(moduleNameZh);
		ScModuleI18n i18nEn = new ScModuleI18n();
		i18nEn.setLangFlag("en_US");
		i18nEn.setModuleDesc(moduleDescEn);
		i18nEn.setModuleName(moduleNameEn);
		List<ScModuleI18n> i18ns = new ArrayList<ScModuleI18n>();
		i18ns.add(i18nZh);
		i18ns.add(i18nEn);
		//新增
		if("1".equals(editType)){
			productService.addModule(module, i18ns);
		}
		//修改
		else if("2".equals(editType)){
			Long scModuleId = VarTypeConvertUtils.string2Long(request.getParameter("moduleId"));
			module.setScModuleId(scModuleId);
			i18nZh.setScModuleId(scModuleId);
			i18nEn.setScModuleId(scModuleId);
			productService.modifyModule(module, i18ns);
		}
	}
}
