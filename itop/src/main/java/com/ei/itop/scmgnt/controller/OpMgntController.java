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
}
