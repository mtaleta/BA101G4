$('.form_datetime').datetimepicker({
		language:  'fr',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		forceParse : 0,
		showMeridian : 1
	});
	$('.form_date').datetimepicker({
		language : 'fr',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		forceParse : 0
	});
	$('.form_time').datetimepicker({
		language : 'fr',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 1,
		minView : 0,
		maxView : 1,
		forceParse : 0
	});
	$(".form_datetime").datetimepicker({
		format : "dd MM yyyy - hh:ii"
	});
	$('#datetimepicker').datetimepicker('setStartDate', '2012-01-01');
