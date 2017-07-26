<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<%
MemberVO memberVO = (MemberVO)  request.getSession().getAttribute("MEMBER");
%>
<html lang="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>CoffeePuzzle</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/indexbar.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"></script>
	<link rel="stylesheet" type="text/css" media="all"href="${pageContext.request.contextPath}/css/default_Selectpage.css" />
</head>
<body>
<!-- <div class="body"></div> -->
    <nav class="navbar navbar-default" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <a class="float-left" href="${pageContext.request.contextPath}/frontend/index.jsp"><img src="${pageContext.request.contextPath}/img/logo/logo-mdpi-36.png"></a>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/frontend/index.jsp">CoffeePuzzle</a>
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
                    <li class="nav-login"><%=memberVO.getMem_name()==null? "CoffeePuzzle":memberVO.getMem_name()%>&nbsp;&nbsp;您好&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>                
                    <li><a href="${pageContext.request.contextPath}/frontend/member/select_page.jsp">個人設定</a></li>
                    <li class="nav-login-out"><a href="${pageContext.request.contextPath}/frontend/member/logout_Member.jsp"><%= memberVO.getMem_name()==null? "":"&nbsp;&nbsp;&nbsp;&nbsp;登出"%></a></li>
                </ul>
            </div>
            <!-- 手機隱藏選單區結束 -->
        </div>
    </nav>
<div id="wrapper">
	<div id="page" class="container">
		<div id="content">
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/member/member.do">
	<table border="0">
		<tr>
			<td>會員帳號：</td>
			<td><%= memberVO.getMem_acct()%></td>
		</tr>
		<tr>
			<td>請輸入 欲修改新密碼：</td>
			<td><input type="password" name="newPassword" size="20" VALUE="" /></td>
		</tr>
		<tr>
			<td>請再輸入一次 新密碼：</td>
			<td><input type="password" name="newPassword2" size="20" VALUE="" /></td>
		</tr>		
	</table>
		
		<input type="hidden" name="action" value="UPDATE_MEMBER_PASSWORD">
		<input type="submit" value="更新密碼">
	</FORM>
</div>
		<div id="sidebar">
			<div class="box2">
				<div class="title">
					<h2>會員資料專區:</h2>
				</div>
				<ul class="style2">
					<%@ include file="/pages/select_mem.jsp" %>
				</ul>
			</div>
		</div>
	</div>
</div>
</body>
</html>