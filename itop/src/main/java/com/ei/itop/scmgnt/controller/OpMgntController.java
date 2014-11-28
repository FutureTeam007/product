package com.ei.itop.scmgnt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.dazzle.util.json.JSONUtils;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ailk.dazzle.util.web.ActionUtils;
import com.ei.itop.common.dbentity.ScOp;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.scmgnt.service.OpService;

@Controller
@RequestMapping("/scmgnt/op")
public class OpMgntController {

	private static final Logger log = Logger.getLogger(OpMgntController.class);

	@Autowired
	private OpService opService;

	@RequestMapping("/list/all")
	public void queryAllOpList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long orgId = SessionUtil.getOpInfo().getOrgId();
		int page = VarTypeConvertUtils.string2Integer(
				request.getParameter("page"), 0);
		// 获取分页大小
		int pageSize = VarTypeConvertUtils.string2Integer(
				request.getParameter("rows"), 0);
		// 获取分页起始位置
		long startIndex = (page - 1) * pageSize + 1;
		// 获得查询条件
		String loginCode = request.getParameter("loginCode");
		if (StringUtils.isEmpty(loginCode)) {
			loginCode = null;
		} else {
			loginCode = ActionUtils.transParam(loginCode, "UTF-8");
		}
		String opName = request.getParameter("opName");
		if (StringUtils.isEmpty(opName)) {
			opName = null;
		} else {
			opName = ActionUtils.transParam(opName, "UTF-8");
		}
		// 获取数据条数
		long count = opService.queryAllOpListCount(orgId, loginCode, opName);
		// 获取数据
		List<ScOp> ops = opService.queryAllOpList(orgId, loginCode, opName,
				startIndex, pageSize);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", count);
		result.put("rows", ops);
		String jsonData = JSONUtils.toJSONString(result);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}

	@RequestMapping("/opadd")
	public void opAdd(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		long orgId = SessionUtil.getOpInfo().getOrgId();

		ScOp op = new ScOp();
		op.setScOrgId(orgId);
		op.setOpCode(null);
		String loginCode = ActionUtils.transParamDecode(
				request.getParameter("loginCode"), "UTF-8");
		op.setLoginCode(loginCode);
		String loginPasswd = ActionUtils.transParamDecode(
				request.getParameter("loginPasswd"), "UTF-8");
		op.setLoginPasswd(loginPasswd);
		String firstName = ActionUtils.transParamDecode(
				request.getParameter("firstName"), "UTF-8");
		op.setFirstName(firstName);
		String lastName = ActionUtils.transParamDecode(
				request.getParameter("lastName"), "UTF-8");
		op.setLastName(lastName);
		String opName = ActionUtils.transParamDecode(
				request.getParameter("opName"), "UTF-8");
		op.setOpName(opName);
		short gender = VarTypeConvertUtils.string2Short(request
				.getParameter("gender"));
		op.setGender(gender);
		short opKind = VarTypeConvertUtils.string2Short(request
				.getParameter("opKind"));
		op.setOpKind(opKind);
		String mobileNo = ActionUtils.transParamDecode(
				request.getParameter("mobileNo"), "UTF-8");
		op.setMobileNo(mobileNo);
		String officeTel = ActionUtils.transParamDecode(
				request.getParameter("officeTel"), "UTF-8");
		op.setOfficeTel(officeTel);
		op.setNotes(null);
		short state = VarTypeConvertUtils.string2Short(request
				.getParameter("state"));
		op.setState(state);

		opService.addOp(op);
	}

	@RequestMapping("/opmodify")
	public void opModify(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		long opId = VarTypeConvertUtils.string2Long(request
				.getParameter("scOpId"));

		ScOp op = new ScOp();
		op.setOpCode(null);
		String loginCode = ActionUtils.transParamDecode(
				request.getParameter("loginCode"), "UTF-8");
		op.setLoginCode(loginCode);
		String loginPasswd = ActionUtils.transParamDecode(
				request.getParameter("loginPasswd"), "UTF-8");
		op.setLoginPasswd(loginPasswd);
		String firstName = ActionUtils.transParamDecode(
				request.getParameter("firstName"), "UTF-8");
		op.setFirstName(firstName);
		String lastName = ActionUtils.transParamDecode(
				request.getParameter("lastName"), "UTF-8");
		op.setLastName(lastName);
		String opName = ActionUtils.transParamDecode(
				request.getParameter("opName"), "UTF-8");
		op.setOpName(opName);
		short gender = VarTypeConvertUtils.string2Short(request
				.getParameter("gender"));
		op.setGender(gender);
		short opKind = VarTypeConvertUtils.string2Short(request
				.getParameter("opKind"));
		op.setOpKind(opKind);
		String mobileNo = ActionUtils.transParamDecode(
				request.getParameter("mobileNo"), "UTF-8");
		op.setMobileNo(mobileNo);
		String officeTel = ActionUtils.transParamDecode(
				request.getParameter("officeTel"), "UTF-8");
		op.setOfficeTel(officeTel);
		op.setNotes(null);
		short state = VarTypeConvertUtils.string2Short(request
				.getParameter("state"));
		op.setState(state);

		opService.modifyOp(opId, op);
	}

	@RequestMapping("/modifystate")
	public void modifyState(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		long opId = VarTypeConvertUtils.string2Long(request
				.getParameter("opId"));

		ScOp op = new ScOp();
		op.setScOpId(opId);

		short state = VarTypeConvertUtils.string2Short(request
				.getParameter("state"));
		op.setState(state);

		opService.modifyOpState(opId, op);
	}
}
