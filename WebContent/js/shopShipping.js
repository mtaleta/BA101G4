$(document).ready(function(){
	
	var path = window.location.pathname;
	var context = path.substring(0, path.indexOf('/', 1));
	
	$(".shipping").click(function(){

		var shipping = $(this);
		var ord_id = shipping.siblings(".ord_id").val();
		var ord_shipping = shipping.siblings(".ord_shipping").val();
		var tdShipping = shipping.parents(".tdShipping");
		
		$.ajax({
			type:"POST",
			url:context+"/frontend/orderlist/orderlist.do",
			data:{action:"shippingByAjax",
				  ord_id:ord_id,
				  ord_shipping:ord_shipping
			},
			success:function (data){
				
				if(ord_shipping == 4){
					tdShipping.text("已出貨");
				}
				else if(ord_shipping == 5){
					tdShipping.text("完成訂單");
				}
			},
			error:function(){alert("error");}
        })

	});
})