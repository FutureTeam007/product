/**
 * 
 */
package com.ei.itop.register.bean;

import com.ei.itop.common.dbentity.CcUser;

/**
 * @author Jack.Qi
 * 
 */
public class RegisterInfo extends CcUser {

	private String loginPasswdConfirmed;

	/**
	 * @return the loginPasswdConfirmed
	 */
	public String getLoginPasswdConfirmed() {
		return loginPasswdConfirmed;
	}

	/**
	 * @param loginPasswdConfirmed
	 *            the loginPasswdConfirmed to set
	 */
	public void setLoginPasswdConfirmed(String loginPasswdConfirmed) {
		this.loginPasswdConfirmed = loginPasswdConfirmed;
	}
}
