package com.ei.itop.scmgnt.service;

import java.util.List;

import com.ei.itop.common.dbentity.ScJob;

public interface JobService {
	/**
	 * 根据商户查询岗位列表
	 * @param orgId
	 * @return
	 */
	List<ScJob> queryJobs(long orgId,String locale) throws Exception;
	/**
	 * 获得单个岗位信息
	 * @param jobId
	 * @return
	 * @throws Exception
	 */
	ScJob getJobInfo(long jobId,String locale) throws Exception;
}
