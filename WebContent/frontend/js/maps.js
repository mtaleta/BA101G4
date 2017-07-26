function doFirst(){

	var myMap = document.getElementById("myMap");
	var myPosition = new google.maps.LatLng(24.9708264,121.18820769999999);
	
	var map = new google.maps.Map(myMap,{
		zoom: 14,
		center: myPosition,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	})
	
	var myMarker = new google.maps.Marker({
		position: myPosition,
		map: map
	})
}

window.addEventListener('load',doFirst,false);