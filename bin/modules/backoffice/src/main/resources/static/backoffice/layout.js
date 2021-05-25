$(function () {
  bsCustomFileInput.init();
});

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

    $('.datetimepicker').daterangepicker({
        singleDatePicker: true,
        timePicker: true,
        timePickerIncrement: 30,
        locale: {
            format: 'MM/DD/YYYY hh:mm A'
        }
    });

    $(function() {
        var allEditors = document.querySelectorAll('.editor');
        for (var i = 0; i < allEditors.length; ++i) {
            ClassicEditor.create(allEditors[i], {
                    ckfinder: {
                        // To avoid issues, set it to an absolute path that does not start with dots, e.g. '/ckfinder/core/php/(...)'
                        uploadUrl: '/ckfinder/connector?command=QuickUpload&type=Files&responseType=json'
                    },
                    toolbar: {
                        items: [
                            'ckfinder', '|',
                            'heading', '|',
                            'fontfamily', 'fontsize', '|',
                            'alignment', '|',
                            'fontColor', 'fontBackgroundColor', '|',
                            'bold', 'italic', 'strikethrough', 'underline', 'subscript', 'superscript', '|',
                            'link', '|',
                            'outdent', 'indent', '|',
                            'bulletedList', 'numberedList', 'todoList', '|',
                            'code', 'codeBlock', '|',
                            'insertTable', '|',
                            'uploadImage', 'blockQuote', '|',
                            'undo', 'redo'
                        ],
                        shouldNotGroupWhenFull: true
                    }

                })
                .then(function(editor) {
                    // console.log( editor );
                })
                .catch(function(error) {
                    console.error(error);
                });
        }
    });
});

function clearFormTitle(){
	$("#form-id").text('');
}