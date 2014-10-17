
//form表单值
var fv = {};

$(function(){
	//如果是编辑状态
	if(openFlag=='m'){
		//查询事件信息绑定表单
		queryIncident();
	}
});

//查看事件
function queryIncident(){
	$.ajax({
		type : 'get',
		url : rootPath + "/incident/query",
		data : {
			incidentId :incidentId
		},
		dataType : 'json',
		success : function(msg) {
			//绑定表单
			$("#prodSel").combobox().setValue(msg.scProductId);
			$("#moduleSel").combobox().setValue(msg.scModuleId);
			//影响度
			$("input[name=affectVar]").each(function(){
				if($(this).val()==msg.affectValOp){
					$(this).attr("checked","checked");
				}
			});
			$("#inciTypeSel").combobox().setValue(msg.classValOp);
			$("#brief").val(msg.brief);
			$("#happenTime").val(msg.happenTime);
			$("#detail").val(msg.detail);
			$("#ccList").val(msg.ccList);
			var attachData = msg.attachList;
			if(attachData!=null){
				for(var i=0;i<attachData.length;i++){
					$("#attach").prepend("<div><a href='#'>"+attachData[i].attachPath+"</a><i class='fa fa-times'></i></div>");
				}
			}
		},
		error : function() {
			$.messager.alert('Error','查询事件信息失败！');
		}
	});
	
}
//整理表单项
function getFromVars(){
	fv.productId = $("#prodSel").combobox('getValue');
	fv.productName = $("#prodSel").combobox('getText');
	fv.moduleId = $("#moduleSel").combobox('getValue');
	fv.moduleName = $("#moduleSel").combobox('getText');
	//影响度
	$("input[name=affectVar]").each(function(){
		if($(this).get(0).checked){
			fv.affectCode = $(this).text();
			fv.affectVar = $(this).val();
		}
	});
	//事件分类
	fv.classCode = $("#inciTypeSel").combobox('getText');
	fv.classVar = $("#inciTypeSel").combobox('getValue');
	fv.brief = encodeURIComponent(encodeURI($.trim($("#brief").val())));
	fv.happenTime =$("#happenTime").datebox('getValue');
	fv.detail = encodeURIComponent(encodeURI($.trim($("#detail").val())));
	fv.ccList = $.trim($("#ccList").val());
	//附件列表
	fv.attachList = "[{\"attachPath\":\"upload/123.txt\"}]";
}

//检查form表单数据正确性
function validateForm(){
	if(fv.productId==-1||fv.productId==""){
		$.messager.alert('提示','请选择产品线！');
		return false;
	}
	if(fv.moduleId==-1||fv.moduleId==""){
		$.messager.alert('提示','请选择服务目录！');
		return false;
	}
	if(fv.affectVar==null){
		$.messager.alert('提示','请选择影响度！');
		return false;
	}
	if(fv.classVar==-1||fv.classVar==""){
		$.messager.alert('提示','请选择事件分类！');
		return false;
	}
	if(fv.brief==""){
		$.messager.alert('提示','请填写事件简述！');
		return false;
	}
	if(fv.happenTime==""){
		$.messager.alert('提示','请填写事件发生时间！');
		return false;
	}
	if(fv.detail==""){
		$.messager.alert('提示','请填写事件详细说明！');
		return false;
	}
	if(fv.ccList!=""){
		var regExp = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		var mails = fv.ccList.split(",");
		for(var i=0;i<mails.length;i++){
			if(!regExp.test(mails[i])){
				$.messager.alert('提示','请填写正确的邮箱地址，多个用逗号分隔！');
				return false;
			}
		}
	}
}
//保存按钮
function save(){
	if(openFlag='a'){
		addIncident();
	}else{
		modifyIncident();
	}
}
//保存提交按钮
function saveAndCommit(){
	if(openFlag='a'){
		addIncidentAutoCommit();
	}else{
		modifyIncidentAutoCommit();
	}
}

//新增事件
function addIncident(){
	getFromVars();
	if(!validateForm()){
		return;
	}
	$.ajax({
		type : 'post',
		url : rootPath + "/incident/add",
		data : fv,
		dataType : 'json',
		success : function(msg) {
			parent.hideSubPage();
			parent.reloadData();
		},
		error : function() {
			$.messager.alert('提示','保存事件失败！');
		}
	});
}
//新增事件直接提交
function addIncidentAutoCommit(){
	getFromVars();
	if(!validateForm()){
		return;
	}
	$.ajax({
		type : 'post',
		url : rootPath + "/incident/addc",
		data : fv,
		dataType : 'json',
		success : function(msg) {
			parent.hideSubPage();
			parent.reloadData();
		},
		error : function() {
			$.messager.alert('提示','提交事件失败！');
		}
	});
}
//修改事件
function modifyIncident(){
	getFromVars();
	if(!validateForm()){
		return;
	}
	fv.incidentId = incidentId;
	$.ajax({
		type : 'post',
		url : rootPath + "/incident/modify",
		data : fv,
		dataType : 'json',
		success : function(msg) {
			parent.hideSubPage();
			parent.reloadData();
		},
		error : function() {
			$.messager.alert('提示','修改事件失败！');
		}
	});
}
//修改事件直接提交
function modifyIncidentAutoCommit(){
	getFromVars();
	if(!validateForm()){
		return;
	}
	fv.incidentId = incidentId;
	$.ajax({
		type : 'post',
		url : rootPath + "/incident/modifyc",
		data : fv,
		dataType : 'json',
		success : function(msg) {
			parent.hideSubPage();
			parent.reloadData();
		},
		error : function() {
			$.messager.alert('提示','提交事件失败！');
		}
	});
}

