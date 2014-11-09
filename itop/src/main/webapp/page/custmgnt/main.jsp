<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="i18n" uri="../../WEB-INF/i18n.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="../common/commonHead.jsp"%>
	<%@ include file="../common/easyuiHead.jsp"%>
	<title><i18n:message code="i18n.custmgnt.main.PageTitle"/></title>
	<script type="text/javascript" src="<%=rootPath%>/page/custmgnt/js/main.js"></script>
</head>
<body style="background:#fff">
<jsp:include page="../common/pageMasterStart.jsp"></jsp:include>
<div class="cust-mgnt-sidebar pull-left">
	<div>
		<div id="custSel"></div>
	</div>
</div>
<div class="col-sm-9" style="padding-left:0px">
	<div class="cust-mgnt-nav">
		<ul class="nav nav-tabs" role="tablist">
		  <li order="1" role="presentation" class="active"><a href="javascript:void(0)"><i18n:message code="i18n.custmgnt.main.CustInfoNav"/></a></li>
		  <li order="2" role="presentation"><a  href="javascript:void(0)"><i18n:message code="i18n.custmgnt.main.UserInfoNav"/></a></li>
		  <li order="3" role="presentation"><a  href="javascript:void(0)"><i18n:message code="i18n.custmgnt.main.ProdInfoNav"/></a></li>
		  <!-- <li order="4" role="presentation"><a  href="javascript:void(0)">SLO规则管理</a></li> -->
		  <a class="btn btn-link top-close-link pull-right" href="<%=rootPath%>/page/incidentmgnt/main"><i18n:message code="i18n.nav.Back2Index"/></a>
		</ul>
	</div>
	<iframe id="cust-mgnt-iframe" name="cust-mgnt-iframe" frameborder="0" width="100%" style="background:#fff;"></iframe>
</div>
<jsp:include page="../common/pageMasterEnd.jsp"></jsp:include>
</body>
</html>