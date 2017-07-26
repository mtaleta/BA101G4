	function sendMessage(ord_id, ord_shipping, ord_pick) {
	        var jsonObj = {"ord_id" : ord_id, "ord_shipping" : ord_shipping, "ord_pick" : ord_pick};
	        webSocket.send(JSON.stringify(jsonObj));
	        console.log("sent");
	}
	
	function ordShippingMapping(ordShipping, ordPick) {
		console.log(ordShipping);
		switch(ordShipping) {
	        case 1:return '0%';
	        case 2:return '100%';
	        case 3:return '33%';
	        case 4:return ordPick==1?'50%':'66%';
	        case 5:return '100%';
	        default:return '0%';
    	}
		
	}
	function ordTextMapping(ordShipping, ordPick) {
		console.log(ordShipping);
		switch(ordShipping) {
	        case 1:return '未處理';
	        case 2:return '交易中止';
	        case 3:return '已接單';
	        case 4:return ordPick==1?'已出貨':ordPick==2?'等待取餐':'出餐中';
	        case 5:return '交易結束';
	        default:return '未處理';
    	}
		
	}
		
	function disconnect () {
		webSocket.close();
	}
	
	$(document).ready(function () {
  	  
  	  var orderId = $('td:nth-of-type(1) span');
  	  
	       var orderIdStr;
	       	for(var i = 0; i < orderId.length; i++) {
	       		i==0? orderIdStr = "/" + orderId.eq(i).attr('id').substring(3)
	       			: orderIdStr += "_" + orderId.eq(i).attr('id').substring(3);
	       	}
	       	console.log(orderIdStr);
	       	
	       	var MyPoint = "/MyEchoServer" + orderIdStr;
	        var host = window.location.host;
	        var path = window.location.pathname;
	        var webCtx = path.substring(0, path.indexOf('/', 1));
	        var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
	        
	    	var webSocket;
  	  
  	  // websocket start;
  	  connect(endPointURL); 
});