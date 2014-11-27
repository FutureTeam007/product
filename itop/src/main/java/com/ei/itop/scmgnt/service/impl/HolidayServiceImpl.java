/**
 * 
 */
package com.ei.itop.scmgnt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ailk.dazzle.util.type.DateUtils;
import com.ei.itop.common.dbentity.ScHoliday;
import com.ei.itop.scmgnt.service.HolidayService;

/**
 * @author Jack.Qi
 * 
 */
@Service("holidayService")
public class HolidayServiceImpl implements HolidayService {

	private static final Logger log = Logger
			.getLogger(HolidayServiceImpl.class);

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, ScHoliday> holidayDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.scmgnt.service.HolidayService#getHolidayList(long,
	 * java.util.Date, java.util.Date)
	 */
	public List<ScHoliday> getHolidayList(long orgId, Date beginDate,
			Date endDate) throws Exception {
		// TODO Auto-generated method stub

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("orgId", orgId);
		if (beginDate != null) {
			hm.put("beginDate", DateUtils.string2Date(
					DateUtils.date2String(beginDate,
							DateUtils.FORMATTYPE_yyyyMMdd) + "000000",
					DateUtils.FORMATTYPE_yyyyMMddHHmmss));
		}
		if (endDate != null) {
			hm.put("endDate", DateUtils.string2Date(
					DateUtils.date2String(endDate,
							DateUtils.FORMATTYPE_yyyyMMdd) + "000000",
					DateUtils.FORMATTYPE_yyyyMMddHHmmss));
		}

		List<ScHoliday> holidays = holidayDAO.findByParams(
				"SC_HOLIDAY.queryHolidayList", hm);

		return holidays;
	}

}
