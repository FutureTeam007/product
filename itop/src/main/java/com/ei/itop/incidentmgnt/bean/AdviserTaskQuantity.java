/**
 * 
 */
package com.ei.itop.incidentmgnt.bean;

/**
 * @author Jack.Qi
 * 
 */
public class AdviserTaskQuantity {

	/**
	 * 顾问ID
	 */
	private Long adviserId;

	/**
	 * 任务量
	 */
	private Long taskQuantity;

	/**
	 * @return the adviserId
	 */
	public Long getAdviserId() {
		return adviserId;
	}

	/**
	 * @param adviserId
	 *            the adviserId to set
	 */
	public void setAdviserId(Long adviserId) {
		this.adviserId = adviserId;
	}

	/**
	 * @return the taskQuantity
	 */
	public Long getTaskQuantity() {
		return taskQuantity;
	}

	/**
	 * @param taskQuantity
	 *            the taskQuantity to set
	 */
	public void setTaskQuantity(Long taskQuantity) {
		this.taskQuantity = taskQuantity;
	}
}
