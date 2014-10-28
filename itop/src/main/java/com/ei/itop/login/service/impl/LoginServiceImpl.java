/**
 * 
 */
package com.ei.itop.login.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.dbentity.CcUser;
import com.ei.itop.common.dbentity.ScOp;
import com.ei.itop.login.bean.LoginInfo;
import com.ei.itop.login.service.LoginService;

/**
 * @author Jack.Qi
 * 
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

	private static final Logger log = Logger.getLogger(LoginServiceImpl.class);

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, CcUser> userDAO;

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScOp> adviserDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.login.service.ILoginService#userLogin(com.ei.itop.login.bean
	 * .LoginInfo)
	 */
	public CcUser userLogin(LoginInfo loginInfo) throws Exception {
		// TODO Auto-generated method stub

		if (loginInfo.getLoginCode() == null
				|| "".equals(loginInfo.getLoginCode())
				|| loginInfo.getLoginPasswd() == null
				|| "".equals(loginInfo.getLoginPasswd())) {
			throw new BizException("用户名及密码均不能为空");
		}

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("loginCode", loginInfo.getLoginCode());

		CcUser user = userDAO.find("CC_USER.queryUserByLoginCode", hm);

		if (user == null) {
			throw new BizException("用户不存在");
		}

		if (user.getState() == 0||user.getState() == 2) {
			throw new BizException("用户已被冻结");
		}
		
		if (user.getState() == -1) {
			throw new BizException("用户未激活，请登录邮箱激活账号");
		}

		if (!loginInfo.getLoginPasswd().equals(user.getLoginPasswd())) {
			throw new BizException("密码错误");
		}

		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.login.service.ILoginService#adviserLogin(com.ei.itop.login
	 * .bean.LoginInfo)
	 */
	public ScOp adviserLogin(LoginInfo loginInfo) throws Exception {
		// TODO Auto-generated method stub

		if (loginInfo.getLoginCode() == null
				|| "".equals(loginInfo.getLoginCode())
				|| loginInfo.getLoginPasswd() == null
				|| "".equals(loginInfo.getLoginPasswd())) {
			throw new BizException("用户名及密码均不能为空");
		}

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("loginCode", loginInfo.getLoginCode());

		ScOp op = adviserDAO.find("SC_OP.queryOpByLoginCode", hm);

		if (op == null) {
			throw new BizException("用户不存在");
		}

		if (op.getState() != 1) {
			throw new BizException("用户状态不正常");
		}

		if (!loginInfo.getLoginPasswd().equals(op.getLoginPasswd())) {
			throw new BizException("密码错误");
		}

		return op;
	}

}
