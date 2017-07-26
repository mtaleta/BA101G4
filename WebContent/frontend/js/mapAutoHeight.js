$(document).ready(function () {

	var map_top = $("#myMap").offset().top;
	var ready_win_height = $(window).height();

	$("#myMap").css("height", (ready_win_height - map_top));

	$(window).resize(function () {

		var resize_win_height = $(window).height();
		$("#myMap").css("height", (resize_win_height - map_top));
	});
});