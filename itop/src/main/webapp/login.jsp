<%@page import="org.springframework.web.servlet.support.RequestContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="i18n" uri="/WEB-INF/i18n.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ITOP<i18n:message code="i18n.login.PageTitle"/></title>
	<%@ include file="page/common/commonHead.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/res/easyui/jquery.cookie.js"></script>
	<script type="text/javascript">
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
				//初始化国际化
				initLocale();
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
					alert(i18n.login.AccountNoBlank);
					return false;
				}
				if(!accountPwd){
					alert(i18n.login.PasswordBlank);
					return false;
				}
				if(!verifyCode){
					alert(i18n.login.VerifyCodeBlank);
					return false;
				}
				return true;
			}
			//设置国际化语言
			function setLocale(locale){
				window.location="${pageContext.request.contextPath}/i18n?locale="+locale;
			}
			//初始化国际化
			function initLocale(){
				var locale = $.cookie('locale');
				if(locale=='en_US'){
					$("button[locale=en_US]").addClass("active");
					$("button[locale=zh_CN]").removeClass("active");
				}else if(locale=='zh_CN'){
					$("button[locale=zh_CN]").addClass("active");
					$("button[locale=en_US]").removeClass("active");
				}else{
					$("button[locale=zh_CN]").addClass("active");
					$("button[locale=en_US]").removeClass("active");
				}
			}
	</script>
</head>
<body>
	<div class="container">
		<div class="language-choose">
			Language:
			<button name="localeBtn" locale="en_US" type="button" class="btn btn-link" onclick="setLocale('en_US')">EN</button>/
			<button name="localeBtn" locale="zh_CN" type="button" class="btn btn-link" onclick="setLocale('zh_CN')">ZH</button>
		</div>
        <div class="row">
            <div class="col-md-4 col-md-offset-4" style="width:38%;margin-left:30%">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading login-header-fix">
                    	<span class="site-logo">&nbsp;</span>
                        <h3 class="panel-title">ITOP</h3>
                        <ul class="nav nav-tabs login-nav clearfix" role="tablist">
						  <li role="presentation" id="opLoginNav"><a href="javascript:void(0)"><i18n:message code="i18n.login.OpType" /></a></li>
						  <li role="presentation" id="userLoginNav"><a href="javascript:void(0)"><i18n:message code="i18n.login.UserType" /></a></li>
						</ul>
                    </div>
                    <div class="panel-body">
                        <form role="form" name="userLoginForm" method="post" action="${pageContext.request.contextPath}/doLogin" onsubmit="return checkFormInfo(1);" class="login-form undis" id="userLoginForm">
                                <div class="form-group clearfix">
                                	<label for="accountNo" class="col-sm-3 control-label"><i18n:message code="i18n.login.AccountNoLabel"/></label>
		    						<div class="col-sm-9">
                                    	<input class="form-control"  name="accountNo" id="accountNo1" type="text" value="${accountNo}" autofocus maxlength="30"/>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                	<label for="accountPwd" class="col-sm-3 control-label"><i18n:message code="i18n.login.PasswordLabel"/></label>
                                	<div class="col-sm-9">
                                    	<input class="form-control"  name="accountPwd" id="accountPwd1" type="password" value="" maxlength="30"/>
                                    	<input type="hidden" name="opType" value="USER"/>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                	<label for="verifyCode" class="col-sm-3 control-label"><i18n:message code="i18n.login.VerifyCodeLabel"/></label>
                                	<div class="col-sm-6">
                                    	<input class="form-control"  name="verifyCode" id="verifyCode1" type="text" value="" maxlength="4"/>
                                    </div>
                                    <div class="col-sm-3" style="padding-left:0px">
                                    	<img id="imgObj1" class="verify-code" width="68" height="33" onclick="verifyCodeChange(1)" src="<%=rootPath%>/verifycode/get" />
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                	<div class="col-sm-6">
		                                <input type="submit" class="btn btn-primary btn-lg pull-right" value="<i18n:message code="i18n.login.LoginBtn"/>"/>
                                    </div>
                                    <div class="col-sm-6">
                                    	<a href="${pageContext.request.contextPath}/register" class="btn btn-warning btn-lg pull-left list-inline"><i18n:message code="i18n.login.RegisterBtn"/></a>
                                    	<!-- <a href="${pageContext.request.contextPath}/page/login/findPwdStep1.jsp" class="btn btn-link login-findpwd-link pull-left list-inline" >忘记密码</a> -->
                                    </div>
                                </div>
                        </form>
                        <form role="form" name="opLoginForm" method="post"  action="${pageContext.request.contextPath}/doLogin" onsubmit="return checkFormInfo(2);" class="login-form undis" id="opLoginForm">
                                <div class="form-group clearfix">
                                	<label for="accountNo" class="col-sm-3 control-label"><i18n:message code="i18n.login.AccountNoLabel"/></label>
		    						<div class="col-sm-9">
                                    	<input class="form-control" placeholder="" name="accountNo" id="accountNo2" type="text" value="${accountNo}" autofocus maxlength="30"/>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                	<label for="accountPwd" class="col-sm-3 control-label"><i18n:message code="i18n.login.PasswordLabel"/></label>
                                	<div class="col-sm-9">
                                    	<input class="form-control" placeholder="" name="accountPwd" id="accountPwd2" type="password" value="" maxlength="30"/>
                                    	<input type="hidden" name="opType" value="OP"/>
                                    </div>
                                </div>
                                 <div class="form-group clearfix">
                                	<label for="verifyCode" class="col-sm-3 control-label"><i18n:message code="i18n.login.VerifyCodeLabel"/></label>
                                	<div class="col-sm-6">
                                    	<input class="form-control" placeholder="" name="verifyCode" id="verifyCode2" type="text" value="" maxlength="4"/>
                                    </div>
                                    <div class="col-sm-3" style="padding-left:0px">
                                    	<img id="imgObj2" class="verify-code" width="68" height="33" onclick="verifyCodeChange(2)" src="<%=rootPath%>/verifycode/get" />
                                    </div>
                                </div>
                                <input type="submit" class="btn btn-lg btn-primary btn-block" value="<i18n:message code="i18n.login.LoginBtn"/>"/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>