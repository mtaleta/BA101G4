<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ include file="/pages/isVisitor.jsp" %>

<jsp:useBean id="base" class="com.sylvie.picture.BaseChanger"/>
<jsp:useBean id="cateSvc" scope="page" class="com.category.model.CategoryService" />
<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService" />
<c:set var="context" value="${pageContext.request.contextPath}" />

<c:set value = "${MEMBER.mem_add}" var="memAdd" />

<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>Title Page</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<!--[if lt IE 9]>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<link rel="stylesheet" href="${context}/css/normal.css">
<link rel="stylesheet" href="${context}/css/syl_product.css">
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
			</div><!-- 手機隱藏選單區結束 -->
		</div>
	</nav><!-- nav end -->

	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-sm-8 col-sm-offset-2">
				<jsp:setProperty name="base" property="imgByte" value="${findByPrimaryKey.store_img}" />
				<span><img class="img float-left img-circle" height="120" src="data:image/jpeg;base64, ${base.baseStr}"></span>
				<span style="font-size:40px"><c:out value="${findByPrimaryKey.store_name}" /></span>
			</div>
		</div><!-- row end -->
	</div><!-- container end -->
	
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-sm-8 col-sm-offset-2">
				<hr>
			</div>
		</div><!-- row end -->
	</div><!-- container end -->	

	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-6 col-lg-offset-2">
				<div class="clearfix">
					<div class="pull-left">	
						<ul id="myTab" class="nav nav-tabs">
				     		<c:forEach var="entry" items="${proMap}" varStatus="status">
						    	<c:set value = "${entry.key}" var = "entryKey"/>
						    	<c:if test="${entryKey=='CATE00000000'}">
							    	<c:set value = "menu" var="cateName" />
					          		<li class="active"><a href="#${entryKey}" data-toggle="tab"><c:out value = "${cateName}"/></a></li>							    	
						    	</c:if>
						    	<c:if test="${entryKey!='CATE00000000'}">
						    		<c:set value = "${cateSvc.findByPrimaryKey(entryKey).cate_name}" var="cateName" />
					          		<li class=""><a href="#${entryKey}" data-toggle="tab"><c:out value = "${cateName}"/></a></li>
						    	</c:if>
				          </c:forEach>			     				          
						</ul>						
					</div>
				</div>
			
				<div class="tab-content pull-left">
				
					<c:forEach var="entry" items="${proMap}" >		           
						<c:set value = "${entry.key}" var = "entryKey"/>
						<c:set value="${entry.value}" var="proVOList" scope="page"/>
						<div class="tab-pane ${entryKey=='CATE00000000'?'active':'fade'}" id="${entryKey}">
							<div class="mainDiv">
								<div class="myItems">
									<display:table id="productVO" name="pageScope.proVOList" class="table" pagesize="100"  sort="external" defaultsort="1"
									cellpadding="5px;" cellspacing="5px;" requestURI="${context}/frontend/store/store.do" export="false"
									size="${proVOList.size()}">
										<jsp:setProperty name="base" property="imgByte" value="${productVO.prod_img}" />
										<display:column title="商品圖片" class="tr_${productVO.prod_id} col-md-3">
											<img class="img" src="data:image/jpeg;base64, ${base.baseStr}" height="120">
										</display:column>
										<display:column title="商品名稱" class="col-md-3 name">
											<c:out value="${productVO.prod_name}" />
										</display:column>		
										<display:column title="商品描述" class="col-md-2">
											<c:out value="${productVO.prod_desc}" />
										</display:column>		
										<display:column title="單價" class="price col-md-1" sortable="true" sortName="price">											
						                    <c:out value="${productVO.prod_price}" />															
										</display:column>		
										<display:column title="選入" class="col-md-3">
											<button class="btn btn-success btn-sm add add_${productVO.prod_id}">ADD</button>
										</display:column>							
									</display:table>
								</div>
							</div>
						</div><!-- Category end -->
					</c:forEach>
					
				</div>
			</div><%/* end column */%>
			
			<div class="col-xs-12 col-sm-6 col-md-2 col-lg-2"><!-- right column start -->
				<div id="cartList"><!-- cartList start -->  
					<h3></h3>
					<form method="post" action="${context}/frontend/orderlist/orderlist.do">
						<div id='orderType' class="text-left">
							<label class="radio-inline" style="margin-right:20px">
								<input type="radio" class="radio radio-info" name="orderType" value="2">外帶
							</label>
							<label class="radio-inline">
								<input type="radio" class="radio radio-info" name="orderType" value="3" checked>外送
							</label>
						</div>
						<div id='orderTypeDetail'>               
						</div>
						<h5>已選購的商品</h5>
						<div id="myItems"></div>
						<div id="sum">總計金額:<span></span><input type="hidden" name="ord_total" value="0"></div>
						<button type="submit" id="checkOut" class="btn btn-success btn-sm" style="width:90%" disabled>確認完成</button>
						<input type="hidden" name="store_id" value="${findByPrimaryKey.store_id}">
						<input type="hidden" name="action" value="insertTakeaway">
					</form>
					<h3></h3>
				</div><!-- cartList end -->          
			</div><!-- right column end -->
		</div><!-- row end -->
	</div><!-- container-fluid end -->


	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="${context}/js/takeaway.js"></script>
	<script type="text/javascript">
        
		function detectSum() {
			var sum = $('#sum').find('input').val();
			var condi = $("input[name='orderType']:checked").val() == '3'?sum>=${findByPrimaryKey.min_order}:sum!=0;
			compareSumWith(condi);
		}
        
        function changeShoppingMode() {
        	initCoords();
        	var deliveryText = "• 最低外送金額 :<c:out value='${findByPrimaryKey.min_order}' default='0'/><br>" +
							   "• 確認地址：<br>" +
							   "<input type='text' class='form-control input-sm' name='mem_add' value='${memAdd}'>";
			var takyawayText = "• 店家地址:<br> ${findByPrimaryKey.store_add}<br>" +
								"<div id='smallMap' style='width:100%; height:150px'></div>" +
								"• 現在位置到店家約 : <input id='dist' type='text' style='width:100%'>";
        	var radioText = $("input[name='orderType']:checked").val() == '3'?deliveryText:takyawayText;
        	$('#orderTypeDetail').html(radioText);
        	detectSum();
        }

    </script>
	<script type="text/javascript">
		var map;
		var directionsService;
		var directionsDisplay;
		var service;
		
		function initCoords() {
			navigator.geolocation? navigator.geolocation.getCurrentPosition(initMap, function(){alert("error code : " + e.code)})
								: showError("Your browser does not support Geolocation!");
		}
        
		function initMap(e) {
			directionsDisplay = new google.maps.DirectionsRenderer();
			directionsService = new google.maps.DirectionsService();
			service = new google.maps.DistanceMatrixService();
			var lati = e.coords.latitude;
			var longi = e.coords.longitude;         
          
			var xy = new google.maps.LatLng(lati, longi);
          
			map = new google.maps.Map(document.getElementById('smallMap'), {
				zoom: 12,
				center: xy,
				mapTypeId: google.maps.MapTypeId.ROADMAP
			});
			
			directionsDisplay.setMap(map);
           
			var markerSelf = new google.maps.Marker({
				position: new google.maps.LatLng(lati,longi),
				map: map,
				icon: '${context}/img/map-pin.png',
				title: 'Your position'
			})
                    
			
			var bounds = new google.maps.LatLngBounds();
            
			var start = new google.maps.LatLng(lati, longi);                   
			var end = new google.maps.LatLng(<c:out value="${findByPrimaryKey.latitude}"/>, <c:out value="${findByPrimaryKey.longitude}"/>);                   
			
			var marker = new google.maps.Marker({
												position: end,
												map: map,
												icon: '${context}/img/coffee32.png',
												title: '<c:out value="${findByPrimaryKey.store_name}"/>'
												});	
			
			bounds.extend(start);
			bounds.extend(end);
	      		                  
			map.fitBounds(bounds);
			var request = {
				origin: start,
				destination: end,
				travelMode: google.maps.TravelMode.DRIVING,
			};
			
			directionsService.route(request, function (response, status) {
				if (status == google.maps.DirectionsStatus.OK) {
					directionsDisplay.setDirections(response);
					directionsDisplay.setMap(map);
				} else {
					alert("Directions Request from " + start.toUrlValue(6) + " to " + end.toUrlValue(6) + " failed: " + status);
				}
			});
			
			service.getDistanceMatrix({
								        origins: [start],
								        destinations: [end],
								        travelMode: google.maps.TravelMode.DRIVING,
								        avoidHighways: false,
								        avoidTolls: false
								    	}, 
								    	callback
									);
			function callback(response, status) {
			    var dist = document.getElementById("dist");

			    if(status=="OK") {
			        dist.value = response.rows[0].elements[0].distance.text;
			    } else {
			        alert("Error: " + status);
			    }
			}
		}
		</script>
		<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCv5F922wKBqLMCXNpAsLAs08co7G32QAU&callback=initCoords"></script>
  </body>
</html>    