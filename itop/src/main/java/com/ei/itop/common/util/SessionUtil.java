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

import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.constants.SysConstants;

public class SessionUtil implements ServletRequestListener{
	
	private static ThreadLocal<HttpServletRequest> value = new ThreadLocal<HttpServletRequest>();
	
	public static HttpSession getSession(){
		return value.get().getSession();
	}
	
	public static HttpServletRequest getRequest(){
		return value.get();
	}
	
	public static RequestContext getRequestContext(){
		return new RequestContext(value.get());
	}
	
	public static Locale getLocale(){
		Cookie cookie = WebUtils.getCookie(value.get(), "locale");
		if(cookie!=null){
			return StringUtils.parseLocaleString(cookie.getValue());
		}else{
			return Locale.CHINA;
		}
	}
	
	public static OpInfo getOpInfo(){
		return (OpInfo)value.get().getSession().getAttribute(SysConstants.SessionAttribute.OP_SESSION);
	}
	
	public void requestDestroyed(ServletRequestEvent sre) {
		value.remove();
	}

	public void requestInitialized(ServletRequestEvent sre) {
		value.set((HttpServletRequest)sre.getServletRequest());
	}
}
