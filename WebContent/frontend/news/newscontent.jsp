<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.store_tag.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.LinkedHashSet" %>

<%@ include file="/pages/isVisitor.jsp" %>

<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />

<!DOCTYPE html>
<html>
<style type="text/css">
	.footer{
	grid-row-start: 2;
    grid-row-end: 3;
    background-color: #333;
    color: #FFF;
    line-height: 1.428571429;
    
}
	
	.event-intro-item {
    padding-left: 10px;
    list-style: none;
}
	.event-intro-item li {
    display: inline-block;
}
	.redactor-box {
    position: relative;
    word-wrap: break-word;
    word-break: break-word;
}
	.col-xs-12 {
    position: relative;
    min-height: 1px;
    padding-left: 10px;
    padding-right: 10px;
}
	.lineCaption {
    text-align: center;
    color: #757575;
    font-size: 18px;
    margin-top: 20px;
    margin-bottom: 20px;
    height: 1px;
    border-top: 1px solid #ddd;
}
	.lineCaption span {
    position: relative;
    top: -8px;
    background: #fff;
    padding: 0 20px;
}
	.event-view-title h2 {
    margin: 0;
    padding: 10px 0;
    min-height: 48px;
    font-size: 28px;
    line-height: 28px;
    text-align: center;
}
	.center {
    width: auto;
    display: table;
    margin-left: auto;
    margin-right: auto;
}

	.float-left {
	    float: left;
	}
</style>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<head>
<title>消息內容 - newscontent.jsp</title>
</head>
<body bgcolor='white'>

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


		
	<div class="container" id="container">
		<hr class="col-xs-12">
		<div class="center">
			<img class="product" style="text-align: center; width:800px; height:600px;"
				src="<%=request.getContextPath()%>/images?action=news&id=${newsVO.news_id}">
		</div>

		<div class="event-view-title">
			<h2>${newsVO.news_title}</h2>
		</div>
		
		<div class="event-section apcss-event-view-introduction-section">
			<div class="col-xs-12 col-sm-8 col-sm-offset-1">
				<div class="row">
					<ul class="event-intro-item">
						<jsp:useBean id="now" scope="page" class="java.util.Date" />
						<li class="item-title"><i class="glyphicon glyphicon-time"></i>
							<span>發佈日期：</span>
						<li class="item-content"><fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${newsVO.news_date}"/></li>
						</li>
					</ul>

					<ul class="event-intro-item">
						<li class="item-title"><i
							class="glyphicon glyphicon-map-marker"></i> <span
							class="intro-title">發佈店家： </span></li>
						<li class="item-content">${storeSvc.getOneStore(newsVO.store_id).store_name}</li>
					</ul>
					<ul class="event-intro-item">
						<li class="item-title"><i
							class="glyphicon glyphicon-tags"></i> <span
							class="intro-title">消息類別：</span></li>
						<li class="item-content">${newsVO.news_class}</li>
					</ul>
					
				</div>
			</div>
			
			
		</div>
		
	
	
		<div class="view-information">
			<div class="row">
				<div class="col-xs-12">
				<div class="collapse" id="cc1" >
							<div id="myMap" style="width:970px;height:400px;" ></div>
			</div>
					<h3 class="lineCaption">
						<span>消息內容</span>
					</h3>
					<div class="redactor-box">
						<c:if test="${not empty newsVO}">
							${newsVO.news_content}
						</c:if>
					</div>
				</div>

				
			</div>
		</div>
	
	</div>
	
		
		<!-- container end -->
			<footer class="footer  text-center" >
				<div class="col-xs-12 col-sm-3">常見問題</div>
				<div class="col-xs-12 col-sm-3">功能介紹</div>
				<div class="col-xs-12 col-sm-3">關於我們</div>
				<div class="col-xs-12 col-sm-3">客服中心</div>
				<center style="margin-top: 10px;">BA101第四組</center>
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