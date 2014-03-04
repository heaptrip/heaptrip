<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="url" value="${requestScope['javax.servlet.forward.servlet_path']}"/>


<nav id="nav">
    <ul>

        <!--c:if test="${not empty param.guid }"-->

            <li>
                <a href="<c:url value="/tidings.html"/>" class="${fn:contains(url, '/tiding') ? 'active':'' }">
                    <fmt:message key="tiding.list.title" />
                </a>
            </li>

        <!--/c:if-->


        <li>
            <a href="<c:url value="/profile.html?guid=${param.guid}"/>"
               class="${fn:contains(url, '/profile') ? 'active':'' }">
                <fmt:message key="accountProfile.info"/>
            </a>
        </li>

        <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
            <sec:authentication var="principal" property="principal"/>

            <c:if test="${not empty principal.id && empty param.guid }">
                <li>
                    <a href="<c:url value="/notification.html?guid=${param.guid}"/>"
                       class='${fn:contains(url, "/notification") ? "active":"" }'>
                        <fmt:message key="accountProfile.notification"/>
                    </a>
                </li>
            </c:if>
        </sec:authorize>


        <li>
            <a href="<c:url value="/"/>" class='${fn:contains(url, "/people") ? "active":"" }'>
                <fmt:message key="accountProfile.people"/>
            </a>
        </li>




        <c:if test="${not empty param.guid }">

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


        </c:if>


        <li>
            <a href="<c:url value="/communities.html?guid=${param.guid}"/>"
               class='${fn:contains(url, "/communit") ? "active":"" }'>
                <fmt:message key="accountProfile.community"/>
            </a>
        </li>

        <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
            <sec:authentication var="principal" property="principal"/>

            <c:if test="${not empty principal.id && empty param.guid }">
                <li>
                    <a href="<c:url value="/options.html?guid=${param.guid}"/>"
                       class='${fn:contains(url, "/options") ? "active":"" }'>
                        <fmt:message key="accountProfile.options"/>
                    </a>
                </li>
            </c:if>
        </sec:authorize>
    </ul>
</nav>



