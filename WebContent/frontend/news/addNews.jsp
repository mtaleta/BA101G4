<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.news.model.*"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
NewsVO newsVO = (NewsVO) request.getAttribute("newsVO");
StoreVO storeVO = (StoreVO) session.getAttribute("STORE");

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
	<link rel="stylesheet" type="text/css" href="js/calendar.css">
	<script language="JavaScript" src="js/calendarcode.js"></script>
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
<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/news/news.do" name="form1" enctype="multipart/form-data">
<table border="0">

	<tr>
		<td></td>
		<td><img id="image" ></td>
	</tr>
	
	<tr>
		<td>上傳圖片</td>
		<td><input type="file" name="news_img" onchange="file_change()"></td>
	</tr>
	
	<tr>
		<td>標題:</td>
		<td><input type="TEXT" name="news_title" size="45" 
			value="<%=(newsVO==null)? "" : newsVO.getNews_title()%>" /></td>
	</tr>
	
	<tr>
		<td>消息內文:</td>
		<td><textarea class="ckeditor" cols="80" id="content" name="news_content" rows="12"
						value="<%=(newsVO==null)? "" : newsVO.getNews_content()%>"></textarea></td>
	</tr>
	
	<tr>
		<td>消息類別:<font color=red><b>*</b></font></td>
		<td><select size="1" name="news_class">
				<option value="咖啡知識" >咖啡知識
				<option value="優惠活動" >優惠活動
				<option value="新店成立" >新店成立
			</select>
		</td>
	</tr>
	
	
	
	<%-- <tr>
		<%java.sql.Date date_SQL = new java.sql.Date(System.currentTimeMillis());%>
		
	</tr> --%>
	
	<!-- <tr><textarea class="ckeditor" cols="80" id="content" name=content rows="12"></textarea></tr> -->

	

</table>
<br>

<input type="hidden" name="action" value="insertNews">
<input type="submit" value="送出新增">
</FORM>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>
<script type="text/javascript">

function file_change() {
	//file = document.getElementById('upfile1').files[0];
	file = document.getElementsByName("news_img");

	//if (file) {
	if (file[0]) {
		fileReader = new FileReader();
		fileReader.onload = openfile;
		//fileReader.readAsDataURL(file);
		fileReader.readAsDataURL(file[0].files[0]);
	}
}
function openfile(event) {
	document.getElementById('image').src = event.target.result;
	result;
}
	
</script>