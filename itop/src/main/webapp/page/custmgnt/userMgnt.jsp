<%@page import="com.ailk.dazzle.util.web.ActionUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="i18n" uri="../../WEB-INF/i18n.tld"%>
<%
	String custId = request.getParameter("custId");
	String domainName = request.getParameter("domainName");
	String custName = ActionUtils.transParamDecode(request.getParameter("custName"), "UTF-8");
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
	</script>
	<script type="text/javascript" src="<%=rootPath%>/page/custmgnt/js/userMgnt.js"></script>
</head>
<body style="background:#fff;">
<div class="inci-search pt15">
	<span class="form-item">
    	<label for="inciNo"><i18n:message code="i18n.custmgnt.userinfo.QryConditionUserCode"/></label>
    	<input type="text" class="form-control" style="width:50%" id="userName"/>
	</span>
  	<span class="form-item">
    	<label for="inciShortDesc"><i18n:message code="i18n.custmgnt.userinfo.QryConditionUserName"/></label>
    	<input type="text" class="form-control" style="width:50%"  id="userCode"/>
  	</span>
  	<span class="form-item pull-right mb5" style="width:50%;margin-bottom:5px">
		<button type="button" class="btn btn-default btn-sm mr20 pull-right"  onclick="reset()">&nbsp;<i18n:message code="i18n.custmgnt.userinfo.ResetBtn"/>&nbsp;</button>
		<button type="button" class="btn btn-primary btn-sm mr15 pull-right"  onclick="query()">&nbsp;&nbsp;&nbsp;&nbsp;<i18n:message code="i18n.custmgnt.userinfo.QryBtn"/>&nbsp;&nbsp;&nbsp;&nbsp;</button>
	</span>
</div>
<div class="userDataTable">
	<table  style="width:99%;height:430px"  id="userDataTable"></table>
</div>
</body>
</html>