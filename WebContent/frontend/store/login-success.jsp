<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.store.model.*"%><html>
<%
	StoreVO storeVO = (StoreVO) request.getSession().getAttribute("STORE");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title></title>
</head>
<body>
<p>You are successfully logged in!</p>
	Welcome, ${sessionScope.STORE.store_acct}
</body>
</html>