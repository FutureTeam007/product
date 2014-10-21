<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.ei.itop.common.util.SessionUtil" %>
<%@ page import="com.ei.itop.common.bean.OpInfo" %>
<%
	String baseURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	String rootPath = request.getContextPath();
	OpInfo opInfo = SessionUtil.getOpInfo();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<link href="<%=rootPath%>/res/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
<link href="<%=rootPath%>/res/bootstrap/css/plugins/metisMenu/metisMenu.min.css" rel="stylesheet"/>
<link href="<%=rootPath%>/res/bootstrap/css/plugins/timeline.css" rel="stylesheet"/>
<link href="<%=rootPath%>/res/bootstrap/css/sb-admin-2.css" rel="stylesheet"/>
<!-- Website Default CSS -->
<link href="<%=rootPath%>/theme/default/default.css" rel="stylesheet">
<!-- Custom Fonts -->
<link href="<%=rootPath%>/res/bootstrap/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="<%=rootPath%>/res/bootstrap/js/html5shiv.js"></script>
    <script src="<%=rootPath%>/res/bootstrap/js/respond.min.js"></script>
<![endif]-->
<!-- /#wrapper -->
<!-- jQuery Version 1.11.0 -->
<script src="<%=rootPath%>/res/bootstrap/js/jquery-1.11.0.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="<%=rootPath%>/res/bootstrap/js/bootstrap.min.js"></script>
<!-- Metis Menu Plugin JavaScript -->
<script src="<%=rootPath%>/res/bootstrap/js/plugins/metisMenu/metisMenu.min.js"></script>
<!-- Custom Theme JavaScript -->
<script src="<%=rootPath%>/res/bootstrap/js/sb-admin-2.js"></script>
<script type="text/javascript">
<!--
	var rootPath = "<%=rootPath%>";
	<% if (opInfo!=null) {%>
		var opId = "<%=opInfo.getOpId()%>";
		var opName = "<%=opInfo.getOpName()%>";
		var opCode = "<%=opInfo.getOpCode()%>";
		var opType = "<%=opInfo.getOpType()%>";
	<% }%>
//-->
</script>

