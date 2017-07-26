$(document).ready(function(){
	
	var path = window.location.pathname;
	var context = path.substring(0, path.indexOf('/', 1));
	
	$(".launched").click(function(){

		var prod_id = $(this);
		var launch = prod_id.parents("tr").children("td.launch");
		
		$.ajax({
			type:"POST",
			url:context+"/frontend/product/product.do",
			data:{action:"launchedByAjax",
				  prod_id:prod_id.attr("id")
			},
			success:function (data){
				if(prod_id.val() == "上架商品"){
					prod_id.val("下架商品");
					launch.text("上架中");
				}
				else if(prod_id.val() == "下架商品"){
					prod_id.val("上架商品");
					launch.text("下架中");
				}

			},
			error:function(){alert("error");}
        })

	});

})