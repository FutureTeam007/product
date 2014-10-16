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
}
