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
	initIframe();
});

//初始化左侧客户树
function initLeftTree(){
	$('#custSel').tree({
		editable:false,
		disabled:false,
	    url:rootPath+'/custmgnt/custinfo/tree',
	    loadFilter: function(data){ 
	    	return [{text: i18n.custmgnt.main.CustTreeRoot,id:-1,children:data}];
	    },
	    onSelect:function(data){
	    	if(data.id==-1){
	    		return false;
	    	}
	    	var reloadURL= navLink[currentLinkOrder]+"?custId="+data.attributes.ccCustId+"&custName="+encodeURI(data.attributes.custName)+"&domainName="+data.attributes.domainName+"&orgLevel="+data.attributes.orgLevel;
	    	$('#cust-mgnt-iframe').attr('src',reloadURL);
	    },
	    onLoadSuccess:function(){
			var rootNode = $('#custSel').tree('getRoot');
			var children = $('#custSel').tree('getChildren',rootNode);
			if(children&&children.length>1){
				$('#custSel').tree('select',children[1].target);
			}
		}
	});
}

//初始化左侧客户树
function refreshLeftTree(nodeId){
	if(nodeId){
		$('#custSel').tree({
			onLoadSuccess:function(){
				var node = $('#custSel').tree('find',nodeId);
				$('#custSel').tree('select',node.target);
			}
		});
	}else{
		$('#custSel').tree({
			onLoadSuccess:function(){
				var rootNode = $('#custSel').tree('getRoot');
				var children = $('#custSel').tree('getChildren',rootNode);
				if(children&&children.length>1){
					$('#custSel').tree('select',children[1].target);
				}
			}
		});
	}
	$('#custSel').tree('reload');
}

//初始化Frame高度
function initIframe(){
	//设置默认高度
	var height = (this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height;
	height = height-150;
	$('#cust-mgnt-iframe').css("height",height+"px");
	//定期检测frame内部的高度，变化iframe的高度
	setInterval(function(){
		try{
			var changeHeight = $(window.frames["cust-mgnt-iframe"].document).height();
			$('#cust-mgnt-iframe').css("height",changeHeight+"px");
		}catch(e){}
	},500);
}

//绑定切换标签卡
function initNavCard(){
	$(".nav-tabs li").click(function(){
		//切换样式
		$(this).siblings().removeClass("active");
		$(this).addClass("active");
		//重置链接
		var order = $(this).attr("order");
		currentLinkOrder = order;
		var node = $('#custSel').tree('getSelected');
		if(node){
			var reloadURL= navLink[order]+"?custId="+node.attributes.ccCustId+"&custName="+encodeURI(node.attributes.custName)+"&domainName="+node.attributes.domainName+"&orgLevel="+node.attributes.orgLevel;
			$('#cust-mgnt-iframe').attr('src',reloadURL);
		}
	});
}

//绑定切换标签卡
function showMsg(msg){
	$.messager.alert(i18n.dialog.AlertTitle,msg);
}


