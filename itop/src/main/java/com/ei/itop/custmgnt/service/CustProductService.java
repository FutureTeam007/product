/**
 * 
 */
package com.ei.itop.custmgnt.service;

import java.util.List;

import com.ei.itop.common.dbentity.CcCustProd;
import com.ei.itop.common.dbentity.CcCustProdOp;
import com.ei.itop.common.dbentity.ScProduct;
import com.ei.itop.custmgnt.bean.CustProductInfo;
import com.ei.itop.custmgnt.bean.CustProductOpInfo;


/**
 * @author vintin
 */
public interface CustProductService {

	/**
	 * 获取为该客户服务的产品线
	 * @param orgId
	 * @param custId
	 * @return
	 */
	List<CustProductInfo> queryProductsServiceFor(long orgId,long custId) throws Exception;
	/**
	 * 获取客户产品线顾问
	 * @param orgId
	 * @param custId
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	List<CustProductOpInfo> queryCustProductOpInfoList(long orgId,long custId,long productId) throws Exception;
	/**
	 * 删除客户产品
	 * @param orgId
	 * @param custId
	 * @param productId
	 * @throws Exception
	 */
	void removeCustProduct(long orgId, long custId, long productId) throws Exception;
	/**
	 * 修改客户产品
	 * @param ccp
	 * @throws Exception
	 */
	void modifyCustProduct(CcCustProd ccp) throws Exception;
	/**
	 * 新增客户产品
	 * @param ccp
	 * @throws Exception
	 */
	void addCustProduct(CcCustProd ccp) throws Exception;
	/**
	 * 删除客户产品线顾问关系
	 * @param orgId
	 * @param custId
	 * @param productId
	 * @param opId
	 * @throws Exception
	 */
	void removeCustProductOp(long orgId, long custId, long productId, long opId) throws Exception;
	/**
	 * 新增客户产品顾问关系
	 * @param ccpo
	 * @throws Exception
	 */
	void addCustProductOp(CcCustProdOp ccpo) throws Exception;
	/**
	 * 修改客户产品顾问关系
	 * @param ccpo
	 * @throws Exception
	 */
	void modifyCustProductOp(CcCustProdOp ccpo) throws Exception;
}
