package com.ei.itop.custmgnt.bean;

import com.ei.itop.common.dbentity.CcCustProdOp;

public class CustProductOpInfo extends CcCustProdOp{

	private String opName;
	
	private String lastName;
	
	private String firstName;
	
	private String opCode;

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public String getOpCode() {
		return opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
}
