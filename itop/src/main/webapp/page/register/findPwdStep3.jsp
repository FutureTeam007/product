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
	<div class="panel panel-warning">
      <div class="panel-heading">
        <h3 class="panel-title success-title">密码修改成功！</h3>
      </div>
      <div class="panel-body success-content middle-content" >
         	<a href="../incidentmgnt/incidentQry.jsp" class="btn btn-link" >点击此处回到登录页面或等待3秒自动跳转……</a>
      </div>
    </div>
</div>
<jsp:include page="../common/pageMasterEnd.jsp"></jsp:include>
</body>
</html>