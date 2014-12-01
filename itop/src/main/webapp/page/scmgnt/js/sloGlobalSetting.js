
$(function(){
	bindSLOTimeParameter();
	bindSLODefaultRules();
});

//加载slo时间参数设置
function bindSLOTimeParameter(){
	setFormDisable();
	$("#editBtn").show();
	$("#backBtn").hide();
	$("#submitBtn").hide();
	$.ajax({
		type : 'get',
		url : rootPath + "/scmgnt/slo/getTimeConfig",
		cache: false,
		dataType : 'json',
		success : function(msg) {
			$("#workStartTimeAm").datetimespinner('setValue',msg.workStartTimeAm);
			$("#workEndTimeAm").datetimespinner('setValue',msg.workEndTimeAm);
			$("#workStartTimePm").datetimespinner('setValue',msg.workStartTimePm);
			$("#workEndTimePm").datetimespinner('setValue',msg.workEndTimePm);
			if(msg.timeType==-1){
				hideTimeConfig();
			}else if(msg.timeType==1){
				showTimeConfig();
			}
			$("input[name=timeType]").each(function(){
				if($(this).val()==msg.timeType){
					$(this).attr("checked","checked");
				}
			});
			$("#paramId").val(msg.paramId);
			
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,msg);
		}
	});
}

//设置sloform禁用
function setFormDisable(){
	$("#workStartTimeAm").datetimespinner('disable');
	$("#workEndTimeAm").datetimespinner('disable');
	$("#workStartTimePm").datetimespinner('disable');
	$("#workEndTimePm").datetimespinner('disable');
	$("input[name=timeType]").each(function(){
		$(this).attr("disabled","disabled");
	});
}
//设置sloform启用
function setFormEnable(){
	$("#workStartTimeAm").datetimespinner('enable');
	$("#workEndTimeAm").datetimespinner('enable');
	$("#workStartTimePm").datetimespinner('enable');
	$("#workEndTimePm").datetimespinner('enable');
	$("input[name=timeType]").each(function(){
		$(this).removeAttr("disabled");
	});
}

//编辑slo时间
function editTimeRule(){
	setFormEnable();
	$("#editBtn").hide();
	$("#backBtn").show();
	$("#submitBtn").show();
}

//取消编辑slo
function backFront(){
	bindSLOTimeParameter();
}

//当选择7*24时隐藏时间设置
function hideTimeConfig(){
	$("#workTimeAmItem").slideUp('fast');
	$("#workTimePmItem").slideUp('fast');
}

//当选择5*8时显示时间设置
function showTimeConfig(){
	$("#workTimeAmItem").slideDown('fast');
	$("#workTimePmItem").slideDown('fast');
}

//提交slo时间参数
function submitTimeRule(){
	$.messager.progress();
	//获取参数
	var fp = {};
	$("input[name=timeType]").each(function(){
		if($(this).get(0).checked){
			fp.timeType = $(this).val();
		}
	});
	fp.paramId = $("#paramId").val();
	fp.workStartTimeAm=$("#workStartTimeAm").datetimespinner('getValue');
	fp.workEndTimeAm=$("#workEndTimeAm").datetimespinner('getValue');
	fp.workStartTimePm=$("#workStartTimePm").datetimespinner('getValue');
	fp.workEndTimePm=$("#workEndTimePm").datetimespinner('getValue');
	$.ajax({
		type : 'post',
		url : rootPath + "/scmgnt/slo/modifyTimeConfig",
		cache: false,
		data: fp,
		dataType : 'text',
		success : function() {
			$.messager.progress('close');
			bindSLOTimeParameter();
		},
		error : function(request) {
			$.messager.progress('close');
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,msg);
		}
	});
}


/////////////////////////SLO缺省规则//////////////////////////
//初始化产品数据表格
function bindSLODefaultRules(){
	$('#sloDataTable').datagrid({
		url:rootPath+'/custmgnt/slo/list',
	    queryParams:{custId:-1},
	    method:'get',
		loadMsg:i18n.loading.GridLoading,
		singleSelect:true,
		remoteSort:false,
		pagination:false,
		fitColumns:true,
	    columns:[[
	        {field:'scProductId',width:fixWidth(0.12),title:'',formatter:operationFormatter},
            {field:'priorityVal',width:fixWidth(0.1),title:i18n.custmgnt.slorule.SloTableTitlePriority},
            {field:'complexVal',width:fixWidth(0.1),title:i18n.custmgnt.slorule.SloTableTitleComplex},
            {field:'responseTime',width:fixWidth(0.15),title:i18n.custmgnt.slorule.SloTableTitleRespTime,editor:{type:'numberbox',options:{required:true,min:10,max:999999}}},
            {field:'dealTime',width:fixWidth(0.15),title:i18n.custmgnt.slorule.SloTableTitleDealTime,editor:{type:'numberbox',options:{required:true,min:10,max:999999}}}
	    ]]
	});
}

//调整表格列宽
function fixWidth(percent){
	return document.body.clientWidth * percent;
}

//操作列格式化
function operationFormatter(val,row,index){
	var buttons = "<button type='button' class='btn btn-link' onclick='edit("+index+",this)'>"+i18n.custmgnt.slorule.EditBtn+"</button>";
	buttons += "<button type='button' class='btn btn-link' onclick='clear(-1,-1,"+row.priorityCode+","+row.complexCode+")'>"+i18n.custmgnt.slorule.ClearBtn+"</button>";
	buttons += "<button type='button' class='btn btn-link undis' onclick='submitChanges(this)'>"+i18n.custmgnt.slorule.SubmitBtn+"</button>";
	buttons += "<button type='button' class='btn btn-link undis' onclick='cancelEdit("+index+",this)'>"+i18n.custmgnt.slorule.CancelEditBtn+"</button>";
	return buttons;
}

var editIndex = null;
//编辑
function edit(rowIndex,el){
	if(editIndex!=null){
		return;
	}
	$('#sloDataTable').datagrid('selectRow',rowIndex).datagrid('beginEdit', rowIndex);
	editIndex = rowIndex;
	//切换按钮
	$(el).siblings().hide();
	$(el).hide();
	//显示提交按钮
	$(el).next().next().show();
	//显示取消编辑按钮
	$(el).next().next().next().show();
}

//清除
function clear(custId,productId,priority,complex){
	$.ajax({
		type : 'post',
		url : rootPath+"/custmgnt/slo/clear",
		data : {
			custId:custId,
			productId:productId,
			priority:priority,
			complex:complex
		},
		dataType : 'text',
		success : function() {
			$('#sloDataTable').datagrid('acceptChanges');
			$('#sloDataTable').datagrid('reload');
		},
		error : function(request) {
			$('#sloDataTable').datagrid('loaded');
			var msg = eval("("+request.responseText+")").errorMsg;
			parent.showMsg(msg);
		}
	});
}

//提交SLO新值
function submitChanges(el){
	$('#sloDataTable').datagrid('endEdit',editIndex);
	editIndex = null;
	//恢复按钮
	$(el).siblings().show();
	//隐藏提交按钮
	$(el).hide();
	//隐藏取消按钮
	$(el).next().hide();
	if ($('#sloDataTable').datagrid('validateRow', editIndex)){
		//向后台提交更新请求
		if ($('#sloDataTable').datagrid('getChanges').length) {
			var updated = $('#sloDataTable').datagrid('getChanges', "updated");
			if(updated&&updated.length>0){
				var data = updated[0];
				$('#opDataTable').datagrid('loading');
				$.ajax({
					type : 'post',
					url : rootPath+"/custmgnt/slo/modify",
					data : data,
					dataType : 'text',
					success : function() {
						$('#opDataTable').datagrid('acceptChanges');
						$('#opDataTable').datagrid('loaded');
					},
					error : function(request) {
						$('#opDataTable').datagrid('loaded');
						var msg = eval("("+request.responseText+")").errorMsg;
						parent.showMsg(msg);
					}
				});
			}
		}
	}
}

//取消编辑
function cancelEdit(rowIndex,el){
	if(editIndex!=null){
		//取消编辑
		$('#sloDataTable').datagrid('cancelEdit', editIndex);
		editIndex = null;
		//恢复按钮
		$(el).siblings().show();
		//隐藏取消按钮
		$(el).hide();
		//隐藏提交按钮
		$(el).prev().hide();
	}
}