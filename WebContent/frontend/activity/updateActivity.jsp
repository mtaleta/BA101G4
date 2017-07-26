<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.activity.model.*"%>

<%@ include file="/pages/isVisitor.jsp" %>

<link
	href="<%=request.getContextPath()%>/frontend/activity/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link
	href="<%=request.getContextPath()%>/frontend/activity/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script>

<%
	ActivityVO activityVO = (ActivityVO) request.getAttribute("activityVO");
%>
<jsp:useBean id="now" scope="page" class="java.util.Date" />
<jsp:useBean id="storeSvc" scope="page"
	class="com.store.model.StoreService" />
<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService" />
<%-- <% session.setAttribute("MEMBER", memSvc.getOneMember("MEM00000001")); %> --%>
<html>
<head>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>辦活動</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<!--[if lt IE 9]>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/frontend/css/main.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/activity/css/updateActivity.css">

</head>
<title>舉辦活動</title>
<link rel="stylesheet" type="text/css" href="js/calendar.css">
<script language="JavaScript" src="js/calendarcode.js"></script>
</head>
<div id="popupcalendar" class="text"></div>
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
		<div class="col-xs-12 col-sm-12">
			<ol class="breadcrumb">
				<li>辦活動</li>
				<li class="active">活動詳細資料</li>
				<li>設定完成</li>
			</ol>
		</div>
		<FORM METHOD="post"
			ACTION="<%=request.getContextPath()%>/frontend/activity/activity.do"
			name="form1" enctype="multipart/form-data">
			<div class="col-xs-12 col-sm-12">
				<p class="text-right">
					<input class="btn btn-cofe" type="submit" value="儲存並發佈"> 
					<input type="hidden" name="action" value="update"> 
					<input type="hidden" name="activ_id" value="<%=activityVO.getActiv_id()%>"> 
					<input type="hidden" name="mem_id" value="<%=activityVO.getMem_id()%>">
					<input type="hidden" name="store_id" value="<%=activityVO.getStore_id()%>"> 
				</p>
			</div>
			<div class="col-xs-12 col-sm-12">
				<div class="form-horizontal">
					<div class="form-group">
						<div class="col-xs-12 col-sm-12">
							<input type="text" name="activ_name" id="titleGroup"
								class="form-controls" value="<%=activityVO.getActiv_name()%>" />
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-12 col-sm-12">
				<figure class="figure" style="width: 100%;">
					<img id="image" class="img-responsive" style="max-height:525px"
						src="<%=request.getContextPath()%>/images?action=activity&id=${activityVO.activ_id}" >
					<figcaption class="figure-caption" ></figcaption>

					<a href="javascript:;" class="uploadimg a-upload"> <input
						type="file" name="prod_img" id="upfile1" onchange="file_change()" >上傳圖片
					</a>
				</figure>
			</div>



			<div class="background-gray eventInfo">
				<div class="container">
					<div class="row">
						<div class="col-xs-12 col-sm-6">
							<div class="form-group">
								<label for="aa" class="col-xs-12 col-sm-3 control-label" style="width: 100%"> 
									<i class="glyphicon glyphicon-time"></i> 開始時間*
								</label> <br>
								<div class="row">
									<div class="col-xs-12 col-sm-6">
										<div class="form-group">
												<input size="16" type="text" value="<%=activityVO.getActiv_starttime()%>" readonly name="activ_starttime" class="input-append date form_datetime form-input error-input"> 
												<span class="add-on">
													<i class="icon-th"></i>
												</span>
										</div>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="aa" class="col-xs-12 col-sm-3 control-label" style="width: 100%"> 
									<i class="glyphicon glyphicon-time"></i> 結束時間*
								</label> <br>
								<div class="row">
									<div class="col-xs-12 col-sm-6">
											<input size="16" type="text" value="<%=activityVO.getActiv_endtime()%>" readonly name="activ_endtime" class="input-append date form_datetime form-input error-input"> 
											<span class="add-on">
												<i class="icon-th"></i> 
											</span>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="aa" class="col-xs-12 col-sm-3 control-label" style="width: 100%"> 
									<i class="glyphicon glyphicon-time"></i> 報名截止時間:
								</label> <br>
								<div class="row">
									<div class="col-xs-12 col-sm-6">
											<input size="16" type="text" value="<%=activityVO.getActiv_expire()%>" readonly name="activ_expire" class="input-append date form_datetime form-input error-input"> 
											<span class="add-on">
												<i class="icon-th"></i> 
											</span>
									</div>
								</div>
							</div>

							<div class="form-group">
								<label for="aa" class="col-xs-12 col-sm-12 control-label" style="width: 100%"> 
									<i class="glyphicon glyphicon-map-marker"></i></i> 參與人數(人數限制最大為99喔)*
								</label> <br>
								<div class="row">
									<div class="col-xs-12 col-sm-6">
										<input type="TEXT" name="activ_num" class="form-input error-input" value="<%=activityVO.getActiv_num()%>" />
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-12 col-sm-6">
							<div class="form-group">
								<label for="aa" class="col-xs-12 col-sm-3 control-label" style="width: 100%"> 
									<i class="glyphicon glyphicon-map-marker"></i></i> 舉辦店家*
								</label> <br>
								<div class="row">
									<div class="col-xs-12 col-sm-6">
										<c:forEach var="storeVO" items="${storeSvc.all}">
											<c:if test="${activityVO.store_id==storeVO.store_id}">${storeVO.store_name}</c:if>
										</c:forEach>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="aa" class="col-xs-12 col-sm-3 control-label" style="width: 100%"> <i class="glyphicon glyphicon-map-marker"></i></i> 店家確認*
								</label> <br>
								<div class="row">
									<div class="col-xs-12 col-sm-6"">
										<a class="form-text">尚未確認</a>
										<input type="hidden" name="activ_store_cfm" value="0" />
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="aa" class="col-xs-12 col-sm-3 control-label"
									style="width: 100%"> <i
									class="glyphicon glyphicon-map-marker"></i></i> 活動摘要*
								</label> <br>
								<div class="row">
									<div class="col-xs-12 col-sm-12" style="float: right; height: 100px;rows=3;">
										<textarea cols="5"
											name="activ_summary" class="form-input error-input"><%=activityVO.getActiv_summary()%></textarea>
									</div>
								</div>
							</div>
						</div>
					</div>
					<h3 class="lineCaption">
						<span>活動介紹</span>
					</h3>
					<div>
						<textarea class="ckeditor" cols="80" id="activ_intro"
							name="activ_intro" rows="12"
							 /><%=activityVO.getActiv_intro()%></textarea>
					</div>
				</div>
			</div>
		</form>
	</div>
	<footer class="footer text-center">
		<div class="col-xs-12 col-sm-3">常見問題</div>
		<div class="col-xs-12 col-sm-3">功能介紹</div>
		<div class="col-xs-12 col-sm-3">關於我們</div>
		<div class="col-xs-12 col-sm-3">客服中心</div>
		<center style="margin-top: 10px;">BA101第四組</center>
	</footer>
</div>

<script type="text/javascript" src="./jquery/jquery-1.8.3.min.js"
	charset="UTF-8"></script>
<script type="text/javascript" src="./bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="./js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="./js/locales/bootstrap-datetimepicker.zh-TW.js" charset="UTF-8"></script>
<script src="<%=request.getContextPath()%>/frontend/activity/js/timeSelect.js"></script>
<script src="<%=request.getContextPath()%>/frontend/activity/js/pictureReader.js"></script>
</html>

