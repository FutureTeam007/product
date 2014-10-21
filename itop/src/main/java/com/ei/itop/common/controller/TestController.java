package com.ei.itop.common.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.dazzle.util.misc.VerifyCodeUtil;
import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.constants.SysConstants;

@Controller
@RequestMapping("/test")

public class TestController {

	@RequestMapping("/login")
	public String test1(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		int role = Integer.parseInt(request.getParameter("role"));
		request.getSession().removeAttribute("opInfo");
		OpInfo oi = new OpInfo();
		switch (role) {
			case 1:
				oi.setOpCode("NO-1");
				oi.setOpId(9001);
				oi.setOpName("itop1-cnh");
				oi.setOpType("USER");
				oi.setOrgId(2001);
				oi.setOrgName("EI");
				request.getSession().setAttribute("opInfo", oi);
				break;
			case 2:
				oi.setOpCode("NO-2");
				oi.setOpId(9002);
				oi.setOpName("itop2-cnh");
				oi.setOpType("USER");
				oi.setOrgId(2001);
				oi.setOrgName("EI");	
				request.getSession().setAttribute("opInfo", oi);
				break;
			case 3:
				oi.setOpCode("SP200001");
				oi.setOpId(200001);
				oi.setOpName("ITOPEI2-EI");
				oi.setOpType("OP");
				oi.setOrgId(2001);
				oi.setOrgName("EI");
				request.getSession().setAttribute("opInfo", oi);
				break;
			case 4:
				oi.setOpCode("SP200002");
				oi.setOpId(200002);
				oi.setOpName("拓创");
				oi.setOpType("OP");
				oi.setOrgId(2001);
				oi.setOrgName("EI");
				request.getSession().setAttribute("opInfo", oi);
				break;
			default:
				break;
		}
		return "redirect:/page/incidentmgnt/main";
	}
	
	@RequestMapping("/logout")
	public String test2(HttpServletRequest request, HttpServletResponse response){
		request.getSession().invalidate();
		return "redirect:/test.jsp";
	}
}
