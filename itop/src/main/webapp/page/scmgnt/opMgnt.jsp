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
	<title><i18n:message code="i18n.scmgnt.main.OpNav"/></title>
	<script type="text/javascript" src="<%=rootPath%>/page/scmgnt/js/opMgnt.js"></script>
</head>
<body style="background:#fff;padding:20px">
<div class="inci-search pt15">
	<span class="form-item">
    	<label for="inciNo"><i18n:message code="i18n.scmgnt.opinfo.QryConditionLoginCode"/></label>
    	<input type="text" class="form-control" style="width:50%" id="userName"/>
	</span>
  	<span class="form-item">
    	<label for="inciShortDesc"><i18n:message code="i18n.scmgnt.opinfo.QryConditionOpName"/></label>
    	<input type="text" class="form-control" style="width:50%"  id="userCode"/>
  	</span>
  	<span class="form-item pull-right mb5" style="width:50%;margin-bottom:5px">
		<button type="button" class="btn btn-default btn-sm mr20 pull-right"  onclick="reset()">&nbsp;<i18n:message code="i18n.custmgnt.userinfo.ResetBtn"/>&nbsp;</button>
		<button type="button" class="btn btn-primary btn-sm mr15 pull-right"  onclick="query()">&nbsp;&nbsp;&nbsp;&nbsp;<i18n:message code="i18n.custmgnt.userinfo.QryBtn"/>&nbsp;&nbsp;&nbsp;&nbsp;</button>
	</span>
</div>
<div class="mb10">
	<div>
		<div class="list-inline pull-right mb5 mr10"><button type="button" class="btn btn-danger btn-sm" id="addOpBtn" onclick="addOp()"><i18n:message code="i18n.scmgnt.opinfo.AddOpBtn"/></button></div>
	</div>
	<table  style="width:99%;height:430px"  id="opDataTable"></table>
</div>
</body>
</html>