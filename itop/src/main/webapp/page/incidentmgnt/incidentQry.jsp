<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		    	<input type="text" class="form-control" id="inciNo"/>
		  	</span>
		  	<span class="form-item">
		    	<label for="inciShortDesc">事件简述</label>
		    	<input type="text" class="form-control" id="inciShortDesc"/>
		  	</span>
		  	<span class="form-item">
		    	<label for="inciTypeSel">事件类别</label>
		    	<input class="easyui-combobox" style="width:60%"  name="inciTypeSel" id="inciTypeSel" data-options="
					url:'js/json/incidentType_data.json',
					method:'get',
					valueField:'paramCode',
					textField:'paramValue',
					panelHeight:'auto'"
				/>
		  	</span>
		  	<span class="form-item">
		    	<label for="prodSel">产品线</label>
		    	<input class="easyui-combobox" style="width:60%"  name="prodSel" id="prodSel" data-options="
					url:'js/json/prod_data.json',
					method:'get',
					valueField:'prodId',
					textField:'prodName',
					panelHeight:'auto'"
				/>
		  	</span>
	  	</div>
	  	<div>
			<span class="form-item">
		    	<label>影响度</label>
		    	<span class="checkbox-inline">
				  <input type="checkbox" id="effectLevel1" value="1"> 咨询
				</span>
				<span class="checkbox-inline">
				  <input type="checkbox" id="effectLevel2" value="2"> 一般
				</span>
				<span class="checkbox-inline">
				  <input type="checkbox" id="effectLevel3" value="3"> 严重
				</span>
				<span class="checkbox-inline">
				  <input type="checkbox" id="effectLevel4" value="4"> 重大
				</span>
		  	</span>
		  	<span class="form-item">
		    	<label for="inciNo">优先级</label>
		    	<span class="checkbox-inline">
				  <input type="checkbox" id="priLevel1" value="1"> 低
				</span>
				<span class="checkbox-inline">
				  <input type="checkbox" id="priLevel2" value="2"> 中
				</span>
				<span class="checkbox-inline">
				  <input type="checkbox" id="priLevel3" value="3"> 高
				</span>
				<span class="checkbox-inline">
				  <input type="checkbox" id="priLevel4" value="4"> 紧急
				</span>
		  	</span>
		  	<span class="form-item">
		    	<label for="inciType">提出时间</label>
		    	<input class="easyui-datebox" style="width:28%" id="qryStartDate" data-options="sharedCalendar:'#cc'">
		    	至&nbsp;<input class="easyui-datebox" style="width:28%" id="qryEndDate" data-options="sharedCalendar:'#cc'">
		  	</span>
		  	<span class="form-item">
		    	<button type="button" class="btn btn-primary btn-sm">&nbsp;&nbsp;&nbsp;查询&nbsp;&nbsp;&nbsp;</button>
		    	<button type="button" class="btn btn-default btn-sm">&nbsp;&nbsp;&nbsp;重置&nbsp;&nbsp;&nbsp;</button>
		  	</span>
	  	</div>
	</div>
	<div class="inci-data">
		<div>
			<span style="width:7.5%;float:left">
				<button type="button" class="btn btn-danger" onclick="add()">+ 新建一条事件</button>
			</span>
			<ul class="nav nav-tabs" role="tablist" id="statusNav" >
			  <li role="presentation"><a href="#">全部(612)</a></li>
			  <li role="presentation"><a href="#">已关闭(129)</a></li>
			  <li role="presentation"><a href="#">已完成(421)</a></li>
			  <li role="presentation"><a href="#">已挂起(2)</a></li>
			  <li role="presentation"><a href="#">顾问处理中(6)</a></li>
			  <li role="presentation"><a href="#">客户处理中(12)</a></li>
			  <li role="presentation"><a href="#">待提交(8)</a></li>
			  <li role="presentation" class="active"><a href="#">待响应(8)</a></li>
			</ul>
		</div>
		<table style="width:99%;height:390px" id="incidentDataTable"></table>
		
		
		
		<table class="easyui-datagrid" style="width:99%;height:390px" id="incidentDataTable"
			data-options="singleSelect:true,collapsible:false,
				url:rootPath+'/incident/list?stateVal=2',
				method:'get',
				loadMsg:'数据加载中，请稍后……',
				remoteSort:true,
				multiSort:true,
				pagination:true
			">
			<thead>
				<tr>
					<th data-options="field:'incidentId',formatter:formatOperations" width="9%"></th>
					<th data-options="field:'incidentCode'" width="9%">事件序列号</th>
					<th data-options="field:'brief'" width="15%">事件简述</th>
					<th data-options="field:'prodName'" width="8%">产品线</th>
					<th data-options="field:'classCodeUser'" width="5%">类别</th>
					<th data-options="field:'affectVarUser'" width="5%">影响度</th>
					<th data-options="field:'priorityVar'" width="5%">优先级</th>
					<th data-options="field:'itStateCode'" width="5%">当前状态</th>
					<th data-options="field:'creator'" width="5%">提交人</th>
					<th data-options="field:'registTime',sortable:true" width="8%">提交时间</th>
					<th data-options="field:'scLoginName'" width="5%">处理人</th>
					<th data-options="field:'modifyDate',sortable:true" width="8%">最近更新时间</th>
					<th data-options="field:'finishTime'" width="8%">完成时间</th>
					<th data-options="field:'feedbackVal'" width="5%">满意度</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<div id="subPage" class="subPage"><iframe id="subPageIframe" frameborder="0" scrolling="auto" style="overflow-x:hidden" width="100%" height="100%" src=""></iframe></div>
<div id="cc" class="easyui-calendar"></div>
<jsp:include page="../common/pageMasterEnd.jsp"></jsp:include>
<div id="pageMask" class="pageMask"></div>
</body>
</html>