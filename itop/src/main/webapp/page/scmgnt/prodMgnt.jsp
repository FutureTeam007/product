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
<div class="prodDataTable mb10">
	<div>
		<div class="list-inline pull-right mb5 mr10"><button type="button" class="btn btn-danger btn-sm" id="addProductBtn" onclick="addProduct()"><i18n:message code="i18n.scmgnt.prodinfo.AddProductBtn"/></button></div>
	</div>
	<table  style="width:99%;height:200px"  id="prodDataTable"></table>
</div>
<div class="moduleDataTable">
	<div>
		<div class="list-inline pull-left pl10"><i18n:message code="i18n.scmgnt.prodinfo.ModuleTableTitle"/></div>
		<div class="list-inline pull-right mb5 mr10"><button type="button" class="btn btn-primary btn-sm" id="addModuleBtn" onclick="addModule()"><i18n:message code="i18n.scmgnt.prodinfo.AddModuleBtn"/></button></div>
	</div>
	<table  style="width:99%;height:200px"  id="moduleDataTable"></table>
</div>
</body>
</html>