<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


	<c:set var="url" value="${urlBase}" />
	<c:set var="params" value="${pageContext.request.queryString}" />

	<c:set var="en" value="${url}?locale=en&${params}" />
	<c:set var="ru" value="${url}?locale=ru&${params}" />


	<a id="logo"></a>
	<div id="language">
		<div id="language_now"><span class='<fmt:message key="locale.name"/>'></span><fmt:message key="locale.current" /></div>
			<ul>
				<li><a href='<c:out value="${ru}"/>'><span class="ru"></span><fmt:message key="locale.ru" /></a></li>
				<li><a href='<c:out value="${en}"/>'><span class="en"></span><fmt:message key="locale.en" /></a></li>
				<li><a href="/"><span class="du"></span><fmt:message key="locale.du" /></a></li>
				<li><a href="/"><span class="fr"></span><fmt:message key="locale.fr" /></a></li>
				<li><a href="/"><span class="yk"></span><fmt:message key="locale.yk" /></a></li>
				<li><a href="/"><span class="sw"></span><fmt:message key="locale.sw" /></a></li>
			</ul>
	</div>		

	
<!-- <div align="right">

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


 -->