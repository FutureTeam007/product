<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="i18n" uri="../WEB-INF/i18n.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	setTimeout(function(){
		parent.parent.parent.parent.parent.parent.parent.parent.parent.parent.parent.location.href="${pageContext.request.contextPath}/login.jsp";
	}, 1500);
</script>
<title></title>
</head>
<body>
<i18n:message code="i18n.login.SessionTimeout"/>
</body>
</html>