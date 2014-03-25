<%@page import="com.heaptrip.domain.entity.LangEnum" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="curr_locale" scope="request"><fmt:message key="locale.name"/></c:set>
<c:set var="url" scope="request" value="${requestScope['javax.servlet.forward.servlet_path']}"/>
<c:set var="lang_values" scope="request" value="<%=LangEnum.getValues()%>"/>

<sec:authorize ifNotGranted="ROLE_ANONYMOUS">
    <sec:authentication var="principal" scope="request" property="principal"/>
</sec:authorize>

<c:if test="${not empty param.guid && param.guid ne principal.id}">
    <c:set var="catcher" scope="request" value="${profileModelService.getAccountInformation(param.guid)}"/>
</c:if>

<a id="logo"></a>

<div id="path">
    <div id="path_text">
        <c:if test="${not empty principal}">
            ${principal.name} -
        </c:if>
        <c:if test="${not empty catcher}">
            <span style="color: #ffee2f"> ${catcher.name} - </span>
        </c:if>
            <span style="color: #2abeff">
                ${fn:contains(url, "tidings") ? "tidings":"" }
                ${fn:contains(url, "profile") ? "profiles":"" }
                ${fn:contains(url, "travel") ? "travels":"" }
                ${fn:contains(url, "post") ? "posts":"" }
                ${fn:contains(url, "question") ? "questions":"" }
                ${fn:contains(url, "event") ? "events":"" }
                ${fn:contains(url, "notification") ? "notifications":"" }
                ${fn:contains(url, "communit") ? "communities":"" }
                ${fn:contains(url, "option") ? "options":"" }
                ${fn:contains(url, "peopl") ? "people":"" }
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
            <a href="<c:url value="/login.html"/>">
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

