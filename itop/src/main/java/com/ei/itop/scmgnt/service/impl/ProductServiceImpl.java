package com.ei.itop.scmgnt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ei.itop.common.dbentity.CcCust;
import com.ei.itop.common.dbentity.ScModule;
import com.ei.itop.common.dbentity.ScProduct;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.custmgnt.service.CustMgntService;
import com.ei.itop.scmgnt.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService{

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScProduct> productDAO;
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScModule> moduleDAO;
	
	@Autowired 
	CustMgntService custMgntService;
	
	public List<ScProduct> queryProductList(Long orgId,Long custId) throws Exception {
		Map params = new HashMap();
		params.put("orgId", orgId);
		if(custId!=null){
			//获得custId信息
			CcCust cust = custMgntService.getCustInfo(custId);
			//获得顶级cust
			CcCust topCust = custMgntService.getTopCustInfo(custId, cust.getDomainName());
			//通过顶级cust获得所有cust节点
			List<CcCust> custs = custMgntService.getSubCusts(topCust.getCcCustId());
			Long[] custIds = new Long[custs.size()];
			for(int i=0;i<custs.size();i++){
				custIds[i]=custs.get(i).getCcCustId();
			}
			params.put("custId", custIds);
		}
		params.put("locale", SessionUtil.getLocale().toString());
		return productDAO.findByParams("SC_PRODUCT.selectByOrgIdAndCustId", params);
	}

	public List<ScModule> queryModuleList(long orgId, long prodcutId)
			throws Exception {
		Map params = new HashMap();
		params.put("orgId", orgId);
		params.put("productId", prodcutId);
		params.put("locale", SessionUtil.getLocale().toString());
		return moduleDAO.findByParams("SC_MODULE.selectByOrgIdAndProductId", params);
	}
	
}
