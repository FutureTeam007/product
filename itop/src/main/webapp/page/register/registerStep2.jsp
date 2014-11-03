<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="i18n" uri="../../WEB-INF/i18n.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="../common/commonHead.jsp"%>
	<title><i18n:message code="i18n.register.PageTitle"/></title>
</head>
<body>
<jsp:include page="../common/pageMasterStartNotLogin.jsp"></jsp:include>
<div class="register-nav">
	<div class="register-step"><i18n:message code="i18n.register.TitleStep1"/></div>
	<div class="register-step register-step-active"><i18n:message code="i18n.register.TitleStep2"/></div>
	<div class="register-step"><i18n:message code="i18n.register.TitleStep3"/></div>
</div>
<div class="register-wrapper">
	<div class="panel panel-warning">
      <div class="panel-heading">
        <h3 class="panel-title success-title"><i18n:message code="i18n.register.RegisterSuccess"/></h3>
      </div>
      <div class="panel-body success-content" >
         	<i18n:message code="i18n.register.ActivateLinkHasBeenSent"/>
      </div>
    </div>
</div>
<jsp:include page="../common/pageMasterEnd.jsp"></jsp:include>
</body>
</html>