$(function() {
	$('body').append('<iframe frameborder="0" id="hiddenSubmit" name="hiddenSubmit" style="width: 0;height: 0" src=""></iframe>');
});

function save() {
	var files = $('.upload_btn').multifileupload('getFiles');
	var filesJson = JSON.stringify(files);
	$("#filesJson").val(filesJson);
	$("#oprform").submit();
}