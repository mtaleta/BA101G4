<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.participant.model.*"%>
<%@ page import="com.activity.model.*"%>
<%@ page import="java.util.*"%>

<%@ include file="/pages/isVisitor.jsp" %>

<%
	ParticipantService participantSvc = new ParticipantService();
	Set<ParticipantVO> list = (Set<ParticipantVO>) request.getAttribute("participantVO");
	pageContext.setAttribute("list", list);
%>
<%
	ActivityService activitySvc = new ActivityService();
	pageContext.setAttribute("activitySvc", activitySvc);
%>
<%-- 此頁練習採用 EL 的寫法取值 --%>
<jsp:useBean id="now" scope="page" class="java.util.Date" />
<jsp:useBean id="memberSvc" scope="page"
	class="com.member.model.MemberService" />
<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService" />
<%-- <% session.setAttribute("MEMBER", memSvc.getOneMember("MEM00000001")); %> --%>
<!DOCTYPE html>
<html lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>票卷管理</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<!--[if lt IE 9]>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/frontend/css/main.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/activity/css/myActivity.css">
<style type="text/css">

</style>
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
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<h2>我的活動</h2>
			</div>
			<div class="col-xs-12 col-sm-9"></div>
			<div class="col-xs-12 col-sm-12">
				<div role="tabpanel">
					<!-- 標籤面板：標籤區 -->
					<ul class="nav nav-tabs tab-nav" role="tablist">
						<li role="presentation" class="active"><a href="#tab1"
							aria-controls="tab1" role="tab" data-toggle="tab">已報名(${list.size()})</a></li>
					</ul>



					<!-- 標籤面板：內容區 -->
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active" id="tab1">
							<c:forEach var="participantVO" items="${list}">
								<div class="ticketBox">
									<div class="info">
										<span class="categoryTag">
											<div class="row">
												<div class="col-xs-12 col-sm-12 col-md-7">
													<c:forEach var="activityVO" items="${activitySvc.all}">
														<a
															href="<%=request.getContextPath()%>/frontend/activity/activity.do?activ_id=${activityVO.activ_id}&&action=getOne_For_Activity"
															class="text-primary h4"> <c:if
																test="${participantVO.activ_id==activityVO.activ_id}"> ${activityVO.activ_name}</c:if>
														</a>
													</c:forEach>
													<p class="date">
														<c:forEach var="activityVO" items="${activitySvc.all}">
															<c:if
																test="${participantVO.activ_id==activityVO.activ_id}">
																<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
																	value="${activityVO.activ_starttime}" />
															</c:if>
														</c:forEach>
														~
														<c:forEach var="activityVO" items="${activitySvc.all}">
															<c:if
																test="${participantVO.activ_id==activityVO.activ_id}">
																<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
																	value="${activityVO.activ_endtime}" />
															</c:if>
														</c:forEach>
													</p>
													<p class="ticketid">活動編號：${participantVO.activ_id}</p>

												</div>
												<div class="col-xs-12 col-sm-12 col-md-5">
													<div class="ticketStatus">
													<c:forEach var="activityVO" items="${activitySvc.all}">
													<c:if
																test="${participantVO.activ_id==activityVO.activ_id}">
														<a
															href="<%=request.getContextPath()%>/frontend/activity/activity.do?activ_id=${activityVO.activ_id}&&action=getOne_For_Activity"
															>詳情
															</a>
															</c:if>
															</c:forEach>
														<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/participant/participant.do">
																	 	<input type="submit" value="退出"> 
																		<input type="hidden" name="activ_id" value="${participantVO.activ_id}">
																    	<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
																    	<input type="hidden" name="action"	value="delete">
																</FORM>	
													</div>
												</div>
											</div>
										</span>
									</div>
								</div>
							</c:forEach>
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
	</div>
	<script src="https://code.jquery.com/jquery.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>
