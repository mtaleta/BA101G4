function doFirst(){
	document.getElementById('dropZone').ondragover = dragOver;
	document.getElementById('dropZone').ondrop = dropped;
}
function dragOver(e){
	e.preventDefault();
}
function dropped(e){
	e.preventDefault();

	var file = e.dataTransfer.files[0];

	var readFile = new FileReader();
	readFile.readAsDataURL(file);
	readFile.addEventListener('load',function(){
		var image = document.getElementById('image');
		image.src = this.result;
		
		var prod_img = document.getElementById('prod_img');
		prod_img.value = this.result;
	},false);
}
window.addEventListener('load',doFirst,false);