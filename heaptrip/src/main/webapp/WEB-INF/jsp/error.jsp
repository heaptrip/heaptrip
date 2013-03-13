<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div>

	<h1>
		<fmt:message key="error.title" />
	</h1>
	
	<p>
		<fmt:message key="error.message" /> <div id=message>"${message}"</div>
	</p>

</div>