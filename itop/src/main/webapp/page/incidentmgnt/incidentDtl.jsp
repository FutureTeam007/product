<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String id = request.getParameter("id");
	String openFlag = request.getParameter("openFlag");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>事件新增/编辑</title>
	<%@ include file="../common/commonHead.jsp"%>
	<%@ include file="../common/easyuiHead.jsp"%>
	<script type="text/javascript">
		var incidentId = "<%=id%>";
		var openFlag = "<%=openFlag%>";
	</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/res/easyui/jquery.ajaxfileupload.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/incidentmgnt/js/incidentDtl.js"></script>
</head>
<body style="background:#fff;overflow-x:hidden;">
	<div>
		<button type='button' class='btn btn-link top-close-link' onclick='parent.hideSubPage()'>&lt;&lt;关闭</button>
	</div>
	<div class="inci-dtl-title">
		<h4><%=("a".equals(openFlag)?"新建":"编辑")%>事件</h4>
	</div>
	<form class="form-horizonta inci-info-form" role="form">
		<div class="form-group clearfix">
		    <label for="prodSel" class="col-sm-2 control-label">产品线</label>
		    <div class="col-sm-7">
		      <input class="easyui-combobox" style="width:100%"  name="prodSel" id="prodSel" data-options="
					url:rootPath+'/product/productList',
					method:'get',
					valueField:'scProductId',
					textField:'prodName',
					onSelect:prodSelChange,
					panelHeight:'auto'"
			  />
		    </div>
		    <div class="col-sm-3 form-desc">
		     	选择事件所关联的产品线
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="moduleSel" class="col-sm-2 control-label">服务目录</label>
		    <div class="col-sm-7">
		      <input class="easyui-combotree" style="width:100%"  name="moduleSel" id="moduleSel" data-options="panelHeight:'auto'"
			  />
		    </div>
		    <div class="col-sm-3 form-desc">
		     	选择事件关联的服务目录
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="moduleSel" class="col-sm-2 control-label">影响度</label>
		    <div class="col-sm-7">
		      	<c:forEach var="affect" items="${affectP}" begin="0" step="1">
		    		<span class="radio-inline"><input type="radio" name="affectVar" value="${affect.paramCode}" text="${affect.paramValue}">${affect.paramValue}</span>
		    	</c:forEach>
		    </div>
		    <div class="col-sm-3 form-desc">
		     	为事件标记影响度
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="inciTypeSel" class="col-sm-2 control-label">事件类别</label>
		    <div class="col-sm-7">
		    	<input class="easyui-combobox" style="width:100%"  name="classVar" id="inciTypeSel" data-options="
					url:rootPath+'/param/list?kindCode=IC_CLASS',
					method:'get',
					valueField:'paramCode',
					textField:'paramValue',
					panelHeight:'auto'"
				/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="brief" class="col-sm-2 control-label">事件简述</label>
		    <div class="col-sm-7">
		       <input type="text" class="form-control small-control" id="brief" maxlength="40"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    	简要描述事件的特征
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="happenTime" class="col-sm-2 control-label">发生时间</label>
		    <div class="col-sm-7">
		       <input class="easyui-datebox" style="width:100%" id="happenTime" data-options="
		       		formatter: function(date){ return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();}
		       	">
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="detail" class="col-sm-2 control-label">详细描述</label>
		    <div class="col-sm-7">
		       <textarea class="form-control small-control-large" rows="5" id="detail"></textarea>
		    </div>
		    <div class="col-sm-3 form-desc">
		    	详细说明事件的各项内容
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="ccList" class="col-sm-2 control-label">抄送</label>
		    <div class="col-sm-7">
		       <input type="text" class="form-control small-control" id="ccList" maxlength="100"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    	填写抄送用户的邮箱地址，多个地址用逗号分隔
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="happenDate" class="col-sm-2 control-label">附件</label>
		    <div class="col-sm-10 attachList" id="attachList">
		       <input type="file" id="uploadFile1" name="uploadFile" value="选择文件" onchange="attachUpload()"/>
		    </div>
  		</div>
  		<div class="form-group clearfix inci-form-btns">
		    <button class="btn btn-outline btn-warning btn-lg" style="margin-left:280px;margin-right:20px" type="button" onclick="save()">保存</button>
		    <button class="btn btn-warning btn-lg" type="button" onclick="saveAndCommit()">直接提交</button>
  		</div>
	</form>
</body>
</html>