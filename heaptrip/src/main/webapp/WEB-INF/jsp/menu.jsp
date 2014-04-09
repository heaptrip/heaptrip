<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav id="nav">

<div class="global_menu">
    <ul>
        <c:if test="${not empty principal}">
            <li>
                <a href="<c:url value="/ct-tidings.html"/>" class='${mode eq 'CONTENT' ? "active":"" }'> <fmt:message
                        key="content.list.title"/></a>
            </li>
            <li>
                <a href="<c:url value="/pf-profile.html"/>" class='${mode eq 'PROFILE' ? "active":"" }'>
                    <fmt:message key="accountProfile.title"/>
                </a>
            </li>
            <li>
                <a href="<c:url value="/my-travels.html"/>" class='${mode eq 'MY' ? "active":"" }'>
                    <fmt:message key="accountProfile.my"/>
                </a>
            </li>
            <li>
                <a href="<c:url value="/fv-travels.html"/>" class='${mode eq 'FAVORITE' ? "active":"" }'>
                    <fmt:message key="accountProfile.favorite"/>
                </a>
            </li>
        </c:if>
    </ul>
</div>

<ul>
    <c:if test="${mode eq 'CONTENT'}">
        <li>
            <a href="<c:url value="/ct-tidings.html"/>" class="${fn:contains(url, '/ct-tiding') ? 'active':'' }">
                <fmt:message key="tiding.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/ct-travels.html"/>" class='${fn:contains(url, "/ct-travel") ? "active":"" }'>
                <fmt:message key="trip.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/ct-posts.html"/>" class='${fn:contains(url, "/ct-post") ? "active":"" }'>
                <fmt:message key="post.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/ct-questions.html"/>"
               class='${fn:contains(url, "/ct-questions") ? "active":"" }'>
                <fmt:message key="question.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/ct-events.html"/>" class='${fn:contains(url, "/ct-events") ? "active":"" }'>
                <fmt:message key="event.list.title"/>
            </a>
        </li>
    </c:if>

    <c:if test="${mode eq 'MY'}">
        <li>
            <a href="<c:url value="/my-travels.html"/>" class='${fn:contains(url, "/my-travel") ? "active":"" }'>
                <fmt:message key="trip.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/my-posts.html"/>" class='${fn:contains(url, "/my-post") ? "active":"" }'>
                <fmt:message key="post.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/my-questions.html"/>"
               class='${fn:contains(url, "/my-questions") ? "active":"" }'>
                <fmt:message key="question.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/my-events.html"/>" class='${fn:contains(url, "/my-events") ? "active":"" }'>
                <fmt:message key="event.list.title"/>
            </a>
        </li>
    </c:if>

    <c:if test="${mode eq 'FAVORITE'}">
        <li>
            <a href="<c:url value="/fv-travels.html"/>" class='${fn:contains(url, "/fv-travel") ? "active":"" }'>
                <fmt:message key="trip.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/fv-posts.html"/>" class='${fn:contains(url, "/fv-post") ? "active":"" }'>
                <fmt:message key="post.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/fv-questions.html"/>"
               class='${fn:contains(url, "/fv-questions") ? "active":"" }'>
                <fmt:message key="question.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/fv-events.html"/>" class='${fn:contains(url, "/fv-events") ? "active":"" }'>
                <fmt:message key="event.list.title"/>
            </a>
        </li>
    </c:if>

    <%--свой аккаунт--%>
    <c:if test="${mode eq 'PROFILE' && empty catcher}">
        <li>
            <a href="<c:url value="/pf-profile.html"/>" class='${fn:contains(url, "/pf-profile") ? "active":"" }'>
                <fmt:message key="accountProfile.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/pf-notification.html"/>"
               class='${fn:contains(url, "/pf-notification") ? "active":"" }'>
                <fmt:message key="accountProfile.notification"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/pf-people.html"/>" class='${fn:contains(url, "/pf-people") ? "active":"" }'>
                <fmt:message key="accountProfile.people"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/pf-communities.html"/>" class='${fn:contains(url, "/pf-communit") ? "active":"" }'>
                <fmt:message key="accountProfile.community"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/pf-options.html"/>" class='${fn:contains(url, "/pf-options") ? "active":"" }'>
                <fmt:message key="accountProfile.options"/>
            </a>
        </li>
    </c:if>

    <%--чужой аккаунт--%>
    <c:if test="${mode eq 'PROFILE' && not empty catcher  }">


        <%--тип чужого аккаунта пользователь--%>

        <c:if test="${ catcher.typeAccount eq 'USER' }">


            <li>
                <a href="<c:url value="/pf-profile.html?guid=${catcher.id}"/>"
                   class='${fn:contains(url, "/pf-profile") ? "active":"" }'>
                    <fmt:message key="accountProfile.title"/>
                </a>
            </li>

            <li>
                <a href="<c:url value="/pf-communities.html?guid=${catcher.id}"/>"
                   class='${fn:contains(url, "/pf-communit") ? "active":"" }'>
                    <fmt:message key="accountProfile.community"/>
                </a>
            </li>

        </c:if>
        <%--тип чужого аккаунта сообщество--%>
        <c:if test="${ catcher.typeAccount ne 'USER' }">


            <li>
                <a href="<c:url value="/pf-community.html?guid=${catcher.id}"/>"
                   class='${fn:contains(url, "/pf-community") ? "active":"" }'>
                    <fmt:message key="accountProfile.title"/>
                </a>
            </li>

            <li>
                <a href="<c:url value="/pf-tidings.html?guid=${catcher.id}"/>"
                   class='${fn:contains(url, "/pf-tidings") ? "active":"" }'>
                    <fmt:message key="tiding.list.title"/>
                </a>
            </li>

        </c:if>


        <li>
            <a href="<c:url value="/pf-travels.html?guid=${catcher.id}"/>"
               class='${fn:contains(url, "/pf-travel") ? "active":"" }'>
                <fmt:message key="trip.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/pf-posts.html?guid=${catcher.id}"/>"
               class='${fn:contains(url, "/pf-post") ? "active":"" }'>
                <fmt:message key="post.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/pf-questions.html?guid=${catcher.id}"/>"
               class='${fn:contains(url, "/pf-questions") ? "active":"" }'>
                <fmt:message key="question.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/pf-events.html?guid=${catcher.id}"/>"
               class='${fn:contains(url, "/pf-events") ? "active":"" }'>
                <fmt:message key="event.list.title"/>
            </a>
        </li>
    </c:if>

</ul>
</nav>



