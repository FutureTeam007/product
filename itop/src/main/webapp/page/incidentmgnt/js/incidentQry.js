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
	var topOffset = 70;
    var height = (this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height;
    height = height - topOffset;
    if (height < 1) height = 1;
    if (height > topOffset) {
        $("#subPage").css("height", (height) + "px");
    }
}

function formatOperations(val,row){
	return "<button type='button' class='btn btn-link' onclick='javascript:view("+val+")'>查看</button>&nbsp;&nbsp;<button type='button' class='btn btn-link' onclick='javascript:edit("+val+")'>编辑</button>";
}

function view(id){
	$("#subPage").empty();
	$("#subPage").load("incidentView.jsp");
	$("#subPage").slide
}

function edit(id){
	
} 



