<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


 	<c:set var="url" value="${requestScope['javax.servlet.forward.servlet_path']}" />

	<nav id="nav">
    	<ul>
    		<li>
    			<a href="<c:url value="/tidings.html"/>" class='${fn:contains(url, "/tidings.html") ? "active":"" }'>
    				<fmt:message key="entity.tidings" />
    			</a>
    		</li>
    		<li>
    			<a href="<c:url value="/travels.html"/>" class='${fn:contains(url, "/travels.html") ? "active":"" }'>
    				<fmt:message key="entity.travels" />
				</a>
    		</li>
    			<li>
    			<a href="<c:url value="/posts.html"/>" class='${fn:contains(url, "/posts.html") ? "active":"" }'>
    				<fmt:message key="entity.posts" />
				</a>
    		</li>
    			<li>
    			<a href="<c:url value="/questions.html"/>" class='${fn:contains(url, "/questions.html") ? "active":"" }'>
    				<fmt:message key="entity.questions" />
				</a>
    		</li>
    			<li>
    			<a href="<c:url value="/events.html"/>" class='${fn:contains(url, "/events.html") ? "active":"" }'>
    				<fmt:message key="entity.events" />
				</a>
    		</li>
    	</ul>	
	</nav>



