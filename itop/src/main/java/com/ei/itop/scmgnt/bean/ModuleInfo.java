package com.ei.itop.scmgnt.bean;

import com.ei.itop.common.dbentity.ScModule;

public class ModuleInfo extends ScModule{

	private String moduleNameZh;
	
	private String moduleNameEn;
	
	private String moduleDescZh;
	
	private String moduleDescEn;

	public String getModuleNameZh() {
		return moduleNameZh;
	}

	public void setModuleNameZh(String moduleNameZh) {
		this.moduleNameZh = moduleNameZh;
	}

	public String getModuleNameEn() {
		return moduleNameEn;
	}

	public void setModuleNameEn(String moduleNameEn) {
		this.moduleNameEn = moduleNameEn;
	}

	public String getModuleDescZh() {
		return moduleDescZh;
	}

	public void setModuleDescZh(String moduleDescZh) {
		this.moduleDescZh = moduleDescZh;
	}

	public String getModuleDescEn() {
		return moduleDescEn;
	}

	public void setModuleDescEn(String moduleDescEn) {
		this.moduleDescEn = moduleDescEn;
	}
	
}
