//全局查询条件
var qp={};
qp.stateVal = (opType=="USER"?1:2);
qp.incidentCode = null;
qp.brief = null;
qp.classVar = null;
qp.prodId = null;
qp.affectVar = null;
qp.priorityVal = null;
qp.productId = null;
qp.registerTimeBegin = null;
qp.registerTimeEnd = null;
qp.custId = null;
//默认选中的数据行
var selectedDataRow = null;
//Grid对象
var inciGrid = null;

$(function(){
	//如果是用户，则显示创建事件按钮、隐藏客户选择列表
	if(opType=='USER'){
		$("#addBtn").show();
		//$("#custSel").hide();
		//$("#custSelLabel").hide();
	}
	//初始化客户查询条件
	$('#custSel').combotree({
		editable:false,
		disabled:(opType=='USER'?true:false),
	    url:rootPath+'/register/custlist/get',
	    onLoadSuccess:function(){
	    	if(opType=='USER'){
	    		$('#custSel').combotree('setValue',opCustId);
	    	}
	    }
	});
	//初始化子页滑动
	initSubPage();
	//默认执行一次查询
	query();
});

//查看事件
function view(id){
	showSubPage(rootPath+"/page/incidentmgnt/view?id="+id);
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
	//选择客户
	$('#custSel').combotree('clear');
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
			reloadData(2);
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert('提示','提交错误：'+msg);
		}
	});
}
//删除事件
function remove(id){
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
	    			reloadData(1);
	    		},
	    		error : function(request) {
	    			var msg = eval("("+request.responseText+")").errorMsg;
	    			$.messager.alert('提示','删除错误：'+msg);
	    		}
	    	});
	    }
	});
}
//关闭事件
function closeIncident(id){
	$.ajax({
		type : 'post',
		url : rootPath + "/incident/close",
		data : {
			incidentId :id
		},
		dataType : 'text',
		success : function() {
			$.messager.alert('提示','关闭事件成功！');
			reloadData(8);
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert('提示','关闭错误：'+msg);
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
	var el = $("input[name=feedbackVar]:checked");
	var feedbackVal = el.attr("text");
	var feedbackCode =el.val();
	if(!feedbackVal){
		$.messager.alert('提示','请选择一个评价值！');
		return;
	}
	$.ajax({
		type : 'post',
		url : rootPath + "/incident/feedback",
		data : {
			incidentId :id,
			feedbackVal:feedbackVal,
			feedbackCode:feedbackCode
		},
		dataType : 'text',
		success : function() {
			$.messager.alert('提示','评价成功！');
			$('#feedbackWin').dialog('close');
			reloadData();
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert('提示','评价错误：'+msg);
		}
	});
}

//显示归档窗口
function showStockIncident(id){
	$("#stockBtn").attr("incidentId",id);
	$('#stockWin').dialog('open');
}
//归档事件
function stockIncident(){
	var id = $("#stockBtn").attr("incidentId");
	var stockFlags = [];
	$("input[name=stockVar]:checked").each(function(){
		stockFlags.push($(this).val());
	});
	if(stockFlags.length==0){
		$.messager.alert('提示','请至少为事件打一个标记');
		return;
	}
	$.ajax({
		type : 'post',
		url : rootPath + "/incident/stock",
		data : {
			incidentId :id,
			stockVar:stockFlags.join(",")
		},
		dataType : 'text',
		success : function() {
			$.messager.alert('提示','归档成功！');
			$('#stockWin').dialog('close');
			reloadData(10);
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert('提示','归档错误：'+msg);
		}
	});
}

//设置查询条件
function setQueryConditions(){
	qp.incidentCode = null;
	qp.brief = null;
	qp.classVar = null;
	qp.productId = null;
	qp.affectVar = null;
	qp.priorityVal = null;
	qp.registerTimeBegin = null;
	qp.registerTimeEnd = null;
	qp.custId = null;
	//事件系列号
	qp.incidentCode = $.trim($("#incidentCode").val());
	//事件简述
	qp.brief = $.trim($("#brief").val());
	//事件分类
	qp.classVar = $("#classVar").combobox('getValue');
	//产品线
	qp.productId = $("#prodSel").combobox('getValue');
	//影响度
	var affectVarArr = [];
	$("input[name=affectVar]:checked").each(function(){
		affectVarArr.push($(this).val());
	});
	if(affectVarArr.length!=0){
		qp.affectVar = affectVarArr.join(",");
	}
	//优先级
	var pritVarArr = [];
	$("input[name=priorityVar]:checked").each(function(){
		pritVarArr.push($(this).val());
	});
	if(pritVarArr.length!=0){
		qp.priorityVal = pritVarArr.join(",");
	}
	//起始时间
	qp.registerTimeBegin = $("#qryStartDate").datebox('getValue');
	//截止时间
	qp.registerTimeEnd = $("#qryEndDate").datebox('getValue');
	//设置客户ID
	if(opType=='USER'){
		qp.custId = opCustId;
	}else{
		qp.custId = $('#custSel').combotree('getValue');
	}
}
//查询主方法
function query(flag){
	selectedDataRow = null;
	setQueryConditions();
	//通过点击标签查询
	if(flag&&flag==1){
		qp.r = new Date();
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
		cache: false,  
		data : qp,
		dataType : 'json',
		success : function(msg) {
			if(msg!=null&&msg.length!=0){
				$("#statusNav").empty();
				//如果传递了status，加载完直接选中该status值对应的标签
				if(status){
					for(var i=msg.length-1;i>=0;i--){
						if(status==msg[i].stateCode){
							$("#statusNav").append("<li role=\"presentation\" value=\""+msg[i].stateCode+"\" class=\"active\"><a href=\"#\">"+msg[i].stateVal+"("+msg[i].recordCount+")</a></li>");
							if(msg[i].stateCode==-1){
								if(opType=="OP"){
									qp.stateVal = "2,3,4,5,8,9,10";
								}else{
									qp.stateVal = "1,2,3,4,5,8,9,10";
								}
							}else{
								qp.stateVal = msg[i].stateCode;
							}
						}else{
							$("#statusNav").append("<li role=\"presentation\" value=\""+msg[i].stateCode+"\" ><a href=\"#\">"+msg[i].stateVal+"("+msg[i].recordCount+")</a></li>");
						}
					}
				}
				//否则如果客户点击了查询按钮进行查询
				else {
					for(var i=msg.length-1;i>=0;i--){
						if(qp.stateVal==msg[i].stateCode||((qp.stateVal+"").indexOf(",")!=-1&&msg[i].stateCode==-1)){
							$("#statusNav").append("<li role=\"presentation\" value=\""+msg[i].stateCode+"\" class=\"active\"><a href=\"#\">"+msg[i].stateVal+"("+msg[i].recordCount+")</a></li>");
						}else{
							$("#statusNav").append("<li role=\"presentation\" value=\""+msg[i].stateCode+"\" ><a href=\"#\">"+msg[i].stateVal+"("+msg[i].recordCount+")</a></li>");
						}
					}
				}
				bindStatusToggle();
				if(inciGrid!=null){
					//取得当前选中数据行的主键ID
					var rows = $("#incidentDataTable").datagrid('getSelections');
					if(rows&&rows.length!=0){
						selectedDataRow = rows[0].icIncidentId;
					}
				}
				qp.r = new Date();
				//如果没设置状态，则说明是普通查询，需重新从第一页加载表格；如果设置了状态，说明只是刷新nav状态导航并刷新当前页
				if(inciGrid==null){
					inciGrid = $('#incidentDataTable').datagrid({
						idField:'icIncidentId',
					    url:rootPath+'/incident/list',
					    queryParams:qp,
					    method:'get',
						loadMsg:'数据加载中，请稍后……',
						singleSelect:true,
						fitColumns:true,
						remoteSort:true,
						multiSort:true,
						pagination:true,
						pageNumber:1,
						pageSize:10,
						showPageList:false,
					    columns:[[
					        {field:'icIncidentId',width:fixWidth(0.09),title:'',formatter:formatOperations,align:'center'},
					        {field:'incidentCode',width:fixWidth(0.1),title:'事件序列号'},
					        {field:'brief',width:fixWidth(0.11),title:'事件简述'},
					        {field:'prodName',width:fixWidth(0.08),title:'产品线'},
					        {field:'classValOp',width:fixWidth(0.05),title:'类别'},
					        {field:'affectValOp',width:fixWidth(0.04),title:'影响度'},
					        {field:'priorityVal',width:fixWidth(0.04),title:'优先级'},
					        {field:'itStateVal',width:fixWidth(0.05),title:'状态'},
					        {field:'plObjectName',width:fixWidth(0.09),title:'登记人'},
					        {field:'registeTime',width:fixWidth(0.08),title:'登记时间',sortable:true,formatter:dateFormatter},
					        {field:'scLoginName',width:fixWidth(0.09),title:'责任顾问'},
					        {field:'modifyDate',width:fixWidth(0.08),title:'最近更新时间',sortable:true,formatter:dateFormatter},
					        {field:'finishTime',width:fixWidth(0.06),title:'完成时间',formatter:dateFormatter},
					        {field:'feedbackVal',width:fixWidth(0.05),title:'满意度',formatter:formatFeedback}
					    ]]
					});
					initDataPager();
				}else{
					$('#incidentDataTable').datagrid('load',qp);
				}
			}
		},
		error : function() {
			$.messager.alert('提示','登录已超时，请重新登录!');
			location.href=rootPath+'/login.jsp';
		}
	});
}
//调整表格列宽
function fixWidth(percent){
	return document.body.clientWidth * percent;
}
//绑定状态标签事件
function bindStatusToggle(){
	$("#statusNav li").click(function(){
		$(this).addClass("active");
		$(this).siblings().removeClass("active");
		qp.stateVal = ($(this).val()=="0"||$(this).val()=="-1")?null: $(this).val();
		var status = qp.stateVal==null?-1:qp.stateVal;
		selectedDataRow = null;
		reRenderStatusNav(status);
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
	},350);
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
    $('#stockWin').dialog({
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
			}else{
				//增加逻辑，默认选中之前选中的行或者选中第一行
				if(selectedDataRow){
					var pageRows = $('#incidentDataTable').datagrid('getRows');
					if(pageRows&&pageRows.length>0){
						for(var i=0;i<pageRows.length;i++){
							if(pageRows[i].icIncidentId==selectedDataRow){
								var index = $('#incidentDataTable').datagrid('getRowIndex',pageRows[i]);
								$('#incidentDataTable').datagrid('selectRow',index);
							}
						}
					}
				}else{
					$('#incidentDataTable').datagrid('selectRow',0);
				}
			}
		}
	});
}
//重新加载数据
function reloadData(status){
	reRenderStatusNav(status);
}


//格式化操作列
function formatOperations(val,row){
	var buttons = "";
	if(row.itStateCode==1&&row.plObjectId==opId){
		buttons += "<button type='button' class='btn btn-link' onclick='commit("+val+")'>提交</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='edit("+val+")'>编辑</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='remove("+val+")'>删除</button>";
	}else if(row.itStateCode==2||row.itStateCode==3||row.itStateCode==4||row.itStateCode==5||row.itStateCode==8||row.itStateCode==9){
		buttons += "<button type='button' class='btn btn-link' onclick='view("+val+")'>查看</button>";
	}
	//如果是完成状态且操作员是顾问，显示关闭按钮
	if(row.itStateCode==8&&opType=="OP"){
		buttons += "<button type='button' class='btn btn-link' onclick='closeIncident("+val+")'>关闭</button>";
	}
	//如果是关闭状态且操作员是客户方的IT人员，则显示归档
	if(row.itStateCode==9&&opType=="USER"&&opKind==3){
		buttons += "<button type='button' class='btn btn-link' onclick='showStockIncident("+val+")'>归档</button>";
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
	if(row.feedbackVal!=null){
		return row.feedbackVal;
	}else if(row.itStateCode=="8"&&opType=="USER"){
		 return "<button type='button' class='btn btn-link' onclick='showFeedback("+row.icIncidentId+")'>待评价</button>";
	}
}