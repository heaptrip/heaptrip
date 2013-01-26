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

		$('body').data('filelist', new Array());

		<c:forEach var="image" items="${post.images}">
		var image = {
			id : '${image.id}',
			name : '${image.name}',
			size : '${image.size}'
		};
		addRow(image);
		</c:forEach>

		var editor = CKEDITOR.replace('textDescription', {
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

		$("#btnSave").click(function() {
			var jsonData = {
				id : '${post.id}',
				name : $("#txtName").val(),
				description : editor.getData(),
				'images' : $('body').data('filelist')
			};

			$.postJSON('${saveUrl}', jsonData, function onSuccess(result) {
				alert("Success");
			}, function onError(errorMsg) {
				alert("Error: " + errorMsg);
			}, function onFinally() {
				$(location).attr('href', '${postsUrl}');
			});
		});

		$('#upload').fileupload({
			dataType : 'json',
			done : function(e, data) {
				$.each(data.result, function(index, file) {
					addRow(file);
				});
			}
		});

		// Technique borrowed from http://stackoverflow.com/questions/1944267/how-to-change-the-button-text-of-input-type-file
		// http://stackoverflow.com/questions/210643/in-javascript-can-i-make-a-click-event-fire-programmatically-for-a-file-input
		$("#attach").click(function() {
			$("#upload").trigger('click');
		});
	}

	function addRow(file) {
		$('body').data('filelist').push(file);
		$('#dataTable > tbody:last').append('<tr id="' + file.id + '"><td>' + formatFileDisplay(file) + '</td></tr>');
	}

	function formatFileDisplay(file) {
		var fileUrl = '${pageContext.request.contextPath}/image.html?imageId=' + file.id;
		var link = '<img src="' + fileUrl + '"/><br/>';

		var size = '<span style="font-style:italic">' + (file.size / 1000).toFixed(2) + 'K</span>';

		var removeLinkId = 'a' + file.id;
		var remove = '<a id="' + removeLinkId + '"  href="#" >remove</a>';

		$('#' + removeLinkId).live("click", function() {
			var files = $('body').data('filelist');
			$.each(files, function(i, file) {
				if (file.id === removeLinkId.substring(1)) {
					files.splice(i, 1);
					return false;
				}
			});
			$("#" + file.id).remove();
			return false;
		});

		return link + file.name + ' (' + size + ')' + remove;
	}
</script>

<h1>Post edit</h1>

<div id="postEdit">

	<button id="btnSave">Save</button>

	<div>
		<form id='uploadForm'>
			<fieldset>
				<legend>Header</legend>

				<div>
					<TABLE id="dataTable" border="0">
						<TR>
							<TD></TD>
						</TR>
					</TABLE>
				</div>
				<br /> <a href='#' id='attach'>Add a file</a><br /> <input id="upload" type="file" name="file"
					data-url="${fileUploadUrl}" multiple style="opacity: 0; filter: alpha(opacity :         0);" />
			</fieldset>
		</form>
	</div>

	<h3>Name</h3>
	<input id="txtName" type="text" name="name" value="${post.name}" />

	<h3>Description</h3>
	<form:textarea id="textDescription" path="post.description" cols="60" rows="8" />

</div>

