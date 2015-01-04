/**
 * 
 */
package com.ei.itop.daemon.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ailk.dazzle.util.AppContext;
import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ailk.dazzle.util.type.DateUtils;
import com.ei.itop.common.dbentity.CcCustProdOp;
import com.ei.itop.common.dbentity.CcSlo;
import com.ei.itop.common.dbentity.IcIncident;
import com.ei.itop.common.dbentity.ScOrg;
import com.ei.itop.common.service.MailSendService;
import com.ei.itop.daemon.service.TimeoutRemindService;

/**
 * @author QiLin
 * 
 */
@Service(value = "timeoutRemindService")
public class TimeoutRemindServiceImpl implements TimeoutRemindService {

	private static final Logger log = Logger
			.getLogger(TimeoutRemindServiceImpl.class);

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScOrg> orgDAO;

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, CcSlo> sloDAO;

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, IcIncident> incidentDAO;

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, CcCustProdOp> custProdOpDAO;

	@Resource(name = "mailSendService")
	private MailSendService mailSendService;

	@Resource(name = "mailSendConfig")
	Map<String, String> mailSendConfig;

	public void responseTimeoutRemind(Date currentTime) throws Exception {
		// TODO Auto-generated method stub

		// 取得系统中的商户列表
		Map<String, Object> hm1 = new HashMap<String, Object>();
		List<ScOrg> orgList = orgDAO.findByParams("SC_ORG.queryOrg", hm1);

		// 循环每一个商户
		for (int i = 0; orgList != null && i < orgList.size(); i++) {
			ScOrg org = orgList.get(i);

			// 取得商户用于判断超时的SLO记录
			Map<String, Object> hm2 = new HashMap<String, Object>();
			hm2.put("orgId", org.getScOrgId());
			hm2.put("custId", -1);
			hm2.put("productId", -1);
			CcSlo slo = null;
			try {
				slo = sloDAO.findByParams(
						"CC_SLO.querySloRulesOrderByPriorityDescColplexDesc",
						hm2).get(0);
			} catch (Exception e) {
				log.error("【超时未响应】获取orgId为" + org.getScOrgId()
						+ "的slo时出错，跳过并继续。", e);
				continue;
			}

			if (slo == null) {
				log.error("【超时未响应】获取orgId为" + org.getScOrgId()
						+ "的slo为空，跳过并继续。");
				continue;
			}

			// 取得未响应状态的事件列表
			Map<String, Object> hm3 = new HashMap<String, Object>();
			hm3.put("orgId", org.getScOrgId());
			String[] stateCode = { "2" };
			hm3.put("stateCode", stateCode);
			List<IcIncident> incidentList = null;
			try {
				incidentList = incidentDAO.findByParams(
						"IC_INCIDENT.queryIncidentByOrgIdAndStateCode", hm3);
			} catch (Exception e) {
				log.error("【超时未响应】获取orgId为" + org.getScOrgId()
						+ "的未响应事件列表时出错，跳过并继续。", e);
				continue;
			}

			// 循环处理每一个未响应状态的事件
			for (int j = 0; incidentList != null && j < incidentList.size(); j++) {
				IcIncident incident = incidentList.get(j);

				// 判断是否满足提醒条件
				if (needRemind4ResponseTimeout(incident, currentTime,
						slo.getResponseTime())) {
					// 需要提醒
					// 执行提醒操作
					remind4ResponseTimeout(incident);
				}
			}
		}
	}

	/**
	 * 对超时未响应事件，判断是否须提醒，距登记时间（REGISTE_TIME）已经超过响应时限整小时数（0-n），则发送提醒邮件
	 * 
	 * @param incident
	 * @param currentTime
	 * @param responseTime
	 * @return
	 * @throws Exception
	 */
	protected boolean needRemind4ResponseTimeout(IcIncident incident,
			Date currentTime, long responseTime) throws Exception {

		Date d = incident.getRegisteTime();
		d = DateUtils.dateOffset(d, Calendar.MINUTE, (int) responseTime);

		long diffSeconds = DateUtils.getDiffSeconds(currentTime, d);
		log.debug("diffSeconds is " + diffSeconds);

		// 超时后每小时的第一个时间间隔（当前是5分钟）处理，其余不处理，如果规则改变为不是每小时，需调整此处的3600
		long mod = diffSeconds % 3600;
		// 间隔5分钟，即为0-300秒，如果间隔改变，需调整此处的300
		if (mod >= 0 && mod < 300) {
			return true;
		}

		log.debug("【超时未响应】事件【ID=" + incident.getIcIncidentId() + "，系列号="
				+ incident.getIncidentCode() + "】不满足提醒条件，跳过。");

		return false;
	}

	/**
	 * 对超时未响应的事件执行提醒操作
	 * 
	 * @param incident
	 * @throws Exception
	 */
	protected void remind4ResponseTimeout(IcIncident incident) throws Exception {

		log.debug("【超时未响应】事件【ID=" + incident.getIcIncidentId() + "，系列号="
				+ incident.getIncidentCode() + "】满足提醒条件，发送提醒邮件。");

		// 将事件经理设置为邮件抄送人
		String[] ccAddr = null;
		// 得到事件的事件经理
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", incident.getScOrgId());
		hm.put("custId", incident.getCcCustId());
		hm.put("productId", incident.getScProductId());
		hm.put("jobLevel", "9");
		List<CcCustProdOp> custProdOpList = custProdOpDAO.findByParams(
				"CC_CUST_PROD_OP.queryManager", hm);
		// ccAddr = new String[1];
		// ccAddr[0] = "kfqilin@163.com";
		if (custProdOpList != null && custProdOpList.size() > 0) {
			ccAddr = new String[custProdOpList.size()];
			for (int i = 0; custProdOpList != null && i < custProdOpList.size(); i++) {
				ccAddr[i] = custProdOpList.get(i).getLoginCode();
			}
		}

		// 发送邮件
		if (Boolean.parseBoolean(mailSendConfig.get("mail.allowed"))) {
			log.debug("【超时未响应】发送邮件方法传入参数：" + incident.getIcOwnerName() + ","
					+ incident.getScLoginName() + ","
					+ incident.getScLoginCode() + "," + ccAddr);
			mailSendService.sendResponseTimeoutRemindMail(
					incident.getIcOwnerName(), incident.getScLoginName(),
					incident.getScLoginCode(), ccAddr, incident);
			// "jack.qi@eiconsulting.cn", ccAddr, incident);
		}
	}

	public void dealTimeoutRemind(Date currentTime) throws Exception {
		// TODO Auto-generated method stub

		// 取得系统中的商户列表
		Map<String, Object> hm1 = new HashMap<String, Object>();
		List<ScOrg> orgList = orgDAO.findByParams("SC_ORG.queryOrg", hm1);

		// 循环每一个商户
		for (int i = 0; orgList != null && i < orgList.size(); i++) {
			ScOrg org = orgList.get(i);

			// 取得商户用于判断超时的SLO记录
			Map<String, Object> hm2 = new HashMap<String, Object>();
			hm2.put("orgId", org.getScOrgId());
			hm2.put("custId", -1);
			hm2.put("productId", -1);
			CcSlo slo = null;
			try {
				slo = sloDAO.findByParams(
						"CC_SLO.querySloRulesOrderByPriorityDescColplexDesc",
						hm2).get(0);
			} catch (Exception e) {
				log.error("【超时未完成】获取orgId为" + org.getScOrgId()
						+ "的slo时出错，跳过并继续。", e);
				continue;
			}

			if (slo == null) {
				log.error("【超时未完成】获取orgId为" + org.getScOrgId()
						+ "的slo为空，跳过并继续。");
				continue;
			}

			// 取得已响应但尚未完毕的事件列表
			Map<String, Object> hm3 = new HashMap<String, Object>();
			hm3.put("orgId", org.getScOrgId());
			String[] stateCode = { "3", "4", "5" };
			hm3.put("stateCode", stateCode);
			List<IcIncident> incidentList = null;
			try {
				incidentList = incidentDAO.findByParams(
						"IC_INCIDENT.queryIncidentByOrgIdAndStateCode", hm3);
			} catch (Exception e) {
				log.error("【超时未完成】获取orgId为" + org.getScOrgId()
						+ "的已响应但尚未完毕的事件列表时出错，跳过并继续。", e);
				continue;
			}

			// 循环处理每一个已响应但尚未完毕的事件
			for (int j = 0; incidentList != null && j < incidentList.size(); j++) {
				IcIncident incident = incidentList.get(j);

				// 判断是否满足提醒条件
				if (needRemind4DealTimeout(incident, currentTime,
						slo.getDealTime())) {
					// 需要提醒
					// 执行提醒操作
					remind4DealTimeout(incident);
				}
			}
		}
	}

	/**
	 * 对超时未处理完毕的事件，判断是否须提醒，距登记时间（REGISTE_TIME）已经超过处理时限，则发送第一封提醒邮件，然后是每天一封，
	 * 暂定每天凌晨2:00
	 * 
	 * @param incident
	 * @param currentTime
	 * @param dealTime
	 * @return
	 * @throws Exception
	 */
	protected boolean needRemind4DealTimeout(IcIncident incident,
			Date currentTime, long dealTime) throws Exception {

		Date d = incident.getRegisteTime();
		d = DateUtils.dateOffset(d, Calendar.MINUTE, (int) dealTime);

		long diffSeconds = DateUtils.getDiffSeconds(currentTime, d);
		log.debug("diffSeconds is " + diffSeconds);

		// 超时后的第一个时间间隔（当前是5分钟）处理
		// 间隔5分钟，即为0-300秒，如果间隔改变，需调整此处的300
		if (diffSeconds >= 0 && diffSeconds < 300) {
			return true;
		}

		// 判断是否为当天2:00后的第一个时间间隔，如果是，则为每天的定时处理时刻
		String strTodaysClock = DateUtils.date2String(currentTime,
				DateUtils.FORMATTYPE_yyyyMMdd) + "020000";
		Date todaysClock = DateUtils.string2Date(strTodaysClock,
				DateUtils.FORMATTYPE_yyyyMMddHHmmss);
		diffSeconds = DateUtils.getDiffSeconds(currentTime, todaysClock);
		log.debug("diffSeconds is " + diffSeconds);
		if (diffSeconds >= 0 && diffSeconds < 300) {
			return true;
		}

		log.debug("【超时未完成】事件【ID=" + incident.getIcIncidentId() + "，系列号="
				+ incident.getIncidentCode() + "】不满足提醒条件，跳过。");

		return false;
	}

	/**
	 * 对超时未完成的事件执行提醒操作
	 * 
	 * @param incident
	 * @throws Exception
	 */
	protected void remind4DealTimeout(IcIncident incident) throws Exception {

		log.debug("【超时未完成】事件【ID=" + incident.getIcIncidentId() + "，系列号="
				+ incident.getIncidentCode() + "】满足提醒条件，发送提醒邮件。");

		// 将事件经理设置为邮件抄送人
		String[] ccAddr = null;
		// 得到事件的事件经理
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", incident.getScOrgId());
		hm.put("custId", incident.getCcCustId());
		hm.put("productId", incident.getScProductId());
		hm.put("jobLevel", "9");
		List<CcCustProdOp> custProdOpList = custProdOpDAO.findByParams(
				"CC_CUST_PROD_OP.queryManager", hm);
		// ccAddr = new String[1];
		// ccAddr[0] = "kfqilin@163.com";
		if (custProdOpList != null && custProdOpList.size() > 0) {
			ccAddr = new String[custProdOpList.size()];
			for (int i = 0; custProdOpList != null && i < custProdOpList.size(); i++) {
				ccAddr[i] = custProdOpList.get(i).getLoginCode();
			}
		}

		// 发送邮件
		if (Boolean.parseBoolean(mailSendConfig.get("mail.allowed"))) {
			log.debug("【超时未完成】发送邮件方法传入参数：" + incident.getIcOwnerName() + ","
					+ incident.getScLoginName() + ","
					+ incident.getScLoginCode() + "," + ccAddr);
			mailSendService.sendDealTimeoutRemindMail(
					incident.getIcOwnerName(), incident.getScLoginName(),
					incident.getScLoginCode(), ccAddr, incident);
			// "jack.qi@eiconsulting.cn", ccAddr, incident);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		TimeoutRemindService trs = (TimeoutRemindService) AppContext
				.getBean("timeoutRemindService");

		IcIncident incident = new IcIncident();
		incident.setIcOwnerName("事件OwnerName");
		incident.setScLoginName("事件负责顾问Name");
		incident.setScLoginCode("jack.qi@eiconsulting.cn");
		incident.setIncidentCode("incidentcode");
		incident.setBrief("brief");
		incident.setDetail("detail");
		incident.setHappenTime(new Date());
		incident.setReponseDur2(new Date());
		incident.setDealDur2(new Date());
		// trs.remind4ResponseTimeout(incident);

		// trs.responseTimeoutRemind(new Date());
		trs.dealTimeoutRemind(new Date());
	}

}
