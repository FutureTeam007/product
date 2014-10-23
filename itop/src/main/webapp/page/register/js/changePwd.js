//修改密码
function changePassword(){
	var oldPasswd = $.trim($("#oldPasswd").val());
	var newPasswd = $.trim($("#newPasswd").val());
	var passwdConfirm = $.trim($("#passwdConfirm").val());
	if(!oldPasswd){
		$.messager.alert('提示','请输入旧密码');
		return;
	}
	if(!newPasswd){
		$.messager.alert('提示','请输入新密码');
		return;
	}
	if(newPasswd.length<3){
		$.messager.alert('提示','密码位数不能小于3位');
		return;
	}
	if(!passwdConfirm){
		$.messager.alert('提示','请输入确认密码');
		return;
	}
	if(newPasswd!=passwdConfirm){
		$.messager.alert('提示','新密码和确认密码不一致');
		return;
	}
	
	$.ajax({
		type : 'post',
		url : rootPath + (opType=="USER"?"/custmgnt/user/changepwd":"/op/changepwd"),
		data : {
			oldPassword:oldPasswd,
			newPassword:newPasswd
		},
		dataType : 'text',
		success : function() {
			$.messager.alert('提示','修改成功,新密码下次登录生效');
			$("#oldPasswd").val("");
			$("#newPasswd").val("");
			$("#passwdConfirm").val("");
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert('提示','修改失败：'+msg);
		}
	});
}