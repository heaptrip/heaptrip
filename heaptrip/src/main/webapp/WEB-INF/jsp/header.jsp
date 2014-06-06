<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<a id="logo"></a>

<c:if test="${not empty principal}">

    <a href="<c:url value='/pf/notification-security'/>"
       id="alert">${notificationServiceWrapper.getUnreadNotificationFromUsers()}/${notificationServiceWrapper.getUnreadNotificationFromCommunities()}</a>

</c:if>

<div id="path">
    <div id="path_text">
        <c:if test="${not empty principal}">
            ${principal.name} -
        </c:if>
        <c:if test="${not empty catcher}">
            <span style="color: #ffee2f"> ${catcher.name} - </span>
        </c:if>
            <span style="color: #2abeff">



                <c:if test='${fn:contains(url, "/my")}'>
                    <fmt:message key="accountProfile.my"/>
                </c:if>

                <c:choose>
                    <c:when test='${fn:contains(url, "tidings")}'>
                        <fmt:message key="tiding.list.title"/>
                    </c:when>
                    <c:when test='${fn:contains(url, "profile")}'>
                        <fmt:message key="accountProfile.title"/>
                    </c:when>
                    <c:when test='${fn:contains(url, "travel")}'>
                        <fmt:message key="trip.list.title"/>
                    </c:when>
                    <c:when test='${fn:contains(url, "post")}'>
                        <fmt:message key="post.list.title"/>
                    </c:when>
                    <c:when test='${fn:contains(url, "question")}'>
                        <fmt:message key="question.list.title"/>
                    </c:when>
                    <c:when test='${fn:contains(url, "event")}'>
                        <fmt:message key="event.list.title"/>
                    </c:when>
                    <c:when test='${fn:contains(url, "notification")}'>
                        <fmt:message key="accountProfile.notification"/>
                    </c:when>
                    <c:when test='${fn:contains(url, "communities")}'>
                        <fmt:message key="accountProfile.community"/>
                    </c:when>
                    <c:when test='${fn:contains(url, "communit")}'>
                        <fmt:message key="accountProfile.title"/> <fmt:message key="accountProfile.community"/>
                    </c:when>
                    <c:when test='${fn:contains(url, "option")}'>
                        <fmt:message key="accountProfile.options"/>
                    </c:when>
                    <c:when test='${fn:contains(url, "peopl")}'>
                        <fmt:message key="accountProfile.people"/>
                    </c:when>
                    <c:when test='${fn:contains(url, "error")}'>
                        <fmt:message key="page.action.error"/>
                    </c:when>
                    <c:otherwise>

                    </c:otherwise>
                </c:choose>
            </span>
    </div>
</div>

<div id="language">
    <div id="language_now">
        <span id="language_show"></span>
        <span class='${curr_locale}'></span>
        <fmt:message key="locale.current"/>
    </div>
    <ul>
        <c:forEach items="${lang_values}" var="langValue">
            <c:if test="${curr_locale ne langValue}">
                <li>
                    <a onClick="onLocaleChange('${langValue}')">
                        <span class="${langValue}"></span>
                        <fmt:message key="locale.${langValue}"/>
                    </a>
                </li>
            </c:if>
        </c:forEach>
    </ul>
</div>

<div id="account">
    <sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
        <div id="account_name" class="login">
            <a href="<c:url value="/pf/login"/>">
                <fmt:message key="user.action.login"/>
            </a>
        </div>
    </sec:authorize>
    <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
        <sec:authentication var="principal" property="principal"/>
        <div id="account_name" class="login">
            <a href="<c:url value="/logout"/>">
                <fmt:message key="user.action.logout"/>
            </a>
        </div>
    </sec:authorize>
</div>

