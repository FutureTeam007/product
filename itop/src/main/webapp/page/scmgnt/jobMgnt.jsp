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
	<title><i18n:message code="i18n.scmgnt.main.JobNav"/></title>
	<script type="text/javascript" src="<%=rootPath%>/page/scmgnt/js/jobMgnt.js"></script>
</head>
<body style="background:#fff;padding:20px">
<div class="mb10">
	<div>
		<div class="list-inline pull-left mb5 ml3"><button type="button" class="btn btn-danger btn-sm" id="addJobBtn" onclick="addJob()"><i18n:message code="i18n.scmgnt.jobinfo.AddJobBtn"/></button></div>
	</div>
	<table  style="width:99%;height:400px"  id="jobDataTable"></table>
</div>
</body>
</html>