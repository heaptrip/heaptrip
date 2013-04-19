<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<tiles:useAttribute id="style" name="styles" classname="java.util.List" />
<c:forEach var="style" items="${style}">
	<link rel="stylesheet" type="text/css" href="<c:url value="${style}"/>" media="screen, projection"/>
</c:forEach>