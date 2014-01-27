<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


 	<c:set var="url" value="${requestScope['javax.servlet.forward.servlet_path']}" />

	<nav id="nav">
    	<ul>
    		<li>
    			<a href="<c:url value="/tidings.html"/>" class="${fn:contains(url, '/tiding') ? 'active':'' }">
    				<fmt:message key="tiding.list.title" />
    			</a>
    		</li>
    		<li>
    			<a href="<c:url value="/travels.html"/>" class='${fn:contains(url, "/travel") ? "active":"" }'>
    				<fmt:message key="trip.list.title" />
				</a>
    		</li>
    			<li>
    			<a href="<c:url value="/posts.html"/>" class='${fn:contains(url, "/post") ? "active":"" }'>
    				<fmt:message key="post.list.title" />
				</a>
    		</li>
    			<li>
    			<a href="<c:url value="/questions.html"/>" class='${fn:contains(url, "/question") ? "active":"" }'>
    				<fmt:message key="question.list.title" />
				</a>
    		</li>
    			<li>
    			<a href="<c:url value="/events.html"/>" class='${fn:contains(url, "/event") ? "active":"" }'>
    				<fmt:message key="event.list.title" />
				</a>
    		</li>
    	</ul>	
	</nav>



