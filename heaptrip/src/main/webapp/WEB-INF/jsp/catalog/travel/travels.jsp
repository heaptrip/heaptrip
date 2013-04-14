<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h1>
	<fmt:message key="trip.list.title" />
</h1>

<c:forEach var="travel" items="${travels}">

	<div>${travel.name}</div>
	<br>

</c:forEach>
