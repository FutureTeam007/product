package com.ei.itop.common.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ei.itop.common.constants.SysConstants;

public class LoginFilter implements Filter{

	public void init(FilterConfig filterConfig) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hrs = (HttpServletRequest)request;
		HttpServletResponse hrq = (HttpServletResponse)response;
		if(hrs.getSession().getAttribute(SysConstants.SessionAttribute.OP_SESSION)==null&&!isStaticResource(hrs.getRequestURL().toString())){
			hrq.sendRedirect(hrs.getContextPath()+"/sys/sessionTimeout.jsp");
		}else{
			chain.doFilter(request, response);
		}
	}
	
	
	public boolean isStaticResource(String url){
		String suffix = url.substring(url.lastIndexOf(".")+1,url.length());
		if("js".equals(suffix)){
			return true;
		}
		return false;
	}
	

	public void destroy() {
		
	}
	
}
