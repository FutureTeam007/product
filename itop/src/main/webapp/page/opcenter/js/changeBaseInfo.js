
//初始化绑定信息
$(function(){
	baseInfoDataBind();
});

//绑定基本信息表单
function baseInfoDataBind(){
	$.ajax({
		type : 'get',
		url : rootPath + "/op/get",
		cache: false,
		data : {opId:opId},
		dataType : 'json',
		success : function(msg) {
			$("#acountNo").val(msg.loginCode);
			$("#opCode").val(msg.opCode);
			$("#chineseName").val(msg.opName);
			$("#givenName").val(msg.firstName);
			$("#familyName").val(msg.lastName);
			$("input[name=gender]").each(function(){
				if($(this).val()==msg.gender){
					$(this).attr("checked",true);
				}
			});
			$("input[name=jobRole]").each(function(){
				if($(this).val()==msg.opKind){
					$(this).attr("checked",true);
				}
			});
			$("#mobileNo").val(msg.mobileNo);
			var segs = msg.officeTel.split("-");
			$("#areaCode").val(segs[0]);
			$("#phoneNo").val(segs[1]);
		},
		error : function() {
			$.messager.alert('提示','查询信息错误,可能无法修改！');
		}
	});
}

//检查Form表单项各值
function validateModifyFormVars(){
	//取得form表单项
	var opCode = $.trim($("#opCode").val());
	var chineseName = $.trim($("#chineseName").val());
	var givenName = $.trim($("#givenName").val());
	var familyName = $.trim($("#familyName").val());
	var gender = $("input[name=gender]:checked").val();
	var jobRole = $("input[name=jobRole]:checked").val();
	var mobileNo = $.trim($("#mobileNo").val());
	var areaCode = $.trim($("#areaCode").val());
	var phoneNo = $.trim($("#phoneNo").val());
	//校验form表单项
	if(!opCode||!/^[0-9a-zA-Z]{4,}$/.test(opCode)){
		$.messager.alert('提示','工号必须是不小于4位的字母或数字');
		return false;
	}
	if(!jobRole){
		$.messager.alert('提示','请选择工作角色');
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
	if(!mobileNo||!/^[0-9]{11}$/.test(mobileNo)){
		$.messager.alert('提示','请输入11位的手机号码');
		return false;
	}
	if(!areaCode||!/^[0-9]{3,4}$/.test(areaCode)){
		$.messager.alert('提示','请输入正确的区号');
		return false;
	}
	if(!phoneNo||!/^[0-9]{7,8}$/.test(phoneNo)){
		$.messager.alert('提示','请输入正确的办公电话');
		return false;
	}
	return true;
}