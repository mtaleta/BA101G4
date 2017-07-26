<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
  <meta charset="UTF-8">
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="HandheldFriendly" content="true" />
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
	<link href="https://cdn.jotfor.ms/static/formCss.css?3.3.612" rel="stylesheet" type="text/css" />
	<link type="text/css" rel="stylesheet" href="https://cdn.jotfor.ms/css/styles/nova.css?3.3.612" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/addsignin.css" type="text/css">
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/forgetpassword.css" >
		<title>SignIn</title>
</head>
<div class="body"></div>
	<nav class="navbar navbar-default" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <a class="float-left" href="index.html"><img src="${pageContext.request.contextPath}/img/logo/logo-mdpi-36.png"></a>
                <a class="navbar-brand" href="index.html">CoffeePuzzle</a>
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
<%--                     <li class="nav-login"><%= storeVO.getStore_name()==null? "CoffeePuzzle":storeVO.getStore_name()%>&nbsp;&nbsp;您好&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li> --%>
                    <li><a href="${pageContext.request.contextPath}/frontend/store/login_Store.jsp">點此登入&nbsp;&nbsp;</a></li>
                    <li><a href="${pageContext.request.contextPath}/frontend/store/select_page.jsp">個人設定</a></li>
<%--                     <li class="nav-login-out"><a href="${pageContext.request.contextPath}/frontend/store/logout_Store.jsp"><%= storeVO.getStore_name()==null? "":"&nbsp;&nbsp;&nbsp;&nbsp;登出"%></a></li> --%>
                </ul>
            </div>
            <!-- 手機隱藏選單區結束 -->
        </div>
    </nav>
    
    <div class="grad"></div>
		<div class="header-forget">
			<div>會員忘記密碼</div>
		</div>
		<div class="header-forget-1">
			<div>請在右邊填入帳號與信箱→</div>
		</div>
		
		<br>
		<div class="login">
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/store/store.do">
				<table border="0">
						<tr>
						<td>請輸入帳號：</td>
						<td><input type="TEXT" name="store_acct" size="20" VALUE="" /></td>
					</tr>
					<tr>
						<td>請輸入信箱：</td>
						<td><input type="TEXT" name="store_email" size="20" VALUE="" /></td>
					</tr>		
				</table>
					
					<input type="hidden" name="action" value="STORE_FORGET_PASSWORD">
					<input type="submit" value="送出">
				</FORM>
		</div>
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

</body>
</html>