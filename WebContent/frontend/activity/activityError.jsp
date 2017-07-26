<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/pages/isVisitor.jsp" %>

<!DOCTYPE html>

<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService" />
<%-- <% session.setAttribute("MEMBER", memSvc.getOneMember("MEM00000001")); %> --%>

<html lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>活動清單</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<!--[if lt IE 9]>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/frontend/css/main.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/activity/css/activityError.css">	

</head>

<body>
	<nav class="navbar navbar-default" role="navigation">
		<div class="container">
			<%@ include file="/pages/navbarHeader.jsp" %>
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
				<%@ include file="/pages/navbarRight.jsp" %>
			</div>
			<!-- 手機隱藏選單區結束 -->
		</div>
	</nav>
	<!-- nav end -->
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-sm-6 col-sm-offset-3">
				<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/frontend/activity/activity.do"
					class="input-group">
					<input type="text" class="form-control" name="activ_name"
						placeholder="請輸入關鍵字"> <span class="input-group-btn">
						<input type="submit" class="btn btn-cofe" value="搜尋"> <input
						type="hidden" name="action" value="Search_For_Activ_name">
					</span>
				</FORM>
			</div>
			<div class="col-xs-12 col-sm-3">
				<p class="text-right">
					<a href="<%=request.getContextPath()%>/frontend/activity/addActivity.jsp"
						class="btn btn-cofe">辦活動</a>
				</p>
			</div>
			<hr class="col-xs-12">
			<div class="row">
				<div id="error-group">
					<img
						src="<%=request.getContextPath()%>/frontend/img/logo/logo-xxxhdpi-192.png"
						style="display: block; margin: auto;">
					<div class="sorry-group">
						<h2>
							SORRY! <span>我們暫時沒有您想參與的活動</span>
						</h2>
						<p>喝杯咖啡休息一下? 或是 自己舉辦活動?</p>
						<p>
							<a href="">« Go 找間好店</a> / <a
								href="<%=request.getContextPath()%>/frontend/activity/addActivity.jsp">Go
								舉辦活動 »</a>
						</p>
					</div>
				</div>
			</div>
		</div>
		<!-- container end -->
		<footer class="footer text-center">
			<div class="col-xs-12 col-sm-3">常見問題</div>
			<div class="col-xs-12 col-sm-3">功能介紹</div>
			<div class="col-xs-12 col-sm-3">關於我們</div>
			<div class="col-xs-12 col-sm-3">客服中心</div>
			<center style="margin-top: 10px;">BA101第四組</center>
		</footer>
		<script src="https://code.jquery.com/jquery.js"></script>
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>
