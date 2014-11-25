
var selectedParamCode = null;

$(function(){
	bindParamSelect();
	initParamValues();
});

//绑定参数选择列表
function bindParamSelect(){
	$.ajax({
		type : 'get',
		url : rootPath+"/param/select",
		dataType : 'json',
		success : function(response) {
			for(var i=0;i<response.length;i++){
				$("<button type='button' class='btn btn-primary btn-sm btn-outline param-select' onclick='loadParamList(this,\""+response[i].paramKindCode+"\")'>"+response[i].paramKind+"</button>").appendTo("#paramList");
			}
		},
		error : function(response) {
			var msg = response.errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,msg);
		}
	});
	
}
//初始化参数值列表
function initParamValues(){
	$('#paramDataTable').datagrid({
		idField:'scParamId1',
	    method:'get',
		loadMsg:i18n.loading.GridLoading,
		singleSelect:true,
		remoteSort:false,
		pagination:false,
		fitColumns:true,
	    columns:[[
	        {field:'scParamId1',width:fixWidth(0.12),title:'',formatter:operationFormatter},
            {field:'paramKind',width:fixWidth(0.15),title:i18n.scmgnt.paraminfo.ParamTableTitleCode,editor:{type:'textbox',options:{required:true}}},
            {field:'paramCode',width:fixWidth(0.15),title:i18n.scmgnt.paraminfo.ParamTableTitleCode,editor:{type:'textbox',options:{required:true}}},
            {field:'paramValueZh',width:fixWidth(0.2),title:i18n.scmgnt.paraminfo.ParamTableTitleValueZh,editor:{type:'textbox',options:{required:true}}},
            {field:'paramValueEn',width:fixWidth(0.2),title:i18n.scmgnt.paraminfo.ParamTableTitleValueEn,editor:{type:'textbox',options:{required:true}}}
	    ]]
	});
}

//加载参数值列表
function loadParamList(el,kindCode){
	$(el).removeClass("btn-outline");
	$(el).siblings().addClass("btn-outline");
	selectedParamCode = kindCode;
	$('#paramDataTable').datagrid('load',rootPath+'/param/valuelist?kindCode='+kindCode);
}

//操作列格式化
function operationFormatter(val,row){
	if(!row.state){
		var buttons = "<button type='button' class='btn btn-link' onclick='submitChanges(this)'>"+i18n.scmgnt.paraminfo.SubmitBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='canceNewlEdit(0,this)'>"+i18n.scmgnt.paraminfo.CancelEditBtn+"</button>";
		return buttons;
	}else{
		var buttons = "<button type='button' class='btn btn-link' onclick='edit("+index+",this)'>"+i18n.scmgnt.paraminfo.EditBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='removeParam("+row.scParamId1+","+row.scParamId2+")'>"+i18n.scmgnt.paraminfo.RemoveBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link undis' onclick='submitChanges(this)'>"+i18n.scmgnt.paraminfo.SubmitBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link undis' onclick='cancelEdit("+index+",this)'>"+i18n.scmgnt.paraminfo.CancelEditBtn+"</button>";
		return buttons;
	}
}

var editIndex = null;

//编辑
function edit(rowIndex,el){
	if(editIndex!=null){
		return;
	}
	$('#paramDataTable').datagrid('selectRow',rowIndex).datagrid('beginEdit', rowIndex);
	editIndex = rowIndex;
	//切换按钮
	$(el).siblings().hide();
	$(el).hide();
	//显示提交按钮
	$(el).next().next().show();
	//显示取消编辑按钮
	$(el).next().next().next().show();
}

//取消新编辑的行
function canceNewlEdit(rowIndex,el){
	editIndex = null;
	$('#opDataTable').datagrid('deleteRow', 0);
}

//删除
function removeParam(id1,id2){
	$.ajax({
		type : 'post',
		url : rootPath+"/param/remove",
		data : {
			ids:(id1+","+id2)
		},
		dataType : 'text',
		success : function() {
			$('#paramDataTable').datagrid('acceptChanges');
			$('#paramDataTable').datagrid('reload');
		},
		error : function(request) {
			$('#paramDataTable').datagrid('loaded');
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,msg);
		}
	});
}

//提交
function submitChanges(el){
	$('#paramDataTable').datagrid('endEdit',editIndex);
	editIndex = null;
	//恢复按钮
	$(el).siblings().show();
	//隐藏提交按钮
	$(el).hide();
	//隐藏取消按钮
	$(el).next().hide();
	if (!$('#paramDataTable').datagrid('validateRow', editIndex)){
		return;
	}
	//向后台提交更新请求
	if ($('#paramDataTable').datagrid('getChanges').length) {
		var updated = $('#paramDataTable').datagrid('getChanges', "updated");
		if(updated&&updated.length>0){
			var data = updated[0];
			$('#paramDataTable').datagrid('loading');
			$.ajax({
				type : 'post',
				url : rootPath + "/param/modify",
				data : data,
				dataType : 'text',
				success : function() {
					$('#paramDataTable').datagrid('acceptChanges');
					$('#paramDataTable').datagrid('reload');
				},
				error : function(request) {
					$('#paramDataTable').datagrid('loaded');
					var msg = eval("("+request.responseText+")").errorMsg;
					$.messager.alert(i18n.dialog.AlertTitle,msg);
				}
			});
		}
		var inserted = $('#paramDataTable').datagrid('getChanges', "inserted");
		if(inserted&&inserted.length>0){
			var data = inserted[0];
			$('#paramDataTable').datagrid('loading');
			$.ajax({
				type : 'post',
				url : rootPath + "/param/add",
				data : data,
				dataType : 'text',
				success : function() {
					$('#paramDataTable').datagrid('acceptChanges');
					$('#paramDataTable').datagrid('reload');
				},
				error : function(request) {
					$('#paramDataTable').datagrid('loaded');
					var msg = eval("("+request.responseText+")").errorMsg;
					$.messager.alert(i18n.dialog.AlertTitle,msg);
				}
			});
		}
	}
}

//取消
function cancelEdit(rowIndex,el){
	if(editIndex!=null){
		//取消编辑
		$('#paramDataTable').datagrid('cancelEdit', editIndex);
		editIndex = null;
		//恢复按钮
		$(el).siblings().show();
		//隐藏取消按钮
		$(el).hide();
		//隐藏提交按钮
		$(el).prev().prev().hide();
	}
}

//新增
function addParameter(){
	if(editIndex!=null){
		return;
	}
	editIndex = 0;
	//顶部插入一条数据
	$('#paramDataTable').datagrid('insertRow',{
		index:0,
		row:{paramCode:selectedParamCode,state:'1'}
	});
	$('#paramDataTable').datagrid('selectRow', 0).datagrid('beginEdit', 0);
}