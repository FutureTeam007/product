var qp={};
//Grid默认选中的数据
var selectedRowId = null;

$(function(){
	initOpDataGrid();
});

//初始化顾问数据表格
function initOpDataGrid(){
	$('#opDataTable').datagrid({
		idField:'scOpId',
	    url:rootPath+'/scmgnt/op/list/all',
	    queryParams:qp,
	    method:'get',
		loadMsg:i18n.loading.GridLoading,
		singleSelect:true,
		remoteSort:false,
		pagination:true,
		pageNumber:1,
		pageList:[10],
		pageSize:10,
		showPageList:false,
		frozenColumns:[[ 
		     {field:'scOpId',width:fixWidth(0.14),title:i18n.scmgnt.opinfo.TableTitleOperations,formatter:operationsFormatter}
		]],
	    columns:[[
	        {field:'state',width:fixWidth(0.07),title:i18n.scmgnt.opinfo.TableTitleOpState,formatter:stateFormatter},
	        {field:'opCode',width:fixWidth(0.08),title:i18n.scmgnt.opinfo.TableTitleOpCode,editor:{type:'textbox'}},
	        {field:'opName',width:fixWidth(0.11),title:i18n.scmgnt.opinfo.TableTitleOpName,editor:{type:'textbox'}},
            {field:'firstName',width:fixWidth(0.08),title:i18n.scmgnt.opinfo.TableTitleFirstName,editor:{type:'textbox'}},
	        {field:'lastName',width:fixWidth(0.08),title:i18n.scmgnt.opinfo.TableTitleLastName,editor:{type:'textbox'}},
	        {field:'loginCode',width:fixWidth(0.15),title:i18n.scmgnt.opinfo.TableTitleLoginCode,editor:{type:'textbox'}},
	        {field:'loginPasswd',width:fixWidth(0.08),title:i18n.scmgnt.opinfo.TableTitleLoginPasswd,editor:{type:'textbox'}},
	        {field:'gender',width:fixWidth(0.06),title:i18n.scmgnt.opinfo.TableTitleGender,formatter:genderFormatter,editor:genderEditor},
	        {field:'opKind',width:fixWidth(0.10),title:i18n.scmgnt.opinfo.TableTitleOpKind,formatter:opKindFormatter,editor:opKindEditor},
	        {field:'mobileNo',width:fixWidth(0.10),title:i18n.scmgnt.opinfo.TableTitleMobileNo,editor:{type:'numberbox'}},
	        {field:'officeTel',width:fixWidth(0.10),title:i18n.scmgnt.opinfo.TableTitleOfficeTel,editor:{type:'textbox'}}
	    ]],
	    onLoadSuccess:function(){
			var data = $(this).datagrid("getData");
			if(data.rows.length!=0){
				//增加逻辑，默认选中之前选中的行或者选中第一行
				if(selectedRowId){
					var pageRows = $(this).datagrid('getRows');
					if(pageRows&&pageRows.length>0){
						var isFind = false;
						for(var i=0;i<pageRows.length;i++){
							if(pageRows[i].ccUserId==selectedRowId){
								isFind = true;
								var index = $(this).datagrid('getRowIndex',pageRows[i]);
								$(this).datagrid('selectRow',index);
								break;
							}
						}
						if(!isFind){
							$(this).datagrid('selectRow',0);
						}
					}
				}else{
					$(this).datagrid('selectRow',0);
				}
			}
		}
	});
}

//重置
function reset(){
	$("#loginCode").val("");
	$("#opName").val("");
	qp.loginCode = null;
	qp.opName = null;
	selectedRowId = null;
	editIndex = null;
	$('#opDataTable').datagrid('load',qp);
}

//查询
function query(){
	editIndex = null;
	selectedRowId = null;
	qp.loginCode = $.trim($("#loginCode").val());
	qp.opName = $.trim($("#opName").val());
	$('#opDataTable').datagrid('load',qp);
}

//状态格式化列
function stateFormatter(val){
	if(val==null){
		return "";
	}
	if(val==1){
		return i18n.scmgnt.opinfo.StateNormal;
	}else if(val==0){
		return "<font color='red'>"+i18n.scmgnt.opinfo.StateLeave+"</font>";
	}else if(val==2){
		return "<font color='red'>"+i18n.scmgnt.opinfo.StateLock+"</font>";
	}
	return i18n.scmgnt.opinfo.InfoUnknown;
}

//性别列格式化
function genderFormatter(val){
	if(!val){
		return "";
	}
	if(val==1){
		return i18n.scmgnt.opinfo.GenderMr;
	}else if(val==2){
		return i18n.scmgnt.opinfo.GenderMs;
	}
	return i18n.scmgnt.opinfo.InfoUnknown;
}

//性别编辑器
var genderEditor={
	type:'combobox',
	options:{
		mode:'local',
		data:[{
		    "gender":1,
		    "genderName":i18n.scmgnt.opinfo.GenderMr
		},{
		    "gender":2,
		    "genderName":i18n.scmgnt.opinfo.GenderMs
		}],
		valueField:'gender',
		textField:'genderName',
		panelHeight:'auto',
		required:true,
		editable:false
	}
};

//操作员类别列格式化
function opKindFormatter(val){
	if(!val){
		return "";
	}
	if(val==0){
		return i18n.scmgnt.opinfo.OpKindSuper;
	}else if(val==1){
		return i18n.scmgnt.opinfo.OpKindAdmin;
	}else if(val==2){
		return i18n.scmgnt.opinfo.OpKindUser;
	}
	return i18n.scmgnt.opinfo.InfoUnknown;
}

//操作员类别编辑器
var opKindEditor={
	type:'combobox',
	options:{
		mode:'local',
		data:[{
		    "opKind":0,
		    "opKindName":i18n.scmgnt.opinfo.OpKindSuper
		},{
		    "opKind":1,
		    "opKindName":i18n.scmgnt.opinfo.OpKindAdmin
		},{
		    "opKind":2,
		    "opKindName":i18n.scmgnt.opinfo.OpKindUser
		}],
		valueField:'opKind',
		textField:'opKindName',
		panelHeight:'auto',
		required:true,
		editable:false
	}
};

//顾问数据表格操作列
function operationsFormatter(val,row,rowIndex){
	val=row.scOpId;
	if(!row.status){
		var buttons = "";
		if(row.state==1){
			buttons += "<button type='button' class='btn btn-link' onclick='stateChange("+val+",0)'>"+i18n.scmgnt.opinfo.OperLeaveBtn+"</button>";
			buttons += "<button type='button' class='btn btn-link' onclick='stateChange("+val+",2)'>"+i18n.scmgnt.opinfo.OperLockBtn+"</button>";
		}else if(row.state==0){
			buttons += "<button type='button' class='btn btn-link' onclick='stateChange("+val+",1)'>"+i18n.scmgnt.opinfo.OperUnLeaveBtn+"</button>";
		}else if(row.state==2){
			buttons += "<button type='button' class='btn btn-link' onclick='stateChange("+val+",1)'>"+i18n.scmgnt.opinfo.OperUnLockBtn+"</button>";
		}
		buttons += "<button type='button' class='btn btn-link undis' onclick='cancelEdit("+val+",this)'>"+i18n.scmgnt.opinfo.OperCancelEditBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='edit("+val+",this,"+rowIndex+")'>"+i18n.scmgnt.opinfo.OperEditBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link undis' onclick='submitChanges("+val+",this)'>"+i18n.scmgnt.opinfo.OperSubmitBtn+"</button>";
		return buttons;
	}else{
		var buttons = "<button type='button' class='btn btn-link' onclick='cancelNewEdit("+rowIndex+")'>"+i18n.scmgnt.opinfo.CancelBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='submitChanges("+val+",this,"+row.state+")'>"+i18n.scmgnt.opinfo.SubmitBtn+"</button>";
		return buttons;
	}
}

var editIndex=null;

//添加顾问
function addOp(){
	if(editIndex!=null){
		parent.showMsg(i18n.scmgnt.opinfo.EditConflictError);
		return;
	}
	editIndex = 0;
	//顶部插入一条数据
	$('#opDataTable').datagrid('insertRow',{
		index:0,
		row:{status:'1',state:'1'}
	});
	$('#opDataTable').datagrid('selectRow', 0).datagrid('beginEdit', 0);
}

//取消新插入的数据
function cancelNewEdit(rowIndex){
	editIndex = null;
	$('#opDataTable').datagrid('deleteRow', rowIndex);
}

//编辑顾问
function edit(val,el,rowIndex,pOpId){
	if(editIndex!=null){
		return;
	}
	$('#opDataTable').datagrid('selectRow',rowIndex).datagrid('beginEdit', rowIndex);
//	setTimeout(function(){
//		var ed = $('#opDataTable').datagrid('getEditor', {index:editIndex,field:'scOpId'});
//		$(ed.target).combobox('disable');
//	},50);
	editIndex = rowIndex;
	//切换按钮
	$(el).siblings().hide();
	$(el).hide();
	//显示取消按钮
	$(el).prev().show();
	//显示提交按钮
	$(el).next().show();
}

//取消编辑顾问
function cancelEdit(val,el){
	if(editIndex!=null){
		//取消编辑
		$('#opDataTable').datagrid('cancelEdit', editIndex);
		editIndex = null;
		//恢复按钮
		$(el).siblings().show();
		//隐藏取消按钮
		$(el).hide();
		//隐藏提交按钮
		$(el).next().next().hide();
	}
}

//提交信息
function submitChanges(opId,el){
	if(!validateSubmit()){
		return;
	}
	$('#opDataTable').datagrid('endEdit',editIndex);
	editIndex = null;
	//恢复按钮
	$(el).siblings().show();
	//隐藏提交按钮
	$(el).hide();
	//隐藏提交按钮
	$(el).prev().hide();

	//向后台提交更新请求
	if ($('#opDataTable').datagrid('getChanges').length) {
		var updated = $('#opDataTable').datagrid('getChanges', "updated");
		if(updated&&updated.length>0){
			var data = updated[0];
			$('#opDataTable').datagrid('loading');
			$.ajax({
				type : 'post',
				url : rootPath+"/scmgnt/op/opmodify",
				data : data,
				dataType : 'text',
				success : function() {
					$('#opDataTable').datagrid('acceptChanges');
					$('#opDataTable').datagrid('reload');
				},
				error : function(request) {
					$('#opDataTable').datagrid('loaded');
					var msg = eval("("+request.responseText+")").errorMsg;
					parent.showMsg(msg);
				}
			});
		}
		var inserted = $('#opDataTable').datagrid('getChanges', "inserted");
		if(inserted&&inserted.length>0){
			var data = inserted[0];
			$('#opDataTable').datagrid('loading');
			$.ajax({
				type : 'post',
				url : rootPath+"/scmgnt/op/opadd",
				data : data,
				dataType : 'text',
				success : function() {
					selectedRowId = data.scProductId;
					$('#opDataTable').datagrid('acceptChanges');
					$('#opDataTable').datagrid('reload');
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

//状态变更操作
function stateChange(opId,newState){
	selectedRowId = opId; 
	$.ajax({
		type : 'post',
		url : rootPath + "/scmgnt/op/modifystate",
		data : {
			opId :opId,
			state :newState,
		},
		dataType : 'text',
		success : function() {
			$('#opDataTable').datagrid('reload');
			//parent.showMsg(i18n.scmgnt.opinfo.StateChangeSuccess);
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			parent.showMsg(msg);
		}
	});
}
//调整表格列宽
function fixWidth(percent){
	return document.body.clientWidth * percent;
}

var regExp = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
//检查提交更新的内容
function validateSubmit(){
	if ($('#opDataTable').datagrid('validateRow', editIndex)){	
		//opName
		var ed = $('#opDataTable').datagrid('getEditor', {index:editIndex,field:'opName'});
		var opName = $(ed.target).textbox('getText');
		//opCode
		ed = $('#opDataTable').datagrid('getEditor', {index:editIndex,field:'opCode'});
		var opCode = $(ed.target).textbox('getText');
		//firstName
		ed = $('#opDataTable').datagrid('getEditor', {index:editIndex,field:'firstName'});
		var firstName = $(ed.target).textbox('getText');
		//lastName
		ed = $('#opDataTable').datagrid('getEditor', {index:editIndex,field:'lastName'});
		var lastName = $(ed.target).textbox('getText');
		//loginCode
		ed = $('#opDataTable').datagrid('getEditor', {index:editIndex,field:'loginCode'});
		var loginCode = $(ed.target).textbox('getText');
		//loginPasswd
		ed = $('#opDataTable').datagrid('getEditor', {index:editIndex,field:'loginPasswd'});
		var loginPasswd = $(ed.target).textbox('getText');
		//gender
		ed = $('#opDataTable').datagrid('getEditor', {index:editIndex,field:'gender'});
		var gender = $(ed.target).textbox('getText');
		//gender
		ed = $('#opDataTable').datagrid('getEditor', {index:editIndex,field:'opKind'});
		var opKind = $(ed.target).textbox('getText');
		//mobileNo
		ed = $('#opDataTable').datagrid('getEditor', {index:editIndex,field:'mobileNo'});
		var mobileNo = $(ed.target).textbox('getText');
		//officeTel
		ed = $('#opDataTable').datagrid('getEditor', {index:editIndex,field:'officeTel'});
		var officeTel = $(ed.target).textbox('getText');
		if(!opName){
			parent.showMsg(i18n.register.ChineseNameEmpty);
			return false;
		}
		if(!opCode){
			parent.showMsg(i18n.register.OpCodeEmpty);
			return false;
		}
		if(!firstName||!lastName){
			parent.showMsg(i18n.register.EnglishNameEmpty);
			return false;
		}
		if(!regExp.test(loginCode)){
			parent.showMsg(i18n.register.AccountFormatError);
			return false;
		}
		if(!loginPasswd||loginPasswd.length<4){
			parent.showMsg(i18n.register.PasswordFormatError);
			return false;
		}
		if(!gender){
			parent.showMsg(i18n.register.GenderEmpty);
			return false;
		}
		if(!opKind){
			parent.showMsg(i18n.register.OpKindEmpty);
			return false;
		}
		if(!mobileNo||!/^[0-9]{11}$/.test(mobileNo)){
			parent.showMsg(i18n.register.MobileNoFormatError);
			return false;
		}
		if(!officeTel||!/^[0-9]{7,8}$/.test(officeTel)){
			parent.showMsg(i18n.register.OfficeTelFormatError);
			return false;
		}
//		//最后设置选中的客户树的数据
//		ed = $('#opDataTable').datagrid('getEditor', {index:editIndex,field:'ccCustId'});
//		var newCcCustName = $(ed.target).combotree('getText');
//		var rowData = $('#opDataTable').datagrid('getRows')[editIndex];
//		rowData['custName'] = newCcCustName;
		return true;
	} else {
		return false;
	}
}
