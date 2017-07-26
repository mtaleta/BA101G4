$(document).ready(function(){

	var path = window.location.pathname;
	var context = path.substring(0, path.indexOf('/', 1));

	$("#submitBtn").click(function(){
		
		var ord_total = parseInt($("#ord_total").text());
		var ord_add = $("#ord_add").val();
		
		if($.trim(ord_add) == ""){
			swal("錯誤", "請輸入收件地址!!!", "error");
		}
		else{
			$.ajax({
				type:"POST",
				url:context+"/frontend/orderlist/orderlist.do",
				data:{action:"checkPointsByAjax",
					  ord_total:ord_total
				},
				dataType:"json",
				success:function(data){
					if(data[0].type == "success"){
						$("#form").submit();
					}
					else{
						swal({
							title: "餘額不足",
							text: "餘額不足，尚差" + data[0].points + "元，請儲值!!!",
							type: "warning",
							showCancelButton: true,
							confirmButtonColor: "#DD6B55",
							confirmButtonText: "前往儲值",
							cancelButtonText: "取消",
							closeOnConfirm: false
						},
						function(){
							$("#form").submit();
						});
					}
				},
				error:function(){alert("error");}
	        })
		}

	});
})