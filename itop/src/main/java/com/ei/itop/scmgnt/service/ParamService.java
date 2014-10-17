/**
 * 
 */
package com.ei.itop.scmgnt.service;

import java.util.List;

import com.ei.itop.common.dbentity.ScParam;

/**
 * @author Jack.Qi
 * 
 */
public interface ParamService {

	public ScParam getParam(long orgId, String paramKindCode, String paramCode)
			throws Exception;

	public List<ScParam> getParamList(long orgId, String paramKindCode)
			throws Exception;
}
