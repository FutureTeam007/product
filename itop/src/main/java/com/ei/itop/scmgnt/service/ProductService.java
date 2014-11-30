package com.ei.itop.scmgnt.service;

import java.util.List;

import com.ei.itop.common.dbentity.ScModule;
import com.ei.itop.common.dbentity.ScModuleI18n;
import com.ei.itop.common.dbentity.ScProduct;
import com.ei.itop.common.dbentity.ScProductI18n;
import com.ei.itop.scmgnt.bean.ModuleInfo;
import com.ei.itop.scmgnt.bean.ProductInfo;

public interface ProductService {
	/**
	 * 查询商户下的所有产品
	 * @param orgId
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	List<ScProduct> queryProductList(Long orgId,Long custId) throws Exception;
	/**
	 * 查询产品信息（带中英文名称）
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	List<ProductInfo> queryProductInfoList(long orgId) throws Exception;
	/**
	 * 删除产品
	 * @param productId
	 * @throws Exception
	 */
	void removeProduct(long productId) throws Exception;
	
	/**
	 * 查询某产品下的服务目录
	 * @param orgId
	 * @param prodcutId
	 * @return
	 * @throws Exception
	 */
	List<ScModule> queryModuleList(long orgId,long prodcutId) throws Exception;
	/**
	 * 查询某产品下的服务目录（含失效的）
	 * @param orgId
	 * @param prodcutId
	 * @return
	 * @throws Exception
	 */
	List<ScModule> queryAllModuleList(long prodcutId) throws Exception;
	
	/**
	 * 查询单条产品信息
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	ScProduct queryProductInfo(long productId) throws Exception;
	/**
	 * 新增一个产品线
	 * @param product
	 * @param i18ns
	 */
	void addProduct(ScProduct product, List<ScProductI18n> i18ns) throws Exception;
	/**
	 * 修改一个产品线
	 * @param product
	 * @param i18ns
	 * @throws Exception
	 */
	void modifyProduct(ScProduct product, List<ScProductI18n> i18ns) throws Exception;
	/**
	 * 查询一个服务目录的信息
	 * @param moduleId
	 * @return
	 * @throws Exception
	 */
	ModuleInfo queryModuleInfo(long moduleId) throws Exception;
	/**
	 * 删除一个服务目录
	 * @param moduleId
	 * @throws Exception
	 */
	void removeModule(long moduleId) throws Exception;
	/**
	 * 增加一个服务目录
	 * @param module
	 * @param i18ns
	 * @throws Exception
	 */
	void addModule(ScModule module, List<ScModuleI18n> i18ns) throws Exception;
	/**
	 * 修改一个服务目录
	 * @param module
	 * @param i18ns
	 * @throws Exception
	 */
	void modifyModule(ScModule module, List<ScModuleI18n> i18ns) throws Exception;
}
