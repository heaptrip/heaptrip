<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>




<div id="account">
	<sec:authorize ifNotGranted="ROLE_ANONYMOUS">
		<div id="account_name">
			<sec:authentication property="principal.name" />

		</div>
		<ul>
			<li><a href="<c:url value="/tidings.html"/>"><fmt:message key="tiding.list.title" /></a></li>
			<li><a href="/"><fmt:message key="profile.title" /></a></li>
			<li><a href="/"><fmt:message key="profile.my" /></a></li>
			<li><a href="/"><fmt:message key="profile.favorit" /></a></li>
			<li><a href="<c:url value="/logout"/>"><fmt:message key="user.action.logout" /></a></li>
		</ul>
	</sec:authorize>
	<sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
		<div id="account_name" class="login">
			<a href="<c:url value="/login.html"/>"><fmt:message key="user.action.login" /></a>
		</div>
	</sec:authorize>

</div>


