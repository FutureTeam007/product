//事务提交封装参数的对象
var r = null;
//事件提出人类别,判断是否为顾问提交的问题
var incidentPlObjectType = null;
//事件处理人ID，判断是否为当前处理人，产生流程事务还是非流程事务
var icObjectId = null;
//顾问选择表格
var consultantGrid = null;
//CustId
var gCustId = null;
//ProductId
var gProdId = null;
//顾问补全信息时，弹出窗口，保存该变量，当提交补全信息成功后，直接执行该函数提交事务
var callBackFunc = null;
//是否已经补全
var isCompleted = false;
//当前事件状态
var inciCurStateCode = null;

$(function(){
	$.template( "transListTpl", transListTpl); 
	initSubPage();
	queryIncidentInfo();
	//延迟加载，加快页面渲染速度
	setTimeout(queryTransList,500);
});
//初始化弹出窗口
function initSubPage(){
	$('#completeWin').dialog({
		modal:true
	}).dialog('close');
	$('#consultantSelWin').dialog({
		modal:true
	}).dialog('close');
	$('#finishWin').dialog({
		modal:true
	}).dialog('close');
	//上传进度条窗口初始化
	$('#uploadProgress').dialog({
		modal:true
	}).dialog('close');
};

//清空提交表单
function resetCommitForm(){
	$("#transDesc").val("");
	$("#commitAttach").empty();
}

//渲染操作按钮
function renderOpBtns(){
	//如果当前用户是顾问
	if(opType=="OP"){
		//如果当前顾问不是干系顾问，则提交按钮变为搭把手
		if(icObjectId!=opId){
			$(".form-btns input:first").val("搭把手");
		}
		//依据事件状态，显示不同按钮
		switch(parseInt(inciCurStateCode)){
			//待提交，不可能进入查看界面，退出
			case 1:
				break;
			//待响应,除完成按钮外，干系顾问全部按钮可见
			case 2:
				//干系顾问是当前顾问
				if(icObjectId==opId){
					$("#openConsultantSelBtn").show();
					$("#deliverCustCommitBtn").show();
					$("#blockCommitBtn").show();
					$("#finishCommitBtn").hide();
				}else{
					$("#openConsultantSelBtn").hide();
					$("#deliverCustCommitBtn").hide();
					$("#blockCommitBtn").hide();
					$("#finishCommitBtn").hide();
				}
				break;
			//顾问处理中、挂起中，干系顾问全部按钮均可见
			case 3:case 5:
				//干系顾问是当前顾问
				if(icObjectId==opId){
					$("#openConsultantSelBtn").show();
					$("#deliverCustCommitBtn").show();
					$("#blockCommitBtn").show();
					$("#finishCommitBtn").show();
				}else{
					$("#openConsultantSelBtn").hide();
					$("#deliverCustCommitBtn").hide();
					$("#blockCommitBtn").hide();
					$("#finishCommitBtn").hide();
				}
				break;
			//客户处理中，顾问只可见提交按钮
			case 4:
				$("#openConsultantSelBtn").hide();
				$("#deliverCustCommitBtn").hide();
				$("#blockCommitBtn").hide();
				$("#finishCommitBtn").hide();
				break;
			//已完成，顾问只可见提交按钮
			case 8:
				$("#openConsultantSelBtn").hide();
				$("#deliverCustCommitBtn").hide();
				$("#blockCommitBtn").hide();
				$("#finishCommitBtn").hide();
				break;
			//已关闭，所有按钮不可见
			case 9:
				$("#transCommitBtn").hide();
				$("#openConsultantSelBtn").hide();
				$("#deliverCustCommitBtn").hide();
				$("#blockCommitBtn").hide();
				$("#finishCommitBtn").hide();
				break;
			default:
				break;
		}
	}
	//如果当前用户是普通用户
	else if(opType=="USER"){
		$("#openConsultantSelBtn").hide();
		$("#deliverCustCommitBtn").hide();
		$("#blockCommitBtn").hide();
		$("#finishCommitBtn").hide()();
		if(parseInt(inciCurStateCode)==9){
			$("#transCommitBtn").hide();
		}
	}
}



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
			$("#scLoginName").html(msg.icObjectName);
			$("#registTime").html(dateFormatter(msg.registeTime));
			$("#itStateCode").html(msg.itStateVal+"("+msg.itPhase+")");
			//绑定事件详细信息数据
			$("#custName").html(msg.custName);
			$("#classCodeOp").html(msg.classVal);
			$("#prodName").html(msg.prodName);
			$("#moduleName").html(msg.moduleName);
			$("#affectCodeOp").html(msg.affectVal);
			$("#complexCode").html(msg.complexVal);
			$("#happenTime").html(dateFormatter(msg.happenTime));
			$("#itPhase").html(msg.itPhase);
			$("#responseTime").html(msg.responseTime);
			$("#dealTime").html(msg.dealTime);
			$("#priorityValInfo").html(msg.priorityVal);
			$("#itSolution").html(msg.itSolution);
			//$("#responseDur2").html(msg.responseDur2+caculateLights(msg.responseNum));
			//$("#dealDur2").html(msg.dealDur2+caculateLights(msg.dealNum));
			$("#infoScLoginName").html(msg.icObjectName);
			$("#brief").html(msg.brief);
			$("#detail").html(msg.detail);
			$("#attachments").html(msg.attachList);
			//绑定附件
			var attachData = msg.attachList;
			if(attachData!=null){
				for(var i=0;i<attachData.length;i++){
					var attachInfo = '<div attachId='+attachData[i].icAttachId+'><a href="javascript:attachDownLoad('+attachData[i].icAttachId+')">'+attachData[i].attachName+'</a></div>';
					$("#attachments").prepend(attachInfo);
				}
			}
			incidentPlObjectId = msg.plObjectId;
			icObjectId = msg.icObjectId;
			gCustId = msg.ccCustId;
			gProdId = msg.scProductId;
			inciCurStateCode = msg.itStateCode;
			isCompleted = msg.complexVal?true:false;
			//显示操作按钮
			renderOpBtns();
			//查询联系人信息
			if(!flag){
				queryContactorInfo(msg.plObjectId);
			}
			//如果复杂度字段为空， 说明顾问还未补全事件信息，则初始化事件信息确认弹出窗口
			if(!msg.complexCode){
				//事件分类
				$("#inciTypeSel").combobox('setValue',msg.classCode);
				//影响度
				$("input[name=affectVar]").each(function(){
					if($(this).val()==msg.affectCode){
						$(this).attr("checked","checked");
					}
				});
			}
		},
		error : function() {
			$.messager.alert('提示','查询事件信息错误！');
		}
	});
}
//事务列表html模板
var transListTpl = "<div class='trans-item'><div class='col-sm-1'><label>${order}</label></div><div class='col-sm-11'>";
transListTpl+="<div class='trans-item-header clearfix'>";
transListTpl+="<div><label>发起人：</label><span>${objectName}</span></div>";
transListTpl+="<div><label>处理时间：</label><span>${createTime}</span></div>";
transListTpl+="<div><label>类型：</label><span>${transType}</span></div></div>";
transListTpl+="<div><div><label>事务说明：</label><br/><span class='trans-content'>${contents}</span></div></div>";
transListTpl+="<div><div><label></label><br/><span>";
transListTpl+="{{each(i,attach) attachList}}<div><a href=\"javascript:attachDownLoad({{= attach.icAttachId}})\">{{= attach.attachName}}</a></div>";
transListTpl+="{{/each}}</span></div></div></div></div>";

//查询事务列表
function queryTransList(){
	$.ajax({
		type : 'get',
		url : rootPath + "/trans/list",
		data : {incidentId:incidentId},
		dataType : 'json',
		success : function(msg) {
			$("#transList").empty();
			if(msg!=null){
				for(var i=0;i<msg.length;i++){
					msg[i].order = "事务"+(msg.length-i);
					msg[i].createTime = dateTimeFormatter(msg[i].createTime);
				}
				$.tmpl('transListTpl', msg).appendTo('#transList'); 
			}
		},
		error : function() {
			$.messager.alert('提示','查询事务信息错误！');
		}
	});
}

//查询联系人信息
function queryContactorInfo(id){
	setTimeout(function(){
		$.ajax({
			type : 'get',
			url : rootPath + "/custmgnt/user/get",
			data : {userId:id},
			dataType : 'json',
			success : function(msg) {
				$("#opName").html(msg.opName+"/"+msg.lastName+"."+msg.firstName);
				$("#loginCode").html(msg.loginCode);
				$("#mobileNo").html(msg.mobileNo);
				$("#officeTel").html(msg.officeTel);
			},
			error : function() {
				$.messager.alert('提示','查询联系人信息错误！');
			}
		});
	},500);
}
//检查表单项，并封装表单内容
function validateFormAndWrapVar(func){
	var transDesc = $.trim($("#transDesc").val());
	if(transDesc==""){
		$.messager.alert('提示','请填写事务内容！');
		return false;
	}
	//顾问如果没有填写复杂度字段说明没有审核更正过事件信息，弹出窗口，由顾问审核更新事件信息
	if(opId==icObjectId&&!isCompleted){
		$('#completeWin').dialog('open');
		callBackFunc = func;
		return false;
	}
	var cp = {};
	cp.incidentId = incidentId;
	cp.transDesc = transDesc;
	//判断附件
	var attachEl = $("#commitAttach").children();
	if(attachEl!=null&&attachEl.length>0){
		var attachIdArray = [];
		attachEl.each(function(){
			attachIdArray.push($(this).attr("attachId"));
		});
		cp.attachList = attachIdArray.join(",");
	}
	return cp;
}

//提交事务
function transCommit(){
	r = validateFormAndWrapVar(transCommit);
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
		dataType : 'text',
		success : function(msg) {
			$.messager.alert('提示','提交成功！');
			resetCommitForm();
			queryIncidentInfo();
			//提交成功，再刷新一遍事务列表
			queryTransList();
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert('提示','提交错误：'+msg);
		}
	});
}
//查询顾问列表
function queryConsultants(){
	var p = {};
	p.consultantName = encodeURIComponent($.trim($("#consultantNameTxt").val()));
	p.custId=gCustId;
	p.productId=gProdId;
	$("#consultantSelTable").datagrid('load',p);
}
//打开顾问选择窗口
function openConsultantSelWin(){
	r = validateFormAndWrapVar(openConsultantSelWin);
	if(!r){
		return;
	};
	if(consultantGrid==null){
		consultantGrid = $("#consultantSelTable").datagrid({
			idField: 'scOpId',
			height:240,
			remoteSort:false,
			loadMsg:"数据加载中，请稍侯……",
			singleSelect:true,
			nowrap:true,
			fitColumns:true,
			url: rootPath + '/custmgnt/op/list',
			columns:[[
			    {field:'ck',checkbox: true},
			    {field:'opName',title:'姓名',formatter:nameFormatter,width:110},
				{field:'loginCode',title:'账号',width:90},
				{field:'mobileNo',title:'手机号码',width:60},
				{field:'officeTel',title:'办公电话',width:50},
				{field:'firstName',hidden:true},
				{field:'lastName',hidden:true}
			]],
			queryParams:{
				custId: gCustId,
				productId:gProdId
			},
			onLoadSuccess:function(){
				var data = $(this).datagrid("getData");
				if(data.rows.length==0){
					 $(this).parent().find("div").filter(".datagrid-body").html("<div class='ml10 mt10'>暂无数据记录</div>");
				}
			},
			pagination:true,
			pageNumber:1,
			pageSize:10
		}); 
		var pagger = $('#consultantSelTable').datagrid('getPager');
		$(pagger).pagination({
			showPageList:false,
			beforePageText: '第',   
			afterPageText: '页 共 {pages} 页',   
			displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	}
	$('#consultantSelWin').dialog('open');
}

//转其他顾问处理
function deliverConstCommit(){
	//从表格上得到选中行的顾问ID
	var rows = $("#consultantSelTable").datagrid('getSelections');
	if(!rows||rows.length<1){
		$.messager.alert('提示','请选择一个转派的顾问！');
		return;
	}
	//设置转派的顾问参数
	var opInfo = rows[0];
	r.opId = opInfo.scOpId;
	r.opCode = opInfo.loginCode;
	r.opName = opInfo.opName;
	//判断是否转派给自己
	if(opId==opInfo.scOpId){
		$.messager.alert('提示','请注意：事件转派不能转派给自己！');
		return;
	}
	//转顾问操作
	r.xcode=1;
	$.ajax({
		type : 'post',
		url : rootPath + "/trans/commit",
		data : r,
		dataType : 'text',
		success : function(msg) {
			$('#consultantSelWin').dialog('close');
			$.messager.alert('提示','转派成功！');
			resetCommitForm();
			queryIncidentInfo();
			queryTransList();
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert('提示','转派出现错误：'+msg);
		}
	});
}

//转客户补充资料
function deliverCustCommit(){
	r = validateFormAndWrapVar(deliverCustCommit);
	if(!r){
		return;
	}
	//转客户补充资料
	r.xcode=2;
	$.ajax({
		type : 'post',
		url : rootPath + "/trans/commit",
		data : r,
		dataType : 'text',
		success : function(msg) {
			$.messager.alert('提示','提交成功！');
			resetCommitForm();
			queryIncidentInfo();
			//提交成功，再刷新一遍事务列表
			queryTransList();
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert('提示','提交错误：'+msg);
		}
	});
}

//挂起
function blockCommit(){
	r = validateFormAndWrapVar(blockCommit);
	if(!r){
		return;
	}
	//挂起操作
	r.xcode = 3;
	$.ajax({
		type : 'post',
		url : rootPath + "/trans/commit",
		data : r,
		dataType : 'text',
		success : function() {
			$.messager.alert('提示','提交成功！');
			resetCommitForm();
			queryIncidentInfo();
			queryTransList();
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert('提示','挂起错误：'+msg);
		}
	});
}
//打开完成窗口
function openFinishWin(){
	r = validateFormAndWrapVar();
	if(!r){
		return;
	};
	$('#finishWin').dialog('open');
}
//完成
function finishCommit(){
	r = validateFormAndWrapVar();
	if(!r){
		return;
	}
	var finishEl = $("input[name=finishVal]:checked");
	r.finishCode = finishEl.val();
	r.finishVal = finishEl.attr("text");
	if(!r.finishCode){
		$.messager.alert('提示','请选择一个事件完成结果！');
		return;
	}
	//完成操作
	r.xcode = 4;
	$.ajax({
		type : 'post',
		url : rootPath + "/trans/commit",
		data : r,
		dataType : 'text',
		success : function() {
			$.messager.alert('提示','提交成功！');
			resetCommitForm();
			$('#finishWin').dialog('close');
			queryIncidentInfo();
			queryTransList();
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert('提示','提交错误：'+msg);
		}
	});
}

//顾问补全事件信息
function completeIncident(){
	var fv = {};
	fv.incidentId = incidentId;
	//事件类别
	fv.classCode = $("#inciTypeSel").combobox('getValue');
	fv.classVal =  $("#inciTypeSel").combobox('getText');
	//影响度
	var affectEl = $("input[name=affectVar]:checked");
	fv.affectCode = affectEl.val();
	fv.affectVal = affectEl.attr("text");
	//优先级
	var priorityEl = $("input[name=priorityVar]:checked");
	fv.priorityCode = priorityEl.val();
	fv.priorityVal = priorityEl.attr("text");
	if(!fv.priorityCode){
		$.messager.alert('提示','请选择事件优先级');
		return;
	}
	//复杂度
	var complexEl = $("input[name=complexVar]:checked");
	fv.complexCode = complexEl.val();
	fv.complexVal = complexEl.attr("text");
	if(!fv.complexCode){
		$.messager.alert('提示','请选择事件复杂度');
		return;
	}
	$.ajax({
		type : 'post',
		url : rootPath + "/incident/complete",
		data : fv,
		dataType : 'text',
		success : function() {
			//提交成功，重新查询事件信息
			queryIncidentInfo(1);
			$('#completeWin').dialog('close');
			isCompleted = true;
			//补全后，继续提交之前拦截的提交请求
			if(callBackFunc){
				callBackFunc();
			}
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert('提示','提交错误：'+msg);
		}
	});
}


//计算红绿灯的html
function caculateLights(str){
	if(!str){
		return "";
	}
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



//格式化时间列
function dateFormatter(val){
	if(!val){
		return;
	}
	var date = new Date(val);
	//年
	var year = date.getFullYear();
	//月
	var month = date.getMonth();
	//日
	var day = date.getDate();
	return year+"-"+(month+1)+"-"+day;
}
//格式化时间列
function dateTimeFormatter(val){
	if(!val){
		return;
	}
	var date = new Date(val);
	//年
	var year = date.getFullYear();
	//月
	var month = date.getMonth();
	//日
	var day = date.getDate();
	//小时
	var hour = date.getHours();
	hour = hour<10?"0"+hour:hour;
	//分钟
	var minute = date.getMinutes();
	minute = minute<10?"0"+minute:minute;
	//秒
	var second = date.getSeconds();
	second = second<10?"0"+second:second;
	return year+"-"+(month+1)+"-"+day+" "+hour+":"+minute+":"+second;
}
//顾问姓名列格式化
function nameFormatter(val,row){
	return row.opName+"/"+row.firstName+"."+row.lastName;
}

//上传附件
function attachUpload(){
	 $('#uploadProgress').dialog('open');
	 $.ajaxFileUpload({
			url:rootPath+'/attach/upload', 
			secureuri:false,
			fileElementId:'uploadFile1',
			dataType: 'json',
			success: function (data, status){
				$('#uploadProgress').dialog('close');
				if(data.success){
					var attachInfo = '<div attachId='+data.attachId+'><a href="javascript:attachDownLoad('+data.attachId+')">'+data.filename+'</a><i class="fa fa-times" onclick="attachRemove(this,'+data.attachId+')"></i></div>';
					$("#commitAttach").prepend(attachInfo);
				}else{
					alert(data.message);
				}
			},
			error: function (data, status, e){
				$('#uploadProgress').dialog('close');
				$.messager.alert('提示','上传失败了，可能是网络原因或系统故障，请稍后再试');
			}
	  });
}

//下载附件
function attachDownLoad(id){
	window.open(rootPath+'/attach/download?attachId='+id);
}

//删除附件
function attachRemove(obj,id){
	$(obj).parent().remove();
	$.ajax({
		type : 'post',
		url : rootPath + "/attach/remove",
		data : {attachId:id},
		dataType : 'text',
		success : function() {},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert('提示','删除错误：'+msg);
		}
	});
}



