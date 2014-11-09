package com.ei.itop.scmgnt.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.dbentity.ScOrg;
import com.ei.itop.scmgnt.service.ScOrgService;

@Service("scOrgService")
public class ScOrgServiceImpl implements ScOrgService{

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScOrg> orgDAO;
	
	public ScOrg queryScOrg(long orgId) throws Exception {
		return orgDAO.find("SC_ORG.selectByPrimaryKey", orgId);
	}

}
