/**
 * 
 */
package com.ei.itop.incidentmgnt.bean;

/**
 * @author Jack.Qi
 * 
 */
public class IncidentCountInfoByState {

	// 状态代码
	private String stateCode;
	// 状态值
	private String stateVal;
	// 记录数量
	private Long recordCount;

	/**
	 * @return the stateCode
	 */
	public String getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode
	 *            the stateCode to set
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * @return the stateVal
	 */
	public String getStateVal() {
		return stateVal;
	}

	/**
	 * @param stateVal
	 *            the stateVal to set
	 */
	public void setStateVal(String stateVal) {
		this.stateVal = stateVal;
	}

	/**
	 * @return the recordCount
	 */
	public Long getRecordCount() {
		return recordCount;
	}

	/**
	 * @param recordCount
	 *            the recordCount to set
	 */
	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
}
