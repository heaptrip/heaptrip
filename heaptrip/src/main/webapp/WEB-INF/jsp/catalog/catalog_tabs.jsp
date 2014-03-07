<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav id="nav">
    <ul>
        <li>
            <a href="<c:url value="/tidings.html"/>" class="${fn:contains(url, '/tiding') ? 'active':'' }">
                <fmt:message key="tiding.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/travels.html${not empty param.my ? '?my=true':'' }"/>" class='${fn:contains(url, "/travel") ? "active":"" }'>
                <fmt:message key="trip.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/posts.html${not empty param.my ? '?my=true':'' }"/>" class='${fn:contains(url, "/post") ? "active":"" }'>
                <fmt:message key="post.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/questions.ht${not empty param.my ? '?my=true':'' }"/>" class='${fn:contains(url, "/question") ? "active":"" }'>
                <fmt:message key="question.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/events.ht${not empty param.my ? '?my=true':'' }"/>" class='${fn:contains(url, "/event") ? "active":"" }'>
                <fmt:message key="event.list.title"/>
            </a>
        </li>



        <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
            <sec:authentication var="principal" property="principal"/>

            <c:if test="${not empty principal.id && empty param.guid }">
                <li>
                    <a href="<c:url value="/profile.html"/>" class='${fn:contains(url, "/event") ? "active":"" }'>
                        <fmt:message key="accountProfile.title"/>
                    </a>
                </li>
            </c:if>

            <c:if test="${not empty principal.id && empty param.guid }">
                <li>
                    <a href="<c:url value="/travels.html?my=true"/>" class='${not empty param.my ? "active":"" }'>
                        <fmt:message key="accountProfile.my"/>
                    </a>
                </li>
            </c:if>
            <c:if test="${not empty principal.id && empty param.guid }">
                <li>
                    <a href="<c:url value="/events.html"/>" class='${fn:contains(url, "/event") ? "active":"" }'>
                        <fmt:message key="accountProfile.favorite"/>
                    </a>
                </li>
            </c:if>

        </sec:authorize>


    </ul>
</nav>



