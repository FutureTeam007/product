var qp1={};
qp1.custId=custId;
var qp2={};
qp2.custId=custId;
var selectedRowId = null;

$(function(){
	initProductData();
	initOpData();
});


//初始化产品数据表格
function initProductData(){
	$('#prodDataTable').datagrid({
		idField:'scProductId',
	    url:rootPath+'/custmgnt/product/list',
	    queryParams:qp1,
	    method:'get',
		loadMsg:i18n.loading.GridLoading,
		singleSelect:true,
		remoteSort:false,
		pagination:false,
		fitColumns:true,
	    columns:[[
	        {field:'ccCustId',width:fixWidth(0.12),title:'',formatter:prodDataOperFormatter},
	        {field:'scProductId',width:fixWidth(0.33),title:i18n.custmgnt.prodinfo.ProdTableTitleProdName,formatter:prodNameFormatter,editor:prodEditor},
            {field:'prodCode',width:fixWidth(0.15),title:i18n.custmgnt.prodinfo.ProdTableTitleProdCode},
            {field:'serviceStartDt',width:fixWidth(0.2),title:i18n.custmgnt.prodinfo.ProdTableTitleServiceStartDate,editor:dateEditor,formatter:dateFormatter},
            {field:'serviceEndDt',width:fixWidth(0.2),title:i18n.custmgnt.prodinfo.ProdTableTitleServiceEndDate,editor:dateEditor,formatter:dateFormatter},
            {field:'state',hidden:true}
	    ]],
	    onLoadSuccess:function(){
			var data = $(this).datagrid("getData");
			if(data.rows.length!=0){
				//增加逻辑，默认选中之前选中的行或者选中第一行
				if(selectedRowId){
					var pageRows = $(this).datagrid('getRows');
					if(pageRows&&pageRows.length>0){
						var isFind = false;
						for(var i=0;i<pageRows.length;i++){
							if(pageRows[i].scProductId==selectedRowId){
								isFind = true;
								var index = $(this).datagrid('getRowIndex',pageRows[i]);
								$(this).datagrid('selectRow',index);
								break;
							}
						}
						if(!isFind){
							$(this).datagrid('selectRow',0);
						}
					}
				}else{
					$(this).datagrid('selectRow',0);
				}
			}else{
				$(this).parent().find("div").filter(".datagrid-body").html("<div class='ml10 mt10'>"+i18n.loading.GirdDataEmpty+"</div>");
			}
		},
		onSelect:function(rowIndex, rowData){
			if(rowData.scProductId){
				selectedRowId = rowData.scProductId;
				refreshOpData(rowData.scProductId);
			}
		}
	});
}

//产品编辑器
var prodEditor={
	type:'combobox',
	options:{
		method:'get',
		url:rootPath+"/custmgnt/product/list/select?custId="+custId,
		editable:false,
		required:true,
		valueField:'scProductId',
		textField:'prodName',
		panelHeight:200,
		onSelect:function(data){
			var pageRows =$('#prodDataTable').datagrid('getRows');
			if(pageRows&&pageRows.length>0){
				for(var i=0;i<pageRows.length;i++){
					if(i!=prodEditIndex&&pageRows[i].scProductId==data.scProductId){
						parent.showMsg(i18n.custmgnt.prodinfo.ProdSelDup);
						$(this).combobox('setValue',"");
						break;
					}
				}
			}
		}
	}
};
//日期编辑器
var dateEditor={
	type:'datebox',
	options:{
		editable:false,
		required:true,
		formatter:dateFormatter,
		parser:function(s){
			if (!s) return new Date();
			if((s+'').indexOf('-')!=-1){
				var ss = s.split('-');
				var y = parseInt(ss[0],10);
				var m = parseInt(ss[1],10);
				var d = parseInt(ss[2],10);
				if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
					return new Date(y,m-1,d);
				} else {
					return new Date();
				}
			}else{
				return new Date(s);
			}
		}
	}
};
//日期格式化
//格式化时间列
function dateFormatter(val){
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
	day = day<10?("0"+day):day;
	return year+"-"+(month+1)+"-"+day;
}
//产品名称格式化
function prodNameFormatter(val,row){
	return row.prodName;
}

//产品数据表格操作列
function prodDataOperFormatter(val,row,rowIndex){
	val=row.scProductId;
	if(!row.state){
		var buttons = "<button type='button' class='btn btn-link undis' onclick='prodCancelEdit("+val+",this)'>"+i18n.custmgnt.prodinfo.CancelBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='prodEdit("+val+",this,"+rowIndex+","+row.scProductId+")'>"+i18n.custmgnt.prodinfo.EditBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='prodDelete("+row.ccCustId+","+row.scProductId+")'>"+i18n.custmgnt.prodinfo.DeleteBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link undis' onclick='prodSubmitChanges("+val+",this)'>"+i18n.custmgnt.prodinfo.SubmitBtn+"</button>";
		return buttons;
	}else{
		var buttons = "<button type='button' class='btn btn-link' onclick='prodCancelNewEdit("+rowIndex+")'>"+i18n.custmgnt.prodinfo.CancelBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='prodSubmitChanges("+val+",this,"+row.state+")'>"+i18n.custmgnt.prodinfo.SubmitBtn+"</button>";
		return buttons;
	}
}

var prodEditIndex=null; 

//添加产品
function addProduct(){
	if(custLevel!="1"){
		parent.showMsg(i18n.custmgnt.prodinfo.OnlyTopCustCanAddProd);
		return;
	}
	if(prodEditIndex!=null){
		parent.showMsg(i18n.custmgnt.prodinfo.EditConflictError);
		return;
	}
	prodEditIndex = 0;
	//顶部插入一条数据
	$('#prodDataTable').datagrid('insertRow',{
		index:0,
		row:{state:'1'}
	});
	$('#prodDataTable').datagrid('selectRow', 0).datagrid('beginEdit', 0);
}

//取消新增一行的编辑
function prodCancelNewEdit(rowIndex){
	prodEditIndex = null;
	$('#prodDataTable').datagrid('deleteRow', rowIndex);
	if(selectedRowId){
		var pageRows =$('#prodDataTable').datagrid('getRows');
		if(pageRows&&pageRows.length>0){
			var isFind = false;
			for(var i=0;i<pageRows.length;i++){
				if(pageRows[i].scProductId==selectedRowId){
					isFind = true;
					var index = $('#prodDataTable').datagrid('getRowIndex',pageRows[i]);
					$('#prodDataTable').datagrid('selectRow',index);
					break;
				}
			}
			if(!isFind){
				$('#prodDataTable').datagrid('selectRow',0);
			}
		}
	}
}

//取消编辑产品
function prodCancelEdit(val,el){
	if(prodEditIndex!=null){
		//取消编辑
		$('#prodDataTable').datagrid('cancelEdit', prodEditIndex);
		prodEditIndex = null;
		//恢复按钮
		$(el).siblings().show();
		//隐藏取消按钮
		$(el).hide();
		//隐藏提交按钮
		$(el).next().next().hide();
	}
}
//编辑产品
function prodEdit(val,el,rowIndex,productId){
	if(prodEditIndex!=null){
		return;
	}
	$('#prodDataTable').datagrid('selectRow',rowIndex).datagrid('beginEdit', rowIndex);
	setTimeout(function(){
		var ed = $('#prodDataTable').datagrid('getEditor', {index:prodEditIndex,field:'scProductId'});
		$(ed.target).combobox('disable');
	},50);
	prodEditIndex = rowIndex;
	selectedRowId = productId;
	//切换按钮
	$(el).siblings().hide();
	$(el).hide();
	//显示取消按钮
	$(el).prev().show();
	//显示提交按钮
	$(el).next().next().show();
}

//删除产品
function prodDelete(pCustId,productId){
	$.ajax({
		type : 'post',
		url : rootPath + "/custmgnt/product/remove",
		data : {
			custId:pCustId,
			productId:productId
		},
		dataType : 'text',
		success : function() {
			$('#prodDataTable').datagrid('reload');
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			parent.showMsg(msg);
		}
	});
}

//提交编辑
function prodSubmitChanges(val,el,state){
	if ($('#prodDataTable').datagrid('validateRow', prodEditIndex)){	
		//最后设置选中的客户树的数据
		var ed = $('#prodDataTable').datagrid('getEditor', {index:prodEditIndex,field:'scProductId'});
		var newCcProductName = $(ed.target).combobox('getText');
		var rowData = $('#prodDataTable').datagrid('getRows')[prodEditIndex];
		rowData['prodName'] = newCcProductName;
		$('#prodDataTable').datagrid('endEdit',prodEditIndex);
		prodEditIndex = null;
		//恢复按钮
		$(el).siblings().show();
		//隐藏提交按钮
		$(el).hide();
		//隐藏提交按钮
		$(el).prev().prev().hide();
		//向后台提交更新请求
		if ($('#prodDataTable').datagrid('getChanges').length) {
			var updated = $('#prodDataTable').datagrid('getChanges', "updated");
			if(updated&&updated.length>0){
				var data = updated[0];
				$('#prodDataTable').datagrid('loading');
				$.ajax({
					type : 'post',
					url : rootPath+"/custmgnt/product/modify",
					data : data,
					dataType : 'text',
					success : function() {
						selectedRowId = data.scProductId;
						$('#prodDataTable').datagrid('acceptChanges');
						$('#prodDataTable').datagrid('reload');
					},
					error : function(request) {
						$('#prodDataTable').datagrid('loaded');
						var msg = eval("("+request.responseText+")").errorMsg;
						parent.showMsg(msg);
					}
				});
			}
			var inserted = $('#prodDataTable').datagrid('getChanges', "inserted");
			if(inserted&&inserted.length>0){
				var data = inserted[0];
				data.ccCustId = custId;
				$('#prodDataTable').datagrid('loading');
				$.ajax({
					type : 'post',
					url : rootPath+"/custmgnt/product/add",
					data : data,
					dataType : 'text',
					success : function() {
						selectedRowId = data.scProductId;
						$('#prodDataTable').datagrid('acceptChanges');
						$('#prodDataTable').datagrid('reload');
					},
					error : function(request) {
						$('#prodDataTable').datagrid('loaded');
						var msg = eval("("+request.responseText+")").errorMsg;
						parent.showMsg(msg);
					}
				});
			}
		}
	}
}

//初始化顾问数据
function initOpData(){
	$('#opDataTable').datagrid({
		idField:'scOpId',
	    method:'get',
	    queryParams:qp2,
		loadMsg:i18n.loading.GridLoading,
		singleSelect:true,
		remoteSort:false,
		pagination:false,
		fitColumns:true,
	    columns:[[
	        {field:'scProductId',width:fixWidth(0.12),title:'',formatter:opDataOperFormatter},
	        {field:'scOpId',width:fixWidth(0.33),title:i18n.custmgnt.prodinfo.OpTableTitleOpName,formatter:opNameFormatter,editor:opEditor},
            {field:'opCode',width:fixWidth(0.15),title:i18n.custmgnt.prodinfo.OpTableTitleOpCode},
            {field:'loginCode',width:fixWidth(0.2),title:i18n.custmgnt.prodinfo.OpTableTitleLoginCode},
            {field:'scJobId',width:fixWidth(0.2),title:i18n.custmgnt.prodinfo.OpTableTitleJobName,formatter:jobNameFormatter,editor:jobEditor}
	    ]]
	});
}

//顾问编辑器
var opEditor = {
	type:'combobox',
	options:{
		method:'get',
		url:rootPath+"/op/list",
		editable:false,
		required:true,
		valueField:'scOpId',
		textField:'opName',
		panelHeight:200,
		onSelect:function(data){
			var pageRows =$('#opDataTable').datagrid('getRows');
			if(pageRows&&pageRows.length>0){
				for(var i=0;i<pageRows.length;i++){
					if(i!=opEditIndex&&pageRows[i].scOpId==data.scOpId){
						parent.showMsg(i18n.custmgnt.prodinfo.OpSelDup);
						$(this).combobox('setValue',"");
						break;
					}
				}
			}
		},
	    formatter:function(data){
	    	return data.lastName+"."+data.firstName+"/"+data.opName;
	    }
	}	
};

//顾问编辑器
var jobEditor = {
	type:'combobox',
	options:{
		method:'get',
		url:rootPath+"/job/list",
		editable:false,
		required:true,
		valueField:'scJobId',
		textField:'jobName',
		panelHeight:200,
	    formatter:function(data){
	    	return data.jobName+"["+data.jobCode+"]";
	    }
	}		
};

//顾问姓名列格式化
function opNameFormatter(val,row){
	if(row.opName){
		return row.lastName+"."+row.firstName+"/"+row.opName;
	}
	return "";
}
//顾问岗位
function jobNameFormatter(val,row){
	return row.jobName;
}

//顾问数据表格操作列
function opDataOperFormatter(val,row,rowIndex){
	val=row.scProductId;
	if(!row.state){
		var buttons = "<button type='button' class='btn btn-link undis' onclick='opCancelEdit("+val+",this)'>"+i18n.custmgnt.prodinfo.CancelBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='opEdit("+val+",this,"+rowIndex+","+row.scOpId+")'>"+i18n.custmgnt.prodinfo.EditBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='opDelete("+row.ccCustId+","+row.scProductId+","+row.scOpId+")'>"+i18n.custmgnt.prodinfo.DeleteBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link undis' onclick='opSubmitChanges("+val+",this)'>"+i18n.custmgnt.prodinfo.SubmitBtn+"</button>";
		return buttons;
	}else{
		var buttons = "<button type='button' class='btn btn-link' onclick='opCancelNewEdit("+rowIndex+")'>"+i18n.custmgnt.prodinfo.CancelBtn+"</button>";
		buttons += "<button type='button' class='btn btn-link' onclick='opSubmitChanges("+val+",this,"+row.state+")'>"+i18n.custmgnt.prodinfo.SubmitBtn+"</button>";
		return buttons;
	}
}

var opEditIndex=null; 

//添加顾问
function addOp(){
	if(custLevel!="1"){
		parent.showMsg(i18n.custmgnt.prodinfo.OnlyTopCustCanAddConsultant);
		return;
	}
	if(opEditIndex!=null){
		parent.showMsg(i18n.custmgnt.prodinfo.EditConflictError);
		return;
	}
	opEditIndex = 0;
	//顶部插入一条数据
	$('#opDataTable').datagrid('insertRow',{
		index:0,
		row:{state:'1'}
	});
	$('#opDataTable').datagrid('selectRow', 0).datagrid('beginEdit', 0);
}
//取消新插入的数据
function opCancelNewEdit(rowIndex){
	opEditIndex = null;
	$('#opDataTable').datagrid('deleteRow', rowIndex);
}
//取消编辑顾问
function opCancelEdit(val,el){
	if(opEditIndex!=null){
		//取消编辑
		$('#opDataTable').datagrid('cancelEdit', opEditIndex);
		opEditIndex = null;
		//恢复按钮
		$(el).siblings().show();
		//隐藏取消按钮
		$(el).hide();
		//隐藏提交按钮
		$(el).next().next().hide();
	}
}

//编辑顾问
function opEdit(val,el,rowIndex,pOpId){
	if(opEditIndex!=null){
		return;
	}
	$('#opDataTable').datagrid('selectRow',rowIndex).datagrid('beginEdit', rowIndex);
	setTimeout(function(){
		var ed = $('#opDataTable').datagrid('getEditor', {index:opEditIndex,field:'scOpId'});
		$(ed.target).combobox('disable');
	},50);
	opEditIndex = rowIndex;
	//切换按钮
	$(el).siblings().hide();
	$(el).hide();
	//显示取消按钮
	$(el).prev().show();
	//显示提交按钮
	$(el).next().next().show();
}

//删除顾问
function opDelete(pCustId,productId,pOpId){
	$.ajax({
		type : 'post',
		url : rootPath + "/custmgnt/product/opRemove",
		data : {
			custId:pCustId,
			productId:productId,
			opId:pOpId
		},
		dataType : 'text',
		success : function() {
			$('#opDataTable').datagrid('reload');
		},
		error : function(request) {
			var msg = eval("("+request.responseText+")").errorMsg;
			parent.showMsg(msg);
		}
	});
}

//提交顾问
function opSubmitChanges(val,el){
	if ($('#opDataTable').datagrid('validateRow', opEditIndex)){	
		$('#opDataTable').datagrid('endEdit',opEditIndex);
		opEditIndex = null;
		//恢复按钮
		$(el).siblings().show();
		//隐藏提交按钮
		$(el).hide();
		//隐藏提交按钮
		$(el).prev().prev().hide();
		//向后台提交更新请求
		if ($('#opDataTable').datagrid('getChanges').length) {
			var updated = $('#opDataTable').datagrid('getChanges', "updated");
			if(updated&&updated.length>0){
				var data = updated[0];
				$('#opDataTable').datagrid('loading');
				$.ajax({
					type : 'post',
					url : rootPath+"/custmgnt/product/opModify",
					data : data,
					dataType : 'text',
					success : function() {
						$('#opDataTable').datagrid('acceptChanges');
						$('#opDataTable').datagrid('reload');
					},
					error : function(request) {
						$('#opDataTable').datagrid('loaded');
						var msg = eval("("+request.responseText+")").errorMsg;
						parent.showMsg(msg);
					}
				});
			}
			var inserted = $('#opDataTable').datagrid('getChanges', "inserted");
			if(inserted&&inserted.length>0){
				var data = inserted[0];
				data.ccCustId = custId;
				var selProdData = $('#prodDataTable').datagrid('getSelected');
				if(!selProdData){
					return;
				}
				data.scProductId = selProdData.scProductId;
				$('#opDataTable').datagrid('loading');
				$.ajax({
					type : 'post',
					url : rootPath+"/custmgnt/product/opAdd",
					data : data,
					dataType : 'text',
					success : function() {
						selectedRowId = data.scProductId;
						$('#opDataTable').datagrid('acceptChanges');
						$('#opDataTable').datagrid('reload');
					},
					error : function(request) {
						$('#opDataTable').datagrid('loaded');
						var msg = eval("("+request.responseText+")").errorMsg;
						parent.showMsg(msg);
					}
				});
			}
		}
	}
}

//重新加载顾问数据
function refreshOpData(productId){
	qp2.productId = productId;
	$('#opDataTable').datagrid('load',rootPath+"/custmgnt/product/oplist");
}

//调整表格列宽
function fixWidth(percent){
	return document.body.clientWidth * percent;
}
