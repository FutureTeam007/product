package com.ei.itop.scmgnt.service;

import java.util.List;

import com.ei.itop.common.dbentity.ScModule;
import com.ei.itop.common.dbentity.ScProduct;

public interface ProductService {
	/**
	 * 查询商户下的所有产品
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<ScProduct> queryProductList(long orgId) throws Exception;
	/**
	 * 查询某产品下的服务目录
	 * @param orgId
	 * @param prodcutId
	 * @return
	 * @throws Exception
	 */
	List<ScModule> queryModuleList(long orgId,long prodcutId) throws Exception;
	
}
