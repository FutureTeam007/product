/**
 * 
 */
package com.ei.itop.register.service;

import java.util.List;

import com.ei.itop.common.dbentity.CcCust;
import com.ei.itop.common.dbentity.CcUser;
import com.ei.itop.register.bean.RegisterInfo;

/**
 * @author Jack.Qi
 * 
 */
public interface RegisterService {

	/**
	 * 新注册一个用户，然后发送账号激活链接邮件
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
	 * 账号激活，用户点击账号激活链接后的处理逻辑
	 * 
	 * @param userId
	 *            用户ID
	 * @throws Exception
	 */
	public void activeAccount(long userId) throws Exception;

	/**
	 * 校验注册的登录工号是否已存在
	 * 
	 * @param loginCode
	 * @return true-已存在 false-不存在
	 * @throws Exception
	 */
	public boolean checkLoginCodeIsExist(String loginCode) throws Exception;
	/**
	 * 通过loginCode取得Ccuser
	 * 
	 * @param loginCode
	 * @return
	 * @throws Exception
	 */
	public CcUser getCcUserByLoginCode(String loginCode) throws Exception;

	/**
	 * 用户点击修改密码链接后打开修改密码窗口，填入信息，调用该逻辑修改密码
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
