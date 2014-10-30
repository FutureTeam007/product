<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="../common/commonHead.jsp"%>
	<%@ include file="../common/easyuiHead.jsp"%>
	<title>用户注册</title>
	<script type="text/javascript" src="<%=rootPath%>/page/usercenter/js/changeBaseInfo.js"></script>
	<script type="text/javascript">
		$(function(){
			if("${msg}"){
				$.messager.alert('提示',"${msg}");
			}
		});
	</script>
</head>
<body>
<jsp:include page="../common/pageMasterStart.jsp"></jsp:include>
<div class="register-wrapper">
	<div>
		<a class="btn btn-link top-close-link" style="margin-bottom:30px" href="<%=rootPath%>/page/incidentmgnt/main">返回首页</a>
	</div>
	<form class="form-horizonta register-form" style="width:70%" role="form" action="<%=rootPath%>/custmgnt/user/changebaseinfo" method="post" onsubmit="return validateModifyFormVars()">
		<div class="form-group clearfix">
		    <label for="acountNo" class="col-sm-2 control-label">账号</label>
		    <div class="col-sm-10">
		      	<input type="text" class="form-control small-control" id="acountNo" disabled="disabled" name="acountNo"  maxlength="30"/>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="companySel" class="col-sm-2 control-label">公司</label>
		    <div class="col-sm-10">
		      <input class="easyui-combotree" style="width:100%"  name="companySel" id="companySel" data-options="
		      		panelHeight:'auto'"
			  />
			  <input type="hidden" name="companyId" id="companyId" />
			  <input type="hidden" name="companyName" id="companyName" />
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="companySel" class="col-sm-2 control-label">角色</label>
		    <div class="col-sm-10">
		     	<span class="radio-inline">
				  <input type="radio" name="jobRole"  value="2"/>业务人员
				</span>
				<span class="radio-inline">
				  <input type="radio" name="jobRole"  value="3"/>IT人员
				</span>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="chineseName" class="col-sm-2 control-label">中文姓名</label>
		    <div class="col-sm-10">
		      	<input type="text" class="form-control small-control" id="chineseName"  name="chineseName" maxlength="40"/>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="givenName" class="col-sm-2 control-label">英文名</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control small-control" id="givenName"  name="givenName" maxlength="10"/>
		    </div>
		    <label for="familyName" class="col-sm-2 control-label">英文姓</label>
		    <div class="col-sm-4">
		      <input type="text" class="form-control small-control" id="familyName"  name="familyName" maxlength="10"/>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="moduleSel" class="col-sm-2 control-label">性别</label>
		    <div class="col-sm-10">
		        <span class="radio-inline">
				  <input type="radio" name="gender" id="male" value="1" /> 先生
				</span>
				<span class="radio-inline">
				  <input type="radio" name="gender" id="female" value="2"/> 女士
				</span>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="mobileNo" class="col-sm-2 control-label">移动电话</label>
		    <div class="col-sm-10">
		      	<input type="text" class="form-control small-control" id="mobileNo"  name="mobileNo" maxlength="11"/>
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="phoneNo" class="col-sm-2 control-label">固定电话</label>
		    <div class="col-sm-2">
		      	<input type="text" class="form-control small-control" id="areaCode"  name="areaCode" maxlength="4"/>
		    </div>
		    <div class="col-sm-8">
		      	<input type="text" class="form-control small-control" id="phoneNo"  name="phoneNo"  maxlength="8"/>
		    </div>
  		</div>
  		<div class="form-group clearfix register-form-btns">
		    <input type="submit" class="btn btn-warning btn-lg" style="margin-left:40%" value="提交" />
  		</div>
	</form>
</div>
<jsp:include page="../common/pageMasterEnd.jsp"></jsp:include>
</body>
</html>