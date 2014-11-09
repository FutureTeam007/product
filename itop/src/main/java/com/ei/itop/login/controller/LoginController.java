package com.ei.itop.login.controller;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.type.StringUtils;
import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.constants.SysConstants;
import com.ei.itop.common.dbentity.CcUser;
import com.ei.itop.common.dbentity.ScOp;
import com.ei.itop.common.dbentity.ScOrg;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.login.bean.LoginInfo;
import com.ei.itop.login.service.LoginService;
import com.ei.itop.scmgnt.service.ScOrgService;

@Controller
@RequestMapping("")
public class LoginController {

	private static final Logger log = Logger.getLogger(LoginController.class);

	@Autowired
	LoginService loginService;
	
	@Autowired
	ScOrgService orgService;

	@Autowired
	CookieLocaleResolver localeResolver;

	@RequestMapping("/i18n")
	public String changeLocale(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String locale = request.getParameter("locale");
		if (locale == null || locale.equals("")) {
			return "redirect:/login.jsp";
		} else {
			Locale newLocale = org.springframework.util.StringUtils
					.parseLocaleString(locale);
			localeResolver.setLocale(request, response, newLocale);
			return "redirect:/login.jsp";
		}
	}

	@RequestMapping("/doLogin")
	public String doLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long start = System.currentTimeMillis();
		log.debug("controller登录开始");
		log.debug("controller获取前台传入参数");
		// 获取登录的操作员类型
		String opType = request.getParameter("opType");
		// 用户名
		String accountNo = request.getParameter("accountNo");
		// 密码
		String accountPwd = request.getParameter("accountPwd");
		// 验证码
		String verifyCode = request.getParameter("verifyCode");
		log.debug("controller前台传入参数获取完毕");
		// 校验验证码
		if (!request.getSession()
				.getAttribute(SysConstants.SessionAttribute.LOGIN_VERIFY_CODE)
				.equals(verifyCode)) {
			request.setAttribute("errorMsg", SessionUtil.getRequestContext()
					.getMessage("i18n.login.VerifyCodeError"));
			request.setAttribute("accountNo", accountNo);
			log.debug("验证码不正确");
			recordLog(start);
			return "/login";
		} else {
			// 检查信息完整性
			if (StringUtils.isEmpty(accountNo)) {
				request.setAttribute("errorMsg", SessionUtil.getRequestContext()
						.getMessage("i18n.login.AccountEmpty"));
				log.debug("用户名输入不能为空");
				recordLog(start);
				return "/login";
			}
			// 检查信息完整性
			if (StringUtils.isEmpty(accountPwd)) {
				request.setAttribute("errorMsg", SessionUtil.getRequestContext()
						.getMessage("i18n.login.PasswordEmpty"));
				log.debug("密码输入不能为空");
				recordLog(start);
				return "/login";
			}
			// 封装登录Bean
			LoginInfo li = new LoginInfo();
			li.setLoginCode(accountNo.toLowerCase());
			li.setLoginPasswd(accountPwd);
			li.setIdentifyingCode(verifyCode);
			// 如果是用户登录
			if (SysConstants.OpAttribute.OP_ROLE_USER.equals(opType)) {
				log.debug("监测到时普通用户");
				CcUser user = null;
				try {
					log.debug("开始执行Service登录操作");
					user = loginService.userLogin(li);
					log.debug("执行Service登录操作完毕");
				} catch (BizException e) {
					log.warn("", e);
					request.setAttribute("errorMsg", e.getMessage());
					request.setAttribute("accountNo", accountNo);
					log.debug("发生业务异常");
					recordLog(start);
					return "/login";
				} catch (Exception e) {
					log.error("", e);
					request.setAttribute("accountNo", accountNo);
					request.setAttribute("errorMsg", SessionUtil.getRequestContext()
							.getMessage("i18n.login.SystemError"));
					log.debug("发生系统异常");
					recordLog(start);
					return "/login";
				}
				// 账号密码不正确
				if (user == null) {
					request.setAttribute("accountNo", accountNo);
					request.setAttribute("errorMsg", SessionUtil.getRequestContext()
							.getMessage("i18n.login.AccountPasswordNotCorrect"));
					recordLog(start);
					log.debug("账号密码不正确，请重新输入");
					return "/login";
				} else {
					log.debug("正常登录，将信息放入Session");
					putLoginInfo2Session(request, response, accountNo,
							user.getCcUserId(), user.getLoginCode(),
							user.getOpName(),
							user.getLastName() + "." + user.getFirstName(),
							opType, user.getScOrgId(), user.getScOrgName(),
							user.getCcCustId(), user.getOpKind());
					log.debug("正常登录，将信息放入Session完毕，即将跳转");
					recordLog(start);
					return "redirect:/page/incidentmgnt/main";
				}
			}
			// 如果是服务方登录
			else if (SysConstants.OpAttribute.OP_ROLE_OP.equals(opType)) {
				ScOp op = null;
				try {
					op = loginService.adviserLogin(li);
				} catch (BizException e) {
					log.warn("", e);
					request.setAttribute("accountNo", accountNo);
					request.setAttribute("errorMsg", e.getMessage());
					recordLog(start);
					return "/login";
				} catch (Exception e) {
					log.error("", e);
					request.setAttribute("accountNo", accountNo);
					request.setAttribute("errorMsg", SessionUtil.getRequestContext()
							.getMessage("i18n.login.SystemError"));
					recordLog(start);
					return "/login";
				}
				// 账号密码不正确
				if (op == null) {
					request.setAttribute("accountNo", accountNo);
					request.setAttribute("errorMsg", SessionUtil.getRequestContext()
							.getMessage("i18n.login.AccountPasswordNotCorrect"));
					recordLog(start);
					return "/login";
				} else {
					//获取组织名称
					ScOrg scOrg = orgService.queryScOrg(op.getScOrgId());
					//放入Session
					putLoginInfo2Session(request, response, accountNo,
							op.getScOpId(), op.getLoginCode(), op.getOpName(),
							op.getLastName() + "." + op.getFirstName(), opType,
							op.getScOrgId(), scOrg.getScOrgName(), null, op.getOpKind().longValue());
					recordLog(start);
					return "redirect:/page/incidentmgnt/main";
				}
			}
			return "/login";
		}
	}

	@RequestMapping("/login-bugfix-hb")
	public String doLoginFixBug(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 用户名
		String accountNo = "abcuser1@163.com";
		// 密码
		String accountPwd = "sdn";
		// 封装登录Bean
		LoginInfo li = new LoginInfo();
		li.setLoginCode(accountNo.toLowerCase());
		li.setLoginPasswd(accountPwd);
		CcUser user = null;
		try {
			user = loginService.userLogin(li);
		} catch (BizException e) {
			log.warn("", e);
			request.setAttribute("errorMsg", e.getMessage());
			request.setAttribute("accountNo", accountNo);
			log.debug("发生业务异常");
			return "/login";
		} catch (Exception e) {
			log.error("", e);
			request.setAttribute("accountNo", accountNo);
			request.setAttribute("errorMsg", "登录异常：系统临时故障，请稍后再试");
			log.debug("发生系统异常");
			return "/login";
		}
		// 账号密码不正确
		if (user == null) {
			request.setAttribute("accountNo", accountNo);
			request.setAttribute("errorMsg", "账号密码不正确，请重新输入");
			return "/login";
		} else {
			putLoginInfo2Session(request, response, accountNo,
					user.getCcUserId(), user.getLoginCode(), user.getOpName(),
					user.getLastName() + "." + user.getFirstName(), "USER",
					user.getScOrgId(), user.getScOrgName(), user.getCcCustId(),
					user.getOpKind());
			return "redirect:/page/incidentmgnt/main";
		}
	}

	private void recordLog(long start) {
		long end = System.currentTimeMillis();
		log.debug("controller登录结束，耗时（ms）：" + (end - start));
	}

	@RequestMapping("/doLogout")
	public String doLogout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().invalidate();
		return "/login";
	}

	/**
	 * 封装SessionBean放入Session
	 * 
	 * @param request
	 * @param response
	 * @param opId
	 * @param opCode
	 * @param opName
	 * @param opEnName
	 * @param opType
	 * @param orgId
	 * @param orgName
	 */
	private void putLoginInfo2Session(HttpServletRequest request,
			HttpServletResponse response, String loginCode, long opId,
			String opCode, String opName, String opEnName, String opType,
			long orgId, String orgName, Long custId, Long opKind) {
		// 封装OP信息放入Session中
		OpInfo op = new OpInfo();
		op.setOpCode(opCode);
		op.setOpId(opId);
		op.setOpName(opName);
		op.setOpFullName(opName + "/" + opEnName);
		op.setOpType(opType);
		op.setOrgId(orgId);
		op.setOrgName(orgName);
		op.setCustId(custId + "");
		op.setOpKind(opKind == null ? 0 : opKind);
		request.getSession().setAttribute(
				SysConstants.SessionAttribute.OP_SESSION, op);
		// 将操作员的登录类型放入Cookie，方便下次登录
		Cookie cookieOpType = new Cookie(SysConstants.CookieAttribute.OP_TYPE,
				opType);
		Cookie cookieAccountNo = new Cookie(
				SysConstants.CookieAttribute.ACCOUNT_NO, loginCode);
		response.addCookie(cookieOpType);
		response.addCookie(cookieAccountNo);
	}

}
