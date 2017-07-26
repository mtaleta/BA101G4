$(document).ready(function(){
	
	var path = window.location.pathname;
	var context = path.substring(0, path.indexOf('/', 1));
	
	function remove(store_id, prod_id){
		
		$.ajax({
			type:"POST",
			url:context+"/frontend/orderlist/orderlist.do",
			data:{action:"removeProductByAjax",
				  store_id:store_id.attr("id"),
				  prod_id:prod_id.attr("id")
			},
			success:function(data){
				prod_id.remove();
				changeOrdtotal(store_id);
				if(store_id.find(".cartProduct").length == 0){
					store_id.remove();
				}
			},
			error:function(){alert("error");}
        })
	}
	
	function changeOrdtotal(store_id){
		
		var ord_total = store_id.find(".ord_total");
		var prod_price = store_id.find(".prod_price");
		var detail_amt = store_id.find(".detail_amt");
		
		var total = 0;
		for(var i = 0; i < detail_amt.length; i++){
			total = total + parseInt(prod_price.eq(i).val()) * parseInt(detail_amt.eq(i).val());
		}
		ord_total.text(total);
	}
	
	function changeSubtotal(detail_amt){
		
		var price = parseInt(detail_amt.siblings(".prod_price").val());
		var subtotal = detail_amt.parents(".cartProduct").find(".subtotal");

		subtotal.text(parseInt(detail_amt.val()) * price);
	}
	
	$(".removeProduct").click(function(){

		var store_id = $(this).parents(".cart");
		var prod_id = $(this).parents(".cartProduct");

		remove(store_id, prod_id);

	});

	$(".sub").click(function(){

		var sub = $(this);
		var detail_amt = sub.parents(".cartProduct").find(".detail_amt");
		var amt = parseInt(sub.parents(".cartProduct").find(".prod_amt").val());
		var store_id = sub.parents(".cart");
		
		if(parseInt(detail_amt.val()) != 1){
			if(amt < parseInt(detail_amt.val())){
				detail_amt.val(amt);
			}
			else if(parseInt(detail_amt.val()) > 1){
				detail_amt.val(parseInt(detail_amt.val()) - 1);
			}
			else if(parseInt(detail_amt.val()) < 1){
				detail_amt.val(1);
			}
			
			changeSubtotal(detail_amt);
			changeOrdtotal(store_id);
		}
		else if(parseInt(detail_amt.val()) == 1){

			var prod_id = sub.parents(".cartProduct");
			remove(store_id, prod_id);
		}

	});
	
	$(".add").click(function(){

		var add = $(this);
		var detail_amt = add.parents(".cartProduct").find(".detail_amt");
		var amt = parseInt(add.parents(".cartProduct").find(".prod_amt").val());
		var store_id = add.parents(".cart");
		
		if(amt < parseInt(detail_amt.val())){
			detail_amt.val(amt);
		}
		else if(parseInt(detail_amt.val()) < 1){
			detail_amt.val(1);
		}
		else if(amt > parseInt(detail_amt.val())){
			detail_amt.val(parseInt(detail_amt.val()) + 1);
		}

		changeSubtotal(detail_amt);
		changeOrdtotal(store_id);
	});

	$(".detail_amt").change(function(){
		
		var detail_amt = $(this);
		var store_id = detail_amt.parents(".cart");
		
		if(isNaN(detail_amt.val())){
			detail_amt.val("1");
			
			changeSubtotal(detail_amt);
			changeOrdtotal(store_id);
		}
		else if(parseInt(detail_amt.val()) == 0){
			var store_id = detail_amt.parents(".cart");
			var prod_id = detail_amt.parents(".cartProduct");
			
			remove(store_id, prod_id);
		}
		else{
			changeSubtotal(detail_amt);
			changeOrdtotal(store_id);
		}

	});
	
	var store = $(".cart");
	for(var i = 0; i < store.length; i++){
		changeOrdtotal(store.eq(i));
	}

})