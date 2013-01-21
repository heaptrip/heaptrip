<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>
	<fmt:message key="login.title" />
</h1>

<div>
	<c:if test="${not empty param.login_error}">
		<div>
			<fmt:message key="err.login.failure" />
		</div>
		<br />
	</c:if>
</div>

<c:set var="domain_url"
	value="${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />

<fmt:bundle basename="socnet">
	<fmt:message key="socnet.vk.client_id" var="vk_client_id" />
	<fmt:message key="socnet.vk.scope" var="vk_scope" />
	<fmt:message key="socnet.vk.authorize_url" var="vk_authorize_url" />
</fmt:bundle>

<c:url var="vkUrl" value="${vk_authorize_url}">
	<c:param name="client_id" value="${vk_client_id}" />
	<c:param name="scope" value="${vk_scope}" />
	<c:param name="redirect_uri" value="http://${domain_url}/registration/socnet/vk.html" />
	<c:param name="display" value="page" />
	<c:param name="response_type" value="code" />
</c:url>

<!--<c:out value="${vkUrl}" />-->

<div>
	<table>
		<tr>
			<td><fmt:message key="registration.title" /></td>
			<td><a href='<c:out value="${vkUrl}"/>'><fmt:message key="socnet.vk.name" /></a></td>
		</tr>
	</table>
</div>

<div class="section">
	<form name="f" action="<c:url value="/loginProcess" />" method="post">
		<fieldset>
			<div class="field">
				<div class="label">
					<label for="j_username"><fmt:message key="login.username" />:</label>
				</div>
				<div class="output">
					<input type="text" name="j_username" id="j_username" value="${SPRING_SECURITY_LAST_USERNAME}" />
				</div>
			</div>
			<div class="field">
				<div class="label">
					<label for="j_password"><fmt:message key="login.password" />:</label>
				</div>
				<div class="output">
					<input type="password" name="j_password" id="j_password" />
				</div>
			</div>
			<div class="field">
				<div class="label">
					<label for="remember_me"><fmt:message key="login.rememberMe" />:</label>
				</div>
				<div class="output">
					<input type="checkbox" name="_spring_security_remember_me" id="remember_me" />
				</div>
			</div>
			<div class="form-buttons">
				<div class="button">
					<input name="submit" id="submit" type="submit" value="<fmt:message key="login.button"/>" />
				</div>
			</div>
		</fieldset>
	</form>
</div>
