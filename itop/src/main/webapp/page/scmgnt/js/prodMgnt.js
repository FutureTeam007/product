
$(function(){
	initProductTable();
	initModuleTree();
});

//初始化产品表格
function initProductTable(){
	$('#prodDataTable').datagrid({
		idField:'scProductId',
	    url:rootPath+'/product/infolist',
	    method:'get',
		loadMsg:i18n.loading.GridLoading,
		singleSelect:true,
		remoteSort:false,
		pagination:false,
		fitColumns:true,
	    columns:[[
	        {field:'scProductId',width:fixWidth(0.15),title:'',formatter:operationFormatter},
	        {field:'productNameZh',width:fixWidth(0.3),title:i18n.scmgnt.prodinfo.ProdTableTitleProdNameZh,editor:{type:'textbox',options:{required:true}}},
	        {field:'productNameEn',width:fixWidth(0.3),title:i18n.scmgnt.prodinfo.ProdTableTitleProdNameEn,editor:{type:'textbox',options:{required:true}}},
            {field:'prodCode',width:fixWidth(0.15),title:i18n.scmgnt.prodinfo.ProdTableTitleProdCode,editor:{type:'textbox',options:{required:true}}},
            {field:'state',width:fixWidth(0.1),title:i18n.scmgnt.prodinfo.ProdTableTitleState,formatter:prodStateFormatter,editor:stateEditor},
	    ]],
	    onLoadSuccess:function(){
	    	//默认选中第一行
	    	var data = $(this).datagrid("getData");
			if(data.rows.length!=0){
				$(this).datagrid('selectRow',0);
			}
		},
		onSelect:function(rowIndex, rowData){
			loadModuleTree(rowData.scProductId);
		}
	});
}

//类型编辑器
var stateEditor={
	type:'combobox',
	options:{
		mode:'local',
		editable:false,
		data:[{
		    "stateCode":1,
		    "stateVal":i18n.scmgnt.prodinfo.ProdStateNormal
		},{
		    "stateCode":0,
		    "stateVal":i18n.scmgnt.prodinfo.ProdStateDisable
		}],
		valueField:'stateCode',
		textField:'stateVal',
		panelHeight:'auto',
		required:true
	}
};

//调整表格列宽
function fixWidth(percent){
	return document.body.clientWidth * percent;
}

//格式化产品线操作列
function prodStateFormatter(val,row){
	if(val=='0'){
		return i18n.scmgnt.prodinfo.ProdStateDisable;
	}else if(val=='1'){
		return i18n.scmgnt.prodinfo.ProdStateNormal;
	}
}

//操作列格式化
function operationFormatter(val,row,index){
	if(row.editState){
		var buttons = "<button type='button' class='btn btn-link' onclick='submitChanges(this)'>"+i18n.scmgnt.paraminfo.SubmitBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='canceNewlEdit(0,this)'>"+i18n.scmgnt.paraminfo.CancelEditBtn+"</button>";
		return buttons;
	}else{
		var buttons = "<button type='button' class='btn btn-link' onclick='edit("+index+",this)'>"+i18n.scmgnt.paraminfo.EditBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='removeProd("+row.scProductId+")'>"+i18n.scmgnt.paraminfo.RemoveBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link undis' onclick='submitChanges(this)'>"+i18n.scmgnt.paraminfo.SubmitBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link undis' onclick='cancelEdit("+index+",this)'>"+i18n.scmgnt.paraminfo.CancelEditBtn+"</button>";
		return buttons;
	}
}

var editIndex = null;
//编辑
function edit(rowIndex,el){
	$('#prodDataTable').datagrid('selectRow',rowIndex).datagrid('beginEdit', rowIndex);
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
	$('#prodDataTable').datagrid('deleteRow', 0);
}

//删除
function removeProd(productId){
	$.ajax({
		type : 'post',
		url : rootPath+"/product/remove",
		data : {
			productId:productId
		},
		dataType : 'text',
		success : function() {
			$('#prodDataTable').datagrid('acceptChanges');
			$('#prodDataTable').datagrid('reload');
		},
		error : function(request) {
			$('#prodDataTable').datagrid('loaded');
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,msg);
		}
	});
}

//提交
function submitChanges(el){
	if(!validBeforeSubmit()){
		return;
	}
	$('#prodDataTable').datagrid('endEdit',editIndex);
	editIndex = null;
	//恢复按钮
	$(el).siblings().show();
	//隐藏提交按钮
	$(el).hide();
	//隐藏取消按钮
	$(el).next().hide();
	//向后台提交更新请求
	if ($('#prodDataTable').datagrid('getChanges').length) {
		var updated = $('#prodDataTable').datagrid('getChanges', "updated");
		if(updated&&updated.length>0){
			var data = updated[0];
			$('#prodDataTable').datagrid('loading');
			$.ajax({
				type : 'post',
				url : rootPath + "/product/modify",
				data : data,
				dataType : 'text',
				success : function() {
					$('#prodDataTable').datagrid('acceptChanges');
					$('#prodDataTable').datagrid('reload');
				},
				error : function(request) {
					$('#prodDataTable').datagrid('loaded');
					var msg = eval("("+request.responseText+")").errorMsg;
					$.messager.alert(i18n.dialog.AlertTitle,msg);
				}
			});
		}
		var inserted = $('#prodDataTable').datagrid('getChanges', "inserted");
		if(inserted&&inserted.length>0){
			var data = inserted[0];
			$('#prodDataTable').datagrid('loading');
			$.ajax({
				type : 'post',
				url : rootPath + "/product/add",
				data : data,
				dataType : 'text',
				success : function() {
					$('#prodDataTable').datagrid('acceptChanges');
					$('#prodDataTable').datagrid('reload');
				},
				error : function(request) {
					$('#prodDataTable').datagrid('loaded');
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
	if ($('#prodDataTable').datagrid('validateRow', editIndex)){
		var ed = $('#prodDataTable').datagrid('getEditor', {index:editIndex,field:'prodCode'});
		var val = $(ed.target).textbox('getText');
		var data = $("#prodDataTable").datagrid("getData");
		if(data.rows.length!=0){
			var rows = data.rows;
			for(var i=0;i<rows.length;i++){
				if(i!=editIndex&&rows[i].paramCode==val){
					$.messager.alert(i18n.dialog.AlertTitle,i18n.scmgnt.prodinfo.ProdCodeExist);
					return false;
				}
			}
		}
		return true;
	}else{
		return false;
	}
}

//取消
function cancelEdit(rowIndex,el){
	if(editIndex!=null){
		//取消编辑
		$('#prodDataTable').datagrid('cancelEdit', editIndex);
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
function addProduct(){
	if(editIndex!=null){
		return;
	}
	editIndex = 0;
	//顶部插入一条数据
	$('#prodDataTable').datagrid('insertRow',{
		index:0,
		row:{editState:'1'}
	});
	$('#prodDataTable').datagrid('selectRow', 0).datagrid('beginEdit', 0);
}

//加载树的数据
function loadModuleTree(productId){
	if(!productId){
		return;
	}
	$("#productId").val(productId);
	$('#moduleTree').tree('options').url = rootPath+"/product/moduleTree/all?productId="+productId;
	$('#moduleSel').combotree('options').url = rootPath+"/product/moduleTree/all?productId="+productId;
	$('#moduleTree').tree('reload');
	$('#moduleSel').combotree('reload');
}

//初始化服务目录表格
function initModuleTree(){
	$('#moduleTree').tree({
		editable:false,
		disabled:false,
	    onSelect:function(data){
	    	bindModuleData(data.attributes.scModuleId);
	    },
	    onLoadSuccess:function(){
	    	var rootNodes = $(this).tree('getRoots');
			if(rootNodes&&rootNodes.length>0){
				$(this).tree('select',rootNodes[0].target);
			}
		}
	});
	$("#moduleSel").combotree({
		editable:false,
		panelHeight:'200',
		loadFilter: function(data){ 
	    	return [{text: i18n.scmgnt.prodinfo.ModuleSelClear,id:-1}].concat(data);
	    },
	    onSelect:function(data){
	    	if(data.id==-1){
	    		$("#moduleSel").combotree('clear');
	    	}
	    }
	});
}

//绑定服务目录数据
function bindModuleData(moduleId){
	resetForm();
	$.ajax({
		type : 'get',
		url : rootPath + "/product/module/get",
		cache: false,
		data : {
			moduleId :moduleId
		},
		dataType : 'json',
		success : function(msg) {
			$("#addBtn").show();
			$("#editBtn").show();
			$("#deleteBtn").show();
			$("#backBtn").hide();
			$("#submitBtn").hide();
			$("#moduleId").val(msg.scModuleId);
			$("#moduleCode").val(msg.moduleCode);
			$("#moduleNameZh").val(msg.moduleNameZh);
			$("#moduleNameEn").val(msg.moduleNameEn);
			$("#moduleDescZh").val(msg.moduleDescZh);
			$("#moduleDescEn").val(msg.moduleDescEn);
			$('#moduleSel').combotree('setValue',msg.supModuleId);
			$("input[name=status]").each(function(){
				if($(this).val()==msg.state){
					$(this).attr("checked","checked");
				}
			});
			$("#editType").val("2");
			setFormDisable();
		},
		error : function() {
			$.messager.alert(i18n.dialog.AlertTitle,i18n.scmgnt.moduleinfo.QryModuleInfoError);
		}
	});
}

//新建服务目录
function addNewModule(){
	resetForm();
	setFormEnable();
	$("#submitBtn").show();
	$("#backBtn").show();
	$("#addBtn").hide();
	$("#deleteBtn").hide();
	$("#editBtn").hide();
	$("#editType").val("1");
}

//编辑服务目录
function editModule(){
	setFormEnable();
	$("#submitBtn").show();
	$("#backBtn").show();
	$("#addBtn").hide();
	$("#deleteBtn").hide();
	$("#editBtn").hide();
	$("#editType").val("2");
}

//移除服务目录
function removeModule(){
	//判断是否有子节点
	var isLeaf = $('#moduleTree').tree('isLeaf',$('#moduleTree').tree('getSelected').target);
	if(!isLeaf){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.scmgnt.prodinfo.ModuleHasSubModule);
		return;
	}
	$.ajax({
		type : 'post',
		url : rootPath + "/product/module/remove",
		cache: false,
		data : {
			moduleId:$('#moduleTree').tree('getSelected').attributes.scModuleId
		},
		dataType : 'text',
		success : function(msg) {
			$('#moduleTree').tree('reload');
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,msg);
		}
	});
}

//检查Form表单
function validateForm(){
	var moduleCode = $.trim($("#moduleCode").val());
	var moduleNameEn = $.trim($("#moduleNameEn").val());
	var moduleNameZh = $.trim($("#moduleNameZh").val());
	var moduleDescEn = $.trim($("#moduleDescEn").val());
	var moduleDescZh = $.trim($("#moduleDescZh").val());
	if(!moduleCode){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.scmgnt.moduleinfo.ModuleCodeEmptyError);
		return false;
	}
	if(!moduleNameEn){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.scmgnt.moduleinfo.ModuleZhNameEmptyError);
		return false;
	}
	if(!moduleNameZh){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.scmgnt.moduleinfo.ModuleEnNameEmptyError);
		return false;
	}
	if(!moduleDescZh||!moduleDescEn){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.scmgnt.moduleinfo.ModuleDescEmptyError);
		return false;
	}
	return true;
}

//返回编辑之前的状态
function backFront(){
	bindModuleData($('#moduleTree').tree('getSelected').attributes.scModuleId);
}

//设置服务目录表单为禁止编辑状态
function setFormDisable(){
	$("#moduleCode").attr("disabled","disabled");
	$("#moduleNameZh").attr("disabled","disabled");
	$("#moduleNameEn").attr("disabled","disabled");
	$("#moduleDescZh").attr("disabled","disabled");
	$("#moduleDescEn").attr("disabled","disabled");
	$("input[name=status]").each(function(){
		$(this).attr("disabled","disabled");
	});
	$('#moduleSel').combotree('disable');
}
//设置服务目录表单为启用编辑状态
function setFormEnable(){
	$("#moduleCode").removeAttr("disabled");
	$("#moduleNameZh").removeAttr("disabled");
	$("#moduleNameEn").removeAttr("disabled");
	$("#moduleDescZh").removeAttr("disabled");
	$("#moduleDescEn").removeAttr("disabled");
	$('#moduleSel').combotree('enable');
	$("input[name=status]").each(function(){
		$(this).removeAttr("disabled");
	});
}

//重置表单
function resetForm(){
	$("#moduleCode").val("");
	$("#moduleNameEn").val("");
	$("#moduleNameZh").val("");
	$("#moduleDesc").val("");
	//$('#moduleSel').combotree('setValue',$('#moduleTree').tree('getSelected').attributes.scModuleId);
	$("input[name=status]").each(function(){
		if($(this).val()==1){
			$(this).attr("checked","checked");
		}
	});
}

function submitModule(){
	if(!validateForm()){
		return;
	}
	$.messager.progress();
	var fp = {};
	fp.moduleId = $.trim($("#moduleId").val());
	fp.productId = $.trim($("#productId").val());
	fp.editType = $.trim($("#editType").val());
	fp.supModuleId = $("#moduleSel").combotree('getValue');
	fp.moduleCode = $.trim($("#moduleCode").val());
	fp.moduleNameEn = $.trim($("#moduleNameEn").val());
	fp.moduleNameZh = $.trim($("#moduleNameZh").val());
	fp.moduleDescEn = $.trim($("#moduleDescEn").val());
	fp.moduleDescZh = $.trim($("#moduleDescZh").val());
	$("input[name=status]").each(function(){
		if($(this).attr("checked")=="checked"){
			fp.status = $(this).val();
		}
	});
	$.ajax({
		type : 'post',
		url : rootPath + "/product/module/save",
		cache: false,
		data : fp,
		dataType : 'text',
		success : function(msg) {
			$('#moduleTree').tree('reload');
			$.messager.progress('close');
		},
		error : function(request) {
			$.messager.progress('close');
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,msg);
		}
	});
}

