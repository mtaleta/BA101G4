<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.news.model.*"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
StoreVO storeVO = (StoreVO) session.getAttribute("STORE");
String session_store_id = storeVO.getStore_id();

NewsService newsSvc = new NewsService();
List<NewsVO> newsList = newsSvc.getAllNewsByStore_id(session_store_id);
request.setAttribute("newsList", newsList);
%>
<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
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
    <script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
                    <li class="nav-login"><%=storeVO.getStore_name()==null? "CoffeePuzzle":storeVO.getStore_name()%>&nbsp;&nbsp;您好&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
                    <li><a href="${pageContext.request.contextPath}/frontend/store/select_page.jsp">個人設定</a></li>
                    <li class="nav-login-out"><a href="${pageContext.request.contextPath}/frontend/store/logout_Store.jsp"><%= storeVO.getStore_name()==null? "":"&nbsp;&nbsp;&nbsp;&nbsp;登出"%></a></li>
                </ul>
            </div>
            <!-- 手機隱藏選單區結束 -->
        </div>
    </nav>
<div id="wrapper">
	<div id="page" class="container">
		<div id="content">
<table border='1' bordercolor='#774f34' width='700'>
	<tr>
		<th>發布店家</th>
		<th>標題</th>
		<th>日期</th>
		<th>狀態</th>
		<th>內容</th>
		<th>審核</th>
	</tr>
	<tr align='center' valign='middle'>
	
	<%-- 	<td><%=storeVO.getStore_id() %></td>
		<td><%=storeVO.getStore_name() %></td>
		<td><%=storeVO.getStore_add() %></td> --%>
		
	
		
		<%-- <%@ include file="page3.file"  %>  --%>
			
		<c:forEach var="newsList" items="${newsList}" >
		<tr align='center' valign='middle'>
			<td>${storeSvc.getOneStore(newsList.store_id).store_name}</td>
			<td>${newsList.news_title}</td>
			<td><fmt:formatDate  pattern="yyyy-MM-dd HH:mm:ss" value="${newsList.news_date}"/></td>
			<td>
				<c:if test="${newsList.news_pass eq 1}">
					通過
				</c:if>
				<c:if test="${newsList.news_pass eq 0}">
					審核中
				</c:if>
			</td>
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/news/news.do">
			    <input type="submit" value="查看"> 
			    <input type="hidden" name="news_id" value="${newsList.news_id}">
			    <input type="hidden" name="action" value="getStoreNewsContent">
			</td></FORM>
			
			<td>
				<c:if test="${newsList.news_pass eq 1}">
					<img src="<%=request.getContextPath()%>/img/OK.jpg" height='100'>
				</c:if>
				<c:if test="${newsList.news_pass eq 0}">
					<img src="<%=request.getContextPath()%>/img/passing.png" height='100'>
				</c:if>
			</td>
				
			
        </c:forEach>
        
	</tr>
	
</table>
</div>
		<div id="sidebar">
			<div class="box2">
				<div class="title">
					<h2>店家資料專區:</h2>
				</div>
				<ul class="style2">
					<%@ include file="/pages/select_store.jsp" %>
				</ul>
			</div>
		</div>
	</div>
</div>
</body>
</html>