/**
 * 
 */
package com.ei.itop.common.util;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ailk.dazzle.util.type.DateUtils;
import com.ei.itop.common.bean.WorkPeriod;
import com.ei.itop.common.dbentity.ScHoliday;

/**
 * @author Jack.Qi
 * 
 */
public class SLAUtil {

	private static final Logger log = Logger.getLogger(SLAUtil.class);

	/**
	 * 判断一个日期时间是否在工作时段
	 * 
	 * @param toBeCheckedDateTime
	 *            待校验的日期时间
	 * @param workPeriodsOfDate
	 *            工作日工作时段列表
	 * @param holidays
	 *            假日列表
	 * @return 不在工作时段-返回空值，在工作时段-返回所在的工作时段
	 * @throws Exception
	 */
	protected static WorkPeriod isInWorkPeriod(Date toBeCheckedDateTime,
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
			if (DateUtils.getDiffMinutes(toBeCheckedDateTime,
					workPeriod.getBeginTime()) >= 0
					&& DateUtils.getDiffMinutes(toBeCheckedDateTime,
							workPeriod.getEndTime()) <= 0) {
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
	 *            假日列表
	 * @return 下一个工作时段
	 * @throws Exception
	 */
	protected static WorkPeriod getNextWorkPeriod(Date seedTime,
			List<WorkPeriod> workPeriodsOfDate, List<ScHoliday> holidays)
			throws Exception {

		// 当前日期当前时刻之后还有工作时段
		for (int i = 0; workPeriodsOfDate != null
				&& i < workPeriodsOfDate.size(); i++) {
			WorkPeriod workPeriod = workPeriodsOfDate.get(i);
			if (DateUtils.getDiffMinutes(seedTime, workPeriod.getBeginTime()) < 0) {
				// 返回该工作时段
				return workPeriod;
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
	 * 得到下一个工作日
	 * 
	 * @param seedTime
	 *            用于计算的基准时刻
	 * @param holidays
	 *            假日列表
	 * @return 下一个工作日
	 * @throws Exception
	 */
	protected static Date getNextWorkDate(Date seedTime,
			List<ScHoliday> holidays) throws Exception {
		return null;
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
	 *            工作日工作时段列表
	 * @param holidays
	 *            假日列表
	 * @return 截止时刻
	 * @throws Exception
	 */
	public static Date calculateCutOffTime(long remainingTime,
			Date timeInWorkPeriod, WorkPeriod workPeriod,
			List<WorkPeriod> workPeriodsOfDate, List<ScHoliday> holidays)
			throws Exception {

		if (DateUtils.getDiffMinutes(timeInWorkPeriod,
				workPeriod.getBeginTime()) < 0
				|| DateUtils.getDiffMinutes(timeInWorkPeriod,
						workPeriod.getEndTime()) > 0) {
			throw new Exception("入参有误，该方法只能根据某工作时段内的时刻递归运算");
		}

		// 得到工作时段剩余时长
		long currentWorkPeriodRemainingTime = DateUtils.getDiffMinutes(
				workPeriod.getEndTime(), timeInWorkPeriod);

		// 判断工作时段是否有足够的时长，如果有足够的时长，则计算截止时刻，退出递归
		if (currentWorkPeriodRemainingTime >= remainingTime) {
			// 根据传入的时刻，加上偏移分钟数，得出截止时刻并返回
			return DateUtils.dateOffset(timeInWorkPeriod, 12,
					(int) remainingTime);
		}

		// 取得下一个工作时段
		WorkPeriod nextWorkPeriod = getNextWorkPeriod(workPeriod.getEndTime(),
				workPeriodsOfDate, holidays);
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
		log.debug("asdf");
		Date d1 = DateUtils.string2Date("20141125030000",
				DateUtils.FORMATTYPE_yyyyMMddHHmmss);
		Date d2 = DateUtils.string2Date("20141125033000",
				DateUtils.FORMATTYPE_yyyyMMddHHmmss);
		long minuteOffSet = DateUtils.getDiffMinutes(d1, d2);
		log.debug(minuteOffSet);
		log.debug(DateUtils.date2String(d2,
				DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
		log.debug(DateUtils.date2String(DateUtils.dateOffset(d2, 12, 1),
				DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss));
	}

}
