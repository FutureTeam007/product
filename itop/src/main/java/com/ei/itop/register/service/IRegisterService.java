/**
 * 
 */
package com.ei.itop.register.service;

import java.util.List;

import com.ei.itop.common.dbentity.CcCust;
import com.ei.itop.register.bean.RegisterInfo;

/**
 * @author Jack.Qi
 * 
 */
public interface IRegisterService {

	/**
	 * 新注册一个用户
	 * 
	 * @param registerInfo
	 *            用户注册信息
	 * @return 新增的用户ID
	 * @throws Exception
	 */
	public long userRegister(RegisterInfo registerInfo) throws Exception;

	/**
	 * 根据域名精确查询客户（不区分大小写），返回公司及子公司列表
	 * 
	 * @param domainName
	 *            域名
	 * @return 客户列表
	 * @throws Exception
	 */
	public List<CcCust> queryCustListByDomainName(String domainName)
			throws Exception;

	/**
	 * 账号激活
	 * 
	 * @param loginCode
	 *            登录工号
	 * @return 是否激活成功
	 * @throws Exception
	 */
	public boolean activeAccount(String loginCode) throws Exception;
}
