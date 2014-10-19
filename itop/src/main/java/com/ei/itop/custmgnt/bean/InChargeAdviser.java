/**
 * 
 */
package com.ei.itop.custmgnt.bean;

import com.ei.itop.common.dbentity.ScOp;

/**
 * @author Jack.Qi
 * 
 */
public class InChargeAdviser extends ScOp {

	/**
	 * 客户ID
	 */
	private Long custId;
	/**
	 * 客户名称
	 */
	private String custName;
	/**
	 * 产品线ID
	 */
	private Long productId;
	/**
	 * 产品线名称
	 */
	private String productName;
	/**
	 * 岗位ID
	 */
	private Long jobId;
	/**
	 * 岗位名称
	 */
	private String jobName;
	/**
	 * 岗位类别
	 */
	private String jobClass;
	/**
	 * 岗位级别
	 */
	private String jobLevel;

	/**
	 * @return the custId
	 */
	public Long getCustId() {
		return custId;
	}

	/**
	 * @param custId
	 *            the custId to set
	 */
	public void setCustId(Long custId) {
		this.custId = custId;
	}

	/**
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * @param custName
	 *            the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName
	 *            the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the jobId
	 */
	public Long getJobId() {
		return jobId;
	}

	/**
	 * @param jobId
	 *            the jobId to set
	 */
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @param jobName
	 *            the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @return the jobClass
	 */
	public String getJobClass() {
		return jobClass;
	}

	/**
	 * @param jobClass
	 *            the jobClass to set
	 */
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	/**
	 * @return the jobLevel
	 */
	public String getJobLevel() {
		return jobLevel;
	}

	/**
	 * @param jobLevel
	 *            the jobLevel to set
	 */
	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}
}
