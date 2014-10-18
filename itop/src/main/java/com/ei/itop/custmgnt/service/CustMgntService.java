/**
 * 
 */
package com.ei.itop.custmgnt.service;

import java.util.List;

import com.ei.itop.common.dbentity.CcCust;
import com.ei.itop.common.dbentity.CcCustProdOp;

/**
 * @author Jack.Qi
 * 
 */
public interface CustMgntService {

	/**
	 * 查询顾问以某种岗位支持客户的产品的关系记录
	 * 
	 * @param orgId
	 * @param custId
	 * @param productId
	 * @param opId
	 * @return
	 * @throws Exception
	 */
	public CcCustProdOp getCustProdOpInfo(long orgId, long custId,
			long productId, long opId) throws Exception;

	/**
	 * 根据商户、客户、产品，查询多个负责顾问记录
	 * 
	 * @param orgId
	 * @param custId
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public List<CcCustProdOp> getCustProdOpList(long orgId, long custId,
			long productId) throws Exception;

	/**
	 * 根据客户ID查询客户信息
	 * 
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public CcCust getCustInfo(long custId) throws Exception;
}
