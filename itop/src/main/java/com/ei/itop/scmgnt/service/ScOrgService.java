/**
 * 
 */
package com.ei.itop.scmgnt.service;

import com.ei.itop.common.dbentity.ScOrg;
import com.ei.itop.scmgnt.bean.SloTime;

/**
 * @author Jack.Qi
 * 
 */
public interface ScOrgService {

	/**
	 * 查询商户信息
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	public ScOrg queryScOrg(long orgId) throws Exception; 
	/**
	 * 查询商户默认的SLO时间设置
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	public SloTime querySloTimeConfig(long orgId) throws Exception; 
	/**
	 * 修改商户默认的SLO时间设置
	 * @return
	 * @throws Exception
	 */
	public void modifySloTimeConfig(SloTime sloTime) throws Exception; 
}
