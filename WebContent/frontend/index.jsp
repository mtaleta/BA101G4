<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.store.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/pages/isVisitor.jsp" %>

<c:set var="context" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>CoffeePuzzle</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<!--[if lt IE 9]>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/indexbar.css">
	<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script>
window.onload=ShowHello;
 function ShowHello(){
	$("#title1").fadeTo(1500, 1);
	$("#title2").fadeTo(1500, 1);
	$("#title3").fadeTo(1500, 1);
    $("#banner").fadeTo(2300, 1);
  }
</script>

</head>
<style>
 body { 
 background-image: url(welcome.jpg); 
 background-size: 100%; 
 } 

	#banner {
		padding: 8em 0 6em 0;
		align-items: center;
		display: flex; 
		justify-content: center;
		background-position: center;
		background-size: cover;
		background-repeat: no-repeat;
		background-attachment: fixed;
		border-top: 0;
		min-height: 100vh;
		height: 100vh;
		position: relative;
		text-align: center;
		overflow: hidden;
		width:100%;
 		opacity:0; 
	}
	aa{
	    transition: opacity 1s ease, transform 1s ease;
    opacity: 1;
    position: relative;
    z-index: 2;
	}
	.searchs{
	    width: 100%;
	}
	#title1 {
 	opacity:0; 
	}
	#title2 {
 	opacity:0; 
	}
	#title3{
 	opacity:0; 
	}
</style>
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
	<!-- nav end -->
	<div class="row">
		<section id="banner" class="bg-img" style="background-image:url(${pageContext.request.contextPath}/img/temp2/toa-index.jpg);">
			<div>
				<header style="margin-bottom:400px;" >
					<p class="titlesearch"  id="title1">CoffeePuzzle </p>
                    <form method="post" action="${context}/frontend/store/store.do">
						<input type="text" id="title2" name="area_or_name" class="searchs form-control" placeholder="嚐嚐新味道">
						<input type="hidden" name="action" value="findStoreByAreaOrName">
					</form>
                    <p style="font-size: 20px;float: right;margin-top: 10px" id="title3">everyone’s invited.</p>
				</header>
			</div>
		</section>
	</div>
	<jsp:useBean id="newsSvc" scope="page" class="com.news.model.NewsService" />
	<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
	
	<div class="container bot" id="newsBar">
		<div class="row">
			<div class="section-title">最新消息</div>
			<hr class="col-xs-12">
			<div class="col-xs-12 col-sm-6">
				<div class="dynamic-title1">最新消息</div>
				<div class="news-list">
					
					<c:forEach var="newsVO" items="${newsSvc.littlePassNews}">
						<div class="news-item">
							<div class="decoration">
								<div class="news-circle">
									<div class="solid-circle"></div>
								</div>
							</div>
							<div class="news-box">
								<a href="<%=request.getContextPath()%>/news/news.do?news_id=${newsVO.news_id}&action=getFrontNewsContent" class="news-title"
									data-id="17700" target="_blank">${newsVO.news_title}</a>
								<div class="news-time"><fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${newsVO.news_date}"/></div>
								<div style="margin-left:350px;"><i>by~${storeSvc.getOneStore(newsVO.store_id).store_name}</i></div>
							</div>
						</div>
					</c:forEach>
					
				</div>
				<div class="more-news">
					<a href="<%=request.getContextPath()%>/frontend/news/listOfAllNewsByStore.jsp" target="_blank"> <span class="more-news-tip">更多消息
					</span><img
						src="${pageContext.request.contextPath}/img/temp2/right-circle.png"
						class="more-img"> <!-- <img data-original="/Public/img/gray_right.png" alt="" class="more-news-img "> -->
					</a>
				</div>
			</div>
			
			<div class="col-xs-12 col-sm-6 col-sm-offset-2"></div>
		</div>
	</div>
	
	
	
	<!--晨均晨均    廣告區廣告區   晨均晨均    廣告區廣告區  晨均晨均    廣告區廣告區  晨均晨均    廣告區廣告區  晨均晨均    廣告區廣告區  -->
	<div class="section-title">CoffePuzzle好康報報</div>	
		<hr class="col-xs-12">
	<jsp:include page="/frontend/advertisment/advertisment.jsp" />
	
	
	
	<!--晨均晨均    廣告區廣告區   晨均晨均    廣告區廣告區  晨均晨均    廣告區廣告區  晨均晨均    廣告區廣告區  晨均晨均    廣告區廣告區  -->
	
	
	
	
	<footer class="footer text-center" style="margin-top:30px;">
		<div class="container">
			<div class="row">
				<span>常見問題 | </span> <span>功能介紹 | </span> <span>關於我們 | </span> <span>客服中心</span>
			</div>
			<center style="margin-top: 10px; margin-bottom: 10px">BA101第四組</center>
		</div>
	</footer>
	<script src="https://code.jquery.com/jquery.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>
