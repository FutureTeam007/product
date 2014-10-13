<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String rootPath = request.getContextPath();
%>
<div id="wrapper">
        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top navbar-fix" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header header-fix">
            	<span class="site-logo">&nbsp;</span>
                <a class="navbar-brand brand-fix" href="#">iTop</a>
            </div>
            <div class="input-group custom-search-form" style="width:300px;float:left;margin:15px auto 15px 32px">
                <input type="text" class="form-control" placeholder="输入事件系列号查询事件" />
                <span class="input-group-btn">
                 <button class="btn btn-default" style="height:34px" type="button">
                     <i class="fa fa-search"></i>
                 </button>
            	</span>
            </div>
            <!-- /.navbar-header -->
            <ul class="nav navbar-top-links navbar-right topbar-fix">
                <!-- /.dropdown -->
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="#"><i class="fa fa-user fa-fw"></i>个人信息</a>
                        </li>
                        <li><a href="#"><i class="fa fa-gear fa-fw"></i>修改密码</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="#"><i class="fa fa-sign-out fa-fw"></i>注销</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li>
                            <a href="<%=rootPath %>/page/incidentmgnt/incidentQry.jsp"><i class="fa fa-table fa-fw"></i> 事件管理 </a>
                        </li>
                        <li>
                            <a href="<%=rootPath %>/page/custmgnt/main.jsp"><i class="fa fa-wrench fa-fw"></i> 客户管理 </a>
                        </li>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>
	<div id="page-wrapper">