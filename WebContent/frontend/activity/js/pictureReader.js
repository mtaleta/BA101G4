
	var file;
	var fileReader;

	function file_change() {
		//file = document.getElementById('upfile1').files[0];
		file = document.getElementsByName("prod_img");

		//if (file) {
		if (file[0]) {
			fileReader = new FileReader();
			fileReader.onload = openfile;
			//fileReader.readAsDataURL(file);
			fileReader.readAsDataURL(file[0].files[0]);
		}
	}
	function openfile(event) {
		document.getElementById('image').src = event.target.result;
		result;
	}