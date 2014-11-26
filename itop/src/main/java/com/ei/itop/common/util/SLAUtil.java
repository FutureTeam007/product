/**
 * 
 */
package com.ei.itop.common.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ailk.dazzle.util.type.DateUtils;
import com.ei.itop.common.bean.WorkPeriod;
import com.ei.itop.common.dbentity.ScHoliday;

/**
 * 记录：DateUtils.getDiff系列函数有BUG，后面不为全0时有一个单位的误差
 * 
 * @author Jack.Qi
 * 
 */
public class SLAUtil {

	private static final Logger log = Logger.getLogger(SLAUtil.class);

	private long getDateOffset(Date d1, Date d2, String type) throws Exception {
		Date tmpD1 = d1;
		Date tmpD2 = d2;
		// 天
		if ("d".equals(type)) {
			tmpD1 = DateUtils.string2Date(
					DateUtils.date2String(tmpD1, DateUtils.FORMATTYPE_yyyyMMdd)
							+ "000000", DateUtils.FORMATTYPE_yyyyMMddHHmmss);
			tmpD2 = DateUtils.string2Date(
					DateUtils.date2String(tmpD2, DateUtils.FORMATTYPE_yyyyMMdd)
							+ "000000", DateUtils.FORMATTYPE_yyyyMMddHHmmss);
			return DateUtils.getDiffDays(tmpD1, tmpD2);
		}
		// 分钟
		else if ("m".equals(type)) {
			tmpD1 = DateUtils.string2Date(
					"20010101"
							+ DateUtils.date2String(tmpD1,
									DateUtils.FORMATTYPE_HHmmss),
					DateUtils.FORMATTYPE_yyyyMMddHHmmss);
			tmpD2 = DateUtils.string2Date(
					"20010101"
							+ DateUtils.date2String(tmpD2,
									DateUtils.FORMATTYPE_HHmmss),
					DateUtils.FORMATTYPE_yyyyMMddHHmmss);
			return DateUtils.getDiffMinutes(tmpD1, tmpD2);
		}

		throw new Exception("不支持的类型，目前支持d-天、m-分钟");
	}

	/**
	 * 判断一个日期时间是否在工作时段
	 * 
	 * @param toBeCheckedDateTime
	 *            待校验的日期时间
	 * @param workPeriodsOfDate
	 *            工作日工作时段列表，必须已经按时间先后排序
	 * @param holidays
	 *            当天及以后的假日列表，升序排列
	 * @return 不在工作时段-返回空值，在工作时段-返回所在的工作时段
	 * @throws Exception
	 */
	public WorkPeriod isInWorkPeriod(Date toBeCheckedDateTime,
			List<WorkPeriod> workPeriodsOfDate, List<ScHoliday> holidays)
			throws Exception {

		// 判断是否为工作日
		boolean isHoliday = false;
		for (int i = 0; holidays != null && i < holidays.size(); i++) {
			ScHoliday holiday = holidays.get(i);
			if (DateUtils
					.getDiffDays(holiday.getHoliday(), toBeCheckedDateTime) == 0) {
				isHoliday = true;
				break;
			}
		}

		// 是节假日
		if (isHoliday) {
			// 不在工作时段
			return null;
		}

		// 是工作日
		// 判断是否在工作时段
		for (int i = 0; workPeriodsOfDate != null
				&& i < workPeriodsOfDate.size(); i++) {
			WorkPeriod workPeriod = workPeriodsOfDate.get(i);
			// 大于等于时段开始时刻，且小于等于时段结束时刻，证明在该工作时段内，返回该工作时段
			if (getDateOffset(toBeCheckedDateTime, workPeriod.getBeginTime(),
					"m") >= 0
					&& getDateOffset(toBeCheckedDateTime,
							workPeriod.getEndTime(), "m") <= 0) {
				return workPeriod;
			}
		}
		// 不在工作时段
		return null;
	}

	/**
	 * 得到下一个工作时段，如果当前日期已经没有工作时段，则自动查找下一工作日的第一工作时段
	 * 
	 * @param seedTime
	 *            用于计算的基准时刻
	 * @param workPeriodsOfDate
	 *            工作日工作时段列表，必须已经按时间先后排序
	 * @param holidays
	 *            当天及以后的假日列表，升序排列
	 * @return 下一个工作时段
	 * @throws Exception
	 */
	public WorkPeriod getNextWorkPeriod(Date seedTime,
			List<WorkPeriod> workPeriodsOfDate, List<ScHoliday> holidays)
			throws Exception {

		// log.debug("getNextWorkPeriod.seedTime is " + seedTime);

		// 当前日期当前时刻之后还有工作时段
		for (int i = 0; workPeriodsOfDate != null
				&& i < workPeriodsOfDate.size(); i++) {
			WorkPeriod workPeriod = workPeriodsOfDate.get(i);
			// log.debug("&&&&"
			// + getDateOffset(seedTime, workPeriod.getBeginTime(), "m"));
			if (getDateOffset(seedTime, workPeriod.getBeginTime(), "m") < 0) {
				// 纠正年月日
				WorkPeriod rtnValue = new WorkPeriod();
				rtnValue.setBeginTime(DateUtils.string2Date(
						DateUtils.date2String(seedTime,
								DateUtils.FORMATTYPE_yyyyMMdd)
								+ DateUtils.date2String(
										workPeriod.getBeginTime(),
										DateUtils.FORMATTYPE_HHmmss),
						DateUtils.FORMATTYPE_yyyyMMddHHmmss));
				rtnValue.setEndTime(DateUtils.string2Date(
						DateUtils.date2String(seedTime,
								DateUtils.FORMATTYPE_yyyyMMdd)
								+ DateUtils.date2String(
										workPeriod.getEndTime(),
										DateUtils.FORMATTYPE_HHmmss),
						DateUtils.FORMATTYPE_yyyyMMddHHmmss));
				// 返回该工作时段
				return rtnValue;
			}
		}

		// 当前日期当前时刻之后已没有工作时段
		// 查找下一个工作日
		Date nextWorkDate = getNextWorkDate(seedTime, holidays);
		// 返回下一个工作日的第一个工作时段
		WorkPeriod workPeriod = workPeriodsOfDate.get(0);
		Date beginTime = DateUtils.string2Date(
				DateUtils.date2String(nextWorkDate,
						DateUtils.FORMATTYPE_yyyyMMdd)
						+ DateUtils.date2String(workPeriod.getBeginTime(),
								DateUtils.FORMATTYPE_HHmmss),
				DateUtils.FORMATTYPE_yyyyMMddHHmmss);
		Date endTime = DateUtils.string2Date(
				DateUtils.date2String(nextWorkDate,
						DateUtils.FORMATTYPE_yyyyMMdd)
						+ DateUtils.date2String(workPeriod.getEndTime(),
								DateUtils.FORMATTYPE_HHmmss),
				DateUtils.FORMATTYPE_yyyyMMddHHmmss);
		workPeriod.setBeginTime(beginTime);
		workPeriod.setEndTime(endTime);

		return workPeriod;
	}

	/**
	 * 得到下一个工作日，递归
	 * 
	 * @param seedTime
	 *            用于计算的基准时刻
	 * @param holidays
	 *            当天及以后的假日列表，升序排列
	 * @return 下一个工作日，只精确到年月日
	 * @throws Exception
	 */
	protected Date getNextWorkDate(Date seedTime, List<ScHoliday> holidays)
			throws Exception {

		Date tmpSeedTime = DateUtils.string2Date(
				DateUtils.date2String(seedTime, DateUtils.FORMATTYPE_yyyyMMdd),
				DateUtils.FORMATTYPE_yyyyMMdd);

		// 得到下一天的日期
		Date tomorrow = DateUtils.dateOffset(tmpSeedTime, Calendar.DATE, 1);
		// log.debug(tomorrow);

		// 假日标志
		boolean isHoliday = false;
		for (int i = 0; holidays != null && i < holidays.size(); i++) {
			ScHoliday holiday = holidays.get(i);
			Date holidayDate = DateUtils.string2Date(DateUtils.date2String(
					holiday.getHoliday(), DateUtils.FORMATTYPE_yyyyMMdd),
					DateUtils.FORMATTYPE_yyyyMMdd);
			if (getDateOffset(tomorrow, holidayDate, "d") == 0) {
				isHoliday = true;
				break;
			}
		}

		// log.debug("isHoliday is " + isHoliday);
		// 下一天是假日
		if (isHoliday) {
			// 递归计算下一天
			return getNextWorkDate(tomorrow, holidays);
		}
		// 下一天不是假日
		else {
			// 返回下一天
			return tomorrow;
		}
	}

	/**
	 * 从某一工作时段内的某一时刻，根据剩余时长，递归计算截止时刻
	 * 
	 * @param remainingTime
	 *            用于判断的剩余时长（分钟数）
	 * @param timeInWorkPeriod
	 *            工作时段内的某一时刻，或工作时段的开始时刻
	 * @param workPeriod
	 *            工作时段
	 * @param workPeriodsOfDate
	 *            工作日工作时段列表，必须已经按时间先后排序
	 * @param holidays
	 *            当天及以后的假日列表，升序排列
	 * @return 截止时刻
	 * @throws Exception
	 */
	public Date calculateCutOffTime(long remainingTime, Date timeInWorkPeriod,
			WorkPeriod workPeriod, List<WorkPeriod> workPeriodsOfDate,
			List<ScHoliday> holidays) throws Exception {

		// log.debug("*************");
		// log.debug("remainingTime is " + remainingTime);
		// log.debug("timeInWorkPeriod is " + timeInWorkPeriod);
		// log.debug("workPeriod.getBeginTime() is " +
		// workPeriod.getBeginTime());
		// log.debug("workPeriod.getEndTime() is " + workPeriod.getEndTime());

		if (getDateOffset(timeInWorkPeriod, workPeriod.getBeginTime(), "m") < 0
				|| getDateOffset(timeInWorkPeriod, workPeriod.getEndTime(), "m") > 0) {
			throw new Exception("入参有误，该方法只能根据某工作时段内的时刻递归运算");
		}

		// 得到工作时段剩余时长
		long currentWorkPeriodRemainingTime = getDateOffset(
				workPeriod.getEndTime(), timeInWorkPeriod, "m");
		// log.debug("currentWorkPeriodRemainingTime is "
		// + currentWorkPeriodRemainingTime);

		// 判断工作时段是否有足够的时长，如果有足够的时长，则计算截止时刻，退出递归
		if (currentWorkPeriodRemainingTime >= remainingTime) {
			// 根据传入的时刻，加上偏移分钟数，得出截止时刻并返回
			return DateUtils.dateOffset(timeInWorkPeriod, Calendar.MINUTE,
					(int) remainingTime);
		}

		// 取得下一个工作时段
		WorkPeriod nextWorkPeriod = getNextWorkPeriod(workPeriod.getEndTime(),
				workPeriodsOfDate, holidays);
		// log.debug("nextWorkPeriod is " + nextWorkPeriod.getBeginTime() + ","
		// + nextWorkPeriod.getEndTime());
		// 当前工作时段没有足够的时长，递归下一个工作时段
		return calculateCutOffTime(remainingTime
				- currentWorkPeriodRemainingTime,
				nextWorkPeriod.getBeginTime(), nextWorkPeriod,
				workPeriodsOfDate, holidays);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		SLAUtil sla = new SLAUtil();

		log.debug("asdf");
		Date d1 = DateUtils.string2Date("20141125030055",
				DateUtils.FORMATTYPE_yyyyMMddHHmmss);
		Date d2 = DateUtils.string2Date("20141125033050",
				DateUtils.FORMATTYPE_yyyyMMddHHmmss);
		long minuteOffSet = 0;
		minuteOffSet = DateUtils.getDiffDays(d1, d2);
		log.debug(minuteOffSet);
		minuteOffSet = DateUtils.getDiffMinutes(d1, d2);
		log.debug(minuteOffSet);
		log.debug(DateUtils.date2String(d2,
				DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
		log.debug(DateUtils.date2String(
				DateUtils.dateOffset(d2, Calendar.MINUTE, 1),
				DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
		log.debug(DateUtils.date2String(
				DateUtils.dateOffset(d2, Calendar.DATE, 1),
				DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));

		List<ScHoliday> holidays = new ArrayList<ScHoliday>();
		ScHoliday sh1 = new ScHoliday();
		sh1.setScOrgId((long) 2001);
		sh1.setHoliday(DateUtils.string2Date("20141127",
				DateUtils.FORMATTYPE_yyyyMMdd));
		ScHoliday sh2 = new ScHoliday();
		sh2.setScOrgId((long) 2001);
		sh2.setHoliday(DateUtils.string2Date("20141129",
				DateUtils.FORMATTYPE_yyyyMMdd));
		ScHoliday sh3 = new ScHoliday();
		sh3.setScOrgId((long) 2001);
		sh3.setHoliday(DateUtils.string2Date("20141201",
				DateUtils.FORMATTYPE_yyyyMMdd));
		ScHoliday sh4 = new ScHoliday();
		sh4.setScOrgId((long) 2001);
		sh4.setHoliday(DateUtils.string2Date("20141202",
				DateUtils.FORMATTYPE_yyyyMMdd));
		holidays.add(sh1);
		holidays.add(sh2);
		holidays.add(sh3);
		holidays.add(sh4);

		Date date = DateUtils.string2Date("20141130013000",
				DateUtils.FORMATTYPE_yyyyMMddHHmmss);
		log.debug("********");
		log.debug(DateUtils.date2String(sla.getNextWorkDate(date, holidays),
				DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));

		List<WorkPeriod> workPeriodsOfDate = new ArrayList<WorkPeriod>();
		WorkPeriod wp1 = new WorkPeriod();
		wp1.setBeginTime(DateUtils.string2Date("20141126080000",
				DateUtils.FORMATTYPE_yyyyMMddHHmmss));
		wp1.setEndTime(DateUtils.string2Date("20141126120000",
				DateUtils.FORMATTYPE_yyyyMMddHHmmss));
		WorkPeriod wp2 = new WorkPeriod();
		wp2.setBeginTime(DateUtils.string2Date("20141126130000",
				DateUtils.FORMATTYPE_yyyyMMddHHmmss));
		wp2.setEndTime(DateUtils.string2Date("20141126170000",
				DateUtils.FORMATTYPE_yyyyMMddHHmmss));
		workPeriodsOfDate.add(wp1);
		workPeriodsOfDate.add(wp2);
		Date timeInWorkPeriod = DateUtils.string2Date("20141126090000",
				DateUtils.FORMATTYPE_yyyyMMddHHmmss);
		Date finalDate = sla.calculateCutOffTime(new Long(999999),
				timeInWorkPeriod, wp1, workPeriodsOfDate, holidays);
		log.debug("********");
		log.debug(finalDate);

		// log.debug("#########"
		// + DateUtils.getDiffMinutes(DateUtils.string2Date(
		// "20141126170000", DateUtils.FORMATTYPE_yyyyMMddHHmmss),
		// DateUtils.string2Date("20141126130000",
		// DateUtils.FORMATTYPE_yyyyMMddHHmmss)));
		//
		// log.debug("%%%");
		// WorkPeriod wp = sla.getNextWorkPeriod(DateUtils.string2Date(
		// "20141128120000", DateUtils.FORMATTYPE_yyyyMMddHHmmss),
		// workPeriodsOfDate, holidays);
		// log.debug("%%%" + wp.getBeginTime() + "," + wp.getEndTime());
	}
}
