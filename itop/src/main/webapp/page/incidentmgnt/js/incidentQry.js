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
qp.adviserId = null;
qp.registeMan = null;
//默认选中的数据行
var selectedDataRow = null;
//Grid对象
var inciGrid = null;

$(function(){
	//如果是用户，则显示创建事件按钮、隐藏客户选择列表
	if(opType=='USER'){
		$("#addBtn").show();
		$("#qryUserMineBtn").show();
		$("#qryOpMineBtn").hide();
	}else{
		$("#qryOpMineBtn").show();
		$("#qryUserMineBtn").hide();
	}
	bindScroll();
	//初始化下拉列表
	initDropdownLists();
	//初始化子页滑动
	initSubPage();
	//默认执行一次查询
	query();
});

//绑定滚动条滚动事件
function bindScroll(){
	$(window.document).scroll(function (){
		var scroll = $(document).scrollTop();
		$("#detailMask").css({bottom:'-'+scroll+'px' });
	});
}


//初始化查询下拉列表
function initDropdownLists(){
	//初始化客户查询条件
	var custSelURL = rootPath+'/register/custlist/get';
	custSelURL += (opType=="USER"?("?domainName="+opCode.split("@")[1]):"");
	$('#custSel').combotree({
		editable:false,
		disabled:false,
	    url:custSelURL,
	    onLoadSuccess:function(){
	    	if(opType=='USER'){
	    		$('#custSel').combotree('setValue',opCustId);
	    	}
	    },
	    onSelect:function(data){
	    	$('#registeMan').combobox('setValue',"");
	    	$('#registeMan').combobox('reload',rootPath+'/custmgnt/user/list?custId='+data.attributes.ccCustId);
	    }
	});
	//初始化顾问查询条件
	$('#adviserSel').combobox({
		multiple:true,
		separator:',',
		editable:false,
		url:rootPath+'/op/list',
	    valueField:'scOpId',
	    textField:'opName',
	    formatter:function(row){
	    	return "["+row.opCode+"] "+row.lastName+"."+row.firstName+"/"+row.opName;
	    }
	});
	//初始化登记人查询条件
	$('#registeMan').combobox({
		multiple:true,
		separator:',',
		editable:false,
	    url:(opType=='USER'?(rootPath+'/custmgnt/user/list?custId='+opCustId):null),
	    valueField:'ccUserId',
	    textField:'opName',
	    panelHeight:'auto',
	    onShowPanel:function(){
	    	if(!$('#custSel').combotree('getValue')){
	    		$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.query.QryConditionCustEmpty);
	    		return false;
	    	}
	    },
	    formatter:function(row){
	    	return row.lastName+"."+row.firstName+"/"+row.opName;
	    }
	});
}

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
	//客户
	if(opType=="USER"){
		$('#custSel').combotree('setValue',opCustId);
	}else{
		$('#custSel').combotree('clear');
	}
	//登记人
	$("#registeMan").combobox('setValue',"");
	//责任顾问
	$('#adviserSel').combobox('setValue',"");
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
	
	//执行一次查询
	query();
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
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.CommitIncidentSuccess);
			reloadData(2);
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.CommitFailure+'：'+msg);
		}
	});
}
//删除事件
function removeTicket(id){
	$.messager.confirm('Confirm',i18n.incident.mgnt.RemoveIncidentConfirm,function(r){
	    if (r){
	    	$.ajax({
	    		type : 'post',
	    		url : rootPath + "/incident/remove",
	    		data : {
	    			incidentId :id
	    		},
	    		dataType : 'text',
	    		success : function() {
	    			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.RemoveIncidentSuccess);
	    			reloadData(1);
	    		},
	    		error : function(request) {
	    			var msg = eval("("+request.responseText+")").errorMsg;
	    			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.CommitFailure+'：'+msg);
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
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.CloseIncidentSuccess);
			reloadData(8);
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.CommitFailure+'：'+msg);
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
		$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.FeedbackOptionEmpty);
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
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.FeedbackIncidentSuccess);
			$('#feedbackWin').dialog('close');
			reloadData();
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.CommitFailure+'：'+msg);
		}
	});
}

//恢复事件
function back2Proccess(id){
	$.ajax({
		type : 'post',
		url : rootPath + "/incident/back2Process",
		data : {
			incidentId :id,
		},
		dataType : 'text',
		success : function() {
			reloadData(3);
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.CommitFailure+'：'+msg);
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
		$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.MarkOptionEmpty);
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
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.MarkIncidentSuccess);
			$('#stockWin').dialog('close');
			reloadData(10);
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.CommitFailure+'：'+msg);
		}
	});
}

var exportCustSel = null;
var exportRegisteManSel = null;
var exportAdviserSel = null;
//打开导出事件报表的窗口
function openExportReportWin(){
	$('#exportWin').dialog('open');
	if(!exportCustSel){
		//初始化窗口中的组件
		var custSelURL = rootPath+'/register/custlist/get';
		custSelURL += (opType=="USER"?("?domainName="+opCode.split("@")[1]):"");
		exportCustSel = $('#exportCustSel').combotree({
			editable:false,
			disabled:false,
		    url:custSelURL,
		    onLoadSuccess:function(){
		    	if(opType=='USER'){
		    		$('#exportCustSel').combotree('setValue',opCustId);
		    	}
		    },
		    onSelect:function(data){
		    	$('#exportRegisteMan').combobox('setValue','');
		    	$('#exportRegisteMan').combobox('reload',rootPath+'/custmgnt/user/list?custId='+data.attributes.ccCustId);
		    }
		});
	}
	if(!exportRegisteManSel){
		//初始化登记人查询条件
		exportRegisteManSel = $('#exportRegisteMan').combobox({
			multiple:true,
			separator:',',
			editable:false,
		    url:(opType=='USER'?(rootPath+'/custmgnt/user/list?custId='+opCustId):null),
		    valueField:'ccUserId',
		    textField:'opName',
		    panelHeight:'auto',
		    onShowPanel:function(){
		    	if(!$('#exportCustSel').combotree('getValue')){
		    		$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.query.QryConditionCustEmpty);
		    		return false;
		    	}
		    },
		    formatter:function(row){
		    	return row.lastName+"."+row.firstName+"/"+row.opName;
		    }
		});
	}
	if(!exportAdviserSel){
		//初始化顾问查询条件
		exportAdviserSel = $('#exportAdviserSel').combobox({
			multiple:true,
			separator:',',
			editable:false,
			url:rootPath+'/op/list',
		    valueField:'scOpId',
		    textField:'opName',
		    formatter:function(row){
		    	return "["+row.opCode+"] "+row.lastName+"."+row.firstName+"/"+row.opName;
		    }
		});
	}
}
//导出事件报表
function exportReport(){
	//获取导出条件
	var expParam = {};
	//客户Id
	expParam.expCustId = $('#exportCustSel').combotree('getValue');
	if(!expParam.expCustId){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.ExportCustEmpty);
		return;
	}
	//登记人Id
	expParam.expRegisterId = $('#exportRegisteMan').combotree('getValues').join(",");
	//责任顾问Id
	expParam.expConsultantId = $('#exportAdviserSel').combotree('getValues').join(",");
	//日期范围
	expParam.expStartDate = $('#exportStartDate').datebox('getValue');
	expParam.expEndDate = $('#exportEndDate').datebox('getValue');
	if(!expParam.expStartDate||!expParam.expEndDate){
		$.messager.alert(i18n.dialog.AlertTitle,i18n.incident.mgnt.ExportDateEmpty);
		return;
	}
	//状态范围
	expParam.expStatus = [];
	$('input[name=exportStatus]').each(function(){
		expParam.expStatus.push($(this).val());
	});
	expParam.expStatus = expParam.expStatus.join(",");
	//构建导出URL地址
	var expURL = rootPath + "/incident/export?a=1";
	for(var p in expParam){
		expURL += "&"+p+"="+expParam[p];
	}
	window.frames["exportIframe"].location.href= expURL;
}
//导出重置
function exportReset(){
	//客户Id
	$('#exportCustSel').combotree('setValue','');
	//登记人Id
	$('#exportRegisteMan').combobox('setValue','');
	//责任顾问Id
	$('#exportAdviserSel').combobox('setValue','');
	//日期范围
	$('#exportStartDate').datebox('setValue','');
	$('#exportEndDate').datebox('setValue','');
	//状态范围
	$('input[name=exportStatus]').each(function(){
		$(this).removeAttr("checked");
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
	qp.adviserId = null;
	qp.registeMan = null;
	//事件系列号
	qp.incidentCode = $.trim($("#incidentCode").val());
	//事件简述
	qp.brief = $.trim($("#brief").val());
	//事件分类
	qp.classVar = $("#classVar").combobox('getValue');
	//产品线
	qp.productId = $("#prodSel").combobox('getValue');
	//责任顾问
	qp.adviserId = $("#adviserSel").combobox('getValues').join(",");
	//责任顾问
	qp.registeMan = $("#registeMan").combobox('getValues').join(",");
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
	var getCustId = $('#custSel').combotree('getValue');
	if(getCustId){
		qp.custId = getCustId;
	}else if(opType=='USER'){
		qp.custId = opCustId;
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
//查询我负责的
function queryOpMine(){
	$('#adviserSel').combobox('setValue',opId);
	query();
}
//查询我登记的
function queryUserMine(){
	$('#registeMan').combobox('setValue',opId);
	query();
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
									qp.stateVal = "2,3,4,5,8,9,91";
								}else{
									qp.stateVal = "1,2,3,4,5,8,9,91";
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
						loadMsg:i18n.loading.GridLoading,
						singleSelect:true,
						//fitColumns:true,
						remoteSort:true,
						multiSort:true,
						pagination:true,
						pageNumber:1,
						pageList:[8],
						pageSize:8,
						showPageList:false,
						frozenColumns:[[ 
			                {field:'icIncidentId',width:fixWidth(0.12),title:'',formatter:formatOperations,align:'center'},
					        {field:'incidentCode',width:fixWidth(0.10),title:i18n.incident.query.DataTitleIncidentCode},
					        {field:'brief',width:fixWidth(0.12),title:i18n.incident.query.DataTitleBrief},
					        {field:'prodName',width:fixWidth(0.12),title:i18n.incident.query.DataTitleProdName},
					        {field:'priorityVal',width:fixWidth(0.04),title:i18n.incident.query.DataTitlePriorityVal},
					        {field:'itStateVal',width:fixWidth(0.11),title:i18n.incident.query.DataTitleItStateVal}
						]],
					    columns:[[
					        {field:'complexVal',width:fixWidth(0.06),title:i18n.incident.query.DataTitleComplexVal},
					        {field:'custName',width:fixWidth(0.12),title:i18n.incident.query.DataTitleCustName},
			                {field:'classValOp',width:fixWidth(0.10),title:i18n.incident.query.DataTitleClassValOp},
					        {field:'affectValOp',width:fixWidth(0.08),title:i18n.incident.query.DataTitleAffectValOp},
					        {field:'plObjectName',width:fixWidth(0.06),title: i18n.incident.query.DataTitlePlObjectName},
					        {field:'registeTime',width:fixWidth(0.08),title:i18n.incident.query.DataTitleRegisteTime,sortable:true,formatter:dateTimeFormatterMinute},
					        {field:'scLoginName',width:fixWidth(0.06),title:i18n.incident.query.DataTitleScLoginName},
					        {field:'dealDur2',width:fixWidth(0.10),title:i18n.incident.query.DataTitlePlanFinishTime,sortable:true,formatter:dateTimeFormatterMinute},
					        {field:'finishTime',width:fixWidth(0.10),title:i18n.incident.query.DataTitleFinishTime,formatter:dateTimeFormatterMinute},
					        {field:'modifyDate',width:fixWidth(0.08),title:i18n.incident.query.DataTitleModifyDate,sortable:true,formatter:dateTimeFormatterMinute},
					        {field:'feedbackVal',width:fixWidth(0.07),title:i18n.incident.query.DataTitleFeedbackVal,formatter:formatFeedback}
					    ]],
					    onMouseOverRow:function(index,row){
					    	$("#detailMaskContent").html(row.detail);
					    	$("#detailMask").show();
					    },
					    onMouseOutRow:function(index,row){
					    	$("#detailMaskContent").empty();
					    	$("#detailMask").hide();
					    }
					});
					initDataPager();
				}else{
					$('#incidentDataTable').datagrid('load',qp);
				}
			}
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,msg);
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
		window.selectedDataRow = null;
		$('#incidentDataTable').datagrid('clearSelections');
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
    $('#exportWin').dialog({
		modal:true
	}).dialog('close');
}
//初始化表格分页条
function initDataPager(){
	var pager = $('#incidentDataTable').datagrid('getPager');
	$(pager).pagination({
		pageList:[8],
		pageSize: 8,
		showPageList:false,
		beforePageText: '第',
		afterPageText: '页共 {pages} 页',
		displayMsg: '当前显示 {from} - {to} 条记录共 {total} 条记录',
	});
	$('#incidentDataTable').datagrid({
		onLoadSuccess:function(){
			var data = $(this).datagrid("getData");
			if(data.rows.length==0){
				 //$(this).parent().find("div").filter(".datagrid-body").html("<div class='none-data-info'>"+i18n.loading.GirdDataEmpty+"</div>");
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
		buttons += "<button type='button' class='btn btn-link' onclick='commit("+val+")'>"+i18n.incident.mgnt.CommitBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='edit("+val+")'>"+i18n.incident.mgnt.EditBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='removeTicket("+val+")'>"+i18n.incident.mgnt.DeleteBtn+"</button>";
	}else if(row.itStateCode==2||row.itStateCode==3||row.itStateCode==4||row.itStateCode==5||row.itStateCode==8||row.itStateCode==9||row.itStateCode==10){
		buttons += "<button type='button' class='btn btn-link' onclick='view("+val+")'>"+i18n.incident.mgnt.ViewBtn+"</button>";
	}
	//如果是完成状态且操作员是顾问，显示关闭按钮
	if(row.itStateCode==8&&opType=="OP"){
		buttons += "<button type='button' class='btn btn-link' onclick='closeIncident("+val+")'>"+i18n.incident.mgnt.CloseBtn+"</button>";
	}
	//如果是关闭状态且操作员是客户方的IT人员，则显示归档
	if(row.itStateCode==9&&opType=="USER"&&opKind==3){
		buttons += "<button type='button' class='btn btn-link' onclick='showStockIncident("+val+")'>"+i18n.incident.mgnt.MarkBtn+"</button>";
	}
	//如果状态为完成，且当前操作员是客户方的用户，则显示评价按钮
	if(row.itStateCode==8&&opType=="USER"){
		buttons += "<button type='button' class='btn btn-link' onclick='showFeedback("+val+")'>"+i18n.incident.mgnt.EvaluateBtn+"</button>";
	}
	//如果状态为完成或关闭，且当前操作员是管理员，则显示重开事件按钮
	if((row.itStateCode==8||row.itStateCode==9)&&opType=="OP"&&opKind==1){
		buttons += "<button type='button' class='btn btn-link' onclick='back2Proccess("+val+")'>"+i18n.incident.mgnt.ReOpenBtn+"</button>";
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
	return year+"/"+(month+1)+"/"+day;
}
//格式化时间列
function dateTimeFormatterMinute(val){
	if(!val){
		return;
	}
	var date = new Date(val);
	//年
	var year = date.getFullYear();
	//月
	var month = date.getMonth();
	month = month<10?"0"+month:month;
	//日
	var day = date.getDate();
	day = day<10?"0"+day:day;
	//小时
	var hour = date.getHours();
	hour = hour<10?"0"+hour:hour;
	//分钟
	var minute = date.getMinutes();
	minute = minute<10?"0"+minute:minute;
	return year+"-"+(month+1)+"-"+day+" "+hour+":"+minute;
}
//格式化时间
function dateFormatter2(val){
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
	return year+"-"+(month+1)+"-"+day;
}
//格式化评价列
function formatFeedback(val,row){
	if(row.feedbackVal!=null){
		return row.feedbackVal;
	}else{
		return "";
	}
}

//设置客户选择下拉列表值
function setCompanyValue(value){
	$('#custSel').combotree('setValue',value);
}