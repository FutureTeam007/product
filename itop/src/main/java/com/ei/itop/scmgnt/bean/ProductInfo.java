package com.ei.itop.scmgnt.bean;

import com.ei.itop.common.dbentity.ScProduct;

public class ProductInfo extends ScProduct{

	private String productNameZh;
	
	private String productNameEn;
	
	private String productDescZh;
	
	private String productDescEn;
	

	public String getProductNameZh() {
		return productNameZh;
	}

	public void setProductNameZh(String productNameZh) {
		this.productNameZh = productNameZh;
	}

	public String getProductNameEn() {
		return productNameEn;
	}

	public void setProductNameEn(String productNameEn) {
		this.productNameEn = productNameEn;
	}

	public String getProductDescZh() {
		return productDescZh;
	}

	public void setProductDescZh(String productDescZh) {
		this.productDescZh = productDescZh;
	}

	public String getProductDescEn() {
		return productDescEn;
	}

	public void setProductDescEn(String productDescEn) {
		this.productDescEn = productDescEn;
	}
}
