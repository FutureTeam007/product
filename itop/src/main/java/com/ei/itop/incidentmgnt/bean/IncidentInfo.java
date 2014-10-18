/**
 * 
 */
package com.ei.itop.incidentmgnt.bean;

import java.util.List;

import com.ei.itop.common.dbentity.IcAttach;
import com.ei.itop.common.dbentity.IcIncident;

/**
 * 用于新增、编辑事件信息的业务Bean
 * 
 * @author Jack.Qi
 * 
 */
public class IncidentInfo extends IcIncident {

	// 附件列表
	private List<IcAttach> attachList;

	private String strHappenTime;

	/**
	 * @return the strHappenTime
	 */
	public String getStrHappenTime() {
		return strHappenTime;
	}

	/**
	 * @param strHappenTime
	 *            the strHappenTime to set
	 */
	public void setStrHappenTime(String strHappenTime) {
		this.strHappenTime = strHappenTime;
	}

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
