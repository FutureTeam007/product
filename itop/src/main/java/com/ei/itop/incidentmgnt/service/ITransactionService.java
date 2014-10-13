/**
 * 
 */
package com.ei.itop.incidentmgnt.service;

import java.util.List;

import com.ei.itop.common.dbentity.IcTransaction;
import com.ei.itop.incidentmgnt.bean.IncidentInfo;
import com.ei.itop.incidentmgnt.bean.TransactionInfo;

/**
 * @author Jack.Qi
 * 
 */
public interface ITransactionService {

	/**
	 * 根据事件ID查询事务列表
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param opId
	 *            操作员ID
	 * @return 事务列表
	 * @throws Exception
	 */
	public List<IcTransaction> queryTransaction(long incidentId, long opId)
			throws Exception;

	/**
	 * 用户提交事务
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param transactionInfo
	 *            事务信息
	 * @param opId
	 *            操作员ID
	 * @return 事务ID
	 * @throws Exception
	 */
	public long userCommitTransaction(long incidentId,
			TransactionInfo transactionInfo, long opId) throws Exception;

	/**
	 * 顾问提交事务
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param transactionInfo
	 *            事务信息
	 * @param opId
	 *            操作员ID
	 * @return 事务ID
	 * @throws Exception
	 */
	public long adviserCommitTransaction(long incidentId,
			TransactionInfo transactionInfo, long opId) throws Exception;

	/**
	 * 顾问提交事务，包含补全事件影响度、事件分类、事件优先级信息
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param incidentInfo
	 *            事件信息，此逻辑仅关注顾问设置的影响度、分类、优先级三个信息
	 * @param transactionInfo
	 *            事务信息
	 * @param opId
	 *            操作员ID
	 * @return 事务ID
	 * @throws Exception
	 */
	public long adviserCommitTransaction(long incidentId,
			IncidentInfo incidentInfo, TransactionInfo transactionInfo,
			long opId) throws Exception;

	/**
	 * 需用户补充资料
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param transactionInfo
	 *            事务信息
	 * @param opId
	 *            操作员ID
	 * @return 事务ID
	 * @throws Exception
	 */
	public long needAdditionalInfo(long incidentId,
			TransactionInfo transactionInfo, long opId) throws Exception;

	/**
	 * 顾问转派事务
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param transactionInfo
	 *            事务信息
	 * @param opId
	 *            操作员ID
	 * @return 事务ID
	 * @throws Exception
	 */
	public long adviserTransferTransaction(long incidentId,
			TransactionInfo transactionInfo, long opId) throws Exception;

	/**
	 * 顾问挂起事件
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param transactionInfo
	 *            事务信息
	 * @param opId
	 *            操作员ID
	 * @return 事务ID
	 * @throws Exception
	 */
	public long adviserHangUpTransaction(long incidentId,
			TransactionInfo transactionInfo, long opId) throws Exception;

	/**
	 * 顾问设置事件处理完成
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param incidentInfo
	 *            事件信息，此逻辑仅关注顾问设置的事件结束代码
	 * @param transactionInfo
	 *            事务信息
	 * @param opId
	 *            操作员ID
	 * @return 事务ID
	 * @throws Exception
	 */
	public long adviserCompleteTransaction(long incidentId,
			IncidentInfo incidentInfo, TransactionInfo transactionInfo,
			long opId) throws Exception;
}
