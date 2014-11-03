<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="i18n" uri="../../WEB-INF/i18n.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="../common/commonHead.jsp"%>
	<%@ include file="../common/easyuiHead.jsp"%>
	<title><i18n:message code="i18n.usercenter.pwdchange.PageTitle"/></title>
	<script type="text/javascript" src="<%=rootPath%>/page/usercenter/js/changePwd.js"></script>
</head>
<body>
<jsp:include page="../common/pageMasterStart.jsp"></jsp:include>
<div class="register-wrapper">
	<div>
		<a class="btn btn-link top-close-link" style="margin-bottom:30px" href="<%=rootPath%>/page/incidentmgnt/main"><i18n:message code="i18n.nav.Back2Index"/></a>
	</div>
	<form class="form-horizonta register-form" role="form">
		<div class="form-group clearfix">
		    <label for="passwd" class="col-sm-2 control-label"><i18n:message code="i18n.usercenter.pwdchange.OldPasswordLabel"/></label>
		    <div class="col-sm-7">
		      	<input type="password" class="form-control" id="oldPasswd" maxlength="20"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    </div>
  		</div>
		<div class="form-group clearfix">
		    <label for="passwd" class="col-sm-2 control-label"><i18n:message code="i18n.usercenter.pwdchange.NewPasswordLabel"/></label>
		    <div class="col-sm-7">
		      	<input type="password" class="form-control" id="newPasswd" maxlength="20"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="passwdConfirm" class="col-sm-2 control-label"><i18n:message code="i18n.usercenter.pwdchange.NewPasswordConfirmLabel"/></label>
		    <div class="col-sm-7">
		      	<input type="password" class="form-control" id="passwdConfirm" maxlength="20"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    </div>
  		</div>
  		<div class="form-group clearfix register-form-btns">
		    <a class="btn btn-warning btn-lg" style="margin-left:30%" href="javascript:changePassword()"><i18n:message code="i18n.usercenter.pwdchange.ChangeSubmit"/></a>
  		</div>
	</form>
</div>
<jsp:include page="../common/pageMasterEnd.jsp"></jsp:include>
</body>
</html>