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
	<title><i18n:message code="i18n.scmgnt.main.SloNav"/></title>
	<script type="text/javascript" src="<%=rootPath%>/page/scmgnt/js/sloGlobalSetting.js"></script>
</head>
<body style="background:#fff;padding:20px">
<div>
	<div style="border-bottom:solid 1px #ccc;margin:10px auto 10px 0px;width:99%;color:#666">
		<i18n:message code="i18n.scmgnt.sloinfo.SloTimeParamTitle"/>
	</div>
	<div>
		<form class="form-horizonta op-search slo-time" style="width:90%" role="form" >
			<div class="form-item clearfix mb5">
			    <input type="hidden" id="paramId" name="paramId"  value="" />
			    <label for="timeType" class="col-sm-2 control-label"><i18n:message code="i18n.scmgnt.sloinfo.TimeTypeLabel"/></label>
			    <div class="col-sm-10">
			     	<span class="radio-inline">
					  <input type="radio" name="timeType"  value="-1" onclick="hideTimeConfig()"/> 7*24
					</span>
					<span class="radio-inline">
					  <input type="radio" name="timeType"  value="1" onclick="showTimeConfig()"/> 5*8
					</span>
			    </div>
	  		</div>
	  		<div id="workTimeAmItem" class="form-item clearfix mb5">
			    <label for="workStartTime" class="col-sm-2 control-label"><i18n:message code="i18n.scmgnt.sloinfo.WorkTimeAmLabel"/></label>
			    <div class="col-sm-10">
			    	<input class="easyui-timespinner mr10" id="workStartTimeAm" style="width:15%" >
			    	<input class="easyui-timespinner" id="workEndTimeAm" style="width:15%" >
			    </div>
	  		</div>
	  		<div id="workTimePmItem" class="form-item clearfix mb5">
			    <label for="workEndTime" class="col-sm-2 control-label"><i18n:message code="i18n.scmgnt.sloinfo.WorkTimePmLabel"/></label>
			    <div class="col-sm-10">
			    	<input class="easyui-timespinner mr10" id="workStartTimePm" style="width:15%" >
			    	<input class="easyui-timespinner" id="workEndTimePm" style="width:15%" >
			    </div>
	  		</div>
	  		<div class="form-group clearfix register-form-btns mt20" style="margin-left:20%">
			    <input type="button" id="editBtn" class="btn btn-primary mr20" onclick="editTimeRule()" value="<i18n:message code="i18n.scmgnt.sloinfo.EditBtn"/>" />
			    <input type="button" id="backBtn" class="undis btn btn-primary btn-outline mr20" onclick="backFront()" value="<i18n:message code="i18n.scmgnt.sloinfo.BackBtn"/>" />
			    <input type="button" id="submitBtn" class="btn btn-primary undis" onclick="submitTimeRule()" value="<i18n:message code="i18n.scmgnt.sloinfo.SubmitBtn"/>" />
	  		</div>
		</form>
	</div>
</div>
<div>
	<div style="border-bottom:solid 1px #ccc;margin:10px auto 10px 0px;width:99%;color:#666">
		<i18n:message code="i18n.scmgnt.sloinfo.SloDefautRuleTitle"/>
	</div>
	<div>
		<table style="width:99%;height:400px"  id="sloDataTable"></table>
	</div>
</div>
</body>
</html>