$(document).ready(function() {

	var spin = document.getElementById("form-save-button-spin");
	if (spin !== null) {
		if (spin.style.display === "none") {
			spin.style.display = "block";
		} else {
			spin.style.display = "none";
		}
	}

	var btn = document.getElementById("form-save-button");
	if (spin !== null) {
		if (btn.style.display === "none") {
			btn.style.display = "block";
		} else {
			btn.style.display = "none";
		}
	}
});