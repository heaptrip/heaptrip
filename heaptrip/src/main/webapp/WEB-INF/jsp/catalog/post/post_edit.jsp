<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url value="/post/upload/header" var="fileUploadUrl" />

<c:url value="/posts.html" var="postsUrl" />

<c:url value="/post/edit.html" var="saveUrl" />

<script type='text/javascript' src='<c:url value="/resources/js/util.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/lib/jquery-fileupload/vendor/jquery.ui.widget.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/lib/jquery-fileupload/jquery.iframe-transport.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/lib/jquery-fileupload/jquery.fileupload.js"/>'></script>

<script type='text/javascript'>
	$(function() {
		init();
	});

	function init() {
		
		<c:forEach var="image" items="${post.images}">
			var image = {
					id : '${image.id}',
					name : '${image.name}',
					size : '${image.size}'
			};	
			addRow(image);
		</c:forEach>
	
		var editor = CKEDITOR
		.replace(
				'textDescription',
				{
					filebrowserUploadUrl : '${pageContext.request.contextPath}/post/upload?id=${post.id}',
					height : '800',
					width : '100%'
				});

		CKEDITOR.dialog.add('image', function(editor) {
			return {
				title : 'Upload image',
				resizable : CKEDITOR.DIALOG_RESIZE_BOTH,
				minWidth : 500,
				minHeight : 150,
				contents : [ {
					id : 'Upload',
					hidden : true,
					filebrowser : 'uploadButton',
					label : editor.lang.image.upload,
					elements : [ {
						type : 'file',
						id : 'upload',
						label : editor.lang.image.btnUpload,
						style : 'height:40px',
						size : 38
					}, {
						type : 'fileButton',
						id : 'uploadButton',
						filebrowser : 'info:txtUrl',
						label : editor.lang.image.btnUpload,
						'for' : [ 'Upload', 'upload' ]
					} ]
				}, ],
			};
		});
		
		 $("#btnSave").click(function(e) {
			 $.postJSON('${saveUrl}', {
				id : '${post.id}',
				name : $("#txtName").val(),
				//dateCreate : '${post.dateCreate}',
				description : editor.getData(),
				'images' : $('body').data('filelist')
				}, function(result) {
					if (result == "OK") {
						dialog('Success', 'Files have been uploaded!');
					} else {
						dialog('Failure', 'Unable to upload files!');
					}
				});
			 	$(location).attr('href', '${postsUrl}');
		     });
		
		$('#reset').click(function() {
			clearForm();
			dialog('Success', 'Fields have been cleared!');
		});

		$('#upload').fileupload({
			dataType : 'json',
			done : function(e, data) {
				$.each(data.result, function(index, file) {
					$('body').data('filelist').push(file);
					addRow(file);
					//$('#filename').append(formatFileDisplay(file));
					$('#attach').empty().append('Add another file');
				});
			}
		});
		
		// Technique borrowed from http://stackoverflow.com/questions/1944267/how-to-change-the-button-text-of-input-type-file
		// http://stackoverflow.com/questions/210643/in-javascript-can-i-make-a-click-event-fire-programmatically-for-a-file-input
		$("#attach").click(function() {
			$("#upload").trigger('click');
		});

		$('body').data('filelist', new Array());
	}

	function formatFileDisplay(file) {
		
		var size = '<span style="font-style:italic">'
				+ (file.size / 1000).toFixed(2) + 'K</span>';
		
		var fileUrl = '${pageContext.request.contextPath}/image.html?imageId=' + file.id;
		var link = '<img src="' + fileUrl + '"/ ttt>';
		var remove = '<a id="a' + file.id + '"  href="<c:url value="/post/remove.html?id=${post.id}"/>">remove</a>';
		
		$('#a' + file.id).live("click", function () {
			var files = $('body').data('filelist');
			$.each(files, function(i){
			    if(files[i].id === file.id) files.splice(i,1);
			});
			$('body').data('filelist',files);
			$("#"+file.id).remove();
		    return false;
		});
				
		return file.name + ' (' + size + ')' + link + remove;
	}

	function getFilelist() {
		var files = $('body').data('filelist');
		var filenames = '';
		for ( var i = 0; i < files.length; i < i++) {
			var suffix = (i == files.length - 1) ? '' : ',';
			filenames += files[i].name + suffix;
		}
		return filenames;
	}

	function dialog(title, text) {
		$('#msgbox').text(text);
		$('#msgbox').dialog({
			title : title,
			modal : true,
			buttons : {
				"Ok" : function() {
					$(this).dialog("close");
				}
			}
		});
	}

	function clearForm() {
		$('#filename').empty();
		$('#attach').empty().append('Add a file');
		$('body').data('filelist', new Array());
	}
	
	function addRow(file) {
		$('#dataTable > tbody:last').append('<tr id="' + file.id + '"><td>' + formatFileDisplay(file) + '</td></tr>');
    }
</script>

<h1>Post edit</h1>

<div id="postEdit">

	<button id="btnSave">Save</button>

	<h3>Header</h3>
	<div>
		<form id='uploadForm'>
			<fieldset>
				<legend>Files</legend>
				 
				<div>
					<TABLE id="dataTable" width="350px" border="0">
				        <TR>
				            <TD></TD>
						</TR>
				    </TABLE>
				</div>
				<br /> 
				
				<a href='#' id='attach'>Add a file</a><br />
				<input id="upload" type="file" name="file" data-url="${fileUploadUrl}" multiple style="opacity: 0; filter: alpha(opacity :     0);"><br />
				<input type='button' value='Reset' id='reset' />
			</fieldset>
		</form>
	</div>
	
	<div id='msgbox' title='' style='display: none'></div>
	
	<h3>Name</h3>
	<input id="txtName" type="text" name="name" value="${post.name}" />

	<h3>Description</h3>
	<form:textarea id="textDescription" path="post.description" cols="60" rows="8" />

</div>

