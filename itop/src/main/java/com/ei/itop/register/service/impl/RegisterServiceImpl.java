/**
 * 
 */
package com.ei.itop.register.service.impl;

import java.util.List;

import com.ei.itop.common.dbentity.CcCust;
import com.ei.itop.register.bean.RegisterInfo;
import com.ei.itop.register.service.IRegisterService;

/**
 * @author Jack.Qi
 * 
 */
public class RegisterServiceImpl implements IRegisterService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.register.service.IRegisterService#userRegister(com.ei.itop
	 * .register.bean.RegisterInfo)
	 */
	public long userRegister(RegisterInfo registerInfo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.register.service.IRegisterService#queryCustListByDomainName
	 * (java.lang.String)
	 */
	public List<CcCust> queryCustListByDomainName(String domainName)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 发送账号激活链接邮件
	 * 
	 * @param userId
	 *            用户ID
	 * @param email
	 *            用户邮箱
	 * @throws Exception
	 */
	public void sendEmailActiveAccount(long userId, String email)
			throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.register.service.IRegisterService#activeAccount(long)
	 */
	public void activeAccount(long userId) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.register.service.IRegisterService#checkEmail(java.lang.String
	 * )
	 */
	public boolean checkEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 发送修改密码链接邮件
	 * 
	 * @param email
	 *            需发送修改密码链接的email
	 * @throws Exception
	 */
	public void sendEmailChgPasswd(String email) throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.register.service.IRegisterService#userChgPasswd(long,
	 * java.lang.String, java.lang.String)
	 */
	public void userChgPasswd(long userId, String oldPasswd, String newPasswd)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
