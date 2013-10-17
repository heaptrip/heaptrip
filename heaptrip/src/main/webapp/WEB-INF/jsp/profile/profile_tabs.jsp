<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

 	<c:set var="url" value="${requestScope['javax.servlet.forward.servlet_path']}" />
 

	<nav id="nav">
    	<ul>
    		<li>
    			<a href="<c:url value="/profile.html?uid=${param.uid}"/>" class="${fn:contains(url, '/profile') ? 'active':'' }">
    				<fmt:message key="profile.info" />
    			</a>
    		</li>
    		
    		<sec:authorize ifNotGranted="ROLE_ANONYMOUS">
    			<sec:authentication var="principal" property="principal" />
    			
    			<c:if test="${principal.id eq param.uid}">
		   			<li>
    					<a href="<c:url value="/notification.html?uid=${param.uid}"/>" class='${fn:contains(url, "/notification") ? "active":"" }'>
    						<fmt:message key="profile.notification" />
						</a>
    				</li>
    			</c:if>	
    		</sec:authorize>	
    		
    		
    		<li>
    			<a href="<c:url value="/people.todo?uid=${param.uid}"/>" class='${fn:contains(url, "/people") ? "active":"" }'>
    				<fmt:message key="profile.people" />
				</a>
    		</li>
    			<li>
    			<a href="<c:url value="/community.todo?uid=${param.uid}"/>" class='${fn:contains(url, "/community") ? "active":"" }'>
    				<fmt:message key="profile.community" />
				</a>
    		</li>
    			<li>
    			<a href="<c:url value="/options.todo?uid=${param.uid}"/>" class='${fn:contains(url, "/options") ? "active":"" }'>
    				<fmt:message key="profile.options" />
				</a>
    		</li>
    	</ul>	
	</nav>



