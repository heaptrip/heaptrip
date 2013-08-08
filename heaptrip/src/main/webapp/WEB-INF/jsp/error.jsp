<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<nav id="nav"><fmt:message key="err.title" /></nav>

	<section id="middle">
		<div id="container">
			<div id="contents">
			
				<p>
					<div id="message">"${message}"</div>
				</p>
	
				<img src="<c:url value="/images/error.jpg"/>"><br /> 
				<a href="/"><fmt:message key="page.action.refresh" /></a><br /> 
				<a href="<c:url value="/"/>"><fmt:message key="page.action.home" /></a><br /> 
				<a href="/"><fmt:message key="page.action.support" /></a><br />
				
			</div><!-- #content-->
		</div><!-- #container-->
	</section><!-- #middle-->
	
	
