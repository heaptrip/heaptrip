

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div>



	<script>
	
	var onRegistrationSubmit = function(){
		
		var url = 'rest/registration';
		
		// registrationInfo
		var jsonData = {
				socNetName:'',
				socNetUserUID:''
		};
		
		var callbackSuccess = function(data){
			
			console.log(data);
			
			alert(data);
		};
		
		var callbackError = function(error){
			alert(error);
		};
		
		var callbackFinally = function(){
			
		};
		
		$.postJSON(url, jsonData, callbackSuccess, callbackError, callbackFinally);
		
	};
	
	var onRegistrationCansel = function(){
		window.location='tidings.html';
	};
	
	</script>


	<h1>
		<fmt:message key="registration.title" />
	</h1>

	<form:form method="POST" modelAttribute="registrationInfo" action="registration.html">

		<form:hidden path="socNetName" />
		<form:hidden path="socNetUserUID" />
		<form:hidden path="photoUrl" />


		<c:if test="${not empty error}">
			<div>
				${error}
			</div>
			<br />
		</c:if>

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
			<tr>
				<!-- <td><input type="button" name="registration_aplay" value=<fmt:message key="bottom.submit" /> /></td> -->
				<td><input type="button" onClick="onRegistrationSubmit()" value=<fmt:message key="bottom.submit" /> /></td>
				<td><input type="button" onClick="onRegistrationCansel()" value=<fmt:message key="bottom.cancel" /> /></td>
			</tr>
		</table>

	</form:form>

</div>