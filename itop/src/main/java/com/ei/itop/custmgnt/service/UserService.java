/**
 * 
 */
package com.ei.itop.custmgnt.service;

import com.ei.itop.common.dbentity.CcUser;

/**
 * @author Jack.Qi
 * 
 */
public interface UserService {

	/**
	 * 根据ID查询用户信息
	 * 
	 * @param userId
	 *            用户ID
	 * @return 用户信息
	 * @throws Exception
	 */
	public CcUser queryUser(long userId) throws Exception;

	/**
	 * 修改用户登录密码
	 * 
	 * @param passwd
	 * @throws Exception
	 */
	public void changeLoginPasswd(long userId, String passwd) throws Exception;
	
	/**
	 * 修改用户信息
	 * 
	 * @param passwd
	 * @throws Exception
	 */
	public void modifyUserInfo(CcUser user) throws Exception;
	/**
	 * 激活用户
	 * @param userId
	 * @throws Exception
	 */
	public void activeUser(long userId) throws Exception;
}
