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
		    	<label id="custSelLabel"><i18n:message code="i18n.incident.query.QryConditionAdviser"/></label>
		  		<input style="width:63%" id="adviserSel" />
		  	</span>
		  	<span class="form-item">
		    	<label><i18n:message code="i18n.incident.query.QryConditionRegiserTime"/></label>
		    	<input class="easyui-datebox" style="width:28%" id="qryStartDate" data-options="
		       		formatter: function(date){ return dateFormatter2(date);}
		       	">
		    	<span style="color:#999">—</span>&nbsp;
		    	<input class="easyui-datebox" style="width:28%" id="qryEndDate" data-options="
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
			</span>
			<ul class="nav nav-tabs" role="tablist" id="statusNav" >
			</ul>
		</div>
		<table  style="width:99%;height:390px" id="incidentDataTable"></table>
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
<div id="subPage" class="subPage"><iframe id="subPageIframe" frameborder="0" scrolling="auto" style="overflow-x:hidden" width="100%" height="100%" src=""></iframe></div>
<jsp:include page="../common/pageMasterEnd.jsp"></jsp:include>
<div id="pageMask" class="pageMask"></div>
</body>
</html>