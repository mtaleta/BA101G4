<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.activity.model.*"%>

<%@ include file="/pages/isVisitor.jsp" %>

<%
	ActivityService activitySvc = new ActivityService();
	List<ActivityVO> list = (List<ActivityVO>)request.getAttribute("Search_Activity");
	pageContext.setAttribute("list",list);
%>
<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService" />
<%-- <% session.setAttribute("MEMBER", memSvc.getOneMember("MEM00000001")); %> --%>
<!DOCTYPE html>
<%-- <jsp:useBean id="activitySvc" scope="page" class="com.activity.model.ActivityService" />  --%>
<%-- <jsp:useBean id="storeSvc" scope="page" --%>
<%-- 	class="com.store.model.StoreService" /> --%>
<html>
<head>
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/activity/css/activitySearch.css">

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
					<a
						href="<%=request.getContextPath()%>/frontend/activity/addActivity.jsp"
						class="btn btn-cofe">辦活動</a>
				</p>
			</div>
			<hr class="col-xs-12">
			<div class="row">
				<%@ include file="../pages/page1.file"%>
				<c:forEach var="activityVO" items="${list}" begin="<%=pageIndex%>"
					end="<%=pageIndex+rowsPerPage-1%>">
					<c:if test="${activityVO.activ_store_cfm eq '1'}">
					<div class="col-xs-12 col-sm-4">
						<div class="activity-card">
							<div class="row">
								<div class="col-xs-12 col-sm-12 activity-header">
																		<a href="<%=request.getContextPath()%>/frontend/activity/activity.do?action=getOne_For_Activity&activ_id=${activityVO.activ_id}">
								
									<img class="activity-image "
										src="<%=request.getContextPath()%>/images?action=activity&id=${activityVO.activ_id}">
								</div>
								<div class="col-xs-12 col-sm-12">
									<div class="activity-body">
										<a href="<%=request.getContextPath()%>/frontend/activity/activity.do?action=getOne_For_Activity&activ_id=${activityVO.activ_id}">
										<h3 class="textsizeb activity-card-title">${activityVO.activ_name}</h3>
										</a>
										<jsp:useBean id="now" scope="page" class="java.util.Date" />
										<p class="activity-date">
											<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
												value="${activityVO.activ_starttime}" />
											~
											<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
												value="${activityVO.activ_endtime}" />
										</p>
										<p>${activityVO.activ_summary}</p>
									</div>
								</div>
								<div class="activity-footer">
								<div class="text-center">
									<a class="btn btn-cofe" href="<%=request.getContextPath()%>/frontend/activity/activity.do?action=getOne_For_Activity&activ_id=${activityVO.activ_id}">活動詳情</a>
									
								</div>
								</div>
							</div>
						</div>
					</div>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</div>

	<!-- row end -->
	<div class="text-center">

		<ul class="pager">
			<%@ include file="../pages/page2.file"%>
		</ul>
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
