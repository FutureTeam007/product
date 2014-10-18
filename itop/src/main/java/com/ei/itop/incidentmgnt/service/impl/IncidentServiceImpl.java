/**
 * 
 */
package com.ei.itop.incidentmgnt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ailk.dazzle.util.AppContext;
import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ailk.dazzle.util.type.DateUtils;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.dao.CommonDAO;
import com.ei.itop.common.dbentity.CcCust;
import com.ei.itop.common.dbentity.CcCustProdOp;
import com.ei.itop.common.dbentity.CcUser;
import com.ei.itop.common.dbentity.IcAttach;
import com.ei.itop.common.dbentity.IcIncident;
import com.ei.itop.common.dbentity.ScOp;
import com.ei.itop.common.dbentity.ScParam;
import com.ei.itop.custmgnt.service.CustMgntService;
import com.ei.itop.custmgnt.service.UserService;
import com.ei.itop.incidentmgnt.bean.AdviserTaskQuantity;
import com.ei.itop.incidentmgnt.bean.IncidentCountInfoByState;
import com.ei.itop.incidentmgnt.bean.IncidentInfo;
import com.ei.itop.incidentmgnt.bean.QCIncident;
import com.ei.itop.incidentmgnt.bean.TransactionInfo;
import com.ei.itop.incidentmgnt.service.AttachService;
import com.ei.itop.incidentmgnt.service.IncidentService;
import com.ei.itop.incidentmgnt.service.TransactionService;
import com.ei.itop.scmgnt.service.OpService;
import com.ei.itop.scmgnt.service.ParamService;

/**
 * @author Jack.Qi
 * 
 */
@Service(value = "incidentService")
public class IncidentServiceImpl implements IncidentService {

	private static final Logger log = Logger
			.getLogger(IncidentServiceImpl.class);

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, IcIncident> incidentDAO;

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, Long> incidentDAOCount;

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, IncidentCountInfoByState> incidentCountInfoByStateDAO;

	@Resource(name = "commonDDLDAO")
	private CommonDAO commonDAO;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "attachService")
	private AttachService attachService;

	@Resource(name = "transactionService")
	private TransactionService transactionService;

	@Resource(name = "custMgntService")
	private CustMgntService custMgntService;

	@Resource(name = "paramService")
	private ParamService paramService;

	@Resource(name = "opService")
	private OpService opService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#queryIncident(com.ei
	 * .itop.incidentmgnt.bean.QCIncident, long, int, long)
	 */
	public List<IcIncident> MBLQueryIncident(QCIncident qcIncident,
			long startIndex, int pageSize, OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("incidentCode", qcIncident.getIncidentCode());
		hm.put("brief", qcIncident.getBrief());
		hm.put("productId", qcIncident.getProductId());
		hm.put("affectCode", qcIncident.getAffectCode());
		hm.put("classCode", qcIncident.getClassCode());
		hm.put("priorityCode", qcIncident.getPriorityCode());
		hm.put("complexCode", qcIncident.getComplexCode());
		hm.put("stateCode", qcIncident.getStateCode());
		hm.put("registerTimeBegin", qcIncident.getRegisterTimeBegin());
		hm.put("registerTimeEnd", qcIncident.getRegisterTimeEnd());

		if (!"desc".equals(qcIncident.getOrderByRegisterTime())
				&& !"asc".equals(qcIncident.getOrderByRegisterTime())) {
			qcIncident.setOrderByRegisterTime("desc");
		}
		if (!"desc".equals(qcIncident.getOrderByLastModifyTime())
				&& !"asc".equals(qcIncident.getOrderByLastModifyTime())) {
			qcIncident.setOrderByLastModifyTime("desc");
		}

		hm.put("orderBy", "registe_time " + qcIncident.getOrderByRegisterTime()
				+ ", modify_date " + qcIncident.getOrderByLastModifyTime());
		hm.put("startIndex", startIndex);

		List<IcIncident> incidentList = null;

		// 不分页
		if (startIndex == -1) {
			incidentList = incidentDAO.findByParams(
					"IC_INCIDENT.queryIncident", hm);
		}
		// 分页
		else {
			long endIndex = startIndex + pageSize - 1;
			hm.put("endIndex", endIndex);

			incidentList = incidentDAO.findByParams(
					"IC_INCIDENT.queryIncidentPaging", hm);
		}

		return incidentList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#queryIncidentCount(
	 * com.ei.itop.incidentmgnt.bean.QCIncident, long)
	 */
	public long MBLQueryIncidentCount(QCIncident qcIncident, OpInfo opInfo)
			throws Exception {
		// TODO Auto-generated method stub

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("incidentCode", qcIncident.getIncidentCode());
		hm.put("brief", qcIncident.getBrief());
		hm.put("productId", qcIncident.getProductId());
		hm.put("affectCode", qcIncident.getAffectCode());
		hm.put("classCode", qcIncident.getClassCode());
		hm.put("priorityCode", qcIncident.getPriorityCode());
		hm.put("complexCode", qcIncident.getComplexCode());
		hm.put("stateCode", qcIncident.getStateCode());
		hm.put("registerTimeBegin", qcIncident.getRegisterTimeBegin());
		hm.put("registerTimeEnd", qcIncident.getRegisterTimeEnd());

		long rowCount = incidentDAOCount.find("IC_INCIDENT.queryIncidentCount",
				hm);

		return rowCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.incidentmgnt.service.IncidentService#
	 * MBLQueryIncidentCountGroupByState
	 * (com.ei.itop.incidentmgnt.bean.QCIncident,
	 * com.ei.itop.common.bean.OpInfo)
	 */
	public List<IncidentCountInfoByState> MBLQueryIncidentCountGroupByState(
			QCIncident qcIncident, long orgId, OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("incidentCode", qcIncident.getIncidentCode());
		hm.put("brief", qcIncident.getBrief());
		hm.put("productId", qcIncident.getProductId());
		hm.put("affectCode", qcIncident.getAffectCode());
		hm.put("classCode", qcIncident.getClassCode());
		hm.put("priorityCode", qcIncident.getPriorityCode());
		hm.put("complexCode", qcIncident.getComplexCode());
		hm.put("stateCode", qcIncident.getStateCode());
		hm.put("registerTimeBegin", qcIncident.getRegisterTimeBegin());
		hm.put("registerTimeEnd", qcIncident.getRegisterTimeEnd());

		// 得到状态定义表所有状态定义
		List<ScParam> paramList = paramService.getParamList(orgId, "IC_STATE");

		if (paramList == null || paramList.size() == 0) {
			throw new Exception("参数表尚未定义该商户的事件状态");
		}

		// 查询事件表所有状态分组数量信息
		List<IncidentCountInfoByState> list = incidentCountInfoByStateDAO
				.findByParams("IC_INCIDENT.queryIncidentCountGroupByState", hm);

		List<IncidentCountInfoByState> resultList = new ArrayList<IncidentCountInfoByState>();

		// 遍历所有已定义事件状态
		// 总数量
		long recordCount = 0;
		for (int i = 0; paramList != null && i < paramList.size(); i++) {
			ScParam param = paramList.get(i);
			IncidentCountInfoByState result = new IncidentCountInfoByState();
			result.setStateCode(param.getParamCode());
			result.setStateVal(param.getParamValue());
			result.setRecordCount(new Long(0));
			// 匹配是否有数量
			for (int j = 0; list != null && j < list.size(); j++) {
				IncidentCountInfoByState item = list.get(j);
				if (item.getStateCode().equals(param.getParamCode())) {
					result.setRecordCount(item.getRecordCount());
					recordCount += item.getRecordCount();
					break;
				}
			}
			resultList.add(result);
			log.debug(result.getStateCode() + "," + result.getStateVal() + ","
					+ result.getRecordCount());
		}

		// 生成全部状态的数量记录
		IncidentCountInfoByState all = new IncidentCountInfoByState();
		all.setStateCode("-1");
		all.setStateVal("全部");
		all.setRecordCount(recordCount);

		// 把全部信息加入result
		resultList.add(all);

		return resultList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#queryIncident(long,
	 * long)
	 */
	public IcIncident MBLQueryIncident(long incidentId, OpInfo opInfo)
			throws Exception {
		// TODO Auto-generated method stub

		IcIncident incident = queryIncident(incidentId);

		// 记录系统操作日志

		return incident;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#queryIncident(long)
	 */
	public IcIncident queryIncident(long incidentId) throws Exception {
		// TODO Auto-generated method stub

		IcIncident incident = incidentDAO.find(
				"IC_INCIDENT.selectByPrimaryKey", incidentId);

		return incident;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#addIncident(com.ei.
	 * itop.incidentmgnt.bean.IncidentInfo, long)
	 */
	public long MBLAddIncidentAndAttach(IncidentInfo incidentInfo, OpInfo opInfo)
			throws Exception {
		// TODO Auto-generated method stub

		// 设置事件状态
		incidentInfo.setItStateCode("1");
		incidentInfo.setItStateVal("待提交");

		// // 自动填入商户信息、客户信息
		CcUser user = userService.queryUser(incidentInfo.getIcOwnerId());
		incidentInfo.setScOrgId(user.getScOrgId());
		incidentInfo.setScOrgName(user.getScOrgName());
		incidentInfo.setCcCustId(user.getCcCustId());
		incidentInfo.setCustName(user.getCustName());

		// 保存事件信息
		long incidentId = addIncidentAndAttach(incidentInfo, opInfo);

		// 记录系统操作日志

		return incidentId;
	}

	/**
	 * 新增事件，此逻辑为原子逻辑，不会触发记录系统操作日志
	 * 
	 * @param incidentInfo
	 * @return
	 * @throws Exception
	 */
	protected long addIncidentAndAttach(IncidentInfo incidentInfo, OpInfo opInfo)
			throws Exception {
		// TODO Auto-generated method stub

		// IncidentInfo ii = new IncidentInfo();

		// 业务信息
		// ii.setScOrgId(incidentInfo.getScOrgId());
		// ii.setScOrgName(incidentInfo.getScOrgName());
		// ii.setCcCustId(incidentInfo.getCcCustId());
		// ii.setCustName(incidentInfo.getCustName());
		//
		// ii.setScProductId(incidentInfo.getScProductId());
		// ii.setProdName(incidentInfo.getProdName());
		// ii.setScModuleId(incidentInfo.getScModuleId());
		// ii.setModuleName(incidentInfo.getModuleName());
		// ii.setAffectCodeUser(incidentInfo.getAffectCodeUser());
		// ii.setAffectValUser(incidentInfo.getAffectValUser());
		// ii.setClassCodeUser(incidentInfo.getClassCodeUser());
		// ii.setClassValUser(incidentInfo.getClassValUser());
		// ii.setBrief(incidentInfo.getBrief());
		// ii.setHappenTime(incidentInfo.getHappenTime());
		// ii.setDetail(incidentInfo.getDetail());
		// ii.setCcList(incidentInfo.getCcList());

		// // 自动填入商户信息、客户信息
		// CcUser user = userService.queryUser(incidentInfo.getIcOwnerId());
		// incidentInfo.setScOrgId(user.getScOrgId());
		// incidentInfo.setScOrgName(user.getScOrgName());
		// incidentInfo.setCcCustId(user.getCcCustId());
		// incidentInfo.setCustName(user.getCustName());

		// 自动填入事件系列号
		incidentInfo.setIncidentCode(generateIncidentCode(incidentInfo
				.getCcCustId()));

		// 自动填入事件提出用户、创建人
		incidentInfo.setPlObjectType(opInfo.getOpType());
		incidentInfo.setPlObjectId(opInfo.getOpId());
		incidentInfo.setPlLoginCode(opInfo.getOpCode());
		incidentInfo.setPlObjectName(opInfo.getOpName());

		// 保存事件实体信息
		long incidentId = incidentDAO.save("IC_INCIDENT.insert", incidentInfo);

		// 保存附件信息
		attachService.saveAttach(incidentId, incidentInfo.getAttachList());

		return incidentId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#MBLModifyIncidentAndAttach
	 * (long, com.ei.itop.incidentmgnt.bean.IncidentInfo, long)
	 */
	public long MBLModifyIncidentAndAttach(long incidentId,
			IncidentInfo incidentInfo, OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub

		// 保存事件实体信息
		modifyIncidentAndAttach(incidentId, incidentInfo, opInfo);

		// 记录系统操作日志

		return incidentId;
	}

	/**
	 * 查询某个客户当月的最新事件记录，注意是当月
	 * 
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	protected IcIncident getLastIncidentByCustId(long custId) throws Exception {
		// TODO Auto-generated method stub

		IcIncident rtnValue = null;

		// 取得当前时间
		Date currentDate = commonDAO.getSysDate();
		String strCurrentMonthFirstDay = DateUtils.date2String(currentDate,
				DateUtils.FORMATTYPE_yyyy_MM_dd);
		strCurrentMonthFirstDay = strCurrentMonthFirstDay.substring(0,
				strCurrentMonthFirstDay.length() - 2);
		strCurrentMonthFirstDay += "01 00:00:00";
		Date currentMonthFirstDay = DateUtils.string2Date(
				strCurrentMonthFirstDay,
				DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss);

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("custId", custId);
		hm.put("currentMonthFirstDay", currentMonthFirstDay);

		List<IcIncident> list = incidentDAO.findByParams(
				"IC_INCIDENT.queryIncidentByCustId", hm);

		if (list != null && list.size() > 0) {
			rtnValue = list.get(0);
		}

		return rtnValue;
	}

	/**
	 * 生成事件系列号
	 * 
	 * @return
	 * @throws Exception
	 */
	protected synchronized String generateIncidentCode(long custId)
			throws Exception {
		// 客户编码（大写）_YYYYMM_000001

		String incidentCode = "";

		// 取得客户信息
		CcCust cust = custMgntService.getCustInfo(custId);

		incidentCode = cust.getCustCode().toUpperCase() + "-";

		String ym = DateUtils.date2String(commonDAO.getSysDate(),
				DateUtils.FORMATTYPE_yyyyMMdd);
		ym = ym.substring(0, ym.length() - 2);

		incidentCode += ym + "-";

		// 取得当前客户的最新一条事件记录
		IcIncident incident = getLastIncidentByCustId(custId);

		// 取得当前顺序号
		String strCurrentSerial = "000000";
		if (incident != null) {
			strCurrentSerial = incident.getIncidentCode().substring(
					incident.getIncidentCode().length() - 6);
		}
		log.debug("strCurrentSerial is " + strCurrentSerial);
		log.debug("VarTypeConvertUtils.string2Long(strCurrentSerial) is "
				+ VarTypeConvertUtils.string2Long(strCurrentSerial));

		// 得出新的顺序号
		long lNewSerial = VarTypeConvertUtils.string2Long(strCurrentSerial) + 1;
		log.debug("lNewSerial is " + lNewSerial);
		log.debug("VarTypeConvertUtils.long2String(lNewSerial) is "
				+ VarTypeConvertUtils.long2String(lNewSerial));
		String strNewSerial = VarTypeConvertUtils.long2String(lNewSerial);
		log.debug("strNewSerial1 is " + strNewSerial);
		int j = strNewSerial.length();
		for (int i = 0; i < 6 - j; i++) {
			strNewSerial = "0" + strNewSerial;
		}
		log.debug("strNewSerial2 is " + strNewSerial);

		// 拼出最终事件系列号
		incidentCode += strNewSerial;

		return incidentCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#modifyIncidentAndAttach
	 * (long, com.ei.itop.incidentmgnt.bean.IncidentInfo, long)
	 */
	public long modifyIncidentAndAttach(long incidentId,
			IncidentInfo incidentInfo, OpInfo opInfo) throws Exception {

		// IncidentInfo ii = new IncidentInfo();

		// 业务信息
		// ii.setScOrgId(incidentInfo.getScOrgId());
		// ii.setScOrgName(incidentInfo.getScOrgName());
		// ii.setCcCustId(incidentInfo.getCcCustId());
		// ii.setCustName(incidentInfo.getCustName());
		//
		// ii.setScProductId(incidentInfo.getScProductId());
		// ii.setProdName(incidentInfo.getProdName());
		// ii.setScModuleId(incidentInfo.getScModuleId());
		// ii.setModuleName(incidentInfo.getModuleName());
		// ii.setAffectCodeUser(incidentInfo.getAffectCodeUser());
		// ii.setAffectValUser(incidentInfo.getAffectValUser());
		// ii.setClassCodeUser(incidentInfo.getClassCodeUser());
		// ii.setClassValUser(incidentInfo.getClassValUser());
		// ii.setBrief(incidentInfo.getBrief());
		// ii.setHappenTime(incidentInfo.getHappenTime());
		// ii.setDetail(incidentInfo.getDetail());
		// ii.setCcList(incidentInfo.getCcList());
		//
		// ii.setItStateCode(incidentInfo.getItStateCode());
		// ii.setItStateVal(incidentInfo.getItStateVal());

		// 设置主键
		incidentInfo.setIcIncidentId(incidentId);

		// 修改人
		incidentInfo.setModifier(opInfo.getOpName());

		// 设置HappenTime
		incidentInfo.setStrHappenTime(DateUtils.date2String(
				incidentInfo.getHappenTime(),
				DateUtils.FORMATTYPE_yyyyMMddHHmmss));

		// 保存事件实体信息
		incidentDAO.update("IC_INCIDENT.updateByPrimaryKeySelective",
				incidentInfo);

		// 保存附件信息
		attachService.saveAttach(incidentId, incidentInfo.getAttachList());

		return incidentId;
	}

	/**
	 * 根据商户、客户查询顾问工作量
	 * 
	 * @return
	 * @throws Exception
	 */
	protected List<AdviserTaskQuantity> queryAdviserTaskQuantity(long orgId,
			long custId) throws Exception {
		return null;
	}

	/**
	 * 自动分派负责顾问
	 * 
	 * @return
	 * @throws Exception
	 */
	protected ScOp getInChargeAdviser(long orgId, long custId, long productId)
			throws Exception {

		// 取得商户、客户、产品的负责顾问列表
		List<CcCustProdOp> custProdOpList = custMgntService.getCustProdOpList(
				orgId, custId, productId);

		if (custProdOpList == null || custProdOpList.size() == 0) {
			throw new Exception("系统尚未配置负责该产品线的顾问");
		}

		// 查询商户、客户下当前负责顾问的工作量
		List<AdviserTaskQuantity> adviserTaskQuantityList = queryAdviserTaskQuantity(
				orgId, custId);

		// 处理事件最少的顾问作为自动分派的顾问
		CcCustProdOp result = null;
		// 如果有顾问还不在当前负责顾问列表里，那么直接指定该顾问
		List<CcCustProdOp> noQuantityList = new ArrayList<CcCustProdOp>();
		for (int i = 0; i < custProdOpList.size(); i++) {
			CcCustProdOp cpo = custProdOpList.get(i);
			boolean inFlag = false;
			for (int j = 0; adviserTaskQuantityList != null
					&& j < adviserTaskQuantityList.size(); j++) {
				AdviserTaskQuantity atq = adviserTaskQuantityList.get(j);
				if (cpo.getScOpId() == atq.getAdviserId()) {
					inFlag = true;
					break;
				}
			}
			if (!inFlag) {
				noQuantityList.add(cpo);
			}
		}
		// 存在没有工作量的顾问，取第一个作为负责顾问
		if (noQuantityList.size() > 0) {
			result = custProdOpList.get(0);
		}
		// 所有顾问均有工作量，那么取最小工作量的那个作为负责顾问
		else {
			long minQuantity = -1;
			int minIndex = -1;
			for (int i = 0; adviserTaskQuantityList != null
					&& i < adviserTaskQuantityList.size(); i++) {
				AdviserTaskQuantity atq = adviserTaskQuantityList.get(i);
				if (minQuantity == -1) {
					minQuantity = atq.getTaskQuantity();
					minIndex = i;
				}
				if (atq.getTaskQuantity() < minQuantity) {
					minQuantity = atq.getTaskQuantity();
					minIndex = i;
				}
			}
			for (int i = 0; i < custProdOpList.size(); i++) {
				CcCustProdOp cpo = custProdOpList.get(i);
				if (cpo.getScOpId() == adviserTaskQuantityList.get(minIndex)
						.getAdviserId()) {
					result = cpo;
				}
			}
		}

		// 处理返回结果
		ScOp op = opService.queryScOp(result.getScOpId());
		// scOp.setScOpId(new Long(200005));
		// scOp.setOpCode("SP200005");
		// scOp.setOpName("EI-PM");

		return op;
	}

	/**
	 * 获得事件所处阶段
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getItPhase(long orgId, long custId, long productId, long opId)
			throws Exception {

		String itPhase = "";

		CcCustProdOp custProdOp = custMgntService.getCustProdOpInfo(orgId,
				custId, productId, opId);

		itPhase = paramService.getParam(orgId, "JOB_LEVEL",
				custProdOp.getJobLevel()).getParamValue();
		itPhase += "-"
				+ paramService.getParam(orgId, "JOB_CLASS",
						custProdOp.getJobClass()).getParamValue();

		return itPhase;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#commitIncident(com.
	 * ei.itop.incidentmgnt.bean.IncidentInfo, long)
	 */
	public long MBLAddAndCommitIncidentAndAttach(IncidentInfo incidentInfo,
			OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub

		// 提交时需调整事件状态为待响应
		incidentInfo.setItStateVal("待响应");
		incidentInfo.setItStateCode("2");

		// 提交时自动填入登记时间
		incidentInfo.setRegisteTime(commonDAO.getSysDate());

		// 自动填入商户信息、客户信息
		CcUser user = userService.queryUser(incidentInfo.getIcOwnerId());
		incidentInfo.setScOrgId(user.getScOrgId());
		incidentInfo.setScOrgName(user.getScOrgName());
		incidentInfo.setCcCustId(user.getCcCustId());
		incidentInfo.setCustName(user.getCustName());

		// 提交时自动分派负责顾问，并作为干系人
		ScOp inChargeAdviser = getInChargeAdviser(incidentInfo.getScOrgId(),
				incidentInfo.getCcCustId(), incidentInfo.getScProductId());
		incidentInfo.setIcObjectType("OP");
		incidentInfo.setIcObjectId(inChargeAdviser.getScOpId());
		incidentInfo.setIcLoginCode(inChargeAdviser.getLoginCode());
		incidentInfo.setIcObjectName(inChargeAdviser.getOpName());

		// 填入事件所处阶段
		incidentInfo.setItPhase(getItPhase(user.getScOrgId(),
				user.getCcCustId(), incidentInfo.getScProductId(),
				inChargeAdviser.getScOpId()));

		// 填入响应时限、处理时限、响应截止时间、处理截止时间、红绿灯-响应时限、红绿灯-处理时限
		// ********

		// 保存事件信息
		long incidentId = addIncidentAndAttach(incidentInfo, opInfo);

		// 系统自动生成第一条事务
		TransactionInfo transactionInfo = new TransactionInfo();
		transactionInfo.setItPhase(incidentInfo.getItPhase());
		transactionInfo.setTransType("流程事务-顾问处理中");
		transactionInfo.setContents(incidentInfo.getDetail());
		long transactionId = transactionService.addTransaction(incidentId,
				transactionInfo, opInfo);

		// 将事件的附件转为第一条事务的附件
		// 即将附件表的事务ID由null改为第一条事务的ID
		attachService.changeTransAttach2IncidAttach(incidentId, transactionId);

		// 记录系统操作日志

		return incidentId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#commitIncident(long,
	 * com.ei.itop.incidentmgnt.bean.IncidentInfo, long)
	 */
	public long MBLModifyAndCommitIncidentAndAttach(long incidentId,
			IncidentInfo incidentInfo, OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub

		// 提交时需调整事件状态为待响应
		incidentInfo.setItStateVal("待响应");
		incidentInfo.setItStateCode("2");

		// 提交时自动填入登记时间
		incidentInfo.setRegisteTime(commonDAO.getSysDate());

		// // 自动填入商户信息、客户信息
		CcUser user = userService.queryUser(incidentInfo.getIcOwnerId());
		// incidentInfo.setScOrgId(user.getScOrgId());
		// incidentInfo.setScOrgName(user.getScOrgName());
		// incidentInfo.setCcCustId(user.getCcCustId());
		// incidentInfo.setCustName(user.getCustName());

		// 提交时自动分派负责顾问，并作为干系人
		ScOp inChargeAdviser = getInChargeAdviser(incidentInfo.getScOrgId(),
				incidentInfo.getCcCustId(), incidentInfo.getScProductId());
		incidentInfo.setIcObjectType("OP");
		incidentInfo.setIcObjectId(inChargeAdviser.getScOpId());
		incidentInfo.setIcLoginCode(inChargeAdviser.getLoginCode());
		incidentInfo.setIcObjectName(inChargeAdviser.getOpName());

		// 填入事件所处阶段
		incidentInfo.setItPhase(getItPhase(user.getScOrgId(),
				user.getCcCustId(), incidentInfo.getScProductId(),
				inChargeAdviser.getScOpId()));

		// 填入响应时限、处理时限、响应截止时间、处理截止时间、红绿灯-响应时限、红绿灯-处理时限
		// ********

		// 保存事件信息
		modifyIncidentAndAttach(incidentId, incidentInfo, opInfo);

		// 系统自动生成第一条事务
		TransactionInfo transactionInfo = new TransactionInfo();
		transactionInfo.setItPhase(incidentInfo.getItPhase());
		transactionInfo.setTransType("流程事务-顾问处理中");
		transactionInfo.setContents(incidentInfo.getDetail());
		long transactionId = transactionService.addTransaction(incidentId,
				transactionInfo, opInfo);

		// 将事件的附件转为第一条事务的附件
		// 即将附件表的事务ID由null改为第一条事务的ID
		attachService.changeTransAttach2IncidAttach(incidentId, transactionId);

		// 记录系统操作日志

		return incidentId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#commitIncident(long,
	 * long)
	 */
	public long MBLCommitIncident(long incidentId, OpInfo opInfo)
			throws Exception {
		// TODO Auto-generated method stub

		IncidentInfo incidentInfo = new IncidentInfo();

		IcIncident incident = queryIncident(incidentId);

		// 提交时需调整事件状态为待响应
		incidentInfo.setItStateVal("待响应");
		incidentInfo.setItStateCode("2");

		// 提交时自动填入登记时间
		incidentInfo.setRegisteTime(commonDAO.getSysDate());

		// // 自动填入商户信息、客户信息
		CcUser user = userService.queryUser(incident.getIcOwnerId());
		// incidentInfo.setScOrgId(user.getScOrgId());
		// incidentInfo.setScOrgName(user.getScOrgName());
		// incidentInfo.setCcCustId(user.getCcCustId());
		// incidentInfo.setCustName(user.getCustName());

		// 提交时自动分派负责顾问，并作为干系人
		ScOp inChargeAdviser = getInChargeAdviser(incident.getScOrgId(),
				incident.getCcCustId(), incident.getScProductId());
		incidentInfo.setIcObjectType("OP");
		incidentInfo.setIcObjectId(inChargeAdviser.getScOpId());
		incidentInfo.setIcLoginCode(inChargeAdviser.getLoginCode());
		incidentInfo.setIcObjectName(inChargeAdviser.getOpName());

		// 填入事件所处阶段
		incidentInfo.setItPhase(getItPhase(user.getScOrgId(),
				user.getCcCustId(), incident.getScProductId(),
				inChargeAdviser.getScOpId()));

		// 填入响应时限、处理时限、响应截止时间、处理截止时间、红绿灯-响应时限、红绿灯-处理时限
		// ********

		// 保存事件信息
		modifyIncidentAndAttach(incidentId, incidentInfo, opInfo);

		// 系统自动生成第一条事务
		TransactionInfo transactionInfo = new TransactionInfo();
		transactionInfo.setItPhase(incident.getItPhase());
		transactionInfo.setTransType("流程事务-顾问处理中");
		transactionInfo.setContents(incident.getDetail());
		long transactionId = transactionService.addTransaction(incidentId,
				transactionInfo, opInfo);

		// 将事件的附件转为第一条事务的附件
		// 即将附件表的事务ID由null改为第一条事务的ID
		attachService.changeTransAttach2IncidAttach(incidentId, transactionId);

		// 记录系统操作日志

		return incidentId;

		// throw new Exception("目前系统中并没有直接提交事件的入口，此逻辑暂未实现");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#removeIncident(long,
	 * long)
	 */
	public void MBLRemoveIncident(long incidentId, OpInfo opInfo)
			throws Exception {
		// TODO Auto-generated method stub

		IcIncident incident = queryIncident(incidentId);

		// 如果不是待提交状态，则不允许删除事件
		if (!"1".equals(incident.getItStateCode())) {
			throw new Exception("只有待提交状态的事件才可以删除");
		}

		// 如果不是事件提出人，则不允许删除事件
		if (!opInfo.getOpType().equals(incident.getPlObjectType())
				&& opInfo.getOpId() != incident.getPlObjectId()) {
			throw new Exception("只有事件提出人才可以删除事件");
		}

		// 修改事件数据状态为已失效
		IncidentInfo ii = new IncidentInfo();
		ii.setDataState(new Long(0));

		// 修改记录状态
		modifyIncidentAndAttach(incidentId, ii, opInfo);

		// 记录系统操作日志
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#adviserCompleteInfo
	 * (com.ei.itop.incidentmgnt.bean.IncidentInfo, long)
	 */
	public void MBLAdviserCompleteInfo(long incidentId, IcIncident incident,
			OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub

		if (incident.getAffectCodeOp() == null
				|| incident.getAffectValOp() == null
				|| incident.getClassCodeOp() == null
				|| incident.getClassValOp() == null
				|| incident.getPriorityCode() == null
				|| incident.getPriorityVal() == null
				|| incident.getComplexCode() == null
				|| incident.getComplexVal() == null) {
			throw new Exception("必须一次性补全顾问影响度、分类、优先级、复杂度，缺一不可");
		}

		IncidentInfo ii = new IncidentInfo();

		// 设置主键
		ii.setIcIncidentId(incidentId);

		// 业务信息
		ii.setAffectCodeOp(incident.getAffectCodeOp());
		ii.setAffectValOp(incident.getAffectValOp());
		ii.setClassCodeOp(incident.getClassCodeOp());
		ii.setClassValOp(incident.getClassValOp());
		ii.setPriorityCode(incident.getPriorityCode());
		ii.setPriorityVal(incident.getPriorityVal());
		ii.setComplexCode(incident.getComplexCode());
		ii.setComplexVal(incident.getComplexVal());

		// 保存事件信息
		modifyIncidentAndAttach(incidentId, ii, opInfo);

		// 记录系统操作日志
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#userSetFeedbackVal(
	 * com.ei.itop.incidentmgnt.bean.IncidentInfo, long)
	 */
	public void MBLUserSetFeedbackVal(long incidentId, IcIncident incident,
			OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub

		// 判断传入信息是否完整
		if (incident.getFeedbackCode() == null
				|| incident.getFeedbackVal() == null) {
			throw new Exception("用户满意度信息填写不完整");
		}

		// 查询事件信息
		IcIncident icIncident = queryIncident(incidentId);

		// 仅当事件状态为8-已完成时才可以进行用户反馈满意度操作
		if (!"8".equals(icIncident.getItStateCode())) {
			throw new Exception("仅当事件状态为已完成时才可以评价");
		}

		IncidentInfo ii = new IncidentInfo();

		// 业务信息
		ii.setFeedbackCode(incident.getFeedbackCode());
		ii.setFeedbackVal(incident.getFeedbackVal());
		ii.setFeedbackTime(commonDAO.getSysDate());

		// 保存事件信息
		modifyIncidentAndAttach(incidentId, ii, opInfo);

		// 记录系统操作日志
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#closeIncident(long)
	 */
	public void MBLAdviserCloseIncident(long incidentId, OpInfo opInfo)
			throws Exception {
		// TODO Auto-generated method stub

		// 查询事件信息
		IcIncident incident = queryIncident(incidentId);

		// 仅当事件状态为8-已完成，且用户已经评价完毕时才可以进行用户反馈满意度操作
		if (!"8".equals(incident.getItStateCode())) {
			throw new Exception("仅当事件状态为已完成且用户已经评价完毕时才可以关闭");
		} else if (incident.getFeedbackCode() == null) {
			throw new Exception("仅当事件状态为已完成且用户已经评价完毕时才可以关闭");
		}

		IncidentInfo ii = new IncidentInfo();

		// 设置事件状态
		ii.setItStateCode("9");
		ii.setItStateVal("已关闭");

		// 业务信息
		ii.setCloseTime(commonDAO.getSysDate());

		// 保存事件信息
		modifyIncidentAndAttach(incidentId, ii, opInfo);

		// 系统自动生成最后一条事务
		TransactionInfo transactionInfo = new TransactionInfo();
		transactionInfo.setTransType("流程事务-关闭");
		transactionInfo.setContents("顾问关闭事务");
		transactionService.addTransaction(incidentId, transactionInfo, opInfo);

		// 记录系统操作日志
	}

	public static void main(String[] args) throws Exception {
		IncidentService is = (IncidentService) AppContext
				.getBean("incidentService");

		OpInfo oi = new OpInfo();
		oi.setOpType("OP");
		oi.setOpId(0);
		oi.setOpCode("SamZhang@163.com");
		oi.setOpName("Sam.Zhang（张三）");

		IncidentInfo ii = new IncidentInfo();
		ii.setIcOwnerType("USER");
		ii.setIcOwnerId(new Long(9001));
		ii.setIcOwnerCode("ITOPCNH1@163.com");
		ii.setIcOwnerName("itop1-cnh");
		ii.setScProductId(new Long(101));
		ii.setProdName("BaaN 财务模块");
		ii.setScModuleId(new Long(12));
		ii.setModuleName("日记账");
		// ii.setHappenTime();
		ii.setClassCodeOp("21");
		ii.setClassValOp("操作故障");
		ii.setAffectCodeOp("2");
		ii.setAffectValOp("一般");
		ii.setComplexCode("2");
		ii.setComplexVal("复杂");
		ii.setPriorityCode("3");
		ii.setPriorityVal("高");
		ii.setCcList("a@a.com,b@b.com,c@c.com,d@d.com");
		ii.setBrief("事件简述11");
		ii.setDetail("事件详细描述22");
		List<IcAttach> attachList = new ArrayList<IcAttach>();
		IcAttach attach1 = new IcAttach();
		attach1.setAttachPath("/data/attach1234.txt");
		IcAttach attach2 = new IcAttach();
		attach2.setAttachPath("/data/attach2234.doc");
		attachList.add(attach1);
		attachList.add(attach2);
		ii.setAttachList(attachList);
		// is.MBLAddIncidentAndAttach(ii, oi);

		// is.MBLRemoveIncident(10026, oi);

		// is.MBLModifyIncidentAndAttach(10021, ii, oi);

		// is.MBLCommitIncident(10026, oi);

		// is.MBLAddAndCommitIncidentAndAttach(ii, oi);

		// is.MBLModifyAndCommitIncidentAndAttach(10026, ii, oi);

		// ii.setAffectCodeOp("2");
		// ii.setAffectValOp("一般1");
		// ii.setClassCodeOp("12");
		// ii.setClassValOp("业务咨询1");
		// ii.setPriorityCode("3");
		// ii.setPriorityVal("高1");
		// ii.setComplexCode("2");
		// ii.setComplexVal("复杂1");
		// is.MBLAdviserCompleteInfo(10025, ii, oi);

		// is.queryIncident(10003);

		// ii.setFeedbackCode("3");
		// ii.setFeedbackVal("不满意");
		// is.MBLUserSetFeedbackVal(10027, ii, oi);

		// is.MBLAdviserCloseIncident(10027, oi);

		// 测试查询
		QCIncident qc = new QCIncident();
		// is.MBLQueryIncident(100027, oi);
		// qc.setIncidentCode("%");
		// qc.setClassCode(new String[] { "101", "102" });
		// qc.setStateCode("2");
		// is.MBLQueryIncidentCount(qc, oi);
		// is.MBLQueryIncident(qc, -1, 10, oi);
		// is.MBLQueryIncident(qc, 0, 3, oi);

		// String itPhase = is.getItPhase(2001, 300002, 102, 200006);
		// log.debug(itPhase);
		// oi.setOpType("USER");
		// oi.setOpId(new Long(9001));
		// oi.setOpCode("NO-1");
		// oi.setOpName("拓创");
		// List<IncidentCountInfoByState> list = is
		// .MBLQueryIncidentCountGroupByState(qc, 2001, oi);
		// for (int i = 0; list != null && i < list.size(); i++) {
		// IncidentCountInfoByState item = list.get(i);
		// String message = item.getStateCode() + "," + item.getStateVal()
		// + "," + item.getRecordCount();
		// log.debug(message);
		// }

		// log.debug(is.generateIncidentCode(300001));
	}
}
