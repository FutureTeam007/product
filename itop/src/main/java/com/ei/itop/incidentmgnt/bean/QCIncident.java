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

	// 事件系列号
	private String incidentCode;
	// 事件简述
	private String brief;
	// 产品线
	private Long productId;
	// 事件影响度，可多选
	private String[] affectVal;
	// 事件分类，可多选
	private String[] classVal;
	// 事件优先级，可多选
	private String[] priorityVal;
	// 按客户登记时间排序，取值：desc、asc
	private String orderByRegisterTime;
	// 按最新更新时间排序，取值：desc、asc
	private String orderByLastModifyTime;

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
	 * @return the affectVal
	 */
	public String[] getAffectVal() {
		return affectVal;
	}

	/**
	 * @param affectVal
	 *            the affectVal to set
	 */
	public void setAffectVal(String[] affectVal) {
		this.affectVal = affectVal;
	}

	/**
	 * @return the classVal
	 */
	public String[] getClassVal() {
		return classVal;
	}

	/**
	 * @param classVal
	 *            the classVal to set
	 */
	public void setClassVal(String[] classVal) {
		this.classVal = classVal;
	}

	/**
	 * @return the priorityVal
	 */
	public String[] getPriorityVal() {
		return priorityVal;
	}

	/**
	 * @param priorityVal
	 *            the priorityVal to set
	 */
	public void setPriorityVal(String[] priorityVal) {
		this.priorityVal = priorityVal;
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
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
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

	private String stateVal;
	private Long userId;
	private Date registerTimeBegin;
	private Date registerTimeEnd;
}
