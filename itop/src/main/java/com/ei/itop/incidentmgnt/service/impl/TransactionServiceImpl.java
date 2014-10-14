/**
 * 
 */
package com.ei.itop.incidentmgnt.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.ailk.dazzle.util.ibatis.GenericDAO;
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
public class TransactionServiceImpl implements ITransactionService {

	private static final Logger log = Logger
			.getLogger(TransactionServiceImpl.class);

	private GenericDAO<Long, IcTransaction> transactionDAO;

	private ICommonDAO commonDAO;

	private IIncidentService incidentService;

	/**
	 * @return the incidentService
	 */
	public IIncidentService getIncidentService() {
		return incidentService;
	}

	/**
	 * @param incidentService
	 *            the incidentService to set
	 */
	public void setIncidentService(IIncidentService incidentService) {
		this.incidentService = incidentService;
	}

	/**
	 * @return the transactionDAO
	 */
	public GenericDAO<Long, IcTransaction> getTransactionDAO() {
		return transactionDAO;
	}

	/**
	 * @param transactionDAO
	 *            the transactionDAO to set
	 */
	public void setTransactionDAO(GenericDAO<Long, IcTransaction> transactionDAO) {
		this.transactionDAO = transactionDAO;
	}

	/**
	 * @return the commonDAO
	 */
	public ICommonDAO getCommonDAO() {
		return commonDAO;
	}

	/**
	 * @param commonDAO
	 *            the commonDAO to set
	 */
	public void setCommonDAO(ICommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.ITransactionService#queryTransaction
	 * (long, long)
	 */
	public List<IcTransaction> queryTransaction(long incidentId, long opId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.ITransactionService#userCommitTransaction
	 * (long, com.ei.itop.incidentmgnt.bean.TransactionInfo, long)
	 */
	public long userCommitTransaction(long incidentId,
			TransactionInfo transactionInfo, long opId) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.ITransactionService#adviserCommitTransaction
	 * (long, com.ei.itop.incidentmgnt.bean.TransactionInfo, long)
	 */
	public long adviserCommitTransaction(long incidentId,
			TransactionInfo transactionInfo, long opId) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.ITransactionService#adviserCommitTransaction
	 * (long, com.ei.itop.incidentmgnt.bean.IncidentInfo,
	 * com.ei.itop.incidentmgnt.bean.TransactionInfo, long)
	 */
	public long adviserCommitTransaction(long incidentId,
			IncidentInfo incidentInfo, TransactionInfo transactionInfo,
			long opId) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 新增事务信息
	 * 
	 * @param transactionInfo
	 *            事务信息
	 * @return 事务ID
	 * @throws Exception
	 */
	private long addTransaction(TransactionInfo transactionInfo)
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
	public long needAdditionalInfo(long incidentId,
			TransactionInfo transactionInfo, long opId) throws Exception {
		// TODO Auto-generated method stub

		// 查询事件信息
		IcIncident incident = incidentService.queryIncident(incidentId);

		// 如果不是干系人，则不允许操作

		// 修改事件当前处理人为事件提出用户
		// 修改事件状态为客户处理中
		incident.setItStateCode("4");
		incident.setItStateVal("客户处理中");
		// 修改事件最新更新时间

		// 保存事务记录
		long transactionId = addTransaction(transactionInfo);

		return transactionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.incidentmgnt.service.ITransactionService#
	 * adviserTransferTransaction(long,
	 * com.ei.itop.incidentmgnt.bean.TransactionInfo, long)
	 */
	public long adviserTransferTransaction(long incidentId,
			TransactionInfo transactionInfo, long nextOpId, long opId)
			throws Exception {
		// TODO Auto-generated method stub

		// 查询事件信息
		IcIncident incident = incidentService.queryIncident(incidentId);

		// 如果不是干系人，则不允许操作

		// 查询被转派者的岗位及层级（一线业务、二线技术等）

		// 修改事件当前处理人为被转派者
		// 修改事件负责顾问为被转派者
		// 修改事件所处阶段
		// 修改事件状态为处理中
		incident.setItStateCode("3");
		incident.setItStateVal("处理中");
		// 修改事件最新更新时间
		incident.setModifyDate(commonDAO.getSysDate());

		// 保存事务记录
		long transactionId = addTransaction(transactionInfo);

		return transactionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.ITransactionService#adviserHangUpTransaction
	 * (long, com.ei.itop.incidentmgnt.bean.TransactionInfo, long)
	 */
	public long adviserHangUpTransaction(long incidentId,
			TransactionInfo transactionInfo, long opId) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.incidentmgnt.service.ITransactionService#
	 * adviserCompleteTransaction(long,
	 * com.ei.itop.incidentmgnt.bean.IncidentInfo,
	 * com.ei.itop.incidentmgnt.bean.TransactionInfo, long)
	 */
	public long adviserCompleteTransaction(long incidentId,
			IncidentInfo incidentInfo, TransactionInfo transactionInfo,
			long opId) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
