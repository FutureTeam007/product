<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="i18n" uri="../../WEB-INF/i18n.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="../common/commonHead.jsp"%>
	<%@ include file="../common/easyuiHead.jsp"%>
	<title><i18n:message code="i18n.opcenter.profile.PageTitle"/></title>
	<script type="text/javascript" src="<%=rootPath%>/page/opcenter/js/changeBaseInfo.js"></script>
	<script type="text/javascript">
		$(function(){
			if("${msg}"){
				$.messager.alert(i18n.dialog.AlertTitle,"${msg}");
			}
		});
	</script>
</head>
<body>
<jsp:include page="../common/pageMasterStart.jsp"></jsp:include>
<div class="register-wrapper">
	<div>
		<a class="btn btn-link top-close-link" style="margin-bottom:30px" href="<%=rootPath%>/page/incidentmgnt/main"><i18n:message code="i18n.nav.Back2Index"/></a>
	</div>
	<form class="form-horizonta register-form" style="width:70%" role="form" action="<%=rootPath%>/op/changebaseinfo" method="post" onsubmit="return validateModifyFormVars()">
		<div class="form-group clearfix">
		    <label for="acountNo" class="col-sm-3 control-label"><i18n:message code="i18n.opcenter.profile.AccountNoLabel"/></label>
		    <div class="col-sm-9">
		      	<input type="text" class="form-control small-control" id="acountNo" disabled="disabled" name="acountNo"  maxlength="30"/>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="opCode" class="col-sm-3 control-label"><i18n:message code="i18n.opcenter.profile.OpCodeLabel"/></label>
		    <div class="col-sm-9">
		      	<input type="text" class="form-control small-control" id="opCode"  name="opCode"  maxlength="30"/>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="companySel" class="col-sm-3 control-label"><i18n:message code="i18n.opcenter.profile.RoleLabel"/></label>
		    <div class="col-sm-9">
		     	<span class="radio-inline">
				  <input type="radio" name="jobRole"  value="2"/><i18n:message code="i18n.opcenter.profile.RoleBusiness"/>
				</span>
				<span class="radio-inline">
				  <input type="radio" name="jobRole"  value="3"/><i18n:message code="i18n.opcenter.profile.RoleIT"/>
				</span>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="chineseName" class="col-sm-3 control-label"><i18n:message code="i18n.opcenter.profile.ChineseNameLabel"/></label>
		    <div class="col-sm-9">
		      	<input type="text" class="form-control small-control" id="chineseName"  name="chineseName" maxlength="40"/>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="givenName" class="col-sm-3 control-label"><i18n:message code="ii18n.opcenter.profile.EnginshFirstNameLabel"/></label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control small-control" id="givenName"  name="givenName" maxlength="10"/>
		    </div>
		    <label for="familyName" class="col-sm-2 control-label"><i18n:message code="i18n.opcenter.profile.EnginshLastNameLabel"/></label>
		    <div class="col-sm-3">
		      <input type="text" class="form-control small-control" id="familyName"  name="familyName" maxlength="10"/>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="moduleSel" class="col-sm-3 control-label"><i18n:message code="i18n.opcenter.profile.GenderLabel"/></label>
		    <div class="col-sm-9">
		        <span class="radio-inline">
				  <input type="radio" name="gender" id="male" value="1" /><i18n:message code="i18n.opcenter.profile.GenderMr"/>
				</span>
				<span class="radio-inline">
				  <input type="radio" name="gender" id="female" value="2"/><i18n:message code="i18n.opcenter.profile.GenderMs"/>
				</span>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="mobileNo" class="col-sm-3 control-label"><i18n:message code="i18n.opcenter.profile.MobileLabel"/></label>
		    <div class="col-sm-9">
		      	<input type="text" class="form-control small-control" id="mobileNo"  name="mobileNo" maxlength="11"/>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="phoneNo" class="col-sm-3 control-label"><i18n:message code="i18n.opcenter.profile.PhoneLabel"/></label>
		    <div class="col-sm-2">
		      	<input type="text" class="form-control small-control" id="areaCode"  name="areaCode" maxlength="4"/>
		    </div>
		    <div class="col-sm-7">
		      	<input type="text" class="form-control small-control" id="phoneNo"  name="phoneNo"  maxlength="8"/>
		    </div>
  		</div>
  		<div class="form-group clearfix register-form-btns">
		    <input type="submit" class="btn btn-warning btn-lg" style="margin-left:40%" value="<i18n:message code="i18n.opcenter.profile.submitBtn"/>" />
  		</div>
	</form>
</div>
<jsp:include page="../common/pageMasterEnd.jsp"></jsp:include>
</body>
</html>