<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ include file="/pages/isVisitor.jsp" %>

<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:useBean id="memberSvc" scope="page" class="com.member.model.MemberService" />
<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />

<c:set value="${MEMBER.mem_id}" var="mem_id"/>

<!DOCTYPE html>
<html lang="">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Title Page</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
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
    </nav><!-- nav end -->

    <div class="container">
      <div class="row">
        <div class="col-xs-12 col-sm-8 col-sm-offset-2">
          
        </div>
        <div class="col-xs-12"><hr></div>
      </div><!-- row end -->
    </div><!-- container end -->

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
              
              <div id="myItems">        
				<c:set value="${memberSvc.getOrderlistListByMem_idAndOrd_pickDESC(mem_id, 2, 3)}" var="orderlistList2" scope="page"/>

				<display:table id="orderlist2VO" name="pageScope.orderlistList2" class="table" pagesize="8"  excludedParams="d-7982359-p"
				cellpadding="5px;" cellspacing="5px;" requestURI="${context}/frontend/orderlist/order_mem.jsp" export="false"
				partialList="true" size="${orderlistList2.size()}">
				<c:set value="${orderlist2VO.store_id}" var="store_id" />
				<c:set value="${storeSvc.findByPrimaryKey(store_id)}" var="storeVO" />
				<%@ include file="/pages/orderStatus.jsp"%>
				<display:column title="訂單時間">
					<span id="tr_${orderlist2VO.ord_id}"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${orderlist2VO.ord_time}" /></span>
				</display:column>
				<display:column title="店家">
					<span><c:out value="${storeVO.store_name}" /></span>
				</display:column>
				<display:column title="總金額">
					<span><c:out value="${orderlist2VO.ord_total}" /></span>
				</display:column>		
				<display:column title="消費型態">
					<span><c:out value="${orderType2}" /></span>
				</display:column>		
				<display:column title="出貨狀態">
					<c:if test="${orderlist2VO.ord_shipping!=5}">				
						<div class="progress progress-striped">
							<div class="progress-bar progress-bar-info progress-bar-animated" id='realtime-progress-bar' role="progressbar" style="width: ${orderlist2VO.ord_shipping==1?'0%':orderlist2VO.ord_shipping==2?'100%':orderlist2VO.ord_shipping==3?'33%':orderlist2VO.ord_shipping==4?'66%':'100%'}"></div>
						</div>
					</c:if>
					<p class="messages"><c:out value="${orderStatus2}" /></p>                      															
				</display:column>		
				<display:column title="訂單明細">
					<span><button data-toggle="collapse" data-target="#detail_${orderlist2VO.ord_id}" class="btn btn-sec btn-sm viewDetail" type="submit">
							查看訂單
							</button>
							<input type='hidden' name='ord_id' value='${orderlist2VO.ord_id}'>
					</span>
					<tr><td colspan="6" class="detailInfo" style="padding: 0px"><div id='detail_${orderlist2VO.ord_id}' class='collapse'></div></td></tr>								
				</display:column>									
				</display:table>
              </div>
            
          </div>          
          </div>
          <%/* 2st PANEL */ %>
			<div id="shopping" class="tab-pane">
          	<div class="mainDiv">             
              <div id="myItems">        
				<c:set value="${memberSvc.getOrderlistListByMem_idAndOrd_pickDESC(mem_id, 1, 1)}" var="orderlistList" scope="page"/>

				<display:table id="orderlistVO" name="pageScope.orderlistList" class="table" pagesize="8" excludedParams="d-7169637-p"
				cellpadding="5px;" cellspacing="5px;" requestURI="${context}/frontend/orderlist/order_mem.jsp" export="false"
				partialList="true" size="${orderlistList.size()}">
				<c:set value="${orderlistVO.store_id}" var="store_id" />
				<c:set value="${storeSvc.findByPrimaryKey(store_id)}" var="storeVO" />
				<%@ include file="/pages/orderStatus.jsp"%>
				<display:column title="訂單時間">
					<span id="tr_${orderlistVO.ord_id}"><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${orderlistVO.ord_time}" /></span>
				</display:column>
				<display:column title="店家">
					<span><c:out value="${storeVO.store_name}" /></span>
				</display:column>
				<display:column title="總金額">
					<span><c:out value="${orderlistVO.ord_total}" /></span>
				</display:column>		
				<display:column title="消費型態">
					<span><c:out value="${orderType}" /></span>
				</display:column>		
				<display:column title="出貨狀態">
					<c:if test="${orderlistVO.ord_shipping!=5}">
						<div class="progress progress-striped">
							<div class="progress-bar progress-bar-info progress-bar-animated" id='realtime-progress-bar' role="progressbar" style="width: ${orderlistVO.ord_shipping==1?'0%':orderlistVO.ord_shipping==4?'50%':'100%'}"></div>
						</div>
						<c:if test="${orderlistVO.ord_shipping==1}">
							<p class="messages text-center"><c:out value="${orderStatus}" /></p>
						</c:if>
						<c:if test="${orderlistVO.ord_shipping==4}">
							<span><button class='btn btn-normal btn-sm ordComplete'>完成交易</button></span>
						</c:if>
					</c:if>
					<c:if test="${orderlistVO.ord_shipping==5}">
						<p class="messages"><c:out value="${orderStatus}" /></p>
					</c:if>				                    															
				</display:column>		
				<display:column title="訂單明細">
					<span><button data-toggle="collapse" data-target="#detail_${orderlistVO.ord_id}" class="btn btn-sec btn-sm viewDetail" type="submit">
							查看訂單
							</button>
							<input type='hidden' name='ord_id' value='${orderlistVO.ord_id}'>
					</span>
					<tr><td colspan="6" class="detailInfo" style="padding: 0px"><div id='detail_${orderlistVO.ord_id}' class='collapse'></div></td></tr>								
				</display:column>									
				</display:table>
              </div>
            
          </div>          
          </div>
           </div><!-- tab-content end -->
           
         </div>
      </div><!-- row end -->
    </div><!-- container-fluid end -->


    
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="${context}/js/websocket.js"></script>
    <script type="text/javascript">
      $(document).ready(function () {
    	      	  
    	  ifSecondTab();
    	  
    	  $('.ordComplete').off('click').on('click',sendStatus);
            
		$('.viewDetail').click(function(){
			var btnObj = $(this);
			var detailDiv = btnObj.attr('data-target');
			$.ajax({
					type:"POST",
	           		url : "${context}/frontend/orderdetail/orderdetail.do",
	           		data : {"action" : "findProductsByOrdId",
							"ord_id" : $(this).closest('tr').find('td:nth-child(1) span').attr('id').substring(3),
							},
	           		dataType:"json",
	           		success:function (data){
		           		selectCreate(data, detailDiv);
		           		btnObj.off('click');
	           		},
	                error:function(){alert("error")}
	            })
	       })   
     
        });
      
      function selectCreate(oJson,target){
    	  var ordId = target.substring(7);
    		$(target).empty();
    		
    		$(target).html("<table class='table'>" +
						    "<thead><tr><th>商品名稱</th><th>單價</th><th>數量</th><th>小計</th></tr></thead>" +
						    "<tbody id='detailBody" + ordId + "'></tbody>" +
						    "</table>");
	
    		var i = 0;
    		$.each(oJson,function(){
    			$('#detailBody'+ ordId).append("<tr><td>"+ oJson[i].prod_name + "</td><td>" + oJson[i].prod_price + "</td><td>" + oJson[i].detail_amt + "</td><td>" + oJson[i].detail_amt*oJson[i].prod_price + "</td></tr>");
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
	        var tr = $('span[id=tr_'+ jsonObj.ord_id + ']').closest('tr');
	        tr.find('td:nth-child(5) div[id=realtime-progress-bar]').css('width', ordShippingMapping(jsonObj.ord_shipping, jsonObj.ord_pick));
	        tr.find('.messages').text(ordTextMapping(jsonObj.ord_shipping, jsonObj.ord_pick));
	        if(jsonObj.ord_pick==1&&jsonObj.ord_shipping==4) {
	        	tr.find('.messages').after().html("<span><button class='btn btn-normal btn-sm ordComplete'>完成交易</button></span>");
	        	$('.ordComplete').off('click').on('click',sendStatus);
	        }
		};

		webSocket.onclose = function(event) {
			console.log("WebSocket 已離線");
		};
	}
	
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
						"ord_shipping" : "5",
						},
				success : function(data) {	
								var span = btnObj.parent();
								span.empty();
								span.text('交易結束');								
								sendMessage(ord_id, data[0].ord_shipping, data[0].ord_pick);
						},
				error : function() {
					alert("error");
				}
		}
		
		$.ajax(params);
	}
	
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

    
</script>
  </body>
</html>