package com.ei.itop.common.util;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ei.itop.common.bean.OpInfo;

public class SessionUtil implements ServletRequestListener{
	
	private static ThreadLocal<HttpServletRequest> value = new ThreadLocal<HttpServletRequest>();
	
	public static HttpSession getSession(){
		return value.get().getSession();
	}
	
	public static OpInfo getOpInfo(){
		return (OpInfo)value.get().getSession().getAttribute("opInfo");
	}
	
	public void requestDestroyed(ServletRequestEvent sre) {
		value.remove();
	}

	public void requestInitialized(ServletRequestEvent sre) {
		value.set((HttpServletRequest)sre.getServletRequest());
	}
}
