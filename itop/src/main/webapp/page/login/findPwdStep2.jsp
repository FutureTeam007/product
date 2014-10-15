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
	<div class="register-step">1.邮箱验证</div>
	<div class="register-step register-step-active">2.修改密码</div>
	<div class="register-step">3.修改完成</div>
</div>
<div class="register-wrapper">
	<form class="form-horizonta register-form" role="form">
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
  		<div class="form-group clearfix register-form-btns">
		    <a class="btn btn-warning btn-lg" style="margin-left:40%" href="findPwdStep3.jsp">提交</a>
  		</div>
	</form>
</div>
<jsp:include page="../common/pageMasterEnd.jsp"></jsp:include>
</body>
</html>