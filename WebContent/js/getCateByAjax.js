$(document).ready(function(){
	
	var path = window.location.pathname;
	var context = path.substring(0, path.indexOf('/', 1));
	
	var cate_id = $("#cate_id");
	var prod_amt = $("#prod_amt");

	$(".prod_category").click(function(){
		
		var prod_category = $(this);
		
		if(prod_category.val() == 1){
			prod_amt.show();
		}
		else{
			prod_amt.hide();
		}

		$.ajax({
			type:"POST",
			url:context+"/frontend/category/category.do",
			data:{action:"getCateByAjax",
				  prod_category:prod_category.val()
			},
			dataType:"json",
			success:function (data){
				var i = 0;
				cate_id.empty();
				$.each(data,function(){
					cate_id.append("<option value='"+data[i].cate_id+"'>"+data[i].cate_name+"</option>");
					i++;
				});
				
			},
			error:function(){alert("error");}
		})
	});
	
	

})