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

function removeSelected(selectedTableId, selectedContainer, uuid){
	$('#'+selectedTableId+uuid).remove();
	$('#'+selectedContainer+' input[type="hidden"][name="'+selectedContainer+'"][value="'+uuid+'"]').remove();
}

function addSelects(selectedContainer, table, selectedTableId, fields){
	$('#'+table+' tbody input').each(function(){
	    if($(this).is(":checked")){
	    	var jsonObj = JSON.parse($(this).val());
	    	if($('#'+selectedTableId).find('tr#'+selectedTableId+jsonObj.uuid).length == 0){
	    		addSelectTable(selectedContainer, table, selectedTableId, fields, jsonObj);
	    	}
	    	$(this).iCheck('uncheck');
	    }
	});
}

function addSelect(selectedContainer, table, selectedTableId, fields){
	$('#'+table+' tbody input').each(function(){
	    if($(this).is(":checked")){
	    	var jsonObj = JSON.parse($(this).val());
	    	if($('#'+selectedTableId).find('tr#'+selectedTableId+jsonObj.uuid).length == 0){
	    		$('#'+selectedTableId+' > tbody').html("");
	    		$('#'+selectedContainer).html("");
	    		addSelectTable(selectedContainer, table, selectedTableId, fields, jsonObj);
	    	}
	    	$(this).iCheck('uncheck');
	    }
	});
}

function addSelectTable(selectedContainer, table, selectedTableId, fields, jsonObj){
	$('#'+selectedContainer).append('<input type="hidden" name="'+selectedContainer+'" value="'+jsonObj.uuid+'"/>');
	
	if(fields == null){
		$('#'+selectedTableId).append('<tr id="'+selectedTableId+jsonObj.uuid+'"><td><span>'+jsonObj.id+'</span></td><td><span>'+jsonObj.name+'</span></td><td><button type="button" class="btn btn-default btn-sm" onclick="removeSelected(\''+selectedTableId+'\', \''+selectedContainer+'\', \''+jsonObj.uuid+'\');"><i class="fa fa-minus"></i></button></td></tr>');
	}
	else{
		$('#'+selectedTableId).append('<tr id="'+selectedTableId+jsonObj.uuid+'"></tr>');
		$.each(fields , function(index, val) { 
			if(jsonObj[val] == null){
	    		$('#'+selectedTableId+' #'+selectedTableId+jsonObj.uuid).append('<td></td>');
			}
			else{
	    		$('#'+selectedTableId+' #'+selectedTableId+jsonObj.uuid).append('<td><span>'+jsonObj[val]+'</span></td>');
			}
		});
		$('#'+selectedTableId+' #'+selectedTableId+jsonObj.uuid).append('<td><button type="button" class="btn btn-default btn-sm" onclick="removeSelected(\''+selectedTableId+'\', \''+selectedContainer+'\', \''+jsonObj.uuid+'\');"><i class="fa fa-minus"></i></button></td>');
		
	}
}