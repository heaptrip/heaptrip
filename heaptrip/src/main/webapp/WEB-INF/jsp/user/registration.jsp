<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="domain_url"
	value="${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />

<fmt:bundle basename="socnet">
	<!-- VKontakte properties -->
	<fmt:message key="socnet.vk.client_id" var="vk_client_id" />
	<fmt:message key="socnet.vk.scope" var="vk_scope" />
	<fmt:message key="socnet.vk.authorize_url" var="vk_authorize_url" />
	<!-- FaceBook properties -->
	<fmt:message key="socnet.fb.client_id" var="fb_client_id" />
	<fmt:message key="socnet.fb.scope" var="fb_scope" />
	<fmt:message key="socnet.fb.authorize_url" var="fb_authorize_url" />
</fmt:bundle>

<c:url var="vkUrl" value="${vk_authorize_url}">
	<c:param name="client_id" value="${vk_client_id}" />
	<c:param name="scope" value="${vk_scope}" />
	<c:param name="redirect_uri" value="http://${domain_url}/rest/registration/socnet/vk" />
	<c:param name="display" value="page" />
	<c:param name="response_type" value="code" />
</c:url>

<c:url var="fbUrl" value="${fb_authorize_url}">
	<c:param name="client_id" value="${fb_client_id}" />
	<c:param name="redirect_uri" value="http://${domain_url}/rest/registration/socnet/fb" />
	<c:param name="display" value="page" />
	<c:param name="response_type" value="code" />
</c:url>


<script type="text/javascript">
	var onRegistrationSubmit = function() {

		var url = 'rest/registration';

		// registrationInfo
		var jsonData = {
			email : $("#email").val(),
			password : $("#password").val(),
			firstName : $("#firstName").val(),
			secondName : $("#secondName").val(),
			socNetName : $("#socNetName").val(),
			socNetUserUID : $("#socNetUserUID").val(),
			photoUrl : $("#photoUrl").val()
		};

		var callbackSuccess = function(data) {
			var domain =  $("#email").val().replace(/.*@/, ""); 
			window.location = 'confirmation.html?domain=' + domain;
		};

		var callbackError = function(error) {
			$("#error_message #msg").text(error);
		};

		$.postJSON(url, jsonData, callbackSuccess, callbackError);

	};
</script>

<nav id="nav">
	<ul>
		<li><a href="<c:url value="/login.html"/>"><fmt:message key="user.action.login" /></a></li>
		<li><a href="<c:url value="/registration.html"/>" class="active"><fmt:message key="user.action.registration" /></a></li>
	</ul>
</nav>


<section id="middle">
	<div id="container">
		<div id="contents">
	
	<div id="error_message">
		<span id="msg"></span>
	</div>

			

			<div id="registration">

				<form:form name="reg" modelAttribute="registrationInfo" action="" method="post">

					<form:hidden path="socNetName" id="socNetName" />
					<form:hidden path="socNetUserUID" id="socNetUserUID" />
					<form:hidden path="photoUrl" id="photoUrl" />


					<dl>
						<dt>
							<label><fmt:message key="user.firstName" /></label>
						</dt>
						<dd>
							<form:input type="text" path="firstName" id="firstName" placeholder="" />
						</dd>
					</dl>
					<dl>
						<dt>
							<label><fmt:message key="user.secondName" /></label>
						</dt>
						<dd>
							<form:input type="text" path="secondName" id="secondName" placeholder="" />
						</dd>
					</dl>
					<c:if test="${ not empty registrationInfo.socNetName}">
						<div>
							<fmt:message key="user.action.enterSocEmail" />
						</div>
					</c:if>
					<dl>
						<dt>
							<label><fmt:message key="user.login" /></label>
						</dt>
						<dd>

							<form:input type="text" path="email" id="email" placeholder="" />
						</dd>
					</dl>

					<c:if test="${ empty registrationInfo.socNetName}">

						<dl>
							<dt>
								<label><fmt:message key="user.password" /></label>
							</dt>
							<dd>
								<input type="password" id="password">
							</dd>
						</dl>
						<dl>
							<dt>
								<label><fmt:message key="user.action.pswRepeat" /></label>
							</dt>
							<dd>
								<input type="password" id="repeatPassword">
							</dd>
						</dl>

						<dl id="capcha">
							<dt>
								<label><fmt:message key="user.action.enterCode" /></label>
							</dt>
							<dd>
								<input type="text" name="capcha" value=""><img src="<c:url value="/images/capcha.jpg"/>" />
							</dd>
						</dl>


					</c:if>

					<dl id="soglashenie">
						<dt>
							<label><input type="checkbox" id="soglashenie"> <fmt:message key="user.action.acceptTerms" /><a
								href="/"><br /> <fmt:message key="user.action.agreement" /> </a></label>
						</dt>

			
						
						<dd><a id="go" onClick="onRegistrationSubmit()"><fmt:message key="user.action.registration" /></a></dd>
						
						<!-- <dd><input onClick="onRegistrationCansel()" type="submit" id="go" name="go" value="<fmt:message key="user.action.registration" />"></dd> -->
					</dl>
				</form:form>

				<c:if test="${ empty registrationInfo.socNetName}">
					<div id="reg_soc">
						<span><fmt:message key="user.action.registration" /><br /> <fmt:message key="user.action.throughSocNet" />:</span>
						<a href="<c:out value="${fbUrl}"/>" class="fb"> </a> <a href="" class="od"> </a> <a href="" class="tv"></a> <a
							href="<c:out value="${vkUrl}"/>" class="vk"></a>
					</div>
				</c:if>
			</div>
		</div>
		<!-- #content-->
	</div>
	<!-- #container-->
</section>
<!-- #middle-->


<!--

	<h1>
		<fmt:message key="registration.title" />
	</h1>

	<form:form method="POST" modelAttribute="registrationInfo" action="registration.html">

		


		

		<fieldset>
			<p>
				<img src="${registrationInfo.photoUrl}">
			</p>
			<table>
				<tr>
					<td><label for=firstName><fmt:message key="registration.firstName" />:</label></td>
					<td><span><form:input path="firstName" /></span></td>
				</tr>
				<tr>
					<td><label for=secondName><fmt:message key="registration.secondName" />:</label></td>
					<td><span><form:input path="secondName" /></span></td>
				</tr>
			</table>

		</fieldset>

		<table>
			<tr> -->
<!-- <td><input type="button" name="registration_aplay" value=<fmt:message key="bottom.submit" /> /></td> -->
<!--<td><input type="button" onClick="onRegistrationSubmit()" value=<fmt:message key="bottom.submit" /> /></td>
<td><input type="button" onClick="onRegistrationCansel()" value=<fmt:message key="bottom.cancel" /> /></td>
</tr>
</table>

</form:form>

</div>
-->
