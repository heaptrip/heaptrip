
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<head>

<!-- title  -->
<title><fmt:message key="site.title" /></title>

<!-- meta -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />



<!-- js -->
<tiles:insertAttribute name="scripts_ins" />

</head>

<html>

<body>
	<div id="header">
		<tiles:insertAttribute name="header" />
	</div>
	<div id="main_content">
		<tiles:insertAttribute name="main_content" />
	</div>
	<div id="footer">
		<tiles:insertAttribute name="footer" />
	</div>
</body>

</html>
