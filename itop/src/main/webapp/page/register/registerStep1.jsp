<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="../common/commonHead.jsp"%>
	<title>用户注册</title>
	<script type="text/javascript" src="<%=rootPath%>/page/register/js/registerStep1.js"></script>
</head>
<body>
<jsp:include page="../common/pageMasterStartNotLogin.jsp"></jsp:include>
<div class="register-nav">
	<div class="register-step register-step-active">1.填写基本信息</div>
	<div class="register-step">2.注册完成</div>
	<div class="register-step">3.账号激活</div>
</div>
<div class="register-wrapper">
	<form class="form-horizonta register-form" role="form">
		<div class="form-group clearfix">
		    <label for="acountNo" class="col-sm-2 control-label">账号</label>
		    <div class="col-sm-7">
		      	<input type="text" class="form-control" id="acountNo"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		     	请使用公司邮箱
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="passwd" class="col-sm-2 control-label">密码</label>
		    <div class="col-sm-7">
		      	<input type="password" class="form-control" id="passwd"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="passwdConfirm" class="col-sm-2 control-label">确认密码</label>
		    <div class="col-sm-7">
		      	<input type="password" class="form-control" id="passwdConfirm"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="companySel" class="col-sm-2 control-label">公司</label>
		    <div class="col-sm-7">
		      <input class="easyui-combobox" style="width:100%"  name="companySel" id="companySel" data-options="
					url:'js/json/company_data.json',
					method:'get',
					valueField:'custId',
					textField:'custName',
					panelHeight:'auto'"
			  />
		    </div>
		    <div class="col-sm-3 form-desc">
		     	选择归属的公司
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="companySel" class="col-sm-2 control-label">部门</label>
		    <div class="col-sm-7">
		      <input class="easyui-combobox" style="width:100%"  name="companySel" id="companySel" data-options="
					url:'js/json/company_data.json',
					method:'get',
					valueField:'custId',
					textField:'custName',
					panelHeight:'auto'"
			  />
		    </div>
		    <div class="col-sm-3 form-desc">
		     	选择归属的部门
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="chineseName" class="col-sm-2 control-label">中文姓名</label>
		    <div class="col-sm-7">
		      	<input type="text" class="form-control" id="chineseName"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		     	请使用公司邮箱
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="givenName" class="col-sm-2 control-label">英文名</label>
		    <div class="col-sm-3">
		      <input type="text" class="form-control" id="givenName"/>
		    </div>
		    <label for="familyName" class="col-sm-1 control-label">英文姓</label>
		    <div class="col-sm-3">
		      <input type="text" class="form-control" id="familyName"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		     	
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="moduleSel" class="col-sm-2 control-label">性别</label>
		    <div class="col-sm-7">
		      <span class="radio-inline">
				  <input type="radio" name="gender" id="male" value="1"> 先生
				</span>
				<span class="radio-inline">
				  <input type="radio" name="gender" id="female" value="2"> 女士
				</span>
		    </div>
		    <div class="col-sm-3 form-desc">
		     	
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="mobileNo" class="col-sm-2 control-label">移动电话</label>
		    <div class="col-sm-7">
		      	<input type="text" class="form-control" id="mobileNo"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		     	
		    </div>
  		</div>
  		<div class="form-group clearfix">
		    <label for="phoneNo" class="col-sm-2 control-label">固定电话</label>
		    <div class="col-sm-7">
		      	<input type="text" class="form-control" id="phoneNo"/>
		    </div>
		    <div class="col-sm-3 form-desc">
		    </div>
  		</div>
  		<div class="form-group clearfix register-form-btns">
		    <a class="btn btn-warning btn-lg" style="margin-left:40%" href="registerStep2.jsp">提交</a>
  		</div>
	</form>
</div>
<jsp:include page="../common/pageMasterEnd.jsp"></jsp:include>
</body>
</html>