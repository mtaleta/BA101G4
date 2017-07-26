$(document).ready(function() {
	var list_top = $("#list").offset().top;
	var ready_win_height = $(window).height();

	$("#list").css("height", (ready_win_height - list_top));

	$(window).resize(function () {
		 var resize_win_height = $(window).height();
		$("#list").css("height", (resize_win_height - list_top));
	});
});