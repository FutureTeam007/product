//全局查询条件
var qp={};
//默认查询待响应事件
qp.stateVar = 2;
qp.incidentCode = null;
qp.brief = null;
qp.classVar = null;
qp.prodId = null;
qp.affectVar = null;
qp.priorityVal = null;
qp.productId = null;
qp.registerTimeBegin = null;
qp.registerTimeEnd = null;

$(function(){
	//初始化分页条
	initDataPager();
	//初始化状态切换标签
	bindStatusToggle();
	//初始化子页滑动
	initSubPage();
});

//查看事件
function view(id){
	showSubPage("incidentView.jsp?id="+id);
}
//编辑事件
function edit(id){
	showSubPage("incidentDtl.jsp?openFlag=m&id="+id);
} 
//新增事件
function add(){
	showSubPage("incidentDtl.jsp?openFlag=a");
}
//提交事件
function commit(id){
	$.ajax({
		type : 'post',
		url : rootPath + "/incident/commit",
		data : {
			incidentId :id
		},
		dataType : 'json',
		success : function(msg) {
			$.messager.alert('Alert','提交事件成功！');
		},
		error : function() {
			$.messager.alert('Error','提交事件失败！');
		}
	});
}
//删除事件
function remove(){
	$.messager.confirm('Confirm','您确认要删除该事件？',function(r){
	    if (r){
	    	$.ajax({
	    		type : 'post',
	    		url : rootPath + "/incident/remove",
	    		data : {
	    			incidentId :id
	    		},
	    		dataType : 'json',
	    		success : function(msg) {
	    			$.messager.alert('Alert','删除事件成功！');
	    		},
	    		error : function() {
	    			$.messager.alert('Error','删除事件失败！');
	    		}
	    	});
	    }
	});
}
//关闭事件
function close(id){
	$.ajax({
		type : 'post',
		url : rootPath + "/incident/close",
		data : {
			incidentId :id
		},
		dataType : 'json',
		success : function(msg) {
			$.messager.alert('Alert','关闭事件成功！');
		},
		error : function() {
			$.messager.alert('Error','关闭事件失败！');
		}
	});
}

//评价事件
function feedback(id){
	$.ajax({
		type : 'post',
		url : rootPath + "/incident/feedback",
		data : {
			incidentId :id,
			feedbackVal:'',
			feedbackCode:''
		},
		dataType : 'json',
		success : function(msg) {},
		error : function() {
			$.messager.alert('Error','评价事件失败！');
		}
	});
}

//设置查询条件
function setQueryConditions(){
	//事件系列号
	qp.incidentCode = $.trim($("#incidentCode").val());
	//事件简述
	qp.brief = encodeURIComponent(encodeURI($.trim($("#brief").val())));
	//事件分类
	qp.classVar = $("#classVar").combobox('getValue');
	//产品线
	qp.productId = $("#prodSel").combobox('getValue');
	//影响度
	var affectVarArr = [];
	if($("#affectVar1").get(0).checked){
		affectVarArr.push($("#affectVar1").val());
	}
	if($("#affectVar2").get(0).checked){
		affectVarArr.push($("#affectVar2").val());
	}
	if($("#affectVar3").get(0).checked){
		affectVarArr.push($("#affectVar3").val());
	}
	if($("#affectVar4").get(0).checked){
		affectVarArr.push($("#affectVar4").val());
	}
	if(affectVarArr.length!=0){
		qp.affectVar = affectVarArr.join(",");
	}
	//优先级
	var pritVarArr = [];
	if($("#priLevel1").get(0).checked){
		pritVarArr.push($("#priLevel1").val());
	}
	if($("#priLevel2").get(0).checked){
		pritVarArr.push($("#priLevel2").val());
	}
	if($("#priLevel3").get(0).checked){
		pritVarArr.push($("#priLevel3").val());
	}
	if($("#priLevel4").get(0).checked){
		pritVarArr.push($("#priLevel4").val());
	}
	if(pritVarArr.length!=0){
		qp.priorityVal = pritVarArr.join(",");
	}
	//起始时间
	qp.registerTimeBegin = $("#qryStartDate").val();
	//截止时间
	qp.registerTimeEnd = $("#qryEndDate").val();
}
//查询主方法
function query(){
	setQueryConditions();
	$('#incidentDataTable').datagrid().load({
		queryParams:qp
	});
}
//绑定切换状态标签页事件
function bindStatusToggle(){
	$("#statusNav li").click(function(){
		$(this).addClass("active");
		$(this).siblings().removeClass("active");
		qp.stateVar = $(this).val()=="-1"?null: $(this).val();
		query();
	});
}
//显示子页
function showSubPage(url){
	window.scrollTo(0,0); 
	$("body").css({"overflow":"hidden"});
	$("#pageMask").show();
	$("#subPage").show();
	$("#subPageIframe").attr("src",url);
	$("#subPage").animate({right: '0px'}, "50");
}
//隐藏子页
function hideSubPage(){
	$("body").css({"overflow":"auto"});
	$("#pageMask").hide();
	$("#subPage").hide();
	$("#subPage").css({right: '-900px'});
	$("#subPageIframe").attr("src","");
}
//初始化子页
function initSubPage(){
    var height = (this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height;
    var width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
    if (height < 1) height = 1;
    $("#subPage").css("height", (height) + "px");
    $("#pageMask").css("height", (height) + "px");
    $("#pageMask").css("width", (width-24) + "px");
}
//初始化表格分页条
function initDataPager(){
	var pager = $('#incidentDataTable').datagrid('getPager');
	$(pager).pagination({
		pageSize: 10,
		pageList: [10,20],
		beforePageText: '第',
		afterPageText: '页共 {pages} 页',
		displayMsg: '当前显示 {from} - {to} 条记录共 {total} 条记录',
	});
}
function formatOperations(val,row){
	return "<button type='button' class='btn btn-link' onclick='view("+val+")'>查看</button><button type='button' class='btn btn-link' onclick='edit("+val+")'>编辑</button><button type='button' class='btn btn-link' onclick='close("+val+")'>关闭</button>";
}