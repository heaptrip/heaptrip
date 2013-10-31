<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<tiles:useAttribute id="script" name="scripts" classname="java.util.List" />
<c:forEach var="script" items="${script}">
	<script type="text/javascript" src="<c:url value="${script}" />"></script>
</c:forEach>

<c:set var="curr_locale"><fmt:message key="locale.name"/></c:set>

<script type="text/javascript" src="js/locale/locale_${curr_locale}.js"></script>


<script type="text/javascript">
	<sec:authorize ifNotGranted="ROLE_ANONYMOUS">
		window.user = {};
        window.user.id = '<sec:authentication property="principal.id" />'
		window.user.name = '<sec:authentication property="principal.name" />'
	</sec:authorize>
</script>