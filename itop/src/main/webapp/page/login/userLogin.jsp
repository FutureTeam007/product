<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>iTop用户登录</title>
	<%@ include file="../common/commonHead.jsp"%>
</head>
<body>
	<div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading login-header-fix">
                    	<span class="site-logo">&nbsp;</span>
                        <h3 class="panel-title">ITOP</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" class="login-form">
                                <div class="form-group clearfix">
                                	<label for="accountNo" class="col-sm-3 control-label">邮箱账号</label>
		    						<div class="col-sm-9">
                                    	<input class="form-control" placeholder="公司邮箱" name="accountNo" type="text" autofocus/>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                	<label for="accountPwd" class="col-sm-3 control-label">密码</label>
                                	<div class="col-sm-9">
                                    	<input class="form-control" placeholder="登录密码" name="accountPwd" type="password" value=""/>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                	<label for="verifyCode" class="col-sm-3 control-label">验证码</label>
                                	<div class="col-sm-6">
                                    	<input class="form-control" placeholder="验证码" name="verifyCode" type="text" value=""/>
                                    </div>
                                    <div class="col-sm-3" style="padding-left:0px">
                                    	<img id="imgObj" class="verify-code" width="68" height="33" onclick="verifyCodeChange()" src="<%=rootPath%>/verifycode/get" />
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                	<div class="col-sm-6">
		                                <a href="${pageContext.request.contextPath}/page/incidentmgnt/incidentQry.jsp" class="btn btn-primary btn-lg pull-right">登录</a>
                                    </div>
                                    <div class="col-sm-6">
                                    	<a href="${pageContext.request.contextPath}/page/register/registerStep1.jsp" class="btn btn-warning btn-lg pull-left list-inline">注册</a>
                                    	<a href="${pageContext.request.contextPath}/page/login/findPwdStep1.jsp" class="btn btn-link login-findpwd-link pull-left list-inline" >忘记密码</a>
                                    </div>
                                </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>