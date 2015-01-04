/**
 * 
 */
package com.ei.itop.daemon.service;

import java.util.Date;

/**
 * @author QiLin
 * 
 */
public interface TimeoutRemindService {

	/**
	 * 响应超时邮件提醒
	 * 
	 * @param currentTime
	 *            当前时间
	 * @throws Exception
	 */
	public void responseTimeoutRemind(Date currentTime) throws Exception;

	/**
	 * 处理超时邮件提醒
	 * 
	 * @param currentTime
	 *            当前时间
	 * @throws Exception
	 */
	public void dealTimeoutRemind(Date currentTime) throws Exception;
}
