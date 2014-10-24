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
	<div class="register-step">1.填写基本信息</div>
	<div class="register-step register-step-active">2.注册完成</div>
	<!-- <div class="register-step">3.账号激活</div>  -->
</div>
<div class="register-wrapper">
	<div class="panel panel-warning">
      <div class="panel-heading">
        <h3 class="panel-title success-title">恭喜您，注册成功！</h3>
      </div>
      <!-- 
      <div class="panel-body success-content" >
         	账号激活邮件已发至您的邮箱，请登录邮箱使用激活链接进行账号激活！
      </div>
       -->
      <div class="panel-body success-content" >
         	<a href="<%=rootPath%>" class="btn btn-link" >点击此处登录</a>
      </div>
    </div>
</div>
<jsp:include page="../common/pageMasterEnd.jsp"></jsp:include>
</body>
</html>