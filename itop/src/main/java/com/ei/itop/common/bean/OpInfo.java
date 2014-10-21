/**
 * 
 */
package com.ei.itop.common.bean;

/**
 * @author Jack.Qi
 * 
 */
public class OpInfo {

	// 操作员类别，取值：USER-用户，OP-顾问
	private String opType;

	// 操作员ID
	private long opId;

	// 操作员登录账号
	private String opCode;

	// 操作员姓名
	private String opName;
	
	// 操作员全名(中文名/英文名.英文姓)
	private String opFullName;
	
	// 商户ID
	private long orgId;
	
	// 商户名称
	private String orgName;

	/**
	 * @return the opType
	 */
	public String getOpType() {
		return opType;
	}

	/**
	 * @param opType
	 *            the opType to set
	 */
	public void setOpType(String opType) {
		this.opType = opType;
	}

	/**
	 * @return the opId
	 */
	public long getOpId() {
		return opId;
	}

	/**
	 * @param opId
	 *            the opId to set
	 */
	public void setOpId(long opId) {
		this.opId = opId;
	}

	/**
	 * @return the opCode
	 */
	public String getOpCode() {
		return opCode;
	}

	/**
	 * @param opCode
	 *            the opCode to set
	 */
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	/**
	 * @return the opName
	 */
	public String getOpName() {
		return opName;
	}

	/**
	 * @param opName
	 *            the opName to set
	 */
	public void setOpName(String opName) {
		this.opName = opName;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOpFullName() {
		return opFullName;
	}

	public void setOpFullName(String opFullName) {
		this.opFullName = opFullName;
	}
	
}
