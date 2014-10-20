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
		<div>
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
		    	<input class="easyui-combobox" style="width:60%"  name="classVar" id="classVar" data-options="
					url:rootPath+'/param/list?kindCode=IC_CLASS',
					method:'get',
					valueField:'paramCode',
					textField:'paramValue',
					panelHeight:'auto'"
				/>
		  	</span>
		  	<span class="form-item">
		    	<label for="prodSel">产品线</label>
		    	<input class="easyui-combobox" style="width:60%"  name="prodSel" id="prodSel" data-options="
					url:rootPath+'/product/productList',
					method:'get',
					valueField:'scProductId',
					textField:'prodName',
					panelHeight:'auto'"
			   />
		  	</span>
	  	</div>
	  	<div>
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
		    	<input class="easyui-datebox" style="width:28%" id="qryStartDate" data-options="sharedCalendar:'#cc'">
		    	至&nbsp;<input class="easyui-datebox" style="width:28%" id="qryEndDate" data-options="sharedCalendar:'#cc'">
		  	</span>
		  	<span class="form-item">
		    	<button type="button" class="btn btn-primary btn-sm" onclick="query()">&nbsp;&nbsp;&nbsp;查询&nbsp;&nbsp;&nbsp;</button>
		    	<button type="button" class="btn btn-default btn-sm" onclick="reset()">&nbsp;&nbsp;&nbsp;重置&nbsp;&nbsp;&nbsp;</button>
		  	</span>
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
		<table class="easyui-datagrid" style="width:99%;height:390px" id="incidentDataTable"
			data-options="singleSelect:true,collapsible:false,
				method:'get',
				loadMsg:'数据加载中，请稍后……',
				remoteSort:true,
				multiSort:true,
				pagination:true,
				showPageList:false
			">
			<thead>
				<tr>
					<th data-options="field:'icIncidentId',formatter:formatOperations,align:'center'" width="9%"></th>
					<th data-options="field:'incidentCode'" width="10%">事件序列号</th>
					<th data-options="field:'brief'" width="11%">事件简述</th>
					<th data-options="field:'prodName'" width="8%">产品线</th>
					<th data-options="field:'classValOp'" width="5%">类别</th>
					<th data-options="field:'affectValOp'" width="4%">影响度</th>
					<th data-options="field:'priorityVal'" width="4%">优先级</th>
					<th data-options="field:'itStateVal'" width="5%">当前状态</th>
					<th data-options="field:'plObjectName'" width="9%">提交人</th>
					<th data-options="field:'registeTime',sortable:true,formatter:dateFormatter" width="8%">提交时间</th>
					<th data-options="field:'icObjectName'" width="9%">处理人</th>
					<th data-options="field:'modifyDate',sortable:true,formatter:dateFormatter" width="8%">最近更新时间</th>
					<th data-options="field:'finishTime'" width="6%">完成时间</th>
					<th data-options="field:'feedbackVal',formatter:formatFeedback" width="5%">满意度</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<div id="feedbackWin" title="评价事件" style="width:300px;height:120px;">
	<div class="col-sm-12 feedback-options">
      	<span class="radio-inline">
		  <input type="radio" name="feedbackVar" value="1" checked="checked">十分满意
		</span>
		<span class="radio-inline">
		  <input type="radio" name="feedbackVar" value="2" >满意
		</span>
		<span class="radio-inline">
		  <input type="radio" name="feedbackVar" value="3">不满意
		</span>
    </div>
    <div class="clearfix">
		<button id="feedbackBtn" class="btn btn-warning" type="button" incidentId="" onclick="feedback()">提交</button>
  	</div>
</div>
<div id="subPage" class="subPage"><iframe id="subPageIframe" frameborder="0" scrolling="auto" style="overflow-x:hidden" width="100%" height="100%" src=""></iframe></div>
<div id="cc" class="easyui-calendar"></div>
<jsp:include page="../common/pageMasterEnd.jsp"></jsp:include>
<div id="pageMask" class="pageMask"></div>
</body>
</html>