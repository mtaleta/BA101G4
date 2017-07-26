$(document).ready(function(){
	
	var path = window.location.pathname;
	var context = path.substring(0, path.indexOf('/', 1));
	
	$(".addCart").click(function(){

		var btnAddCart = $(this);
		var prod_id = btnAddCart.siblings(".prod_id").val();
		var store_id = btnAddCart.siblings(".store_id").val();
		var detail_amt = btnAddCart.parents(".product").find(".detail_amt").val();
		
		$.ajax({
			type:"POST",
			url:context+"/frontend/orderlist/orderlist.do",
			data:{action:"addCartByAjax",
				  prod_id:prod_id,
				  store_id:store_id,
				  detail_amt:detail_amt
			},
			dataType:"json",
			success:function (data){
				if(data[0].type == "success"){
					swal("成功", data[0].msg, "success");
				}
				else{
					swal("錯誤", data[0].msg, "error");
				}

			},
			error:function(){alert("error");}
        })

	});
	
	
	$(".sub").click(function(){
		
		var btnSub = $(this);
		var detail_amt = btnSub.parents(".cartAmt").find(".detail_amt");
		var prod_amt = parseInt(btnSub.parents(".cartAmt").find(".prod_amt").val());

		if(prod_amt < parseInt(detail_amt.val())){
			detail_amt.val(prod_amt);
		}
		else if(parseInt(detail_amt.val()) > 1){
			detail_amt.val(parseInt(detail_amt.val()) - 1);
		}
		else if(parseInt(detail_amt.val()) < 1){
			detail_amt.val(1);
		}
		else{
			detail_amt.val(1);
		}

	});
	
	$(".add").click(function(){
		
		var btnAdd = $(this);
		var detail_amt = btnAdd.parents(".cartAmt").find(".detail_amt");
		var prod_amt = parseInt(btnAdd.parents(".cartAmt").find(".prod_amt").val());

		if(prod_amt <= parseInt(detail_amt.val())){
			detail_amt.val(prod_amt);
		}
		else if(parseInt(detail_amt.val()) < 1){
			detail_amt.val(1);
		}
		else if(prod_amt > parseInt(detail_amt.val())){
			detail_amt.val(parseInt(detail_amt.val()) + 1);
		}
		else{
			detail_amt.val(1);
		}

	});

	
	$(".detail_amt").change(function(){
		
		var detail_amt = $(this);
		
		if(isNaN(detail_amt.val())){
			detail_amt.val("1");
		}
	});
	
	
})