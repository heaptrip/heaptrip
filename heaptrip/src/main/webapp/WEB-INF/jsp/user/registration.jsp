<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div>

	<h1>Registration</h1>

	<form:form modelAttribute="registrationInfo">


		<fieldset>
			<div>
				<label for=firstName><fmt:message key="registration.form.firstName" />:</label> <span><form:input
						path="firstName" /></span>
			</div>
			<div>
				<label for=secondName><fmt:message key="registration.form.secondName" />:</label> <span><form:input
						path="secondName" /></span>
			</div>
		</fieldset>
	</form:form>

</div>