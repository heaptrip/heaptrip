<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div>

	<h1>
		<fmt:message key="registration.title" />
	</h1>

	<form:form modelAttribute="registrationInfo">

		<fieldset>

			<p>
				<img src="${registrationInfo.photoUrl}">
			</p>

			<p>
				<label for=firstName><fmt:message key="registration.firstName" />:</label> <span><form:input
						path="firstName" /></span>
			</p>

			<p>
				<label for=secondName><fmt:message key="registration.secondName" />:</label> <span><form:input
						path="secondName" /></span>
			</p>

		</fieldset>

	</form:form>

</div>