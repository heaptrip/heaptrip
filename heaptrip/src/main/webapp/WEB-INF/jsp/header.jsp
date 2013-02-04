<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div align="right">
	<div>
		<fmt:message key="locale.language" />

		<c:set var="page_url" value="${urlBase}" />

		<a href='<c:out value="${page_url}?locale=en&${pageContext.request.queryString}"/>'> <fmt:message
				key="locale.english" />
		</a>
		<a href='<c:out value="${page_url}?locale=ru&${pageContext.request.queryString}"/>'> <fmt:message
				key="locale.russian" />
		</a>
	</div>

	<br />

	<div>
		<sec:authorize ifNotGranted="ROLE_ANONYMOUS">
			<div align="right">
				<sec:authentication property="principal.firstName" />
				<sec:authentication property="principal.secondName" />
				<a href="<c:url value="/logout"/>"><fmt:message key="logout.button" /></a>
			</div>
		</sec:authorize>
		<sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
			<div align="right">
				<a href="<c:url value="/login.html"/>"><fmt:message key="login.button" /></a>
			</div>
		</sec:authorize>
	</div>

</div>

<div>

	<h1>
		<fmt:message key="header.name" />
	</h1>

</div>