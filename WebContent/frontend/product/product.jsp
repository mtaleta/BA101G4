<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.product.model.*"%>
<%@ page import="com.msg.model.*"%>
<%@ page import="com.reply.model.*"%>

<%@ include file="/pages/isVisitor.jsp" %>

<jsp:useBean id="prodSvc" scope="page" class="com.product.model.ProductService" />
<jsp:useBean id="memSvc" scope="page" class="com.member.model.MemberService" />
<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
<jsp:useBean id="cateSvc" scope="page" class="com.category.model.CategoryService" />
<jsp:useBean id="msgSvc" scope="page" class="com.msg.model.MsgService" />

<c:if test="${not empty productVO}" var="hasProduct" scope="page">
	<c:set var="storeVO" value="${storeSvc.getOneStore(productVO.store_id)}" scope="page" />
</c:if>

<%
	MsgVO msgVO = (MsgVO) request.getAttribute("msgVO");
	ReplyVO replyVO = (ReplyVO) request.getAttribute("replyVO");
%>

<!DOCTYPE html>
<html lang="">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>${productVO.prod_name}</title>
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
				</div><!-- 手機隱藏選單區結束 -->
			</div>
		</nav><!-- nav end -->
	
		<div class="container">
			<div class="col-xs-12 col-sm-6 col-sm-offset-3">
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/product/product.do">
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
					<c:forEach var="cateVO" items="${cateSvc.getByProd_category(1)}">
						<li><a href="<%=request.getContextPath()%>/frontend/category/category.do?action=listLaunchedProductsByCate_idDesc&cate_id=${cateVO.cate_id}">${cateVO.cate_name}</a></li>
					</c:forEach>
				</ul>
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
	
			<div class="col-xs-12">
				<div class="theProduct product">
					<div class="row">
						<div class="col-xs-12 col-sm-5">
							<div class="prod_img text-center">
								<img src="<%=request.getContextPath()%>/images?action=product&id=${productVO.prod_id}">
							</div>
						</div>
						<div class="col-xs-12 col-sm-4">
							<b class="prod_name">
								${productVO.prod_name}
							</b>
							<p>
								<i class="glyphicon glyphicon-usd"></i><span class="price colorRed fontSize16px">${productVO.prod_price}</span>
							</p>
							<p>
								庫存 ${productVO.prod_amt} 件
							</p>
							<br>
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
								<c:if test="${!hasAmt}">
									<span class="col-xs-12 colorRed fontSize16px text-center">已無庫存</span>
								</c:if>
							</c:if>
							<div class="row">
								<div class="col-xs-12"><br>${productVO.prod_desc}</div>
							</div>
						</div>
						<div class="store col-xs-12 col-sm-3">
							<div class="text-center">
								<img class="logo margin5px" src="<%=request.getContextPath()%>/images?action=store&id=${storeSvc.getOneStore(productVO.store_id).store_id}">
							</div>
							<div class="store_name">
								<p>${storeVO.store_name}</p>
							</div>
							<div>
								<p><i class="glyphicon glyphicon-phone-alt"></i> ${storeVO.store_tel}</p>
							</div>
							<div>
								<p><i class="glyphicon glyphicon-map-marker"></i> ${storeVO.store_add}</p>
							</div>
						</div>
					</div><!-- row -->
				</div><!-- class="theProduct product" -->
			</div>
			<hr class="col-xs-12">
			<div class="section-title">問與答</div>
	        <c:if test="${not empty MEMBER}">
	        	<hr class="col-xs-12">
				<div class="col-xs-12 questions">
					<p>提出問題</p>
					<br>
					<div class="text-center">
						<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/msg/msg.do">
							<textarea name="msg_content" rows="10" cols="130"><%= (msgVO == null) ? "來留言吧!!!" : msgVO.getMsg_content() %></textarea>
							<br>
							<input type="hidden" name="action" value="insert">
							<input type="hidden" name="prod_id" value="${productVO.prod_id}">
							<br>
							<input class="btn btn-success" type="submit" value="提出問題">
						</FORM>
					</div>
				</div>
	        </c:if>
			<hr class="col-xs-12">
			<div class="msg col-xs-12">
				<c:forEach var="msgVO" items="${prodSvc.getMsgsByProd_id(productVO.prod_id)}" >
					<img class="msg_logo" src="<%=request.getContextPath()%>/images?action=member&id=${memSvc.getOneMember(msgVO.mem_id).mem_id}">
					<span class="msg_member">${memSvc.getOneMember(msgVO.mem_id).mem_name}</span>
					<span class="msg_time time">
						(
						<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${msgVO.msg_date}"/>
						)
					</span>
					<div class="msg_content">
						${msgVO.msg_content}
					</div>

					<c:forEach var="replyVO" items="${msgSvc.getReplysByMsg_id(msgVO.msg_id)}" >
						<div class="reply">
							<c:if test="${not empty replyVO.mem_id}">
								<img class="reply_logo" src="<%=request.getContextPath()%>/images?action=member&id=${memSvc.getOneMember(replyVO.mem_id).mem_id}">
								<span class="reply_member">
									${memSvc.getOneMember(replyVO.mem_id).mem_name}
								</span>
							</c:if>
							<c:if test="${not empty replyVO.store_id}">
								<img class="reply_logo" src="<%=request.getContextPath()%>/images?action=store&id=${storeSvc.getOneStore(replyVO.store_id).store_id}">
								<span class="reply_member">
									${storeSvc.getOneStore(replyVO.store_id).store_name}
								</span>
							</c:if>
							<span class="reply_time time">
								(
								<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${replyVO.reply_date}"/>
								)
							</span>
							<div class="reply_content">
								${replyVO.reply_content}
							</div>
						</div>
					</c:forEach>
					<c:if test="${not empty MEMBER || not empty STORE}">
						<div class="reply text-center">
							<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/frontend/reply/reply.do" name="form1">
								<textarea name="reply_content" rows="3" cols="130"><%= (replyVO == null) ? "回覆吧!!!" : replyVO.getReply_content() %></textarea>
								<br>
								<input type="hidden" name="action" value="insert">
								<input type="hidden" name="msg_id" value="${msgVO.msg_id}">
								<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
								<input class="btn btn-success" type="submit" value="回覆">
							</FORM>
						</div>
					</c:if>
				</c:forEach>
			</div>
		</div><!-- container end -->
		<br>
		<br>
		<br>
		<br>
	</body>
	
	<script src="<%=request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/sweetalert.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/addCart.js"></script>
	<script src="<%=request.getContextPath()%>/js/launchedProduct.js"></script>

</html>
