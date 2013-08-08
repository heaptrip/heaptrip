<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<script type="text/javascript">

		var pagingFMT = {
			text: '<fmt:message key="wgt.paging.text" />',
			go: '<fmt:message key="wgt.paging.go" />',
	 	};
	 
	 	/*var pagingSET = {
			recordsperpage: 3
	 	};*/ 
 
	</script>

	<script src="<c:url value="/js/paginator.js"/>" type="text/javascript"></script>
	<link href="<c:url value="/css/paginator.css"/>" rel="stylesheet" type="text/css" />

	<div id="pagination">
		<div id="paginator"></div>
	</div>