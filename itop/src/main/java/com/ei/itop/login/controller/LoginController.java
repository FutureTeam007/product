package com.ei.itop.login.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.type.StringUtils;
import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.constants.SysConstants;
import com.ei.itop.common.dbentity.CcUser;
import com.ei.itop.common.dbentity.ScOp;
import com.ei.itop.login.bean.LoginInfo;
import com.ei.itop.login.service.LoginService;

@Controller
@RequestMapping("")
public class LoginController {

	private static final Logger log = Logger
			.getLogger(LoginController.class);
	
	@Autowired
	LoginService loginService;

	@RequestMapping("/doLogin")
	public String doLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 获取登录的操作员类型
		String opType = request.getParameter("opType");
		// 用户名
		String accountNo = request.getParameter("accountNo");
		// 密码
		String accountPwd = request.getParameter("accountPwd");
		// 验证码
		String verifyCode = request.getParameter("verifyCode");
		// 校验验证码
		if (!request.getSession()
				.getAttribute(SysConstants.SessionAttribute.LOGIN_VERIFY_CODE)
				.equals(verifyCode)) {
			request.setAttribute("errorMsg", "验证码不正确，请重新输入");
			request.setAttribute("accountNo", accountNo);
			return "/login";
		} else {
			//检查信息完整性
			if(StringUtils.isEmpty(accountNo)){
				request.setAttribute("errorMsg", "用户名输入不能为空");
				return "/login";
			}
			//检查信息完整性
			if(StringUtils.isEmpty(accountPwd)){
				request.setAttribute("errorMsg", "密码输入不能为空");
				return "/login";
			}
			// 封装登录Bean
			LoginInfo li = new LoginInfo();
			li.setLoginCode(accountNo.toLowerCase());
			li.setLoginPasswd(accountPwd);
			li.setIdentifyingCode(verifyCode);
			// 如果是用户登录
			if (SysConstants.OpAttribute.OP_ROLE_USER.equals(opType)) {
				CcUser user = null;
				try {
					user = loginService.userLogin(li);
				} catch (BizException e) {
					log.warn("",e);
					request.setAttribute("errorMsg", e.getMessage());
					request.setAttribute("accountNo", accountNo);
					return "/login";
				} catch (Exception e) {
					log.error("",e);
					request.setAttribute("accountNo", accountNo);
					request.setAttribute("errorMsg", "登录异常：系统临时故障，请稍后再试");
					return "/login";
				}
				// 账号密码不正确
				if (user == null) {
					request.setAttribute("accountNo", accountNo);
					request.setAttribute("errorMsg", "账号密码不正确，请重新输入");
					return "/login";
				} else {
					putLoginInfo2Session(request, response,accountNo,user.getCcUserId(),
							user.getLoginCode(), user.getOpName(),
							user.getLastName() + "." + user.getFirstName(),
							opType, user.getScOrgId(), user.getScOrgName());
					return "redirect:/page/incidentmgnt/main";
				}
			}
			// 如果是服务方登录
			else if (SysConstants.OpAttribute.OP_ROLE_OP.equals(opType)) {
				ScOp op = null;
				try {
					op = loginService.adviserLogin(li);
				}  catch (BizException e) {
					log.warn("",e);
					request.setAttribute("accountNo", accountNo);
					request.setAttribute("errorMsg", e.getMessage());
					return "/login";
				} catch (Exception e) {
					log.error("",e);
					request.setAttribute("accountNo", accountNo);
					request.setAttribute("errorMsg", "登录异常：系统临时故障，请稍后再试");
					return "/login";
				}
				// 账号密码不正确
				if (op == null) {
					request.setAttribute("accountNo", accountNo);
					request.setAttribute("errorMsg", "账号密码不正确，请重新输入");
					return "login";
				} else {
					putLoginInfo2Session(request, response, accountNo,op.getScOpId(),
							op.getLoginCode(), op.getOpName(),
							op.getLastName() + "." + op.getFirstName(),
							opType, op.getScOrgId(), "待填");
					return "redirect:/page/incidentmgnt/main";
				}
			}
			return "/login";
		}
	}

	@RequestMapping("/doLogout")
	public String doLogout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().invalidate();
		return "/login";
	}
	/**
	 * 封装SessionBean放入Session
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
			HttpServletResponse response,String loginCode, long opId, String opCode,
			String opName, String opEnName, String opType, long orgId,
			String orgName) {
		// 封装OP信息放入Session中
		OpInfo op = new OpInfo();
		op.setOpCode(opCode);
		op.setOpId(opId);
		op.setOpName(opName);
		op.setOpFullName(opName + "/" + opEnName);
		op.setOpType(opType);
		op.setOrgId(orgId);
		op.setOrgName(orgName);
		request.getSession().setAttribute(
				SysConstants.SessionAttribute.OP_SESSION, op);
		// 将操作员的登录类型放入Cookie，方便下次登录
		Cookie cookieOpType = new Cookie(SysConstants.CookieAttribute.OP_TYPE, opType);
		Cookie cookieAccountNo = new Cookie(SysConstants.CookieAttribute.ACCOUNT_NO, loginCode);
		response.addCookie(cookieOpType);
		response.addCookie(cookieAccountNo);
	}

}
