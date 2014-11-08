//选项卡切换链接
var navLink={};
navLink["1"]=rootPath+'/page/custmgnt/custInfoMaintence.jsp';
navLink["2"]=rootPath+'/page/custmgnt/userMgnt.jsp';
navLink["3"]=rootPath+'/page/custmgnt/prodMgnt.jsp';
navLink["4"]=rootPath+'/page/custmgnt/sloRuleMgnt.jsp';
//当前标签卡顺序
var currentLinkOrder="1";
//选中的CustId
var selectedCustId=null;

$(function(){
	initLeftTree();
	initNavCard();
	//默认加载第一个选项卡
	$('#cust-mgnt-iframe').attr('src',navLink["1"]);
});

//初始化左侧客户树
function initLeftTree(){
	$('#custSel').tree({
		editable:false,
		disabled:false,
	    url:rootPath+'/register/custlist/get',
	    loadFilter: function(data){ 
	    	return [{text: '全部客户',id:-1,children:data}];
	    },
	    onSelect:function(data){
	    	var reloadURL= navLink[currentLinkOrder]+"?custId="+data.attributes.ccCustId;
	    	$('#cust-mgnt-iframe').attr('src',reloadURL);
	    }
	});
}

//绑定切换标签卡
function initNavCard(){
	$(".nav-tabs li").click(function(){
		//切换样式
		$(this).siblings().removeClass("active");
		$(this).addClass("active");
		//重置链接
		var order = $(this).attr("order");
		var link = navLink[order];
		currentLinkOrder = order;
		link += "?custId="+selectedCustId;
    	$('#cust-mgnt-iframe').attr('src',link);
	});
	
}

//绑定切换标签卡
function showMsg(msg){
	$.messager.alert(i18n.dialog.AlertTitle,msg);
}


