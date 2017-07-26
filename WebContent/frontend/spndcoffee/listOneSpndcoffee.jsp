<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spndcoffeercd.model.*"%>
<%@ page import="com.spndcoffee.model.*"%>
<%@ page import="com.spndcoffeelist.model.*"%>
<%@ page import="com.store.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/pages/isVisitor.jsp" %>

<jsp:useBean id="spndcoffeeSvc" scope="page" class="com.spndcoffee.model.SpndcoffeeService" />
<jsp:useBean id="memberSvc" scope="page" class="com.member.model.MemberService" />
<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
<jsp:useBean id="spndcoffeelistSvc" scope="page" class="com.spndcoffeelist.model.SpndcoffeelistService" />
<%-- 取出 對應的DeptVO物件--%>
<%-- 
  DeptService deptSvc = new DeptService();
  DeptVO deptVO = deptSvc.getOneDept(empVO.getDeptno());
--%>

<!DOCTYPE html> 
<html lang=""> 
    <head> 
        <meta charset="utf-8"> 
        <meta http-equiv="X-UA-Compatible" content="IE=edge"> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no"> 
        <title>會員清單</title>         
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css"> 
        <!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->         
        <link rel="stylesheet" href="<%= request.getContextPath()%>/frontend/css/spndcoffee-findmem.css"> 
        <style type="text/css"> 
            /**{
            outline: 1px solid #F00;
            }*/
            body {
            font-family: Microsoft Yahei, Arial, Helvetica, sans-serif, serif;
            position: relative;
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
                        <input type="text" class="form-control" placeholder="請輸入會員名稱"> 
                        <span class="input-group-btn"> <button class="btn btn-cofe" type="button">搜尋</button> </span> 
                    </div>  
                                       
                </div>  
                
                    <div class="col-xs-12 col-sm-12">
                            <ol class="breadcrumb">
                                <li>寄杯</li>
                               
                                <li class="active">寄杯紀錄</li>
                        
                            </ol>
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
                <div class="col-xs-12 col-sm-6 col-lg-4"> 
                    <div class="container-fluid"> 
                        <div class="row"> 
                                                   
                            <div class="col-xs-12 col-sm-6 "> 
                                <h2>寄杯紀錄</h2> 
                            </div>                             
                        </div>
                        <!-- row end -->                         
                    </div>
                    <!-- container-fluid end -->                     
                    <div id="list" class="container-fluid"> 
                        <div class="row header-btn"> 
                                                 
                        </div>
                        <!-- row end -->                         
                        <div class="findStore"> 
                        
                            <img class="logo float-left margin5px" src="img/logo/logo-hdpi-72.png"> 
                            <div class="store-info"> 
                                <div class="store-name">${SpndcoffeelistVO.list_id}</div>                                 
        
                                		寄杯商品：${SpndcoffeelistVO.spnd_prod}
                                <br> 
                               		 	店家名稱：${storeSvc.getOneStore(SpndcoffeelistVO.store_id).store_name}
                                <br> 
                                		個人寄杯總數量：${SpndcoffeelistVO.list_amt}
                                <br>
                      					剩餘杯數：${SpndcoffeelistVO.list_left}
                      			<br>
                      					寄杯時間：<fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${SpndcoffeelistVO.list_date}"/>
                            </div>                             
                            <div style="clear: both;"></div>                             
                        </div>
                       
                        <!-- findStore end -->                         
                                       
                        <div class="text-center"> 
                            <ul class="pagination pagination-xs"> 
                                <li>
                                    <a href="#">&laquo;</a>
                                </li>                                 
                                <li class="active">
                                    <a href="#">1</a>
                                </li>                                 
                                <li>
                                    <a href="#">2</a>
                                </li>                                 
                                <li>
                                    <a href="#">3</a>
                                </li>                                 
                                <li>
                                    <a href="#">4</a>
                                </li>                                 
                                <li>
                                    <a href="#">5</a>
                                </li>                                 
                                <li>
                                    <a href="#">&raquo;</a>
                                </li>                                 
                            </ul>
                            <!-- pagination -->                             
                        </div>                         
                    </div>                     
                </div>      
                
                
                <c:forEach var="SpndcoffeercdVO" items="${spndcoffeelistSvc.getSpndcoffeercdsByList_id(SpndcoffeelistVO.list_id)}" >
                
                           
                <div class="col-xs-12 col-sm-6 "> 
                    <h3>全部取杯紀錄</h3> 
                    <div class=" row-bottom-padded-sm spec_table">
                        <table class="mem_table"> 
                            <tr> 
                                <th>取杯明細編號</th> 
                                <td>${SpndcoffeercdVO.rcd_id}</td>
                            </tr>                             
                            <tr> 
                                <th>寄杯紀錄編號</th> 
                                <td>${SpndcoffeercdVO.list_id}</td> 
                            </tr>                             
                            <tr> 
                                <th>單次領取杯數</th> 
                                <td>${SpndcoffeercdVO.single_amt}</td> 
                            </tr>                             
                            <tr> 
                                <th>取杯時間</th> 
                                <td><fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${SpndcoffeercdVO.rcd_date}"/></td> 
                            </tr>                             
                                                      
                                                    
                        </table>                         
                    </div>                     
                </div>
                </c:forEach>                 
            </div>
            <!-- row end -->             
        </div>
        <!-- container-fluid end -->         
        <script src="https://code.jquery.com/jquery.js"></script>         
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>         
        <script src="http://maps.google.com/maps/api/js"></script>         
        <script src="js/maps.js"></script>         
        <script src="js/mapAutoHeight.js"></script>         
        <script type="text/javascript">
			$(document).ready(function () {

				var list_top = $("#list").offset().top;
				var ready_win_height = $(window).height();

		        $("#list").css("height", (ready_win_height - list_top));

		        $(window).resize(function () {

		            var resize_win_height = $(window).height();
		        	$("#list").css("height", (resize_win_height - list_top));
		        });
		    });
		</script>         
    </body>     
</html>









================================================================


<html>
<head>
<title>寄杯活動資料 - listOneSpndcoffee.jsp</title>
</head>
<body bgcolor='white'>
<b><font color=red>此頁練習採用 Script 的寫法取值:</font></b>
<table border='1' cellpadding='5' cellspacing='0' width='800'>
	<tr bgcolor='#CCCCFF' align='center' valign='middle' height='20'>
		<td>
		<h3>寄杯活動資料 - ListOneSpndcoffee.jsp</h3>
		<a href="<%=request.getContextPath()%>/frontend/spndcoffee/spndcoffee_select_page.jsp"><img src="img/back1.gif" width="100" height="32" border="0">回首頁</a>
		</td>
	</tr>
</table>

<table border='1' bordercolor='#CCCCFF' width='800'>
	<tr>
		<th>寄杯活動編號</th>
		<th>店家編號</th>
		<th>寄杯活動名稱</th>
		<th>寄杯商品</th>
		<th>活動結束日期</th>
		<th>店家寄杯總杯數</th>
		<th>活動圖片</th>
	</tr>
	<tr align='center' valign='middle'>
		<td>${spndcoffeeVO.spnd_id}</td>
		<td>${storeSvc.getOneStore(spndcoffeeVO.getStore_id()).store_name}</td>
		<td>${spndcoffeeVO.spnd_name}</td>
		<td>${spndcoffeeVO.spnd_prod}</td>
		<td>${spndcoffeeVO.spnd_enddate}</td>
		<td>${spndcoffeeVO.spnd_amt}</td>
		<td><img src="<%= request.getContextPath() %>/images?action=spndcoffee&id=${spndcoffeeVO.spnd_id}"></td>
	</tr>
</table>
<table border='1' bordercolor='#CCCCFF' width='800'>
	<tr>
		<th>寄杯紀錄編號</th>
		<th>寄杯活動編號</th>
		<th>會員編號</th>
		<th>寄杯商品</th>
		<th>店家編號</th>
		<th>個人寄杯總數量</th>
		<th>剩餘杯數</th>
		<th>寄杯時間</th>
	</tr>
	<c:forEach var="SpndcoffeelistVO" items="${spndcoffeeSvc.getSpndcoffeelistsBySpnd_id(spndcoffeeVO.spnd_id)}" >
		<tr align='center' valign='middle'>
			<td>${SpndcoffeelistVO.list_id}</td>
          
            <td>${spndcoffeeSvc.getOneSpndcoffee(SpndcoffeelistVO.spnd_id).spnd_name}</td>
            <td>${memberSvc.getOneMember(SpndcoffeelistVO.mem_id).mem_name}</td>
            <td>${SpndcoffeelistVO.spnd_prod}</td>
            <td>${storeSvc.getOneStore(SpndcoffeelistVO.store_id).store_name}</td>
            <td>${SpndcoffeelistVO.list_amt}</td>
            <td>${SpndcoffeelistVO.list_left}</td>
            <td><fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${SpndcoffeelistVO.list_date}"/></td>

		</tr>
	</c:forEach>
</table>
</body>
</html>
