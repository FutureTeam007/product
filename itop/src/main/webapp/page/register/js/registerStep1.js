
//邮箱检查正则表达式
var regExp = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
//查询公司列表，绑定下拉框
function queryCompanyList(){
	$("#companySel").combotree('setValue','');
	var acountNo = $.trim($("#acountNo").val());
	//如果满足邮箱格式,开始加载列表
	if(regExp.test(acountNo)){
		var domainName = acountNo.split("@")[1];
		$("#companySel").combotree('reload',rootPath+'/register/custlist/get?domainName='+domainName);
	}
}

//检查Form表单项各值
function validateRegisterFormVars(){
	//取得form表单项
	var account = $.trim($("#acountNo").val());
	var passwd = $.trim($("#passwd").val());
	var passwdConfirm = $.trim($("#passwdConfirm").val());
	var companyId = $("#companySel").combotree('getValue');
	$("#companyId").val(companyId);
	var companyName = $("#companySel").combotree('getText');
	$("#companyName").val(companyName);
	var chineseName = $.trim($("#chineseName").val());
	var givenName = $.trim($("#givenName").val());
	var familyName = $.trim($("#familyName").val());
	var gender = $("input[name=gender]:checked").val();
	var jobRole = $("input[name=jobRole]:checked").val();
	var mobileNo = $.trim($("#mobileNo").val());
	var areaCode = $.trim($("#areaCode").val());
	var phoneNo = $.trim($("#phoneNo").val());
	//校验form表单项
	if(!regExp.test(account)){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.register.AccountFormatError);
		return false;
	}
	if(!passwd||passwd.length<4){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.register.PasswordFormatError);
		return false;
	}
	if(passwd!=passwdConfirm){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.register.PasswordConfirmError);
		return false;
	}
	if(!companyId){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.register.CompanyEmpty);
		return false;
	}
	if(!jobRole){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.register.RoleEmpty);
		return false;
	}
	if(!chineseName){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.register.ChineseNameEmpty);
		return false;
	}
	if(!givenName||!familyName){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.register.EnglishNameEmpty);
		return false;
	}
	if(!gender){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.register.GenderEmpty);
		return false;
	}
	if(!mobileNo||!/^[0-9]{11}$/.test(mobileNo)){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.register.MobileNoFormatError);
		return false;
	}
	if(!areaCode||!/^[0-9]{3,4}$/.test(areaCode)){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.register.AreaCodeFormatError);
		return false;
	}
	if(!phoneNo||!/^[0-9]{7,8}$/.test(phoneNo)){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.register.OfficeTelFormatError);
		return false;
	}
	return true;
}