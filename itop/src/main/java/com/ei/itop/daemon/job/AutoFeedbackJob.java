package com.ei.itop.daemon.job;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.dbentity.IcIncident;
/**
 * 自动评价任务，当未评价的事件超过2天的，自动评价为非常满意
 * @author vintin
 */
@Component
public class AutoFeedbackJob {
	
	private static final Logger log = Logger
			.getLogger(AutoFeedbackJob.class);
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, Date> commonDAO;
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, IcIncident> incidentDAO;
	
	@Scheduled(cron = "0 0 0 * * ?")
    void work(){
		log.info("自动处理2天未评价的已完成事件的任务开始工作");
		try {
			List<IcIncident> incidents = incidentDAO.findByParams("IC_INCIDENT.queryFinishedIncidentFeedbacktimeout", null);
			for(IcIncident incident:incidents){
				incident.setFeedbackCode("1");
				incident.setFeedbackVal("十分满意");
				incident.setFeedbackTime(commonDAO.find("COMMON.getSysDate", 1L));
			}
			incidentDAO.updateBatch("IC_INCIDENT.updateByPrimaryKeySelective", incidents);
		} catch (Exception e) {
			log.error("作业调度-自动处理2天未评价的已完成事件，出现异常：",e);
		}
		log.info("自动处理2天未评价的已完成事件的任务结束工作");
    }  
}
