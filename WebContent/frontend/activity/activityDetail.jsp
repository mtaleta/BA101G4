<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.participant.model.*"%>
<%@ page import="com.rept_activ.model.*"%>

<%@ include file="/pages/isVisitor.jsp" %>

<!DOCTYPE html>
<%
	Rept_activVO rept_activVO = (Rept_activVO) request.getAttribute("rept_activVO");
%>

<jsp:useBean id="activitySvc" scope="page"
	class="com.activity.model.ActivityService" />
<jsp:useBean id="storeSvc" scope="page"
	class="com.store.model.StoreService" />
<jsp:useBean id="memSvc" scope="page"
	class="com.member.model.MemberService" />
<%-- <%session.setAttribute("MEMBER", memSvc.getOneMember("MEM00000001"));%> --%>

<!-- 	抓連入頁面的的活動編號 /好像已用不到-->
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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/frontend/activity/css/activityDetail.css">
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
		<hr class="col-xs-12">
		<div class="center">
			<img class="product" style="text-align: center;"
				src="<%=request.getContextPath()%>/images?action=activity&id=${activityVO.activ_id}">
		</div>

		<div class="event-view-title">
			<h2>${activityVO.activ_name}</h2>
		</div>

		<div class="event-section apcss-event-view-introduction-section">
			<div class="col-xs-12 col-sm-8 col-sm-offset-1">
				<div class="row">
					<ul class="event-intro-item">
						<jsp:useBean id="now" scope="page" class="java.util.Date" />
						<li class="item-title"><i class="glyphicon glyphicon-time"></i>
							<span>活動日期：</span>
						<li class="item-content"><fmt:formatDate
								pattern="yyyy-MM-dd HH:mm" value="${activityVO.activ_starttime}" />
							~ <fmt:formatDate pattern="yyyy-MM-dd HH:mm"
								value="${activityVO.activ_endtime}" /></li>
						</li>
					</ul>

					<ul class="event-intro-item">
						<li class="item-title"><i
							class="glyphicon glyphicon-map-marker"></i> <span
							class="intro-title">活動店家：</span></li>
						<li class="item-content"><c:forEach var="storeVO"
								items="${storeSvc.all}">
								<c:if test="${activityVO.store_id==storeVO.store_id}">${storeVO.store_name}</c:if>
							</c:forEach></li>
					</ul>
					<ul class="event-intro-item">
						<li class="item-title"><i
							class="glyphicon glyphicon-map-marker"></i> <span
							class="intro-title">活動地點：</span></li>
						<li class="item-content"><c:forEach var="storeVO"
								items="${storeSvc.all}">
								<c:if test="${activityVO.store_id==storeVO.store_id}">${storeVO.store_add}</c:if>
							</c:forEach></li>
					</ul>
					<ul class="event-intro-item">
						<li class="item-title"><i
							class="glyphicon glyphicon-map-marker"></i> <span
							class="intro-title">舉辦會員：</span></li>
						<li class="item-content"><c:forEach var="memberVO"
								items="${memberSvc.all}">
								<c:if test="${activityVO.mem_id==memberVO.mem_id}">${memberVO.mem_name}</c:if>
							</c:forEach></li>
					</ul>
				</div>
			</div>
			<div class="col-xs-12 col-sm-2 ">
				<div class="event-section apcss-event-view-introduction-section">
					<div class="btn-group-activity" style="text-align:right;">
                             <a class="btn btn-cofe" data-toggle="collapse" href="#cc1" aria-expanded="false" aria-controls="#cc1" id="btnJson" >
						檢視地圖</a>
                        </div>
				</div>
				<div class="event-section apcss-event-view-introduction-section">
					<p style="text-align:right;">
						<a href="#signUp" class="btn btn-cofe">立刻報名活動</a>
					</p>
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
						<span>活動介紹</span>
					</h3>
					<div class="redactor-box">${activityVO.activ_intro}</div>
				</div>

				<div class="event-section apcss-event-view-introduction-section">
					<div class="col-xs-12">
						<a name="signUp"></a>
						<h3 class="text-center lineCaption2">票券資訊</h3>
						<div class="row hidden-xs hidden-sm">
							<table class="ticket-table table table-hover">
								<thead>
									<tr>
										<th class="list-title">活動名稱</th>
										<th class="list-title"></th>
										<th class="list-title">價格</th>
										<th class="list-title">張數</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<th class="ticket-name ">${activityVO.activ_name}</th>
										<th class="ticket-more-info">更多資訊</th>
										<th class="ticket-price">免費參加</th>
										<th class="ticket-name ">1</th>
									</tr>
								</tbody>
							</table>
						</div>
						<a class="btn btn-primary btn-block center"
							href="<%=request.getContextPath()%>/frontend/activity/activity.do?action=listActivitys_Activity_B&activ_id=${activityVO.activ_id}">立即報名</a>
						<c:if test="${not empty errorMsgsActiv}">
							<font color='red'>
								<ul style="text-align: center;">
									<c:forEach var="message" items="${errorMsgsActiv}">${message}</c:forEach>
								</ul>
							</font>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="event-section text-left center" style="text-align: center;">
		<a href="#" class="block negative-link text-center " role="button"
			data-toggle="modal" data-target="#login-modal"> <i
			class="glyphicon glyphicon-thumbs-down"></i>檢舉此活動
		</a>
		<c:if test="${not empty errorMsgs}">
			<font color='red'>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">${message}</c:forEach>
				</ul>
			</font>
		</c:if>
	</div>

	<!-- 檢舉燈箱-->
	<div class="modal fade in" id="login-modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" align="center">
					<a class="modal-close" aria-hidden="true" data-dismiss="modal">×</a>
					<h4 class="modal-title">檢舉此活動</h4>
				</div>
				<div class="modal-body">
					<p class="text-center">請協助提供您檢舉此活動的原因</p>
					<h4 class="text-center event-report-subtitle">檢舉原因</h4>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/frontend/rept_activ/rept_activ.do"
						name="form1">
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
						<textarea class="evnet-report-advance" placeholder="請填寫檢舉原因"></textarea>
						<input type="submit" value="提交審查" class="btn btn-cofe btn-block">
						<input type="hidden" name="action" value="insert"> <input
							type="hidden" name="activ_id" value="${activityVO.activ_id}" />
						<input type="hidden" name="repo_rev" value="0" /> <input
							type="hidden" name="requestURL"
							value="<%=request.getServletPath()%>">
					</FORM>
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
			<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDk88Gg5mfA5IuhvQHHQuwmSksbVIsMfYY&callback=doFirst"></script>
		
</body>

</html>
<script type="text/javascript">
var store_name;
var lon;
var lat;
function doFirst(){
	  $.ajax({
			 type:"GET",
			 url:"<%=request.getContextPath()%>/frontend/activity/activity.do",
			 data:{action:"getJson",store_id:"${activityVO.store_id}"},
			 dataType:"json",
			 success:function (data){
				store_name = data[0]['name'];	
				lon = data[0]['lon'];
				lat = data[0]['lat'];
		     },
       error:function(){alert("error")}
   })
   document.getElementById('btnJson').addEventListener('click',initMap,false);
}


function initMap(){
	

	var myMap = document.getElementById('myMap');
	var myPosition = new google.maps.LatLng(lat,lon);
	
	
	var map = new google.maps.Map(myMap,{
		zoom: 16,
		center: myPosition,
		mapTypeId: google.maps.MapTypeId.ROADMAP,
		styles: [
		    {
		        "featureType": "administrative",
		        "elementType": "all",
		        "stylers": [
		            {
		                "visibility": "on"
		            },
		            {
		                "lightness": 33
		            }
		        ]
		    },
		    {
		        "featureType": "landscape",
		        "elementType": "all",
		        "stylers": [
		            {
		                "color": "#f2e5d4"
		            }
		        ]
		    },
		    {
		        "featureType": "poi.park",
		        "elementType": "geometry",
		        "stylers": [
		            {
		                "color": "#c5dac6"
		            }
		        ]
		    },
		    {
		        "featureType": "poi.park",
		        "elementType": "labels",
		        "stylers": [
		            {
		                "visibility": "on"
		            },
		            {
		                "lightness": 20
		            }
		        ]
		    },
		    {
		        "featureType": "road",
		        "elementType": "all",
		        "stylers": [
		            {
		                "lightness": 20
		            }
		        ]
		    },
		    {
		        "featureType": "road.highway",
		        "elementType": "geometry",
		        "stylers": [
		            {
		                "color": "#c5c6c6"
		            }
		        ]
		    },
		    {
		        "featureType": "road.arterial",
		        "elementType": "geometry",
		        "stylers": [
		            {
		                "color": "#e4d7c6"
		            }
		        ]
		    },
		    {
		        "featureType": "road.local",
		        "elementType": "geometry",
		        "stylers": [
		            {
		                "color": "#fbfaf7"
		            }
		        ]
		    },
		    {
		        "featureType": "water",
		        "elementType": "all",
		        "stylers": [
		            {
		                "visibility": "on"
		            },
		            {
		                "color": "#acbcc9"
		            }
		        ]
		    }
		]
	});
	var contentString = '<div id="content">'+
    '<div id="siteNotice">'+
    '</div>'+
    '<h2 id="firstHeading" class="firstHeading">${activityVO.activ_name}</h1>'+
    '<div id="bodyContent">'+
    '<p><b>活動時間 ：</b><br>'+
    '<p><fmt:formatDate	pattern="yyyy-MM-dd HH:mm" value="${activityVO.activ_starttime}" /> ~ <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${activityVO.activ_endtime}" /></p>'+
    '</div>'+
    '</div>';

	var infowindow = new google.maps.InfoWindow({
	  content: contentString
	});

	var marker = new google.maps.Marker({
		position: myPosition,
		map: map,
		icon:'${pageContext.request.contextPath}/img/logo/logo-mdpi-36.png',
		
	});
	marker.addListener('click', function() {
        infowindow.open(map, marker);
      });
}
   </script>

    	