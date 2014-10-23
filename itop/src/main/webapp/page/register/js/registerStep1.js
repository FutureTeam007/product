//邮箱检查正则表达式
var regExp = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
//查询公司列表，绑定下拉框
function queryCompanyList(){
	var acountNo = $.trim($("#acountNo").val());
	//如果满足邮箱格式,开始加载列表
	if(regExp.test(acountNo)){
		var domainName = acountNo.split("@")[1];
		$("#companySel").combotree('reload',rootPath+'/register/custlist/get?domainName='+domainName);
	}
}

//获取Form表单项各值
function getRegisterFormVars(){
	//取得form表单项
	var account = $.trim($("#acountNo").val());
	var passwd = $.trim($("#passwd").val());
	var passwdConfirm = $.trim($("#passwdConfirm").val());
	var companyId = $("#companySel").combotree('getValue');
	var companyName = $("#companySel").combotree('getText');
	var chineseName = $.trim($("#chineseName").val());
	var givenName = $.trim($("#givenName").val());
	var familyName = $.trim($("#familyName").val());
	var gender = $("input[name=gender]:checked").val();
	var mobileNo = $.trim($("#mobileNo").val());
	var areaCode = $.trim($("#areaCode").val());
	var phoneNo = $.trim($("#phoneNo").val());
	//校验form表单项
	if(!regExp.test(account)){
		$.messager.alert('提示','请输入邮箱格式的登录账号');
		return false;
	}
	if(!passwd||passwd.length<4){
		$.messager.alert('提示','请输入至少4位的登录密码');
		return false;
	}
	if(passwd!=passwdConfirm){
		$.messager.alert('提示','登录密码与确认密码不一致');
		return false;
	}
	if(!companyId){
		$.messager.alert('提示','请选择您所在的公司');
		return false;
	}
	if(!chineseName){
		$.messager.alert('提示','请输入中文姓名');
		return false;
	}
	if(!givenName||!familyName){
		$.messager.alert('提示','请输入英文姓名');
		return false;
	}
	if(!gender){
		$.messager.alert('提示','请选择性别');
		return false;
	}
	if(!mobileNo||mobileNo.length<11){
		$.messager.alert('提示','请输入11位的手机号码');
		return false;
	}
	if(!areaCode||areaCode.length<2){
		$.messager.alert('提示','请输入正确的区号');
		return false;
	}
	if(!phoneNo||phoneNo.length<7){
		$.messager.alert('提示','请输入正确的办公电话');
		return false;
	}
	var fv = {};
	//取得form表单项
	fv.account = account;
	fv.passwd = passwd;
	fv.passwdConfirm = passwdConfirm;
	fv.companyId = companyId;
	fv.companyName = companyName;
	fv.chineseName = chineseName;
	fv.givenName = givenName;
	fv.familyName = familyName;
	fv.gender = gender;
	fv.mobileNo = mobileNo;
	fv.areaCode = areaCode;
	fv.phoneNo = phoneNo;
	return fv;
}

//注册提交
function registerCommit(){
	var fv = getRegisterFormVars();
	if(!fv){
		return;
	}
	//注册提交
	
	
	
	
}