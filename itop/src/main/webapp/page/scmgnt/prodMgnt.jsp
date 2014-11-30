<%@page import="com.ailk.dazzle.util.web.ActionUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="i18n" uri="../../WEB-INF/i18n.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="../common/commonHead.jsp"%>
	<%@ include file="../common/easyuiHead.jsp"%>
	<title><i18n:message code="i18n.scmgnt.main.ProdInfoNav"/></title>
	<script type="text/javascript" src="<%=rootPath%>/page/scmgnt/js/prodMgnt.js"></script>
</head>
<body style="background:#fff;padding:20px">
<div>
	<div>
		<div>
			<div class="list-inline pull-left mb5 ml3"><button type="button" class="btn btn-danger btn-sm" id="addProductBtn" onclick="addProduct()"><i18n:message code="i18n.scmgnt.prodinfo.AddProductBtn"/></button></div>
		</div>
		<table  style="width:99%;height:200px"  id="prodDataTable"></table>
	</div>
	<div>
		<div style="border-bottom:solid 1px #ccc;margin:10px auto 10px 0px;width:99%;color:#666"><i18n:message code="i18n.scmgnt.prodinfo.ModuleFormTitle"/></div>
		<div class="list-inline pull-left" style="width:32%">
			<div id="moduleTree" style="background:#f3f3f3;min-height:400px"></div>
		</div>
		<div class="list-inline pull-right" style="width:67%">
			<form class="form-horizonta inci-info-form" style="width:90%" role="form" >
				<div class="form-group clearfix">
				    <label for="moduleCode" class="col-sm-3 control-label"><i18n:message code="i18n.scmgnt.prodinfo.ModuleCodeLabel"/></label>
				    <div class="col-sm-9">
				    	<input type="hidden"  id="editType" name="editType" value="1" />
				    	<input type="hidden"  id="moduleId" name="moduleId" />
				    	<input type="hidden"  id="productId" name="productId" />
				      	<input type="text" class="form-control small-control" style="width:99%" id="moduleCode" name="moduleCode"  maxlength="30"/>
				    </div>
		  		</div>
		  		<div class="form-group clearfix">
				    <label for="moduleSel" class="col-sm-3 control-label"><i18n:message code="i18n.scmgnt.prodinfo.SupModuleLabel"/></label>
				    <div class="col-sm-9">
				      	<input style="width:100%" id="moduleSel" name="supModuleId" />
				    </div>
		  		</div>
		  		<div class="form-group clearfix">
				    <label for="moduleNameZh" class="col-sm-3 control-label"><i18n:message code="i18n.scmgnt.prodinfo.ModuleNameZhLabel"/></label>
				    <div class="col-sm-9">
				      	<input type="text" class="form-control small-control" style="width:99%" id="moduleNameZh"  name="moduleNameZh"  maxlength="30"/>
				    </div>
		  		</div>
		  		<div class="form-group clearfix">
				    <label for="moduleNameEn" class="col-sm-3 control-label"><i18n:message code="i18n.scmgnt.prodinfo.ModuleNameEnLabel"/></label>
				    <div class="col-sm-9">
				      	<input type="text" class="form-control small-control" style="width:99%" id="moduleNameEn"  name="moduleNameEn"  maxlength="30"/>
				    </div>
		  		</div>
		  		<div class="form-group clearfix">
				    <label  class="col-sm-3 control-label"><i18n:message code="i18n.scmgnt.prodinfo.ModuleDescZhLabel"/></label>
				    <div class="col-sm-9">
				      	<textarea class="form-control small-control" style="height:60px" rows="3" style="width:99%" id="moduleDescZh"  name="moduleDescZh"></textarea>
				    </div>
		  		</div>
		  		<div class="form-group clearfix">
				    <label  class="col-sm-3 control-label"><i18n:message code="i18n.scmgnt.prodinfo.ModuleDescEnLabel"/></label>
				    <div class="col-sm-9">
				      	<textarea class="form-control small-control" style="height:60px" rows="3" style="width:99%" id="moduleDescEn"  name="moduleDescEn"></textarea>
				    </div>
		  		</div>
		  		<div class="form-group clearfix">
				    <label for="status" class="col-sm-3 control-label"><i18n:message code="i18n.scmgnt.prodinfo.ModuleStatusLabel"/></label>
				    <div class="col-sm-9">
				     	<span class="radio-inline">
						  <input type="radio" name="status"  value="1" checked="checked" /><i18n:message code="i18n.scmgnt.prodinfo.StatusNormal"/>
						</span>
						<span class="radio-inline">
						  <input type="radio" name="status"  value="0" /><i18n:message code="i18n.scmgnt.prodinfo.StatusDisable"/>
						</span>
				    </div>
		  		</div>
		  		<div class="form-group clearfix register-form-btns text-center">
				    <input type="button" id="addBtn" class="btn btn-primary btn-outline mr20" onclick="addNewModule()" value="<i18n:message code="i18n.scmgnt.prodinfo.AddNewBtn"/>" />
				    <input type="button" id="editBtn" class="btn btn-primary btn-outline mr20" onclick="editModule()" value="<i18n:message code="i18n.scmgnt.prodinfo.EditBtn"/>" />
				    <input type="button" id="deleteBtn" class="btn btn-primary mr20" onclick="removeModule()" value="<i18n:message code="i18n.scmgnt.prodinfo.DeleteBtn"/>" />
				    <input type="button" id="backBtn" class="undis btn btn-primary btn-outline mr20" onclick="backFront()" value="<i18n:message code="i18n.scmgnt.prodinfo.BackBtn"/>" />
				    <input type="button" id="submitBtn" class="btn btn-primary undis" onclick="submitModule()" value="<i18n:message code="i18n.scmgnt.prodinfo.SubmitBtn"/>" />
		  		</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>