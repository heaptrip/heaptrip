<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
	$(function() {

		var CKEDITOR = window.parent.CKEDITOR;

		// Helper function to get parameters from the query string.
		function getUrlParam(paramName) {
			var reParam = new RegExp('(?:[\?&]|&)' + paramName + '=([^&]+)', 'i');
			var match = window.location.search.match(reParam);

			return (match && match.length > 1) ? match[1] : null;
		}

		var ckeInstance = getUrlParam('CKEditor');
		//var funcNum = getUrlParam('CKEditorFuncNum');
		var imageId = getUrlParam('imageId');
		var fileUrl = '${pageContext.request.contextPath}/image.html?imageId=' + imageId;

		//CKEDITOR.tools.callFunction(funcNum, fileUrl);
		CKEDITOR.instances[ckeInstance].insertHtml('<img src="' + fileUrl + '"/>');
		CKEDITOR.dialog.getCurrent().hide();
	});
</script>

<h1>Hello</h1>