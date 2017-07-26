function sprintf(template, values) {
	return template.replace(/%s/g, function() {
		return values.shift();
	});
}

function fixDiv() {
	var $cache = $('#cartList');
	if ($(window).scrollTop() > 100)
		$cache.css({
			'position': 'fixed',
			'top': '10px',
		});
	else
		$cache.css({
			'position': 'relative',
			'top': 'auto',
		});
}

function adjustBtnWithId(id, name, inputValue) {
	return "<span class='input-group-btn'>" +
				"<button class='btn btn-success btn-sm minus' type='button'> – </button>" +
			"</span>" +
			"<input type='text' class='"+ id + " form-control text-center input-sm' name='"+ name +"' value='" + inputValue + "'>" +
			"<span class='input-group-btn'>" +
				"<button class='btn btn-success btn-sm plus' type='button'> + </button>" +
			"</span>";
}

function changeSumTo(sum) {
	$('#sum').find('span').html(sum);
	$('#sum').find('input').attr('value',sum);
	$('#sum input').on("change",detectSum).triggerHandler('change');
	$('#sum input').off("change");
}

function compareSumWith(condi) {
	condi?$('#checkOut').prop('disabled',false):$('#checkOut').prop('disabled',true);
}

$(document).ready(function() {
	// radio
	changeShoppingMode();
	$('input[type=radio]').on('click', changeShoppingMode);
    
     // right div
	$(window).scroll(fixDiv);
	fixDiv();
            
	var sum = 0;

	$(document).on("click", ".add", function() {
		var count = 1;
		var tr = $(this).closest('tr');
		var name = tr.find('.name').text().trim();          
		var price = tr.find('.price').text().trim();
		var prod_id = tr.find('td:nth-child(1)').attr('class').substr(3,12);
		console.log(prod_id);
    
		$("#myItems").append("<div class='itemList clearfix'><div>" + name + "</div>" +
							"<input type='hidden' name='"+ prod_id +"' value='"+ prod_id +"'>" +
							"<input type='hidden' name='"+ prod_id +"' value='"+ name +"'>" +
							"<div class='input-group pull-left' style='width:100px'>" + 
								adjustBtnWithId('inputA_' + prod_id, prod_id, count) +
							"</div>" +
							"<div class='input-group subtotal align-middle pull-left'>" +
							"x" + price + " = " + count*price +
							"</div><input type='hidden' name='"+ prod_id +"' value='"+ price +"'></div>");

		$(".add_" + prod_id).replaceWith("<div class='input-group countBtn' style='width:100px'>" + adjustBtnWithId('inputB_' + prod_id, prod_id,count) + "</div>");
        
		sum = sum + count*price;                   	
		$('#sum').find('span').html(sum); 
		$('#sum').find('input').attr('value',sum);
       
		$('#sum input').on("change",detectSum).triggerHandler('change');
		$('#sum input').off("change");
      
		$(".plus").unbind("click").click(function() {
			var counter = $(this).parent().prev().val();
			var fullId = $(this).parent().prev().attr('class').substr(0,19);        	  
			var prodId = fullId.substring(7);
			var p = $(".inputB_"+prodId).closest('tr').find('.price').html();        

			$(".inputA_"+prodId).attr('value',++counter);
			$(".inputB_"+prodId).attr('value',counter);
			$(".inputA_"+prodId).parent().next().html("x" + p + " = " + counter*p);
    	  
			sum = sum + parseInt(p);
			changeSumTo(sum);
    	  
		})

		$(".minus").unbind("click").click(function() {
			var counter = $(this).parent().next().val();
			var fullId = $(this).parent().next().attr('class').substr(0,19);
			var prodId = fullId.substring(7);
			var p = $(".inputB_"+prodId).closest('tr').find('.price').html();
	 	
			if(counter>1) { // after -> 1
				$(".inputA_"+prodId).attr('value',--counter);
				$(".inputB_"+prodId).attr('value',counter);
				$(".inputA_"+prodId).parent().next().html("x" + p + " = " + counter*p);      	
			} else {
				$(".inputA_"+prodId).parent().parent().remove();
				$(".inputB_"+prodId).parent().empty().replaceWith(sprintf('<div class="btn btn-success btn-sm add add_%s">ADD</div>', [prodId]));
			}
			sum = sum - parseInt(p);
			changeSumTo(sum);
	 	
		})
	})
});