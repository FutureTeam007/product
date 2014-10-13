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
	 * 发送账号激活邮件
	 * 
	 * @param userId
	 *            用户ID
	 * @param email
	 *            用户邮箱
	 * @throws Exception
	 */
	public void sendEmailActiveAccount(long userId, String email)
			throws Exception;

	/**
	 * 账号激活
	 * 
	 * @param userId
	 *            用户ID
	 * @throws Exception
	 */
	public void activeAccount(long userId) throws Exception;

	/**
	 * 发送修改密码邮件
	 * 
	 * @param email
	 *            需发动修改密码链接的email
	 * @throws Exception
	 */
	public void sendEmailChgPasswd(String email) throws Exception;

	/**
	 * 修改密码
	 * 
	 * @param userId
	 *            用户ID
	 * @param oldPasswd
	 *            旧密码
	 * @param newPasswd
	 *            新密码
	 * @throws Exception
	 */
	public void userChgPasswd(long userId, String oldPasswd, String newPasswd)
			throws Exception;
}
