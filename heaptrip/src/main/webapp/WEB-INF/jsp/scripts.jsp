<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<tiles:useAttribute id="script" name="scripts" classname="java.util.List" />
<c:forEach var="script" items="${script}">
	<script type="text/javascript" src="<c:url value="${script}" />"></script>
</c:forEach>