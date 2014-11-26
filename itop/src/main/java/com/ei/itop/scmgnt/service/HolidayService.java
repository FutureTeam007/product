/**
 * 
 */
package com.ei.itop.scmgnt.service;

import java.util.Date;
import java.util.List;

import com.ei.itop.common.dbentity.ScHoliday;

/**
 * @author Jack.Qi
 * 
 */
public interface HolidayService {

	public List<ScHoliday> getHolidayList(long orgId, Date beginDate,
			Date endDate) throws Exception;
}
