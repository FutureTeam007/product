<%@ page import="com.ei.itop.common.util.SessionUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="i18n" uri="../../WEB-INF/i18n.tld"%>
<%
	String rootPath = request.getContextPath();
	String menuOpType = SessionUtil.getOpInfo()==null?null:SessionUtil.getOpInfo().getOpType();
%>
<div id="wrapper">
        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top navbar-fix" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header header-fix">
            	<span class="site-logo">&nbsp;</span>
                <a class="navbar-brand brand-fix" href="#">ITOP</a>
            </div>
            <!-- /.navbar-header -->
            <ul class="nav navbar-top-links navbar-right topbar-fix">
                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>${sessionScope.opInfo.opFullName}&nbsp;<i class="fa fa-caret-down"></i>
                    </a>
                    <!-- 
                    <ul class="nav nav-tabs" role="tablist" style="display:none">
					  <li role="presentation"><a href="#">事件管理</a></li>
					  <li role="presentation"><a href="#">客户管理</a></li>
					</ul>
					 -->
                    <ul class="dropdown-menu dropdown-user">
                    	<li>
                        	<a href="${pageContext.request.contextPath}/page/<%="USER".equals(menuOpType)?"usercenter":"opcenter"%>/changeBaseInfo.jsp"><i class="fa fa-user fa-fw"></i><i18n:message code="i18n.nav.Profile" /></a>
                        </li>
                        <li>
                        	<a href="${pageContext.request.contextPath}/page/usercenter/changePwd.jsp"><i class="fa fa-gear fa-fw"></i><i18n:message code="i18n.nav.ChangePassword" /></a>
                        </li>
                        <c:if test="${sessionScope.opInfo.opType eq 'OP'&&sessionScope.opInfo.opKind==1}">
	                        <li class="divider"></li>
	                        <li>
	                        	<a href="${pageContext.request.contextPath}/page/custmgnt/main.jsp"><i class="fa fa-gear fa-fw"></i><i18n:message code="i18n.nav.Management" /></a>
	                        </li>
                        </c:if>
                        <li class="divider"></li>
                        <li><a href="${pageContext.request.contextPath}/doLogout"><i class="fa fa-sign-out fa-fw"></i><i18n:message code="i18n.nav.Logout" /></a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->
        </nav>
	<div id="page-wrapper-full">