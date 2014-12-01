package com.ei.itop.custmgnt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.dbentity.CcCust;
import com.ei.itop.common.dbentity.CcCustProd;
import com.ei.itop.common.dbentity.CcCustProdOp;
import com.ei.itop.common.dbentity.ScJob;
import com.ei.itop.common.dbentity.ScOp;
import com.ei.itop.common.dbentity.ScProduct;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.custmgnt.bean.CustProductInfo;
import com.ei.itop.custmgnt.bean.CustProductOpInfo;
import com.ei.itop.custmgnt.service.CustMgntService;
import com.ei.itop.custmgnt.service.CustProductService;
import com.ei.itop.scmgnt.service.JobService;
import com.ei.itop.scmgnt.service.OpService;
import com.ei.itop.scmgnt.service.ProductService;

@Service("custProductService")
public class CustProductServiceImpl implements CustProductService{
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, CcCustProd> custProdDAO;
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, CustProductInfo> custProdInfoDAO;
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, CcCustProdOp> custProdOpDAO;
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, CustProductOpInfo> custProdInfoOpDAO;
	
	@Autowired
	CustMgntService custMgntService;
	
	@Autowired
	JobService jobService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OpService opService;
	
	public List<CustProductInfo> queryProductsServiceFor(long orgId, long custId) throws Exception {
		Map params = new HashMap();
		params.put("orgId", orgId);
		if(custId==-1){
			return null;
		}
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
		params.put("locale", SessionUtil.getLocale().toString());
		return custProdInfoDAO.findByParams("CC_CUST_PROD.selectProductsServiceFor", params);
	}

	public List<CustProductOpInfo> queryCustProductOpInfoList(long orgId,
			long custId, long productId) throws Exception {
		Map params = new HashMap();
		params.put("orgId", orgId);
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
		params.put("productId", productId);
		return custProdInfoOpDAO.findByParams("CC_CUST_PROD_OP.queryCustProdOpInfoList", params);
	}

	public void removeCustProduct(long orgId, long custId, long productId)
			throws Exception {
		//如果客户产品下顾问，则不允许删除
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", orgId);
		hm.put("custId", custId);
		hm.put("productId", productId);
		List<CcCustProdOp> result = custProdOpDAO.findByParams("CC_CUST_PROD_OP.queryAllCustProducts", hm);
		if(result!=null&&result.size()>0){
			throw new BizException(SessionUtil.getRequestContext().getMessage("i18n.custmgnt.prodinfo.ProductHasOpWhenDelete"));
		}else{
			custProdDAO.deleteByParams("CC_CUST_PROD.deleteCustProduct", hm);
		}
	}

	public void modifyCustProduct(CcCustProd ccp) throws Exception {
		custProdDAO.update("CC_CUST_PROD.updateByPrimaryKeySelective", ccp);
	}

	public void addCustProduct(CcCustProd ccp) throws Exception {
		CcCust cust = custMgntService.getCustInfo(ccp.getCcCustId());
		if(cust!=null){
			ccp.setCustName(cust.getCustName());
		}
		custProdDAO.save("CC_CUST_PROD.insert", ccp);
	}

	public void removeCustProductOp(long orgId, long custId, long productId,
			long opId) throws Exception {
		//如果客户产品下顾问，则不允许删除
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", orgId);
		hm.put("custId", custId);
		hm.put("productId", productId);
		hm.put("opId", opId);
		custProdOpDAO.deleteByParams("CC_CUST_PROD_OP.deleteCustProductOp", hm);
	}

	public void addCustProductOp(CcCustProdOp ccpo) throws Exception {
		//补全客户信息
		CcCust cust = custMgntService.getCustInfo(ccpo.getCcCustId());
		if(cust!=null){
			ccpo.setCustName(cust.getCustName());
		}
		//补全岗位信息
		ScJob job = jobService.getJob(ccpo.getScJobId(),SessionUtil.getLocale().toString());
		if(job!=null){
			ccpo.setJobClass(job.getJobClass());
			ccpo.setJobName(job.getJobName());
			ccpo.setJobLevel(job.getJobLevel());
		}
		//补全产品信息
		ScProduct product = productService.queryProductInfo(ccpo.getScProductId());
		if(product!=null){
			ccpo.setProdName(product.getProdName());
		}
		//补全顾问信息
		ScOp op = opService.queryScOp(ccpo.getScOpId());
		if(op!=null){
			ccpo.setLoginCode(op.getLoginCode());
		}
		custProdOpDAO.save("CC_CUST_PROD_OP.insert", ccpo);
	}

	public void modifyCustProductOp(CcCustProdOp ccpo) throws Exception {
		ScJob job = jobService.getJob(ccpo.getScJobId(),SessionUtil.getLocale().toString());
		if(job!=null){
			ccpo.setJobClass(job.getJobClass());
			ccpo.setJobName(job.getJobName());
			ccpo.setJobLevel(job.getJobLevel());
		}
		custProdOpDAO.update("CC_CUST_PROD_OP.updateByPrimaryKeySelective", ccpo);
	}
}
