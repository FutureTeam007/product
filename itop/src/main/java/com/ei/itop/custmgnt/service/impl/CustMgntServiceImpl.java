/**
 * 
 */
package com.ei.itop.custmgnt.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.dbentity.CcCustProdOp;
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
				"CC_CUST_PROD_OP.queryCustProdOpInfo", hm);

		return custProdOpList;
	}

}
