/**
 * 
 */
package com.ei.itop.custmgnt.service;

import java.util.List;

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
	/**
	 * 查询用户(正常状态)
	 * @param orgId
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public List<CcUser> queryUserList(long orgId, long custId) throws Exception;
	
	/**
	 * 查询用户(所有状态，包含锁定或离职)
	 * @param orgId
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public List<CcUser> queryAllUserList(long orgId, long[] custId,String userCode,String userName,long startIndex,int pageSize) throws Exception;
	/**
	 * 查询用户(所有状态，包含锁定或离职)查询总数据条数
	 * @param orgId
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public long queryAllUserListCount(long orgId, long[] custId,String userCode,String userName,long startIndex,int pageSize) throws Exception;
}
