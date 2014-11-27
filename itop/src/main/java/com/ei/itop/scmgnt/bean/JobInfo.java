package com.ei.itop.scmgnt.bean;

import java.util.List;

import com.ei.itop.common.dbentity.ScJob;
import com.ei.itop.common.dbentity.ScJobI18n;

public class JobInfo extends ScJob{
	/**
	 * 岗位类型名称
	 */
	private String jobClassName;

	/**
	 * 岗位级别名称
	 */
	private String jobLevelName;
	
	/**
	 * 岗位中文
	 */
	private String jobNameZh;
	
	/**
	 * 岗位英文
	 */
	private String jobNameEn;
	
	public String getJobClassName() {
		return jobClassName;
	}

	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}

	public String getJobLevelName() {
		return jobLevelName;
	}

	public void setJobLevelName(String jobLevelName) {
		this.jobLevelName = jobLevelName;
	}

	public String getJobNameZh() {
		return jobNameZh;
	}

	public void setJobNameZh(String jobNameZh) {
		this.jobNameZh = jobNameZh;
	}

	public String getJobNameEn() {
		return jobNameEn;
	}

	public void setJobNameEn(String jobNameEn) {
		this.jobNameEn = jobNameEn;
	}
}
