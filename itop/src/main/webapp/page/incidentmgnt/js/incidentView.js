//事务提交封装参数的对象
var r = null;
//事件提出人类别,判断是否为顾问提交的问题
var incidentPlObjectType = null;
//事件处理人ID，判断是否为当前处理人，产生流程事务还是非流程事务
var scOpId = null;

$(function(){
	initSubPage();
	queryIncidentInfo();
	//延迟加载，加快页面渲染速度
	setTimeout(queryTransList,500);
});

function initSubPage(){
	$('#completeWin').dialog({
		modal:true
	});
};

//查询事件信息
function queryIncidentInfo(flag){
	$.ajax({
		type : 'get',
		url : rootPath + "/incident/query",
		data : {incidentId:incidentId},
		dataType : 'json',
		success : function(msg) {
			//绑定标题数据
			$("#incidentCode").html(msg.incidentCode);
			$("#scLoginName").html(msg.scLoginName);
			$("#registTime").html(msg.registTime);
			$("#itStateCode").html(msg.itPhase);
			//绑定事件详细信息数据
			$("#custName").html(msg.custName);
			$("#classCodeOp").html(msg.classCodeOp);
			$("#prodName").html(msg.prodName);
			$("#moduleName").html(msg.moduleName);
			$("#affectCodeOp").html(msg.affectCodeOp);
			$("#complexCode").html(msg.complexCode);
			$("#happenTime").html(msg.happenTime);
			$("#itPhase").html(msg.itPhase);
			$("#responseTime").html(msg.responseTime);
			$("#dealTime").html(msg.dealTime);
			$("#responseDur2").html(msg.responseDur2+caculateLights(msg.responseNum));
			$("#dealDur2").html(msg.dealDur2+caculateLights(msg.dealNum));
			$("#scLoginName").html(msg.scLoginName);
			$("#brief").html(msg.brief);
			$("#detail").html(msg.detail);
			$("#attachments").html(msg.attachList);
			incidentPlObjectId = msg.plObjectId;
			scOpId = msg.scOpId;
			if(!flag){
				queryContactorInfo(msg.plObjectId);
			}
		},
		error : function() {
			$.messager.alert('提示','查询事件信息失败！');
		}
	});
}

//查询事务列表
function queryTransList(){
	$.ajax({
		type : 'get',
		url : rootPath + "/trans/list",
		data : {incidentId:incidentId},
		dataType : 'json',
		success : function(msg) {
			$("#transList").empty();
			for(var i=0;msg!=null&&i<msg.length;i++){
				msg[i].order = "事务"+(msg.length-i);
				var attachs = msg[i].attachList;
				for(var j=0;attachs!=null&&j<attachs;j++){
					msg[i].attachs += "<a href=\"javascript:downloadAttach("+attachs[j].attachId+")\">"+attachs[j].attachName+"</a>";
				}
				$("#transListTpl").tmpl(msg).appendTo("#transList");
			}
		},
		error : function() {
			$.messager.alert('提示','查询事务信息失败！');
		}
	});
}

//查询联系人信息
function queryContactorInfo(id){
	setTimeout(function(){
		$.ajax({
			type : 'get',
			url : rootPath + "/custmgnt/contactorinfo/get",
			data : {plObjectId:id},
			dataType : 'json',
			success : function(msg) {
				$("#opName").html(msg.opName+"/"+msg.lastName+"."+msg.firstName);
				$("#loginCode").html(msg.loginCode);
				$("#mobileNo").html(msg.mobileNo);
				$("#officeTel").html(msg.officeTel);
			},
			error : function() {
				$.messager.alert('提示','查询联系人信息失败！');
			}
		});
	},500);
}
//检查表单项，并封装表单内容
function validateFormAndWrapVar(){
	var transDesc = encodeURIComponent(encodeURI($.trim($("#transDesc").html())));
	if(transDesc==""){
		$.messager.alert('提示','请填写事务内容！');
		return false;
	}
	//顾问如果没有填写复杂度字段说明没有审核更正过事件信息，弹出窗口，由顾问审核更新事件信息
	if(opId==scOpId&&$("#complexCode").html()==""){
		$('#completeWin').dialog('open');
		return false;
	}
	var cp = {};
	cp.incidentId = incidentId;
	cp.transDesc = transDesc;
	//判断附件
	var attachEl = $("#commitAttach").children();
	if(attachEl.has("a")){
		var attachList = [];
		attachEl.each(function(){
			var attach = {};
			attach.incidentId = incidentId;
			attach.attachName = $(this).attr("name");
			attach.attachPath = $(this).attr("path");
			attachList.push(attach);
		});
		cp.attachList = attachList;
	}
	return cp;
}

//提交事务
function transCommit(){
	r = validateFormAndWrapVar();
	if(!r){
		return;
	}
	//简单提交事务
	if(opType=="USER"){
		r.xcode=-1;
	}else if(opType=="OP"){
		r.xcode=0;
	}
	$.ajax({
		type : 'post',
		url : rootPath + "/trans/commit",
		data : r,
		dataType : 'json',
		success : function(msg) {
			//提交成功，再刷新一遍事务列表
			queryTransList();
		},
		error : function() {
			$.messager.alert('提示','提交事务失败！');
		}
	});
}
//打开顾问选择窗口
function openConsultantSelWin(){
	r = validateFormAndWrapVar();
	if(!r){
		return;
	}
	$('#consultantSelWin').dialog('open');
}

//转其他顾问处理
function deliverConstCommit(){
	//从表格上得到选中行的顾问ID
	var sel = $("#consultantSelTable").datagrid('');
	//TODO
	var rows = $('#dg').datagrid('getSelections');
	for(var i=0; i<rows.length; i++){
		var row = rows[i];
		ss.push('<span>'+row.itemid+":"+row.productid+":"+row.attr1+'</span>');
	}
	
	//转顾问操作
	r.xcode=1;
	$.ajax({
		type : 'post',
		url : rootPath + "/trans/commit",
		data : r,
		dataType : 'json',
		success : function(msg) {
			//提交成功，再刷新一遍事务列表
			queryTransList();
		},
		error : function() {
			$.messager.alert('提示','提交事务失败！');
		}
	});
}

//转客户补充资料
function deliverCustCommit(){
	r = validateFormAndWrapVar();
	if(!r){
		return;
	}
	//转客户补充资料
	r.xcode=2;
	$.ajax({
		type : 'post',
		url : rootPath + "/trans/commit",
		data : r,
		dataType : 'json',
		success : function(msg) {
			//提交成功，再刷新一遍事务列表
			queryTransList();
		},
		error : function() {
			$.messager.alert('提示','提交事务失败！');
		}
	});
}

//挂起
function blockCommit(){
	r = validateFormAndWrapVar();
	if(!r){
		return;
	}
	//挂起操作
	r.xcode = 3;
	$.ajax({
		type : 'post',
		url : rootPath + "/trans/commit",
		data : r,
		dataType : 'json',
		success : function(msg) {
			//提交成功，再刷新一遍事务列表
			queryTransList();
		},
		error : function() {
			$.messager.alert('提示','提交事务失败！');
		}
	});
}

//完成
function finishCommit(){
	r = validateFormAndWrapVar();
	if(!r){
		return;
	}
	//完成操作
	r.xcode = 4;
	$.ajax({
		type : 'post',
		url : rootPath + "/trans/commit",
		data : r,
		dataType : 'json',
		success : function(msg) {
			//提交成功，再刷新一遍事务列表
			queryTransList();
		},
		error : function() {
			$.messager.alert('提示','提交事务失败！');
		}
	});
}
//顾问补全事件信息
function completeIncident(){
	var fv = {};
	fv.incidentId = incidentId;
	fv.classCodeOp = $("#inciTypeSel").combobox('getText');
	fv.classValOp =  $("#inciTypeSel").combobox('getValue');
	//影响度
	$("input[name=affectVar]").each(function(){
		if($(this).get(0).checked){
			fv.affectCode = $(this).text();
			fv.affectVar = $(this).val();
		}
	});
	//优先级
	$("input[name=priorityVar]").each(function(){
		if($(this).get(0).checked){
			fv.priorityCode = $(this).text();
			fv.priorityVal = $(this).val();
		}
	});
	//复杂度
	$("input[name=complexVar]").each(function(){
		if($(this).get(0).checked){
			fv.complexCode = $(this).text();
			fv.complexVal = $(this).val();
		}
	});
	
	$.ajax({
		type : 'post',
		url : rootPath + "/incident/complete",
		data : fv,
		dataType : 'json',
		success : function(msg) {
			//提交成功，重新查询事件信息
			queryIncidentInfo(1);
		},
		error : function() {
			$.messager.alert('Error','更新事件信息失败！');
		}
	});
}


//计算红绿灯的html
function caculateLights(str){
	var lightsHtml = "";
	var lights = str.split("/");
	var redLightNum = lights[0].split("=")[1];
	var yelloLightNum = lights[1].split("=")[1];
	var greenLightNum = lights[2].split("=")[1];
	for(var i=0;i<redLightNum;i++){
		lightsHtml+="<button class='btn btn-danger btn-circle' type='button'></button>";
	}
	for(var i=0;i<yelloLightNum;i++){
		lightsHtml+="<button class='btn btn-warning btn-circle' type='button'></button>";
	}
	for(var i=0;i<greenLightNum;i++){
		lightsHtml+="<button class='btn btn-success btn-circle' type='button'></button>";
	}
	return lightsHtml;
}

//事务列表html模板
var transListTpl = "<div class='trans-item'><div class='col-sm-1'>{order}</div><div class='col-sm-11'>";
transListTpl+="<div class='trans-item-header clearfix'>";
transListTpl+="<div><label>处理人：</label><span>{objectName}</span></div>";
transListTpl+="<div><label>处理时间：</label><span>{createTime}</span></div>";
transListTpl+="<div><label>类型：</label><span>{transType}</span></div></div>";
transListTpl+="<div><div><label>事务说明：</label><br/><span class='trans-content'>{contents}</span></div></div>";
transListTpl+="<div><div><label>附件：</label><br/><span>{attachs}</span></div></div></div></div>";
$.template( "transListTpl", transListTpl); 
