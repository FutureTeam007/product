package com.ei.itop.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.json.JSONUtils;
import com.ailk.dazzle.util.web.HttpUtil;
import com.ei.itop.common.sec.MacBind;

public class ExceptionHandleFilter implements Filter{

	private static final Logger log = Logger
			.getLogger(ExceptionHandleFilter.class);
	
	public void init(FilterConfig filterConfig) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException{
		HttpServletRequest hrt = (HttpServletRequest)request;
		HttpServletResponse hrs = (HttpServletResponse)response;
		if(!MacBind.isOk()){
			log.error("产品License异常，您正在使用未激活的产品");
			hrs.setCharacterEncoding("UTF-8");
			hrs.setContentType("text/html; charset=UTF-8");
			hrs.getWriter().write("产品License异常，您正在使用未激活的产品");
			return;
		}
		Map<String, String> json = new HashMap<String, String>();
		try{
			chain.doFilter(request, response);
		}catch(Exception e){
			//获得原始异常对象
			Throwable excp = e.getCause();
			if(excp instanceof BizException){
				log.warn(e.getMessage(),e);
				json.put("errorMsg", excp.getMessage());
			}else{
				log.error(e.getMessage(),e);
				json.put("errorMsg", SessionUtil.getRequestContext().getMessage("i18n.system.500err"));
			}
		}finally{
			if (json.get("errorMsg")!=null&&HttpUtil.isAjaxRequest(hrt)) {
				hrs.setStatus(500);
				hrs.setCharacterEncoding("UTF-8");
				hrs.setContentType("text/html; charset=UTF-8");
				try {
					hrs.getWriter().print(JSONUtils.toJSONString(json));
				} catch (Exception e) {
					log.error("",e);
				}
			}
		}
	}

	public void destroy() {
		
	}
	
}
