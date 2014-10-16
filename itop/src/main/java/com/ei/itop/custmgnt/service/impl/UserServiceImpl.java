/**
 * 
 */
package com.ei.itop.custmgnt.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

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

}
