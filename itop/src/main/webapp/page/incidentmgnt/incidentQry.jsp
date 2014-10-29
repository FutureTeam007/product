<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="../common/commonHead.jsp"%>
	<%@ include file="../common/easyuiHead.jsp"%>
	<script type="text/javascript" src="<%=rootPath%>/page/incidentmgnt/js/incidentQry.js"></script>
	<title>事件管理</title>
</head>
<body>
<jsp:include page="../common/pageMasterStart.jsp"></jsp:include>
<div>
	<div class="inci-search  clearfix">
		<div style="width:89%;" class="pull-left list-inline clearfix">
			<span class="form-item">
		    	<label for="inciNo">事件序列号</label>
		    	<input type="text" class="form-control" id="incidentCode"/>
		  	</span>
		  	<span class="form-item">
		    	<label for="inciShortDesc">事件简述</label>
		    	<input type="text" class="form-control" id="brief"/>
		  	</span>
		  	<span class="form-item">
		    	<label for="inciTypeSel">事件类别</label>
		    	<input class="easyui-combobox" style="width:70%"  name="classVar" id="classVar" data-options="
					url:rootPath+'/param/list?kindCode=IC_CLASS',
					method:'get',
					valueField:'paramCode',
					textField:'paramValue',
					editable:false,
					panelHeight:'auto'"
				/>
		  	</span>
		  	<span class="form-item">
		    	<label for="prodSel">产品线</label>
		    	<input class="easyui-combobox" style="width:70%"  name="prodSel" id="prodSel" data-options="
					url:rootPath+'/product/productList',
					method:'get',
					editable:false,
					valueField:'scProductId',
					textField:'prodName',
					panelHeight:'auto'"
			   />
		  	</span>
	  	</div>
	  	<div style="width:89%;" class="pull-left list-inline clearfix">
			<span class="form-item" id="affectQry">
		    	<label>影响度</label>
		    	<c:forEach var="affect" items="${affectP}" begin="0" step="1">
		    		<span class="checkbox-inline"><input type="checkbox" name="affectVar" value="${affect.paramCode}">${affect.paramValue}</span>
		    	</c:forEach>
		  	</span>
		  	<span class="form-item" id="priorityQry">
		    	<label>优先级</label>
		    	<c:forEach var="priority" items="${priorityP}" begin="0" step="1">
		    		<span class="checkbox-inline"><input type="checkbox" name="priorityVar" value="${priority.paramCode}">${priority.paramValue}</span>
		    	</c:forEach>
		  	</span>
		  	<span class="form-item">
		    	<label>提出时间</label>
		    	<input class="easyui-datebox" style="width:33%" id="qryStartDate" data-options="sharedCalendar:'#cc'">
		    	至&nbsp;<input class="easyui-datebox" style="width:33%" id="qryEndDate" data-options="sharedCalendar:'#cc'">
		  	</span>
		  	<span class="form-item">
		  		<label id="custSelLabel">客户</label>
		  		<input style="width:70%" id="custSel" />
		  	</span>
	  	</div>
	  	<div style="width:10%;" class="pull-right list-inline">
		    <button type="button" class="btn btn-primary btn-sm mr5"  onclick="query()">&nbsp;&nbsp;&nbsp;查询&nbsp;&nbsp;&nbsp;</button>
		    <button type="button" class="btn btn-default btn-sm"  onclick="reset()">&nbsp;&nbsp;&nbsp;重置&nbsp;&nbsp;&nbsp;</button>
	  	</div>
	</div>
	<div class="inci-data">
		<div>
			<span style="width:7.5%;float:left">
				<button type="button" class="btn btn-danger undis" id="addBtn" onclick="add()">+ 新建一条事件</button>
			</span>
			<ul class="nav nav-tabs" role="tablist" id="statusNav" >
			</ul>
		</div>
		<table  style="width:99%;height:390px" id="incidentDataTable"></table>
	</div>
</div>
<div id="feedbackWin" title="评价事件" style="width:300px;height:120px;">
	<div class="col-sm-12 feedback-options">
		<c:forEach var="feedback" items="${feedbackP}" begin="0" step="1">
		    <span class="radio-inline"><input type="radio" name="feedbackVar" value="${feedback.paramCode}" text="${feedback.paramValue}">${feedback.paramValue}</span>
		</c:forEach>
    </div>
    <div class="clearfix">
		<button id="feedbackBtn" class="btn btn-warning" type="button" onclick="feedback()">提交</button>
  	</div>
</div>
<div id="stockWin" title="标记事件进行归档" style="width:450px;height:120px;">
	<div class="col-sm-12 feedback-options">
		<c:forEach var="stock" items="${stockP}" begin="0" step="1">
		    <span class="checkbox-inline"><input type="checkbox" name="stockVar" value="${stock.paramCode}" text="${stock.paramValue}">${stock.paramValue}</span>
		</c:forEach>
    </div>
    <div class="clearfix" style="text-align:center">
		<button id="stockBtn" class="btn btn-warning" type="button" onclick="stockIncident()">提交</button>
  	</div>
</div>
<div id="subPage" class="subPage"><iframe id="subPageIframe" frameborder="0" scrolling="auto" style="overflow-x:hidden" width="100%" height="100%" src=""></iframe></div>
<div id="cc" class="easyui-calendar"></div>
<jsp:include page="../common/pageMasterEnd.jsp"></jsp:include>
<div id="pageMask" class="pageMask"></div>
</body>
</html>