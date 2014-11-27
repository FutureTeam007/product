package com.ei.itop.scmgnt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.dbentity.ScJob;
import com.ei.itop.common.dbentity.ScJobI18n;
import com.ei.itop.scmgnt.bean.JobInfo;
import com.ei.itop.scmgnt.service.JobService;

@Service("jobService")
public class JobServiceImpl implements JobService{

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScJob> jobDAO;
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScJobI18n> jobI18nDAO;
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, JobInfo> jobInfoDAO;
	
	public List<ScJob> queryJobs(long orgId,String locale) throws Exception {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", orgId);
		hm.put("locale", locale);
		return jobDAO.findByParams("SC_JOB.selectByOrgId", hm);
	}
	
	public List<JobInfo> queryJobInfos(long orgId, String locale)
			throws Exception {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", orgId);
		hm.put("locale", locale);
		List<JobInfo> jobInfos = jobInfoDAO.findByParams("SC_JOB.selectInfoByOrgId", hm);
		for(JobInfo ji:jobInfos){
			hm.clear();
			hm.put("jobId", ji.getScJobId());
			ji.setI18n(jobI18nDAO.findByParams("SC_JOB_I18N.selectJobI18n", hm));
		}
		return jobInfos;
	}

	public ScJob getJob(long jobId,String locale) throws Exception {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("jobId", jobId);
		hm.put("locale", locale);
		return jobDAO.find("SC_JOB.selectByPrimaryKey", hm);
	}

	public void addJob(ScJob job, List<ScJobI18n> i18n)
			throws Exception {
		long jobId = jobDAO.save("SC_JOB.insert",job);
		for(ScJobI18n bean:i18n){
			bean.setScJobId(jobId);
		}
		jobI18nDAO.saveBatch("SC_JOB_I18N.insert",i18n);
	}

	public void modifyJob(ScJob job, List<ScJobI18n> i18n)
			throws Exception {
		jobDAO.update("SC_JOB.updateByPrimaryKeySelective",job);
		for(ScJobI18n bean:i18n){
			bean.setScJobId(job.getScJobId());
		}
		jobI18nDAO.updateBatch("SC_JOB_I18N.update",i18n);
	}

	public void removeJob(long jobId) throws Exception {
		jobDAO.delete("SC_JOB.deleteByPrimaryKey", jobId);
	}
}
