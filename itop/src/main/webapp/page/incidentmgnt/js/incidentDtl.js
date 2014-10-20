
//form表单值
var fv = {};

$(function(){
	//如果是编辑状态
	if(openFlag=='m'){
		//查询事件信息绑定表单
		queryIncident();
	}
});

//当产品列表选中时，触发服务目录重新加载
function prodSelChange(data){
	$("#moduleSel").combotree('setValue','');
	$("#moduleSel").combotree('reload',rootPath+'/product/moduleTree?productId='+data.scProductId);
}

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
			$("#prodSel").combobox('setValue',msg.scProductId);
			$("#moduleSel").combotree('reload',rootPath+'/product/moduleTree?productId='+msg.scProductId);
			setTimeout(function(){
				$("#moduleSel").combotree('setValue',msg.scModuleId);
			},200);
			//影响度
			$("input[name=affectVar]").each(function(){
				if($(this).val()==msg.affectCode){
					$(this).attr("checked","checked");
				}
			});
			$("#inciTypeSel").combobox('setValue',msg.classCode);
			$("#brief").val(msg.brief);
			$("#happenTime").datebox('setValue',dateFormatter(msg.happenTime));
			$("#detail").val(msg.detail);
			$("#ccList").val(msg.ccList);
			var attachData = msg.attachList;
			if(attachData!=null){
				for(var i=0;i<attachData.length;i++){
					var attachInfo = '<div attachId='+attachData[i].attachId+'><a href="javascript:attachDownLoad('+attachData[i].attachId+')">'+attachData[i].attachName+'</a><i class="fa fa-times" onclick="attachRemove(this,'+attachData[i].attachId+')"></i></div>';
					$("#attach").prepend(attachInfo);
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
			fv.affectCode =  $(this).val();
			fv.affectVar = $(this).attr("text");
		}
	});
	//事件分类
	fv.classCode = $("#inciTypeSel").combobox('getValue');
	fv.classVar = $("#inciTypeSel").combobox('getText');
	fv.brief = $.trim($("#brief").val());
	fv.happenTime =$("#happenTime").datebox('getValue');
	fv.detail = $.trim($("#detail").val());
	fv.ccList = $.trim($("#ccList").val());
	//1-用户报告 2-顾问开单
	if(opType=="USER"){
		fv.sourceCode = 1;
		fv.sourceVal = "用户报告";
	}else if(opType=="OP"){
		fv.sourceCode = 2;
		fv.sourceVal = "顾问开单";
	}
	//附件列表
	var attachIdArray = [];
	$("#attachList div").each(function(){
		attachIdArray.push($(this).attr("attachId"));
	});
	fv.attachList = attachIdArray.join(",");
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
	return true;
}
//保存按钮
function save(){
	if(openFlag=='a'){
		addIncident();
	}else{
		modifyIncident();
	}
}
//保存提交按钮
function saveAndCommit(){
	if(openFlag=='a'){
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
		success : function() {
			parent.hideSubPage();
			parent.changeStatusNav(1);
			parent.query();
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
		dataType : 'text',
		success : function() {
			parent.hideSubPage();
			parent.reloadData(2);
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
		dataType : 'text',
		success : function() {
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
		dataType : 'text',
		success : function() {
			parent.hideSubPage();
			parent.reloadData(2);
		},
		error : function() {
			$.messager.alert('提示','提交事件失败！');
		}
	});
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

//上传附件
function attachUpload(){
	 $.ajaxFileUpload({
			url:rootPath+'/attach/upload', 
			secureuri:false,
			fileElementId:'uploadFile1',
			dataType: 'json',
			success: function (data, status){
				if(data.success){
					var attachInfo = '<div attachId='+data.attachId+'><a href="javascript:attachDownLoad('+data.attachId+')">'+data.filename+'</a><i class="fa fa-times" onclick="attachRemove(this,'+data.attachId+')"></i></div>';
					$("#attachList").prepend(attachInfo);
				}else{
					alert(data.message);
				}
			},
			error: function (data, status, e){
				//mask.hide();
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
		error : function() {
			$.messager.alert('提示','删除附件失败！');
		}
	});
}
