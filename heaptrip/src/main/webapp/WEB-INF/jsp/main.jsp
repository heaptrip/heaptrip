<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>

    <meta name="keywords" content=""/>
    <meta name="description" content=""/>

    <link rel="shortcut icon" href="<c:url value="/images/ht.png"/>" type="image/x-icon">

    <!-- title  -->
    <title><fmt:message key="site.title"/></title>
    <!-- meta -->
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <!-- css -->
    <tiles:insertAttribute name="styles_ins"/>
    <!-- js -->
    <tiles:insertAttribute name="scripts_ins"/>
</head>

<body>
<div id="top"></div>
<div id="wrapper">
    <!-- #header-->
    <header id="header">
        <tiles:insertAttribute name="header"/>
    </header>
    <!-- #main_content-->
    <tiles:insertAttribute name="main_content"/>
</div>
<!-- #footer -->
<tiles:insertAttribute name="footer"/>

</body>
</html>
