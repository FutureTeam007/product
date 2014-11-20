<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="i18n" uri="../../WEB-INF/i18n.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="../common/commonHead.jsp"%>
	<%@ include file="../common/easyuiHead.jsp"%>
	<script type="text/javascript" src="<%=rootPath%>/page/incidentmgnt/js/incidentQry.js"></script>
	<title><i18n:message code="i18n.incident.query.PageTitle"/></title>
</head>
<body>
<jsp:include page="../common/pageMasterStart.jsp"></jsp:include>
<div>
	<div class="inci-search  clearfix mb5">
		<div style="width:99%;" class="clearfix">
			<span class="form-item">
		    	<label for="inciNo"><i18n:message code="i18n.incident.query.QryConditionIncidentCode"/></label>
		    	<input type="text" class="form-control" style="width:63%" id="incidentCode"/>
		  	</span>
		  	<span class="form-item">
		    	<label for="inciShortDesc"><i18n:message code="i18n.incident.query.QryConditionBrief"/></label>
		    	<input type="text" class="form-control" style="width:63%"  id="brief"/>
		  	</span>
		  	<span class="form-item">
		    	<label for="inciTypeSel"><i18n:message code="i18n.incident.query.QryConditionClass"/></label>
		    	<input class="easyui-combobox" style="width:63%"  name="classVar" id="classVar" data-options="
					url:rootPath+'/param/list?kindCode=IC_CLASS',
					method:'get',
					valueField:'paramCode',
					textField:'paramValue',
					editable:false,
					panelHeight:'auto'"
				/>
		  	</span>
		  	<span class="form-item">
		    	<label for="prodSel"><i18n:message code="i18n.incident.query.QryConditionProduct"/></label>
		    	<input class="easyui-combobox" style="width:63%"  name="prodSel" id="prodSel" data-options="
					url:rootPath+'/product/productList',
					method:'get',
					editable:false,
					valueField:'scProductId',
					textField:'prodName',
					panelHeight:300"
			   />
		  	</span>
	  	</div>
	  	<div style="width:99%;" class="clearfix">
	  		<span class="form-item">
		  		<label id="custSelLabel"><i18n:message code="i18n.incident.query.QryConditionCust"/></label>
		  		<input style="width:63%" id="custSel" />
		  	</span>
		  	<span class="form-item">
		    	<label id="registeManLabel"><i18n:message code="i18n.incident.query.QryConditionRegistrant"/></label>
		  		<input style="width:63%" id="registeMan" />
		  	</span>
	  		<span class="form-item" id="affectQry">
		    	<label id="adviserSelLabel"><i18n:message code="i18n.incident.query.QryConditionAdviser"/></label>
		  		<input style="width:63%" id="adviserSel" />
		  	</span>
		  	<span class="form-item">
		    	<label><i18n:message code="i18n.incident.query.QryConditionRegiserTime"/></label>
		    	<input class="easyui-datebox" style="width:28%" id="qryStartDate" data-options="editable:false,
		       		formatter: function(date){ return dateFormatter2(date);}
		       	">
		    	<span style="color:#999">—</span>&nbsp;
		    	<input class="easyui-datebox" style="width:28%" id="qryEndDate" data-options="editable:false,
		       		formatter: function(date){ return dateFormatter2(date);}
		       	">
		  	</span>
	  	</div>
	  	<div style="width:99%;" class="clearfix">
	  		<span class="form-item" id="priorityQry">
		    	<label><i18n:message code="i18n.incident.query.QryConditionPriority"/></label>
		    	<c:forEach var="priority" items="${priorityP}" begin="0" step="1">
		    		<span class="checkbox-inline"><input type="checkbox" name="priorityVar" value="${priority.paramCode}">${priority.paramValue}</span>
		    	</c:forEach>
		  	</span>
	  		<span class="form-item" id="affectQry" class="mb5" style="margin-bottom:5px">
		    	<label><i18n:message code="i18n.incident.query.QryConditionAffect"/></label>
		    	<c:forEach var="affect" items="${affectP}" begin="0" step="1">
		    		<span class="checkbox-inline"><input type="checkbox" name="affectVar" value="${affect.paramCode}">${affect.paramValue}</span>
		    	</c:forEach>
		  	</span>
		  	<span class="form-item pull-right mb5" style="width:50%;margin-bottom:5px">
		    	<button type="button" class="btn btn-default btn-sm mr20 pull-right"  onclick="reset()">&nbsp;<i18n:message code="i18n.incident.query.QryBtnReset"/>&nbsp;</button>
		    	<button type="button" id="qryOpMineBtn" class="btn btn-primary btn-outline btn-sm mr20 pull-right"  onclick="queryOpMine()">&nbsp;<i18n:message code="i18n.incident.query.QryBtnOp"/>&nbsp;</button>
		    	<button type="button" id="qryUserMineBtn" class="btn btn-primary btn-outline btn-sm mr20 pull-right"  onclick="queryUserMine()">&nbsp;<i18n:message code="i18n.incident.query.QryBtnUser"/>&nbsp;</button>
		  		<button type="button" class="btn btn-primary btn-sm mr15 pull-right"  onclick="query()">&nbsp;&nbsp;&nbsp;&nbsp;<i18n:message code="i18n.incident.query.QryBtnMain"/>&nbsp;&nbsp;&nbsp;&nbsp;</button>
	  		</span>
	  	</div>
	</div>
	<div class="inci-data">
		<div>
			<span style="width:7.5%;float:left">
				<button type="button" class="btn btn-danger undis" id="addBtn" onclick="add()">+ <i18n:message code="i18n.incident.mgnt.AddNewBtn"/></button>
				<button type="button" class="btn btn-primary btn-outline btn-sm" id="exportBtn" onclick="openExportReportWin()"><i18n:message code="i18n.incident.mgnt.ExportBtn"/></button>
			</span>
			<ul class="nav nav-tabs" role="tablist" id="statusNav" >
			</ul>
		</div>
		<table  style="width:99%;height:340px" id="incidentDataTable"></table>
	</div>
</div>
<div id="feedbackWin" title="<i18n:message code="i18n.incident.mgnt.FeedbackWinTitle"/>">
	<div class="col-sm-12 feedback-options">
		<c:forEach var="feedback" items="${feedbackP}" begin="0" step="1">
		    <span class="radio-inline"><input type="radio" name="feedbackVar" value="${feedback.paramCode}" text="${feedback.paramValue}">${feedback.paramValue}</span>
		</c:forEach>
    </div>
    <div class="clearfix">
		<button id="feedbackBtn" class="btn btn-warning" type="button" onclick="feedback()"><i18n:message code="i18n.incident.mgnt.FeedbackSubmitBtn"/></button>
  	</div>
</div>
<div id="stockWin" title="<i18n:message code="i18n.incident.mgnt.StockWinTitle"/>" style="width:450px;height:120px;">
	<div class="col-sm-12 feedback-options">
		<c:forEach var="stock" items="${stockP}" begin="0" step="1">
		    <span class="checkbox-inline"><input type="checkbox" name="stockVar" value="${stock.paramCode}" text="${stock.paramValue}">${stock.paramValue}</span>
		</c:forEach>
    </div>
    <div class="clearfix" style="text-align:center">
		<button id="stockBtn" class="btn btn-warning" type="button" onclick="stockIncident()"><i18n:message code="i18n.incident.mgnt.StockSubmitBtn"/></button>
  	</div>
</div>
<div id="exportWin" title="<i18n:message code="i18n.incident.mgnt.ExportWinTitle" />" style="width:580px;height:450px">
	<!-- 客户 -->
	<div class="form-group clearfix p10">
   	 	<label id="custSelLabel" class="col-sm-3 control-label"><i18n:message code="i18n.incident.query.QryConditionCust"/></label>
    	<div class="col-sm-9">
		      <input style="width:100%" id="exportCustSel" />
		</div>
	</div>
	<!-- 登记人范围 -->
	<div class="form-group clearfix p10" >
   	 	<label id="registeManLabel" class="col-sm-3 control-label"><i18n:message code="i18n.incident.query.QryConditionRegistrant"/></label>
   	 	<div class="col-sm-9">
    		<input style="width:100%" id="exportRegisteMan" />
    	</div>
	</div>
	<!-- 责任顾问范围 -->
	<div class="form-group clearfix p10">
	    <label id="exportAdviserSelLabel" class="col-sm-3 control-label"><i18n:message code="i18n.incident.query.QryConditionAdviser"/></label>
	    <div class="col-sm-9">
	    	<input style="width:100%" id="exportAdviserSel" />
	    </div>
	</div>
	<!-- 日期范围 -->
	<div class="form-group clearfix  p10">
		<label class="col-sm-3 control-label"><i18n:message code="i18n.incident.query.QryConditionRegiserTime"/></label>
	    <div class="col-sm-9">
	      	<input class="easyui-datebox" style="width:46%" id="exportStartDate" data-options="editable:false,
		       		formatter: function(date){ return dateFormatter2(date);}" />
		    <span style="color:#999">—</span>&nbsp;
		    <input class="easyui-datebox" style="width:46%" id="exportEndDate" data-options="editable:false,
		       		formatter: function(date){ return dateFormatter2(date);}" />
	    </div>
	</div>
	<!-- 状态范围 -->
	<div class="form-group clearfix p10">
   	 	<label class="col-sm-3 control-label"><i18n:message code="i18n.incident.mgnt.ExportStatusLabel" /></label>
    	<div class="col-sm-9" style="padding-left:10px;">
		    <c:forEach var="ticketStatus" items="${statusP}" begin="0" step="1">
		    	<span class="checkbox-inline exp-status"><input type="checkbox" name="exportStatus" value="${ticketStatus.paramCode}" text="${ticketStatus.paramValue}">${ticketStatus.paramValue}</span>
			</c:forEach>
		</div>
	</div>
    <div class="clearfix p10" style="text-align:center">
		<button id="exportSubBtn" class="btn btn-primary" type="button" onclick="exportReport()"><i18n:message code="i18n.incident.mgnt.ExportSubBtn" /></button>
  	</div>
  	<iframe id="exportIframe" src="" width="0" height="0"></iframe>
</div>
<div id="subPage" class="subPage"><iframe id="subPageIframe" frameborder="0" scrolling="auto" style="overflow-x:hidden" width="100%" height="100%" src=""></iframe></div>
<jsp:include page="../common/pageMasterEnd.jsp"></jsp:include>
<div id="pageMask" class="pageMask"></div>
<div id="detailMask" class="detailMask">
	<div style="font-size:14px;font-weight:bold;"><i18n:message code="i18n.incident.edit.DetailLabel" />:</div>
	<div id="detailMaskContent"></div>
</div>
</body>
</html>