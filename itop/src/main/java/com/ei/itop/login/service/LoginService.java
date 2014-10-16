/**
 * 
 */
package com.ei.itop.login.service;

import com.ei.itop.common.dbentity.CcUser;
import com.ei.itop.common.dbentity.ScOp;
import com.ei.itop.login.bean.LoginInfo;

/**
 * @author Jack.Qi
 * 
 */
public interface LoginService {

	/**
	 * 用户登录
	 * 
	 * @param loginInfo
	 * @return 用户信息
	 * @throws Exception
	 */
	public CcUser userLogin(LoginInfo loginInfo) throws Exception;

	/**
	 * 顾问登录
	 * 
	 * @param loginInfo
	 * @return 顾问信息
	 * @throws Exception
	 */
	public ScOp adviserLogin(LoginInfo loginInfo) throws Exception;
}
