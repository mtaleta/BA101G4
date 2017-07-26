<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.participant.model.*"%>
<%@ page import="com.member.model.*"%>

<%@ include file="/pages/isVisitor.jsp" %>

<!DOCTYPE html>

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
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>參與活動</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<!--[if lt IE 9]>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/frontend/css/main.css">
<script src='https://www.google.com/recaptcha/api.js'></script>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/frontend/activity/css/participant.css">

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
			<div class="Info">
				<div class="col-xs-12 col-sm-7 col-sm-offset-1">
					<h4 class="activName">${activityVO.activ_name}</h4>
					<d1 class="dl-event">
					<dt>
						<i class="glyphicon glyphicon-time"></i>時間
					</dt>
					<dd>
						<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
							value="${activityVO.activ_starttime}" />
						~
						<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
							value="${activityVO.activ_endtime}" />
					</dd>
					<dt>
						<i class="glyphicon glyphicon-map-marker"></i>地點
					</dt>
					<dd><c:forEach var="storeVO" items="${storeSvc.all}">
							<c:if test="${activityVO.store_id==storeVO.store_id}">${storeVO.store_add}</c:if>
						</c:forEach></dd>
					<dt>
						<i class="glyphicon glyphicon-flag"></i>主辦單位
					</dt>
					<dd>
						<c:forEach var="storeVO" items="${storeSvc.all}">
							<c:if test="${activityVO.store_id==storeVO.store_id}">${storeVO.store_name}</c:if>
						</c:forEach>
					</dd>
					<dt>
						<i class="glyphicon glyphicon-earphone"></i>聯絡方式
					</dt>
					<dd>
						<c:forEach var="storeVO" items="${storeSvc.all}">
							<c:if test="${activityVO.store_id==storeVO.store_id}">${storeVO.store_tel} or ${storeVO.store_email}</c:if>
						</c:forEach>
						<span>*若有任何活動相關問題，請洽活動主辦單位</span>
					</dd>
					</d1>
				</div>
				<div class="col-xs-12 col-sm-1">
					<div>
						<div class="btn-group-activity">
                             <a class="btn btn-cofe" role="button" data-toggle="collapse" href="#cc1" aria-expanded="false" aria-controls="#cc1" id="btnJson" >
						檢視地圖</a>
                        </div>
					</div>
				</div>
					<div class="col-xs-12 col-sm-3">
						<img
							src="<%=request.getContextPath()%>/images?action=activity&id=${activityVO.activ_id}"
							class="img-responsive">
						</div>
					</div>
				
				<div class="col-xs-12 col-sm-1"></div>
				<!-- 報名內容 -->
				<div class="scope">
					<!-- <div class="col-xs-12 col-sm-3">
                        <a href="" class="btn-link" style="text-decoration:none;"><i class="glyphicon glyphicon-backward"></i>返回活動頁</a>
                    </div> -->
					<div class="col-xs-12 col-sm-11 col-sm-offset-1">
					<div class="collapse" id="cc1">
					<br>
						<div class="list-group">
							<div id="myMap" style="width:1040px;height:400px;"></div>
						</div>
					</div>
						<div class="alert alert-info alert-dismissable"
							style="margin-top: 25px;">
							<strong>貼心提醒：</strong>請確認報名項目並輸入驗證碼後，繼續報名。我們將在下一步開始為您暫時保留名額，建議您儘速完成本步驟，以確保您的報名權益。
							
						</div>
						<div class="g-recaptcha" data-sitekey="6LcQ-CgUAAAAAIBRqz2Ola7rCtmZ_LcbsA2Qo5zu"></div>
						<p class="h4">確認報名</p>
						<div class="activity-alert" style="margin-top: 15px;">報名完成後，我們將寄送確認通知至報名人的會員電子郵件中。</div>
						<table class="table table-bordered table-hover"
							style="margin-top: 15px;">
							<thead>
								<tr>
									<th>活動名稱</th>
									<th>人數</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${activityVO.activ_name}</td>
									<td class="center">1</td>
								</tr>
							</tbody>
						</table>
						<div class="pull-right hidden-xs ">
							<FORM METHOD="post"	ACTION="<%=request.getContextPath()%>/frontend/participant/participant.do">
							<input type="submit" class="btn btn-cofe btn-lg truebtn" value="確認報名" style="margin-top: 25px; margin-bottom: 45px;"> 
							<input type="hidden" name="activ_id" value="${activityVO.activ_id}"> 
							<input type="hidden" name="action" value="insert">
							</FORM>
						</div>
					</div>
					<div class="col-xs-12 col-sm-1"></div>
				</div>
			</div>
	</div>
	<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDk88Gg5mfA5IuhvQHHQuwmSksbVIsMfYY&callback=doFirst"></script>
	<script src="https://code.jquery.com/jquery.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
		
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

    	
