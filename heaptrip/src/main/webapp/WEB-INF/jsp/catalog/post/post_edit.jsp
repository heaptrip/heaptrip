<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
	$(function() {

		CKEDITOR.replace('description', {
			filebrowserBrowseUrl : '${pageContext.request.contextPath}/post/browse',
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
	});
</script>
<h1>Post edit</h1>

<div id="postEdit">
	<!-- 
	<h1>Please upload a file</h1>
	<form method="post" action="${pageContext.request.contextPath}/post/upload" enctype="multipart/form-data">
		<input type="file" name="file" /> <input type="submit" />
	</form>
	 -->

	<form:form id="postUpdateForm" modelAttribute="post" method="post">
		<button>Save</button>

		<h3>Name</h3>
		<input type="text" name="name" value="${post.name}" />

		<h3>Description</h3>
		<form:textarea path="description" cols="60" rows="8" id="description" />

	</form:form>

</div>

