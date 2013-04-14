<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>










<h1>
	<fmt:message key="tiding.list.title" />
</h1>

<c:forEach var="tiding" items="${tidings}">

	<div>${tiding.name}</div>
	<br>

</c:forEach>
