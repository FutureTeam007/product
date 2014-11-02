<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="i18n" uri="../../WEB-INF/i18n.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="../common/commonHead.jsp"%>
	<%@ include file="../common/easyuiHead.jsp"%>
	<title><i18n:message code="i18n.register.PageTitle"/></title>
	<script type="text/javascript" src="<%=rootPath%>/page/register/js/registerStep1.js"></script>
</head>
<body>
<jsp:include page="../common/pageMasterStartNotLogin.jsp"></jsp:include>
<div class="register-nav">
	<div class="register-step register-step-active"><i18n:message code="i18n.register.TitleStep1"/></div>
	<div class="register-step"><i18n:message code="i18n.register.TitleStep2"/></div>
	<div class="register-step"><i18n:message code="i18n.register.TitleStep3"/></div>
</div>
<div class="register-wrapper">
	<form class="form-horizonta register-form" role="form" action="<%=rootPath%>/doRegister" method="post" onsubmit="return validateRegisterFormVars()">
		<div class="form-group clearfix">
		    <label for="acountNo" class="col-sm-2 control-label"><i18n:message code="i18n.register.AccountNoLabel"/></label>
		    <div class="col-sm-7">
		      	<input type="text" class="form-control small-control" id="acountNo" name="acountNo" value="${acountNo}" onblur="queryCompanyList()" maxlength="30"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		     	${accountMsg}
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="passwd" class="col-sm-2 control-label"><i18n:message code="i18n.register.PasswordLabel"/></label>
		    <div class="col-sm-7">
		      	<input type="password" class="form-control small-control" id="passwd" name="passwd"  maxlength="30"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="passwdConfirm" class="col-sm-2 control-label"><i18n:message code="i18n.register.PasswordConfirmLabel"/></label>
		    <div class="col-sm-7">
		      	<input type="password" class="form-control small-control" id="passwdConfirm" maxlength="30"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="companySel" class="col-sm-2 control-label"><i18n:message code="i18n.register.CompanyLabel"/></label>
		    <div class="col-sm-7">
		      <input class="easyui-combotree" style="width:100%"  name="companySel" id="companySel" data-options="
		      		panelHeight:'auto'"
			  />
			  <input type="hidden" name="companyId" id="companyId"/>
			  <input type="hidden" name="companyName" id="companyName"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		     	<i18n:message code="i18n.register.CompanyLabelTip"/>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="companySel" class="col-sm-2 control-label"><i18n:message code="i18n.register.RoleLabel"/></label>
		    <div class="col-sm-7">
		     	<span class="radio-inline">
				  <input type="radio" name="jobRole"  value="2" checked="checked" /><i18n:message code="i18n.register.RoleBusiness"/>
				</span>
				<span class="radio-inline">
				  <input type="radio" name="jobRole"  value="3"/><i18n:message code="i18n.register.RoleIT"/>
				</span>
		    </div>
		    <div class="col-sm-3 form-desc"></div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="chineseName" class="col-sm-2 control-label"><i18n:message code="i18n.register.ChineseNameLabel"/></label>
		    <div class="col-sm-7">
		      	<input type="text" class="form-control small-control" id="chineseName" value="${chineseName}" name="chineseName" maxlength="40"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="givenName" class="col-sm-2 control-label"><i18n:message code="i18n.register.EnginshFirstNameLabel"/></label>
		    <div class="col-sm-3">
		      <input type="text" class="form-control small-control" id="givenName" value="${givenName}" name="givenName" maxlength="10"/>
		    </div>
		    <label for="familyName" class="col-sm-2 control-label"><i18n:message code="i18n.register.EnginshLastNameLabel"/></label>
		    <div class="col-sm-2">
		      <input type="text" class="form-control small-control" id="familyName" value="${familyName}" name="familyName" maxlength="10"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		     	
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="moduleSel" class="col-sm-2 control-label"><i18n:message code="i18n.register.GenderLabel"/></label>
		    <div class="col-sm-7">
		        <span class="radio-inline">
				  <input type="radio" name="gender" id="male" value="1" /> <i18n:message code="i18n.register.GenderMr"/>
				</span>
				<span class="radio-inline">
				  <input type="radio" name="gender" id="female" value="2"/> <i18n:message code="i18n.register.GenderMs"/>
				</span>
		    </div>
		    <div class="col-sm-3 form-desc"></div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="mobileNo" class="col-sm-2 control-label"><i18n:message code="i18n.register.MobileLabel"/></label>
		    <div class="col-sm-7">
		      	<input type="text" class="form-control small-control" id="mobileNo" value="${mobileNo}" name="mobileNo" maxlength="11"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		     	
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="phoneNo" class="col-sm-2 control-label"><i18n:message code="i18n.register.PhoneLabel"/></label>
		    <div class="col-sm-2">
		      	<input type="text" class="form-control small-control" id="areaCode" value="${areaCode}" name="areaCode" maxlength="4"/>
		    </div>
		    <div class="col-sm-5">
		      	<input type="text" class="form-control small-control" id="phoneNo" value="${phoneNo}" name="phoneNo"  maxlength="8"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    </div>
  		</div>
  		<div class="form-group clearfix register-form-btns">
		    <input type="submit" class="btn btn-warning btn-lg" style="margin-left:40%" value="<i18n:message code="i18n.register.registerBtn"/>" />
  		</div>
	</form>
</div>
<jsp:include page="../common/pageMasterEnd.jsp"></jsp:include>
</body>
</html>