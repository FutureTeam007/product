/**
 * 
 */
package com.ei.itop.custmgnt.service;

import java.util.List;

import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.dbentity.CcCust;
import com.ei.itop.common.dbentity.CcCustProdOp;
import com.ei.itop.common.dbentity.CcSlo;
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
	/**
	 * 根据域名查询客户公司
	 * @param domainName
	 * @return
	 * @throws Exception
	 */
	public List<CcCust> queryCustListByDomainName(Long orgId,String domainName) throws Exception;
	/**
	 * 根据域名查询客户公司(后台配置管理使用，忽略State字段是否正常，由前台显示状态值)
	 * @param domainName
	 * @return
	 * @throws Exception
	 */
	public List<CcCust> queryAllCustListByDomainName(Long orgId,String domainName) throws Exception;
	
	/**
	 * 查询Slo规则
	 * @return
	 * @throws Exception
	 */
	public List<CcSlo> querySloRules(long orgId,long custId,long productId,String priorityCode,String complexCode) throws Exception;
	
	/**
	 * 根据custId查询顶层的Cust信息
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public CcCust getTopCustInfo(long custId,String domainName) throws Exception;
	
	/**
	 * 根据custId查询所有子客户（递归）
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public List<CcCust> getSubCusts(long custId) throws Exception;
	/**
	 * 修改客户信息
	 * @param cust
	 * @throws Exception
	 */
	public void MBLModifyCustInfo(CcCust cust,OpInfo opInfo) throws Exception;
	/**
	 * 保存客户信息
	 * @param cust
	 * @throws Exception
	 */
	public long MBLAddCustInfo(CcCust cust,OpInfo opInfo) throws Exception;
	/**
	 * 删除客户信息
	 * @param custId
	 * @param opInfo
	 * @throws Exception
	 */
	public void MBLRemoveCustInfo(long custId, OpInfo opInfo) throws Exception;
	/**
	 * 查询SLO配置列表
	 * @param orgId
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public List<CcSlo> querySloConfigList(long orgId,long custId) throws Exception;
	/**
	 * 删除SLO规则
	 * @param orgId
	 * @param custId
	 * @param productId
	 * @param priorityVal
	 * @param complexVal
	 * @throws Exception
	 */
	public void MBLRemoveSloRule(long orgId,long custId,long productId,long priorityCode,long complexCode) throws Exception;
	
	/**
	 * 修改Slo规则
	 * @param slo
	 * @throws Exception
	 */
	public void MBLModifySloRule(CcSlo slo) throws Exception;
}
