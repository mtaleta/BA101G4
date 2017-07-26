<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>
<%
MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
%>

<%@ include file="/pages/isVisitor.jsp" %>

<!DOCTYPE html>
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>儲值點數</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/sweetalert.css">
		<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/kao.css">
		<style type="text/css">
			p{
				margin-top: 3px;
				margin-bottom: 0px;
			}

		    .section-title {
		        font-size: 30px;
		        padding-top: 60px;
		        text-align: left;
		        color: #969696;
		    }
		    
		    #creditcard{
		    	background-size: cover;
				height: 100px;
				background-image: url("${pageContext.request.contextPath}/img/creditcard.png");
			}
			
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
		</nav><!-- nav end -->
		<div class="container">
			<div class="section-title">儲值點數</div>
	        <hr class="col-xs-12">

			<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">

			</c:if>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/member/member.do" name="form1">
				<div class="col-xs-12">
					儲值金額:
					<input type="NUMBER" name="mem_points" min="1" max="999999999" size="9" maxlength="9"
						value="<%= (memberVO==null)? "1000" : memberVO.getMem_points() %>" />
				</div>
				<div class="col-xs-12">
					<br>
					驗證碼:
					<input type="TEXT" name="card_check" size="3" maxlength="3"
						value="<%= (request.getParameter("card_check") == null)? "123" : request.getParameter("card_check") %>" />
				</div>
				<div id="creditcard" class="col-xs-12">
					<div class="row">
						<div id="card_nos" class="col-xs-12 text-center">
							<input type="TEXT" id="card_no_1" class="card_no" name="card_no" size="4" maxlength="4"
								value="<%= (request.getParameterValues("card_no") == null)? "ABCD" : request.getParameterValues("card_no")[0] %>" />
							<span class="card_sub">&nbsp;</span>
							<input type="TEXT" id="card_no_2" class="card_no" name="card_no" size="4" maxlength="4"
								value="<%= (request.getParameterValues("card_no") == null)? "EFGH" : request.getParameterValues("card_no")[1] %>" />
							<span class="card_sub">&nbsp;</span>
							<input type="TEXT" id="card_no_3" class="card_no" name="card_no" size="4" maxlength="4"
								value="<%= (request.getParameterValues("card_no") == null)? "IJKL" : request.getParameterValues("card_no")[2] %>" />
							<span class="card_sub">&nbsp;</span>
							<input type="TEXT" id="card_no_4" class="card_no" name="card_no" size="4" maxlength="4"
								value="<%= (request.getParameterValues("card_no") == null)? "MNOP" : request.getParameterValues("card_no")[3] %>" />
						</div><!-- card_nos -->
						<div id="card_date" class="col-xs-12 text-center">
							<input type="TEXT" class="card_date" name="card_month" size="2" maxlength="2"
								value="<%= (request.getParameter("card_month") == null)? "07" : request.getParameter("card_month") %>" />
							<span class="card_sub">&nbsp;&nbsp;</span>
							<input type="TEXT" class="card_date" name="card_year" size="2" maxlength="2"
								value="<%= (request.getParameter("card_year") == null)? "18" : request.getParameter("card_year") %>" />
						</div>
					</div><!-- row -->
				</div><!-- creditcard -->
				<div class="col-xs-12">
					<input type="hidden" name="action" value="topUp">
					<input type="hidden" name="requestURL"
						value="<%= (request.getParameter("requestURL") == null)? request.getServletPath() : request.getParameter("requestURL")%>">
					<c:if test="${not empty param.requestURL}">
						<input type="hidden" name="store_id" value="${param.store_id}">
						<input type="hidden" name="ord_total" value="${param.ord_total}">
					</c:if>
					<input type="submit" class="col-xs-12 btn btn-success" value="儲值">
				</div>
			</FORM>
		</div><!-- container end -->
		<br><br><br><br>
	</body>
	<script src="<%=request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/sweetalert.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/topUp.js"></script>
</html>

