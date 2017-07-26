<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.spndcoffee.model.*"%>

<%@ include file="/pages/isVisitor.jsp" %>

<%
    SpndcoffeeService spndcoffeeSvc = new SpndcoffeeService();
    List<SpndcoffeeVO> list = spndcoffeeSvc.getAllWithinTheDeadline();
    pageContext.setAttribute("list",list);  //?
%>

<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />

<!DOCTYPE html>
<html lang="">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>活動清單</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <!--[if lt IE 9]>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css">
    <style type="text/css">
        .body {
        font-size: 14px;
        line-height: 1.428571429;
    }
    
    .footer {
        grid-row-start: 2;
        grid-row-end: 3;
        background-color: #333;
        color: #FFF;
        line-height: 1.428571429;
    }
    
    .footer:before {
        content: "";
        display: block;
        height: 4px;
        /*background: #0088d2;*/
        margin-bottom: 16px;
    }
    
    .textsizeb {
        font-weight: bold;
    }
    
    .btn-cofe {
        background: #fff;
        /* Old browsers */
        background: -moz-linear-gradient(top, #7f5f42 0%, #7f5f42 50%, #7f5f42 100%);
        /* FF3.6+ */
        background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #7f5f42), color-stop(50%, #7f5f42), color-stop(100%, #7f5f42));
        /*   Chrome,Safari4+ */
        background: -webkit-linear-gradient(top, #7f5f42 0%, #7f5f42 50%, #7f5f42 100%);
        /* Chrome10+,Safari5.1+ */
        background: -o-linear-gradient(top, #7f5f42 0%, #7f5f42 50%, #7f5f42 100%);
        /* Opera 11.10+ */
        background: -ms-linear-gradient(top, #7f5f42 0%, #7f5f42 50%, #7f5f42 100%);
        /* IE10+ */
        background: linear-gradient(to bottom, #7f5f42 0%, #7f5f42 50%, #7f5f42 100%);
        /* W3C */
        filter: progid: DXImageTransform.Microsoft.gradient( startColorstr='#7f5f42', endColorstr='#7f5f42', GradientType=0);
        /* IE6-9 */
        color: #fff;
    }
    
    .btn-cofe:hover {
        background: #603813;
        /*旧版浏览器*/
        color: #fff;
    }
        /*旧版浏览器*/
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
					<li class="active"><a href="<%=request.getContextPath()%>/frontend/spndcoffee/listAllSpndCoffee.jsp">寄杯</a></li>
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
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="請輸入關鍵字">
                    <span class="input-group-btn">
                            <button class="btn btn-cofe" type="button">搜尋</button>
                     </span>
                </div>
            </div>
        </div>
        
         <div class="col-xs-12 col-sm-12">
                <ol class="breadcrumb">
                    <li>寄杯</li>
                    <li class="active">瀏覽所有寄杯活動</li>
                    
                </ol>
            </div>
            
         <span class="input-group-btn">
                            <a href ="<%= request.getContextPath() %>/frontend/member/listSpndCoffeeListByMem_id.jsp"><button class="btn btn-cofe" type="button">瀏覽自己的寄杯活動</button></a>
         </span>   
        <hr class="col-xs-12">
        <div class="row">
        	<%@ include file="/pages/page1.file" %>
        	<c:forEach var="SpndcoffeeVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
                <div class="col-xs-12 col-sm-4">
                    <div class="list">
                        <div class="row">
            				<div class="col-xs-12 col-sm-12">
                					<div class="input-group">
                					</div>
                            	<div class="col-xs-12 col-sm-12">
                                	<img class="product" src="<%= request.getContextPath() %>/images?action=spndcoffee&id=${SpndcoffeeVO.spnd_id}">
                                </div>
                           
                            <div class="col-xs-12 col-sm-12">
                                <div class="float-left margin5px">
                                    <p class="textsizeb">${SpndcoffeeVO.spnd_name}</p>
                                    <p>${SpndcoffeeVO.spnd_enddate}</p>
                                    <p>寄杯商品:${SpndcoffeeVO.spnd_prod}</p>
                                    <p>多杯購買享折扣，更可跨店兌換！濃密奶泡，香醇的咖啡氣息，是每日早晨的微笑秘密 </p>
                                    
                                </div>
                            </div>
                            </div>
                        </div><!-- row end -->
                    </div><!-- list end -->
                </div>
           </c:forEach>
		</div><!-- row end -->
		<div class="text-center">
			<ul class="pagination pagination-lg">
				<li><a href="#">&laquo;</a></li>
                <li class="active"><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li><a href="#">&raquo;</a></li>
            </ul>
        </div>
    </div><!-- container end -->
    <footer class="footer text-center">
        <div class="col-xs-12 col-sm-3">
        	常見問題
        </div>
        <div class="col-xs-12 col-sm-3">
        	功能介紹
        </div>
        <div class="col-xs-12 col-sm-3">
        	關於我們
        </div>
        <div class="col-xs-12 col-sm-3">
        	客服中心
        </div>
        <center style="margin-top: 10px;">BA101第四組</center>
    </footer>
        <script src="https://code.jquery.com/jquery.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>



