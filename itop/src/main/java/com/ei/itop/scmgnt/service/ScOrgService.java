/**
 * 
 */
package com.ei.itop.scmgnt.service;

import com.ei.itop.common.dbentity.ScOrg;

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
}
