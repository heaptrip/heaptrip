<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<c:set var="url" value="${urlBase}" />
<c:set var="params" value="${pageContext.request.queryString}" />

<c:set var="en" value="${url}?locale=en&${params}" />
<c:set var="ru" value="${url}?locale=ru&${params}" />

<a id="logo"></a>

<tiles:insertAttribute name="account" />

<div id="language">
	<div id="language_now">
		<span class='<fmt:message key="locale.name"/>'></span>
		<fmt:message key="locale.current" />
	</div>
	<ul>	
		<li><a href='<c:out value="${ru}"/>'><span class="ru"></span> <fmt:message key="locale.ru" /></a></li>
		<li><a href='<c:out value="${en}"/>'><span class="en"></span> <fmt:message key="locale.en" /></a></li>
		<li><a href="/"><span class="du"></span> <fmt:message key="locale.du" /></a></li>
		<li><a href="/"><span class="fr"></span> <fmt:message key="locale.fr" /></a></li>
		<li><a href="/"><span class="yk"></span> <fmt:message key="locale.yk" /></a></li>
		<li><a href="/"><span class="sw"></span> <fmt:message key="locale.sw" /></a></li>
	</ul>
</div>
