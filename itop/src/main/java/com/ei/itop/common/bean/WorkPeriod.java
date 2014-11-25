/**
 * 
 */
package com.ei.itop.common.bean;

import java.util.Date;

/**
 * 工作时段
 * 
 * @author Jack.Qi
 * 
 */
public class WorkPeriod {

	// 时段开始时刻，精确到秒
	private Date beginTime;

	// 时段结束时刻，精确到秒
	private Date endTime;

	/**
	 * @return the beginTime
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime
	 *            the beginTime to set
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
