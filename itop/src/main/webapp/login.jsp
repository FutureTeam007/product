<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ITOP登录</title>
	<%@ include file="page/common/commonHead.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/res/easyui/jquery.cookie.js"></script>
	<script type="text/javascript">
		<!--
			//登录模式 1-用户登录 2-服务方登录
			var loginModel = null;
		
			$(function(){
				//初始化登录表单
				initLoginNav();
				//后台返回的错误信息
				var errorMsg = "${errorMsg}";
				if(errorMsg){
					alert(errorMsg);
				}
			});
			//初始化登录标签页
			function initLoginNav(){
				//取得Cookie，判断用户上次的登录角色是USER还是OP
				var cookieOpType = $.cookie('opType');
				//如果用户登录过
				if(cookieOpType){
					if(cookieOpType=="USER"){
						setUserLoginForm();													
					}else if(cookieOpType=="OP"){
						setOpLoginForm();
					}
				}else{
					setUserLoginForm();
				}
				//取得历史账号，方便用户直接登录
				var cookieAccountNo = $.cookie('accountNo');
				if(cookieAccountNo){
					$("#accountNo"+loginModel).val(cookieAccountNo);
				}
				//绑定LoginNav标签的切换事件
				$(".login-nav li").each(function(){
					$(this).click(function(){
						if($(this).attr("id")=="userLoginNav"){
							setUserLoginForm();
						}else if($(this).attr("id")=="opLoginNav"){
							setOpLoginForm();
						}
					});
				});
			}
			//设置为用户登录模式
			function setUserLoginForm(){
				loginModel = 1;
				$("#userLoginNav").addClass("active");
				$("#userLoginNav").siblings().removeClass("active");
				$("#opLoginForm").hide();
				$("#userLoginForm").show();
			}
			
			//设置服务方登录模式
			function setOpLoginForm(){
				loginModel = 2;
				$("#opLoginNav").addClass("active");
				$("#opLoginNav").siblings().removeClass("active");
				$("#userLoginForm").hide();
				$("#opLoginForm").show();
			}
			
			//验证码切换方法
			function verifyCodeChange(id){
				$("#imgObj"+id).attr("src","${pageContext.request.contextPath}/verifycode/get?r="+new Date());
			}
			
			//检查提交的表单项
			function checkFormInfo(id){
				var accountNo = $.trim($("#accountNo"+id).val());
				var accountPwd = $.trim($("#accountPwd"+id).val());
				var verifyCode = $.trim($("#verifyCode"+id).val());
				if(!accountNo){
					alert("请填写登录账号");
					return false;
				}
				if(!accountPwd){
					alert("请填写登录密码");
					return false;
				}
				if(!verifyCode){
					alert("请填写验证码");
					return false;
				}
				return true;
			}
		-->
	</script>
</head>
<body>
	<div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading login-header-fix">
                    	<span class="site-logo">&nbsp;</span>
                        <h3 class="panel-title">ITOP</h3>
                        <ul class="nav nav-tabs login-nav clearfix" role="tablist">
						  <li role="presentation" id="opLoginNav"><a href="javascript:void(0)">服务方登录</a></li>
						  <li role="presentation" id="userLoginNav"><a href="javascript:void(0)">用户登录</a></li>
						</ul>
                    </div>
                    <div class="panel-body">
                        <form role="form" method="post" action="${pageContext.request.contextPath}/doLogin" onsubmit="return checkFormInfo(1)" class="login-form undis" id="userLoginForm">
                                <div class="form-group clearfix">
                                	<label for="accountNo" class="col-sm-3 control-label">邮箱账号</label>
		    						<div class="col-sm-9">
                                    	<input class="form-control" placeholder="公司邮箱" name="accountNo" id="accountNo1" type="text" value="${accountNo}" autofocus maxlength="30"/>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                	<label for="accountPwd" class="col-sm-3 control-label">密码</label>
                                	<div class="col-sm-9">
                                    	<input class="form-control" placeholder="登录密码" name="accountPwd" id="accountPwd1" type="password" value="" maxlength="30"/>
                                    	<input type="hidden" name="opType" value="USER"/>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                	<label for="verifyCode" class="col-sm-3 control-label">验证码</label>
                                	<div class="col-sm-6">
                                    	<input class="form-control" placeholder="验证码" name="verifyCode" id="verifyCode1" type="text" value="" maxlength="4"/>
                                    </div>
                                    <div class="col-sm-3" style="padding-left:0px">
                                    	<img id="imgObj1" class="verify-code" width="68" height="33" onclick="verifyCodeChange(1)" src="<%=rootPath%>/verifycode/get" />
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                	<div class="col-sm-6">
		                                <input type="submit" class="btn btn-primary btn-lg pull-right" value="登录"/>
                                    </div>
                                    <div class="col-sm-6">
                                    	<a href="${pageContext.request.contextPath}/register" class="btn btn-warning btn-lg pull-left list-inline">注册</a>
                                    	<!-- <a href="${pageContext.request.contextPath}/page/login/findPwdStep1.jsp" class="btn btn-link login-findpwd-link pull-left list-inline" >忘记密码</a> -->
                                    </div>
                                </div>
                        </form>
                        <form role="form" method="post"  action="${pageContext.request.contextPath}/doLogin" onsubmit="return checkFormInfo(2)" class="login-form undis" id="opLoginForm">
                                <div class="form-group clearfix">
                                	<label for="accountNo" class="col-sm-3 control-label">邮箱账号</label>
		    						<div class="col-sm-9">
                                    	<input class="form-control" placeholder="公司邮箱" name="accountNo" id="accountNo2" type="text" value="${accountNo}" autofocus maxlength="30"/>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                	<label for="accountPwd" class="col-sm-3 control-label">密码</label>
                                	<div class="col-sm-9">
                                    	<input class="form-control" placeholder="登录密码" name="accountPwd" id="accountPwd2" type="password" value="" maxlength="30"/>
                                    	<input type="hidden" name="opType" value="OP"/>
                                    </div>
                                </div>
                                 <div class="form-group clearfix">
                                	<label for="verifyCode" class="col-sm-3 control-label">验证码</label>
                                	<div class="col-sm-6">
                                    	<input class="form-control" placeholder="验证码" name="verifyCode" id="verifyCode2" type="text" value="" maxlength="4"/>
                                    </div>
                                    <div class="col-sm-3" style="padding-left:0px">
                                    	<img id="imgObj2" class="verify-code" width="68" height="33" onclick="verifyCodeChange(2)" src="<%=rootPath%>/verifycode/get" />
                                    </div>
                                </div>
                                <input type="submit" class="btn btn-lg btn-primary btn-block" value="登录"/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>