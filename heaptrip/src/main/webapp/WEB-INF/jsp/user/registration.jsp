<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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


<script type="text/javascript">
    var onRegistrationSubmit = function (btn) {

        $(btn).prop('disabled', true);

        var url = '../rest/user/registration';

        // registrationInfo
        var jsonData = {
            email: $("#email").val(),
            password: $("#password").val(),
            firstName: $("#firstName").val(),
            secondName: $("#secondName").val(),
            socNetName: $("#socNetName").val(),
            socNetUserUID: $("#socNetUserUID").val(),
            photoUrl: $("#photoUrl").val()
        };

        var callbackSuccess = function (data) {
            var domain = $("#email").val().replace(/.*@/, "");
            window.location = 'confirmation?domain=' + domain;
        };

        var callbackError = function (error) {
            $(btn).prop('disabled', false);
            $(".error_message").html('<p class="red">' + error + '</p>');
        };

        $.postJSON(url, jsonData, callbackSuccess, callbackError);

    };
</script>

<nav id="nav">
    <ul>
        <li><a href="<c:url value="/pf/login"/>"><fmt:message key="user.action.login"/></a></li>
        <li><a href="<c:url value="/pf/registration"/>" class="active"><fmt:message
                key="user.action.registration"/></a></li>
    </ul>
</nav>


<section id="middle">
    <div id="container">
        <div id="contents">

            <div id="error_message" class="error_message"></div>

            <div id="registration">

                <form:form name="reg" modelAttribute="registrationInfo" action="" method="post">

                    <form:hidden path="socNetName" id="socNetName"/>
                    <form:hidden path="socNetUserUID" id="socNetUserUID"/>
                    <form:hidden path="photoUrl" id="photoUrl"/>


                    <dl>
                        <dt>
                            <label><fmt:message key="user.firstName"/></label>
                        </dt>
                        <dd>
                            <form:input type="text" path="firstName" id="firstName" placeholder=""/>
                        </dd>
                    </dl>
                    <dl>
                        <dt>
                            <label><fmt:message key="user.secondName"/></label>
                        </dt>
                        <dd>
                            <form:input type="text" path="secondName" id="secondName" placeholder=""/>
                        </dd>
                    </dl>
                    <c:if test="${ not empty registrationInfo.socNetName}">
                        <div class="error_message">
                            <p class="green"><fmt:message key="user.action.enterSocEmail"/></p>
                        </div>
                    </c:if>
                    <dl>
                        <dt>
                            <label><fmt:message key="user.login"/></label>
                        </dt>
                        <dd>

                            <form:input type="text" path="email" id="email" placeholder=""/>
                        </dd>
                    </dl>

                    <c:if test="${ empty registrationInfo.socNetName}">

                        <dl>
                            <dt>
                                <label><fmt:message key="user.password"/></label>
                            </dt>
                            <dd>
                                <input type="password" id="password">
                            </dd>
                        </dl>
                        <dl>
                            <dt>
                                <label><fmt:message key="user.action.pswRepeat"/></label>
                            </dt>
                            <dd>
                                <input type="password" id="repeatPassword">
                            </dd>
                        </dl>

                        <dl id="capcha">
                            <dt>
                                <label><fmt:message key="user.action.enterCode"/></label>
                            </dt>
                            <dd>
                                <input type="text" name="capcha" value=""><img
                                    src="<c:url value="/images/capcha.jpg"/>"/>
                            </dd>
                        </dl>


                    </c:if>

                    <dl id="soglashenie">
                        <dt>
                            <label><input type="checkbox" id="soglashenie"> <fmt:message key="user.action.acceptTerms"/><a
                                    href="/"><br/> <fmt:message key="user.action.agreement"/> </a></label>
                        </dt>


                        <dd><a id="go" onClick="onRegistrationSubmit()"><fmt:message
                                key="user.action.registration"/></a></dd>

                        <!-- <dd><input onClick="onRegistrationCansel()" type="submit" id="go" name="go" value="<fmt:message key="user.action.registration" />"></dd> -->
                    </dl>
                </form:form>

                <c:if test="${ empty registrationInfo.socNetName}">
                    <div id="reg_soc">
                        <span><fmt:message key="user.action.registration"/><br/> <fmt:message
                                key="user.action.throughSocNet"/>:</span>
                        <a href="<c:out value="${fbUrl}"/>" class="fb"> </a> <a href="" class="od"> </a> <a href=""
                                                                                                            class="tv"></a>
                        <a
                                href="<c:out value="${vkUrl}"/>" class="vk"></a>
                    </div>
                </c:if>
            </div>
        </div>
        <!-- #content-->
    </div>
    <!-- #container-->
</section>
<!-- #middle-->