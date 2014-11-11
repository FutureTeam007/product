package com.ei.itop.scmgnt.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.dbentity.ScJob;
import com.ei.itop.scmgnt.service.JobService;

@Service("jobService")
public class JobServiceImpl implements JobService{

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScJob> jobDAO;
	
	public List<ScJob> queryJobs(long orgId) throws Exception {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", orgId);
		return jobDAO.findByParams("SC_JOB.selectByOrgId", hm);
	}

	public ScJob getJobInfo(long jobId) throws Exception {
		return jobDAO.find("SC_JOB.selectByPrimaryKey", jobId);
	}

}
