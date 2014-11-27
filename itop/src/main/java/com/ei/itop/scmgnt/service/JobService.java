package com.ei.itop.scmgnt.service;

import java.util.List;
import java.util.Map;

import com.ei.itop.common.dbentity.ScJob;
import com.ei.itop.common.dbentity.ScJobI18n;
import com.ei.itop.scmgnt.bean.JobInfo;

public interface JobService {
	/**
	 * 根据商户查询岗位列表
	 * @param orgId
	 * @return
	 */
	List<ScJob> queryJobs(long orgId,String locale) throws Exception;
	
	/**
	 * 根据商户查询岗位列表
	 * @param orgId
	 * @return
	 */
	List<JobInfo> queryJobInfos(long orgId,String locale) throws Exception;
	
	/**
	 * 获得单个岗位信息
	 * @param jobId
	 * @return
	 * @throws Exception
	 */
	ScJob getJob(long jobId,String locale) throws Exception;
	
	/**
	 * 增加岗位
	 * @return
	 * @throws Exception
	 */
	void addJob(ScJob job,List<ScJobI18n> i18n) throws Exception;
	
	/**
	 * 修改岗位
	 * @return
	 * @throws Exception
	 */
	void modifyJob(ScJob job,List<ScJobI18n> i18n) throws Exception;
	
	/**
	 * 删除岗位
	 * @return
	 * @throws Exception
	 */
	void removeJob(long jobId) throws Exception;
}
