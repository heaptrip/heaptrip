<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


	<nav id="nav"><fmt:message key="err.page.notfound" /></nav>

	<section id="middle">
		<div id="container">
			<div id="contents">
				<img src="<c:url value="/images/404.jpg"/>"><br />  
				<a href="<c:url value="/"/>"><fmt:message key="page.action.home" /></a><br /> 
				<a href="/"><fmt:message key="page.action.support" /></a><br />
			</div><!-- #content-->
		</div><!-- #container-->
	</section><!-- #middle-->