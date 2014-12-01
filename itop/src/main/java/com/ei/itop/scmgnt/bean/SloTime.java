package com.ei.itop.scmgnt.bean;

/**
 * Slo时间参数设置
 * @author vintin
 *
 */
public class SloTime {
	
	private long paramId;

	private int timeType;
	
	private String workStartTimeAm;
	
	private String workEndTimeAm;
	
	private String workStartTimePm;
	
	private String workEndTimePm;

	public int getTimeType() {
		return timeType;
	}

	public void setTimeType(int timeType) {
		this.timeType = timeType;
	}

	public String getWorkStartTimeAm() {
		return workStartTimeAm;
	}

	public void setWorkStartTimeAm(String workStartTimeAm) {
		this.workStartTimeAm = workStartTimeAm;
	}

	public String getWorkEndTimeAm() {
		return workEndTimeAm;
	}

	public void setWorkEndTimeAm(String workEndTimeAm) {
		this.workEndTimeAm = workEndTimeAm;
	}

	public String getWorkStartTimePm() {
		return workStartTimePm;
	}

	public void setWorkStartTimePm(String workStartTimePm) {
		this.workStartTimePm = workStartTimePm;
	}

	public String getWorkEndTimePm() {
		return workEndTimePm;
	}

	public void setWorkEndTimePm(String workEndTimePm) {
		this.workEndTimePm = workEndTimePm;
	}

	public long getParamId() {
		return paramId;
	}

	public void setParamId(long paramId) {
		this.paramId = paramId;
	}
}
