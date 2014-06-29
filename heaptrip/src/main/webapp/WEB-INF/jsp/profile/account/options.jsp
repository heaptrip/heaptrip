<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
    var onChangeEmailSubmit = function (btn) {

        $(btn).prop('disabled', true);

        var jsonData = {
            id: window.catcher ? window.catcher.id : window.principal.id,
            currentValue:  $("#current_email").val(),
            newValue: $("#new_email").val()
        };

        var url = '../rest/security/change_email';

        var callbackSuccess = function (data) {
            $(btn).prop('disabled', false);
            $(".error_message").html('<p class="green">' + <fmt:message key="accountProfile.Setting.email"/> + '</p>');
        };

        var callbackError = function (error) {
            $(btn).prop('disabled', true);
            $("#error_message #msg").html(error + '<br/><br/>');
        };

        $.postJSON(url, jsonData, callbackSuccess, callbackError);
    };

    var onChangePasswordSubmit = function (btn) {

        $(btn).prop('disabled', true);

        var jsonData = {
            id: window.catcher ? window.catcher.id : window.principal.id,
            currentValue:  $("#current_password").val(),
            newValue: $("#new_password").val()
        };

        var url = '../rest/security/change_password';

        var callbackSuccess = function (data) {
            $(btn).prop('disabled', false);
            $(".error_message").html('<p class="green">' + <fmt:message key="accountProfile.Setting.password"/> + '</p>');
        };

        var callbackError = function (error) {
            $(btn).prop('disabled', true);
            $("#error_message #msg").html(error + '<br/><br/>');
        };

        $.postJSON(url, jsonData, callbackSuccess, callbackError);
    };

    var onDeleteSubmit = function (btn) {
        var url = '../rest/security/user_delete';

        var id = window.catcher ? window.catcher.id : window.principal.id;

        var callbackSuccess = function (data) {
            window.location = '../ct/tidings';
        };

        var callbackError = function (error) {
            $(btn).prop('disabled', true);
            $("#error_message #msg").html(error + '<br/><br/>');
        };

        $.postJSON(url, id, callbackSuccess, callbackError);
    };
</script>

<section id="middle">
    <div id="container">
        <div id="contents">

            <div id="error_message">
                <span id="msg" class="error_message"></span>
            </div>

            <div class="left">
                <h2 class="people_title"><fmt:message key="accountProfile.Setting.password"/></h2>
            </div>

            <dl>
                <dd>
                    <input id="current_password" type="text"/>
                </dd>
                <dd>
                    <input id="new_password" type="text"/>
                </dd>
                <dd>
                    <input id="new_password_repeat" type="text"/>
                </dd>
            </dl>

            <div>
                <a id="changePassword" class="button" onClick="onChangePasswordSubmit()"><fmt:message key="page.action.change"/></a>
            </div>

            <div class="left">
                <h2 class="people_title"><fmt:message key="accountProfile.Setting.email"/></h2>
            </div>

            <dl>
                <dd>
                    <input id="current_email" type="text"/>
                </dd>
                <dd>
                    <input id="new_email" type="text"/>
                </dd>
                <dd>
                    <input id="new_email_repeat" type="text"/>
                </dd>
            </dl>

            <div>
                <a id="changeEmail" class="button" onClick="onChangeEmailSubmit()"><fmt:message key="page.action.change"/></a>
            </div>

            <div class="left">
                <h2 class="people_title"><fmt:message key="accountProfile.Setting.delete"/></h2>
            </div>

            <div>
                <a id="delete" class="button" onClick="onDeleteSubmit()"><fmt:message key="page.action.delete"/></a>
            </div>
        </div>
    </div>
</section>