/**
 * 
 */
package com.ei.itop.custmgnt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.AppContext;
import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.dbentity.CcCust;
import com.ei.itop.common.dbentity.CcCustProdOp;
import com.ei.itop.common.dbentity.CcSlo;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.custmgnt.bean.InChargeAdviser;
import com.ei.itop.custmgnt.service.CustMgntService;

/**
 * @author Jack.Qi
 * 
 */
@Service("custMgntService")
public class CustMgntServiceImpl implements CustMgntService {

	private static final Logger log = Logger
			.getLogger(CustMgntServiceImpl.class);

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, CcCustProdOp> custProdOpDAO;

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, CcCust> custDAO;

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, InChargeAdviser> inChargeAdviserDAO;

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, CcSlo> ccSloDAO;

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, Long> inChargeAdviserCountDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.custmgnt.service.CustMgntService#getCustProdOpInfo(long,
	 * long, long, long)
	 */
	public CcCustProdOp getCustProdOpInfo(long orgId, long custId,
			long productId, long opId) throws Exception {
		// TODO Auto-generated method stub

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", orgId);
		hm.put("custId", custId);
		hm.put("productId", productId);
		hm.put("opId", opId);

		CcCustProdOp custProdOp = custProdOpDAO.find(
				"CC_CUST_PROD_OP.queryCustProdOpInfo", hm);

		return custProdOp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.custmgnt.service.CustMgntService#getCustProdOpList(long,
	 * long, long)
	 */
	public List<CcCustProdOp> getCustProdOpList(long orgId, long custId,
			long productId) throws Exception {
		// TODO Auto-generated method stub

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", orgId);
		hm.put("custId", custId);
		hm.put("productId", productId);

		List<CcCustProdOp> custProdOpList = custProdOpDAO.findByParams(
				"CC_CUST_PROD_OP.queryCustProdOpList", hm);

		return custProdOpList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.custmgnt.service.CustMgntService#getCustInfo(long)
	 */
	public CcCust getCustInfo(long custId) throws Exception {
		// TODO Auto-generated method stub

		CcCust cust = custDAO.find("CC_CUST.selectByPrimaryKey", custId);

		return cust;
	}

	public CcCust getTopCustInfo(long custId, String domainName)
			throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("custId", custId);
		hm.put("domainName", domainName);
		CcCust cust = custDAO.find("CC_CUST.selectTopCustByCustId", hm);
		return cust;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.custmgnt.service.CustMgntService#queryInChargeAdviser(java
	 * .lang.Long, java.lang.Long, java.lang.String)
	 */
	public List<InChargeAdviser> queryInChargeAdviser(Long custId,
			Long productId, String adviserName, Long currentAdviserId,
			long startIndex, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("custId", custId);
		hm.put("productId", productId);
		hm.put("currentAdviserId", currentAdviserId);
		String conditionAdviserName = adviserName == null ? "" : adviserName;
		hm.put("adviserName", "%" + conditionAdviserName + "%");
		hm.put("startIndex", startIndex);

		List<InChargeAdviser> list = null;

		// 不分页
		if (startIndex == -1) {
			list = inChargeAdviserDAO.findByParams(
					"CC_CUST_PROD_OP.queryInChargeAdviser", hm);
		}
		// 分页
		else {
			long endIndex = startIndex + pageSize - 1;
			hm.put("endIndex", endIndex);

			list = inChargeAdviserDAO.findByParams(
					"CC_CUST_PROD_OP.queryInChargeAdviserPaging", hm);
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.custmgnt.service.CustMgntService#queryInChargeAdviserCount
	 * (java.lang.Long, java.lang.Long, java.lang.String)
	 */
	public long queryInChargeAdviserCount(Long custId, Long productId,
			String adviserName, Long currentAdviserId) throws Exception {
		// TODO Auto-generated method stub

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("custId", custId);
		hm.put("productId", productId);
		hm.put("currentAdviserId", currentAdviserId);
		String conditionAdviserName = adviserName == null ? "" : adviserName;
		hm.put("adviserName", "%" + conditionAdviserName + "%");

		long rowCount = inChargeAdviserCountDAO.find(
				"CC_CUST_PROD_OP.queryInChargeAdviserCount", hm);

		return rowCount;
	}

	public static void main(String[] args) throws Exception {
		CustMgntService cms = (CustMgntService) AppContext
				.getBean("custMgntService");

		List<InChargeAdviser> list = null;

		// 不分页
		list = cms.queryInChargeAdviser(new Long(300001), new Long(102), "",
				200001l, -1, 2);
		log.debug("list.size() is " + list.size());
		for (int i = 0; list != null && i < list.size(); i++) {
			log.debug(list.get(i).getCustId() + "," + list.get(i).getCustName());
		}

		// 分页
		list = cms.queryInChargeAdviser(new Long(300001), new Long(102), "",
				200001l, 1, 2);
		log.debug("list.size() is " + list.size());
		for (int i = 0; list != null && i < list.size(); i++) {
			log.debug(list.get(i).getCustId() + "," + list.get(i).getCustName());
		}

		long cnt = cms.queryInChargeAdviserCount(new Long(300001),
				new Long(102), "PM", 200001l);
		log.debug(cnt);
	}

	public List<CcCust> queryCustListByDomainName(String domainName)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("domainName", domainName);
		return custDAO
				.findByParams("CC_CUST.queryCustListByDomainName", params);
	}

	public List<CcSlo> querySloRules(long orgId, long custId, long productId,
			String priorityCode, String complexCode) throws Exception {

		List<CcSlo> result = null;

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("priorityCode", priorityCode);
		params.put("complexCode", complexCode);

		// 查找产品ID、客户ID、商户ID均存在的SLO
		params.put("orgId", orgId);
		params.put("custId", custId);
		params.put("productId", productId);
		result = ccSloDAO.findByParams("CC_SLO.querySloRules", params);
		if (result != null) {
			return result;
		}

		// 查找客户ID、商户ID存在的SLO
		params.remove("productId");
		params.put("productId", -1);
		result = ccSloDAO.findByParams("CC_SLO.querySloRules", params);
		if (result != null) {
			return result;
		}

		// 查找商户ID存在的SLO
		params.remove("productId");
		params.remove("custId");
		params.put("productId", -1);
		params.put("custId", -1);
		result = ccSloDAO.findByParams("CC_SLO.querySloRules", params);
		if (result != null) {
			return result;
		}

		// 查找系统默认SLO
		params.remove("productId");
		params.remove("custId");
		params.remove("orgId");
		params.put("productId", -1);
		params.put("custId", -1);
		params.put("orgId", -1);
		result = ccSloDAO.findByParams("CC_SLO.querySloRules", params);
		// 系统默认SLO不允许为空
		if (result == null) {
			throw new BizException(SessionUtil.getRequestContext().getMessage(
					"i18n.custmgnt.query.DefaultSloRulesNotExist"));
			// "系统默认SLO未设置");
		}

		return result;
	}

	public List<CcCust> getSubCusts(long custId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ccCustId", custId);
		List<CcCust> custs = custDAO.findByParams(
				"CC_CUST.selectSubCustsByCustId", params);
		return custs;
	}
}
