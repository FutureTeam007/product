package com.ei.itop.common.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.ei.itop.common.bean.OpInfo;

public class LoginFilter implements Filter{

	public void init(FilterConfig filterConfig) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hr = (HttpServletRequest)request;
		OpInfo oi = new OpInfo();
		oi.setOpCode("100");
		oi.setOpId(100);
		oi.setOpName("test");
		oi.setOpType("user");
		hr.getSession().setAttribute("opInfo", oi);
		chain.doFilter(request, response);
	}

	public void destroy() {
		
	}
	
}
