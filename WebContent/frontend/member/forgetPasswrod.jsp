<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/forgetpassword.css" >
</head>
<body>
<div class="body"></div>
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
<%--                     <li class="nav-login"><%=memberVO.getMem_name()==null? "CoffeePuzzle":memberVO.getMem_name()%>&nbsp;&nbsp;您好&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li> --%>
                    <li><a href="${pageContext.request.contextPath}/frontend/member/login_Member.jsp">點此登入&nbsp;&nbsp;</a></li>
                    <li><a href="${pageContext.request.contextPath}/frontend/member/select_page.jsp">個人設定</a></li>
<%--                     <li class="nav-login-out"><a href="${pageContext.request.contextPath}/frontend/member/logout_Member.jsp"><%= memberVO.getMem_name()==null? "":"&nbsp;&nbsp;&nbsp;&nbsp;登出"%></a></li> --%>
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
		<div class="header-forget">
			<div>會員忘記密碼</div>
		</div>
		<div class="header-forget-1">
			<div>請在右邊填入帳號與信箱→</div>
		</div>
		
		<br>
		<div class="login">
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/member/member.do">
				<table border="0">
				<tr>
					<td class="account">帳號：&nbsp;</td>
					<td><input type="TEXT" placeholder="useraccount" name="mem_acct" size="30" VALUE="" required="required"/></td>
				</tr>
				<tr>
					<td>信箱：&nbsp;</td>
					<td><input type="email" placeholder="email" name="mem_email" size="30"  VALUE="" required="required"/></td>
				</tr>
				
				</table>
				<input type="hidden" name="action" value="MEM_FORGET_PASSWORD">
				<input type="submit" value="送出">
			</FORM>	
</div>
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
</body>
</html>