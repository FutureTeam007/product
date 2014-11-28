/**
 * 
 */
package com.ei.itop.scmgnt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, Long> opDAOCount;

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

	public List<ScOp> queryAllOp(long orgId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		return opDAO.findByParams("SC_OP.queryAllOpByOrgId", params);
	}

	public void modifyScOp(ScOp op) throws Exception {
		opDAO.update("SC_OP.updateByPrimaryKeySelective", op);
	}

	public long queryAllOpListCount(long orgId, String loginCode, String opName)
			throws Exception {
		// TODO Auto-generated method stub

		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", orgId);
		if (loginCode != null) {
			hm.put("loginCode", "%" + loginCode + "%");
		}
		if (opName != null) {
			hm.put("opName", "%" + opName + "%");
		}

		long rowCount = opDAOCount.find("SC_OP.queryAllOpCount", hm);

		return rowCount;
	}

	public List<ScOp> queryAllOpList(long orgId, String loginCode,
			String opName, long startIndex, int pageSize) throws Exception {
		// TODO Auto-generated method stub

		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", orgId);
		if (loginCode != null) {
			hm.put("loginCode", "%" + loginCode + "%");
		}
		if (opName != null) {
			hm.put("opName", "%" + opName + "%");
		}

		hm.put("startIndex", startIndex);
		List<ScOp> opList = null;
		// 不分页
		if (startIndex == -1) {
			opList = opDAO.findByParams("SC_OP.queryAllOp", hm);
		}
		// 分页
		else {
			long endIndex = startIndex + pageSize - 1;
			hm.put("endIndex", endIndex);

			opList = opDAO.findByParams("SC_OP.queryAllOpPaging", hm);
		}

		return opList;
	}

	public long addOp(ScOp op) throws Exception {
		// TODO Auto-generated method stub

		if (op.getLoginCode() == null || "".equals(op.getLoginCode())) {
			throw new BizException("登录工号不能为空");
		}

		if (loginCodeIsExist(op.getScOrgId(), op.getLoginCode())) {
			throw new BizException("登录工已存在");
		}

		if (op.getLoginPasswd() == null || "".equals(op.getLoginPasswd())) {
			throw new BizException("密码不能为空");
		}

		op.setLoginCode(op.getLoginCode().toLowerCase());

		long opId = opDAO.save("SC_OP.insert", op);

		return opId;
	}

	public void modifyOp(long opId, ScOp op) throws Exception {
		// TODO Auto-generated method stub

		if (op.getLoginCode() == null || "".equals(op.getLoginCode())) {
			throw new BizException("登录工号不能为空");
		}

		if (loginCodeIsExist(op.getLoginCode(), opId)) {
			throw new BizException("登录工已存在");
		}

		if (op.getLoginPasswd() == null || "".equals(op.getLoginPasswd())) {
			throw new BizException("密码不能为空");
		}

		op.setLoginCode(op.getLoginCode().toLowerCase());

		op.setScOpId(opId);

		opDAO.save("SC_OP.updateByPrimaryKeySelective", op);
	}

	public void modifyOpState(long opId, ScOp op) throws Exception {
		// TODO Auto-generated method stub

		ScOp param = new ScOp();

		param.setScOpId(opId);
		param.setState(op.getState());

		opDAO.save("SC_OP.updateByPrimaryKeySelective", param);
	}

	public boolean loginCodeIsExist(long orgId, String loginCode)
			throws Exception {
		// TODO Auto-generated method stub

		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", orgId);
		hm.put("loginCode", loginCode);

		List<ScOp> opList = opDAO.findByParams("SC_OP.checkLoginCode", hm);

		if (opList != null && opList.size() > 0) {
			return true;
		}

		return false;
	}

	public boolean loginCodeIsExist(String loginCode, Long excludeOpId)
			throws Exception {
		// TODO Auto-generated method stub

		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("loginCode", loginCode);
		hm.put("excludeOpId", excludeOpId);

		List<ScOp> opList = opDAO.findByParams("SC_OP.checkLoginCode", hm);

		if (opList != null && opList.size() > 0) {
			return true;
		}

		return false;
	}

}
