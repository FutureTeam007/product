<%@page import="com.ailk.dazzle.util.web.ActionUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="i18n" uri="../../WEB-INF/i18n.tld"%>
<%
	String custId = request.getParameter("custId");
	String custLevel = request.getParameter("orgLevel");
	String domainName = request.getParameter("domainName");
	String custName = ActionUtils.transParam(request.getParameter("custName"), "UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="../common/commonHead.jsp"%>
	<%@ include file="../common/easyuiHead.jsp"%>
	<title><i18n:message code="i18n.custmgnt.main.PageTitle"/></title>
	<script type="text/javascript">
		var custId = "<%=custId%>";
		var pCustName = "<%=custName%>";
		var pDomainName = "<%=domainName%>";
		var custLevel = "<%=custLevel%>";
	</script>
	<script type="text/javascript" src="<%=rootPath%>/page/custmgnt/js/prodMgnt.js"></script>
</head>
<body style="background:#fff;padding:20px">
<div class="prodDataTable mb10">
	<div>
		<div class="list-inline pull-left pl10"><i18n:message code="i18n.custmgnt.prodinfo.CustNameLabel"/>：[<%=custName%>]</div>
		<div class="list-inline pull-right mb5 mr10"><button type="button" class="btn btn-danger btn-sm" id="addProductBtn" onclick="addProduct()"><i18n:message code="i18n.custmgnt.prodinfo.AddProductBtn"/></button></div>
	</div>
	<table  style="width:99%;height:200px"  id="prodDataTable"></table>
</div>
<div class="opDataTable">
	<div>
		<div class="list-inline pull-left pl10"><i18n:message code="i18n.custmgnt.prodinfo.OpTableTitle"/></div>
		<div class="list-inline pull-right mb5 mr10"><button type="button" class="btn btn-primary btn-sm" id="addProductBtn" onclick="addOp()"><i18n:message code="i18n.custmgnt.prodinfo.AddOpBtn"/></button></div>
	</div>
	<table  style="width:99%;height:200px"  id="opDataTable"></table>
</div>
</body>
</html>