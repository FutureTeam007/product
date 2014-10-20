/**
 * 
 */
package com.ei.itop.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.dazzle.common.web.BizController;
import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.web.HttpUtil;

@Controller("exceptionController")
public class ExceptionController extends BizController {
	
	private static final Logger logger = Logger
			.getLogger(ExceptionController.class);

	@ExceptionHandler
	public void handleException(Throwable ex, HttpServletRequest request, HttpServletResponse response) {
		String msg = null;
		if (ex != null) {
			if (ex instanceof ServletException) {
				final Throwable causeT = ((ServletException) ex).getRootCause();
				if (causeT != null) {
					ex = causeT;
				}
			}
			logger.error(ex, ex);
			if (ex instanceof BizException) {
				msg = ex.getMessage();
			} else {
				msg = "对不起，系统临时故障，请稍后再试!";
			}
		}
		processErrorResult(request, response, msg);
	}

	protected void processErrorResult(HttpServletRequest request,
			HttpServletResponse response, String msg) {
		if (HttpUtil.isAjaxRequest(request)) {
			// ajax请求
			Map<String, Object> json = new HashMap<String, Object>();
			json.put("errorMsg", msg);
			response.setStatus(500);
			returnJson(response, json);
		}
	}
	
	@RequestMapping("/error")
	public void error(HttpServletRequest request, HttpServletResponse response) {
		handleException((Throwable)request.getAttribute("exception"), request, response);
	}

}
