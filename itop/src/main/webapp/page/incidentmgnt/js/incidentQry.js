//全局查询条件
var qp={};
//默认查询待响应事件
qp.stateVal = 2;
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
	//初始化子页滑动
	initSubPage();
	//默认执行一次查询
	query();
});

//查看事件
function view(id){
	showSubPage("incidentView.jsp?id="+id);
}
//编辑事件
function edit(id){
	showSubPage(rootPath+"/page/incidentmgnt/dtl?openFlag=m&id="+id);
} 
//新增事件
function add(){
	showSubPage(rootPath+"/page/incidentmgnt/dtl?openFlag=a");
}

//重置表单
function reset(){
	//事件系列号
	$("#incidentCode").val("");
	//事件简述
	$("#brief").val("");
	//事件分类
	$("#classVar").combobox('setValue',"");
	//产品线
	$("#prodSel").combobox('setValue',"");
	//影响度
	$("input[name=affectVar]").each(function(){
		$(this).removeAttr("checked");
	});
	//优先级
	$("input[name=priorityVar]").each(function(){
		$(this).removeAttr("checked");
	});
	//起始时间
	$("#qryStartDate").datebox('setValue','');
	//截止时间
	$("#qryEndDate").datebox('setValue','');
}
//提交事件
function commit(id){
	$.ajax({
		type : 'post',
		url : rootPath + "/incident/commit",
		data : {
			incidentId :id
		},
		dataType : 'text',
		success : function() {
			$.messager.alert('提示','提交事件成功！');
			$('#incidentDataTable').datagrid('reload');
		},
		error : function() {
			$.messager.alert('提示','提交事件失败！');
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
	    		dataType : 'text',
	    		success : function() {
	    			$.messager.alert('提示','删除事件成功！');
	    			$('#incidentDataTable').datagrid('reload');
	    		},
	    		error : function() {
	    			$.messager.alert('提示','删除事件失败！');
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
		dataType : 'text',
		success : function() {
			$.messager.alert('提示','关闭事件成功！');
			$('#incidentDataTable').datagrid('reload');
		},
		error : function() {
			$.messager.alert('提示','关闭事件失败！');
		}
	});
}
//显示评价事件的窗口
function showFeedback(id){
	$("#feedbackBtn").attr("incidentId",id);
	$('#feedbackWin').dialog('open');
}
//评价事件
function feedback(){
	var id = $("#feedbackBtn").attr("incidentId");
	var feedbackVal = null;
	var feedbackCode = null;
	$("input[name=feedbackVar]").each(function(){
		if($(this).get(0).checked){
			feedbackCode = $(this).text();
			feedbackVal = $(this).val();
		}
	});
	$.ajax({
		type : 'post',
		url : rootPath + "/incident/feedback",
		data : {
			incidentId :id,
			feedbackVal:feedbackVal,
			feedbackCode:feedbackCode
		},
		dataType : 'json',
		success : function(msg) {
			$('#feedbackWin').dialog('close');
			reloadData();
		},
		error : function() {
			$.messager.alert('提示','评价事件失败！');
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
	$("input[name=affectVar]").each(function(){
		if($(this).get(0).checked){
			affectVarArr.push($(this).val());
		}
	});
	if(affectVarArr.length!=0){
		qp.affectVar = affectVarArr.join(",");
	}
	//优先级
	var pritVarArr = [];
	$("input[name=priorityVar]").each(function(){
		if($(this).get(0).checked){
			pritVarArr.push($(this).val());
		}
	});
	if(pritVarArr.length!=0){
		qp.priorityVal = pritVarArr.join(",");
	}
	//起始时间
	qp.registerTimeBegin = $("#qryStartDate").datebox('getValue');
	//截止时间
	qp.registerTimeEnd = $("#qryEndDate").datebox('getValue');
}
//查询主方法
function query(flag){
	setQueryConditions();
	//通过点击标签查询
	if(flag&&flag==1){
		$('#incidentDataTable').datagrid({url:rootPath+'/incident/list',queryParams:qp});
	}else{
		reRenderStatusNav();
	}
}
//重新渲染状态标签
function reRenderStatusNav(status){
	$.ajax({
		type : 'get',
		url : rootPath + "/incident/statusCount",
		data : qp,
		dataType : 'json',
		success : function(msg) {
			if(msg!=null&&msg.length!=0){
				$("#statusNav").empty();
				for(var i=msg.length-1;i>=0;i--){
					if(i==0){
						$("#statusNav").append("<li role=\"presentation\" value=\""+msg[i].stateCode+"\" class=\"active\"><a href=\"#\">"+msg[i].stateVal+"("+msg[i].recordCount+")</a></li>");
						qp.stateVal = msg[i].stateCode;
					}else{
						$("#statusNav").append("<li role=\"presentation\" value=\""+msg[i].stateCode+"\" ><a href=\"#\">"+msg[i].stateVal+"("+msg[i].recordCount+")</a></li>");
					}
				}
				bindStatusToggle();
				var pagger = $('#incidentDataTable').datagrid('getPager');
				$(pagger).pagination('refresh',{
					pageNumber:1,
					pageSize:10
				});
				$('#incidentDataTable').datagrid({url:rootPath+'/incident/list',queryParams:qp});
			}
		},
		error : function() {}
	});
}
//绑定状态标签事件
function bindStatusToggle(){
	$("#statusNav li").click(function(){
		$(this).addClass("active");
		$(this).siblings().removeClass("active");
		qp.stateVal = $(this).val()=="0"?null: $(this).val();
		query(1);
	});
}
//切换状态标签
function changeStatusNav(status){
	$("#statusNav li").each(function(){
		if($(this).attr("value")==status){
			$(this).addClass("active");
			$(this).siblings().removeClass("active");
			qp.stateVal =status;
		}
	});
}
//显示子页
function showSubPage(url){
	window.scrollTo(0,0); 
	$("body").css({"overflow":"hidden"});
	$("#pageMask").show();
	$("#subPage").show();
	setTimeout(function(){
		$("#subPageIframe").attr("src",url);
	},300);
	$("#subPage").animate({right: '0px'}, "50");
}
//隐藏子页
function hideSubPage(){
	$("body").css({"overflow":"auto"});
	$("#pageMask").hide();
	$("#subPage").hide();
	$("#subPage").css({right: '-1000px'});
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
    $('#feedbackWin').dialog({
		modal:true
	}).dialog('close');
}
//初始化表格分页条
function initDataPager(){
	var pager = $('#incidentDataTable').datagrid('getPager');
	$(pager).pagination({
		pageSize: 10,
		showPageList:false,
		beforePageText: '第',
		afterPageText: '页共 {pages} 页',
		displayMsg: '当前显示 {from} - {to} 条记录共 {total} 条记录',
	});
	$('#incidentDataTable').datagrid({
		onLoadSuccess:function(){
			var data = $(this).datagrid("getData");
			if(data.rows.length==0){
				 $(this).parent().find("div").filter(".datagrid-body").html("<div class='none-data-info'>暂无数据记录</div>");
			}
		}
	});
}
//重新加载数据
function reloadData(){
	$('#incidentDataTable').datagrid('reload');
}


//格式化操作列
function formatOperations(val,row){
	var buttons = "";
	if(row.itStateCode==1||row.itStateCode==4){
		buttons += "<button type='button' class='btn btn-link' onclick='commit("+val+")'>提交</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='edit("+val+")'>编辑</button>";
	}else if(row.itStateCode==2||row.itStateCode==3||row.itStateCode==5||row.itStateCode==8){
		buttons += "<button type='button' class='btn btn-link' onclick='view("+val+")'>查看</button>";
		if(opType=="OP"){
			buttons += "<button type='button' class='btn btn-link' onclick='close("+val+")'>关闭</button>";
		}
	}else{
		buttons += "<button type='button' class='btn btn-link' onclick='view("+val+")'>查看</button>";
	}
	return buttons;
}
//格式化时间列
function dateFormatter(val,row){
	if(!val){
		return;
	}
	var date = new Date(val);
	//年
	var year = date.getFullYear();
	//月
	var month = date.getMonth();
	//日
	var day = date.getDate();
	//小时
	var hour = date.getHours();
	//分钟
	var minute = date.getMinutes();
	//秒
	var second = date.getSeconds();
	return year+"/"+(month+1)+"/"+day;
}
//格式化评价列
function formatFeedback(val,row){
	if(val!=null){
		return val;
	}else if(row.itStateCode=="8"){
		 return "<button type='button' class='btn btn-link' onclick='showFeedback("+val+")'>待评价</button>";
	}
}