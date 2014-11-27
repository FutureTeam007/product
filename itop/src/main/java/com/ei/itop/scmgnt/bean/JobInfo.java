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
	 * 岗位名称（国际化）
	 * @return
	 */
	private List<ScJobI18n> i18n;
	

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

	public List<ScJobI18n> getI18n() {
		return i18n;
	}

	public void setI18n(List<ScJobI18n> i18n) {
		this.i18n = i18n;
	}
}
