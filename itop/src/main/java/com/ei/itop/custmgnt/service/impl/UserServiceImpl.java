/**
 * 
 */
package com.ei.itop.custmgnt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.dbentity.CcUser;
import com.ei.itop.custmgnt.service.UserService;

/**
 * @author Jack.Qi
 * 
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	private static final Logger log = Logger.getLogger(UserServiceImpl.class);

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, CcUser> userDAO;
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, Long> userCountDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.custmgnt.service.UserService#queryUser(long)
	 */
	public CcUser queryUser(long userId) throws Exception {
		// TODO Auto-generated method stub

		CcUser user = userDAO.find("CC_USER.selectByPrimaryKey", userId);

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.custmgnt.service.UserService#modifyLoginPasswd(java.lang.
	 * String)
	 */
	public void changeLoginPasswd(long userId, String passwd) throws Exception {
		// TODO Auto-generated method stub

		if (passwd == null || passwd.length() == 0) {
			throw new BizException("密码不能为空");
		}

		CcUser user = new CcUser();
		user.setCcUserId(userId);
		user.setLoginPasswd(passwd);

		userDAO.update("CC_USER.updateByPrimaryKeySelective", user);
	}

	public void activeUser(long userId) throws Exception {
		CcUser user = new CcUser();
		user.setCcUserId(userId);
		user.setState(1L);
		userDAO.update("CC_USER.updateByPrimaryKeySelective", user);
	}

	public void modifyUserInfo(CcUser user) throws Exception {
		userDAO.update("CC_USER.updateByPrimaryKeySelective", user);
	}

	public List<CcUser> queryUserList(long orgId, long custId) throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orgId", orgId);
		params.put("custId", custId);
		return userDAO.findByParams("CC_USER.queryUsersByOrgIdAndCustId", params);
	}

	public List<CcUser> queryAllUserList(long orgId, long[] custId,String userCode,String userName,long startIndex,int pageSize)
			throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orgId", orgId);
		params.put("custId", custId);
		params.put("userCode", userCode);
		params.put("userName", userName);
		params.put("startIndex", startIndex);
		params.put("endIndex", startIndex + pageSize - 1);
		return userDAO.findByParams("CC_USER.queryAllUsersByOrgIdAndCustIdPaging", params);
	}
	
	public long queryAllUserListCount(long orgId, long[] custId,String userCode,String userName,long startIndex,int pageSize)
			throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orgId", orgId);
		params.put("custId", custId);
		params.put("userCode", userCode);
		params.put("userName", userName);
		return userCountDAO.find("CC_USER.queryAllUsersByOrgIdAndCustIdCount", params);
	}

}
