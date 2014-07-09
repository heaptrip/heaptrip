<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
    <%--var onChangeEmailSubmit = function (btn) {--%>

        <%--$(btn).prop('disabled', true);--%>

        <%--var jsonData = {--%>
            <%--id: window.catcher ? window.catcher.id : window.principal.id,--%>
            <%--currentValue: $("#current_email").val(),--%>
            <%--newValue: $("#new_email").val()--%>
        <%--};--%>

        <%--var url = '../rest/security/change_email';--%>

        <%--var callbackSuccess = function (data) {--%>
            <%--$(btn).prop('disabled', false);--%>
            <%--$(".error_message").html('<p class="green">' + <fmt:message key="accountProfile.Setting.email"/> +'</p>');--%>
        <%--};--%>

        <%--var callbackError = function (error) {--%>
            <%--$(btn).prop('disabled', true);--%>
            <%--$("#error_message #msg").html(error + '<br/><br/>');--%>
        <%--};--%>

        <%--$.postJSON(url, jsonData, callbackSuccess, callbackError);--%>
    <%--};--%>

    <%--var onChangePasswordSubmit = function (btn) {--%>

        <%--$(btn).prop('disabled', true);--%>

        <%--var jsonData = {--%>
            <%--id: window.catcher ? window.catcher.id : window.principal.id,--%>
            <%--currentValue: $("#current_password").val(),--%>
            <%--newValue: $("#new_password").val()--%>
        <%--};--%>

        <%--var url = '../rest/security/change_password';--%>

        <%--var callbackSuccess = function (data) {--%>
            <%--$(btn).prop('disabled', false);--%>
            <%--$(".error_message").html('<p class="green">' + <fmt:message key="accountProfile.Setting.password"/> +'</p>');--%>
        <%--};--%>

        <%--var callbackError = function (error) {--%>
            <%--$(btn).prop('disabled', true);--%>
            <%--$("#error_message #msg").html(error + '<br/><br/>');--%>
        <%--};--%>

        <%--$.postJSON(url, jsonData, callbackSuccess, callbackError);--%>
    <%--};--%>

    <%--var onDeleteSubmit = function (btn) {--%>
        <%--var url = '../rest/security/user_delete';--%>

        <%--var id = window.catcher ? window.catcher.id : window.principal.id;--%>

        <%--var callbackSuccess = function (data) {--%>
            <%--window.location = '../ct/tidings';--%>
        <%--};--%>

        <%--var callbackError = function (error) {--%>
            <%--$(btn).prop('disabled', true);--%>
            <%--$("#error_message #msg").html(error + '<br/><br/>');--%>
        <%--};--%>

        <%--$.postJSON(url, id, callbackSuccess, callbackError);--%>
    <%--};--%>
</script>

<section id="middle">
    <div id="container">
        <div id="contents">


            <article id="article" class="deteil edit">

                <div id="error_message">
                    <span id="msg" class="error_message"></span>
                </div>

                <%----%>
                <%--<div class="new_password form_new_data">--%>
                    <%--<span class="people_title"><fmt:message key="accountProfile.Setting.password"/></span>--%>

                    <%--<form name="auth" action="" method="post">--%>
                        <%--<dl>--%>
                            <%--<dt><label><fmt:message key="user.currentPassword"/></label></dt>--%>
                            <%--<dd><input id="current_password" type="password" name="" value=""></dd>--%>
                        <%--</dl>--%>
                        <%--<dl>--%>
                            <%--<dt><label><fmt:message key="user.newPassword"/></label></dt>--%>
                            <%--<dd><input id="new_password" type="password" name="" value=""></dd>--%>
                        <%--</dl>--%>
                        <%--<dl>--%>
                            <%--<dt><label><fmt:message key="user.newPasswordRepeat"/></label></dt>--%>
                            <%--<dd><input id="new_password_repeat" type="password" name="" value=""></dd>--%>
                        <%--</dl>--%>
                        <%--<dl>--%>
                            <%--<dd><a class="button go" onClick="onChangePasswordSubmit()"><fmt:message--%>
                                    <%--key="page.action.change"/></a></dd>--%>
                        <%--</dl>--%>
                    <%--</form>--%>
                <%--</div>--%>


                <div class="new_password form_new_data">
                    <span class="people_title"><fmt:message key="accountProfile.Setting.email"/></span>

                    <form name="auth" action="" method="post">
                        <dl>
                            <dt><label><fmt:message key="user.currentEmail"/></label></dt>
                            <dd><input id="current_email" type="text" name="" value=""></dd>
                        </dl>
                        <dl>
                            <dt><label><fmt:message key="user.newEmail"/></label></dt>
                            <dd><input id="new_email" type="text" name="" value=""></dd>
                        </dl>
                        <dl>
                            <dt><label><fmt:message key="user.newEmailRepeat"/></label></dt>
                            <dd><input id="new_email_repeat" type="text" name="" value=""></dd>
                        </dl>
                        <dl>
                            <dd><a class="button go" onClick="onChangeEmailSubmit()"><fmt:message
                                    key="page.action.change"/></a></dd>
                        </dl>
                    </form>
                </div>

                <div class="description">
                    <%--<div class="list_user edit list_user_soc">--%>
                    <%--<div class="list_user_inf people_title">Аккаунты</div>--%>
                    <%--<ul><!----%>
                    <%----><li class="participants_li options_fb"><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
                    <%----><li class="participants_li options_vk"><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
                    <%----><li class="participants_li options_od"><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
                    <%----><li class="participants_li options_tv"><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
                    <%----></ul>--%>
                    <%--<ul class="soc_list"><!----%>
                    <%----><li class="fb"></li><!----%>
                    <%----><li class="od"></li><!----%>
                    <%----><li class="tv"></li><!----%>
                    <%----><li class="vk"></li><!----%>
                    <%----></ul>--%>
                    <%--</div>--%>
                </div>
                <div class="options_top">
                    <h2 class="people_title"><fmt:message key="accountProfile.Setting.delete"/></h2>
                </div>
                <div class="left">
                    <a id="delete" class="button" onClick="onDeleteSubmit()"><fmt:message key="page.action.delete"/></a>
                </div>

            </article>
        </div>
    </div>
</section>