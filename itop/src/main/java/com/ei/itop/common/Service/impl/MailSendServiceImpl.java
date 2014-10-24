package com.ei.itop.common.Service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.sec.Encrypt;
import com.ei.itop.common.Service.MailSendService;
import com.ei.itop.common.dbentity.IcIncident;
import com.ei.itop.common.dbentity.IcTransaction;
import com.ei.itop.common.util.SessionUtil;

@Service("mailSendService")
public class MailSendServiceImpl implements MailSendService {

	private static final Logger log = Logger
			.getLogger(MailSendServiceImpl.class);

	@Resource(name = "mailSendConfig")
	Map<String, String> mailSendConfig;

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	private VelocityEngine velocityEngine;

	public void sendUserActiveMail(final String userName,
			final String activeURL, final String receiveAddr) throws Exception {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "GBK");
				message.setTo(receiveAddr);// 设置接收方的email地址
				message.setSubject("[ITOP] 感谢您注册，请激活邮箱完成注册");// 设置邮件主题
				message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
				Map<String, String> params = new HashMap<String, String>();
				params.put("website", getWebsite());
				params.put("userName", userName);
				params.put("activeURL", activeURL);
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "vm/account-active.vm", "UTF-8", params);
				message.setText(text, true);
			}
		};
		try {
			mailSender.send(preparator);
		} catch (Exception e) {
			log.error("", e);
			throw new BizException("账号激活邮件发送失败，" + e.getMessage(), e);
		}
	}

	public void sendIncidentMail(final String userName, final String opName,
			final String userReceiveAddr, final String opReceiveAddr,
			final String[] ccAddr, final IcIncident incident) throws Exception {
		MimeMessagePreparator preparator1 = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "GBK");
				message.setTo(userReceiveAddr);// 设置接收方的email地址
				message.setSubject("[ITOP]成功提交事件通知，事件编号["
						+ incident.getIncidentCode() + "]");// 设置邮件主题
				message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
				message.setCc(ccAddr);
				Map<String, String> params = new HashMap<String, String>();
				params.put("website", getWebsite());
				params.put("userName", userName);
				params.put("opName", opName);
				params.put("content", incident.getDetail());
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "vm/incident-commit-user.vm", "UTF-8", params);
				message.setText(text, true);
			}
		};
		try {
			mailSender.send(preparator1);
		} catch (Exception e) {
			log.error("", e);
			throw new BizException("用户方事件提交提醒邮件发送失败，" + e.getMessage(), e);
		}

		MimeMessagePreparator preparator2 = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "GBK");
				message.setTo(opReceiveAddr);// 设置接收方的email地址
				message.setSubject("[ITOP]新事件通知，事件编号["
						+ incident.getIncidentCode() + "],来自于[" + userName
						+ "]");// 设置邮件主题
				message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
				Map<String, String> params = new HashMap<String, String>();
				params.put("website", getWebsite());
				params.put("userName", userName);
				params.put("opName", opName);
				params.put("content", incident.getDetail());
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "vm/incident-commit-op.vm", "UTF-8", params);
				message.setText(text, true);
			}
		};
		try {
			mailSender.send(preparator2);
		} catch (Exception e) {
			log.error("", e);
			throw new BizException("顾问方新事件提交提醒邮件发送失败，" + e.getMessage(), e);
		}

	}

	public void sendTransactionCommitMail(final String name,
			final String receiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception {
		MimeMessagePreparator preparator1 = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "GBK");
				message.setTo(receiveAddr);// 设置接收方的email地址
				message.setSubject("[ITOP]事务处理成功提交通知，事件编号["
						+ incident.getIncidentCode() + "]");// 设置邮件主题
				message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
				message.setCc(ccAddr);
				Map<String, String> params = new HashMap<String, String>();
				params.put("website", getWebsite());
				params.put("name", name);
				params.put("content", transaction.getContents());
				params.put("op", "普通提交");
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "vm/transaction-commit.vm", "UTF-8", params);
				message.setText(text, true);
			}
		};
		try {
			mailSender.send(preparator1);
		} catch (Exception e) {
			log.error("", e);
			throw new BizException("事务提交提醒邮件发送失败，" + e.getMessage(), e);
		}
	}

	public void sendTransactionTransferMail(final String toName,
			final String fromName, final String receiveAddr,
			final String transferReceiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception {
		MimeMessagePreparator preparator1 = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "GBK");
				message.setTo(receiveAddr);// 设置接收方的email地址
				message.setSubject("[ITOP]事务处理成功提交通知，事件编号["
						+ incident.getIncidentCode() + "]");// 设置邮件主题
				message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
				message.setCc(ccAddr);
				Map<String, String> params = new HashMap<String, String>();
				params.put("website", getWebsite());
				params.put("name", fromName);
				params.put("content", transaction.getContents());
				params.put("op", "事务转派");
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "vm/transaction-commit.vm", "UTF-8", params);
				message.setText(text, true);
			}
		};
		try {
			mailSender.send(preparator1);
		} catch (Exception e) {
			log.error("", e);
			throw new BizException("事务转派提交提醒邮件发送失败，" + e.getMessage(), e);
		}

		MimeMessagePreparator preparator2 = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "GBK");
				message.setTo(transferReceiveAddr);// 设置接收方的email地址
				message.setSubject("[ITOP]事务转派通知，事件编号["
						+ incident.getIncidentCode() + "]，来自[" + fromName + "]");// 设置邮件主题
				message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
				Map<String, String> params = new HashMap<String, String>();
				params.put("website", getWebsite());
				params.put("name", toName);
				params.put("content", transaction.getContents());
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "vm/transaction-transfer.vm", "UTF-8", params);
				message.setText(text, true);
			}
		};
		try {
			mailSender.send(preparator2);
		} catch (Exception e) {
			log.error("", e);
			throw new BizException("事务转派新提醒邮件发送失败，" + e.getMessage(), e);
		}
	}

	public void sendTransactionNeedMoreInfoMail(final String toName,
			final String fromName, final String receiveAddr,
			final String userReceiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception {

		MimeMessagePreparator preparator1 = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "GBK");
				message.setTo(receiveAddr);// 设置接收方的email地址
				message.setSubject("[ITOP]事务处理成功提交通知，事件编号["
						+ incident.getIncidentCode() + "]");// 设置邮件主题
				message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
				message.setCc(ccAddr);
				Map<String, String> params = new HashMap<String, String>();
				params.put("website", getWebsite());
				params.put("name", fromName);
				params.put("content", transaction.getContents());
				params.put("op", "需客户补充资料");
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "vm/transaction-commit.vm", "UTF-8", params);
				message.setText(text, true);
			}
		};
		try {
			mailSender.send(preparator1);
		} catch (Exception e) {
			log.error("", e);
			throw new BizException("提交需客户补充资料提醒邮件发送失败，" + e.getMessage(), e);
		}

		MimeMessagePreparator preparator2 = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "GBK");
				message.setTo(userReceiveAddr);// 设置接收方的email地址
				message.setSubject("[ITOP]需补充资料通知，事件编号["
						+ incident.getIncidentCode() + "]，来自[" + fromName + "]");// 设置邮件主题
				message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
				Map<String, String> params = new HashMap<String, String>();
				params.put("website", getWebsite());
				params.put("name", toName);
				params.put("content", transaction.getContents());
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "vm/transaction-needmoreinfo.vm", "UTF-8", params);
				message.setText(text, true);
			}
		};
		try {
			mailSender.send(preparator2);
		} catch (Exception e) {
			log.error("", e);
			throw new BizException("补充资料新提醒邮件发送失败，" + e.getMessage(), e);
		}
	}

	public void sendTransactionBlockMail(final String name,
			final String receiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception {
		MimeMessagePreparator preparator1 = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "GBK");
				message.setTo(receiveAddr);// 设置接收方的email地址
				message.setSubject("[ITOP]事务处理成功提交通知，事件编号["
						+ incident.getIncidentCode() + "]");// 设置邮件主题
				message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
				message.setCc(ccAddr);
				Map<String, String> params = new HashMap<String, String>();
				params.put("website", getWebsite());
				params.put("name", name);
				params.put("content", transaction.getContents());
				params.put("op", "挂起");
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "vm/transaction-commit.vm", "UTF-8", params);
				message.setText(text, true);
			}
		};
		try {
			mailSender.send(preparator1);
		} catch (Exception e) {
			log.error("", e);
			throw new BizException("事务提交提醒邮件发送失败，" + e.getMessage(), e);
		}
	}

	public void sendTransactionFinishMail(final String name,
			final String receiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception {
		MimeMessagePreparator preparator1 = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "GBK");
				message.setTo(receiveAddr);// 设置接收方的email地址
				message.setSubject("[ITOP]事务处理成功提交通知，事件编号["
						+ incident.getIncidentCode() + "]");// 设置邮件主题
				message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
				message.setCc(ccAddr);
				Map<String, String> params = new HashMap<String, String>();
				params.put("website", getWebsite());
				params.put("name", name);
				params.put("content", transaction.getContents());
				params.put("op", "完成");
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "vm/transaction-commit.vm", "UTF-8", params);
				message.setText(text, true);
			}
		};
		try {
			mailSender.send(preparator1);
		} catch (Exception e) {
			log.error("", e);
			throw new BizException("事务提交提醒邮件发送失败，" + e.getMessage(), e);
		}
	}

	public void sendTransactionCloseMail(final String name,
			final String receiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception {
		MimeMessagePreparator preparator1 = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true, "GBK");
				message.setTo(receiveAddr);// 设置接收方的email地址
				message.setSubject("[ITOP]事务处理成功提交通知，事件编号["
						+ incident.getIncidentCode() + "]");// 设置邮件主题
				message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
				message.setCc(ccAddr);
				Map<String, String> params = new HashMap<String, String>();
				params.put("website", getWebsite());
				params.put("name", name);
				params.put("content", transaction.getContents());
				params.put("op", "关闭");
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "vm/transaction-commit.vm", "UTF-8", params);
				message.setText(text, true);
			}
		};
		try {
			mailSender.send(preparator1);
		} catch (Exception e) {
			log.error("", e);
			throw new BizException("事务提交提醒邮件发送失败，" + e.getMessage(), e);
		}
	}

	private String getWebsite() {
		StringBuffer website = new StringBuffer();
		HttpServletRequest hsr = SessionUtil.getRequest();
		website.append(hsr.getScheme() + "://");
		website.append(hsr.getServerName());
		website.append("80".equals(hsr.getServerPort()) ? "/" : (":"
				+ hsr.getServerPort() + "/"));
		website.append(hsr.getContextPath());
		return website.toString();
	}

}
