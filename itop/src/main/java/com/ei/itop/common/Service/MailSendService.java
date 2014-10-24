package com.ei.itop.common.Service;

import com.ei.itop.common.dbentity.IcIncident;
import com.ei.itop.common.dbentity.IcTransaction;

public interface MailSendService {

	/**
	 * 发送激活邮件
	 * @param userName
	 * 			用户名
	 * @param activeURL
	 * 			激活链接
	 * @param receiveAddr
	 * 			收件地址
	 * @throws Exception
	 */
	public void sendUserActiveMail(final String userName,
			final String activeURL, final String receiveAddr) throws Exception;

	/**
	 * 提交事件提醒邮件
	 * @param userName
	 * 			用户名
	 * @param opName
	 * 			责任顾问名
	 * @param userReceiveAddr
	 * 			用户收件地址
	 * @param opReceiveAddr
	 * 			顾问收件地址
	 * @param ccAddr
	 * 			抄送邮箱地址
	 * @param incident
	 * 			事件对象
	 * @throws Exception
	 */
	public void sendIncidentMail(final String userName, final String opName,
			final String userReceiveAddr, final String opReceiveAddr,
			final String[] ccAddr, final IcIncident incident) throws Exception;

	/**
	 * 事务提交提醒
	 * @param name
	 * 			当前操作员姓名
	 * @param receiveAddr
	 * 			收件地址
	 * @param ccAddr
	 * 			抄送地址列表
	 * @param incident
	 * 			事件对象
	 * @param transaction
	 * 			事务对象
	 * @throws Exception
	 */
	public void sendTransactionCommitMail(final String name,
			final String receiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception;

	/**
	 * 转派事务提醒
	 * @param toName
	 * 			转派接收人姓名
	 * @param fromName
	 * 			转派人姓名
	 * @param receiveAddr
	 * 			转派人收件地址
	 * @param transferReceiveAddr
	 * 			接收人收件地址
	 * @param ccAddr
	 * 			抄送地址列表
	 * @param incident
	 * 			事件对象
	 * @param transaction
	 * 			事务对象
	 * @throws Exception
	 */
	public void sendTransactionTransferMail(final String toName,
			final String fromName, final String receiveAddr,
			final String transferReceiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception;

	/**
	 * 转客户补充资料事务提交
	 * @param toName
	 * 			客户姓名
	 * @param fromName
	 * 			顾问姓名
	 * @param receiveAddr
	 * 			顾问邮件地址
	 * @param userReceiveAddr
	 * 			客户邮件地址
	 * @param ccAddr
	 * 			抄送邮件列表
	 * @param incident
	 * 			事件对象
	 * @param transaction
	 * 			事务对象
	 * @throws Exception
	 */
	public void sendTransactionNeedMoreInfoMail(final String toName,
			final String fromName, final String receiveAddr,
			final String userReceiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception;

	/**
	 * 挂起事务提交
	 * @param name
	 * 			操作员姓名
	 * @param receiveAddr
	 * 			收件地址
	 * @param ccAddr
	 * 			抄送地址列表
	 * @param incident
	 * 			事件对象
	 * @param transaction
	 * 			事务对象
	 * @throws Exception
	 */
	public void sendTransactionBlockMail(final String name,
			final String receiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception;

	/**
	 * 完成事务提交
	 * @param name
	 * 			操作员姓名
	 * @param receiveAddr
	 * 			收件地址
	 * @param ccAddr
	 * 			抄送地址列表
	 * @param incident
	 * 			事件对象
	 * @param transaction
	 * 			事务对象
	 * @throws Exception
	 */
	public void sendTransactionFinishMail(final String name,
			final String receiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception;

	/**
	 * 关闭事务提交
	 * @param name
	 * 			操作员姓名
	 * @param receiveAddr
	 * 			收件地址
	 * @param ccAddr
	 * 			抄送地址列表
	 * @param incident
	 * 			事件对象
	 * @param transaction
	 * 			事务对象
	 * @throws Exception
	 */
	public void sendTransactionCloseMail(final String name,
			final String receiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception;
}
