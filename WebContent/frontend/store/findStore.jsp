<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Base64" %>
<%@ page import="com.store.model.StoreVO" %>

<%@ include file="/pages/isVisitor.jsp" %>
<jsp:useBean id="tagSvc" scope="page" class="com.tag.model.TagService" />

<jsp:useBean id="base" class="com.sylvie.picture.BaseChanger" />
<jsp:useBean id="dayOfWeek" scope="page" class="com.sylvie.day.Day" />
<jsp:useBean id="operatingTime" scope="page" class="com.sylvie.day.OperatingTime" />
<c:set var="dayOfWeek" value="${dayOfWeek.day}" />

<c:set var="context" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>搜尋店家</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
		<link rel="stylesheet" href="${context}/css/main.css">
		<link rel="stylesheet" href="${context}/css/storeInfo.css">
		<link rel="stylesheet" href="${context}/css/normal.css">
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
						<li class="active"><a href="<%=request.getContextPath()%>/frontend/store/findStore.jsp">搜尋店家</a></li>
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
			<div class="row">
				<div class="col-xs-12 col-sm-6 col-sm-offset-3">
					<div class="input-group clearfix">						
						<form method="post" action="${context}/frontend/store/store.do">
							<div class="pull-left"><input type="text" name="area_or_name" class="form-control" placeholder="請輸入關鍵字"></div>					
							<div class="pull-right"><button class="btn btn-primary" type="submit">搜尋</button></div>
							<input type="hidden" name="action" value="findStoreByAreaOrName">
						</form>
					</div>
				</div>
				<div class="col-xs-12"><hr></div>
			</div><!-- row end -->
		</div><!-- container end -->

		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-12 col-sm-6 col-lg-4">
					<div class="container-fluid">
						<div class="row">
							<div id="addStore" class="col-xs-12 col-sm-6 col-sm-push-6">
								<a href="#" class="btn btn-custo"><h4>新增店家</h4></a>
							</div>
							<div class="col-xs-12 col-sm-6 col-sm-pull-6">
								<h2>店家清單</h2>
							</div>
						</div><!-- row end -->
					</div><!-- container-fluid end -->
					<div id="list" class="container-fluid">
						<div class="row header-btn">
							<div class="col-xs-12">
								<c:if test="${isMember}">
									<a id="fav_store" href="<%=request.getContextPath()%>/fav_store/fav_store.do?action=listOfFavStore" class="btn btn-custo">瀏覽已收藏店家</a>
								</c:if>
								<ul class="tag-list">
									<c:forEach var="tagVO" items="${tagSvc.all}" >
										<li><a class="btn btn-custo" href="<%=request.getContextPath()%>/store/store_tag.do?tag_id=${tagVO.tag_id}&action=getStoresBYTAG">${tagVO.tag_content}</a></li>
									</c:forEach>
								</ul>
							</div>
						</div><!-- row end -->
					
						<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
						<c:set value="${storeSvc.all}" var="getAll"/>
						<c:set value="${findStoreByAreaOrName}" var="findStoreByAreaOrName"/>
							<c:forEach var="storeVO" items="${empty findStoreByAreaOrName?getAll:findStoreByAreaOrName}">
							<%@ include file="/pages/operatingHour.jsp" %>						
								<div class="findStore">							
									<jsp:setProperty name="base" property="imgByte"  value="${storeVO.store_img}" />
									<c:if test="${empty storeVO.store_img}">
										<img class="logo float-left margin5px" style="width:140px" src="${context}/img/acup.jpg">
									</c:if>
									<c:if test="${not empty storeVO.store_img}">
										<img class="logo float-left margin5px" style="width:140px" src="data:image/jpeg;base64, ${base.baseStr}">
									</c:if>
										<div class="store-info">
											<div class="store-name">${storeVO.store_name}</div>
											<div class="iw-subTitle">今日營業時間</div>
											<c:out value="${dy}"/> : <fmt:formatDate pattern = "HH:mm" value="${open}" /> - <fmt:formatDate pattern = "HH:mm" value="${close}" />
											<c:out value="${abc}" default="休息日"/>
											<div class="iw-subTitle">聯絡方式</div>
											<c:out value="${storeVO.store_add}"/><br><c:out value="${storeVO.store_tel}"/>											
										</div>
										<div style="clear: both;"></div>
								</div><!-- findStore end -->
							</c:forEach>
					</div>
				</div>
				<div class="col-xs-12 col-sm-6 col-lg-8">
					<div id="myMap" style="height:580px;"></div>
				</div>
			</div><!-- row end -->
		</div><!-- container-fluid end -->
		
		
		
		<div id="light" class="white_content" style="width:30%; left:35%; ">
			<div class="header clearfix">
				<div class="pull-right">
					<div id="closeBtn" style="margin:0px 0px 10px 50px"><img src="${context}/img/cross.png"></div>
					<div id="takeawayBtn"></div>
				</div>
				<div id="storeImg" class="pull-left"></div>
				<div>
					<div id="storeName" class="name"></div>
					<div id="contact">
						<div class=""></div>
						<div class=""></div>
						<div class=""></div>
					</div>				
				</div>		
			</div>
			<hr>
			<div class="body">
				<h4>店家詳細資訊</h4>
				<div id="detailInfo" class="clearfix">
					<div class="pull-left col-lg-6">
						<div></div>
						<div></div>
						<div></div>
						<div></div>
						<div></div>
					</div>
					<div class="pull-left col-lg-6">
						<div></div>
						<div></div>
						<div></div>
						<div></div>
						<div></div>					
					</div>
				</div>
				<hr>
				<h4>營業時間</h4>
				<div id="operatingHour">
						<div></div>
						<div></div>
						<div></div>
						<div></div>
						<div></div>
						<div></div>
						<div></div>
				</div>
				<hr>
				<div><button id="uploadPhoto" class="btn btn-success btn-sm" style="margin:5px 0px 5px 15px"><i class="glyphicon glyphicon-new-window"></i>上傳圖片</button></div>
				<div id="dropZoneBlock">
					<div id="dropZone"><img class="" id="box" style="height:100%" src="${context}/img/box.png"></div>
					<div><button id="upload" class="btn btn-success btn-sm" style="margin:10px" disabled="disabled">確認</button></div>
				</div>
				<hr>
				<h4>店家相簿</h4>
				<div id="photo">
				</div>
			</div>
			<hr>
			<div class="footer">
			</div>
		</div>
		<div id="fade" class="black_overlay"></div>


		<script src="https://code.jquery.com/jquery.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script src="${context}/js/original.js"></script>
		<script>
			
			function openMask() {
				$("#light").show();
				$("#fade").show();
			}
			function closeMask() {
				$("#light").hide();
				$("#fade").hide();
				$('#dropZoneBlock').css('display','none');
				$('#dropZone').html('<img class="" id="box" style="height:100%" src="${context}/img/box.png">');
			}
			
		    var map;
		    
		    function initCoords() {
		    	navigator.geolocation? navigator.geolocation.getCurrentPosition(initMap, function(){alert("error code : " + e.code)})
		    	 					 : showError("Your browser does not support Geolocation!");
		    }
		    
		    function initMap(e) {

	        	var lati = e.coords.latitude;
				var longi = e.coords.longitude;			    
		    	
		    	var xy = new google.maps.LatLng(lati, longi);
		    	
		    	 map = new google.maps.Map(document.getElementById('myMap'), {
		         zoom: 14,
		         center: xy,
		         
		         mapTypeId: google.maps.MapTypeId.ROADMAP
		        });
		    	 
		    	var markerSelf = new google.maps.Marker({
		    		position: new google.maps.LatLng(lati,longi),
		    		map: map,
		    		icon: '${context}/img/map-pin.png',
		    		title: 'Your position'
		    	})
		    		        
		        var bounds = new google.maps.LatLngBounds();

		    	 <c:forEach var="storeVO" items="${empty findStoreByAreaOrName?getAll:findStoreByAreaOrName}" varStatus="status">
							var latLng = new google.maps.LatLng(<c:out value="${storeVO.latitude}"/>, <c:out value="${storeVO.longitude}"/>);                   
							bounds.extend(latLng);
								         
							var marker = new google.maps.Marker({
							position: latLng,
							map: map,
							icon: '${context}/img/coffee32.png',
							title: '<c:out value="${storeVO.store_name}"/>'
							});
							
							google.maps.event.addListener(marker, "click", function(){getStoreInfo('${storeVO.store_id}')}); 
			     </c:forEach>
		         
		       map.fitBounds(bounds);
		       
		       
		    }
		    
		    function openUploadArea() {
		    	if($('#dropZoneBlock').css('display') == 'none') {
			    	$('#dropZoneBlock').css('display','block');		    		
		    	} else {
		    		$('#dropZoneBlock').css('display','none');	
		    	}
		    }
		    
		    function convertToHour(obj) {
		    	var hour = obj.hours.toString();
		    	var min = obj.minutes.toString();
				hour = hour.length==1?'0' + hour:hour;
				min = min.length==1?'0'+ min:min;
		    	return hour + ' : ' + min;
		    }
		    
		    function uploadPhotos(store_id) {
				console.log(store_id);
				
				var strArr = $('#dropZone').find('input[name=photo]').toArray();
				var picArr = new Array();

				for(var i = 0; i < strArr.length; i ++) {
					picArr.push(strArr[i].value);
				}
				var jsonString={};
				jsonString  = JSON.stringify(picArr);
				
				var params = {
						type : "POST",
						url : "${context}/frontend/photo_store/photo_store.do",
						dataType : "json",
						data : {"action" : "insert",
								"store_id" : store_id,
								"photo" : jsonString,
								},
						success : function(data) {
									$('#dropZone').html('<img class="" id="box" style="height:100%" src="${context}/img/box.png">');
									$('#upload').prop("disabled",true);
									$('#photo').empty();
									for(var i = 0; i < data[0].photo_store.length; i++) {
										$('#photo').append("<div style='margin-bottom:10px'><img class='img' style='width:80%' src='data:image/jpeg;base64," + data[0].photo_store[i] +"'></div>");
									}
								},
						error : function() {
									alert("error");
								}
				}
				
				$.ajax(params);
		    }
		    
			function getStoreInfo(store_id) {
				var btnObj = $(this);
				var storeId = store_id;
				console.log(store_id);
				
				var params = {
						type : "POST",
						url : "${context}/frontend/store/store.do",
						dataType : "json",
						data : {"action" : "findByPrimaryKey",
								"store_id" : store_id,
								},
						success : function(data) {	
									openMask();
									$('#uploadPhoto').off('click').on('click',openUploadArea);
									$('#upload').off('click').on('click',function() {uploadPhotos(storeId);});
									$('#closeBtn').off('click').on('click',closeMask);
									$('#fade').css('opacity','.10');
									if(data[0].sun_isopen==1) {
										$('#takeawayBtn').html('<form method="post" action="${context}/frontend/store/store.do">' +
												'<button type="submit" class="btn btn-normal btn-sm" style="width:80px;margin:0px"><i class="glyphicon glyphicon-cutlery"></i> 外帶外送</button>' +
												'<input type="hidden" name="store_id" value="'+ storeId +'">' +
												'<input type="hidden" name="action" value="getTakeawayProductsByStore_id">' +
												'</form>');										
									}
									
									$('#storeImg').html("<img class='img img-circle' height='120' style='margin:0px 10px 0px 0px' src='data:image/jpeg;base64," + data[0].store_img +"'>");
									$('#storeName').html(data[0].store_name);
									$('#contact div:nth-child(1)').html('<i class="glyphicon glyphicon-phone-alt"></i> ' + data[0].store_tel);
									$('#contact div:nth-child(2)').html('<i class="glyphicon glyphicon-map-marker"></i> ' + data[0].store_add);
									$('#contact div:nth-child(3)').html(data[0].store_email);
									$('#detailInfo div:nth-child(1) div:nth-child(1)').html("有無Wifi : " + (data[0].is_wifi==1?'Yes':'No'));
									$('#detailInfo div:nth-child(1) div:nth-child(2)').html("有無低消  : " + (data[0].is_min_order==1?'Yes':'No'));
									$('#detailInfo div:nth-child(1) div:nth-child(3)').html("有無插座  : " + (data[0].is_plug==1?'Yes':'No'));
									$('#detailInfo div:nth-child(1) div:nth-child(4)').html("有無限時 : " + (data[0].is_time_limit==1?'Yes':'No'));
									$('#detailInfo div:nth-child(2) div:nth-child(1)').html("有賣正餐 : " + (data[0].is_meal==1?'Yes':'No'));
									$('#detailInfo div:nth-child(2) div:nth-child(2)').html("有賣甜品 : " + (data[0].is_dessert==1?'Yes':'No'));
									$('#detailInfo div:nth-child(2) div:nth-child(3)').html("有賣單品 : " + (data[0].is_single_orgn==1?'Yes':'No'));
									$('#operatingHour div:nth-child(1)').html("星期日 : " + (data[0].sun_isopen==1?convertToHour(data[0].sun_open) + '-' + convertToHour(data[0].sun_close):'休息日'));
									$('#operatingHour div:nth-child(2)').html("星期一 : " + (data[0].mon_isopen==1?convertToHour(data[0].mon_open) + '-' + convertToHour(data[0].mon_close):'休息日'));
									$('#operatingHour div:nth-child(3)').html("星期二 : " + (data[0].tue_isopen==1?convertToHour(data[0].tue_open) + '-' + convertToHour(data[0].tue_close):'休息日'));
									$('#operatingHour div:nth-child(4)').html("星期三 : " + (data[0].wed_isopen==1?convertToHour(data[0].wed_open) + '-' + convertToHour(data[0].wed_close):'休息日'));
									$('#operatingHour div:nth-child(5)').html("星期四 : " + (data[0].thu_isopen==1?convertToHour(data[0].thu_open) + '-' + convertToHour(data[0].thu_close):'休息日'));
									$('#operatingHour div:nth-child(6)').html("星期五 : " + (data[0].fri_isopen==1?convertToHour(data[0].fri_open) + '-' + convertToHour(data[0].fri_close):'休息日'));
									$('#operatingHour div:nth-child(7)').html("星期六 : " + (data[0].sat_isopen==1?convertToHour(data[0].sat_open) + '-' + convertToHour(data[0].sat_close):'休息日'));
									$('#photo').empty();
									for(var i = 0; i < data[0].photo_store.length; i++) {
										$('#photo').append("<div style='margin-bottom:10px'><img class='img' style='width:95%' src='data:image/jpeg;base64," + data[0].photo_store[i] +"'></div>");
									}
								},
						error : function() {
									alert("error");
								}
				}
				
				$.ajax(params);
			}
			
			function doFirst(){
				document.getElementById('dropZone').ondragover = dragOver;
				document.getElementById('dropZone').ondrop = dropped;
			}
			function dragOver(e){
				e.preventDefault();
			}
			function dropped(e){
				e.preventDefault();

				var files = e.dataTransfer.files;
				var readFile = new FileReader();

				for(var key in files){	//files[key]
					readFile.readAsDataURL(files[key]);
					var str;
					readFile.addEventListener('load',function(){
						$('#dropZone img[id=box]').remove();
						$('#upload').prop("disabled",false);
						var image = document.createElement('img');
						image.src = readFile.result;
						image.style.width = "100%";
						console.log(typeof readFile.result);
						str = readFile.result.substring(23);
						var dropZone = document.getElementById('dropZone');
						dropZone.insertBefore(image,dropZone.firstChild);
					$('#dropZone').append("<input type='hidden' name='photo' value='" + str + "'>");
					$('#dropZone').css('height','auto');
					},false);
					
				}
			}
			
			window.addEventListener('load',doFirst,false);
    </script>
		<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCv5F922wKBqLMCXNpAsLAs08co7G32QAU&callback=initCoords"></script>
		<script src="${context}/js/mapAutoHeight.js"></script>
	</body>
</html>