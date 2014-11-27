
$(function(){
	initJobTable();
});

//初始化岗位表格
function initJobTable(){
	$('#jobDataTable').datagrid({
		idField:'scJobId',
	    method:'get',
	    url:rootPath+'/job/infolist',
		loadMsg:i18n.loading.GridLoading,
		singleSelect:true,
		remoteSort:false,
		pagination:false,
		fitColumns:true,
	    columns:[[
	        {field:'scJobId',width:fixWidth(0.2),title:'',formatter:operationFormatter},
	        {field:'jobNameZh',width:fixWidth(0.15),title:i18n.scmgnt.jobinfo.JobTableTitleNameZh,editor:{type:'textbox',options:{required:true}}},
	        {field:'jobNameEn',width:fixWidth(0.15),title:i18n.scmgnt.jobinfo.JobTableTitleNameEn,editor:{type:'textbox',options:{required:true}}},
	        {field:'jobCode',width:fixWidth(0.1),title:i18n.scmgnt.jobinfo.JobTableTitleCode,editor:{type:'textbox',options:{required:true}}},
            {field:'jobClass',width:fixWidth(0.2),title:i18n.scmgnt.jobinfo.JobTableTitleClass,editor:jobClassEditor,formatter:function(val,row){return row.jobClassName;}},
	        {field:'jobLevel',width:fixWidth(0.2),title:i18n.scmgnt.jobinfo.JobTableTitleLevel,editor:jobLevelEditor,formatter:function(val,row){return row.jobLevelName;}}
	    ]]
	});
}

//查找名称国际化值
function getLocaleValue(datas,locale){
	if(!datas||datas.length==0){
		return "";
	}
	for(var i=0;i<datas.length;i++){
		if(datas[i].langFlag==locale){
			return datas[i].jobName;
		}
	}
}

//岗位类别编辑器
var jobClassEditor={
	type:'combobox',
	options:{
		method:'get',
		url:rootPath+"/param/list?kindCode=JOB_CLASS",
		editable:false,
		required:true,
		valueField:'paramCode',
		textField:'paramValue',
		panelHeight:200
	}
};

//岗位级别编辑器
var jobLevelEditor={
	type:'combobox',
	options:{
		method:'get',
		url:rootPath+"/param/list?kindCode=JOB_LEVEL",
		editable:false,
		required:true,
		valueField:'paramCode',
		textField:'paramValue',
		panelHeight:200
	}
};


//调整表格列宽
function fixWidth(percent){
	return document.body.clientWidth * percent;
}

//操作列格式化
function operationFormatter(val,row,index){
	if(row.editState){
		var buttons = "<button type='button' class='btn btn-link' onclick='submitChanges(this)'>"+i18n.scmgnt.jobinfo.SubmitBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='canceNewlEdit(0,this)'>"+i18n.scmgnt.jobinfo.CancelEditBtn+"</button>";
		return buttons;
	}else{
		var buttons = "<button type='button' class='btn btn-link' onclick='edit("+index+",this)'>"+i18n.scmgnt.jobinfo.EditBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='removeJob("+row.scJobId+")'>"+i18n.scmgnt.jobinfo.RemoveBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link undis' onclick='submitChanges(this)'>"+i18n.scmgnt.jobinfo.SubmitBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link undis' onclick='cancelEdit("+index+",this)'>"+i18n.scmgnt.jobinfo.CancelEditBtn+"</button>";
		return buttons;
	}
}

var editIndex = null;

//新增岗位
function addJob(){
	if(editIndex!=null){
		return;
	}
	editIndex = 0;
	//顶部插入一条数据
	$('#jobDataTable').datagrid('insertRow',{
		index:0,
		row:{editState:'1'}
	});
	$('#jobDataTable').datagrid('selectRow', 0).datagrid('beginEdit', 0);
}
//取消新增岗位
function canceNewlEdit(rowIndex,el){
	editIndex = null;
	$('#jobDataTable').datagrid('deleteRow', 0);
}
//编辑岗位
function edit(rowIndex,el){
	if(editIndex!=null){
		return;
	}
	$('#jobDataTable').datagrid('selectRow',rowIndex).datagrid('beginEdit', rowIndex);
	editIndex = rowIndex;
	//切换按钮
	$(el).siblings().hide();
	$(el).hide();
	//显示提交按钮
	$(el).next().next().show();
	//显示取消编辑按钮
	$(el).next().next().next().show();
}
//取消编辑岗位
function cancelEdit(rowIndex,el){
	if(editIndex!=null){
		//取消编辑
		$('#jobDataTable').datagrid('cancelEdit', editIndex);
		editIndex = null;
		//恢复按钮
		$(el).siblings().show();
		//隐藏取消按钮
		$(el).hide();
		//隐藏提交按钮
		$(el).prev().prev().hide();
	}
}
//提交岗位信息
function submitChanges(el){
	if(!validBeforeSubmit()){
		return;
	}
	$('#jobDataTable').datagrid('endEdit',editIndex);
	editIndex = null;
	//恢复按钮
	$(el).siblings().show();
	//隐藏提交按钮
	$(el).hide();
	//隐藏取消按钮
	$(el).next().hide();
	//向后台提交更新请求
	if ($('#jobDataTable').datagrid('getChanges').length) {
		var updated = $('#jobDataTable').datagrid('getChanges', "updated");
		if(updated&&updated.length>0){
			var data = updated[0];
			$('#jobDataTable').datagrid('loading');
			$.ajax({
				type : 'post',
				url : rootPath + "/job/modify",
				data : data,
				dataType : 'text',
				success : function() {
					$('#jobDataTable').datagrid('acceptChanges');
					$('#jobDataTable').datagrid('reload');
				},
				error : function(request) {
					$('#jobDataTable').datagrid('loaded');
					var msg = eval("("+request.responseText+")").errorMsg;
					$.messager.alert(i18n.dialog.AlertTitle,msg);
				}
			});
		}
		var inserted = $('#jobDataTable').datagrid('getChanges', "inserted");
		if(inserted&&inserted.length>0){
			var data = inserted[0];
			$('#jobDataTable').datagrid('loading');
			$.ajax({
				type : 'post',
				url : rootPath + "/job/add",
				data : data,
				dataType : 'text',
				success : function() {
					$('#jobDataTable').datagrid('acceptChanges');
					$('#jobDataTable').datagrid('reload');
				},
				error : function(request) {
					$('#jobDataTable').datagrid('loaded');
					var msg = eval("("+request.responseText+")").errorMsg;
					$.messager.alert(i18n.dialog.AlertTitle,msg);
				}
			});
		}
	}
	editIndex = null;
}

//提交前检查
function validBeforeSubmit(){
	if ($('#jobDataTable').datagrid('validateRow', editIndex)){
		var ed = $('#jobDataTable').datagrid('getEditor', {index:editIndex,field:'jobCode'});
		var val = $(ed.target).textbox('getText');
		var data = $("#jobDataTable").datagrid("getData");
		if(data.rows.length!=0){
			var rows = data.rows;
			for(var i=0;i<rows.length;i++){
				if(i!=editIndex&&rows[i].jobCode==val){
					$.messager.alert(i18n.dialog.AlertTitle,i18n.scmgnt.jobinfo.JobCodeExist);
					return false;
				}
			}
		}
		return true;
	}else{
		return false;
	}
}
//删除岗位
function removeJob(jobId){
	$.ajax({
		type : 'post',
		url : rootPath+"/job/remove",
		data : {
			jobId:jobId
		},
		dataType : 'text',
		success : function() {
			$('#jobDataTable').datagrid('acceptChanges');
			$('#jobDataTable').datagrid('reload');
		},
		error : function(request) {
			$('#jobDataTable').datagrid('loaded');
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,msg);
		}
	});
}
