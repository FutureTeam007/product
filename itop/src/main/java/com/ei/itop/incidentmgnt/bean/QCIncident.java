/**
 * 
 */
package com.ei.itop.incidentmgnt.bean;

import java.util.Date;

/**
 * 用于查询事件的条件Bean
 * 
 * @author Jack.Qi
 * 
 */
public class QCIncident {

	// 商户ID
	private Long orgId;
	// 客户ID
	private Long custId;
	// 事件系列号
	private String incidentCode;
	// 事件简述
	private String brief;
	// 产品线
	private Long productId;
	// 事件影响度，可多选
	private String[] affectCode;
	// 事件分类，可多选
	private String[] classCode;
	// 事件优先级，可多选
	private String[] priorityCode;
	// 事件复杂度
	private String[] complexCode;
	// 事件状态
	private String[] stateCode;
	// 事件提出时间开始范围
	private Date registerTimeBegin;
	// 事件提出时间开始范围
	private Date registerTimeEnd;
	// 按客户登记时间排序，取值：desc、asc
	private String orderByRegisterTime;
	// 按最新更新时间排序，取值：desc、asc
	private String orderByLastModifyTime;

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

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
	 * @return the stateCode
	 */
	public String[] getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode
	 *            the stateCode to set
	 */
	public void setStateCode(String[] stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * @return the affectCode
	 */
	public String[] getAffectCode() {
		return affectCode;
	}

	/**
	 * @param affectCode
	 *            the affectCode to set
	 */
	public void setAffectCode(String[] affectCode) {
		this.affectCode = affectCode;
	}

	/**
	 * @return the classCode
	 */
	public String[] getClassCode() {
		return classCode;
	}

	/**
	 * @param classCode
	 *            the classCode to set
	 */
	public void setClassCode(String[] classCode) {
		this.classCode = classCode;
	}

	/**
	 * @return the priorityCode
	 */
	public String[] getPriorityCode() {
		return priorityCode;
	}

	/**
	 * @param priorityCode
	 *            the priorityCode to set
	 */
	public void setPriorityCode(String[] priorityCode) {
		this.priorityCode = priorityCode;
	}

	/**
	 * @return the complexCode
	 */
	public String[] getComplexCode() {
		return complexCode;
	}

	/**
	 * @param complexCode
	 *            the complexCode to set
	 */
	public void setComplexCode(String[] complexCode) {
		this.complexCode = complexCode;
	}

	/**
	 * @return the orderByLastModifyTime
	 */
	public String getOrderByLastModifyTime() {
		return orderByLastModifyTime;
	}

	/**
	 * @param orderByLastModifyTime
	 *            the orderByLastModifyTime to set
	 */
	public void setOrderByLastModifyTime(String orderByLastModifyTime) {
		this.orderByLastModifyTime = orderByLastModifyTime;
	}

	/**
	 * @return the orderByRegisterTime
	 */
	public String getOrderByRegisterTime() {
		return orderByRegisterTime;
	}

	/**
	 * @param orderByRegisterTime
	 *            the orderByRegisterTime to set
	 */
	public void setOrderByRegisterTime(String orderByRegisterTime) {
		this.orderByRegisterTime = orderByRegisterTime;
	}

	/**
	 * @return the incidentCode
	 */
	public String getIncidentCode() {
		return incidentCode;
	}

	/**
	 * @param incidentCode
	 *            the incidentCode to set
	 */
	public void setIncidentCode(String incidentCode) {
		this.incidentCode = incidentCode;
	}

	/**
	 * @return the brief
	 */
	public String getBrief() {
		return brief;
	}

	/**
	 * @param brief
	 *            the brief to set
	 */
	public void setBrief(String brief) {
		this.brief = brief;
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
	 * @return the registerTimeBegin
	 */
	public Date getRegisterTimeBegin() {
		return registerTimeBegin;
	}

	/**
	 * @param registerTimeBegin
	 *            the registerTimeBegin to set
	 */
	public void setRegisterTimeBegin(Date registerTimeBegin) {
		this.registerTimeBegin = registerTimeBegin;
	}

	/**
	 * @return the registerTimeEnd
	 */
	public Date getRegisterTimeEnd() {
		return registerTimeEnd;
	}

	/**
	 * @param registerTimeEnd
	 *            the registerTimeEnd to set
	 */
	public void setRegisterTimeEnd(Date registerTimeEnd) {
		this.registerTimeEnd = registerTimeEnd;
	}

}
