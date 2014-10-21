package com.ei.itop.common.constants;

public interface SysConstants {
	
	//Session变量
	public interface SessionAttribute{
		String	LOGIN_VERIFY_CODE = "loginVerifyCode";
		String	OP_SESSION = "opInfo";
	}
	
	//Cookie变量
	public interface CookieAttribute{
		String	OP_TYPE = "opType";
		String	ACCOUNT_NO = "accountNo";
	}
	
	//Op变量
	public interface OpAttribute{
		String	OP_ROLE_OP = "OP";
		String	OP_ROLE_USER = "USER";
	}
	
}
