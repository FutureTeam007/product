package com.ei.itop.register.bean;

import java.util.List;

import com.ailk.dazzle.util.tree.AbstractTreeNode;
import com.ailk.dazzle.util.tree.JqEasyUITreeNode;
import com.ei.itop.common.dbentity.CcCust;

public class CustTreeNode  extends AbstractTreeNode implements JqEasyUITreeNode{

	public String getText() {
		return ((CcCust)super.data).getCustName();
	}

	public List getChildren() {
		return super.subNodes;
	}

	public Object getAttributes() {
		return super.data;
	}

	@Override
	public Integer getOrderNo() {
		return Integer.parseInt(((CcCust)super.data).getCcCustId()+"");
	}

	@Override
	public Object getParentId() {
		return ((CcCust)super.data).getSupCustId();
	}

	@Override
	public Object getId() {
		return ((CcCust)super.data).getCcCustId();
	}

}
