package com.ei.itop.daemon.job;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.daemon.service.TimeoutRemindService;
/**
 * 处理超时作业
 * @author vintin
 */
@Component
public class DealTimeoutJob {
	
	private static final Logger log = Logger
			.getLogger(DealTimeoutJob.class);
	
	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, Date> commonDAO;
	
	@Autowired
	TimeoutRemindService timeoutRemindService;
	
	@Scheduled(cron = "0 0/5 * * * ?")
    void work(){
		//获取当前系统时间
		try {
			Date currentDate = commonDAO.find("COMMON.getSysDate", 1L);
			timeoutRemindService.dealTimeoutRemind(currentDate);
		} catch (Exception e) {
			log.error("作业调度-处理超时作业出现异常：",e);
		}
    }  
}
