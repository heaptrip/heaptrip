<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.heaptrip.domain.entity.LangEnum" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:useAttribute id="script" name="scripts" classname="java.util.List"/>

<c:forEach var="script" items="${script}">
    <script type="text/javascript" src="<c:url value="${script}" />"></script>
</c:forEach>

<c:set var="curr_locale" scope="request"><fmt:message key="locale.name"/></c:set>

<script type="text/javascript" src="js/locale/locale_${curr_locale}.js"></script>

<c:set var="url" scope="request" value="${requestScope['javax.servlet.forward.servlet_path']}"/>
<c:set var="lang_values" scope="request" value="<%=LangEnum.getValues()%>"/>

<sec:authorize ifNotGranted="ROLE_ANONYMOUS">
    <sec:authentication var="principal" scope="request" property="principal"/>
</sec:authorize>

<c:if test="${not empty param.guid && param.guid ne principal.id}">
    <c:set var="catcher" scope="request" value="${profileServiceWrapper.getAccountInformation(param.guid)}"/>
</c:if>

<c:if test="${fn:contains(url, 'ct-')}">
    <c:set var="mode" scope="request" value="CONTENT"/>
</c:if>
<c:if test="${fn:contains(url, 'my-')}">
    <c:set var="mode" scope="request" value="MY"/>
</c:if>
<c:if test="${fn:contains(url, 'fv-')}">
    <c:set var="mode" scope="request" value="FAVORITE"/>
</c:if>
<c:if test="${fn:contains(url, 'pf-')}">
    <c:set var="mode" scope="request" value="PROFILE"/>
</c:if>


<script type="text/javascript">
    <c:if test="${not empty mode}">
        window.mode = '${mode}'
    </c:if>
    <c:if test="${not empty principal}">
        window.principal = {};
        window.principal.id = '${principal.id}';
        window.principal.name = '${principal.name}';
    </c:if>
    <c:if test="${not empty catcher}">
        window.catcher = {};
        window.catcher.id = '${catcher.id}';
        window.catcher.name = '${catcher.name}';
    </c:if>

</script>