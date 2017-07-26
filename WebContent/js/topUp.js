$(document).ready(function () {

	var window_width = $(window).width();
	var card = $("#creditcard");
	var card_nos = $("#card_nos");
	var card_no = $(".card_no");
	var card_date_id = $("#card_date");
	var card_date_class = $(".card_date");
	var card_top = card.offset().top;
	var card_width = card.width();
	var height_ctrl = 1395 / 2153;
	var nos_top_ctrl = 770 / 1395;
	var date_top_ctrl = 1000 / 1395;

	var height = card_width * height_ctrl;
	card.css("height", height);
	card_nos.css("top", height * nos_top_ctrl);
	card_date_id.css("top", height * date_top_ctrl);
	
	if(window_width > 216){
		card_no.css({"height": (height / 9), "font-size": (height / 9 - 6)});
		card_date_class.css({"height": (height / 10), "font-size": (height / 10 - 8)});
	}
	else{
		card_no.css({"height": 24, "font-size": 24});
		card_date_class.css({"height": 20, "font-size": 20});
	}

	$(window).resize(function () {

		var resize_window_width = $(window).width();
		var resize_card_width = card.width();
		var resize_height = resize_card_width * height_ctrl
		card.css("height", resize_height);
		card_nos.css("top", resize_height * nos_top_ctrl);
		card_date_id.css("top", resize_height * date_top_ctrl);

		if(resize_window_width > 216){
			card_no.css({"height": resize_height / 9, "font-size": resize_height / 9 - 6});
			card_date_class.css({"height": (resize_height / 10), "font-size": (resize_height / 10 - 8)});
		}
		else{
			card_no.css({"height": 24, "font-size": 24});
			card_date_class.css({"height": 20, "font-size": 20});
		}
	});
	
	$(".card_no").keyup(function(){
		
		var this_no = $(this);

		if(this_no.val().length == 4){
			this_no.blur().nextAll(".card_no")[0].focus();
		}
	})
	
});