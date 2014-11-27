package com.ei.itop.scmgnt.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.dazzle.util.json.JSONUtils;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.constants.SysConstants;
import com.ei.itop.common.dbentity.ScJob;
import com.ei.itop.common.dbentity.ScJobI18n;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.scmgnt.bean.JobInfo;
import com.ei.itop.scmgnt.service.JobService;

@Controller
@RequestMapping("/job")
public class JobController {

	@Autowired
	private JobService jobService;

	/**
	 * 查询岗位(下拉列表用)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public void queryJobList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		List<ScJob> jobs = jobService.queryJobs(oi.getOrgId(),SessionUtil.getLocale().toString());
		String jsonData = JSONUtils.toJSONString(jobs);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	/**
	 * 查询岗位(管理表格用)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/infolist")
	public void queryJobInfos(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		List<JobInfo> jobs = jobService.queryJobInfos(oi.getOrgId(),SessionUtil.getLocale().toString());
		String jsonData = JSONUtils.toJSONString(jobs);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	/**
	 * 修改岗位
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/modify")
	public void modifyJob(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		long scJobId = VarTypeConvertUtils.string2Long(request.getParameter("scJobId"));
		String jobCode = request.getParameter("jobCode");
		String jobClass = request.getParameter("jobClass");
		String jobLevel = request.getParameter("jobLevel");
		String jobNameEn = request.getParameter("jobNameEn");
		String jobNameZh = request.getParameter("jobNameZh");
		ScJob job = new ScJob();
		job.setScJobId(scJobId);
		job.setJobClass(jobClass);
		job.setJobLevel(jobLevel);
		job.setJobCode(jobCode);
		job.setJobName(jobNameZh);
		job.setScOrgId(oi.getOrgId());
		List<ScJobI18n> i18n = new ArrayList<ScJobI18n>();
		ScJobI18n i18nZh = new ScJobI18n();
		i18nZh.setJobName(jobNameZh);
		i18nZh.setScJobId(scJobId);
		i18nZh.setLangFlag(SysConstants.I18nAttribute.zh_CN);
		i18n.add(i18nZh);
		ScJobI18n i18nEn = new ScJobI18n();
		i18nEn.setJobName(jobNameEn);
		i18nEn.setScJobId(scJobId);
		i18nEn.setLangFlag(SysConstants.I18nAttribute.en_US);
		i18n.add(i18nEn);
		jobService.modifyJob(job, i18n);
	}
	
	@RequestMapping("/add")
	public void addJob(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		String jobCode = request.getParameter("jobCode");
		String jobClass = request.getParameter("jobClass");
		String jobLevel = request.getParameter("jobLevel");
		String jobNameEn = request.getParameter("jobNameEn");
		String jobNameZh = request.getParameter("jobNameZh");
		ScJob job = new ScJob();
		job.setJobClass(jobClass);
		job.setJobLevel(jobLevel);
		job.setJobCode(jobCode);
		job.setJobName(jobNameZh);
		job.setScOrgId(oi.getOrgId());
		List<ScJobI18n> i18n = new ArrayList<ScJobI18n>();
		ScJobI18n i18nZh = new ScJobI18n();
		i18nZh.setJobName(jobNameZh);
		i18nZh.setLangFlag(SysConstants.I18nAttribute.zh_CN);
		i18n.add(i18nZh);
		ScJobI18n i18nEn = new ScJobI18n();
		i18nEn.setJobName(jobNameEn);
		i18nEn.setLangFlag(SysConstants.I18nAttribute.en_US);
		i18n.add(i18nEn);
		jobService.addJob(job, i18n);
	}
	
	@RequestMapping("/remove")
	public void removeJob(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long jobId = VarTypeConvertUtils.string2Long(request.getParameter("jobId"));
		jobService.removeJob(jobId);
	}
}
