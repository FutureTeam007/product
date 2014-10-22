/**
 * 
 */
package com.ei.itop.custmgnt.service;

import java.util.List;

import com.ei.itop.common.dbentity.CcCust;
import com.ei.itop.common.dbentity.CcCustProdOp;
import com.ei.itop.custmgnt.bean.InChargeAdviser;

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

	/**
	 * 根据客户、产品线、顾问姓名（模糊匹配）查询负责的顾问列表
	 * 
	 * @param custId
	 *            客户ID
	 * @param productId
	 *            产品线ID
	 * @param adviserName
	 *            顾问姓名（模糊匹配）
	 * @param currentAdviserId
	 *            要排除的当前顾问Id
	 * @param startIndex
	 *            分页起始记录，从1开始，-1为不分页
	 * @param pageSize
	 *            每页记录数
	 * @throws Exception
	 */
	public List<InChargeAdviser> queryInChargeAdviser(Long custId,
			Long productId, String adviserName,Long currentAdviserId, long startIndex, int pageSize)
			throws Exception;

	/**
	 * 查询记录数，根据客户、产品线、顾问姓名（模糊匹配）查询负责的顾问列表
	 * 
	 * @param custId
	 * @param productId
	 * @param adviserName
	 * @param currentAdviserId 要排除的当前顾问
	 * @return
	 * @throws Exception
	 */
	public long queryInChargeAdviserCount(Long custId, Long productId,
			String adviserName,Long currentAdviserId) throws Exception;
}
