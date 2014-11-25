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

	public List<ScParam> getParamDistinct(long orgId) throws Exception;

	public List<ScParam> getParamValues(long orgId, String kindCode,
			String locale) throws Exception;
	
	public void addParams(List<ScParam> params) throws Exception;
	
	public void modifyParams(List<ScParam> params) throws Exception;
	
	public void removeParams(List<Long> paramIds) throws Exception;
}
