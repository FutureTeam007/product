package com.ei.itop.common.util;

import java.util.Locale;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.util.WebUtils;

import com.ailk.dazzle.exception.BizException;
import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.constants.SysConstants;

public class SessionUtil implements ServletRequestListener {

	private static ThreadLocal<HttpServletRequest> value = new ThreadLocal<HttpServletRequest>();

	
	/**
	 * 页面使用
	 * @return
	 */
	public static HttpSession getPageSession() {
		return value.get().getSession();
	}
	
	/**
	 * 页面使用
	 * @return
	 */
	public static OpInfo getPageOpInfo() {
		return (OpInfo) value.get().getSession()
				.getAttribute(SysConstants.SessionAttribute.OP_SESSION);
	}

	public static HttpServletRequest getRequest() {
		return value.get();
	}

	public static RequestContext getRequestContext() {
		return new RequestContext(value.get());
	}

	public static Locale getLocale() {
		Cookie cookie = WebUtils.getCookie(value.get(), "locale");
		if (cookie != null) {
			return StringUtils.parseLocaleString(cookie.getValue());
		} else {
			return Locale.US;
		}
	}
	
	public static HttpSession getSession() throws Exception {
		HttpSession session = value.get().getSession();
		if (session.getAttribute(SysConstants.SessionAttribute.OP_SESSION) == null) {
			throw new BizException(getRequestContext().getMessage(
					"i18n.login.SessionTimeout"));
		} else {
			return session;
		}
	}

	public static OpInfo getOpInfo() {
		OpInfo opInfo = (OpInfo) value.get().getSession()
				.getAttribute(SysConstants.SessionAttribute.OP_SESSION);
		if (opInfo == null) {
			throw new BizException(getRequestContext().getMessage(
					"i18n.login.SessionTimeout"));
		} else {
			return opInfo;
		}
	}

	public void requestDestroyed(ServletRequestEvent sre) {
		value.remove();
	}

	public void requestInitialized(ServletRequestEvent sre) {
		value.set((HttpServletRequest) sre.getServletRequest());
	}
}
