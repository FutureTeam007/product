package com.ei.itop.scmgnt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.dazzle.util.json.JSONUtils;
import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.dbentity.ScJob;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.scmgnt.service.JobService;

@Controller
@RequestMapping("/job")
public class JobController {

	@Autowired
	private JobService jobService;

	/**
	 * 查询岗位
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public void queryJobList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		List<ScJob> jobs = jobService.queryJobs(oi.getOrgId());
		String jsonData = JSONUtils.toJSONString(jobs);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
}
