package com.ei.itop.scmgnt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.dbentity.ScModule;
import com.ei.itop.common.dbentity.ScProduct;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.scmgnt.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService{

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScProduct> productDAO;
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScModule> moduleDAO;
	
	public List<ScProduct> queryProductList(long orgId) throws Exception {
		Map params = new HashMap();
		params.put("orgId", orgId);
		params.put("locale", SessionUtil.getLocale().toString());
		return productDAO.findByParams("SC_PRODUCT.selectByOrgId", params);
	}

	public List<ScModule> queryModuleList(long orgId, long prodcutId)
			throws Exception {
		Map params = new HashMap();
		params.put("orgId", orgId);
		params.put("productId", prodcutId);
		params.put("locale", SessionUtil.getLocale().toString());
		return moduleDAO.findByParams("SC_MODULE.selectByOrgIdAndProductId", params);
	}
	
}
