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
		var msg = "${msg}";
		var newCustId = "${newCustId}";
		if(msg){
			parent.showMsg(msg);
			if(newCustId){
				parent.refreshLeftTree(newCustId);
			}else{
				parent.refreshLeftTree(custId);
			}
		}
	</script>
	<script type="text/javascript" src="<%=rootPath%>/page/custmgnt/js/custInfoMaintence.js"></script>
</head>
<body style="background:#fff;">
<div class="register-wrapper">
	<form class="form-horizonta register-form" onsubmit="return validateForm();" style="width:90%" role="form" action="<%=rootPath%>/custmgnt/custinfo/modify" method="post">
		<div class="form-group clearfix">
		    <label for="custCode" class="col-sm-3 control-label"><i18n:message code="i18n.custmgnt.custinfo.CustCodeLabel"/></label>
		    <div class="col-sm-9">
		    	<input type="hidden"  id="editType" name="editType" value="1" />
		    	<input type="hidden"  id="custId" name="custId" />
		      	<input type="text" class="form-control small-control" style="width:99%" id="custCode" name="custCode"  maxlength="30"/>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="custName" class="col-sm-3 control-label"><i18n:message code="i18n.custmgnt.custinfo.CustNameLabel"/></label>
		    <div class="col-sm-9">
		      	<input type="text" class="form-control small-control" style="width:99%" id="custName"  name="custName"  maxlength="30"/>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="custShortName" class="col-sm-3 control-label"><i18n:message code="i18n.custmgnt.custinfo.CustShortNameLabel"/></label>
		    <div class="col-sm-9">
		      	<input type="text" class="form-control small-control" style="width:99%" id="custShortName"  name="custShortName"  maxlength="30"/>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="custEnName" class="col-sm-3 control-label"><i18n:message code="i18n.custmgnt.custinfo.CustEnNameLabel"/></label>
		    <div class="col-sm-9">
		      	<input type="text" class="form-control small-control" style="width:99%" id="custEnName"  name="custEnName"  maxlength="30"/>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="custSel" class="col-sm-3 control-label"><i18n:message code="i18n.custmgnt.custinfo.SupCustLabel"/></label>
		    <div class="col-sm-9">
		      	<input style="width:100%" id="custSel" name="supCustId" />
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="domainName" class="col-sm-3 control-label"><i18n:message code="i18n.custmgnt.custinfo.DomainNameLabel"/></label>
		    <div class="col-sm-9">
		      	<input type="text" class="form-control small-control" style="width:99%" id="domainName"  name="domainName"  maxlength="30"/>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="notes" class="col-sm-3 control-label"><i18n:message code="i18n.custmgnt.custinfo.RemarkLabel"/></label>
		    <div class="col-sm-9">
		      	<textarea class="form-control small-control-large" style="width:99%" rows="5" id="notes" name="notes"></textarea>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="status" class="col-sm-3 control-label"><i18n:message code="i18n.custmgnt.custinfo.StatusLabel"/></label>
		    <div class="col-sm-9">
		     	<span class="radio-inline">
				  <input type="radio" name="status"  value="1" checked="checked" /><i18n:message code="i18n.custmgnt.custinfo.StatusNormal"/>
				</span>
				<span class="radio-inline">
				  <input type="radio" name="status"  value="2" /><i18n:message code="i18n.custmgnt.custinfo.StatusLock"/>
				</span>
		    </div>
  		</div>
  		<div class="form-group clearfix register-form-btns text-center">
		    <input type="button" id="addBtn" class="btn btn-primary btn-outline mr20" onclick="addNewCust()" value="<i18n:message code="i18n.custmgnt.custinfo.AddNewBtn"/>" />
		    <input type="button" id="editBtn" class="btn btn-primary btn-outline mr20" onclick="editCust()" value="<i18n:message code="i18n.custmgnt.custinfo.EditBtn"/>" />
		    <input type="button" id="deleteBtn" class="btn btn-primary mr20" onclick="removeCust()" value="<i18n:message code="i18n.custmgnt.custinfo.DeleteBtn"/>" />
		    <input type="button" id="backBtn" class="undis btn btn-primary btn-outline mr20" onclick="backFront()" value="<i18n:message code="i18n.custmgnt.custinfo.BackBtn"/>" />
		    <input type="submit" id="submitBtn" class="btn btn-primary undis" value="<i18n:message code="i18n.custmgnt.custinfo.SubmitBtn"/>" />
  		</div>
	</form>
</div>
</body>
</html>