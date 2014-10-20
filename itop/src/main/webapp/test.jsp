<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ITOP测试账号入口</title>
</head>
<body>
	<h3>普通用户入口</h3>
	<ul>
		<li>
			<div>OpCode[NO-1],OpId[9001],OpName[拓创],OrgId[2001],OrgName[EI]</div>
			<a href="${pageContext.request.contextPath}/test/login?role=1">用户01</a>
		</li>
		<li>
			<div>OpCode[NO-2],OpId[9002],OpName[拓创],OrgId[2001],OrgName[EI]</div>
			<a href="${pageContext.request.contextPath}/test/login?role=2">用户02</a>
		</li>
	</ul>
	<h3>顾问入口</h3>
	<ul>
		<li>
			<div>OpCode[SP200001],OpId[200001],OpName[ITOPEI1-EI],OrgId[2001],OrgName[EI]</div>
			<a href="${pageContext.request.contextPath}/test/login?role=3">顾问01</a>
		</li>
		<li>
			<div>OpCode[SP200002],OpId[200002],OpName[ITOPEI2-EI],OrgId[2001],OrgName[EI]</div>
			<a href="${pageContext.request.contextPath}/test/login?role=4">顾问02</a>
		</li>
	</ul>
	<div><a href="${pageContext.request.contextPath}/test/logout">注销</a></div>
</body>
</html>