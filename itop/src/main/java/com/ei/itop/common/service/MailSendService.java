package com.ei.itop.common.service;

import com.ei.itop.common.dbentity.IcIncident;
import com.ei.itop.common.dbentity.IcTransaction;

/**
 * 邮件发送服务
 * 
 * @author vintin
 * 
 */
public interface MailSendService {
	/**
	 * 发送激活邮件
	 * 
	 * @param userName
	 * @param activeURL
	 * @param receiveAddr
	 * @throws Exception
	 */
	public void sendUserActiveMail(final String userName,
			final String activeURL, final String receiveAddr) throws Exception;

	/**
	 * 发送事件提交邮件
	 * 
	 * @param userName
	 * @param opName
	 * @param userReceiveAddr
	 * @param opReceiveAddr
	 * @param ccAddr
	 * @param incident
	 * @throws Exception
	 */
	public void sendIncidentMail(final String userName,
			final String userReceiveAddr, final String opName,
			final String opReceiveAddr, final String[] ccAddr,
			final IcIncident incident) throws Exception;

	/**
	 * 顾问提交普通事务
	 * 
	 * @param userName
	 * @param userRceiveAddr
	 * @param opName
	 * @param opReceiveAddr
	 * @param ccAddr
	 * @param incident
	 * @param transaction
	 * @throws Exception
	 */
	public void sendAdviserTransactionCommitMail(final String userName,
			final String userRceiveAddr, final String opName,
			final String opReceiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception;

	/**
	 * 用户提交事务
	 * 
	 * @param userName
	 * @param userRceiveAddr
	 * @param opName
	 * @param opReceiveAddr
	 * @param ccAddr
	 * @param incident
	 * @param transaction
	 * @throws Exception
	 */
	public void sendUserTransactionCommitMail(final String userName,
			final String userRceiveAddr, final String opName,
			final String opReceiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception;

	/**
	 * 转派事务
	 * 
	 * @param toName
	 * @param fromName
	 * @param userName
	 * @param receiveAddr
	 * @param transferReceiveAddr
	 * @param userReceiveAddr
	 * @param ccAddr
	 * @param incident
	 * @param transaction
	 * @throws Exception
	 */
	public void sendTransactionTransferMail(final String toName,
			final String fromName, final String userName,
			final String receiveAddr, final String transferReceiveAddr,
			final String userReceiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception;

	/**
	 * 转客户补充资料
	 * 
	 * @param toName
	 * @param fromName
	 * @param receiveAddr
	 * @param userReceiveAddr
	 * @param ccAddr
	 * @param incident
	 * @param transaction
	 * @throws Exception
	 */
	public void sendTransactionNeedMoreInfoMail(final String toName,
			final String fromName, final String receiveAddr,
			final String userReceiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception;

	/**
	 * 挂起事务
	 * 
	 * @param userName
	 * @param userReceiveAddr
	 * @param opName
	 * @param opReceiveAddr
	 * @param ccAddr
	 * @param incident
	 * @param transaction
	 * @throws Exception
	 */
	public void sendTransactionBlockMail(final String userName,
			final String userReceiveAddr, final String opName,
			final String opReceiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception;

	/**
	 * 完成事务
	 * 
	 * @param userName
	 * @param userReceiveAddr
	 * @param opName
	 * @param opReceiveAddr
	 * @param ccAddr
	 * @param incident
	 * @param transaction
	 * @throws Exception
	 */
	public void sendTransactionFinishMail(final String userName,
			final String userReceiveAddr, final String opName,
			final String opReceiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception;

	/**
	 * 关闭事务
	 * 
	 * @param userName
	 * @param userReceiveAddr
	 * @param opName
	 * @param opReceiveAddr
	 * @param ccAddr
	 * @param incident
	 * @param transaction
	 * @throws Exception
	 */
	public void sendTransactionCloseMail(final String userName,
			final String userReceiveAddr, final String opName,
			final String opReceiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception;

	/**
	 * 评价事件
	 * 
	 * @param userName
	 * @param opName
	 * @param opReceiveAddr
	 * @param feedbackVal
	 * @param incident
	 * @param transaction
	 * @throws Exception
	 */
	public void sendIncidentFeedbackMail(final String userName,
			final String opName, final String opReceiveAddr,
			String feedbackVal, final IcIncident incident) throws Exception;

	/**
	 * 发送响应超时提醒邮件
	 * 
	 * @param userName
	 * @param opName
	 * @param opReceiveAddr
	 * @param ccAddr
	 * @param incident
	 * @throws Exception
	 */
	public void sendResponseTimeoutRemindMail(final String userName,
			final String opName, final String opReceiveAddr,
			final String[] ccAddr, final IcIncident incident) throws Exception;

	/**
	 * 发送处理超时提醒邮件
	 * 
	 * @param userName
	 * @param opName
	 * @param opReceiveAddr
	 * @param ccAddr
	 * @param incident
	 * @throws Exception
	 */
	public void sendDealTimeoutRemindMail(final String userName,
			final String opName, final String opReceiveAddr,
			final String[] ccAddr, final IcIncident incident) throws Exception;
}
