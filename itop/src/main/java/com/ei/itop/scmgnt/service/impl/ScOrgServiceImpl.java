package com.ei.itop.scmgnt.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ei.itop.common.dbentity.ScOrg;
import com.ei.itop.common.dbentity.ScParam;
import com.ei.itop.scmgnt.bean.SloTime;
import com.ei.itop.scmgnt.service.ScOrgService;

@Service("scOrgService")
public class ScOrgServiceImpl implements ScOrgService {

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScOrg> orgDAO;

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScParam> paramDAO;

	public ScOrg queryScOrg(long orgId) throws Exception {
		return orgDAO.find("SC_ORG.selectByPrimaryKey", orgId);
	}

	public SloTime querySloTimeConfig(long orgId) throws Exception {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", orgId);
		hm.put("paramKindCode", "IC_SLO_FLAG");
		List<ScParam> params = paramDAO.findByParams("SC_PARAM.querySloParam", hm);
		ScParam param = params.get(0);
		SloTime slo = new SloTime();
		slo.setTimeType(VarTypeConvertUtils.string2Integer(
				param.getParamCode(), -1));
		String[] times = param.getParamValue().split(";");
		String[] timeAm = times[0].split("~");
		String[] timePm = times[1].split("~");
		slo.setWorkStartTimeAm(timeAm[0].substring(0, 2) + ":"
				+ timeAm[0].substring(2, 4));
		slo.setWorkEndTimeAm(timeAm[1].substring(0, 2) + ":"
				+ timeAm[1].substring(2, 4));
		slo.setWorkStartTimePm(timePm[0].substring(0, 2) + ":"
				+ timePm[0].substring(2, 4));
		slo.setWorkEndTimePm(timePm[1].substring(0, 2) + ":"
				+ timePm[1].substring(2, 4));
		slo.setParamId(param.getScParamId());
		return slo;
	}

	public void modifySloTimeConfig(SloTime sloTime)
			throws Exception {
		String paramValue = sloTime.getWorkStartTimeAm().replaceAll(":", "")
				+ "~" + sloTime.getWorkEndTimeAm().replaceAll(":", "") + ";"
				+ sloTime.getWorkStartTimePm().replaceAll(":", "") + "~"
				+ sloTime.getWorkEndTimePm().replaceAll(":", "");
		ScParam param = new ScParam();
		param.setScParamId(sloTime.getParamId());
		param.setParamCode(sloTime.getTimeType()+"");
		param.setParamValue(paramValue);
		paramDAO.update("SC_PARAM.updateByPrimaryKeySelective", param);
	}
}
