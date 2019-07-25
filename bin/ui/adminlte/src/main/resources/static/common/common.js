function icheck() {
	$('input').iCheck({
		checkboxClass : 'icheckbox_square-blue',
		radioClass : 'iradio_square-blue',
		increaseArea : '20%' // optional
	});
}

$(function () {
	$('.select2').select2();
})

function addCommas(x) {
	if(x){
	    var parts = x.toString().split(".");
	    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	    return parts.join(".");
	}
	else{
		return '-';
	}
}