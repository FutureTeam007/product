/**
 * 
 */
package com.ei.itop.scmgnt.service;

import java.util.List;

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
	 * 修改顾问信息
	 * 
	 * @param op
	 * @return
	 * @throws Exception
	 */
	public void modifyScOp(ScOp op) throws Exception;

	/**
	 * 修改用户登录密码
	 * 
	 * @param passwd
	 * @throws Exception
	 */
	public void changeLoginPasswd(long opId, String passwd) throws Exception;

	/**
	 * 查询商户下所有的顾问
	 * 
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	public List<ScOp> queryAllOp(long orgId) throws Exception;

	public long queryAllOpListCount(long orgId, String loginCode, String opName)
			throws Exception;

	public List<ScOp> queryAllOpList(long orgId, String loginCode,
			String opName, long startIndex, int pageSize) throws Exception;

	public long addOp(ScOp op) throws Exception;

	public void modifyOp(long opId, ScOp op) throws Exception;

	public void modifyOpState(long opId, ScOp op) throws Exception;

	/**
	 * 校验登录账号是否已经存在
	 * 
	 * @param orgId
	 *            商户ID
	 * @param loginCode
	 *            待校验的登录账号 排除的opId，可选
	 * @return
	 * @throws Exception
	 */
	public boolean loginCodeIsExist(long orgId, String loginCode)
			throws Exception;

	/**
	 * 校验登录账号是否已经存在
	 * 
	 * @param loginCode
	 *            待校验的登录账号
	 * @param excludeOpId
	 *            排除的opId，可选
	 * @return
	 * @throws Exception
	 */
	public boolean loginCodeIsExist(String loginCode, Long excludeOpId)
			throws Exception;
}
