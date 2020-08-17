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

function removeSelected(selectedContainer, uuid){
	$('#row'+uuid).remove();
	$('#'+selectedContainer+' input[type="hidden"][name="'+selectedContainer+'"][value="'+uuid+'"]').remove();
}

function addSelect(selectedContainer, table, selectedTable){
	$('#'+table+' tbody input').each(function(){
	    if($(this).is(":checked")){
	    	var jsonObj = JSON.parse($(this).val());
	    	if($('#'+selectedTable).find('tr#row'+jsonObj.uuid).length == 0){
		    	$('#'+selectedContainer).append('<input type="hidden" name="'+selectedContainer+'" value="'+jsonObj.uuid+'"/>');
		    	$('#'+selectedTable).append('<tr id="row'+jsonObj.uuid+'"><td><span>'+jsonObj.id+'</span></td><td><span>'+jsonObj.name+'</span></td><td><button class="btn btn-default btn-xs" onclick="removeSelected("selectedRegions", "'+jsonObj.uuid+'");"><i class="fa fa-minus color"></i></button></td></tr>');
	    	}
	    	$(this).iCheck('uncheck');
	    }
	});
}