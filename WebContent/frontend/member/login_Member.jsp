<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<html lang="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>CoffeePuzzle</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/indexbar.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
	<div class="body"></div>
	<nav class="navbar navbar-default" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<a class="float-left"
					href="${pageContext.request.contextPath}/frontend/index.jsp"><img
					src="${pageContext.request.contextPath}/img/logo/logo-mdpi-36.png"></a>
				<a class="navbar-brand"
					href="${pageContext.request.contextPath}/frontend/index.jsp">CoffeePuzzle</a>
			</div>
			<!-- 手機隱藏選單區 -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<!-- 左選單 -->
				<ul class="nav navbar-nav">
					<li><a href="<%=request.getContextPath()%>/frontend/news/listOfAllNewsByStore.jsp" >最新消息</a></li>
					<li><a href="<%=request.getContextPath()%>/frontend/store/findStore.jsp">搜尋店家</a></li>
					<li><a href="<%=request.getContextPath()%>/frontend/spndcoffee/listAllSpndCoffee.jsp">寄杯</a></li>
					<li><a href="<%=request.getContextPath()%>/frontend/product/shop.jsp">購物</a></li>
					<li><a href="<%=request.getContextPath()%>/frontend/activity/activityList.jsp">活動</a></li>
				</ul>
				<!-- 右選單 -->
				<ul class="nav navbar-nav navbar-right">
					<li><a>${sessionScope.MEMBER==null?"CoffeePuzzle":sessionScope.MEMBER.mem_name}&nbsp;&nbsp;您好&nbsp;&nbsp;</a></li>
					<li class="nav-login-out"><a href="${pageContext.request.contextPath}/frontend/member/logout_Member.jsp">${sessionScope.MEMBER==null?"":"&nbsp;&nbsp;&nbsp;&nbsp;登出"}</a></li>
				</ul>
			</div>
			<!-- 手機隱藏選單區結束 -->
		</div>
	</nav>
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font color='red'>請修正以下錯誤:
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li>${message}</li>
				</c:forEach>
			</ul>
		</font>
	</c:if>
	<div class="grad"></div>
	<div class="header">
		<div>CoffeePuzzle</div>
	</div>

	<br>
	<div class="login">
		<div>
			<ul class="tab-group">
				<li class="tab active" style="font-family: 'FontAwesome';"><a
					href="${pageContext.request.contextPath}/frontend/member/login_Member.jsp"
					class="member_logincs">一般會員</a></li>
				<li class="tab"><a
					href="${pageContext.request.contextPath}/frontend/store/login_Store.jsp"
					class="store_logincs">店家</a></li>
			</ul>
		</div>
		<FORM METHOD="post"
			ACTION="<%=request.getContextPath()%>/frontend/member/member.do">
			<table border="0">
				<tr>
					<td>帳號:&nbsp;</td>
					<td><input type="TEXT" placeholder="useraccount"
						name="mem_acct" size="20" VALUE="" required="required" /></td>
				</tr>
				<tr>
					<td>密碼:&nbsp;</td>
					<td><input type="password" placeholder="password" name="mem_pwd"
						size="20" VALUE="" required="required" /></td>
				</tr>

			</table>
			<input type="hidden" name="action" value="Login_Member"> <input
				type="submit" value="登入">
		</FORM>

		<ul>
			<li><a
				href='${pageContext.request.contextPath}/frontend/member/addMember.jsp'>註冊會員</a></li>
			<li><a
				href='${pageContext.request.contextPath}/frontend/member/forgetPasswrod.jsp'>忘記密碼</a></li>
		</ul>

	</div>
	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
</body>
</html>