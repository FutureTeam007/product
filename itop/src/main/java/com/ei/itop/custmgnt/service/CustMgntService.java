/**
 * 
 */
package com.ei.itop.custmgnt.service;

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
}
