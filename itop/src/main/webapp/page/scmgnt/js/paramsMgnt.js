
$(function(){
	loadParamSelect();
});

function loadParamSelect(){
	$.ajax({
		type : 'get',
		url : rootPath+"/param/select",
		dataType : 'json',
		success : function(response) {
			for(var i=0;i<response.length;i++){
				$("<button type='button' class='btn btn-primary btn-sm btn-outline param-select' onclick='loadParamList(this,\""+response[i].paramKindCode+"\")'>"+response[i].paramKind+"</button>").appendTo("#paramList");
			}
		},
		error : function(response) {
			var msg = response.errorMsg;
			$.messager.alert(i18n.dialog.AlertTitle,msg);
		}
	});
}

//加载参数值列表
function loadParamList(el,kindCode){
	$(el).removeClass("btn-outline");
	$(el).siblings().addClass("btn-outline");
	$('#prodDataTable').datagrid({
		idField:'scProductId',
	    url:rootPath+'/param/valuelist',
	    method:'get',
		loadMsg:i18n.loading.GridLoading,
		singleSelect:true,
		remoteSort:false,
		pagination:false,
		fitColumns:true,
	    columns:[[
	        {field:'scParamId1',width:fixWidth(0.12),title:''},
	        {field:'paramKind',width:fixWidth(0.33),title:i18n.custmgnt.prodinfo.ProdTableTitleProdName},
	        {field:'paramKindCode',width:fixWidth(0.33),title:i18n.custmgnt.prodinfo.ProdTableTitleProdName},
            {field:'paramCode',width:fixWidth(0.15),title:i18n.custmgnt.prodinfo.ProdTableTitleProdCode},
            {field:'paramValueZh',width:fixWidth(0.2),title:i18n.custmgnt.prodinfo.ProdTableTitleServiceStartDate},
            {field:'paramValueEn',width:fixWidth(0.2),title:i18n.custmgnt.prodinfo.ProdTableTitleServiceStartDate}
	    ]]
	});
}