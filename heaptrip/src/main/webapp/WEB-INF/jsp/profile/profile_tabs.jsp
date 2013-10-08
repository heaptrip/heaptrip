<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

 	<c:set var="url" value="${requestScope['javax.servlet.forward.servlet_path']}" />

	<nav id="nav">
    	<ul>
    		<li>
    			<a href="<c:url value="/account_info.html"/>" class="${fn:contains(url, '/account_info') ? 'active':'' }">
    				<fmt:message key="profile.info" />
    			</a>
    		</li>
    		<li>
    			<a href="<c:url value="/notification.html"/>" class='${fn:contains(url, "/notification") ? "active":"" }'>
    				<fmt:message key="profile.notification" />
				</a>
    		</li>
    			<li>
    			<a href="<c:url value="/people.html"/>" class='${fn:contains(url, "/people") ? "active":"" }'>
    				<fmt:message key="profile.people" />
				</a>
    		</li>
    			<li>
    			<a href="<c:url value="/community.html"/>" class='${fn:contains(url, "/community") ? "active":"" }'>
    				<fmt:message key="profile.community" />
				</a>
    		</li>
    			<li>
    			<a href="<c:url value="/options.html"/>" class='${fn:contains(url, "/options") ? "active":"" }'>
    				<fmt:message key="profile.options" />
				</a>
    		</li>
    	</ul>	
	</nav>



