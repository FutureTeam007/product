package com.ei.itop.incidentmgnt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ailk.dazzle.util.json.JSONUtils;
import com.ailk.dazzle.util.type.ArrayUtils;
import com.ailk.dazzle.util.type.StringUtils;
import com.ailk.dazzle.util.type.VarTypeConvertUtils;
import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.dbentity.IcAttach;
import com.ei.itop.common.dbentity.IcTransaction;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.incidentmgnt.bean.IncidentInfo;
import com.ei.itop.incidentmgnt.bean.TransactionInfo;
import com.ei.itop.incidentmgnt.service.AttachService;
import com.ei.itop.incidentmgnt.service.TransactionService;

@Controller
@RequestMapping("/trans")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AttachService attachService;
	
	@RequestMapping("/list")
	public void queryTransList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		long incidentId = VarTypeConvertUtils.string2Long(request.getParameter("incidentId"));
		List<TransactionInfo> data = transactionService.MBLQueryTransaction(incidentId, SessionUtil.getOpInfo());
		String jsonData = JSONUtils.toJSONString(data);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(jsonData);
	}
	
	@RequestMapping("/commit")
	public void addTrans(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//获取操作代码
		int xcode = VarTypeConvertUtils.string2Integer(request.getParameter("xcode"));
		//获取事件ID
		long incidentId = VarTypeConvertUtils.string2Long(request.getParameter("incidentId"));
		//获取事务内容
		String contents = request.getParameter("transDesc");
		//获取事务附件
		String attachList = request.getParameter("attachList");
		//创建一个事务信息对象
		TransactionInfo ti = new TransactionInfo();
		ti.setIcIncidentId(incidentId);
		ti.setContents(contents);
		//设置附件
		if (!StringUtils.isEmpty(attachList)) {
			List<IcAttach> attachs = attachService.queryAttachList(ArrayUtils
					.stringArray2LongArray(attachList.split(",")));
			ti.setAttachList(attachs);
		}
		//获取操作员信息
		OpInfo opInfo = SessionUtil.getOpInfo();
		//判断操作代码执行相应业务逻辑
		switch(xcode){
			case -1://用户提交一条普通事务
				transactionService.MBLUserCommitTransaction(incidentId, ti, opInfo);
				break;
			case 0://顾问提交一条普通事务
				transactionService.MBLAdviserCommitTransaction(incidentId, ti, opInfo);
				break;
			case 1://顾问提交一条转派事务
				long opId = VarTypeConvertUtils.string2Long(request.getParameter("opId"));
				String opCode = request.getParameter("opCode");
				String opName = request.getParameter("opName");
				OpInfo nextOpInfo = SessionUtil.getOpInfo();
				nextOpInfo.setOpId(opId);
				nextOpInfo.setOpCode(opCode);
				nextOpInfo.setOpName(opName);
				nextOpInfo.setOpType("OP");
				transactionService.MBLAdviserTransferTransaction(incidentId, ti, nextOpInfo, opInfo);
				break;
			case 2://顾问提交一条客户补充资料事务
				transactionService.MBLNeedAdditionalInfo(incidentId, ti, opInfo);
				break;
			case 3://顾问提交一条挂起事务
				transactionService.MBLAdviserHangUpTransaction(incidentId, ti, opInfo);
				break;
			case 4://顾问提交一条完成事务
				IncidentInfo ii = new IncidentInfo();
				ii.setItSolution(contents);
				ii.setIcIncidentId(incidentId);
				transactionService.MBLAdviserCompleteTransaction(incidentId, ii, ti, opInfo);
				break;
			default:break;
		}
	}
	
}
