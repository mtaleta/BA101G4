<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>

<%@ include file="/pages/isVisitor.jsp" %>

<jsp:useBean id="searchLaunchedByProd_nameAndProd_category" scope="request" type="java.util.List" />
<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
<jsp:useBean id="cateSvc" scope="page" class="com.category.model.CategoryService" />

<!DOCTYPE html>
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>購物 - 搜尋</title>
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
						<li class="active"><a href="<%=request.getContextPath()%>/frontend/product/shop.jsp">購物</a></li>
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
			<div class="col-xs-12">
				<ul class="nav nav-pills">
					<li><a href="<%=request.getContextPath()%>/frontend/product/shop.jsp">全部商品</a></li>
					<c:forEach var="cateVO" items="${cateSvc.getByProd_category(1)}" >
						<li><a href="<%=request.getContextPath()%>/frontend/category/category.do?action=listLaunchedProductsByCate_idDesc&cate_id=${cateVO.cate_id}">${cateVO.cate_name}</a></li>
					</c:forEach>
				</ul>
			</div>
        	<hr class="col-xs-12">
			<div class="section-title">
				<c:forEach var="keyword" items="${searchLaunchedByProd_name}">
					${keyword}
				</c:forEach>
			</div>
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
			
			<c:if test="${empty searchLaunchedByProd_nameAndProd_category}">
				<div class="col-xs-12">
					<h1>查無商品</h1>
				</div>
			</c:if>
			<%@ include file="/frontend/product/pages/page1_searchLaunchedByProd_nameAndProd_category.file" %> 
			<c:forEach var="productVO" items="${searchLaunchedByProd_nameAndProd_category}" varStatus="count" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				<c:if test="${count.count % 3 == 1}">
					<div class="row">
				</c:if>
				<div class="col-xs-12 col-sm-4">
					<div class="shopProduct product">
						<div class="row">
							<div class="prod_img col-xs-12 col-sm-12 text-center">
								<a href="<%=request.getContextPath()%>/frontend/product/product.do?action=getOne_For_Display&prod_id=${productVO.prod_id}">
									<img src="<%=request.getContextPath()%>/images?action=product&id=${productVO.prod_id}">
								</a>
							</div>
							<div class="col-xs-12 col-sm-12">
								<p class="prod_name">
									<a href="<%=request.getContextPath()%>/frontend/product/product.do?action=getOne_For_Display&prod_id=${productVO.prod_id}">
										${productVO.prod_name}
									</a>
								</p>
								<p class="text-center"><span class="price colorRed fontSize16px">${productVO.prod_price}</span>元</p>
							</div>
							<div class="col-xs-12 col-sm-12">
								<img class="logo float-left margin5px" src="<%=request.getContextPath()%>/images?action=store&id=${storeSvc.getOneStore(productVO.store_id).store_id}">
								<div class="float-left store_name">
									<p>${storeSvc.getOneStore(productVO.store_id).store_name}</p>
								</div>
							</div>
							<c:if test="${not empty MEMBER}">
								<c:if test="${productVO.prod_amt > 0}" var="hasAmt">
									<div class="col-xs-12 col-sm-8 col-sm-push-2">
										<div class="input-group cartAmt">
											<span class="input-group-btn">
										    	<input type="button" class="sub btn btn-success" value="－" />
										    </span>
											<input type="text" class="detail_amt form-control" value="1" maxlength="4" size="4" />
											<span class="input-group-btn">
										    	<input type="button" class="add btn btn-success" value="＋" />
										    </span>
										    <input type="hidden" class="prod_amt" value="${productVO.prod_amt}">
										</div>
										<br>
										<div class="addCart btn btn-success col-xs-12">
											<i class="glyphicon glyphicon-shopping-cart"></i>
											<span>加入購物車</span>
										</div>
										<input type="hidden" class="prod_id" value="${productVO.prod_id}">
										<input type="hidden" class="store_id" value="${productVO.store_id}">
									</div>
								</c:if>
								<c:if test="${!hasAmt}"><span class="col-xs-12 colorRed fontSize16px text-center">已無庫存</span></c:if>
							</c:if>
						</div><!-- .row end -->
						<br>
					</div><!-- .product end -->
				</div>
				<c:if test="${count.count % 3 == 0}">
					</div><!-- .row end -->
				</c:if>
			</c:forEach>

			<%@ include file="/frontend/product/pages/page2_searchLaunchedByProd_nameAndProd_category.file" %>
			<br><br>
		</div><!-- container end -->
	</body>

	<script src="<%=request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/sweetalert.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/addCart.js"></script>

</html>