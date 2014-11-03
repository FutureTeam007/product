//修改密码
function changePassword(){
	var oldPasswd = $.trim($("#oldPasswd").val());
	var newPasswd = $.trim($("#newPasswd").val());
	var passwdConfirm = $.trim($("#passwdConfirm").val());
	if(!oldPasswd){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.usercenter.pwdchange.OldPasswordEmpty);
		return;
	}
	if(!newPasswd){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.usercenter.pwdchange.NewPasswordEmpty);
		return;
	}
	if(newPasswd.length<4){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.usercenter.pwdchange.NewPasswordFormatError);
		return;
	}
	if(!passwdConfirm){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.usercenter.pwdchange.ConfirmPasswordEmpty);
		return;
	}
	if(newPasswd!=passwdConfirm){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.usercenter.pwdchange.PasswordNotSame);
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
			$.messager.alert(i18n.dialog.AlertTitle,i18n.usercenter.pwdchange.ChangeSuccess);
			$("#oldPasswd").val("");
			$("#newPasswd").val("");
			$("#passwdConfirm").val("");
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,msg);
		}
	});
}