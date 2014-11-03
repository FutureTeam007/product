
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
			$.messager.alert(i18n.dialog.AlertTitle,i18n.opcenter.profile.QueryInfoError);
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
		$.messager.alert(i18n.dialog.AlertTitle,i18n.opcenter.profile.OpCodeFormatError);
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