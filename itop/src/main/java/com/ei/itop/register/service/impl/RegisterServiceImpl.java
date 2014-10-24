/**
 * 
 */
package com.ei.itop.register.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.dbentity.CcCust;
import com.ei.itop.common.dbentity.CcUser;
import com.ei.itop.custmgnt.service.CustMgntService;
import com.ei.itop.custmgnt.service.UserService;
import com.ei.itop.register.bean.RegisterInfo;
import com.ei.itop.register.service.RegisterService;

/**
 * @author Jack.Qi
 * 
 */
@Service("registerService")
public class RegisterServiceImpl implements RegisterService {

	private static final Logger log = Logger
			.getLogger(RegisterServiceImpl.class);

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, CcUser> userDAO;

	@Resource(name = "custMgntService")
	private CustMgntService custMgntService;

	@Resource(name = "userService")
	private UserService userService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.register.service.IRegisterService#userRegister(com.ei.itop
	 * .register.bean.RegisterInfo)
	 */
	public long userRegister(RegisterInfo registerInfo) throws Exception {
		// TODO Auto-generated method stub

		// 填入商户信息
		CcCust cust = custMgntService.getCustInfo(registerInfo.getCcCustId());
		registerInfo.setScOrgId(cust.getScOrgId());
		registerInfo.setScOrgName(cust.getScOrgName());

		// 设置操作员类别，2-普通用户
		registerInfo.setOpKind(new Long(2));

		// 设置账户状态，2-锁定
		//registerInfo.setState(new Long(2));
		//临时调整为1-正常状态，待加入激活功能后再恢复
		registerInfo.setState(new Long(1));

		long userId = userDAO.save("CC_USER.insert", registerInfo);

		return userId;
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
		userService.activeUser(userId);
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

		CcUser user = userService.queryUser(userId);

		// 校验原密码
		if (!user.getLoginPasswd().equals(oldPasswd)) {
			throw new BizException("原密码有误");
		}

		// 保存新密码
		userService.changeLoginPasswd(userId, newPasswd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.register.service.RegisterService#checkLoginCodeIsExist(java
	 * .lang.String)
	 */
	public boolean checkLoginCodeIsExist(String loginCode) throws Exception {
		// TODO Auto-generated method stub

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("loginCode", loginCode);

		List<CcUser> userList = userDAO.findByParams(
				"CC_USER.queryUserByLoginCode", hm);

		boolean rtnValue = false;

		// 已存在
		if (userList != null && userList.size() > 0) {
			rtnValue = true;
		}

		return rtnValue;
	}

	public CcUser getCcUserByLoginCode(String loginCode) throws Exception {
		// TODO Auto-generated method stub

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("loginCode", loginCode);

		List<CcUser> userList = userDAO.findByParams(
				"CC_USER.queryUserByLoginCode", hm);

		// 已存在
		if (userList != null && userList.size() > 0) {
			userList.get(0);
		}

		return null;
	}
	
	
}
