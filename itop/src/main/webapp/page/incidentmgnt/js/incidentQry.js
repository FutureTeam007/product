$(function(){
	initDataPager();
	bindStatusToggle();
	initSubPage();
});

function initDataPager(){
	var pager = $('#incidentDataTable').datagrid().datagrid('getPager');
	$(pager).pagination({
		pageSize: 15,
		pageList: [15,30],
		beforePageText: '第',
		afterPageText: '页共 {pages} 页',
		displayMsg: '当前显示 {from} - {to} 条记录共 {total} 条记录',
	});
}

function bindStatusToggle(){
	$("#statusNav li").click(function(){
		$(this).addClass("active");
		$(this).siblings().removeClass("active");
	});
}

function initSubPage(){
	var topOffset = 66;
    var height = (this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height;
    var width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
    height = height - topOffset;
    if (height < 1) height = 1;
    if (height > topOffset) {
        $("#subPage").css("height", (height) + "px");
        $("#pageMask").css("height", (height) + "px");
        $("#pageMask").css("width", (width-200) + "px");
    }
}

function formatOperations(val,row){
	return "<button type='button' class='btn btn-link' onclick='view("+val+")'>查看</button>&nbsp;&nbsp;<button type='button' class='btn btn-link' onclick='edit("+val+")'>编辑</button>";
}

function view(id){
	showSubPage("incidentView.jsp?id="+id);
}

function edit(id){
	showSubPage("incidentDtl.jsp?id="+id);
} 

function add(){
	showSubPage("incidentDtl.jsp");
}

function showSubPage(url){
	$("#pageMask").show();
	$("#subPage").show();
	$("#subPageIframe").attr("src",url);
	$("#subPage").animate({right: '0px'}, "50");
}

function hideSubPage(){
	$("#pageMask").hide();
	$("#subPage").hide();
	$("#subPage").css({right: '-900px'});
	$("#subPageIframe").attr("src","");
}



