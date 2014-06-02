<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type='text/javascript' src='<c:url value="/js/util.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/lib/jquery-fileupload/vendor/jquery.ui.widget.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/lib/jquery-fileupload/jquery.iframe-transport.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/lib/jquery-fileupload/jquery.fileupload.js"/>'></script>

<script type='text/javascript'>
	$(function() {
		<c:forEach var="image" items="${post.images}">
		var image = {
			id : '${image.id}',
			name : '${image.name}',
			size : '${image.size}'
		};
		addRow(image);
		</c:forEach>
	});

	function addRow(file) {
		$('#dataTable > tbody:last').append('<tr id="' + file.id + '"><td>' + formatFileDisplay(file) + '</td></tr>');
	}

	function formatFileDisplay(file) {
		var fileUrl = '${pageContext.request.contextPath}/image?imageId=' + file.id;
		var link = '<img src="' + fileUrl + '"/><br/>';

		var size = '<span style="font-style:italic">' + (file.size / 1000).toFixed(2) + 'K</span>';

		return link + file.name + ' (' + size + ')';
	}
</script>

<h1>Post read</h1>

<div id="postEdit">
	<div>
		<TABLE id="dataTable" border="0">
			<TR>
				<TD></TD>
			</TR>
		</TABLE>
	</div>

	<h3>Name</h3>
	<input type="text" name="name" value="${post.name}" />

	<h3>Description</h3>
	${post.description}

</div>