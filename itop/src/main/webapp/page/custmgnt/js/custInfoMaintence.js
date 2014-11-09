
$(function(){
	//初始化上级客户树
	initSuperCustTree();
	//如果是查看，则加载客户信息
	if(custId&&custId!="null"){
		bindCustInfo();
	}
});


//初始化上级客户树
function initSuperCustTree(){
	$('#custSel').combotree({
		editable:false,
		disabled:false,
		url:rootPath+'/custmgnt/custinfo/tree',
		loadFilter: function(data){ 
			var rtnData = [{text: i18n.custmgnt.custinfo.CustTreeRoot,id:-1}];
			return rtnData.concat(data);
	    },
	    onSelect:function(data){
	    	if(data.id!=-1){
	    		$("#domainName").val(data.attributes.domainName);
	    	}else{
	    		$("#domainName").val("");
	    	}
	    }
	});
}

//加载客户信息
function bindCustInfo(){
	$.ajax({
		type : 'get',
		url : rootPath + "/custmgnt/custinfo/get",
		cache: false,
		data : {
			custId :custId
		},
		dataType : 'json',
		success : function(msg) {
			$("#custCode").val(msg.custCode);
			$("#custId").val(msg.ccCustId);
			$("#custName").val(msg.custName);
			$("#custShortName").val(msg.shortName);
			$("#custEnName").val(msg.lastName);
			if(msg.supCustId){
				$('#custSel').combotree('setValue',msg.supCustId);
			}else{
				$('#custSel').combotree('setValue',-1);
			}
			$("#domainName").val(msg.domainName);
			$("#notes").val(msg.notes);
			$("input[name=status]").each(function(){
				if($(this).val()==msg.state){
					$(this).attr("checked","checked");
				}
			});
			$("#editType").val("2");
			setFormDisable();
		},
		error : function() {
			parent.showMsg(i18n.custmgnt.custinfo.QryCustInfoError);
		}
	});
}

//检查Form表单
function validateForm(){
	var custCode = $.trim($("#custCode").val());
	var custName = $.trim($("#custName").val());
	var custShortName = $.trim($("#custShortName").val());
	var custEnName = $.trim($("#custEnName").val());
	var domainName = $.trim($("#domainName").val());
	var supCustId = $('#custSel').combotree('getValue');
	if(!custCode){
		parent.showMsg(i18n.custmgnt.custinfo.CustCodeEmptyError);
		return false;
	}
	if(!custName){
		parent.showMsg(i18n.custmgnt.custinfo.CustNameEmptyError);
		return false;
	}
	if(!custShortName){
		parent.showMsg(i18n.custmgnt.custinfo.ShortNameEmptyError);
		return false;
	}
	if(!custEnName){
		parent.showMsg(i18n.custmgnt.custinfo.EnNameEmptyError);
		return false;
	}
	if(!supCustId){
		parent.showMsg(i18n.custmgnt.custinfo.SupCustEmptyError);
		return false;
	}
	if(!domainName){
		parent.showMsg(i18n.custmgnt.custinfo.DomainNameEmptyError);
		return false;
	}
	return true;
}

//新增客户
function addNewCust(){
	resetForm();
	setFormEnable();
	$("#submitBtn").show();
	$("#backBtn").show();
	$("#addBtn").hide();
	$("#deleteBtn").hide();
	$("#editBtn").hide();
	$("#editType").val("1");
}
//编辑客户
function editCust(){
	setFormEnable();
	$("#submitBtn").show();
	$("#backBtn").show();
	$("#addBtn").hide();
	$("#deleteBtn").hide();
	$("#editBtn").hide();
	$("#editType").val("2");
}

//重置表单
function resetForm(){
	$("#custCode").val("");
	$("#custName").val("");
	$("#custShortName").val("");
	$("#custEnName").val("");
	if(custId&&custId!="null"){
		$('#custSel').combotree('setValue',custId);
		$("#domainName").val(pDomainName);
	}else{
		$('#custSel').combotree('clearValue');
		$("#domainName").val("");
	}
	$("#notes").val("");
	$("input[name=status]").each(function(){
		if($(this).val()==1){
			$(this).attr("checked","checked");
		}
	});
}

//禁用表单
function setFormDisable(){
	$("#custCode").attr("disabled","disabled");
	$("#custId").attr("disabled","disabled");
	$("#custName").attr("disabled","disabled");
	$("#custShortName").attr("disabled","disabled");
	$("#custEnName").attr("disabled","disabled");
	$('#custSel').combotree('disable');
	$("#domainName").attr("disabled","disabled");
	$("#notes").attr("disabled","disabled");
	$("input[name=status]").each(function(){
		$(this).attr("disabled","disabled");
	});
}

//启用表单
function setFormEnable(){
	$("#custCode").removeAttr("disabled");
	$("#custId").removeAttr("disabled");
	$("#custName").removeAttr("disabled");
	$("#custShortName").removeAttr("disabled");
	$("#custEnName").removeAttr("disabled");
	$('#custSel').combotree('enable');
	$("#domainName").removeAttr("disabled");
	$("#notes").removeAttr("disabled");
	$("input[name=status]").each(function(){
		$(this).removeAttr("disabled");
	});
}
//返回
function backFront(){
	window.location.reload();
}

//删除客户
function removeCust(){
	$.ajax({
		type : 'post',
		url : rootPath + "/custmgnt/custinfo/remove",
		cache: false,
		data : {
			custId :$("#custId").val()
		},
		dataType : 'text',
		success : function(msg) {
			parent.showMsg(i18n.custmgnt.custinfo.DeleteSuccess);
			parent.refreshLeftTree();
			resetForm();
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			parent.showMsg(msg);
		}
	});
}


