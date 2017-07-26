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
		<title>購物車</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">
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
			<div class="col-xs-12 col-sm-6 col-sm-offset-3">
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product/product.do" >
					<div class="input-group">
	                    <input type="text" name="prod_name" class="form-control" placeholder="請輸入關鍵字">
	                    <span class="input-group-btn">
	                        <input type="submit" class="btn btn-primary" value="搜尋" />
	                    </span>
	                    <input type="hidden" name="prod_category" value="1">
	                    <input type="hidden" name="action" value="searchLaunchedByProd_nameAndProd_category">
	                </div>
	            </FORM>
			</div>
			<hr class="col-xs-12">
			<div class="section-title">購物車</div>
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
			<% int count = 0; %>
			<c:forEach var="cart" items="${shoppingcart}">
				<div class="col-xs-12">
					<div class="carts row">
						<h3>購物車 (第<%= ++count %>台)</h3>
						<b>販售店家：</b>
						<div class="col-xs-12">
							<img class="logo float-left margin5px" src="<%=request.getContextPath()%>/images?action=store&id=${cart.key}">
							<div class="float-left store_name">
								<p>${storeSvc.getOneStore(cart.key).store_name}</p>
							</div>
						</div>
						<div id="${cart.key}" class="cart col-xs-12">
							<div class="row">
								<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/orderlist/orderlist.do">
									<div class="col-xs-12 col-sm-10">
										<div class="row">
											<c:forEach var="orderdetailVO" items="${cart.value}">
												<div id="${orderdetailVO.prod_id}" class="cartProduct col-xs-12">
													<div class="prod_img col-xs-12 col-sm-3 col-md-2 text-center">
														<a href="<%=request.getContextPath()%>/frontend/product/product.do?action=getOne_For_Display&prod_id=${orderdetailVO.prod_id}">
															<img src="<%=request.getContextPath()%>/images?action=product&id=${orderdetailVO.prod_id}">
														</a>
													</div>
													<div class="col-xs-12 col-sm-9 col-md-10">
														<div class="row">
															<div class="col-xs-12 col-md-6">
																<p class="prod_name">
																	<a href="<%=request.getContextPath()%>/frontend/product/product.do?action=getOne_For_Display&prod_id=${orderdetailVO.prod_id}">
																		${prodSvc.getOneProduct(orderdetailVO.prod_id).prod_name}
																	</a>
																</p>
																<p>單價：<span class="price colorRed fontSize16px">${orderdetailVO.prod_price}</span>元</p>
															</div>
															<div class="col-xs-12 col-md-6">
																<div class="row">
																	<div class="col-xs-12 col-md-6">
																		<p>庫存 ${prodSvc.getOneProduct(orderdetailVO.prod_id).prod_amt} 件</p>
																		<div class="input-group cartAmt">
																			<span class="input-group-btn">
																		    	<input type="button" class="sub btn btn-success" value="－" />
																		    </span>
																			<input type="text" name="detail_amt" class="detail_amt form-control" value="${orderdetailVO.detail_amt}" maxlength="4" size="4" />
																			<span class="input-group-btn">
																		    	<input type="button" class="add btn btn-success" value="＋" />
																		    </span>
																		    <input type="hidden" class="prod_price" value="${orderdetailVO.prod_price}">
																		    <input type="hidden" class="prod_amt" value="${prodSvc.getOneProduct(orderdetailVO.prod_id).prod_amt}">
																		    <input type="hidden" name="prod_id" value="${orderdetailVO.prod_id}">
																		</div>
																		<br>
																		<input class="removeProduct btn btn-warning" type="button" value="移除" />
																	</div>
																	<div class="col-xs-12 col-md-6">
																		<p>小計：<span class="subtotal colorRed fontSize16px">${orderdetailVO.detail_amt * orderdetailVO.prod_price}</span>元</p>
																	</div>
																</div><!-- row -->
															</div>
														</div><!-- row -->
													</div>
												</div><!-- cartProduct -->
											</c:forEach>
										</div><!-- row -->
									</div>
									<div class="col-xs-12 col-sm-2 text-center">
										<p>訂單總計</p>
										<p><span class="ord_total colorRed fontSize16px"></span>元</p>
										<input class="btn btn-success col-xs-12" type="submit" value="付款結帳" />
										<br><br>
										<a class="btn btn-default col-xs-12" href='<%=request.getContextPath()%>/frontend/product/shop.jsp'>繼續購物</a>
										<input type="hidden" name="action" value="getOne_For_Insert">
										<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
										<input type="hidden" name="store_id" value="${cart.key}">
									</div>
								</FORM>
							</div><!-- row -->
						</div><!-- cart -->
					</div><!-- row -->
				</div><!-- carts -->
			</c:forEach>

			<br><br>
		</div><!-- container end -->
		<br><br><br><br>
	</body>
	<script src="<%=request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/removeProduct.js"></script>
</html>
