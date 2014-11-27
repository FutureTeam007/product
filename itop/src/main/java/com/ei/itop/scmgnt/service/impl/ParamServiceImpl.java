/**
 * 
 */
package com.ei.itop.scmgnt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ailk.dazzle.util.type.DateUtils;
import com.ei.itop.common.bean.WorkPeriod;
import com.ei.itop.common.dbentity.ScParam;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.scmgnt.service.ParamService;

/**
 * @author Jack.Qi
 * 
 */
@Service("paramService")
public class ParamServiceImpl implements ParamService {

	private static final Logger log = Logger.getLogger(ParamServiceImpl.class);

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScParam> paramDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.scmgnt.service.ParamService#getWorkPeriodsOfDate(com.ei.itop
	 * .common.dbentity.ScParam)
	 */
	public List<WorkPeriod> getWorkPeriodsOfDate(ScParam paramSLOFlag)
			throws Exception {
		// TODO Auto-generated method stub

		List<WorkPeriod> rtnValue = new ArrayList<WorkPeriod>();

		String strSloFlag = paramSLOFlag.getParamValue();

		String[] strPeriods = strSloFlag.split(";");

		for (int i = 0; strPeriods != null && i < strPeriods.length; i++) {
			String strPeriod = strPeriods[i];
			String[] period = strPeriod.split("~");
			WorkPeriod wp = new WorkPeriod();
			Date beginTime = DateUtils.string2Date("20010101" + period[0]
					+ "00", DateUtils.FORMATTYPE_yyyyMMddHHmmss);
			wp.setBeginTime(beginTime);
			Date endTime = DateUtils.string2Date("20010101" + period[1] + "00",
					DateUtils.FORMATTYPE_yyyyMMddHHmmss);
			wp.setEndTime(endTime);
			rtnValue.add(wp);
		}

		return rtnValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.scmgnt.service.ParamService#getParam(long,
	 * java.lang.String, java.lang.String)
	 */
	public ScParam getParam(long orgId, String paramKindCode, String paramCode)
			throws Exception {
		// TODO Auto-generated method stub

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", orgId);
		hm.put("paramKindCode", paramKindCode);
		hm.put("paramCode", paramCode);
		hm.put("locale", SessionUtil.getLocale().toString());

		ScParam param = paramDAO.find("SC_PARAM.queryParam", hm);

		return param;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.scmgnt.service.ParamService#getParam(long,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	public ScParam getParam(long orgId, String paramKindCode, String paramCode,
			String locale) throws Exception {
		// TODO Auto-generated method stub

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", orgId);
		hm.put("paramKindCode", paramKindCode);
		hm.put("paramCode", paramCode);
		hm.put("locale", locale);

		ScParam param = paramDAO.find("SC_PARAM.queryParam", hm);

		return param;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.scmgnt.service.ParamService#getParamList(long,
	 * java.lang.String)
	 */
	public List<ScParam> getParamList(long orgId, String paramKindCode)
			throws Exception {
		// TODO Auto-generated method stub

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", orgId);
		hm.put("paramKindCode", paramKindCode);
		hm.put("locale", SessionUtil.getLocale().toString());

		List<ScParam> paramList = paramDAO.findByParams(
				"SC_PARAM.queryParamList", hm);

		return paramList;
	}

	public List<ScParam> getParamDistinct(long orgId) throws Exception {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", orgId);
		hm.put("locale", SessionUtil.getLocale().toString());
		List<ScParam> paramList = paramDAO.findByParams(
				"SC_PARAM.queryParamDistinct", hm);
		return paramList;
	}

	public List<ScParam> getParamValues(long orgId, String kindCode,
			String locale) throws Exception {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", orgId);
		hm.put("kindCode", kindCode);
		hm.put("locale", locale);
		List<ScParam> paramList = paramDAO.findByParams(
				"SC_PARAM.queryParamValues", hm);
		return paramList;
	}

	public void addParams(List<ScParam> params) throws Exception {
		paramDAO.saveBatch("SC_PARAM.insert", params);
	}

	public void modifyParams(List<ScParam> params) throws Exception {
		paramDAO.updateBatch("SC_PARAM.updateByPrimaryKeySelective", params);
	}

	public void removeParams(List<Long> paramIds) throws Exception {
		paramDAO.deleteBatch("SC_PARAM.deleteByPrimaryKey", paramIds);
	}

}
