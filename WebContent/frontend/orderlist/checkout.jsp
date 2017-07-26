<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>

<%@ include file="/pages/isVisitor.jsp" %>

<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
<jsp:useBean id="prodSvc" scope="page" class="com.product.model.ProductService" />

<!DOCTYPE html>
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>購物結帳</title>
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
			<div class="section-title">購物結帳</div>
	        <hr class="col-xs-12">

			<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">
				<font color='red'>請修正以下錯誤:
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li>${message}</li>
					</c:forEach>
				</ul>
				</font>
			</c:if>
			<div class="col-xs-12">
				<div class="carts row">
					<FORM id="form" METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/orderlist/orderlist.do">
						<div class="col-xs-12">
							<p>販售店家：</p>
							<img class="logo float-left margin5px" src="<%=request.getContextPath()%>/images?action=store&id=${param.store_id}">
							<div class="float-left store_name">
								<p>${storeSvc.getOneStore(param.store_id).store_name}</p>
							</div>
						</div>
						<div class="cart col-xs-12">
							<div class="row">
								<c:forEach var="orderdetailVO" items="${shoppingcart[param.store_id]}">
									<div class="cartProduct col-xs-12">
										<div class="prod_img col-xs-12 col-sm-3 col-md-2 text-center">
											<a href="<%=request.getContextPath()%>/frontend/product/product.do?action=getOne_For_Display&prod_id=${orderdetailVO.prod_id}">
												<img src="<%=request.getContextPath()%>/images?action=product&id=${orderdetailVO.prod_id}">
											</a>
										</div>
										<div class="col-xs-12 col-sm-9 col-md-10">
											<div class="row">
												<div class="col-xs-12">
													<p class="prod_name">
														<a href="<%=request.getContextPath()%>/frontend/product/product.do?action=getOne_For_Display&prod_id=${orderdetailVO.prod_id}">
															${prodSvc.getOneProduct(orderdetailVO.prod_id).prod_name}
														</a>
													</p>
												</div>
												<div class="col-xs-12 col-sm-4 text-center">
													<p>單價</p>
													<p class="price colorRed fontSize16px">${orderdetailVO.prod_price}</p>
												</div>
												<div class="col-xs-12 col-sm-4 text-center">
													<p>數量</p>
													<p>${orderdetailVO.detail_amt}</p>
												</div>
												<div class="col-xs-12 col-sm-4 text-center">
													<p>小計</p>
													<p class="subtotal colorRed fontSize16px">${orderdetailVO.detail_amt * orderdetailVO.prod_price}</p>
												</div>
											</div><!-- row -->
										</div>
									</div><!-- cartProduct -->
								</c:forEach>
							</div><!-- row -->
						</div><!-- cart -->
						<div class="col-xs-12">
							<div class="row">
								<label class="col-xs-12" for="ord_add">收件地址：</label>
								<div class="col-xs-12">
									<input class="col-xs-12 col-sm-6" type="TEXT" id="ord_add" name="ord_add" value="${MEMBER.mem_add}">
								</div>
							</div>
						</div>
						<div class="col-xs-12">
							<c:if test="${empty ord_total}">
								<c:set var="ord_total" value="${param.ord_total}" scope="request" />
							</c:if>
							<p>訂單總計</p>
							<p><span id="ord_total" class="ord_total colorRed fontSize20px">${ord_total}</span>元</p>
							<input id="submitBtn" class="btn btn-success col-xs-12" type="button" value="結帳" />
							<input type="hidden" name="action" value="insert">
							<input type="hidden" name="store_id" value="${param.store_id}">
							<input type="hidden" name="ord_total" value="${ord_total}">
							<input type="hidden" name="ord_pick" value="1">
							<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
						</div>
					</FORM>
				</div><!-- carts --><!-- row -->
			</div>
			<br><br>
		</div><!-- container end -->
		<br><br><br><br>
	</body>
	<script src="<%=request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/sweetalert.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/checkout.js"></script>
	
</html>
