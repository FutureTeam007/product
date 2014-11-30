var qp={};
qp.custId = custId;
//Grid默认选中的数据
var selectedRowId = null;

$(function(){
	initUserDataGrid();
});


//查询
function query(){
	editIndex = null;
	selectedRowId = null;
	qp.userName = $.trim($("#userName").val());
	qp.userCode = $.trim($("#userCode").val());
	$('#userDataTable').datagrid('load',qp);
}

//重置
function reset(){
	$("#userName").val("");
	$("#userCode").val("");
	qp.userName = null;
	qp.userCode = null;
	selectedRowId = null;
	editIndex = null;
	$('#userDataTable').datagrid('load',qp);
}

//初始化用户数据表格
function initUserDataGrid(){
	$('#userDataTable').datagrid({
		idField:'ccUserId',
	    url:rootPath+'/custmgnt/user/list/all',
	    queryParams:qp,
	    method:'get',
		loadMsg:i18n.loading.GridLoading,
		singleSelect:true,
		remoteSort:false,
		pagination:true,
		pageNumber:1,
		pageList:[15],
		pageSize:15,
		showPageList:false,
		frozenColumns:[[ 
		     {field:'ccUserId',width:fixWidth(0.14),title:i18n.custmgnt.userinfo.TableTitleOperations,formatter:operationsFormatter}
		]],
	    columns:[[
	        {field:'state',width:fixWidth(0.07),title:i18n.custmgnt.userinfo.TableTitleUserState,formatter:userStateFormatter},
	        {field:'opName',width:fixWidth(0.11),title:i18n.custmgnt.userinfo.TableTitleUserName,editor:{type:'textbox'}},
            {field:'firstName',width:fixWidth(0.08),title:i18n.custmgnt.userinfo.TableTitleFirstName,editor:{type:'textbox'}},
	        {field:'lastName',width:fixWidth(0.08),title:i18n.custmgnt.userinfo.TableTitleLastName,editor:{type:'textbox'}},
	        {field:'ccCustId',width:fixWidth(0.15),title:i18n.custmgnt.userinfo.TableTitleCustName,editor:custEditor,formatter:custFormatter},
	        {field:'loginCode',width:fixWidth(0.15),title:i18n.custmgnt.userinfo.TableTitleUserCode,editor:{type:'textbox'}},
	        {field:'loginPasswd',width:fixWidth(0.08),title:i18n.custmgnt.userinfo.TableTitleUserPasswd,editor:{type:'textbox'}},
	        {field:'gender',width:fixWidth(0.06),title:i18n.custmgnt.userinfo.TableTitleGender,formatter:genderFormatter,editor:genderEditor},
	        {field:'opKind',width:fixWidth(0.10),title:i18n.custmgnt.userinfo.TableTitleUserType,formatter:userTypeFormatter,editor:typeEditor},
	        {field:'mobileNo',width:fixWidth(0.10),title:i18n.custmgnt.userinfo.TableTitleMobileNo,editor:{type:'numberbox'}},
	        {field:'officeTel',width:fixWidth(0.10),title:i18n.custmgnt.userinfo.TableTitleOfficeTel,editor:{type:'textbox'}}
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
//客户编辑器
var custEditor={
	type:'combotree',
	options:{
		editable:false,
		panelHeight:200,
	    url:rootPath+'/custmgnt/custinfo/tree',
		required:true
	}
};
//性别编辑器
var genderEditor={
	type:'combobox',
	options:{
		mode:'local',
		editable:false,
		data:[{
		    "gender":1,
		    "genderName":i18n.custmgnt.userinfo.GenderMr
		},{
		    "gender":2,
		    "genderName":i18n.custmgnt.userinfo.GenderMs
		}],
		valueField:'gender',
		textField:'genderName',
		panelHeight:'auto',
		required:true
	}
};
//类型编辑器
var typeEditor={
	type:'combobox',
	options:{
		mode:'local',
		editable:false,
		data:[{
		    "opKind":2,
		    "opKindName":i18n.custmgnt.userinfo.RoleBusiness
		},{
		    "opKind":3,
		    "opKindName":i18n.custmgnt.userinfo.RoleIT
		}],
		valueField:'opKind',
		textField:'opKindName',
		panelHeight:'auto',
		required:true
	}
};
//性别列格式化
function custFormatter(val,row){
	return row.custName;
}
//性别列格式化
function genderFormatter(val){
	if(!val){
		return "";
	}
	if(val==1){
		return i18n.custmgnt.userinfo.GenderMr;
	}else if(val==2){
		return i18n.custmgnt.userinfo.GenderMs;
	}
	return i18n.custmgnt.userinfo.InfoUnknown;
}
//用户类型列格式化
function userTypeFormatter(val){
	if(!val){
		return "";
	}
	if(val==2){
		return i18n.custmgnt.userinfo.RoleBusiness;
	}else if(val==3){
		return i18n.custmgnt.userinfo.RoleIT;
	}
	return i18n.custmgnt.userinfo.InfoUnknown;
}
//用户状态格式化列
function userStateFormatter(val){
	if(val==null){
		return "";
	}
	if(val==1){
		return i18n.custmgnt.userinfo.StateNormal;
	}else if(val==0){
		return "<font color='red'>"+i18n.custmgnt.userinfo.StateLeave+"</font>";
	}else if(val==2){
		return "<font color='red'>"+i18n.custmgnt.userinfo.StateLock+"</font>";
	}
	return i18n.custmgnt.userinfo.InfoUnknown;
}
//操作列格式化
function operationsFormatter(val,row,rowIndex){
	var buttons = "";
	if(row.state==1){
		buttons += "<button type='button' class='btn btn-link' onclick='stateChange("+val+",0)'>"+i18n.custmgnt.userinfo.OperLeaveBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='stateChange("+val+",2)'>"+i18n.custmgnt.userinfo.OperLockBtn+"</button>";
	}else if(row.state==0){
		buttons += "<button type='button' class='btn btn-link' onclick='stateChange("+val+",1)'>"+i18n.custmgnt.userinfo.OperUnLeaveBtn+"</button>";
	}else if(row.state==2){
		buttons += "<button type='button' class='btn btn-link' onclick='stateChange("+val+",1)'>"+i18n.custmgnt.userinfo.OperUnLockBtn+"</button>";
	}
	buttons += "<button type='button' class='btn btn-link undis' onclick='cancelEdit("+val+",this)'>"+i18n.custmgnt.userinfo.OperCancelEditBtn+"</button>";
	buttons += "<button type='button' class='btn btn-link' onclick='edit("+val+",this,"+rowIndex+")'>"+i18n.custmgnt.userinfo.OperEditBtn+"</button>";
	buttons += "<button type='button' class='btn btn-link undis' onclick='submitChanges("+val+",this)'>"+i18n.custmgnt.userinfo.OperSubmitBtn+"</button>";
	return buttons;
}

//调整表格列宽
function fixWidth(percent){
	return document.body.clientWidth * percent;
}

//状态变更操作
function stateChange(userId,newState){
	selectedRowId = userId; 
	$.ajax({
		type : 'post',
		url : rootPath + "/custmgnt/user/modifyState",
		data : {
			userId :userId,
			state :newState,
		},
		dataType : 'text',
		success : function() {
			$('#userDataTable').datagrid('reload');
			//parent.showMsg(i18n.custmgnt.userinfo.StateChangeSuccess);
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			parent.showMsg(msg);
		}
	});
}

var editIndex = null;
//编辑用户
function edit(userId,el,rowIndex){
	if(editIndex!=null){
		return;
	}
	$('#userDataTable').datagrid('selectRow',rowIndex).datagrid('beginEdit', rowIndex);
	editIndex = rowIndex;
	//切换按钮
	$(el).siblings().hide();
	$(el).hide();
	//显示取消按钮
	$(el).prev().show();
	//显示提交按钮
	$(el).next().show();
}
//取消编辑
function cancelEdit(userId,el,rowIndex){
	if(editIndex!=null){
		//取消编辑
		$('#userDataTable').datagrid('cancelEdit', editIndex);
		editIndex = null;
		//恢复按钮
		$(el).siblings().show();
		//隐藏取消按钮
		$(el).hide();
		//隐藏提交按钮
		$(el).next().next().hide();
	}
}
var regExp = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
//检查提交更新的内容
function validateSubmit(){
	if ($('#userDataTable').datagrid('validateRow', editIndex)){	
		//opName
		var ed = $('#userDataTable').datagrid('getEditor', {index:editIndex,field:'opName'});
		var opName = $(ed.target).textbox('getText');
		//firstName
		ed = $('#userDataTable').datagrid('getEditor', {index:editIndex,field:'firstName'});
		var firstName = $(ed.target).textbox('getText');
		//lastName
		ed = $('#userDataTable').datagrid('getEditor', {index:editIndex,field:'lastName'});
		var lastName = $(ed.target).textbox('getText');
		//loginCode
		ed = $('#userDataTable').datagrid('getEditor', {index:editIndex,field:'loginCode'});
		var loginCode = $(ed.target).textbox('getText');
		//loginPasswd
		ed = $('#userDataTable').datagrid('getEditor', {index:editIndex,field:'loginPasswd'});
		var loginPasswd = $(ed.target).textbox('getText');
		//mobileNo
		ed = $('#userDataTable').datagrid('getEditor', {index:editIndex,field:'mobileNo'});
		var mobileNo = $(ed.target).textbox('getText');
		//officeTel
		ed = $('#userDataTable').datagrid('getEditor', {index:editIndex,field:'officeTel'});
		var officeTel = $(ed.target).textbox('getText');
		if(!opName){
			parent.showMsg(i18n.register.ChineseNameEmpty);
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
		if(!mobileNo||!/^[0-9]{11}$/.test(mobileNo)){
			parent.showMsg(i18n.register.MobileNoFormatError);
			return false;
		}
		if(!officeTel||!/^[0-9]{7,8}$/.test(officeTel)){
			parent.showMsg(i18n.register.OfficeTelFormatError);
			return false;
		}
		//最后设置选中的客户树的数据
		ed = $('#userDataTable').datagrid('getEditor', {index:editIndex,field:'ccCustId'});
		var newCcCustName = $(ed.target).combotree('getText');
		var rowData = $('#userDataTable').datagrid('getRows')[editIndex];
		rowData['custName'] = newCcCustName;
		return true;
	} else {
		return false;
	}
}

//提交信息
function submitChanges(userId,el){
	if(!validateSubmit()){
		return;
	}
	$('#userDataTable').datagrid('endEdit',editIndex);
	editIndex = null;
	//恢复按钮
	$(el).siblings().show();
	//隐藏提交按钮
	$(el).hide();
	//隐藏提交按钮
	$(el).prev().prev().hide();
	
	//向后台提交更新请求
	if ($('#userDataTable').datagrid('getChanges').length) {
		var updated = $('#userDataTable').datagrid('getChanges', "updated");
		if(updated&&updated.length>0){
			var data = updated[0];
			$('#userDataTable').datagrid('loading');
			$.ajax({
				type : 'post',
				url : rootPath + "/custmgnt/user/modifyuserinfo",
				data : data,
				dataType : 'text',
				success : function() {
					$('#userDataTable').datagrid('acceptChanges');
					$('#userDataTable').datagrid('reload');
					parent.showMsg(i18n.custmgnt.userinfo.StateChangeSuccess);
				},
				error : function(request) {
					$('#userDataTable').datagrid('loaded');
					var msg = eval("("+request.responseText+")").errorMsg;
					parent.showMsg(msg);
				}
			});
		}
	}
}
