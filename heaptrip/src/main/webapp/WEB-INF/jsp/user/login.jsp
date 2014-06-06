<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:set var="domain_url"
       value="${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>

<%--<!-- VKontakte properties -->--%>
<c:set var="vk_client_id"><spring:eval expression="@applicationProperties.getProperty('socnet.vk.client_id')"/></c:set>
<c:set var="vk_authorize_url"><spring:eval expression="@applicationProperties.getProperty('socnet.vk.authorize_url')"/></c:set>
<c:set var="vk_scope"><spring:eval expression="@applicationProperties.getProperty('socnet.vk.scope')"/></c:set>
<%--<!-- FaceBook properties -->--%>
<c:set var="socnet.fb.client_id"><spring:eval
        expression="@applicationProperties.getProperty('socnet.fb.client_id')"/></c:set>
<c:set var="fb_authorize_url"><spring:eval expression="@applicationProperties.getProperty('socnet.fb.authorize_url')"/></c:set>

<c:url var="vkUrl" value="${vk_authorize_url}">
    <c:param name="client_id" value="${vk_client_id}"/>
    <c:param name="scope" value="${vk_scope}"/>
    <c:param name="redirect_uri" value="http://${domain_url}/rest/registration/socnet/vk"/>
    <c:param name="display" value="page"/>
    <c:param name="response_type" value="code"/>
</c:url>

<c:url var="fbUrl" value="${fb_authorize_url}">
    <c:param name="client_id" value="${fb_client_id}"/>
    <c:param name="redirect_uri" value="http://${domain_url}/rest/registration/socnet/fb"/>
    <c:param name="display" value="page"/>
    <c:param name="response_type" value="code"/>
</c:url>


<nav id="nav">
    <ul>
        <li><a href="<c:url value="/pf/login"/>" class="active"><fmt:message key="user.action.login"/></a></li>
        <li><a href="<c:url value="/pf/registration"/>"><fmt:message key="user.action.registration"/></a></li>
    </ul>
</nav>

<c:if test="${not empty param.login_error}">
    <div id="error_message" class="error_message">
        <p>
            <c:choose>
                <c:when test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}">
                    ${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}
                </c:when>
                <c:otherwise>
                    ${param.login_error}
                </c:otherwise>
            </c:choose>
        </p>
    </div>
</c:if>

<section id="middle">
    <div id="container">
        <div id="contents">
            <div id="authorization">
                <form name="auth" action="<c:url value="/loginProcess" />" method="post">
                    <dl>
                        <dt>
                            <label><fmt:message key="user.login"/></label>
                        </dt>
                        <dd>
                            <input type="text" placeholder="" name="j_username"
                                   value="${SPRING_SECURITY_LAST_USERNAME}"/>
                        </dd>
                    </dl>
                    <dl>
                        <dt>
                            <label><fmt:message key="user.password"/></label>
                        </dt>
                        <dd>
                            <input type="password" name="j_password"/>
                        </dd>
                    </dl>
                    <dl id="soglashenie">
                        <dt>
                            <label> <input type="checkbox" name="_spring_security_remember_me" id="remember_me"/>
                                <fmt:message key="user.action.rememberMe"/></label>
                        </dt>
                        <dd>
                            <input type="submit" id="submit" name="submit"
                                   value="<fmt:message key="user.action.login"/>">
                        </dd>
                    </dl>
                </form>
                <div id="reg_soc">
                    <span><fmt:message key="user.action.socnetLogin"/>:</span>
                    <a href="${fbUrl}" class="fb"></a>
                    <a href="" class="od"></a>
                    <a href="" class="tv"></a>
                    <a href="${vkUrl}" class="vk"></a>
                </div>
                <div id="link">
                    <a href="<c:url value="/pf/registration"/>" id="reg_link"><fmt:message
                            key="user.action.registration"/></a> <a
                        href="/" id="forgot_password"><fmt:message key="user.action.pswRecover"/></a>
                </div>
            </div>
        </div>
    </div>
</section>
