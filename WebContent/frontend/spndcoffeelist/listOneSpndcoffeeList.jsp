<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.spndcoffeercd.model.*"%>
<%@ page import="com.spndcoffee.model.*"%>
<%@ page import="com.spndcoffeelist.model.*"%>

<%@ include file="/pages/isVisitor.jsp" %>

<jsp:useBean id="spndcoffeeSvc" scope="page" class="com.spndcoffee.model.SpndcoffeeService" />
<jsp:useBean id="spndcoffeelistSvc" scope="page" class="com.spndcoffeelist.model.SpndcoffeelistService" />
<jsp:useBean id="memberSvc" scope="page" class="com.member.model.MemberService" />
<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />

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
   
                            <img class=" float-left margin5px product" width="300" src="<%= request.getContextPath() %>/images?action=spndcoffee&id=${SpndcoffeelistVO.spnd_id}"> 
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
                            
                            <!-- pagination -->                             
                        </div>                         
                    </div>                     
                </div>      
                
                <h3>全部取杯紀錄</h3> 
                <c:forEach var="SpndcoffeercdVO" items="${spndcoffeelistSvc.getSpndcoffeercdsByList_id(SpndcoffeelistVO.list_id)}" >
                
                           
                <div class="col-xs-12 col-sm-6 "> 
             
                    <div class=" row-bottom-padded-sm spec_table">
                        <table class="mem_table"> 
                            <tr> 
                                <th>取杯明細編號</th> 
                                <td>${SpndcoffeercdVO.rcd_id}</td>
                            </tr>                             
                                                       
                            <tr> 
                                <th>單次領取杯數</th> 
                                <td>${SpndcoffeercdVO.single_amt}</td> 
                            </tr>                             
                            <tr> 
                                <th>取杯時間</th> 
                                <td><fmt:formatDate  pattern="yyyy-MM-dd HH:mm" value="${SpndcoffeercdVO.rcd_date}"/></td> 
                            </tr>                             
                            
                            
                           <tr>
                           		<th> -----------------------------</th>
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



