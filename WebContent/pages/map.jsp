<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
	
	var latLng = new google.maps.LatLng(<c:out value="${storeVO.latitude}"/>, <c:out value="${storeVO.longitude}"/>);                   
	bounds.extend(latLng);
		         
	var marker = new google.maps.Marker({
	position: latLng,
	map: map,
	icon: '${context}/img/coffee32.png',
	title: '<c:out value="${storeVO.store_name}"/>'
	});			 
	
		<jsp:setProperty name="base" property="imgByte" value="${storeVO.store_img}" />	 
		var contentString = '<div id="iw-container">' +
                   		    '<div class="iw-title"><div class="clearfix"><div class="pull-left col-lg-8"><c:out value="${storeVO.store_name}"/></div>' + 
		                     			'<div class="pull-right">' +
										'<c:if test="${storeVO.min_order > 0}">' +													
											'<form method="post" action="${context}/frontend/store/store.do">' +
												'<button type="submit" class="btn btn-normal btn-sm" style="width:80px;margin:0px"><i class="glyphicon glyphicon-cutlery"></i> 外帶外送</button>' +
												'<input type="hidden" name="store_id" value="${storeVO.store_id}">' +
												'<input type="hidden" name="action" value="getTakeawayProductsByStore_id">' +
											'</form>' +
										'</c:if>' +
										'</div>' +                  		
									'</div>' +                  		    
								'</div>' +                  		    
                    		'<div class="iw-content">' +
                    		'<div class="iw-subTitle">店家資訊</div>' +
                    			<c:if test="${empty storeVO.store_img}">
									'<div class=""><img class="logo float-left margin5px" style="width:140px" src="${context}/img/acup.jpg"></div>' +
								</c:if>
								<c:if test="${not empty storeVO.store_img}">
									'<div class=""><img class="logo float-left margin5px" src="data:image/jpeg;base64, ${base.baseStr}"></div>' +
								</c:if>
<%--                     		'<div class=""><img src="data:image/jpeg;base64, ${base.baseStr} " height="115"></div>' + --%>
                    			'<div class="clearfix">' +
		                      		'<div class="pull-left">有無Wifi：</div><div class="pull-right"><c:out value="${storeVO.is_wifi==1?'Yes':'No'}"/></div>' +
		                      		'<div class="pull-left">有無低消：</div><div class="pull-right"><c:out value="${storeVO.is_min_order==1?'Yes':'No'}"/></div>' +
		                      		'<div class="pull-left">有無插座：</div><div class="pull-right"><c:out value="${storeVO.is_plug==1?'Yes':'No'}"/></div>' +
		                      		'<div class="pull-left">有無限時：</div><div class="pull-right"><c:out value="${storeVO.is_time_limit==1?'Yes':'No'}"/></div>' +
		                      		'<div class="pull-left">有賣正餐：</div><div class="pull-right"><c:out value="${storeVO.is_meal==1?'Yes':'No'}"/></div>' +
		                      		'<div class="pull-left">有賣甜品：</div><div class="pull-right"><c:out value="${storeVO.is_dessert==1?'Yes':'No'}"/></div>' +
		                      		'<div class="pull-left">有賣單品：</div><div class="pull-right"><c:out value="${storeVO.is_single_orgn==1?'Yes':'No'}"/></div>' +
	                      		'</div>' +  
                    		'</div>' +
                  			'</div>';
		
	var windowDialog = new google.maps.InfoWindow({maxWidth: 350});
		         
	google.maps.event.addListener(marker, "click", (function (marker,contentString,windowDialog) {	
		return function() {
			windowDialog.setContent(contentString);
			windowDialog.open(map, marker); 
		};
	})(marker,contentString,windowDialog));          
	
	google.maps.event.addListener(map, 'click', function() {
    windowDialog.close();
 	});
 	
<!--  	START TO CUSTOMIZE -->
 	google.maps.event.addListener(windowDialog, 'domready', function() {

    var iwOuter = $('.gm-style-iw');

    var iwBackground = iwOuter.prev();

    // Removes background shadow DIV
    iwBackground.children(':nth-child(2)').css({'display' : 'none'});

    // Removes white background DIV
    iwBackground.children(':nth-child(4)').css({'display' : 'none'});

    // Moves the infowindow 115px to the right.
    iwOuter.parent().parent().css({left: '115px'});

    // Moves the shadow of the arrow 76px to the left margin.
    iwBackground.children(':nth-child(1)').attr('style', function(i,s){ return s + 'left: 76px !important;'});

    // Moves the arrow 76px to the left margin.
    iwBackground.children(':nth-child(3)').attr('style', function(i,s){ return s + 'left: 76px !important;'});

    // Changes the desired tail shadow color.
    iwBackground.children(':nth-child(3)').find('div').children().css({'box-shadow': 'rgba(72, 181, 233, 0.6) 0px 1px 6px', 'z-index' : '1'});

    // Reference to the div that groups the close button elements.
    var iwCloseBtn = iwOuter.next();

    // Apply the desired effect to the close button
    iwCloseBtn.css({opacity: '1', right: '48px', top: '8px'});
    iwCloseBtn.css("background-image", "url(${context}/webapp/images/coffee32.png)!important");
    
    // If the content of infowindow not exceed the set maximum height, then the gradient is removed.
    if($('.iw-content').height() < 140){
      $('.iw-bottom-gradient').css({display: 'none'});
    }

    // The API automatically applies 0.7 opacity to the button after the mouseout event. This function reverses this event to the desired value.
    iwCloseBtn.mouseout(function(){
      $(this).css({opacity: '1'});
    });
  });

