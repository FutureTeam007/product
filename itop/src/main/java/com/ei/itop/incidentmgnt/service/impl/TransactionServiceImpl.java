/**
 * 
 */
package com.ei.itop.incidentmgnt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.dao.ICommonDAO;
import com.ei.itop.common.dbentity.IcIncident;
import com.ei.itop.common.dbentity.IcTransaction;
import com.ei.itop.incidentmgnt.bean.IncidentInfo;
import com.ei.itop.incidentmgnt.bean.TransactionInfo;
import com.ei.itop.incidentmgnt.service.IIncidentService;
import com.ei.itop.incidentmgnt.service.ITransactionService;

/**
 * @author Jack.Qi
 * 
 */
@Service(value = "transactionService")
public class TransactionServiceImpl implements ITransactionService {

	private static final Logger log = Logger
			.getLogger(TransactionServiceImpl.class);

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, IcTransaction> transactionDAO;

	@Resource(name = "commonDDLDAO")
	private ICommonDAO commonDAO;

	@Resource(name = "incidentService")
	private IIncidentService incidentService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.ITransactionService#queryTransaction
	 * (long, long)
	 */
	public List<IcTransaction> MBLQueryTransaction(long incidentId,
			OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub
		return null;
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

		// 获得事务创建者信息
		// 补全事务所处阶段

		long transactionId = transactionDAO.save("IC_TRANSACTION.insert",
				transactionInfo);

		// 保存附件信息

		return transactionId;
	}

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
			// 数据库缺当前处理顾问的姓名
			// ii.setIcObjectName(incident.);

			// 不需调整事件所处阶段

			// 调整事件状态
			ii.setItStateCode("3");
			ii.setItStateVal("处理中");

			// 修改事件
			incidentService.modifyIncidentAndAttach(incidentId, ii, opInfo);

			// 设置事务分类
			transactionInfo.setTransType("流程事务-补充资料");
		}
		// 非当前干系人
		else {
			// 不需要操作事件

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

			// 不需调整事件状态

			// 修改事件
			incidentService.modifyIncidentAndAttach(incidentId, ii, opInfo);

			// 设置事务分类
			transactionInfo.setTransType("流程事务-顾问处理");
		}
		// 非当前干系人
		else {
			// 不需要操作事件

			// 设置事务分类
			transactionInfo.setTransType("其他事务");
		}

		// 新增事务信息
		long transactionId = addTransaction(incidentId, transactionInfo, opInfo);

		// 记录操作日志

		return transactionId;
	}

	/**
	 * 新增事务信息
	 * 
	 * @param transactionInfo
	 *            事务信息
	 * @return 事务ID
	 * @throws Exception
	 */
	private long addTransaction1(TransactionInfo transactionInfo)
			throws Exception {

		// 保存事务信息
		long transactionId = transactionDAO.save("IC_TRANSACTION.insert",
				transactionInfo);

		// 保存附件信息

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

			// 需调整事件干系人为用户
			ii.setIcObjectType("USER");
			ii.setIcObjectId(incident.getCcUserId());
			ii.setIcLoginCode(incident.getCcLoginCode());
			// 数据库缺事件提出用户的姓名
			// ii.setIcObjectName(incident.);

			// 不需调整事件所处阶段

			// 需调整事件状态为客户处理中
			ii.setItStateCode("4");
			ii.setItStateVal("客户处理中");

			// 修改事件
			incidentService.modifyIncidentAndAttach(incidentId, ii, opInfo);

			// 设置事务分类
			transactionInfo.setTransType("流程事务-补充资料");
		}
		// 非当前干系人
		else {
			throw new Exception("只有事件当前干系顾问才能进行需用户补充资料操作");
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

			// 需调整负责顾问为备注安排人
			ii.setScOpId(nextOpInfo.getOpId());
			ii.setScLoginCode(nextOpInfo.getOpCode());
			// 缺负责顾问姓名
			// *******************
			// ii.set

			// 需调整事件所处阶段
			// *******************

			// 需调整事件状态为处理中
			ii.setItStateCode("3");
			ii.setItStateVal("处理中");

			// 修改事件
			incidentService.modifyIncidentAndAttach(incidentId, ii, opInfo);

			// 设置事务分类
			transactionInfo.setTransType("流程事务-转派");
		}
		// 非当前干系人
		else {
			throw new Exception("只有事件当前干系顾问才能进行转派操作");
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
			throw new Exception("只有状态为待响应及顾问处理中的事件才能挂起");
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

			// 设置事务分类
			transactionInfo.setTransType("流程事务-转派");
		}
		// 非当前干系人
		else {
			throw new Exception("只有事件当前干系顾问才能进行挂起操作");
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
		return 0;
	}

}
