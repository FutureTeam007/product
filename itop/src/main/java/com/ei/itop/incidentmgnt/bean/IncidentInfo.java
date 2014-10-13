/**
 * 
 */
package com.ei.itop.incidentmgnt.bean;

import java.util.List;

import com.ei.itop.common.dbentity.IcAttach;
import com.ei.itop.common.dbentity.IcIncident;

/**
 * @author Jack.Qi
 * 
 */
public class IncidentInfo extends IcIncident {

	// 附件列表
	private List<IcAttach> attachList;

	/**
	 * @return the attachList
	 */
	public List<IcAttach> getAttachList() {
		return attachList;
	}

	/**
	 * @param attachList
	 *            the attachList to set
	 */
	public void setAttachList(List<IcAttach> attachList) {
		this.attachList = attachList;
	}
}
