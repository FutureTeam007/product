/**
 * 
 */
package com.ei.itop.incidentmgnt.service;

import java.util.List;

import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.dbentity.IcTransaction;
import com.ei.itop.incidentmgnt.bean.IncidentInfo;
import com.ei.itop.incidentmgnt.bean.TransactionInfo;

/**
 * @author Jack.Qi
 * 
 */
public interface TransactionService {

	/**
	 * 根据事件ID查询事务列表
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param opInfo
	 *            操作员ID
	 * @return 事务列表
	 * @throws Exception
	 */
	public List<IcTransaction> MBLQueryTransaction(long incidentId,
			OpInfo opInfo) throws Exception;

	/**
	 * 用户提交事务，在逻辑内部判断用户是否为当前干系人
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param transactionInfo
	 *            事务信息
	 * @param opInfo
	 *            操作员ID
	 * @return 事务ID
	 * @throws Exception
	 */
	public long MBLUserCommitTransaction(long incidentId,
			TransactionInfo transactionInfo, OpInfo opInfo) throws Exception;

	/**
	 * 顾问提交事务，在逻辑内部判断顾问是否为当前干系人
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param transactionInfo
	 *            事务信息
	 * @param opInfo
	 *            操作员ID
	 * @return 事务ID
	 * @throws Exception
	 */
	public long MBLAdviserCommitTransaction(long incidentId,
			TransactionInfo transactionInfo, OpInfo opInfo) throws Exception;

	/**
	 * 需用户补充资料
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param transactionInfo
	 *            事务信息
	 * @param opInfo
	 *            操作员ID
	 * @return 事务ID
	 * @throws Exception
	 */
	public long MBLNeedAdditionalInfo(long incidentId,
			TransactionInfo transactionInfo, OpInfo opInfo) throws Exception;

	/**
	 * 顾问转派事务
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param transactionInfo
	 *            事务信息
	 * @param nextOpId
	 *            被转派的操作员ID
	 * @param opInfo
	 *            操作员ID
	 * @return 事务ID
	 * @throws Exception
	 */
	public long MBLAdviserTransferTransaction(long incidentId,
			TransactionInfo transactionInfo, OpInfo nextOpInfo, OpInfo opInfo)
			throws Exception;

	/**
	 * 顾问挂起事件
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param transactionInfo
	 *            事务信息
	 * @param opInfo
	 *            操作员ID
	 * @return 事务ID
	 * @throws Exception
	 */
	public long MBLAdviserHangUpTransaction(long incidentId,
			TransactionInfo transactionInfo, OpInfo opInfo) throws Exception;

	/**
	 * 顾问设置事件处理完成
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param incidentInfo
	 *            事件信息，此逻辑仅关注顾问设置的事件结束代码
	 * @param transactionInfo
	 *            事务信息
	 * @param opInfo
	 *            操作员ID
	 * @return 事务ID
	 * @throws Exception
	 */
	public long MBLAdviserCompleteTransaction(long incidentId,
			IncidentInfo incidentInfo, TransactionInfo transactionInfo,
			OpInfo opInfo) throws Exception;
}
