//事务提交封装参数的对象
var r = null;
//事件当前处理顾问
var scOpId = null;
//顾问选择表格
var consultantGrid = null;
//CustId
var gCustId = null;
//ProductId
var gProdId = null;
//ModuleId
var gModuleId = null;
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
		if(scOpId!=opId){
			$(".form-btns input:first").val(i18n.incident.view.LendHandBtn);
		}
		//依据事件状态，显示不同按钮
		switch(parseInt(inciCurStateCode)){
			//待提交，不可能进入查看界面，退出
			case 1:
				break;
			//待响应,除完成按钮外，干系顾问全部按钮可见
			case 2:
				//干系顾问是当前顾问
				if(scOpId==opId){
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
				if(scOpId==opId){
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
			//已关闭，所有按钮不可见和提交框均不可见
			case 9:
				$(".inci-trans-commit-form").hide();
				$("#transCommitBtn").hide();
				$("#openConsultantSelBtn").hide();
				$("#deliverCustCommitBtn").hide();
				$("#blockCommitBtn").hide();
				$("#finishCommitBtn").hide();
				break;
				//已归档，所有按钮不可见和提交框均不可见
			case 91:
				$(".inci-trans-commit-form").hide();
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
		$("#finishCommitBtn").hide();
		if(parseInt(inciCurStateCode)==9){
			$(".inci-trans-commit-form").hide();
			$("#transCommitBtn").hide();
		}
	}
}

//查询事件信息
function queryIncidentInfo(flag){
	$.ajax({
		type : 'get',
		url : rootPath + "/incident/query",
		cache: false,
		data : {incidentId:incidentId},
		dataType : 'json',
		success : function(msg) {
			//绑定标题数据
			$("#incidentCode").html(msg.incidentCode);
			$("#scLoginName").html(msg.scLoginName);
			$("#icObjectName").html(msg.icObjectName);
			$("#registTime").html(dateTimeFormatter(msg.registeTime));
			$("#itStateCode").html(msg.itStateVal+"("+msg.itPhase+")");
			//绑定事件详细信息数据
			$("#custName").html(msg.custName);
			$("#classCodeOp").html(msg.classVal);
			$("#prodName").html(msg.prodName);
			$("#moduleName").html(msg.moduleName);
			$("#affectCodeOp").html(msg.affectVal);
			$("#complexCode").html(msg.complexVal);
			$("#happenTime").html(dateTimeFormatter(msg.happenTime));
			$("#itPhase").html(msg.itPhase);
			$("#responseTime").html(msg.responseTime);
			$("#dealTime").html(msg.dealTime);
			$("#priorityValInfo").html(msg.priorityVal);
			$("#itSolution").html(msg.itSolution);
			//$("#responseDur2").html(msg.responseDur2+caculateLights(msg.responseNum));
			//$("#dealDur2").html(msg.dealDur2+caculateLights(msg.dealNum));
			$("#responseDur2").html(dateTimeFormatter(msg.reponseDur2));
			$("#dealDur2").html(dateTimeFormatter(msg.dealDur2));
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
			scOpId = msg.scOpId;
			gCustId = msg.ccCustId;
			gProdId = msg.scProductId;
			gModuleId = msg.scModuleId;
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
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.edit.QueryTicketInfoError);
		}
	});
}
//事务列表html模板  
var transListTpl = "<div class='trans-item'><div class='col-sm-1'><label>${order}</label></div><div class='col-sm-11'>";
transListTpl+="<div class='trans-item-header clearfix'>";
transListTpl+="<div><label>"+i18n.incident.view.TransTplStartMan+"：</label><span>${objectName}</span></div>";
transListTpl+="<div><label>"+i18n.incident.view.TransTplDealtime+"：</label><span>${createTime}</span></div>";
transListTpl+="<div><label>"+i18n.incident.view.TransTplType+"：</label><span>${transType}</span></div></div>";
transListTpl+="<div><div><label>"+i18n.incident.view.TransTplContent+"：</label><br/><span class='trans-content'>${contents}</span></div></div>";
transListTpl+="<div><div><label></label><br/><span>";
transListTpl+="{{each(i,attach) attachList}}<div><a href=\"javascript:attachDownLoad({{= attach.icAttachId}})\">{{= attach.attachName}}</a></div>";
transListTpl+="{{/each}}</span></div></div></div></div>";

//查询事务列表
function queryTransList(){
	$.ajax({
		type : 'get',
		url : rootPath + "/trans/list",
		cache: false,
		data : {incidentId:incidentId},
		dataType : 'json',
		success : function(msg) {
			$("#transList").empty();
			if(msg!=null){
				for(var i=0;i<msg.length;i++){
					msg[i].order = i18n.incident.view.TransOrderPrefix+(msg.length-i);
					msg[i].createTime = dateTimeFormatter(msg[i].createTime);
				}
				$.tmpl('transListTpl', msg).appendTo('#transList'); 
			}
		},
		error : function() {
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.view.QryTransListError);
		}
	});
}

//查询联系人信息
function queryContactorInfo(id){
	setTimeout(function(){
		$.ajax({
			type : 'get',
			url : rootPath + "/custmgnt/user/get",
			cache: false,
			data : {userId:id},
			dataType : 'json',
			success : function(msg) {
				$("#opName").html(msg.opName+"/"+msg.lastName+"."+msg.firstName);
				$("#loginCode").html(msg.loginCode);
				$("#mobileNo").html(msg.mobileNo);
				$("#officeTel").html(msg.officeTel);
			},
			error : function() {
				$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.view.QryContactInfoError);
			}
		});
	},500);
}
var moduleTreeReload = false;
//检查表单项，并封装表单内容
function validateFormAndWrapVar(func){
	var transDesc = $.trim($("#transDesc").val());
	if(transDesc==""){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.view.TransContentEmpty);
		return false;
	}
	//顾问如果没有填写复杂度字段说明没有审核更正过事件信息，弹出窗口，由顾问审核更新事件信息
	if(opId==scOpId&&!isCompleted){
		//初始化产品线、服务目录选择框
		$("#prodSel").combobox({
			url:rootPath+'/product/productList',
			editable:false,
			method:'get',
			valueField:'scProductId',
			textField:'prodName',
			editable:false,
			onSelect:function(data){
				$("#moduleSel").combotree('setValue','');
				moduleTreeReload = true;
				$("#moduleSel").combotree('reload',rootPath+'/product/moduleTree?productId='+data.scProductId);
			},
			panelHeight:'auto',
			onLoadSuccess:function(){
				$("#prodSel").combobox('setValue',gProdId);
			}
		});
		$("#moduleSel").combotree({
			editable:false,
			url:rootPath+'/product/moduleTree?productId='+gProdId,
			panelHeight:'auto',
			onLoadSuccess:function(){
				if(!moduleTreeReload){
					$("#moduleSel").combotree('setValue',gModuleId);
				}
			}
		});
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
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.view.TransCommitSuccess);
			resetCommitForm();
			queryIncidentInfo();
			//提交成功，再刷新一遍事务列表
			queryTransList();
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.CommitFailure+'：'+msg);
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
			loadMsg:i18n.loading.GridLoading,
			singleSelect:true,
			nowrap:true,
			fitColumns:true,
			url: rootPath + '/custmgnt/op/list',
			columns:[[
			    {field:'ck',checkbox: true},
			    {field:'opName',title:i18n.incident.view.TransferNameTitle,formatter:nameFormatter,width:80},
				{field:'loginCode',title:i18n.incident.view.TransferAccountTitle,width:100},
				{field:'mobileNo',title:i18n.incident.view.TransferMobileTitle,width:60},
				{field:'officeTel',title:i18n.incident.view.TransferPhoneTitle,width:70},
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
					 $(this).parent().find("div").filter(".datagrid-body").html("<div class='ml10 mt10'>"+i18n.loading.GirdDataEmpty+"</div>");
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
		$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.view.TransferSelectEmpty);
		return;
	}
	//设置转派的顾问参数
	var opInfo = rows[0];
	r.opId = opInfo.scOpId;
	r.opCode = opInfo.loginCode;
	r.opName = opInfo.opName;
	//判断是否转派给自己
	if(opId==opInfo.scOpId){
		$.messager.alert(i18n.dialog.AlertTitle,'请注意：事件转派不能转派给自己！');
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
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.view.TransferSuccess);
			resetCommitForm();
			queryIncidentInfo();
			queryTransList();
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.CommitFailure+'：'+msg);
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
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.view.TransCommitSuccess);
			resetCommitForm();
			queryIncidentInfo();
			//提交成功，再刷新一遍事务列表
			queryTransList();
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.CommitFailure+'：'+msg);
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
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.view.TransCommitSuccess);
			resetCommitForm();
			queryIncidentInfo();
			queryTransList();
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.CommitFailure+'：'+msg);
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
		$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.view.FinishCodeEmpty);
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
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.view.TransCommitSuccess);
			resetCommitForm();
			$('#finishWin').dialog('close');
			queryIncidentInfo();
			queryTransList();
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.CommitFailure+'：'+msg);
		}
	});
}

//顾问补全事件信息
function completeIncident(){
	var fv = {};
	fv.incidentId = incidentId;
	//产品线
	fv.productId = $("#prodSel").combobox('getValue');
	fv.productName = $("#prodSel").combobox('getText');
	if(!fv.productId){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.view.TransCompleteProductEmpty);
		return;
	}
	//服务目录
	fv.moduleId = $("#moduleSel").combobox('getValue');
	fv.moduleName = $("#moduleSel").combobox('getText');
	if(!fv.moduleId){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.view.TransCompleteModuleEmpty);
		return;
	}
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
		$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.view.TransCompletePriorityEmpty);
		return;
	}
	//复杂度
	var complexEl = $("input[name=complexVar]:checked");
	fv.complexCode = complexEl.val();
	fv.complexVal = complexEl.attr("text");
	if(!fv.complexCode){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.view.TransCompleteComplexEmpty);
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
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.CommitFailure+'：'+msg);
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
					$.messager.alert(i18n.dialog.AlertTitle,data.message);
				}
			},
			error: function (data, status, e){
				$('#uploadProgress').dialog('close');
				$.messager.alert(i18n.dialog.AlertTitle,i18n.upload.UploadFailure);
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
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.CommitFailure+'：'+msg);
		}
	});
}