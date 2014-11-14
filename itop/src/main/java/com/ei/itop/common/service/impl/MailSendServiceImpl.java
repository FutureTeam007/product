package com.ei.itop.common.service.impl;

import java.util.Date;
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
import com.ailk.dazzle.util.type.DateUtils;
import com.ei.itop.common.dbentity.IcIncident;
import com.ei.itop.common.dbentity.IcTransaction;
import com.ei.itop.common.util.SessionUtil;

@Service("mailSendService")
public class MailSendServiceImpl implements
		com.ei.itop.common.service.MailSendService {

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
		final String website = getWebsite();
		new Thread() {
			public void run() {
				MimeMessagePreparator preparator = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(receiveAddr);// 设置接收方的email地址
						message.setSubject("[ITOP] Thank you for registering, please activate account to complete the registration");// 设置邮件主题
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("website", website);
						params.put("userName", userName);
						params.put("activeURL", activeURL);
						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/account-active.vm", "UTF-8", params);
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
		}.start();
	}

	public void sendIncidentMail(final String userName,
			final String userReceiveAddr, final String opName,
			final String opReceiveAddr, final String[] ccAddr,
			final IcIncident incident) throws Exception {
		final String website = getWebsite();
		new Thread() {
			public void run() {
				MimeMessagePreparator preparator1 = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(userReceiveAddr);// 设置接收方的email地址
						message.setSubject("[ITOP] Ticket submit successfully! [No."+incident.getIncidentCode()+"]:"
								+ incident.getBrief());// 设置邮件主题
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("website", website);
						params.put("userName", userName);
						params.put("vDate", DateUtils.date2String(new Date(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("opName", opName);
						params.put("content", incident.getDetail());
						params.put("incident", incident);
						params.put(
								"happenTime",
								incident.getHappenTime() == null ? null
										: DateUtils.date2String(
												incident.getHappenTime(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"reponseDur2",
								incident.getReponseDur2() == null ? null
										: DateUtils.date2String(
												incident.getReponseDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"dealDur2",
								incident.getDealDur2() == null ? null
										: DateUtils.date2String(
												incident.getDealDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/incident-commit-user.vm", "UTF-8",
										params);
						message.setText(text, true);
					}
				};
				try {
					mailSender.send(preparator1);
				} catch (Exception e) {
					log.error("", e);
					throw new BizException("用户方事件提交提醒邮件发送失败，" + e.getMessage(),
							e);
				}

				MimeMessagePreparator preparator2 = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(opReceiveAddr);// 设置接收方的email地址
						message.setSubject("[ITOP] New ticket has been submited! [No."+incident.getIncidentCode()+"] :"
								+ incident.getBrief() + ",from ["
								+ userName + "]");// 设置邮件主题
						if (ccAddr != null) {
							message.setCc(ccAddr);
						}
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("website", website);
						params.put("userName", userName);
						params.put("vDate", DateUtils.date2String(new Date(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("opName", opName);
						params.put("content", incident.getDetail());
						params.put("incident", incident);
						params.put(
								"happenTime",
								incident.getHappenTime() == null ? null
										: DateUtils.date2String(
												incident.getHappenTime(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"reponseDur2",
								incident.getReponseDur2() == null ? null
										: DateUtils.date2String(
												incident.getReponseDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"dealDur2",
								incident.getDealDur2() == null ? null
										: DateUtils.date2String(
												incident.getDealDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));

						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/incident-commit-op.vm", "UTF-8",
										params);
						message.setText(text, true);
					}
				};
				try {
					mailSender.send(preparator2);
				} catch (Exception e) {
					log.error("", e);
					throw new BizException(
							"顾问方新事件提交提醒邮件发送失败，" + e.getMessage(), e);
				}
			}
		}.start();
	}

	public void sendAdviserTransactionCommitMail(final String userName,
			final String userRceiveAddr, final String opName,
			final String opReceiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception {
		final String website = getWebsite();
		new Thread() {
			public void run() {
				MimeMessagePreparator preparator1 = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(opReceiveAddr);
						message.setSubject("[ITOP] Ticket transaction submit successfully! [No."+incident.getIncidentCode()+"]:"
								+ incident.getBrief() + "]");// 设置邮件主题
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("website", website);
						params.put("opName", opName);
						params.put("vDate", DateUtils.date2String(new Date(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("content", transaction.getContents());
						params.put("incident", incident);
						params.put(
								"happenTime",
								incident.getHappenTime() == null ? null
										: DateUtils.date2String(
												incident.getHappenTime(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"reponseDur2",
								incident.getReponseDur2() == null ? null
										: DateUtils.date2String(
												incident.getReponseDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"dealDur2",
								incident.getDealDur2() == null ? null
										: DateUtils.date2String(
												incident.getDealDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/transaction-commit-self.vm",
										"UTF-8", params);
						message.setText(text, true);
					}
				};
				try {
					mailSender.send(preparator1);
				} catch (Exception e) {
					log.error("", e);
					throw new BizException("事务提交提醒邮件发送失败，" + e.getMessage(), e);
				}
				MimeMessagePreparator preparator2 = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(userRceiveAddr);
						message.setSubject("[ITOP] New ticket transaction has submited! [No."+incident.getIncidentCode()+"]:"
								+ incident.getBrief() + ",from ["
								+ opName + "]");// 设置邮件主题
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						if (ccAddr != null) {
							message.setCc(ccAddr);
						}
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("website", website);
						params.put("userName", userName);
						params.put("opName", opName);
						params.put("vDate", DateUtils.date2String(new Date(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("content", transaction.getContents());
						params.put("incident", incident);
						params.put(
								"happenTime",
								incident.getHappenTime() == null ? null
										: DateUtils.date2String(
												incident.getHappenTime(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"reponseDur2",
								incident.getReponseDur2() == null ? null
										: DateUtils.date2String(
												incident.getReponseDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"dealDur2",
								incident.getDealDur2() == null ? null
										: DateUtils.date2String(
												incident.getDealDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/transaction-commit-others.vm",
										"UTF-8", params);
						message.setText(text, true);
					}
				};
				try {
					mailSender.send(preparator2);
				} catch (Exception e) {
					log.error("", e);
					throw new BizException("事务提交提醒邮件发送失败，" + e.getMessage(), e);
				}
			};
		}.start();
	}

	public void sendUserTransactionCommitMail(final String userName,
			final String userRceiveAddr, final String opName,
			final String opReceiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception {
		final String website = getWebsite();
		new Thread() {
			public void run() {
				MimeMessagePreparator preparator1 = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(userRceiveAddr);
						message.setSubject("[ITOP] Ticket transaction submit successfully! [No."+incident.getIncidentCode()+"]:"
								+ incident.getBrief());// 设置邮件主题
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("website", website);
						params.put("opName", userName);
						params.put("vDate", DateUtils.date2String(new Date(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("content", transaction.getContents());
						params.put("incident", incident);
						params.put(
								"happenTime",
								incident.getHappenTime() == null ? null
										: DateUtils.date2String(
												incident.getHappenTime(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"reponseDur2",
								incident.getReponseDur2() == null ? null
										: DateUtils.date2String(
												incident.getReponseDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"dealDur2",
								incident.getDealDur2() == null ? null
										: DateUtils.date2String(
												incident.getDealDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/transaction-commit-self.vm",
										"UTF-8", params);
						message.setText(text, true);
					}
				};
				try {
					mailSender.send(preparator1);
				} catch (Exception e) {
					log.error("", e);
					throw new BizException("事务提交提醒邮件发送失败，" + e.getMessage(), e);
				}
				MimeMessagePreparator preparator2 = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(opReceiveAddr);
						message.setSubject("[ITOP] New ticket transaction has been submited! [No."+incident.getIncidentCode()+"]:"
								+ incident.getIncidentCode() + ",from ["
								+ userName + "]");// 设置邮件主题
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						if (ccAddr != null) {
							message.setCc(ccAddr);
						}
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("website", website);
						params.put("opName", userName);
						params.put("userName", opName);
						params.put("vDate", DateUtils.date2String(new Date(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("content", transaction.getContents());
						params.put("incident", incident);
						params.put(
								"happenTime",
								incident.getHappenTime() == null ? null
										: DateUtils.date2String(
												incident.getHappenTime(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"reponseDur2",
								incident.getReponseDur2() == null ? null
										: DateUtils.date2String(
												incident.getReponseDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"dealDur2",
								incident.getDealDur2() == null ? null
										: DateUtils.date2String(
												incident.getDealDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/transaction-commit-others.vm",
										"UTF-8", params);
						message.setText(text, true);
					}
				};
				try {
					mailSender.send(preparator2);
				} catch (Exception e) {
					log.error("", e);
					throw new BizException("事务提交提醒邮件发送失败，" + e.getMessage(), e);
				}
			}
		}.start();
	}

	public void sendTransactionTransferMail(final String toName,
			final String fromName, final String userName,
			final String receiveAddr, final String transferReceiveAddr,
			final String userReceiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception {
		final String website = getWebsite();
		new Thread() {
			public void run() {
				MimeMessagePreparator preparator1 = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(receiveAddr);// 设置接收方的email地址
						message.setSubject("[ITOP] Ticket handover submit successfully! [No."+incident.getIncidentCode()+"]:"
								+ incident.getBrief());// 设置邮件主题
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("website", website);
						params.put("opName", fromName);
						params.put("vDate", DateUtils.date2String(new Date(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("content", transaction.getContents());
						params.put("incident", incident);
						params.put(
								"happenTime",
								incident.getHappenTime() == null ? null
										: DateUtils.date2String(
												incident.getHappenTime(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"reponseDur2",
								incident.getReponseDur2() == null ? null
										: DateUtils.date2String(
												incident.getReponseDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"dealDur2",
								incident.getDealDur2() == null ? null
										: DateUtils.date2String(
												incident.getDealDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("op", "handover");
						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/transaction-commit-self.vm",
										"UTF-8", params);
						message.setText(text, true);
					}
				};
				try {
					mailSender.send(preparator1);
				} catch (Exception e) {
					log.error("", e);
					throw new BizException("事务转派提交提醒邮件发送失败，" + e.getMessage(),
							e);
				}

				MimeMessagePreparator preparator2 = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(transferReceiveAddr);// 设置接收方的email地址
						message.setSubject("[ITOP] Ticket handover notice! [No."+incident.getIncidentCode()+"]:"
								+ incident.getBrief() + ",from ["
								+ fromName + "]");// 设置邮件主题
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("website", website);
						params.put("toName", toName);
						params.put("fromName", fromName);
						params.put("vDate", DateUtils.date2String(new Date(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("content", transaction.getContents());
						params.put("incident", incident);
						params.put(
								"happenTime",
								incident.getHappenTime() == null ? null
										: DateUtils.date2String(
												incident.getHappenTime(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"reponseDur2",
								incident.getReponseDur2() == null ? null
										: DateUtils.date2String(
												incident.getReponseDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"dealDur2",
								incident.getDealDur2() == null ? null
										: DateUtils.date2String(
												incident.getDealDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/transaction-transfer.vm", "UTF-8",
										params);
						message.setText(text, true);
					}
				};
				try {
					mailSender.send(preparator2);
				} catch (Exception e) {
					log.error("", e);
					throw new BizException("事务转派新提醒邮件发送失败，" + e.getMessage(), e);
				}
				MimeMessagePreparator preparator3 = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(userReceiveAddr);// 设置接收方的email地址
						message.setSubject("[ITOP] Ticket handover notice! [No."+incident.getIncidentCode()+"]:"
								+ incident.getBrief() + ", from ["
								+ fromName + "]");// 设置邮件主题
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						if (ccAddr != null) {
							message.setCc(ccAddr);
						}
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("website", website);
						params.put("toName", toName);
						params.put("userName", userName);
						params.put("fromName", fromName);
						params.put("vDate", DateUtils.date2String(new Date(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("content", transaction.getContents());
						params.put("incident", incident);
						params.put(
								"happenTime",
								incident.getHappenTime() == null ? null
										: DateUtils.date2String(
												incident.getHappenTime(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"reponseDur2",
								incident.getReponseDur2() == null ? null
										: DateUtils.date2String(
												incident.getReponseDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"dealDur2",
								incident.getDealDur2() == null ? null
										: DateUtils.date2String(
												incident.getDealDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/transaction-transfer-user.vm",
										"UTF-8", params);
						message.setText(text, true);
					}
				};
				try {
					mailSender.send(preparator3);
				} catch (Exception e) {
					log.error("", e);
					throw new BizException("事务转派新提醒邮件发送失败，" + e.getMessage(), e);
				}
			}
		}.start();
	}

	public void sendTransactionNeedMoreInfoMail(final String toName,
			final String fromName, final String receiveAddr,
			final String userReceiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception {
		final String website = getWebsite();
		new Thread() {
			public void run() {
				MimeMessagePreparator preparator1 = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(receiveAddr);// 设置接收方的email地址
						message.setSubject("[ITOP] Ticket transaction submit successfully! [No."+incident.getIncidentCode()+"] :"
								+ incident.getBrief());// 设置邮件主题
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						if (ccAddr != null) {
							message.setCc(ccAddr);
						}
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("website", website);
						params.put("vDate", DateUtils.date2String(new Date(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("opName", fromName);
						params.put("content", transaction.getContents());
						params.put("incident", incident);
						params.put(
								"happenTime",
								incident.getHappenTime() == null ? null
										: DateUtils.date2String(
												incident.getHappenTime(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"reponseDur2",
								incident.getReponseDur2() == null ? null
										: DateUtils.date2String(
												incident.getReponseDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"dealDur2",
								incident.getDealDur2() == null ? null
										: DateUtils.date2String(
												incident.getDealDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("op", "need more information");
						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/transaction-commit-self.vm",
										"UTF-8", params);
						message.setText(text, true);
					}
				};
				try {
					mailSender.send(preparator1);
				} catch (Exception e) {
					log.error("", e);
					throw new BizException("提交需客户补充资料提醒邮件发送失败，"
							+ e.getMessage(), e);
				}

				MimeMessagePreparator preparator2 = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(userReceiveAddr);// 设置接收方的email地址
						message.setSubject("[ITOP] Ticket need more information! [No."+incident.getIncidentCode()+"]:"
								+ incident.getBrief() + ", from ["
								+ fromName + "]");// 设置邮件主题
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						Map<String, Object> params = new HashMap<String, Object>();
						if (ccAddr != null) {
							message.setCc(ccAddr);
						}
						params.put("website", website);
						params.put("vDate", DateUtils.date2String(new Date(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("toName", toName);
						params.put("fromName", fromName);
						params.put("content", transaction.getContents());
						params.put("incident", incident);
						params.put(
								"happenTime",
								incident.getHappenTime() == null ? null
										: DateUtils.date2String(
												incident.getHappenTime(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"reponseDur2",
								incident.getReponseDur2() == null ? null
										: DateUtils.date2String(
												incident.getReponseDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"dealDur2",
								incident.getDealDur2() == null ? null
										: DateUtils.date2String(
												incident.getDealDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/transaction-needmoreinfo.vm",
										"UTF-8", params);
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
		}.start();
	}

	public void sendTransactionBlockMail(final String userName,
			final String userReceiveAddr, final String opName,
			final String opReceiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception {
		final String website = getWebsite();
		new Thread() {
			public void run() {
				MimeMessagePreparator preparator1 = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(opReceiveAddr);// 设置接收方的email地址
						message.setSubject("[ITOP] Ticket pending submit successfully! [No."+incident.getIncidentCode()+"]:"
								+ incident.getBrief());// 设置邮件主题
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("website", website);
						params.put("opName", opName);
						params.put("vDate", DateUtils.date2String(new Date(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("content", transaction.getContents());
						params.put("incident", incident);
						params.put(
								"happenTime",
								incident.getHappenTime() == null ? null
										: DateUtils.date2String(
												incident.getHappenTime(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"reponseDur2",
								incident.getReponseDur2() == null ? null
										: DateUtils.date2String(
												incident.getReponseDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"dealDur2",
								incident.getDealDur2() == null ? null
										: DateUtils.date2String(
												incident.getDealDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("op", "pending");
						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/transaction-commit-self.vm",
										"UTF-8", params);
						message.setText(text, true);
					}
				};
				try {
					mailSender.send(preparator1);
				} catch (Exception e) {
					log.error("", e);
					throw new BizException("事务提交提醒邮件发送失败，" + e.getMessage(), e);
				}
				MimeMessagePreparator preparator2 = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(userReceiveAddr);// 设置接收方的email地址
						message.setSubject("[ITOP] Ticket is marked pending! [No."+incident.getIncidentCode()+"]:"
								+ incident.getBrief()+ ", from [" + opName
								+ "]");// 设置邮件主题
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						if (ccAddr != null) {
							message.setCc(ccAddr);
						}
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("website", website);
						params.put("userName", userName);
						params.put("opName", opName);
						params.put("vDate", DateUtils.date2String(new Date(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("content", transaction.getContents());
						params.put("incident", incident);
						params.put(
								"happenTime",
								incident.getHappenTime() == null ? null
										: DateUtils.date2String(
												incident.getHappenTime(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"reponseDur2",
								incident.getReponseDur2() == null ? null
										: DateUtils.date2String(
												incident.getReponseDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"dealDur2",
								incident.getDealDur2() == null ? null
										: DateUtils.date2String(
												incident.getDealDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("op", "pending");
						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/transaction-success-others.vm",
										"UTF-8", params);
						message.setText(text, true);
					}
				};
				try {
					mailSender.send(preparator2);
				} catch (Exception e) {
					log.error("", e);
					throw new BizException("事务提交提醒邮件发送失败，" + e.getMessage(), e);
				}
			}
		}.start();
	}

	public void sendTransactionFinishMail(final String userName,
			final String userReceiveAddr, final String opName,
			final String opReceiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception {
		final String website = getWebsite();
		new Thread() {
			public void run() {
				MimeMessagePreparator preparator1 = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(opReceiveAddr);// 设置接收方的email地址
						message.setSubject("[ITOP] Ticket complete submit successfully! [No."+incident.getIncidentCode()+"]:"
								+ incident.getBrief());// 设置邮件主题
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("website", website);
						params.put("opName", opName);
						params.put("vDate", DateUtils.date2String(new Date(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("content", transaction.getContents());
						params.put("incident", incident);
						params.put(
								"happenTime",
								incident.getHappenTime() == null ? null
										: DateUtils.date2String(
												incident.getHappenTime(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"reponseDur2",
								incident.getReponseDur2() == null ? null
										: DateUtils.date2String(
												incident.getReponseDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"dealDur2",
								incident.getDealDur2() == null ? null
										: DateUtils.date2String(
												incident.getDealDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("op", "complete");
						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/transaction-commit-self.vm",
										"UTF-8", params);
						message.setText(text, true);
					}
				};
				try {
					mailSender.send(preparator1);
				} catch (Exception e) {
					log.error("", e);
					throw new BizException("事务提交提醒邮件发送失败，" + e.getMessage(), e);
				}
				MimeMessagePreparator preparator2 = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(userReceiveAddr);// 设置接收方的email地址
						message.setSubject("[ITOP] Ticket is marked complete! [No."+incident.getIncidentCode()+"]:"
								+ incident.getBrief() + ", from ["
								+ opName + "]");// 设置邮件主题
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						if (ccAddr != null) {
							message.setCc(ccAddr);
						}
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("website", website);
						params.put("opName", opName);
						params.put("userName", userName);
						params.put("vDate", DateUtils.date2String(new Date(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("content", transaction.getContents());
						params.put("incident", incident);
						params.put(
								"happenTime",
								incident.getHappenTime() == null ? null
										: DateUtils.date2String(
												incident.getHappenTime(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"reponseDur2",
								incident.getReponseDur2() == null ? null
										: DateUtils.date2String(
												incident.getReponseDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"dealDur2",
								incident.getDealDur2() == null ? null
										: DateUtils.date2String(
												incident.getDealDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("op", "complete");
						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/transaction-commit-others.vm",
										"UTF-8", params);
						message.setText(text, true);
					}
				};
				try {
					mailSender.send(preparator2);
				} catch (Exception e) {
					log.error("", e);
					throw new BizException("事务提交提醒邮件发送失败，" + e.getMessage(), e);
				}
			}
		}.start();
	}

	public void sendTransactionCloseMail(final String userName,
			final String userReceiveAddr, final String opName,
			final String opReceiveAddr, final String[] ccAddr,
			final IcIncident incident, final IcTransaction transaction)
			throws Exception {
		final String website = getWebsite();
		new Thread() {
			public void run() {
				MimeMessagePreparator preparator1 = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(opReceiveAddr);// 设置接收方的email地址
						message.setSubject("[ITOP] Ticket close submit successfully! [No."+incident.getIncidentCode()+"]:"
								+ incident.getBrief());// 设置邮件主题
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("website", website);
						params.put("opName", opName);
						params.put("vDate", DateUtils.date2String(new Date(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("content", transaction.getContents());
						params.put("incident", incident);
						params.put(
								"happenTime",
								incident.getHappenTime() == null ? null
										: DateUtils.date2String(
												incident.getHappenTime(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"reponseDur2",
								incident.getReponseDur2() == null ? null
										: DateUtils.date2String(
												incident.getReponseDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"dealDur2",
								incident.getDealDur2() == null ? null
										: DateUtils.date2String(
												incident.getDealDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("op", "close");
						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/transaction-commit-self.vm",
										"UTF-8", params);
						message.setText(text, true);
					}
				};
				try {
					mailSender.send(preparator1);
				} catch (Exception e) {
					log.error("", e);
					throw new BizException("事务提交提醒邮件发送失败，" + e.getMessage(), e);
				}
				MimeMessagePreparator preparator2 = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(userReceiveAddr);// 设置接收方的email地址
						message.setSubject("[ITOP] Ticket is marked closed! [No."+incident.getIncidentCode()+"]:"
								+ incident.getBrief() + ", from ["
								+ opName + "]");// 设置邮件主题
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						if (ccAddr != null) {
							message.setCc(ccAddr);
						}
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("website", website);
						params.put("opName", opName);
						params.put("userName", userName);
						params.put("vDate", DateUtils.date2String(new Date(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("content", transaction.getContents());
						params.put("incident", incident);
						params.put(
								"happenTime",
								incident.getHappenTime() == null ? null
										: DateUtils.date2String(
												incident.getHappenTime(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"reponseDur2",
								incident.getReponseDur2() == null ? null
										: DateUtils.date2String(
												incident.getReponseDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"dealDur2",
								incident.getDealDur2() == null ? null
										: DateUtils.date2String(
												incident.getDealDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("op", "close");
						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/transaction-commit-others.vm",
										"UTF-8", params);
						message.setText(text, true);
					}
				};
				try {
					mailSender.send(preparator2);
				} catch (Exception e) {
					log.error("", e);
					throw new BizException("事务提交提醒邮件发送失败，" + e.getMessage(), e);
				}
			}
		}.start();
	}

	public void sendIncidentFeedbackMail(final String userName,
			final String opName, final String opReceiveAddr,
			final String feedbackVal, final IcIncident incident)
			throws Exception {
		final String website = getWebsite();
		new Thread() {
			public void run() {
				MimeMessagePreparator preparator1 = new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage)
							throws Exception {
						MimeMessageHelper message = new MimeMessageHelper(
								mimeMessage, true, "GBK");
						message.setTo(opReceiveAddr);// 设置接收方的email地址
						message.setSubject("[ITOP] Ticket has been added feedback information! [No."+incident.getIncidentCode()+"]:"
								+ incident.getBrief());// 设置邮件主题
						message.setFrom(mailSendConfig.get("mail.from"));// 设置发送方地址
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("website", website);
						params.put("opName", opName);
						params.put("userName", userName);
						params.put("incidentId", incident.getIncidentCode());
						params.put("vDate", DateUtils.date2String(new Date(),
								DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put("content", feedbackVal);
						params.put("incident", incident);
						params.put(
								"happenTime",
								incident.getHappenTime() == null ? null
										: DateUtils.date2String(
												incident.getHappenTime(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"reponseDur2",
								incident.getReponseDur2() == null ? null
										: DateUtils.date2String(
												incident.getReponseDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						params.put(
								"dealDur2",
								incident.getDealDur2() == null ? null
										: DateUtils.date2String(
												incident.getDealDur2(),
												DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
						String text = VelocityEngineUtils
								.mergeTemplateIntoString(velocityEngine,
										"vm/incident-feedback.vm", "UTF-8",
										params);
						message.setText(text, true);
					}
				};
				try {
					mailSender.send(preparator1);
				} catch (Exception e) {
					log.error("", e);
					throw new BizException("评价提醒邮件发送失败，" + e.getMessage(), e);
				}
			}
		}.start();
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
