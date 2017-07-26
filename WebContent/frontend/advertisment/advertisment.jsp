<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<jsp:useBean id="adverSvc" scope="page" class="com.advertisment.model.AdvertismentService" />

<html>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<style type="text/css">
	.carousel-inner>.item>a>img, .carousel-inner>.item>img, .img-responsive, .thumbnail a>img, .thumbnail>img {
    display: block;
    max-width: 100%;
    height:800px;
	}
	
	.AD_IMG {
        
      width:1200px;
        
    }
    
    .slide {
        margin-top: 20px;
    }
    
</style>
<head><title>IBM Store: Home</title></head>
<body bgcolor='white'>
	
   
    <div class="container" >
        <div class="row">
            
            <div class="col-xs-12 col-sm-12" >
                <div id="carousel-id" class="carousel slide" data-ride="carousel" >
                    <!-- 幻燈片小圓點區 -->
                    <ol class="carousel-indicators">
                    	<% int spot = 0; %>
                   		<c:forEach var="adverVO" items="${adverSvc.allup}">
                        <li data-target="#carousel-id" data-slide-to="<%=spot %>" class="<%= spot==0 ? "active" : "" %>"></li>
                        <%spot++; %>
                        </c:forEach>
                    </ol>
                    <!-- 幻燈片主圖區 -->
                    <div class="carousel-inner" >
                    	<% int count = 0; %>
                        <c:forEach var="adverVO" items="${adverSvc.allup}">
	                        <div class="item <%= count==0 ? "active" : "" %>" >
	                        	<% count=1; %>
	                            <img class="AD_IMG" src="<%=request.getContextPath()%>/images?action=advertisment&id=${adverVO.ad_id}" alt="" > 
	                        	
		                        	<a href="<%=request.getContextPath()%>/backend/advertisment/advertisment.do?advertisment_id=${adverVO.ad_id}&action=getAdvertismentContent">
		                        	<div class="carousel-caption" style="border-style: solid; background-Color:#CCC; opacity:0.7;margin-bottom:30px;" >
	   								 <h3 style="color:black">${adverVO.ad_title}</h3>
	  								</div></a>
	  							
	                        </div>
	                        
                        </c:forEach>
                    </div>
                    <!-- 上下頁控制區 -->
                    <a class="left carousel-control" href="#carousel-id" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
                    <a class="right carousel-control" href="#carousel-id" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
                </div>
              </div>
             </div>
            </div>
            






</body>

</html>
<script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>