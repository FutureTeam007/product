package com.ei.itop.scmgnt.bean;

import java.util.List;

import com.ailk.dazzle.util.tree.AbstractTreeNode;
import com.ailk.dazzle.util.tree.JqEasyUITreeNode;
import com.ei.itop.common.dbentity.ScModule;

public class ModuleTreeMNode extends AbstractTreeNode implements JqEasyUITreeNode{

	public String getIconCls() {
		return "";
	}
	
	public Object getAttributes() {
		return super.data;
	}

	public List getChildren() {
		return super.subNodes;
	}

	public Object getId() {
		return ((ScModule)super.data).getScModuleId();
	}

	public Integer getOrderNo() {
		return Integer.parseInt(((ScModule)super.data).getScModuleId()+"");
	}

	public Object getParentId() {
		return ((ScModule)super.data).getSupModuleId();
	}

	public String getText() {
		int state = ((ScModule)super.data).getState();
		if(state==0){
			return "<font color=\"red\">"+((ScModule)super.data).getModuleName()+"</font>";
		}
		return ((ScModule)super.data).getModuleName();
	}
}
