<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.store_tag.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.LinkedHashSet"%>
<%@ page import="java.util.Map"%>

<%@ include file="/pages/isVisitor.jsp" %>

<jsp:useBean id="newsSvc" scope="page"
	class="com.news.model.NewsService" />
<jsp:useBean id="storeSvc" scope="page"
	class="com.store.model.StoreService" />

<!DOCTYPE html>
<html>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<style type="text/css">
	.row {
	min-height: 300px;
	margin-left: 10px;
	padding: 15px;
}

	.footer{
	grid-row-start: 2;
    grid-row-end: 3;
    background-color: #333;
    color: #FFF;
    line-height: 1.428571429;
    
}
	span{
	margin-top:20px;
	margin-left:10px;
	font-size:15px;
}
</style>
<head>
<title>最新消息列表 - listOfAllNewsByStore.jsp</title>
</head>
<body bgcolor='white'>


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
					<li><a href="<%=request.getContextPath()%>/frontend/news/listOfAllNewsByStore.jsp">最新消息</a></li>
					<li><a href="findStore.html">搜尋店家</a></li>
					<li><a href="spndcoffee.html">寄杯</a></li>
					<li><a href="takeout.html">外帶外送</a></li>
					<li><a href="storeProd.html">購物</a></li>
					<li><a
						href="<%=request.getContextPath()%>/frontend/activity/activityList.jsp"">活動</a></li>
				</ul>
				<!-- 右選單 -->
				<ul class="nav navbar-nav navbar-right">
					<li><a>${displayName}</a></li>
					<c:if test="${isVisitor}">
						<li><a
							href="${pageContext.request.contextPath}/frontend/member/login_Member.jsp">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;會員登入&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
						<li><a
							href="${pageContext.request.contextPath}/frontend/store/login_Store.jsp">
								&nbsp;&nbsp;店家登入&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
					</c:if>
					<li><c:choose>
							<c:when test="${isVisitor}">
							</c:when>
							<c:when test="${isMember}">
								<a
									href="${pageContext.request.contextPath}/frontend/member/select_page.jsp">
									&nbsp;&nbsp;個人設定&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
							</c:when>
							<c:when test="${isStore}">
								<a
									href="${pageContext.request.contextPath}/frontend/store/select_page.jsp">
									&nbsp;&nbsp;個人設定&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
							</c:when>
						</c:choose></li>
					<li class="nav-login-out"><c:choose>
							<c:when test="${isVisitor}">
							</c:when>
							<c:when test="${isMember}">
								<a
									href="${pageContext.request.contextPath}/frontend/member/logout_Member.jsp">
									&nbsp;&nbsp;登出&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
							</c:when>
							<c:when test="${isStore}">
								<a
									href="${pageContext.request.contextPath}/frontend/store/logout_Store.jsp">
									&nbsp;&nbsp;登出&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
							</c:when>
						</c:choose></li>
				</ul>
			</div>
			<!-- 手機隱藏選單區結束 -->
		</div>
	</nav>
	<!-- nav end -->
	<!-- nav end -->

	<div class="container bot" id="container">


		<div class="row">

			<div class="section-title" style="font-size:35px;">最新消息</div>
			<hr class="col-xs-12">

			<div class="col-xs-12 col-sm-2">
				<div class="panel-group" id="accordion2" role="tablist"
					aria-multiselectable="true">

					
					<!-- 區塊1 -->
					<div class="panel panel-default">
						<div class="panel-heading" role="tab" id="panel1">
							<h4 class="panel-title">
								<a href="<%=request.getContextPath()%>/frontend/news/listOfAllNewsByStore.jsp"  >
									所有</a>
							</h4>
						</div>
						
					</div>
					
					
					<!-- 區塊2 -->
					<div class="panel panel-default">
						<div class="panel-heading" role="tab" id="panel1">
							<h4 class="panel-title">
								<a href="<%=request.getContextPath()%>/news/news.do?news_class=XXX&action=getNewsByClass"  >
									咖啡知識 </a>
							</h4>
						</div>
						
					</div>

					<!-- 區塊3 -->
					<div class="panel panel-default">
						<div class="panel-heading" role="tab" id="panel2">
							<h4 class="panel-title">
								<a href="<%=request.getContextPath()%>/news/news.do?news_class=YYY&action=getNewsByClass" > 
								    優惠活動</a>
							</h4>
						</div>
						
					</div>
					<!-- 區塊4 -->
					<div class="panel panel-default">
						<div class="panel-heading" role="tab" id="panel3">
							<h4 class="panel-title">
								<a href="<%=request.getContextPath()%>/news/news.do?news_class=ZZZ&action=getNewsByClass" >
								 新店成立 </a>
							</h4>
						</div>
						
					</div>
				</div>
			</div>
	
	<!------------------------- 最新消息列表 --------------------------->
		
			<div class="col-xs-12 col-sm-10">

				
				<div class="news-list">

					<c:forEach var="newsVO" items="${newsList}">
						
						<p class="news-box-inside" style="margin-left:10px; ">
							<span class="news-time" ><fmt:formatDate pattern="yyyy-MM-dd" value="${newsVO.news_date}" /></span>
							<span style="color: #ff0000;"> ${newsVO.news_class}</span>
							<span><a href="<%=request.getContextPath()%>/news/news.do?news_id=${newsVO.news_id}&action=getFrontNewsContent"
									class="news-title" data-id="17700" target="_blank">${newsVO.news_title}</a></span>
								
							<span style="position:absolute; right:0px;">
								<i>${storeSvc.getOneStore(newsVO.store_id).store_name}</i>
							</span>
						</p>
						<hr class="col-xs-12"><br>
					</c:forEach>

				</div>

			</div>
		<!------------------------- 最新消息列表 --------------------------->
			
			<div class="col-xs-12 col-sm-6 col-sm-offset-2"></div>


		</div>

	</div>
	
	
	
	<footer class="footer text-center" style="margin-top:30px;">
		<div class="container">
			<div class="row1">
				<span>常見問題 | </span> <span>功能介紹 | </span> <span>關於我們 | </span> <span>客服中心</span>
			</div>
			<center style="margin-top: 10px; margin-bottom: 10px">BA101第四組</center>
		</div>
		</div>
	</footer>
	
	
</body>
</html>
<script src="https://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
$(document).ready(function () {

	var container_top = $("#container").offset().top;
	var ready_win_height = $(window).height();

	$("#container").css("height", (ready_win_height - container_top));

	$(window).resize(function () {

		var resize_win_height = $(window).height();
		$("#container").css("height", (resize_win_height - container_top));
	});
});


</script>