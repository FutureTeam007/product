<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>事件新增/编辑</title>
	<%@ include file="../common/commonHead.jsp"%>
	<%@ include file="../common/easyuiHead.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/page/incidentmgnt/js/incidentDtl.js"></script>
</head>
<body style="background:#fff;overflow-x:hidden;">
	<div>
		<button type='button' class='btn btn-link' onclick='parent.hideSubPage()'>&lt;&lt;关闭</button>
	</div>
	<div class="inci-dtl-title">
		<h4>新建事件</h4>
	</div>
	<form class="form-horizonta inci-info-form" role="form">
		<div class="form-group clearfix">
		    <label for="prodSel" class="col-sm-2 control-label">产品线</label>
		    <div class="col-sm-7">
		      <input class="easyui-combobox" style="width:100%"  name="prodSel" id="prodSel" data-options="
					url:'js/json/prod_data.json',
					method:'get',
					valueField:'prodId',
					textField:'prodName',
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
		      <input class="easyui-combobox" style="width:100%"  name="moduleSel" id="moduleSel" data-options="
					url:'js/json/prod_data.json',
					method:'get',
					valueField:'prodId',
					textField:'prodName',
					panelHeight:'auto'"
			  />
		    </div>
		    <div class="col-sm-3 form-desc">
		     	选择事件关联的服务目录
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="moduleSel" class="col-sm-2 control-label">影响度</label>
		    <div class="col-sm-7">
		      <span class="radio-inline">
				  <input type="radio" name="effectLevel" id="effectLevel1" value="1"> 咨询
				</span>
				<span class="radio-inline">
				  <input type="radio" name="effectLevel" id="effectLevel2" value="2"> 一般
				</span>
				<span class="radio-inline">
				  <input type="radio" name="effectLevel" id="effectLevel3" value="3"> 严重
				</span>
				<span class="radio-inline">
				  <input type="radio" name="effectLevel" id="effectLevel4" value="4"> 重大
				</span>
		    </div>
		    <div class="col-sm-3 form-desc">
		     	为事件标记影响度
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="inciTypeSel" class="col-sm-2 control-label">事件类别</label>
		    <div class="col-sm-7">
		       <input class="easyui-combobox" style="width:100%"  name="inciTypeSel" id="inciTypeSel" data-options="
					url:'js/json/incidentType_data.json',
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
		    <label for="inciShortDesc" class="col-sm-2 control-label">事件简述</label>
		    <div class="col-sm-7">
		       <input type="text" class="form-control" id="inciShortDesc"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    	简要描述事件的特征
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="happenDate" class="col-sm-2 control-label">发生时间</label>
		    <div class="col-sm-7">
		       <input class="easyui-datebox" style="width:100%" id="happenDate" data-options="sharedCalendar:'#cc'">
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="happenDate" class="col-sm-2 control-label">详细描述</label>
		    <div class="col-sm-7">
		       <textarea class="form-control" rows="3"></textarea>
		    </div>
		    <div class="col-sm-3 form-desc">
		    	详细说明事件的各项内容
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="mailCC1" class="col-sm-2 control-label">抄送其他用户</label>
		    <div class="col-sm-7">
		       <input type="text" class="form-control" id="mailCC1"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    	填写抄送用户的邮箱地址，用分号隔开
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="mailCC2" class="col-sm-2 control-label">抄送指定顾问</label>
		    <div class="col-sm-7">
		       <input type="text" class="form-control" id="mailCC2"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    	填写抄送顾问的邮箱地址，用分号隔开
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="happenDate" class="col-sm-2 control-label">附件</label>
		    <div class="col-sm-10">
		       <a href="#">文件文件文件文件</a>
		       <button type="button" class="btn btn-primary btn-sm">选择文件</button>
		    </div>
  		</div>
  		<div class="form-group clearfix inci-form-btns">
		    <button class="btn btn-outline btn-warning btn-lg" style="margin-left:280px;margin-right:20px" type="button">保存</button>
		    <button class="btn btn-warning btn-lg" type="button">直接提交</button>
  		</div>
	</form>
	<div id="cc" class="easyui-calendar"></div>
</body>
</html>