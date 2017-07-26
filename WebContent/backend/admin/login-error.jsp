<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.admin.model.*"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" >
<title>Insert title here</title>
</head>
<body>
<p class="wrong_admin">Sorry! 帳號  或   密碼 錯誤 ，請重新輸入!!</p>  
<%@ include file="login_Admin.jsp" %>  

</body>
</html>