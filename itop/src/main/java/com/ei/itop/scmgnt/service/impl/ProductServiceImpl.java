package com.ei.itop.scmgnt.service.impl;

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
import com.ei.itop.common.dbentity.IcIncident;
import com.ei.itop.common.dbentity.ScModule;
import com.ei.itop.common.dbentity.ScModuleI18n;
import com.ei.itop.common.dbentity.ScProduct;
import com.ei.itop.common.dbentity.ScProductI18n;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.custmgnt.service.CustMgntService;
import com.ei.itop.scmgnt.bean.ModuleInfo;
import com.ei.itop.scmgnt.bean.ProductInfo;
import com.ei.itop.scmgnt.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService{

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScProduct> productDAO;
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScProductI18n> productI18nDAO;
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, CcCustProd> custProdDAO;
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ProductInfo> productInfoDAO;
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScModule> moduleDAO;
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScModuleI18n> modulei18nDAO;
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ModuleInfo> moduleInfoDAO;
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, IcIncident> incidentDAO;
	
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

	public ScProduct queryProductInfo(long productId) throws Exception {
		return productDAO.find("SC_PRODUCT.selectByPrimaryKey", productId);
	}

	public List<ProductInfo> queryProductInfoList(long orgId) throws Exception {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", orgId);
		return productInfoDAO.findByParams("SC_PRODUCT.selectInfoListByOrgId", hm);
	}

	public void removeProduct(long productId) throws Exception {
		//删除前检查是否已授权给客户使用
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("productId", productId);
		List<CcCustProd> results = custProdDAO.findByParams("CC_CUST_PROD.selectCustProductsByProductId", hm);
		if(results!=null&&results.size()>0){
			throw new BizException(SessionUtil.getRequestContext().getMessage("i18n.scmgnt.prodinfo.ProductInUseError"));
		}
		productI18nDAO.delete("SC_PRODUCT_I18N.deleteByProductId", productId);
		productDAO.delete("SC_PRODUCT.deleteByPrimaryKey", productId);
	}

	public void addProduct(ScProduct product, List<ScProductI18n> i18ns)
			throws Exception {
		long productId = productDAO.save("SC_PRODUCT.insert", product);
		for(ScProductI18n i18n:i18ns){
			i18n.setScProductId(productId);
		}
		productI18nDAO.saveBatch("SC_PRODUCT_I18N.insert", i18ns);
	}

	public void modifyProduct(ScProduct product, List<ScProductI18n> i18ns)
			throws Exception {
		productDAO.update("SC_PRODUCT.updateByPrimaryKeySelective", product);
		productI18nDAO.updateBatch("SC_PRODUCT_I18N.update", i18ns);
	}

	public ModuleInfo queryModuleInfo(long moduleId) throws Exception {
		return moduleInfoDAO.find("SC_MODULE.selectInfoByPrimaryKey", moduleId);
	}

	public void removeModule(long moduleId) throws Exception {
		//检查服务目录是否已使用
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("moduleId", moduleId);
		List<IcIncident> results = incidentDAO.findByParams("IC_INCIDENT.queryIncidentByModuleId", hm);
		if(results!=null&&results.size()>0){
			throw new BizException(SessionUtil.getRequestContext().getMessage("i18n.scmgnt.prodinfo.ModuleInUseError"));
		}
		modulei18nDAO.delete("SC_MODULE_I18N.deleteByModuleId", moduleId);
		moduleDAO.delete("SC_MODULE.deleteByPrimaryKey", moduleId);
	}

	public void addModule(ScModule module, List<ScModuleI18n> i18ns)
			throws Exception {
		long moduleId = moduleDAO.save("SC_MODULE.insert", module);
		for(ScModuleI18n i18n:i18ns){
			i18n.setScModuleId(moduleId);
		}
		modulei18nDAO.saveBatch("SC_MODULE_I18N.insert", i18ns);
	}

	public void modifyModule(ScModule module, List<ScModuleI18n> i18ns)
			throws Exception {
		moduleDAO.update("SC_MODULE.updateByPrimaryKey", module);
		modulei18nDAO.updateBatch("SC_MODULE_I18N.update", i18ns);
	}

	public List<ScModule> queryAllModuleList(long prodcutId) throws Exception {
		Map params = new HashMap();
		params.put("productId", prodcutId);
		params.put("locale", SessionUtil.getLocale().toString());
		return moduleDAO.findByParams("SC_MODULE.selectByProductId", params);
	}
	
}
