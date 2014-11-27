package com.ei.itop.scmgnt.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.dbentity.CcCustProdOp;
import com.ei.itop.common.dbentity.ScJob;
import com.ei.itop.common.dbentity.ScJobI18n;
import com.ei.itop.common.util.SessionUtil;
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
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, CcCustProdOp> custProdOpDAO;
	
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
		return jobInfoDAO.findByParams("SC_JOB.selectInfoByOrgId", hm);
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
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("jobId", jobId);
		List<CcCustProdOp> result = custProdOpDAO.findByParams("CC_CUST_PROD_OP.queryCustProdOpByJobId", hm);
		if(result!=null&&result.size()>0){
			throw new BizException(SessionUtil.getRequestContext().getMessage("i18n.scmgnt.jobinfo.JobInUseError"));
		}
		//删除国际化数据
		hm.clear();
		hm.put("jobId", jobId);
		jobI18nDAO.deleteByParams("SC_JOB_I18N.deleteByJobId",hm);
		//删除主数据
		jobDAO.delete("SC_JOB.deleteByPrimaryKey", jobId);
	}
}
