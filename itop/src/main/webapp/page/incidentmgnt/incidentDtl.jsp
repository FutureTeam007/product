<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="i18n" uri="../../WEB-INF/i18n.tld"%>
<%
	String id = request.getParameter("id");
	String openFlag = request.getParameter("openFlag");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><i18n:message code="i18n.incident.edit.PageTitle" /></title>
	<%@ include file="../common/commonHead.jsp"%>
	<%@ include file="../common/easyuiHead.jsp"%>
	<script type="text/javascript">
		var incidentId = "<%=id%>";
		var openFlag = "<%=openFlag%>";
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/res/easyui/jquery.ajaxfileupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/incidentmgnt/js/incidentDtl.js"></script>
</head>
<body style="background:#fff;overflow-x:hidden;">
	<div>
		<button type='button' class='btn btn-link top-close-link' onclick='parent.hideSubPage()'>&lt;&lt;<i18n:message code="i18n.nav.CloseWin" /></button>
	</div>
	<div class="inci-dtl-title">
		<h4>
			<% if("a".equals(openFlag)) {%>
				<i18n:message code="i18n.incident.edit.SubPageTitleCreate" />
			<% }else{%>
				<i18n:message code="i18n.incident.edit.SubPageTitleEdit" />
			<% }%>
		</h4>
	</div>
	<form class="form-horizonta inci-info-form" role="form">
		<div class="form-group clearfix">
		    <label for="companySel" class="col-sm-2 control-label"><i18n:message code="i18n.incident.edit.CompanyLabel" /></label>
		    <div class="col-sm-7">
		      <input style="width:100%"  name="companySel" id="companySel" />
		    </div>
		    <div class="col-sm-3 form-desc">
		    </div>
  		</div>
		<div class="form-group clearfix">
		    <label for="prodSel" class="col-sm-2 control-label"><i18n:message code="i18n.incident.edit.ProductLabel" /></label>
		    <div class="col-sm-7">
		      <input style="width:100%"  name="prodSel" id="prodSel" />
		    </div>
		    <div class="col-sm-3 form-desc">
		     	<i18n:message code="i18n.incident.edit.ProductLabelTip" />
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="moduleSel" class="col-sm-2 control-label"><i18n:message code="i18n.incident.edit.ModuleLabel" /></label>
		    <div class="col-sm-7">
		      <input style="width:100%"  name="moduleSel" id="moduleSel" />
		    </div>
		    <div class="col-sm-3 form-desc">
		     	<i18n:message code="i18n.incident.edit.ModuleLabelTip" />
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="moduleSel" class="col-sm-2 control-label"><i18n:message code="i18n.incident.edit.AffectLabel" /></label>
		    <div class="col-sm-7">
		      	<c:forEach var="affect" items="${affectP}" begin="0" step="1">
		    		<span class="radio-inline"><input type="radio" name="affectVar" value="${affect.paramCode}" text="${affect.paramValue}">${affect.paramValue}</span>
		    	</c:forEach>
		    </div>
		    <div class="col-sm-3 form-desc">
		     	<i18n:message code="i18n.incident.edit.AffectLabelTip" />
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="inciTypeSel" class="col-sm-2 control-label"><i18n:message code="i18n.incident.edit.ClassLabel" /></label>
		    <div class="col-sm-7">
		    	<input class="easyui-combobox" style="width:100%"  name="classVar" id="inciTypeSel" data-options="
					url:rootPath+'/param/list?kindCode=IC_CLASS',
					method:'get',
					editable:false,
					valueField:'paramCode',
					textField:'paramValue',
					panelHeight:'auto'"
				/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="brief" class="col-sm-2 control-label"><i18n:message code="i18n.incident.edit.BriefLabel" /></label>
		    <div class="col-sm-7">
		       <input type="text" class="form-control small-control" id="brief" maxlength="40"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    	<i18n:message code="i18n.incident.edit.BriefLabelTip" />
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="happenTime" class="col-sm-2 control-label"><i18n:message code="i18n.incident.edit.HappenTimeLabel" /></label>
		    <div class="col-sm-7">
		       <input class="easyui-datetimebox" style="width:100%" id="happenTime" data-options="editable:false,
		       		formatter: function(date){ return dateTimeFormatter(date);}
		       	">
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="detail" class="col-sm-2 control-label"><i18n:message code="i18n.incident.edit.DetailLabel" /></label>
		    <div class="col-sm-7">
		       <textarea class="form-control small-control-large" rows="5" id="detail"></textarea>
		    </div>
		    <div class="col-sm-3 form-desc">
		    	<i18n:message code="i18n.incident.edit.DetailLabelTip" />
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="ccList" class="col-sm-2 control-label"><i18n:message code="i18n.incident.edit.CcLabel" /></label>
		    <div class="col-sm-7">
		       <input type="text" class="form-control small-control" id="ccList" maxlength="100"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    	<i18n:message code="i18n.incident.edit.CcLabelTip" />
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="happenDate" class="col-sm-2 control-label"><i18n:message code="i18n.incident.edit.AttachLabel" /></label>
		    <div class="col-sm-10 attachList" id="attachList">
		       <button type="button" class="btn btn-primary btn-outline btn-sm" ><i18n:message code="i18n.incident.edit.AttachBrowseBtn" /></button>
		       <input type="file"  id="uploadFile1" class="upload_control" name="uploadFile"  title="<i18n:message code="i18n.incident.view.SelectFileLabel" />" onchange="attachUpload()"/>
		    </div>
  		</div>
  		<div class="form-group clearfix inci-form-btns">
		    <button class="btn btn-outline btn-warning btn-lg" style="margin-left:280px;margin-right:20px" type="button" onclick="save()"><i18n:message code="i18n.incident.edit.SaveBtn" /></button>
		    <button class="btn btn-warning btn-lg" type="button" onclick="saveAndCommit()"><i18n:message code="i18n.incident.edit.SaveAndCommitBtn" /></button>
  		</div>
	</form>
	<div id="uploadProgress" title="" style="width:300px;height:100px;border-top-width:1px">
		<div style="text-align:center;margin-top:10px;"><i18n:message code="i18n.upload.WaitMessage" /></div>
		<div class="progress" style="width:90%;margin-left:15px;margin-top:10px;">
		  <div class="progress-bar progress-bar-striped active"  role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
		    <span class="sr-only"><i18n:message code="i18n.upload.WaitMessage" /></span>
		  </div>
		</div>
	</div>
</body>
</html>