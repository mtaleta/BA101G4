<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.activity.model.*"%>
<%@ page import="com.participant.model.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>

<%@ include file="/pages/isVisitor.jsp" %>

<%
	String datetime = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
	pageContext.setAttribute("currentTime", datetime);
%>
<%
	ParticipantService participantSvc = new ParticipantService();
	Set<ParticipantVO> set = participantSvc.getAll();
	pageContext.setAttribute("set", set);
%>
<%
	List<ActivityVO> list = (List<ActivityVO>) request.getAttribute("list");
	pageContext.setAttribute("list", list);
%>
<%-- 此頁練習採用 EL 的寫法取值 --%>
<jsp:useBean id="now" scope="page" class="java.util.Date" />
<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService" />
<%-- <% session.setAttribute("MEMBER", memSvc.getOneMember("MEM00000001")); %> --%>


<!DOCTYPE html>
<html lang="">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>活動管理</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<!--[if lt IE 9]>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/frontend/css/main.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/activity/css/adminActivity.css">

</head>
<%
	int rowNumber = 0;
%>
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
		<div class="container">
			<div class="row">
				<div class="col-xs-12 col-sm-12">
					<h2>主辦的活動</h2>
				</div>
				<div class="col-xs-12 col-sm-3">
					<a
						href="<%=request.getContextPath()%>/frontend/activity/addActivity.jsp"
						class="btn btn-primary btn-block btn-big">新建活動</a>
				</div>
				<div class="col-xs-12 col-sm-9"></div>
				<div class="col-xs-12 col-sm-12">
					<div role="tabpanel">
						<!-- 標籤面板：標籤區 -->

						<%!int wait_activ = 0;%>
						<%!int draft = 0;%>
						<ul class="nav nav-tabs tab-nav" role="tablist">
							<li role="presentation" class="active"><a href="#tab1"
								aria-controls="tab1" role="tab" data-toggle="tab">上架 </a></li>
							<li role="presentation"><a href="#tab2" aria-controls="tab2"
								role="tab" data-toggle="tab">待審核</a></li>
							<li role="presentation"><a href="#tab3" aria-controls="tab3"
								role="tab" data-toggle="tab">結束</a></li>
							<li role="presentation"><a href="#tab4" aria-controls="tab4"
								role="tab" data-toggle="tab">取消</a></li>
						</ul>
                
							<!-- 標籤面板：內容區 -->
						<div class="tab-content">
							<!-- 						進行中的活動 -->
							<div role="tabpanel" class="tab-pane active" id="tab1">
								<c:forEach var="activityVO" items="${list}">
									<c:if test="${activityVO.activ_store_cfm eq '1'}">
										<c:if test="${activityVO.activ_endtime > currentTime}">
											<c:if test="${activityVO.mem_id eq MEMBER.mem_id }">
												<table class="table table-bordered table-hover">

													<thead>
														<tr>
															<th width="280">活動名稱</th>
															<th width="250">舉辦時間</th>
															<th width="150">報名狀況</th>
															<th width="150">功能</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td><a
																href="<%=request.getContextPath()%>/frontend/activity/activity.do?activ_id=${activityVO.activ_id}&&action=getOne_For_Activity">${activityVO.activ_name}</a></td>
															<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
																	value="${activityVO.activ_starttime}" /> ~ <fmt:formatDate
																	pattern="yyyy-MM-dd HH:mm"
																	value="${activityVO.activ_endtime}" /></td>
															<td class="right">
															<% int i=0; %>
 																<c:forEach var="participantVO" items="${set}">
															<c:if test="${participantVO.activ_id == activityVO.activ_id}"> 
															<%i++; %>
																</c:if> 
															</c:forEach> <%=i %>
															/ ${activityVO.activ_num}</td>
															<td class="center">
																 <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/activity/activity.do">
																	 	<input type="submit" value="修改"> 
																		<input type="hidden" name="activ_id" value="${activityVO.activ_id}">
																    	<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
																    	<input type="hidden" name="action"	value="getOne_For_Update">
																</FORM>
																
																   <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/activity/activity.do" name="form1"	enctype="multipart/form-data">
																	    <input type="submit" value="刪除">
																	    <input type="hidden" name="activ_id" value="${activityVO.activ_id}">
																	    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
																	    <input type="hidden" name="action"value="update_cfm">
																	    <input type="hidden" name="mem_id" value="${activityVO.mem_id}">
																		<input type="hidden" name="activ_store_cfm" value="3" >
																    </FORM>
																</i>
															</td>
														</tr>
													</tbody>
												</table>
											</c:if>
										</c:if>
									</c:if>
								</c:forEach>
							</div>

							<!-- 							待審核活動 -->
							<div role="tabpanel" class="tab-pane" id="tab2">
								<c:forEach var="activityVO" items="${list}">
									<c:if test="${activityVO.activ_store_cfm eq 0 }">
										<c:if test="${activityVO.activ_endtime >= currentTime}">
											<c:if test="${activityVO.mem_id eq MEMBER.mem_id }">
												<table class="table table-bordered table-hover">

													<thead>
														<tr>
															<th width="280">活動名稱</th>
															<th width="250">舉辦時間</th>
															<th width="150">報名狀況</th>
															<th width="150">功能</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td><a
																href="<%=request.getContextPath()%>/frontend/activity/activity.do?activ_id=${activityVO.activ_id}&&action=getOne_For_Activity">${activityVO.activ_name}</a></td>
															<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
																	value="${activityVO.activ_starttime}" /> ~ <fmt:formatDate
																	pattern="yyyy-MM-dd HH:mm"
																	value="${activityVO.activ_endtime}" /></td>
															<td class="right">
															<% int i=0; %>
 																<c:forEach var="participantVO" items="${set}">
															<c:if test="${participantVO.activ_id == activityVO.activ_id}"> 
															<%i++; %>
																</c:if> 
															</c:forEach> <%=i %>
															/ ${activityVO.activ_num}</td>
															<td class="center">
															<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/activity/activity.do">
																	 	<input type="submit" value="修改"> 
																		<input type="hidden" name="activ_id" value="${activityVO.activ_id}">
																    	<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
																    	<input type="hidden" name="action"	value="getOne_For_Update">
																</FORM>
																    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/activity/activity.do" name="form1"	enctype="multipart/form-data">
																	    <input type="submit" value="刪除">
																	    <input type="hidden" name="activ_id" value="${activityVO.activ_id}">
																	    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
																	    <input type="hidden" name="action"value="update_cfm">
																	    <input type="hidden" name="mem_id" value="${activityVO.mem_id}">
																		<input type="hidden" name="activ_store_cfm" value="3" >
																    </FORM>
														</tr>
													</tbody>
												</table>
											</c:if>
										</c:if>
									</c:if>
								</c:forEach>
							</div>
							<!-- 							超過系統時間 -->
							<div role="tabpanel" class="tab-pane" id="tab3">
								<c:forEach var="activityVO" items="${list}">
									<c:if test="${activityVO.activ_store_cfm eq 1 }">
										<c:if test="${activityVO.activ_endtime < currentTime}">
											<c:if test="${activityVO.mem_id eq MEMBER.mem_id }">

												<table class="table table-bordered table-hover">
													<thead>
														<tr>
															<th width="280">活動名稱</th>
															<th width="250">舉辦時間</th>
															<th width="150">報名狀況</th>
															<th width="150">功能</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td><a
																href="<%=request.getContextPath()%>/frontend/activity/activity.do?activ_id=${activityVO.activ_id}&&action=getOne_For_Activity">${activityVO.activ_name}</a></td>
															<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
																	value="${activityVO.activ_starttime}" /> ~ <fmt:formatDate
																	pattern="yyyy-MM-dd HH:mm"
																	value="${activityVO.activ_endtime}" /></td>
															<td class="right">
															<% int i=0; %>
 																<c:forEach var="participantVO" items="${set}">
															<c:if test="${participantVO.activ_id == activityVO.activ_id}"> 
															<%i++; %>
																</c:if> 
															</c:forEach> <%=i %>
															/ ${activityVO.activ_num}</td>
															<td class="center"><a href="<%=request.getContextPath()%>/frontend/activity/activity.do?activ_id=${activityVO.activ_id}&&action=getOne_For_Activity">詳情
															</a></td>
														</tr>
													</tbody>
												</table>
											</c:if>
										</c:if>
									</c:if>
								</c:forEach>
							</div>
							<!-- 							會員或店家取消 -->
							<div role="tabpanel" class="tab-pane" id="tab4">
								<c:forEach var="activityVO" items="${list}">
									<c:if test="${activityVO.activ_store_cfm eq 3 }">
										<c:if test="${activityVO.mem_id eq MEMBER.mem_id }">
											<table class="table table-bordered table-hover">
												<thead>
													<tr>
														<th width="280">活動名稱</th>
														<th width="250">舉辦時間</th>
														<th width="150">報名狀況</th>
														<th width="150">功能</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td><a
															href="<%=request.getContextPath()%>/frontend/activity/activity.do?activ_id=${activityVO.activ_id}&&action=getOne_For_Activity">${activityVO.activ_name}</a></td>
														<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
																value="${activityVO.activ_starttime}" /> ~ <fmt:formatDate
																pattern="yyyy-MM-dd HH:mm"
																value="${activityVO.activ_endtime}" /></td>
														<td class="right">
															<% int i=0; %>
 																<c:forEach var="participantVO" items="${set}">
															<c:if test="${participantVO.activ_id == activityVO.activ_id}"> 
															<%i++; %>
																</c:if> 
															</c:forEach> <%=i %>
															/ ${activityVO.activ_num}</td>
														<td class="center">
															<a href="<%=request.getContextPath()%>/frontend/activity/activity.do?activ_id=${activityVO.activ_id}&&action=getOne_For_Activity">詳情
															</a>
														</td>
													</tr>
												</tbody>
											</table>
										</c:if>
									</c:if>
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
