/**
 * 
 */
package com.ei.itop.incidentmgnt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.AppContext;
import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.dao.CommonDAO;
import com.ei.itop.common.dbentity.IcAttach;
import com.ei.itop.common.dbentity.IcIncident;
import com.ei.itop.common.dbentity.IcTransaction;
import com.ei.itop.incidentmgnt.bean.IncidentInfo;
import com.ei.itop.incidentmgnt.bean.TransactionInfo;
import com.ei.itop.incidentmgnt.service.AttachService;
import com.ei.itop.incidentmgnt.service.IncidentService;
import com.ei.itop.incidentmgnt.service.TransactionService;

/**
 * @author Jack.Qi
 * 
 */
@Service(value = "transactionService")
public class TransactionServiceImpl implements TransactionService {

	private static final Logger log = Logger
			.getLogger(TransactionServiceImpl.class);

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, IcTransaction> transactionDAO;

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, TransactionInfo> transactionInfoDAO;

	@Resource(name = "commonDDLDAO")
	private CommonDAO commonDAO;

	@Resource(name = "incidentService")
	private IncidentService incidentService;

	@Resource(name = "attachService")
	private AttachService attachService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.ITransactionService#queryTransaction
	 * (long, long)
	 */
	public List<TransactionInfo> MBLQueryTransaction(long incidentId,
			OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("incidentId", incidentId);

		// 查询事务信息
		List<TransactionInfo> transactionInfoList = transactionInfoDAO
				.findByParams("IC_TRANSACTION.queryTransactionByIncident", hm);

		// 挨个查询事务附件，此逻辑后续可调整为一次查出全部事务的附件，在内存中匹配事务
		for (int i = 0; transactionInfoList != null
				&& i < transactionInfoList.size(); i++) {
			TransactionInfo transactionInfo = new TransactionInfo();

			List<IcAttach> attachList = attachService.getAttachList(incidentId,
					transactionInfo.getTransId());

			transactionInfo.setAttachList(attachList);
		}

		// 记录操作日志

		return transactionInfoList;
	}

	/**
	 * 新增事务信息
	 * 
	 * @param incidentId
	 * @param transactionInfo
	 * @param opInfo
	 * @return
	 * @throws Exception
	 */
	public long addTransaction(long incidentId,
			TransactionInfo transactionInfo, OpInfo opInfo) throws Exception {

		// 自动设置事件ID
		transactionInfo.setIcIncidentId(incidentId);

		// 自动设置事务创建者信息
		transactionInfo.setObjectType(opInfo.getOpType());
		transactionInfo.setObjectId(opInfo.getOpId());
		transactionInfo.setLoginCode(opInfo.getOpCode());
		transactionInfo.setObjectName(opInfo.getOpName());

		long transactionId = transactionDAO.save("IC_TRANSACTION.insert",
				transactionInfo);

		// 保存附件信息
		attachService.saveAttach(incidentId, transactionId,
				transactionInfo.getAttachList());

		return transactionId;
	}

	/**
	 * 判断是否为当前干系人
	 * 
	 * @param incident
	 * @param opType
	 * @param opId
	 * @return
	 * @throws Exception
	 */
	protected boolean isCurrentOp(IcIncident incident, String opType, long opId)
			throws Exception {

		// 判断是否为干系人操作
		boolean isCurrentOp = false;
		if (incident.getIcObjectType().equals(opType)
				&& incident.getIcObjectId() == opId) {
			isCurrentOp = true;
		}

		return isCurrentOp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.ITransactionService#userCommitTransaction
	 * (long, com.ei.itop.incidentmgnt.bean.TransactionInfo, long)
	 */
	public long MBLUserCommitTransaction(long incidentId,
			TransactionInfo transactionInfo, OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub

		// 取得事件信息
		IcIncident incident = incidentService.queryIncident(incidentId);

		// 是当前干系人
		if (isCurrentOp(incident, opInfo.getOpType(), opInfo.getOpId())) {
			// 需要修改部分事件信息
			IncidentInfo ii = new IncidentInfo();

			// 调整事件干系人为事件当前处理顾问
			ii.setIcObjectType("OP");
			ii.setIcObjectId(incident.getScOpId());
			ii.setIcLoginCode(incident.getScLoginCode());
			ii.setIcObjectName(incident.getScLoginName());

			// 不需调整事件所处阶段

			// 调整事件状态
			ii.setItStateCode("3");
			ii.setItStateVal("顾问处理中");

			// 修改事件
			incidentService.modifyIncidentAndAttach(incidentId, ii, opInfo);

			// 设置事务的事件所处阶段
			transactionInfo.setItPhase(incident.getItPhase());

			// 设置事务分类
			transactionInfo.setTransType("流程事务-补充资料");
		}
		// 非当前干系人
		else {
			// 不需要操作事件

			// 设置事务的事件所处阶段
			transactionInfo.setItPhase(incident.getItPhase());

			// 设置事务分类
			transactionInfo.setTransType("其他事务");
		}

		// 新增事务信息
		long transactionId = addTransaction(incidentId, transactionInfo, opInfo);

		// 记录操作日志

		return transactionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.ITransactionService#adviserCommitTransaction
	 * (long, com.ei.itop.incidentmgnt.bean.TransactionInfo, long)
	 */
	public long MBLAdviserCommitTransaction(long incidentId,
			TransactionInfo transactionInfo, OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub

		// 取得事件信息
		IcIncident incident = incidentService.queryIncident(incidentId);

		// 是当前干系人
		if (isCurrentOp(incident, opInfo.getOpType(), opInfo.getOpId())) {
			// 需要修改部分事件信息
			IncidentInfo ii = new IncidentInfo();

			// 不需调整事件干系人

			// 不需调整事件所处阶段

			// 调整事件状态
			ii.setItStateCode("3");
			ii.setItStateVal("顾问处理中");

			// 修改事件
			incidentService.modifyIncidentAndAttach(incidentId, ii, opInfo);

			// 设置事务的事件所处阶段
			transactionInfo.setItPhase(incident.getItPhase());

			// 设置事务分类
			transactionInfo.setTransType("流程事务-顾问处理中");
		}
		// 非当前干系人
		else {
			// 不需要操作事件

			// 设置事务的事件所处阶段
			transactionInfo.setItPhase(incident.getItPhase());

			// 设置事务分类
			transactionInfo.setTransType("其他事务");
		}

		// 新增事务信息
		long transactionId = addTransaction(incidentId, transactionInfo, opInfo);

		// 记录操作日志

		return transactionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.ITransactionService#needAdditionalInfo
	 * (long, com.ei.itop.incidentmgnt.bean.TransactionInfo, long)
	 */
	public long MBLNeedAdditionalInfo(long incidentId,
			TransactionInfo transactionInfo, OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub

		// 取得事件信息
		IcIncident incident = incidentService.queryIncident(incidentId);

		// 是当前干系人
		if (isCurrentOp(incident, opInfo.getOpType(), opInfo.getOpId())) {
			// 需要修改部分事件信息
			IncidentInfo ii = new IncidentInfo();

			// 需调整事件干系人为事件所属人
			ii.setIcObjectType(incident.getIcOwnerType());
			ii.setIcObjectId(incident.getIcOwnerId());
			ii.setIcLoginCode(incident.getIcOwnerCode());
			ii.setIcObjectName(incident.getIcOwnerName());

			// 不需调整事件所处阶段

			// 需调整事件状态为客户处理中
			ii.setItStateCode("4");
			ii.setItStateVal("客户处理中");

			// 修改事件
			incidentService.modifyIncidentAndAttach(incidentId, ii, opInfo);

			// 设置事务的事件所处阶段
			transactionInfo.setItPhase(incident.getItPhase());

			// 设置事务分类
			transactionInfo.setTransType("流程事务-补充资料");
		}
		// 非当前干系人
		else {
			throw new BizException("只有事件当前干系顾问才能进行需用户补充资料操作");
		}

		// 新增事务信息
		long transactionId = addTransaction(incidentId, transactionInfo, opInfo);

		// 记录操作日志

		return transactionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.incidentmgnt.service.ITransactionService#
	 * adviserTransferTransaction(long,
	 * com.ei.itop.incidentmgnt.bean.TransactionInfo, long)
	 */
	public long MBLAdviserTransferTransaction(long incidentId,
			TransactionInfo transactionInfo, OpInfo nextOpInfo, OpInfo opInfo)
			throws Exception {
		// TODO Auto-generated method stub

		// 取得事件信息
		IcIncident incident = incidentService.queryIncident(incidentId);

		// 是当前干系人
		if (isCurrentOp(incident, opInfo.getOpType(), opInfo.getOpId())) {
			// 需要修改部分事件信息
			IncidentInfo ii = new IncidentInfo();

			// 需调整事件干系人为被转派人
			ii.setIcObjectId(nextOpInfo.getOpId());
			ii.setIcLoginCode(nextOpInfo.getOpCode());
			ii.setIcObjectName(nextOpInfo.getOpName());

			// 需调整负责顾问为被转派人
			ii.setScOpId(nextOpInfo.getOpId());
			ii.setScLoginCode(nextOpInfo.getOpCode());
			ii.setScLoginName(nextOpInfo.getOpName());

			// 需调整事件所处阶段
			String itPhase = incidentService.getItPhase(incident.getScOrgId(),
					incident.getCcCustId(), incident.getScProductId(),
					nextOpInfo.getOpId());
			ii.setItPhase(itPhase);

			// 需调整事件状态为处理中
			ii.setItStateCode("3");
			ii.setItStateVal("顾问处理中");

			// 修改事件
			incidentService.modifyIncidentAndAttach(incidentId, ii, opInfo);

			// 设置事务的事件所处阶段
			transactionInfo.setItPhase(itPhase);

			// 设置事务分类
			transactionInfo.setTransType("流程事务-转派");
		}
		// 非当前干系人
		else {
			throw new BizException("只有事件当前干系顾问才能进行转派操作");
		}

		// 新增事务信息
		long transactionId = addTransaction(incidentId, transactionInfo, opInfo);

		// 记录操作日志

		return transactionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.ITransactionService#adviserHangUpTransaction
	 * (long, com.ei.itop.incidentmgnt.bean.TransactionInfo, long)
	 */
	public long MBLAdviserHangUpTransaction(long incidentId,
			TransactionInfo transactionInfo, OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub

		// 取得事件信息
		IcIncident incident = incidentService.queryIncident(incidentId);

		// 只有2-待响应、3-顾问处理中的事件才能挂起
		if (!("2".equals(incident.getItStateCode()) || "3".equals(incident
				.getItStateCode()))) {
			throw new BizException("只有状态为待响应及顾问处理中的事件才能挂起");
		}

		// 是当前干系人
		if (isCurrentOp(incident, opInfo.getOpType(), opInfo.getOpId())) {
			// 需要修改部分事件信息
			IncidentInfo ii = new IncidentInfo();

			// 不需调整事件干系人为被转派人

			// 不需调整负责顾问为备注安排人

			// 不需调整事件所处阶段

			// 需调整事件状态为已挂起
			ii.setItStateCode("5");
			ii.setItStateVal("已挂起");

			// 修改事件
			incidentService.modifyIncidentAndAttach(incidentId, ii, opInfo);

			// 设置事务的事件所处阶段
			transactionInfo.setItPhase(incident.getItPhase());

			// 设置事务分类
			transactionInfo.setTransType("流程事务-挂起");
		}
		// 非当前干系人
		else {
			throw new BizException("只有事件当前干系顾问才能进行挂起操作");
		}

		// 新增事务信息
		long transactionId = addTransaction(incidentId, transactionInfo, opInfo);

		// 记录操作日志

		return transactionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.incidentmgnt.service.ITransactionService#
	 * adviserCompleteTransaction(long,
	 * com.ei.itop.incidentmgnt.bean.IncidentInfo,
	 * com.ei.itop.incidentmgnt.bean.TransactionInfo, long)
	 */
	public long MBLAdviserCompleteTransaction(long incidentId,
			IncidentInfo incidentInfo, TransactionInfo transactionInfo,
			OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub

		// 取得事件信息
		IcIncident incident = incidentService.queryIncident(incidentId);

		// 只有3-顾问处理中的事件才能设置事件完成
		if (!"3".equals(incident.getItStateCode())) {
			throw new BizException("只有状态为顾问处理中的事件才能进行完成操作");
		}

		// 判断时间结束代码是否填写
		if (incidentInfo.getFinishCode() == null
				|| "".equals(incidentInfo.getFinishCode())
				|| incidentInfo.getFinishVal() == null
				|| "".equals(incidentInfo.getFinishVal())) {
			throw new BizException("必须填写事件结束代码");
		}

		// 是当前干系人
		if (isCurrentOp(incident, opInfo.getOpType(), opInfo.getOpId())) {
			// 需要修改部分事件信息
			IncidentInfo ii = new IncidentInfo();

			// 不需调整事件干系人

			// 不需调整负责顾问

			// 不需调整事件所处阶段

			// 需调整事件状态为已挂起
			ii.setItStateCode("8");
			ii.setItStateVal("已完成");

			// 需补全完成操作的业务信息
			ii.setFinishTime(commonDAO.getSysDate());
			ii.setFinishCode(incidentInfo.getFinishCode());
			ii.setFinishVal(incidentInfo.getFinishVal());
			ii.setRestoreTime(incidentInfo.getRestoreTime());
			ii.setItSolution(transactionInfo.getContents());
			// 填入实际响应时长、实际处理时长等信息
			// *******************
			// ii.setDealDur(dealDur);

			// 修改事件
			incidentService.modifyIncidentAndAttach(incidentId, ii, opInfo);

			// 设置事务的事件所处阶段
			transactionInfo.setItPhase(incident.getItPhase());

			// 设置事务分类
			transactionInfo.setTransType("流程事务-完成");
		}
		// 非当前干系人
		else {
			throw new BizException("只有事件当前干系顾问才能进行事件完成操作");
		}

		// 新增事务信息
		long transactionId = addTransaction(incidentId, transactionInfo, opInfo);

		// 记录操作日志

		return transactionId;
	}

	public static void main(String[] args) throws Exception {
		TransactionService ts = (TransactionService) AppContext
				.getBean("transactionService");

		OpInfo oi = new OpInfo();
		oi.setOpType("OP");
		oi.setOpId(0);
		oi.setOpCode("SamZhang@163.com");
		oi.setOpName("Sam.Zhang（张三）");

		// ts.MBLQueryTransaction(10027, oi);

		TransactionInfo ti = new TransactionInfo();
		ti.setContents("事务内容XXX");
		List<IcAttach> attachList = new ArrayList<IcAttach>();
		IcAttach attach1 = new IcAttach();
		attach1.setAttachPath("/data/a1.txt");
		IcAttach attach2 = new IcAttach();
		attach2.setAttachPath("/data/b1.doc");
		attachList.add(attach1);
		attachList.add(attach2);
		ti.setAttachList(attachList);

		// ts.addTransaction(10026, ti, oi);

		// 用户提交事务-非流程
		// oi.setOpType("USER");
		// ts.MBLUserCommitTransaction(10026, ti, oi);

		// 顾问提交事务-非流程
		// ts.MBLAdviserCommitTransaction(10026, ti, oi);
		// 顾问提交事务-流程
		// oi.setOpType("OP");
		// oi.setOpId(200005);
		// oi.setOpCode("SP200005");
		// oi.setOpName("EI-PM");
		// ts.MBLAdviserCommitTransaction(10026, ti, oi);

		// 需补全资料
		// oi.setOpType("OP");
		// oi.setOpId(200005);
		// oi.setOpCode("SP200005");
		// oi.setOpName("EI-PM");
		// ts.MBLNeedAdditionalInfo(10026, ti, oi);

		// 用户提交事务-非流程
		// oi.setOpType("USER");
		// ts.MBLUserCommitTransaction(10026, ti, oi);

		// 用户提交事务-流程
		// oi.setOpType("USER");
		// oi.setOpId(9001);
		// oi.setOpCode("ITOPCNH1@163.com");
		// oi.setOpName("itop1-cnh");
		// ts.MBLUserCommitTransaction(10026, ti, oi);

		// 顾问转派事务
		// oi.setOpType("OP");
		// oi.setOpId(200005);
		// oi.setOpCode("SP200005");
		// oi.setOpName("EI-PM");
		// OpInfo nextOpInfo = new OpInfo();
		// nextOpInfo.setOpType("OP");
		// nextOpInfo.setOpId(200002);
		// nextOpInfo.setOpCode("SP200002");
		// nextOpInfo.setOpName("ITOPEI2-EI");
		// ts.MBLAdviserTransferTransaction(10026, ti, nextOpInfo, oi);

		// 顾问挂起事务
		// oi.setOpType("OP");
		// oi.setOpId(200002);
		// oi.setOpCode("SP200002");
		// oi.setOpName("ITOPEI2-EI");
		// ts.MBLAdviserHangUpTransaction(10026, ti, oi);

		// 顾问转派事务
		// oi.setOpType("OP");
		// oi.setOpId(200002);
		// oi.setOpCode("SP200002");
		// oi.setOpName("ITOPEI2-EI");
		// OpInfo nextOpInfo = new OpInfo();
		// nextOpInfo.setOpType("OP");
		// nextOpInfo.setOpId(200005);
		// nextOpInfo.setOpCode("SP200005");
		// nextOpInfo.setOpName("EI-PM");
		// ts.MBLAdviserTransferTransaction(10026, ti, nextOpInfo, oi);

		// // 顾问挂起事务
		// oi.setOpType("OP");
		// oi.setOpId(200005);
		// oi.setOpCode("SP200005");
		// oi.setOpName("EI-PM");
		// ts.MBLAdviserHangUpTransaction(10026, ti, oi);

		// // 顾问提交事务-非流程
		// ts.MBLAdviserCommitTransaction(10026, ti, oi);

		// 顾问提交事务-流程
		// oi.setOpType("OP");
		// oi.setOpId(200005);
		// oi.setOpCode("SP200005");
		// oi.setOpName("EI-PM");
		// ts.MBLAdviserCommitTransaction(10026, ti, oi);

		// 顾问完成事务
		// IncidentInfo incidentInfo = new IncidentInfo();
		// incidentInfo.setFinishCode("4");
		// incidentInfo.setFinishVal("消失");
		// oi.setOpType("OP");
		// oi.setOpId(200005);
		// oi.setOpCode("SP200005");
		// oi.setOpName("EI-PM");
		// ts.MBLAdviserCompleteTransaction(10026, incidentInfo, ti, oi);
	}
}
