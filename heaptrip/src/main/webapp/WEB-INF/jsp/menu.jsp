<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav id="nav">


<div class="global_menu">
    <ul>
        <c:if test="${not empty principal}">
            <li>
                <a href="<c:url value="/ct/tidings"/>" class='${mode eq 'CONTENT' ? "active":"" }'> <fmt:message
                        key="content.list.title"/></a>
            </li>
            <li>
                <a href="<c:url value="/pf/profile"/>" class='${mode eq 'PROFILE' ? "active":"" }'>
                    <fmt:message key="accountProfile.title"/>
                </a>
            </li>
            <li>
                <a href="<c:url value="/my/travels"/>" class='${mode eq 'MY' ? "active":"" }'>
                    <fmt:message key="accountProfile.my"/>
                </a>
            </li>
            <li>
                <a href="<c:url value="/fv/travels"/>" class='${mode eq 'FAVORITE' ? "active":"" }'>
                    <fmt:message key="accountProfile.favorite"/>
                </a>
            </li>
        </c:if>
    </ul>
</div>

<ul>
    <c:if test="${mode eq 'CONTENT'}">
        <li>
            <a href="<c:url value="/ct/tidings"/>" class="${ fn:indexOf( url , '/ct/tiding') ne -1 ? 'active':'' }">
                <fmt:message key="tiding.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/ct/travels"/>" class='${fn:contains(url, "/ct/travel") ? "active":"" }'>
                <fmt:message key="trip.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/ct/posts"/>" class='${fn:contains(url, "/ct/post") ? "active":"" }'>
                <fmt:message key="post.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/nf/ct/questions"/>"
               class='${fn:contains(url, "/ct/questions") ? "active":"" }'>
                <fmt:message key="question.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/nf/ct/events"/>" class='${fn:contains(url, "/ct/events") ? "active":"" }'>
                <fmt:message key="event.list.title"/>
            </a>
        </li>
    </c:if>

    <c:if test="${mode eq 'MY'}">
        <li>
            <a href="<c:url value="/my/travels"/>" class='${fn:contains(url, "/my/travel") ? "active":"" }'>
                <fmt:message key="trip.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/my/posts"/>" class='${fn:contains(url, "/my/post") ? "active":"" }'>
                <fmt:message key="post.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/nf/my/questions"/>"
               class='${fn:contains(url, "/my/questions") ? "active":"" }'>
                <fmt:message key="question.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/nf/my/events"/>" class='${fn:contains(url, "/my/events") ? "active":"" }'>
                <fmt:message key="event.list.title"/>
            </a>
        </li>
    </c:if>

    <c:if test="${mode eq 'FAVORITE'}">
        <li>
            <a href="<c:url value="/fv/travels"/>" class='${fn:contains(url, "/fv/travel") ? "active":"" }'>
                <fmt:message key="trip.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/fv/posts"/>" class='${fn:contains(url, "/fv/post") ? "active":"" }'>
                <fmt:message key="post.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/nf/fv/questions"/>"
               class='${fn:contains(url, "/fv/questions") ? "active":"" }'>
                <fmt:message key="question.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/nf/fv/events"/>" class='${fn:contains(url, "/fv/events") ? "active":"" }'>
                <fmt:message key="event.list.title"/>
            </a>
        </li>
    </c:if>

    <%--свой аккаунт--%>
    <c:if test="${mode eq 'PROFILE' && empty catcher}">
        <li>
            <a href="<c:url value="/pf/profile"/>" class='${url eq "/heaptrip/pf/profile" ? "active":"" }'>
                <fmt:message key="accountProfile.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/pf/notification-security"/>"
               class='${fn:contains(url, "/pf/notification") ? "active":"" }'>
                <fmt:message key="accountProfile.notification"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/pf/profile_people"/>"
               class='${fn:contains(url, "/pf/profile_people") ? "active":"" }'>
                <fmt:message key="accountProfile.people"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/pf/communities"/>" class='${fn:contains(url, "/pf/communit") ? "active":"" }'>
                <fmt:message key="accountProfile.community"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/pf/profile_modify_options"/>"
               class='${fn:contains(url, "/pf/profile_modify_options") ? "active":"" }'>
                <fmt:message key="accountProfile.options"/>
            </a>
        </li>
    </c:if>

    <%--чужой аккаунт--%>
    <c:if test="${mode eq 'PROFILE' && not empty catcher  }">


        <%--тип чужого аккаунта пользователь--%>

        <c:if test="${ catcher.typeAccount eq 'USER' }">


            <li>
                <a href="<c:url value="/pf/profile?guid=${catcher.id}"/>"
                   class='${fn:contains(url, "/pf/profile") ? "active":"" }'>
                    <fmt:message key="accountProfile.title"/>
                </a>
            </li>

            <li>
                <a href="<c:url value="/pf/communities?guid=${catcher.id}"/>"
                   class='${fn:contains(url, "/pf/communit") ? "active":"" }'>
                    <fmt:message key="accountProfile.community"/>
                </a>
            </li>

        </c:if>
        <%--тип чужого аккаунта сообщество--%>
        <c:if test="${ catcher.typeAccount ne 'USER' }">


            <li>
                <a href="<c:url value="/pf/community?guid=${catcher.id}"/>"
                   class='${url eq "/heaptrip/pf/community" ? "active":"" }'>
                    <fmt:message key="accountProfile.title"/>
                </a>
            </li>

            <li>
                <a href="<c:url value="/pf/tidings?guid=${catcher.id}"/>"
                   class='${fn:contains(url, "/pf/tidings") ? "active":"" }'>
                    <fmt:message key="tiding.list.title"/>
                </a>
            </li>
            <li>
                <a href="<c:url value="/pf/community_people?guid=${catcher.id}"/>"
                   class='${fn:contains(url, "/pf/community_people") ? "active":"" }'>
                    <fmt:message key="accountProfile.people"/>
                </a>
            </li>

        </c:if>

        <li>
            <a href="<c:url value="/pf/travels?guid=${catcher.id}"/>"
               class='${fn:contains(url, "/pf/travel") ? "active":"" }'>
                <fmt:message key="trip.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/pf/posts?guid=${catcher.id}"/>"
               class='${fn:contains(url, "/pf/post") ? "active":"" }'>
                <fmt:message key="post.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/nf/pf/questions?guid=${catcher.id}"/>"
               class='${fn:contains(url, "/pf/questions") ? "active":"" }'>
                <fmt:message key="question.list.title"/>
            </a>
        </li>
        <li>
            <a href="<c:url value="/nf/pf/events?guid=${catcher.id}"/>"
               class='${fn:contains(url, "/pf/events") ? "active":"" }'>
                <fmt:message key="event.list.title"/>
            </a>
        </li>

        <%--тип чужого аккаунта сообщество и ползователь владелец сообщества--%>
        <c:if test="${ catcher.typeAccount ne 'USER' &&  profileServiceWrapper.isUserOwnsCommunity(principal.id, catcher.id)}">
            <li>
                <a href="<c:url value="/pf/community_modify_options?guid=${catcher.id}"/>"
                   class='${fn:contains(url, "/pf/community_modify_options") ? "active":"" }'>
                    <fmt:message key="accountProfile.options"/>
                </a>
            </li>
        </c:if>

    </c:if>

</ul>
</nav>



