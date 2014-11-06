package com.ei.itop.incidentmgnt.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.dazzle.util.json.JSONUtils;
import com.ailk.dazzle.util.type.ArrayUtils;
import com.ailk.dazzle.util.type.DateUtils;
import com.ailk.dazzle.util.type.StringUtils;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ailk.dazzle.util.web.ActionUtils;
import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.dbentity.CcCust;
import com.ei.itop.common.dbentity.IcAttach;
import com.ei.itop.common.dbentity.IcIncident;
import com.ei.itop.common.util.ExceptionHandleFilter;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.custmgnt.service.CustMgntService;
import com.ei.itop.incidentmgnt.bean.IncidentCountInfoByState;
import com.ei.itop.incidentmgnt.bean.IncidentInfo;
import com.ei.itop.incidentmgnt.bean.QCIncident;
import com.ei.itop.incidentmgnt.service.AttachService;
import com.ei.itop.incidentmgnt.service.IncidentService;

@Controller
@RequestMapping("/incident")
public class IncidentController {

	private static final Logger log = Logger
			.getLogger(IncidentController.class);

	@Autowired
	private IncidentService incidentService;

	@Autowired
	private AttachService attachService;

	@Autowired
	private CustMgntService custMgntService;

	/**
	 * 查询列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public void queryIncidentList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		int page = VarTypeConvertUtils.string2Integer(
				request.getParameter("page"), 0);
		// 获取分页大小
		int pageSize = VarTypeConvertUtils.string2Integer(
				request.getParameter("rows"), 0);
		// 获取分页起始位置
		long startIndex = (page - 1) * pageSize + 1;
		// 构建查询条件实体
		QCIncident qi = new QCIncident();
		// 影响度
		String affectVal = request.getParameter("affectVar");
		if (!StringUtils.isEmpty(affectVal)) {
			qi.setAffectCode(affectVal.split(","));
		}
		// 简述
		String brief = request.getParameter("brief");
		if (!StringUtils.isEmpty(brief)) {
			if(log.isDebugEnabled()){
				log.debug("brief original:"+brief);
				log.debug("brief decode1:"+ActionUtils.transParamDecode(brief, "UTF-8"));
				log.debug("brief decode2:"+ActionUtils.transParam(brief, "UTF-8"));
				log.debug("brief decode3:"+new String(brief.getBytes(), "GBK"));
				log.debug("brief decode4:"+new String(brief.getBytes("iso-8859-1"), "GBK"));
				log.debug("brief decode5:"+new String(brief.getBytes(), "UTF-8"));
				log.debug("brief decode6:"+new String(brief.getBytes("iso-8859-1"), "UTF-8"));
			}
			qi.setBrief(ActionUtils.transParam(brief, "UTF-8"));
		}
		// 类别
		String classVal = request.getParameter("classVar");
		if (!StringUtils.isEmpty(classVal)) {
			qi.setClassCode(classVal.split(","));
		}
		// 事件系列号
		String incidentCode = request.getParameter("incidentCode");
		if (!StringUtils.isEmpty(incidentCode)) {
			qi.setIncidentCode(incidentCode);
		}
		// 两个时间排序字段的设置
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			String[] sorts = sort.split(",");
			String[] orders = order.split(",");
			for (int i = 0; i < sorts.length; i++) {
				if ("registeTime".equals(sorts[i])) {
					qi.setOrderByRegisterTime(orders[i]);
				} else if ("modifyDate".equals(sorts[i])) {
					qi.setOrderByLastModifyTime(orders[i]);
				}
			}
		}
		// 优先级
		String priorityVal = request.getParameter("priorityVal");
		if (!StringUtils.isEmpty(priorityVal)) {
			qi.setPriorityCode(priorityVal.split(","));
		}
		// 产品线
		String productId = request.getParameter("productId");
		if (!StringUtils.isEmpty(productId)) {
			qi.setProductId(VarTypeConvertUtils.string2Long(productId, 0));
		}
		// 登记时间起始
		String registerTimeBegin = request.getParameter("registerTimeBegin");
		if (!StringUtils.isEmpty(registerTimeBegin)) {
			qi.setRegisterTimeBegin(DateUtils.string2Date(registerTimeBegin,
					DateUtils.FORMATTYPE_yyyy_MM_dd));
		}
		// 登记时间截止
		String registerTimeEnd = request.getParameter("registerTimeEnd");
		if (!StringUtils.isEmpty(registerTimeEnd)) {
			qi.setRegisterTimeEnd(DateUtils.dateOffset(DateUtils.string2Date(registerTimeEnd,
					DateUtils.FORMATTYPE_yyyy_MM_dd),Calendar.DAY_OF_YEAR,1));
		}
		// 事件状态
		String stateCode = request.getParameter("stateVal");
		if (!StringUtils.isEmpty(stateCode)) {
			qi.setStateCode(stateCode.split(","));
		}
		// 客户ID
		String custId = request.getParameter("custId");
		if (!StringUtils.isEmpty(custId)) {
			//根据客户ID取得所有子客户(含当前客户)
			List<CcCust> custs = custMgntService.getSubCusts(VarTypeConvertUtils.string2Long(custId));
			Long[] custIds = new Long[custs.size()];
			for(int i=0;i<custs.size();i++){
				custIds[i]=custs.get(i).getCcCustId();
			}
			qi.setCustId(custIds);
		}
		// 责任顾问ID
		String adviserId = request.getParameter("adviserId");
		if (!StringUtils.isEmpty(adviserId)) {
			qi.setAdviserId(adviserId.split(","));
		}
		// 登记人ID
		String registeMan = request.getParameter("registeMan");
		if (!StringUtils.isEmpty(registeMan)) {
			qi.setRegisteManId(registeMan.split(","));
		}
		// 设置组织ID
		qi.setOrgId(oi.getOrgId());
		
		// 调用查询获取总数据条数
		long count = incidentService.MBLQueryIncidentCount(qi, oi);
		// 调用查询获取分页数据
		List<IcIncident> data = incidentService.MBLQueryIncident(qi,
				startIndex, pageSize, oi);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", data);
		String jsonData = JSONUtils.toJSONString(result);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}

	/**
	 * 获取各事件状态的事件个数
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/statusCount")
	public void queryStatusCount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		OpInfo oi = SessionUtil.getOpInfo();
		// 构建查询条件实体
		QCIncident qi = new QCIncident();
		// 影响度
		String affectVal = request.getParameter("affectVar");
		if (!StringUtils.isEmpty(affectVal)) {
			qi.setAffectCode(affectVal.split(","));
		}
		// 简述
		String brief = request.getParameter("brief");
		if (!StringUtils.isEmpty(brief)) {
			qi.setBrief(ActionUtils.transParam(brief, "UTF-8"));
		}
		// 类别
		String classVal = request.getParameter("classVar");
		if (!StringUtils.isEmpty(classVal)) {
			qi.setClassCode(classVal.split(","));
		}
		// 事件系列号
		String incidentCode = request.getParameter("incidentCode");
		if (!StringUtils.isEmpty(incidentCode)) {
			qi.setIncidentCode(incidentCode);
		}
		// 优先级
		String priorityVal = request.getParameter("priorityVal");
		if (!StringUtils.isEmpty(priorityVal)) {
			qi.setPriorityCode(priorityVal.split(","));
		}
		// 产品线
		String productId = request.getParameter("productId");
		if (!StringUtils.isEmpty(productId)) {
			qi.setProductId(VarTypeConvertUtils.string2Long(productId, 0));
		}
		// 登记时间起始
		String registerTimeBegin = request.getParameter("registerTimeBegin");
		if (!StringUtils.isEmpty(registerTimeBegin)) {
			qi.setRegisterTimeBegin(DateUtils.string2Date(registerTimeBegin,
					DateUtils.FORMATTYPE_yyyy_MM_dd));
		}
		// 登记时间截止
		String registerTimeEnd = request.getParameter("registerTimeEnd");
		if (!StringUtils.isEmpty(registerTimeEnd)) {
			qi.setRegisterTimeEnd(DateUtils.dateOffset(DateUtils.string2Date(registerTimeEnd,
					DateUtils.FORMATTYPE_yyyy_MM_dd),Calendar.DAY_OF_YEAR,1));
		}
		// 客户ID
		String custId = request.getParameter("custId");
		if (!StringUtils.isEmpty(custId)) {
			// 根据客户ID取得所有子客户(含当前客户)
			List<CcCust> custs = custMgntService
					.getSubCusts(VarTypeConvertUtils.string2Long(custId));
			Long[] custIds = new Long[custs.size()];
			for (int i = 0; i < custs.size(); i++) {
				custIds[i] = custs.get(i).getCcCustId();
			}
			qi.setCustId(custIds);
		}
		// 责任顾问ID
		String adviserId = request.getParameter("adviserId");
		if (!StringUtils.isEmpty(adviserId)) {
			qi.setAdviserId(adviserId.split(","));
		}
		// 登记人ID
		String registeMan = request.getParameter("registeMan");
		if (!StringUtils.isEmpty(registeMan)) {
			qi.setRegisteManId(registeMan.split(","));
		}
		// 设置组织ID
		qi.setOrgId(oi.getOrgId());
		List<IncidentCountInfoByState> stateCount = incidentService
				.MBLQueryIncidentCountGroupByState(qi, oi.getOrgId(), oi);
		String jsonData = JSONUtils.toJSONString(stateCount);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}

	/**
	 * 新增事件（保存）
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/add")
	public void addIncident(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("notCommit", true);
		addIncidentAutoCommit(request, response);
	}

	/**
	 * 新增事件（直接提交）
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addc")
	public void addIncidentAutoCommit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		IncidentInfo ii = new IncidentInfo();
		// 客户ID
		String companyId = request.getParameter("companyId");
		ii.setCcCustId(VarTypeConvertUtils.string2Long(companyId));
		String companyName = request.getParameter("companyName");
		ii.setCustName(companyName);
		// 产品线
		String scProductId = request.getParameter("productId");
		ii.setScProductId(VarTypeConvertUtils.string2Long(scProductId));
		String productName = request.getParameter("productName");
		ii.setProdName(productName);
		// 服务目录
		String scModuleId = request.getParameter("moduleId");
		String moduleName = request.getParameter("moduleName");
		ii.setScModuleId(VarTypeConvertUtils.string2Long(scModuleId));
		ii.setModuleName(moduleName);
		// 影响度
		String affectCode = request.getParameter("affectCode");
		String affectVal = request.getParameter("affectVar");
		ii.setAffectCodeOp(affectCode);
		ii.setAffectValOp(affectVal);
		// 事件类别
		String classCode = request.getParameter("classCode");
		String classVar = request.getParameter("classVar");
		ii.setClassCodeOp(classCode);
		ii.setClassValOp(classVar);
		// 事件简述
		String brief = request.getParameter("brief");
		ii.setBrief(brief);
		// 发生时间
		String happenTime = request.getParameter("happenTime");
		ii.setHappenTime(DateUtils.string2Date(happenTime,
				DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
		// 详细描述
		String detail = request.getParameter("detail");
		ii.setDetail(detail);
		// 事件来源
		String sourceCode = request.getParameter("sourceCode");
		String sourceVal = request.getParameter("sourceVal");
		ii.setSourceCode(sourceCode);
		ii.setSourceVal(sourceVal);

		// 抄送
		String ccList = request.getParameter("ccList");
		ii.setCcList(ccList);

		// 目前默认填写操作员的信息,待后续开放了顾问代客户提交事件后再设置前台传入的值
		ii.setIcOwnerCode(oi.getOpCode());
		ii.setIcOwnerId(oi.getOpId());
		ii.setIcOwnerName(oi.getOpName());
		ii.setIcOwnerType("USER");

		// 附件
		String attachList = request.getParameter("attachList");
		if (!StringUtils.isEmpty(attachList)) {
			List<IcAttach> attachs = attachService.queryAttachList(ArrayUtils
					.stringArray2LongArray(attachList.split(",")));
			ii.setAttachList(attachs);
		}
		if (request.getAttribute("notCommit") != null) {
			incidentService.MBLAddIncidentAndAttach(ii, oi);
		} else {
			incidentService.MBLAddAndCommitIncidentAndAttach(ii, oi);
		}
	}

	/**
	 * 修改事件并保存
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/modify")
	public void modifyIncident(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("notCommit", true);
		modifyIncidentAutoCommit(request, response);
	}

	/**
	 * 修改事件直接提交
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/modifyc")
	public void modifyIncidentAutoCommit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		IncidentInfo ii = new IncidentInfo();
		// 事件ID
		long incidentId = VarTypeConvertUtils.string2Long(request
				.getParameter("incidentId"));
		// 客户ID
		String companyId = request.getParameter("companyId");
		ii.setCcCustId(VarTypeConvertUtils.string2Long(companyId));
		String companyName = request.getParameter("companyName");
		ii.setCustName(companyName);
		// 产品线
		String scProductId = request.getParameter("productId");
		ii.setScProductId(VarTypeConvertUtils.string2Long(scProductId));
		String productName = request.getParameter("productName");
		ii.setProdName(productName);
		// 服务目录
		String scModuleId = request.getParameter("moduleId");
		String moduleName = request.getParameter("moduleName");
		ii.setScModuleId(VarTypeConvertUtils.string2Long(scModuleId));
		ii.setModuleName(moduleName);
		// 影响度
		String affectCode = request.getParameter("affectCode");
		String affectVal = request.getParameter("affectVal");
		ii.setAffectCodeOp(affectCode);
		ii.setAffectValOp(affectVal);
		// 事件类别
		String classCode = request.getParameter("classCode");
		String classVar = request.getParameter("classVar");
		ii.setClassCodeOp(classCode);
		ii.setClassValOp(classVar);
		// 事件简述
		String brief = request.getParameter("brief");
		ii.setBrief(brief);
		// 发生时间
		String happenTime = request.getParameter("happenTime");
		ii.setHappenTime(DateUtils.string2Date(happenTime,
				DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
		// 详细描述
		String detail = request.getParameter("detail");
		ii.setDetail(detail);
		// 抄送
		String ccList = request.getParameter("ccList");
		ii.setCcList(ccList);
		// 附件
		String attachList = request.getParameter("attachList");
		if (!StringUtils.isEmpty(attachList)) {
			List<IcAttach> attachs = attachService.queryAttachList(ArrayUtils
					.stringArray2LongArray(attachList.split(",")));
			ii.setAttachList(attachs);
		}
		// 目前默认填写操作员的信息,待后续开放了顾问代客户提交事件后再设置前台传入的值
		ii.setIcOwnerCode(oi.getOpCode());
		ii.setIcOwnerId(oi.getOpId());
		ii.setIcOwnerName(oi.getOpName());
		ii.setIcOwnerType("USER");
		if (request.getAttribute("notCommit") != null) {
			incidentService.MBLModifyIncidentAndAttach(incidentId, ii, oi);
		} else {
			incidentService.MBLModifyAndCommitIncidentAndAttach(incidentId, ii,
					oi);
		}
	}

	/**
	 * 查询单条事件信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/query")
	public void queryIncidentInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		long incidentId = VarTypeConvertUtils.string2Long(request
				.getParameter("incidentId"));
		IncidentInfo ii = incidentService.MBLQueryIncident(incidentId, oi);
		List<IcAttach> attachList = ii.getAttachList();
		if (attachList != null && attachList.size() > 0) {
			for (IcAttach attach : attachList) {
				attach.setAttachPath("");
			}
		}
		String jsonData = JSONUtils.toJSONString(ii);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}

	/**
	 * 提交事件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/commit")
	public void commitIncident(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		long incidentId = VarTypeConvertUtils.string2Long(request
				.getParameter("incidentId"));
		incidentService.MBLCommitIncident(incidentId, oi);
	}

	/**
	 * 删除一条事件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/remove")
	public void removeIncident(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		long incidentId = VarTypeConvertUtils.string2Long(request
				.getParameter("incidentId"));
		incidentService.MBLRemoveIncident(incidentId, oi);
	}

	/**
	 * 关闭一条事件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/close")
	public void closeIncident(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		long incidentId = VarTypeConvertUtils.string2Long(request
				.getParameter("incidentId"));
		incidentService.MBLAdviserCloseIncident(incidentId, oi);
	}

	/**
	 * 归档一条事件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/stock")
	public void stockIncident(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		long incidentId = VarTypeConvertUtils.string2Long(request
				.getParameter("incidentId"));
		String[] stockFlags = request.getParameter("stockVar").split(",");
		incidentService.MBLUserStockIncident(incidentId, stockFlags, oi);
	}

	/**
	 * 顾问补全事件信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/complete")
	public void completeIncident(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		long incidentId = VarTypeConvertUtils.string2Long(request
				.getParameter("incidentId"));
		IncidentInfo ii = new IncidentInfo();
		
		String productId = request.getParameter("productId");
		String productName = request.getParameter("productName");
		String moduleId = request.getParameter("moduleId");
		String moduleName = request.getParameter("moduleName");
		String classCodeOp = request.getParameter("classCode");
		String classValOp = request.getParameter("classVal");
		String affectValOp = request.getParameter("affectVal");
		String affectCode = request.getParameter("affectCode");
		String priorityCode = request.getParameter("priorityCode");
		String priorityVal = request.getParameter("priorityVal");
		String complexCode = request.getParameter("complexCode");
		String complexVal = request.getParameter("complexVal");
		ii.setScProductId(VarTypeConvertUtils.string2Long(productId));
		ii.setProdName(productName);
		ii.setScModuleId(VarTypeConvertUtils.string2Long(moduleId));
		ii.setModuleName(moduleName);
		ii.setClassCodeOp(classCodeOp);
		ii.setClassValOp(classValOp);
		ii.setAffectValOp(affectValOp);
		ii.setAffectCode(affectCode);
		ii.setPriorityCode(priorityCode);
		ii.setPriorityVal(priorityVal);
		ii.setComplexCode(complexCode);
		ii.setComplexVal(complexVal);
		incidentService.MBLAdviserCompleteInfo(incidentId, ii, oi);
	}

	/**
	 * 评价一条事件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/feedback")
	public void feedBackIncident(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		long incidentId = VarTypeConvertUtils.string2Long(request
				.getParameter("incidentId"));
		IcIncident ii = new IcIncident();
		String feedbackVal = request.getParameter("feedbackVal");
		String feedbackCode = request.getParameter("feedbackCode");
		ii.setFeedbackVal(feedbackVal);
		ii.setFeedbackCode(feedbackCode);
		incidentService.MBLUserSetFeedbackVal(incidentId, ii, oi);
	}
}
