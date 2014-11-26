package com.ei.itop.incidentmgnt.controller;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
			if (log.isDebugEnabled()) {
				log.debug("brief original:" + brief);
				log.debug("brief decode1:"
						+ ActionUtils.transParamDecode(brief, "UTF-8"));
				log.debug("brief decode2:"
						+ ActionUtils.transParam(brief, "UTF-8"));
				log.debug("brief decode3:"
						+ new String(brief.getBytes(), "GBK"));
				log.debug("brief decode4:"
						+ new String(brief.getBytes("iso-8859-1"), "GBK"));
				log.debug("brief decode5:"
						+ new String(brief.getBytes(), "UTF-8"));
				log.debug("brief decode6:"
						+ new String(brief.getBytes("iso-8859-1"), "UTF-8"));
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
			qi.setRegisterTimeEnd(DateUtils.dateOffset(DateUtils.string2Date(
					registerTimeEnd, DateUtils.FORMATTYPE_yyyy_MM_dd),
					Calendar.DAY_OF_YEAR, 1));
		}
		// 事件状态
		String stateCode = request.getParameter("stateVal");
		if (!StringUtils.isEmpty(stateCode)) {
			qi.setStateCode(stateCode.split(","));
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
	 * 导出事件报表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/export")
	public void exportIncidentReport(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		// 构建查询条件实体
		QCIncident qi = new QCIncident();
		// 客户ID
		String custId = request.getParameter("expCustId");
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
		// 登记人ID
		String expRegisterId = request.getParameter("expRegisterId");
		if (!StringUtils.isEmpty(expRegisterId)) {
			qi.setRegisteManId(expRegisterId.split(","));
		}
		// 责任顾问ID
		String adviserId = request.getParameter("expConsultantId");
		if (!StringUtils.isEmpty(adviserId)) {
			qi.setAdviserId(adviserId.split(","));
		}
		// 时间范围
		String expStartDate = request.getParameter("expStartDate");
		if (!StringUtils.isEmpty(expStartDate)) {
			qi.setRegisterTimeBegin(DateUtils.string2Date(expStartDate,
					DateUtils.FORMATTYPE_yyyy_MM_dd));
		}
		String expEndDate = request.getParameter("expEndDate");
		if (!StringUtils.isEmpty(expEndDate)) {
			qi.setRegisterTimeEnd(DateUtils.dateOffset(DateUtils.string2Date(
					expEndDate, DateUtils.FORMATTYPE_yyyy_MM_dd),
					Calendar.DAY_OF_YEAR, 1));
		}
		// 状态范围
		String expStatus = request.getParameter("expStatus");
		if (!StringUtils.isEmpty(expStatus)) {
			qi.setStateCode(expStatus.split(","));
		}
		qi.setOrgId(oi.getOrgId());
		qi.setOrderByRegisterTime("DESC");
		// 查询事件数据
		List<IcIncident> datas = incidentService
				.MBLQueryIncident(qi, -1, 0, oi);
		// 导出Excel文档
		InputStream in = null;
		try {
			in = this.getClass().getClassLoader()
					.getResourceAsStream("export/ams-report-tpl.xlsx");
			Workbook workbook = new XSSFWorkbook(in);
			Sheet sheet = workbook.getSheetAt(0);
			// 设置表头信息
			Row row = sheet.getRow(1);
			// 设置报表时间范围
			Cell dateCell = row.getCell(0);
			String dateRange = "";
			if (StringUtils.isEmpty(expStartDate)
					&& !StringUtils.isEmpty(expEndDate)) {
				dateRange = DateUtils.date2String(datas.get(datas.size() - 1)
						.getRegisteTime(), DateUtils.FORMATTYPE_yyyy_MM_dd)
						+ "~" + expEndDate;

			} else if (!StringUtils.isEmpty(expStartDate)
					&& StringUtils.isEmpty(expEndDate)) {
				dateRange = expStartDate
						+ "~"
						+ DateUtils.date2String(datas.get(0).getRegisteTime(),
								DateUtils.FORMATTYPE_yyyy_MM_dd);
			} else if (StringUtils.isEmpty(expStartDate)
					&& StringUtils.isEmpty(expEndDate)) {
				dateRange = DateUtils.date2String(datas.get(datas.size() - 1)
						.getRegisteTime(), DateUtils.FORMATTYPE_yyyy_MM_dd)
						+ "~"
						+ DateUtils.date2String(datas.get(0).getRegisteTime(),
								DateUtils.FORMATTYPE_yyyy_MM_dd);
			} else if (!StringUtils.isEmpty(expStartDate)
					&& !StringUtils.isEmpty(expEndDate)) {
				dateRange = expStartDate + "~" + expEndDate;
			}
			dateCell.setCellValue(dateRange);
			// 设置单元格格式
			CellStyle style = workbook.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBottomBorderColor(HSSFColor.BLACK.index);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setLeftBorderColor(HSSFColor.BLACK.index);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setRightBorderColor(HSSFColor.BLACK.index);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setTopBorderColor(HSSFColor.BLACK.index);
			style.setWrapText(true);
			Font font = workbook.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 10);
			style.setFont(font);
			// 开始填充数据
			int startRow = 4;
			int no = 1;
			for (IcIncident incident : datas) {
				row = sheet.createRow(startRow);
				int cellIndex = 0;
				//序号
				Cell cellNo = row.createCell(cellIndex++);
				cellNo.setCellValue(no++);
				cellNo.setCellStyle(style);
				//编码
				Cell cellCode = row.createCell(cellIndex++);
				cellCode.setCellValue(incident.getIncidentCode());
				cellCode.setCellStyle(style);
				//描述
				Cell cellDesc = row.createCell(cellIndex++);
				cellDesc.setCellValue(incident.getBrief());
				cellDesc.setCellStyle(style);
				//解决方案
				Cell cellSolution = row.createCell(cellIndex++);
				cellSolution.setCellValue(incident.getItSolution());
				cellSolution.setCellStyle(style);
				//事件分类
				Cell cellClass = row.createCell(cellIndex++);
				cellClass.setCellValue(incident.getClassVal());
				cellClass.setCellStyle(style);
				//产品线
				Cell cellProd = row.createCell(cellIndex++);
				cellProd.setCellValue(incident.getProdName());
				cellProd.setCellStyle(style);
				//模块
				Cell cellModule = row.createCell(cellIndex++);
				cellModule.setCellValue(incident.getModuleName());
				cellModule.setCellStyle(style);
				//登记人
				Cell cellRegister = row.createCell(cellIndex++);
				cellRegister.setCellValue(incident.getPlObjectName());
				cellRegister.setCellStyle(style);
				//责任顾问
				Cell cellOwner = row.createCell(cellIndex++);
				cellOwner.setCellValue(incident.getScLoginName());
				cellOwner.setCellStyle(style);
				//登记时间
				Cell cellRegisterDate = row.createCell(cellIndex++);
				cellRegisterDate.setCellValue(incident.getRegisteTime() == null ? ""
						: DateUtils.date2String(incident.getRegisteTime(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
				cellRegisterDate.setCellStyle(style);
				//计划完成时间
				Cell cellPlanFinishDate = row.createCell(cellIndex++);
				cellPlanFinishDate.setCellValue(incident.getDealDur2() == null ? ""
						: DateUtils.date2String(incident.getDealDur2(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
				cellPlanFinishDate.setCellStyle(style);
				//最近更新时间
				Cell cellLastUpdateDate = row.createCell(cellIndex++);
				cellLastUpdateDate.setCellValue(incident.getModifyDate() == null ? ""
						: DateUtils.date2String(incident.getModifyDate(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
				cellLastUpdateDate.setCellStyle(style);
				//实际完成时间
				Cell cellActualFinishDate = row.createCell(cellIndex++);
				cellActualFinishDate.setCellValue(incident.getFinishTime() == null ? ""
						: DateUtils.date2String(incident.getFinishTime(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
				cellActualFinishDate.setCellStyle(style);
				//影响度
				Cell cellAffect = row.createCell(cellIndex++);
				cellAffect.setCellValue(incident.getAffectVal());
				cellAffect.setCellStyle(style);
				//优先级
				Cell cellPriority = row.createCell(cellIndex++);
				cellPriority.setCellValue(incident.getPriorityVal());
				cellPriority.setCellStyle(style);
				//状态
				Cell cellState = row.createCell(cellIndex++);
				cellState.setCellValue(incident.getItStateVal());
				cellState.setCellStyle(style);
				//工时
				Cell cellTime = row.createCell(cellIndex++);
				if (incident.getRegisteTime() != null
						&& incident.getFinishTime() != null) {
					long diff = DateUtils.getDiffMinutes(
							incident.getFinishTime(), incident.getRegisteTime());
					double times = diff/60f;
					cellTime.setCellValue(String.format("%.2f", times));
				} else {
					cellTime.setCellValue("");
				}
				cellTime.setCellStyle(style);
				//公司
				Cell cellCompany = row.createCell(cellIndex++);
				cellCompany.setCellValue(incident.getCustName());
				cellCompany.setCellStyle(style);
				//地点
				Cell cellCompany2 = row.createCell(cellIndex++);
				cellCompany2.setCellValue(incident.getCustName());
				cellCompany2.setCellStyle(style);
				//归档标记
				String archiveFlag = incident.getArchiveFlag();
				if(StringUtils.isEmpty(archiveFlag)){
					//根本原因
					Cell cellRootcause = row.createCell(cellIndex++);
					cellRootcause.setCellValue("");
					cellRootcause.setCellStyle(style);
					//长期方案
					Cell cellLongTerm = row.createCell(cellIndex++);
					cellLongTerm.setCellValue("");
					cellLongTerm.setCellStyle(style);
					//重复问题
					Cell cellRepeating = row.createCell(cellIndex++);
					cellRepeating.setCellValue("");
					cellRepeating.setCellStyle(style);
					//ITC审查
					Cell cellITC = row.createCell(cellIndex++);
					cellITC.setCellValue("");
					cellITC.setCellStyle(style);
				}else{
					//根本原因
					Cell cellRootcause = row.createCell(cellIndex++);
					cellRootcause.setCellValue(archiveFlag.charAt(0)=='0'?"No":"Yes");
					cellRootcause.setCellStyle(style);
					//长期方案
					Cell cellLongTerm = row.createCell(cellIndex++);
					cellLongTerm.setCellValue(archiveFlag.charAt(1)=='0'?"No":"Yes");
					cellLongTerm.setCellStyle(style);
					//重复问题
					Cell cellRepeating = row.createCell(cellIndex++);
					cellRepeating.setCellValue(archiveFlag.charAt(2)=='0'?"No":"Yes");
					cellRepeating.setCellStyle(style);
					//ITC审查
					Cell cellITC = row.createCell(cellIndex++);
					cellITC.setCellValue(archiveFlag.charAt(3)=='0'?"No":"Yes");
					cellITC.setCellStyle(style);
				}
				//满意度
				Cell cellSatis = row.createCell(cellIndex++);
				cellSatis.setCellValue(incident.getFeedbackVal());
				cellSatis.setCellStyle(style);
				startRow++;
			}
			response.setContentType("application/octet-stream");
			response.addHeader(
					"Content-Disposition",
					"attachment;filename="
							+ new String(("ams-report[" + dateRange + "].xlsx")
									.getBytes("gb2312"), "ISO8859-1"));
			workbook.write(response.getOutputStream());
		} catch (Exception e) {
			log.error("", e);
			response.getWriter()
					.print("<script type='text/javascript'>alert('Report failure!Contact administrator,please!')</script>");
		} finally {
			if (in != null) {
				in.close();
			}
		}
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
			qi.setRegisterTimeEnd(DateUtils.dateOffset(DateUtils.string2Date(
					registerTimeEnd, DateUtils.FORMATTYPE_yyyy_MM_dd),
					Calendar.DAY_OF_YEAR, 1));
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

	/**
	 * 将一条事件置为处理中
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/back2Process")
	public void back2Process(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OpInfo oi = SessionUtil.getOpInfo();
		long incidentId = VarTypeConvertUtils.string2Long(request
				.getParameter("incidentId"));
		incidentService.MBLAdminSetProccess(incidentId, oi);
	}
	
	public static void main(String[] args) {
		double d = 18/60f;
		System.out.println(String.format("%.2f", d));
	}
}
