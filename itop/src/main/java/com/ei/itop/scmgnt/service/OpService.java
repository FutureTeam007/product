/**
 * 
 */
package com.ei.itop.scmgnt.service;

import com.ei.itop.common.dbentity.ScOp;

/**
 * @author Jack.Qi
 * 
 */
public interface OpService {

	/**
	 * 根据ID获取顾问记录
	 * 
	 * @param opId
	 * @return
	 * @throws Exception
	 */
	public ScOp queryScOp(long opId) throws Exception;
	/**
	 * 修改用户登录密码
	 * 
	 * @param passwd
	 * @throws Exception
	 */
	public void changeLoginPasswd(long opId, String passwd) throws Exception;
}
