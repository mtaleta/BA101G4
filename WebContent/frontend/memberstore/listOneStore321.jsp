<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.store_tag.model.*"%>
<%@ page import="com.tag.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.LinkedHashSet"%>

<%@ include file="/pages/isVisitor.jsp" %>

<%
	TagVO tagVO = (TagVO) request.getAttribute("TagVO");
	//EmpServlet.java(Concroller), 存入req的empVO物件
%>


<jsp:useBean id="tagDAO" scope="page" class="com.tag.model.TagDAO" />
<jsp:useBean id="store_tagDAO" scope="page"
	class="com.store_tag.model.Store_tagDAO" />
<html>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<style type="text/css">
.footer {
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
	.rd-nav_main-item{
    position: static;
    border-right: none;
    border-left: none;
    height: auto;
    box-shadow: 0 2px 1px 1px rgba(0, 0, 0, 0.2) inset;
    background-color: #603813;
    background-image: none;
        float: left;
    padding: 18px 0px 0 0px;
    width: 20%;
}
	.rd-nav{
    display: block;
    position: relative;
    z-index: 10;
    border-top: 1px solid #d6ceb4;
    border-bottom: 5px solid #603813;
    background-color: #ffffff;
    margin-top: 30px;
}
	.rd-nav-wrapper{
    margin-top: -30px;
    margin-bottom: 15px;
    padding: 0 10px;
    padding-right: 0;
    padding-left: 0;
}
.rd-nav_main-target{
    border-left: none;
    color: #ffffff;
    text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.2);
    height: 3em;
    display: block;
    position: relative;
    z-index: 2;
    cursor: pointer;
    margin: -17px 0px 0 0px;
    padding: 17px 0px 0 0px;
    text-align: center;
}
	.nav {
    padding-left: 0;
    margin-bottom: 0;
    list-style: none;
}
		
</style>

<head>
<title>店家資料 - listOneStore.jsp</title>
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
	<!-- nav end -->
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-sm-6 col-sm-offset-3">
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/store/store.do" >
				<div class="input-group">
					
					<input type="text" class="searchs form-control" placeholder="請輸入關鍵字" name="store_add_or_name">
					<input type="hidden" name="action" value="getStores_BY_Store_ADD_OR_NAME">
					<span class="input-group-btn" >
						<button class="btn btn-cofe" type="submit" >店家搜尋</button>
					</span>
					
				</div></FORM>
			</div>
			<div class="col-xs-12 col-sm-12">
				<ol class="breadcrumb">
					<li><a href="#">首頁</a></li>
					<li><a href="#">店家資訊</a></li>
					<li class="active">${storeVO.store_name}</li>
				</ol>
			</div>
			<div class="col-xs-12 col-sm-12">
				<div class="rd-header l-container">
					<div class="rd-header_headline award-badge">
						<h2 class="rd-header_rst-name">
							<a href="#" class="rd-header_rst-name-main">${storeVO.store_name}</a>
						
							<div >
							<c:if test="${fav_storeVOcheck.store_id eq storeVO.store_id}">
			 				 	<a href="<%=request.getContextPath()%>/fav_store/fav_store.do?store_id=${storeVO.store_id}&action=delete_Favstore">
								<img src="<%=request.getContextPath()%>/img/heart_red.png" id="heart" title="取消收藏">
								 </a> 
							 </c:if>
			 
			 				<c:if test="${fav_storeVOcheck.store_id eq null}">
								 <a href="<%=request.getContextPath()%>/fav_store/fav_store.do?store_id=${storeVO.store_id}&action=insert_Favstore">
									<img src="<%=request.getContextPath()%>/img/heart_white.png" id="heart" title="加入收藏">
			 						</a> 
							 </c:if>
						
							</div>
						</h2>
						
					</div>

					<div class="event-section apcss-event-view-introduction-section">
						<div class="col-xs-12 col-sm-8 col-sm-offset-1">

							<div class="store_pic">
								<img
									src="<%=request.getContextPath()%>/images?action=store&id=${storeVO.store_id}">
							</div>
							<div class="row">

								<ul class="event-intro-item">
									<li class="item-title"><i
										class="glyphicon glyphicon-map-marker"></i> <span
										class="intro-title">店家地址： </span></li>
									<li class="item-content">${storeVO.store_add}</li>
								</ul>

								<ul class="event-intro-item">
									<li class="item-title"><i
										class="glyphicon glyphicon-map-marker"></i> <span
										class="intro-title">電話： </span></li>
									<li class="item-content">${storeVO.store_tel}</li>
								</ul>
							</div>
							
							<div>
								<%if (request.getAttribute("store_tagVO")!=null){%>
       								<jsp:include page="/frontend/store_tag/listOfStore_tag.jsp" />
								<%} %>
							</div>
						</div>


					</div>
				</div>
			</div>
			<div class="container">
				<div class="row">
					<div class="col-xs-12 col-sm-12">
						<div role="tabpanel" class="rd-nav-wrapper">
							<!-- 標籤面板：標籤區 -->
							<ul class="nav nav-tabs rd-nav" role="tablist">
								<li role="presentation" class="active rd-nav_main-item"><a
									href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab"
									class="rd-nav_main-target"><i
										class="glyphicon glyphicon-heart"></i>店家首頁</a></li>
								<li role="presentation" class="rd-nav_main-item"><a
									href="#tab2" aria-controls="tab2" role="tab" data-toggle="tab"
									class="rd-nav_main-target"><i
										class="glyphicon glyphicon-camera"></i>相片</a></li>
								<li role="presentation" class="rd-nav_main-item"><a
									href="#tab3" aria-controls="tab3" role="tab" data-toggle="tab"
									class="rd-nav_main-target"><i
										class="glyphicon glyphicon-pencil"></i>訪客評價</a></li>
								<li role="presentation" class="rd-nav_main-item"><a
									href="#tab3" aria-controls="tab3" role="tab" data-toggle="tab"
									class="rd-nav_main-target"><i
										class="glyphicon glyphicon-map-marker"></i>地圖</a></li>
								<li role="presentation" class="rd-nav_main-item"><a
									href="#" class="rd-nav_main-target " role="button"
									data-toggle="modal" data-target="#login-modal1"><i
										class="glyphicon glyphicon-paperclip"></i>新增標籤</a></li>
							</ul>
							
<!-- 標籤燈箱--> <!-- 標籤燈箱--> <!-- 標籤燈箱--> <!-- 標籤燈箱-->
	<div class="modal fade in" id="login-modal1" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" align="center">
					<a class="modal-close" aria-hidden="true" data-dismiss="modal1">×</a>
					<h4 class="modal-title">新增標籤</h4>
				</div>
				<div class="modal-body">
					<p class="text-center">請標籤內容</p>
					
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/tag/tag.do"
						name="form1">
						<div class="radio-box">
							<label for="report1">標籤內容</label>
							<input type="TEXT" name="tag_content" size="45" value="<%= (tagVO==null)? "" :tagVO.getTag_content()%>" />
							
						</div>
						
						
						<input type="submit" value="提交" class="btn btn-cofe btn-block">
						<input type="hidden" name="action" value="insertStore_tag"> 
						<input type="hidden" name="store_id" value="${storeVO.store_id}" />
						
					</FORM>
				</div>
			</div>
		</div>
	</div>
	<!-- 標籤燈箱 --> <!-- 標籤燈箱--> <!-- 標籤燈箱-->

							<!-- 照片面板：內容區 -->
							<div class="tab-content">
								<div role="tabpanel" class="tab-pane active" id="tab1">
									<div class="c-heading">
										<h3 class="c-heading-title">這邊是店內照片</h3>
									</div>
									<div class="rd-notice" align="center">

									<%
										if (request.getAttribute("storeVO") != null) {
									%>
									<jsp:include page="/frontend/store/PhotoStore.jsp" />
									<%
										}
									%></div>

									<div class="rd-more-link" align="right">
										<a href="#" class="c-link-circle"> <span>更多圖片</span>
										</a>
									</div>
									
									<hr class="col-xs-12" style="border: 10;border-top: 2px solid #CCC;">
									
									<div class="c-heading">
										<h3 class="c-heading-title">店家評分及留言</h3>
									</div>
									<div class="rd-notice">
										<p class="u-color-notice">
											這些評論資訊，是用戶基於用餐當時狀況所寫的主觀意見及感想。請當作參考並活用此情報。</p>
									</div>
										
									<div>
										<%if (request.getAttribute("storeVO")!=null){%>
      									 <jsp:include page="/frontend/rate_n_rev/listOfRate_n_rev.jsp" />
										<%} %>
									</div>
								</div>
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>


	<footer class="footer text-center">
		<div class="col-xs-12 col-sm-3">常見問題</div>
		<div class="col-xs-12 col-sm-3">功能介紹</div>
		<div class="col-xs-12 col-sm-3">關於我們</div>
		<div class="col-xs-12 col-sm-3">客服中心</div>
		<center style="margin-top: 10px;">BA101第四組</center>
	</footer>


</body>
</html>

<script src="https://code.jquery.com/jquery.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	