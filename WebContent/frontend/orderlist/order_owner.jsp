<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ include file="/pages/isVisitor.jsp" %>

<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:useBean id="base" class="com.sylvie.picture.BaseChanger" />
<jsp:useBean id="categorySvc" scope="page" class="com.category.model.CategoryService" />
<jsp:useBean id="storeSvc" scope="page"	class="com.store.model.StoreService" />
<jsp:useBean id="memberSvc" scope="page" class="com.member.model.MemberService" />

<c:set value="${STORE.store_id}" var="store_id" />	

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
			<div class="col-xs-12 col-sm-8 col-sm-offset-2">

			</div>
			<div class="col-xs-12">
				<hr>
			</div>
		</div>
		<!-- row end -->
	</div>
	<!-- container end -->

	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-8 col-lg-offset-2">

			
				<div class="clearfix">
					<div class=" pull-left"> 
<!-- 					panel panel-normal -->						
						<ul id="myTab" class="nav nav-tabs">
				     		<li class=""><a href="#takeaway" data-toggle="tab">外帶外送</a></li>
				    		<li class=""><a href="#shopping" data-toggle="tab">購物商品</a></li>				     				          
						</ul>
						
					</div>
				</div>
			
			
			
				<div class="tab-content clearfix">
					<%/* 1st PANEL */ %>
					<div id="takeaway" class="tab-pane">
						<div class="mainDiv">
						<div id="myItems" >
						
						<c:set value="${storeSvc.getOrderlistListByStore_idandOrd_pickDESC(store_id,2,3)}" var="orderlistList2" scope="page"/>
							<display:table id="orderlist2VO" name="pageScope.orderlistList2"
							class="table" pagesize="8" excludedParams="d-7982359-p"
							cellpadding="5px;" cellspacing="5px;" requestURI="${context}/frontend/orderlist/order_owner.jsp" export="false"
							partialList="true" size="${orderlistList2.size()}">
							<c:set value="${orderlist2VO.mem_id}" var="test" />
							<c:set value="${memberSvc.findByPrimaryKey(test)}" var="memberVO" />
							<%@ include file="/pages/orderStatus.jsp"%>
							<display:column title="訂單時間">
								<span id="tr_${orderlist2VO.ord_id}"><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${orderlist2VO.ord_time}" /></span>
							</display:column>
							<display:column title="訂購人">
								<span><c:out value="${memberVO.mem_name}" /></span>
							</display:column>
							<display:column title="總金額" class="text-right">
								<span><fmt:formatNumber value="${orderlist2VO.ord_total}" type = "number" maxFractionDigits = "3" minIntegerDigits = "0"/></span>
							</display:column>		
							<display:column title="出貨型態">
								<span><c:out value="${orderType2}" /></span>
							</display:column>		
							<display:column title="出貨狀態">
								<span><c:out value="${orderStatus2}" /></span>
								<span>
									<c:if test="${orderlist2VO.ord_shipping==1}">
										<button class="btn btn-normal btn-sm takeOrd"><i class="glyphicon glyphicon-ok-sign"></i> 接單</button>							
										<button class="btn btn-sub btn-sm cancelOrd"><i class="glyphicon glyphicon-remove-sign"></i> 拒接</button>
									</c:if>
									<c:if test="${orderlist2VO.ord_shipping==3}">
										<button class="btn btn-normal btn-sm sendInfo">
											<c:out value="${orderlist2VO.ord_pick==2?'發送通知':'出餐'}" />
										</button>							
									</c:if>
									<c:if test="${orderlist2VO.ord_shipping==4}">
										<button class="btn btn-normal btn-sm ordComplete">完成交易</button>							
									</c:if>
								</span>							
							</display:column>		
							<display:column title="訂單明細">
								<span><button data-toggle="collapse" data-target="#detail_${orderlist2VO.ord_id}" class="btn btn-sec btn-sm viewDetail" type="submit">
										查看明細
										</button>
										<input class="ord_add" type="hidden" value="${orderlist2VO.ord_add}">
								</span>
								<tr><td colspan="6" class="detailInfo" style="padding: 0px"><div id='detail_${orderlist2VO.ord_id}' class='collapse'></div></td></tr>
							</display:column>
						</display:table>	
							</div>	
						</div>
					</div>
					

					<%/* 2nd PANEL */ %>
					<div id="shopping" class="tab-pane">
						<div class="mainDiv">
						<div id="myItems">
						
						<c:set value="${storeSvc.getOrderlistListByStore_idandOrd_pickDESC(store_id,1,1)}" var="orderlistList" scope="page"/>
							<display:table id="orderlistVO" name="pageScope.orderlistList"
							class="table" pagesize="8" excludedParams="d-7169637-p"
							cellpadding="5px;" cellspacing="5px;" requestURI="${context}/frontend/orderlist/order_owner.jsp" export="false"
							partialList="true" size="${orderlistList.size()}">
							<c:set value="${orderlistVO.mem_id}" var="test" />
							<c:set value="${memberSvc.findByPrimaryKey(test)}" var="memberVO" />
							<%@ include file="/pages/orderStatus.jsp"%>
							<display:column title="訂單時間">
								<span id="tr_${orderlistVO.ord_id}"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${orderlistVO.ord_time}" /></span>
							</display:column>
							<display:column title="訂購人">
								<span><c:out value="${memberVO.mem_name}" /></span>
							</display:column>
							<display:column title="總金額" class="text-right">
								<span><fmt:formatNumber value="${orderlistVO.ord_total}" type = "number" maxFractionDigits = "3" minIntegerDigits = "0"/></span>
							</display:column>		
							<display:column title="出貨型態">
								<span><c:out value="${orderType}" /></span>
							</display:column>		
							<display:column title="出貨狀態">
								<span><c:out value="${orderStatus}" /></span>
								<span>
									<c:if test="${orderlistVO.ord_shipping==1}">
										<button class="btn btn-normal btn-sm sendInfo">出貨</button>							
									</c:if>
									<c:if test="${orderlistVO.ord_shipping==4}">
											<div class="progress progress-striped" style="width:150px">
												<div class="progress-bar progress-bar-info progress-bar-animated" id='realtime-progress-bar' role="progressbar" style="width: 50%"></div>
											</div>
									</c:if>
								</span>							
							</display:column>		
							<display:column title="訂單明細">
								<span><button data-toggle="collapse" data-target="#detail_${orderlistVO.ord_id}" class="btn btn-sec btn-sm viewDetail" type="submit">
										查看明細
										</button>
										<input class="ord_add" type="hidden" value="${orderlistVO.ord_add}">
								</span>
								<tr><td colspan="6" class="detailInfo" style="padding: 0px"><div id='detail_${orderlistVO.ord_id}' class='collapse'></div></td></tr>														
							</display:column>
						</display:table>
						</div>
					</div><%/* end panel */%>
				</div>
			</div>
			</div><%/* end column */%>
		</div>
		<!-- row end -->
	</div>
	<!-- container-fluid end -->


	<div id="light" class="white_content">
		<form id="divForm" method="post" action="${context}/frontend/product/product.do">
			<div class="form-horizontal">
				<div class="form-group">
					<label class="col-sm-2 control-label">商品名稱</label>
					<div class="col-sm-9">
						<input id="prodName" type="text" class="form-control" name="prod_name">
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">商品類型</label>
					<div class="col-sm-9">
						<select id="selectProdType" class="" name="prod_category">
							<option value="">請選擇</option>
							<option value="1">購物商品</option>
							<option value="2">外帶外送</option>
						</select> <select id="selectCateType" class="" name="cate_id">
							<option value="">請選擇</option>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">單價</label>
					<div class="col-sm-9">
						<input id="prodPrice" type="text" class="form-control" name="prod_price">
					</div>
				</div>

				<div id="storage" class="form-group">
					<label class="col-sm-2 control-label">庫存</label>
					<div class="col-sm-9">
						<input id="prodStorage" type="text" class="form-control" name="prod_amt">
					</div>
				</div>
				
				<div id="" class="form-group">
					<label class="col-sm-2 control-label">商品描述</label>
					<div class="col-sm-9">
						<textarea class="form-control" rows="5" id="comment" name="prod_desc"></textarea>
					</div>
				</div>
				
				<div class="form-group" id="dropZone">
					<label class="col-sm-2 control-label">商品圖片</label>
					<div class="col-sm-9">
						<p style="border: 1px solid #ccc; width: 100%; height: 250px; border-radius: 5px">
							<img id="image" style="height: 250px"> <input id="imgg"
								type="hidden" name="prod_img" value="">
						</p>
					</div>
				</div>
				<div id="errorBox">
				</div>
			</div>

			<p class="text-center">
				<button id="updBtn" type="submit" class="btn btn-info">
					<i class="glyphicon glyphicon-ok-sign"></i> 確認
				</button>
				<input id="switchInput" type="hidden" name="action" value="insert">
				<input id="prodId" type="hidden" name="prod_id" value="">
				<a href="#" id="closeBtn" class="btn btn-info"><i class="glyphicon glyphicon-remove-sign"></i> 取消</a>
			</p>
		</form>
	</div>
	<div id="fade" class="black_overlay"></div>


	<script src="https://code.jquery.com/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="${context}/js/websocket.js"></script>
	<script type="text/javascript">
			
			function ifSecondTab() {
				var attr = $(location).attr('search');
				var secondPage = "d-7982359-p";
				
				if (attr.indexOf(secondPage) != -1){	
					$("#takeaway").removeClass("fade active in");
					$("#shopping").addClass("fade active in");
					$("#myTab li:nth-child(1)").removeClass("active");
					$("#myTab li:nth-child(2)").addClass("active");
				}else {
					$("#shopping").removeClass("fade active in");
					$("#takeaway").addClass("fade active in");
					$("#myTab li:nth-child(2)").removeClass("active");
					$("#myTab li:nth-child(1)").addClass("active");
				}
			
			}

		$(document).ready(function() {
			
			$('.viewDetail').click(function() {
				var btnObj = $(this);
				var detailDiv = btnObj.attr('data-target');
				$.ajax({type : "POST",
						url : "${context}/frontend/orderdetail/orderdetail.do",
						data : {"action" : "findProductsByOrdId",
								"ord_id" : $(this).closest('tr').find('td:nth-child(1) span').attr('id').substring(3),
								"ord_add" : $(this).closest('tr').find('.ord_add').val()
								},
						dataType : "json",
						success : function(data) {
									selectCreate(data, detailDiv, btnObj);
									btnObj.off('click');
								},
						error : function() {alert("error")}
						})
			})

	
			
			ifSecondTab();
			
			function sendStatus() {
				var btnObj = $(this);
				var btnClass = $(this).attr('class');
				var ord_id = $(this).closest('tr').find('td:nth-child(1) span').attr('id').substring(3);
				console.log(ord_id);
				
				var params = {
						type : "POST",
						url : "${context}/frontend/orderlist/orderlist.do",
						dataType : "json",
						data : {"action" : "update",
								"ord_id" : ord_id,
								"ord_shipping" : "3",
								},
						error : function() {
							alert("error");
						}
				}
				if(btnClass.indexOf("takeOrd")!=-1) {
						params.data.ord_shipping = "3";
						params.success = function(data) {
											var token = data[0].ord_shipping + data[0].ord_pick; // 5 takeaway 6 delivery
											var result = token == 5? '發送通知': '出餐';
											btnObj.text(result);								
											btnObj.parent().prev().contents().filter(function(){
												  return this.nodeType == 3; 
												})[0].nodeValue = "已接單"; 						
											btnObj.attr('class', 'btn btn-normal btn-sm sendInfo');
											btnObj.next().remove();
											
											sendMessage(ord_id, data[0].ord_shipping, data[0].ord_pick);
										}	
				}
				if(btnClass.indexOf("sendInfo")!=-1) {
			    	params.data.ord_shipping = "4";
			    	params.success = function(data) {
										var token = data[0].ord_shipping + data[0].ord_pick; //5 shopping 6 takeaway 7 delivery
										var text = token == 5? '出貨中':token == 6? '等待取餐':token == 7? '出餐中':'';
											btnObj.parent().prev().contents().filter(function(){
												  return this.nodeType == 3; 
												})[0].nodeValue = text; 						
										if(token!=5) {
											btnObj.text('完成交易');								
											btnObj.attr('class', 'btn btn-normal btn-sm ordComplete');
											sendMessage(ord_id, data[0].ord_shipping, data[0].ord_pick);
										} else {
											btnObj.replaceWith("<div class='progress progress-striped'>"
																+ "<div class='progress-bar progress-bar-info progress-bar-animated' id='realtime-progress-bar' role='progressbar' style='width: 50%'></div>"
																+ "</div>");
											sendMessage(ord_id, data[0].ord_shipping, data[0].ord_pick);
											<%// not close%>
										}
										
									}					
				}
				if(btnClass.indexOf("cancelOrd")!=-1||btnClass.indexOf("ordComplete")!=-1) {
				    	params.data.ord_shipping = "5";
				    	params.success = function(data) {	
											var span = btnObj.parent();
											span.prev().text('交易結束');
											span.empty();
											
											sendMessage(ord_id, data[0].ord_shipping, data[0].ord_pick);
										}
				}

				$.ajax(params);
			}
			
			
			$('.takeOrd').on('click',sendStatus);
			$('.cancelOrd').on('click', sendStatus);
			$('.ordComplete').on('click', sendStatus);			
			$('.sendInfo').on('click', sendStatus);

	});
		
		function selectCreate(oJson, target, thisObj) {
			var ordId = target.substring(7);
			$(target).empty();

			$(target).html("<table class='table'>"
							+ "<thead><tr><th>商品名稱</th><th>單價</th><th>數量</th><th>小計</th></tr></thead>"
							+ "<tbody id='detailBody" + ordId + "'></tbody>"
							+ "</table>");

			var i = 0;
			$.each(oJson, function() {
				$('#detailBody'+ ordId).append(
						 "<tr><td>" + oJson[i].prod_name + "</td><td>"
									+ oJson[i].prod_price + "</td><td>"
									+ oJson[i].detail_amt + "</td><td>"
									+ oJson[i].detail_amt * oJson[i].prod_price
									+ "</td></tr>");

					if(i==oJson.length-1&&oJson[i].ord_add!="") {						
						$('#detailBody'+ ordId).append("<tr><td colspan='6'><i class='glyphicon glyphicon-map-marker'></i> 外送地址："+ oJson[i].ord_add + "</td></tr>");
					}

				i++;
			});
		}
		
	function connect(endPointURL) {
		// 建立 websocket 物件
		webSocket = new WebSocket(endPointURL);
		
		webSocket.onopen = function(event) {
			console.log("WebSocket 成功連線");
		};

		webSocket.onmessage = function(event) {
	        var jsonObj = JSON.parse(event.data);
	        console.log("ord_id : " + jsonObj.ord_id + "\n ord_shipping : " + jsonObj.ord_shipping);
	        if(jsonObj.ord_pick==1&&jsonObj.ord_shipping==5) {
		        var tr = $('span[id=tr_'+ jsonObj.ord_id + ']').closest('tr');
		        tr.find('td:nth-child(5) div[id=realtime-progress-bar]').css('width', ordShippingMapping(jsonObj.ord_shipping, jsonObj.ord_pick));
		        tr.find('td:nth-child(5) span:nth-child(1)').text(ordTextMapping(jsonObj.ord_shipping, jsonObj.ord_pick));	        	
	        }

		};

		webSocket.onclose = function(event) {
			console.log("WebSocket 已離線");
		};
	}
    
</script>
</body>
</html>