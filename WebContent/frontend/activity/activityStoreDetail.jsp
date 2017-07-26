<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.participant.model.*"%>
<%@ page import="com.rept_activ.model.*"%>
<%@ page import="com.activity.model.*"%>

<%@ include file="/pages/isVisitor.jsp" %>

<!DOCTYPE html>
<%
	Rept_activVO rept_activVO = (Rept_activVO) request.getAttribute("rept_activVO");
%>

<%
	ActivityVO activityVO = (ActivityVO) request.getAttribute("activityVO"); //EmpServlet.java (Concroller), 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>
<jsp:useBean id="activitySvc" scope="page"
	class="com.activity.model.ActivityService" />
<jsp:useBean id="storeSvc" scope="page"
	class="com.store.model.StoreService" />
<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService" />
<%-- <% session.setAttribute("MEMBER", memSvc.getOneMember("MEM00000001")); %> --%>
	<!-- 	抓連入頁面的的活動編號 -->
	<%
		String activ_id = request.getParameter("activ_id");
		session.setAttribute("activ_id", activ_id);
	%>

<html lang="">

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
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/activity/css/activityStoreDetail.css">
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
	<hr class="col-xs-12">
	<div class="center">
		<img class="product" style="text-align: center;"
			src="<%=request.getContextPath()%>/images?action=activity&id=${activityVO.activ_id}">
	</div>
	<div class="title center">
		<h2>${activityVO.activ_name}</h2>
	</div>
	<hr class="col-xs-12 col-sm-12">
	<div class="container apcss-event-container">
		<div class="row">
			<div class="col-xs-12 col-sm-12">
				<div class="event-section apcss-event-view-introduction-section">
					<ul class="event-intro-item">
						<li class="item-title"><i class="glyphicon glyphicon-time"></i>
							<span>活動日期：</span></li>
						<jsp:useBean id="now" scope="page" class="java.util.Date" />
						<li class="item-content"><fmt:formatDate
								pattern="yyyy-MM-dd HH:mm" value="${activityVO.activ_starttime}" />
							~ <fmt:formatDate pattern="yyyy-MM-dd HH:mm"
								value="${activityVO.activ_endtime}" /></li>
					</ul>
				</div>
			</div>
			<div class="col-xs-12 col-sm-9 col-md-10">
				<div class="event-section apcss-event-view-introduction-section">
					<ul class="event-intro-item">
						<li class="item-title"><i
							class="glyphicon glyphicon-map-marker"></i> <span
							class="intro-title">活動地點：</span></li>
						<li class="item-content"><c:forEach var="storeVO"
								items="${storeSvc.all}">
								<c:if test="${activityVO.store_id==storeVO.store_id}">${storeVO.store_name}</c:if>
							</c:forEach></li>
					</ul>
				</div>
			</div>
			<div class="col-xs-12 col-sm-3 col-md-2">
				<div class="event-section apcss-event-view-introduction-section">
					<p class="text-right">
						<a href="#signUp" class="btn btn-cofe">確認通過</a>
				</div>
			</div>

			<div class="col-xs-12 col-sm-12">
				<div class="event-section apcss-event-view-introduction-section">
					<h3 class="lineCaption">
						<span>活動介紹</span>
					</h3>
					<div class="col-xs-12 col-sm-10 col-sm-offset-1">
						<div>${activityVO.activ_intro}</div>
					</div>
				</div>
				<hr class="col-xs-12" name="signUp">
				<FORM METHOD="post" ACTION="activity.do" name="form1"
					enctype="multipart/form-data">
					<input type="hidden" name="activ_id"
						value="<%=activityVO.getActiv_id()%>" /> <input type="hidden"
						name="mem_id" value="<%=activityVO.getMem_id()%>" /> <input
						type="hidden" name="store_id"
						value="<%=activityVO.getStore_id()%>" /> <input type="hidden"
						name="activ_name" value="<%=activityVO.getActiv_name()%>" /> <input
						type="file" name="prod_img" onchange="file_change()"
						value="<%=request.getContextPath()%>/images?action=activity&id=${activityVO.activ_id}">
					<input type="hidden" name="activ_starttime"
						value="<%=activityVO.getActiv_starttime()%>" /> <input
						type="hidden" name="activ_endtime"
						value="<%=activityVO.getActiv_endtime()%>" /> <input
						type="hidden" name="activ_expire"
						value="<%=activityVO.getActiv_expire()%>" /> <input type="hidden"
						name="activ_summary" value="<%=activityVO.getActiv_summary()%>" />
					<input type="hidden" name="activ_intro"
						value="<%=activityVO.getActiv_intro()%>" /> <input type="hidden"
						name="activ_num" value="<%=activityVO.getActiv_num()%>" /> <input
						type="hidden" name="action" value="update"> <input
						type="submit" class="btn btn-primary btn-block center"
						value="確認通過"> <input type="hidden" value="1"
						name="activ_store_cfm">
				</FORM>
			</div>
		</div>
	</div>
	<div>
		<div
			class="event-section2-inverted apcss-event-view-discuss-section text-left center">
			<!-- 			<a href="../rept_activ/addRept_activ.jsp" class="block negative-link text-center " ng-click="showReport()"> -->
			<a href="#" class="block negative-link text-center " role="button"
				data-toggle="modal" data-target="#login-modal"> <i
				class="glyphicon glyphicon-thumbs-down"></i>婉拒此活動
			</a>
			<%--             <input type="hidden" name="activ_id" value="${activityVO.activ_id}">  --%>
		</div>


		<!-- 檢舉燈箱-->
		<div class="modal fade in" id="login-modal" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
			style="display: none;">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header" align="center">
						<a class="modal-close" aria-hidden="true" data-dismiss="modal">×</a>
						<h4 class="modal-title">婉拒此活動</h4>
					</div>
					<div class="modal-body">
						<p class="text-center">請協助提供您婉拒此活動的原因</p>
						<h4 class="text-center event-report-subtitle">婉拒原因</h4>
						<div class="radio-box">
							<input type="radio" name="repo_rsn" value="活動資訊有誤" id="report1">
							<label for="report1">活動資訊有誤</label>
						</div>
						<div class="radio-box">
							<input type="radio" name="repo_rsn" value="我認為這不應該出現在這"
								id="report2"> <label for="report2">我認為這不應該出現在這</label>
						</div>
						<div class="radio-box">
							<input type="radio" name="repo_rsn" value="這是有暴力、色情等成人內容的活動"
								id="report3"> <label for="report3">這是有暴力、色情等成人內容的活動</label>
						</div>
						<div class="radio-box">
							<input type="radio" name="repo_rsn" value="這是詐騙、非法活動"
								id="report4"> <label for="report4">這是詐騙、非法活動</label>
						</div>
						<div class="radio-box">
							<input type="radio" name="repo_rsn" value="其他" id="report5">
							<label for="report5">其他</label>
						</div>
						<textarea class="evnet-report-advance" placeholder="請填寫婉拒原因"></textarea>
						<FORM METHOD="post" ACTION="activity.do" name="form1" enctype="multipart/form-data">
							<input type="hidden" name="action" value="update_cfm"> 
							<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>"> 
							<input type="hidden" name="activ_id" value="<%=activityVO.getActiv_id()%>" /> 
								<input type="hidden" name="mem_id" value="<%=activityVO.getMem_id()%>" /> 
								<input type="hidden" name="store_id" value="<%=activityVO.getStore_id()%>" /> 
								<input type="submit" class="btn btn-primary btn-block center" value="確認婉拒"> 
								<input type="hidden" value="2" name="activ_store_cfm">
						</FORM>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 檢舉燈箱 -->

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
