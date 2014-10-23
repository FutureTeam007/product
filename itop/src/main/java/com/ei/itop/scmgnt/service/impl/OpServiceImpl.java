/**
 * 
 */
package com.ei.itop.scmgnt.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.dbentity.ScOp;
import com.ei.itop.scmgnt.service.OpService;

/**
 * @author Jack.Qi
 * 
 */
@Service("opService")
public class OpServiceImpl implements OpService {

	private static final Logger log = Logger.getLogger(OpServiceImpl.class);

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScOp> opDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.scmgnt.service.OpService#queryScOp(long)
	 */
	public ScOp queryScOp(long opId) throws Exception {
		// TODO Auto-generated method stub

		ScOp op = opDAO.find("SC_OP.selectByPrimaryKey", opId);

		return op;
	}

	public void changeLoginPasswd(long opId, String passwd) throws Exception {
		
		if (passwd == null || passwd.length() == 0) {
			throw new BizException("密码不能为空");
		}
		
		ScOp so = new ScOp();
		so.setScOpId(opId);
		so.setLoginPasswd(passwd);

		opDAO.update("SC_OP.updateByPrimaryKeySelective", so);
		
	}

}
