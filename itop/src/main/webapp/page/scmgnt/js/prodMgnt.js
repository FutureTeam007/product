
$(function(){
	initProductTable();
});

//初始化产品表格
function initProductTable(){
	$('#prodDataTable').datagrid({
		idField:'scProductId',
	    url:rootPath+'/scmgnt/product/list',
	    method:'get',
		loadMsg:i18n.loading.GridLoading,
		singleSelect:true,
		remoteSort:false,
		pagination:false,
		fitColumns:true,
	    columns:[[
	        {field:'scProductId',width:fixWidth(0.12),title:''},
	        {field:'prodZhName',width:fixWidth(0.33),title:i18n.custmgnt.prodinfo.ProdTableTitleProdName},
	        {field:'prodEnName',width:fixWidth(0.33),title:i18n.custmgnt.prodinfo.ProdTableTitleProdName},
            {field:'prodCode',width:fixWidth(0.15),title:i18n.custmgnt.prodinfo.ProdTableTitleProdCode},
            {field:'state',width:fixWidth(0.2),title:i18n.custmgnt.prodinfo.ProdTableTitleServiceStartDate},
	    ]],
	    onLoadSuccess:function(){
//			var data = $(this).datagrid("getData");
//			if(data.rows.length!=0){
//				//增加逻辑，默认选中之前选中的行或者选中第一行
//				if(selectedRowId){
//					var pageRows = $(this).datagrid('getRows');
//					if(pageRows&&pageRows.length>0){
//						var isFind = false;
//						for(var i=0;i<pageRows.length;i++){
//							if(pageRows[i].scProductId==selectedRowId){
//								isFind = true;
//								var index = $(this).datagrid('getRowIndex',pageRows[i]);
//								$(this).datagrid('selectRow',index);
//								break;
//							}
//						}
//						if(!isFind){
//							$(this).datagrid('selectRow',0);
//						}
//					}
//				}else{
//					$(this).datagrid('selectRow',0);
//				}
//			}else{
//				$(this).parent().find("div").filter(".datagrid-body").html("<div class='ml10 mt10'>"+i18n.loading.GirdDataEmpty+"</div>");
//			}
		},
		onSelect:function(rowIndex, rowData){
//			if(rowData.scProductId){
//				selectedRowId = rowData.scProductId;
//				refreshOpData(rowData.scProductId);
//			}
		}
	});
}

//初始化服务目录表格
function initModuleTable(){
	
	
}