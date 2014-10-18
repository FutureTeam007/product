package com.ei.itop.scmgnt.service;

import java.util.List;

import com.ei.itop.common.dbentity.ScProduct;

public interface ProductService {
	/**
	 * 查询商户下的所有产品
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<ScProduct> queryProductList(long orgId) throws Exception;
	
}
