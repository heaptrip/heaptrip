<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<nav id="nav">
	<ul>
		<li><a href="<c:url value="/login.html"/>" class="active"><fmt:message key="user.action.login" /></a></li>
		<li><a href="<c:url value="/registration.html"/>"><fmt:message key="user.action.registration" /></a></li>
	</ul>
</nav>

<c:if test="${not empty param.login_error}">
	<div id="error_message">
		${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}
	</div>
</c:if>


<section id="middle">
	<div id="container">
		<div id="contents">
			<div id="authorization">
				<form name="auth" action="<c:url value="/loginProcess" />" method="post">
					<dl>
						<dt>
							<label><fmt:message key="user.login" /></label>
						</dt>
						<dd>
							<input type="text" placeholder="" name="j_username" value="${SPRING_SECURITY_LAST_USERNAME}" />
						</dd>
					</dl>
					<dl>
						<dt>
							<label><fmt:message key="user.password" /></label>
						</dt>
						<dd>
							<input type="password" name="j_password" />
						</dd>
					</dl>
					<dl id="soglashenie">
						<dt>
							<label> <input type="checkbox" name="_spring_security_remember_me" id="remember_me" /> <fmt:message
									key="user.action.rememberMe" /></label>
						</dt>
						<dd>
							<input type="submit" id="submit" name="submit" value="<fmt:message key="user.action.login"/>">
						</dd>
					</dl>
				</form>
				<div id="reg_soc">
					<span><fmt:message key="user.action.socnetLogin" />:</span> <a href="/" class="fb"></a> <a
						href="" class="od"></a> <a href="" class="tv"></a> <a href="/" class="vk"></a>
				</div>
				<div id="link">
					<a href="<c:url value="/registration.html"/>" id="reg_link"><fmt:message key="user.action.registration" /></a> <a
						href="/" id="forgot_password"><fmt:message key="user.action.pswRecover" /></a>
				</div>
			</div>
		</div>
		<!-- #content-->
	</div>
	<!-- #container-->
</section>
<!-- #middle-->













































<!-- 


<div>

</div>


<div>
	<table>
		<tr>
			<td><fmt:message key="registration.title" /></td>
			<td><a href='<c:out value="${vkUrl}"/>'><fmt:message key="socnet.vk.name" /></a></td>
			<td><a href='<c:out value="${fbUrl}"/>'><fmt:message key="socnet.fb.name" /></a></td>
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
</div>-->
