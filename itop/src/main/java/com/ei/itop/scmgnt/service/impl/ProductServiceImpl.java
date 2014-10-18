package com.ei.itop.scmgnt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.dbentity.ScProduct;
import com.ei.itop.scmgnt.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService{

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScProduct> productDAO;
	
	public List<ScProduct> queryProductList(long orgId) throws Exception {
		Map params = new HashMap();
		params.put("orgId", orgId);
		return productDAO.findByParams("SC_PRODUCT.selectByOrgId", params);
	}
	
}