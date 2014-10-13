/**
 * 
 */
package com.ei.itop.login.bean;

/**
 * 用于保存用户填写的登录信息的业务Bean
 * 
 * @author Jack.Qi
 * 
 */
public class LoginInfo {

	private String loginCode;

	private String loginPasswd;

	/**
	 * @return the loginCode
	 */
	public String getLoginCode() {
		return loginCode;
	}

	/**
	 * @param loginCode
	 *            the loginCode to set
	 */
	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	/**
	 * @return the loginPasswd
	 */
	public String getLoginPasswd() {
		return loginPasswd;
	}

	/**
	 * @param loginPasswd
	 *            the loginPasswd to set
	 */
	public void setLoginPasswd(String loginPasswd) {
		this.loginPasswd = loginPasswd;
	}

	/**
	 * @return the identifyingCode
	 */
	public String getIdentifyingCode() {
		return identifyingCode;
	}

	/**
	 * @param identifyingCode
	 *            the identifyingCode to set
	 */
	public void setIdentifyingCode(String identifyingCode) {
		this.identifyingCode = identifyingCode;
	}

	private String identifyingCode;
}
