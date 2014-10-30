package com.ei.itop.common.util;

import java.util.Date;

import com.ailk.dazzle.util.type.DateUtils;
/**
 * 模板中格式化时间
 * @author vintin
 *
 */
public class VelocityDateConvertor {
	
	public String convertDateTime(Date date){
		if(date==null){
			return "";
		}
		return DateUtils.date2String(date, DateUtils.FORMATTYPE_yyyy_MM_dd_HH_mm_ss);
	}
	
}
