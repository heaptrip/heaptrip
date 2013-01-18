<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>Post read</h1>

<div id="postEdit">

	<h3>Name</h3>
	<input type="text" name="name" value="${post.name}" />

	<h3>Description</h3>
	${post.description}

</div>