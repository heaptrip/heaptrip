<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
    var onCommunitySubmit = function (btn) {

        $(btn).prop('disabled', true);

        var jsonData = {
            id: window.catcher ? window.catcher.id : null
        };

        jsonData.name = $("#community_name").val();
        jsonData.email = $("#community_email").val();
        jsonData.typeAccount = "CLUB";

        var url = '../rest/security/community/registration';

        var callbackSuccess = function (data) {
            var domain = $("#community_email").val().replace(/.*@/, "");
            window.location = 'confirmation?domain=' + domain;
        };

        var callbackError = function (error) {
            $(btn).prop('disabled', true);
            $("#error_message #msg").html(error + '<br/><br/>');
        };

        $.postJSON(url, jsonData, callbackSuccess, callbackError);
    };

</script>







<section id="middle">
    <div id="container">
        <div id="contents">

            <div id="error_message">
                <span id="msg" class="error_message"></span>
            </div>

            <div id="registration">

                <form:form name="form">


                    <dl>
                        <dt>
                            <label><fmt:message key="community.name"/></label>
                        </dt>
                        <dd>
                            <input id="community_name" type="text" value="${account.name}"/>
                        </dd>
                    </dl>

                    <dl>
                        <dt>
                            <label><fmt:message key="accountProfile.email"/></label>
                        </dt>
                        <dd>
                            <input id="community_email" type="text" value="${account.name}"/>
                        </dd>
                    </dl>

                    <dl id="soglashenie">
                        <dt>
                            <label><input type="checkbox">
                                <a href="/"><fmt:message key="user.action.acceptTerms"/> <fmt:message
                                        key="user.action.agreement"/> </a>
                            </label>
                        </dt>
                        <dd><a id="go" onClick="onCommunitySubmit(this)"><fmt:message key="page.action.save"/></a></dd>
                    </dl>
                </form:form>
            </div>
        </div>
    </div>
</section>
