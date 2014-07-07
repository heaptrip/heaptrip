<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<script>

    var onSendRequestFriendshipBtnClick = function (e) {
        var url = '../rest/security/request_friendship';
        var callbackSuccess = function (data) {
            $(e).remove();
        };
        var callbackError = function (error) {
            $alert(error);
        };
        $.postJSON(url, window.catcher.id, callbackSuccess, callbackError);
    };

    var onSendRefusalFriendshipBtnClick = function (e) {
        var url = '../rest/security/refusal_of_friendship';
        var callbackSuccess = function (data) {
            $(e).remove();
        };
        var callbackError = function (error) {
            $alert(error);
        };
        $.postJSON(url, window.catcher.id, callbackSuccess, callbackError);
    };

    var onAddPublisherBtnClick = function (e) {
        var url = '../rest/security/add_publisher';
        var callbackSuccess = function (data) {
            $(e).remove();
        };
        var callbackError = function (error) {
            $alert(error);
        };
        $.postJSON(url, window.catcher.id, callbackSuccess, callbackError);
    };

    var onSendUnsubscribeFromPublisherBtnClick = function (e) {
        var url = '../rest/security/unsubscribe_from_publisher';
        var callbackSuccess = function (data) {
            $(e).remove();
        };
        var callbackError = function (error) {
            $alert(error);
        };
        $.postJSON(url, window.catcher.id, callbackSuccess, callbackError);
    };

</script>


<div id="container">
    <div id="contents">

        <article id="article" class="deteil">
            <div class="inf">
                <div class="left">
                    <h2 class="people_title"><fmt:message key="accountProfile.title"/></h2>
                </div>

                <c:if test='${not empty principal && empty catcher}'>
                    <div class="right">
                        <a href="<c:url value="../pf/profile_modify_info?guid=${param.guid}"/>"
                           class="button"><fmt:message
                                key="page.action.edit"/></a>
                    </div>
                </c:if>

                <div class="accountProfile">
                    <div class="my_avatar"><img src="<c:url value="../rest/image/medium/${account.image.id}"/>">
                        <c:choose>
                            <c:when test="${not empty principal && empty catcher}">
                                <a id="my_avatar_btn" class="button"><fmt:message key="page.action.uploadPhoto"/></a>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${not empty principal && not empty catcher}">
                                    <div>
                                        <a class="button" onclick="onSendRequestFriendshipBtnClick(this)"> <fmt:message
                                                key="people.menu.SendRequestFriendship"/></a>
                                        <a class="button" onclick="onAddPublisherBtnClick(this)"><fmt:message
                                                key="people.menu.AddPublisher"/></a>
                                    </div>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="my_inf">
                        <div class="my_name">${account.name}<span>(${account.rating.value})</span></div>
                        <table border="0">
                            <tr>
                                <td valign="top">
                                    <div class="my_location"><span><fmt:message key="user.place"/>: </span></div>
                                </td>
                                <td valign="top">
                                    <div class="my_location">${account.accountProfile.location.data}</div>
                                </td>
                            </tr>
                            <tr>
                                <td valign="top">
                                    <div class="my_date"><span><fmt:message key="user.birthday"/>:</span></div>
                                </td>
                                <td valign="top">
                                    <div class="my_date">${account.userProfile.birthday.text}</div>
                                </td>
                            </tr>
                        </table>

                        <div class="my_lang">

                            <table>
                                <tr>
                                    <td valign="center"><fmt:message key="user.languages"/>:</td>
                                    <td valign="center">
                                        <ul>
                                            <c:forEach items="${account.accountProfile.langs}" var="lang"
                                                       varStatus="stat">
                                                <li class="${lang}"><fmt:message key="locale.${lang}"/></li>
                                            </c:forEach>
                                        </ul>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test='${not empty account.accountProfile.desc}'>
                <div class="description">
                    <h2 class="people_title"><fmt:message key="user.aboutMe"/>:</h2>
                    <br>
                        ${account.accountProfile.desc}
                </div>
            </c:if>
            <c:if test='${fn:length(account.userProfile.knowledgies) > 0}'>
                <div class="table_inf">
                    <h2 class="people_title"><fmt:message key="user.knowledge"/>:</h2>
                    <table>
                        <thead>
                        <tr>
                            <th><fmt:message key="page.date.period"/></th>
                            <th><fmt:message key="user.specialty"/></th>
                            <th><fmt:message key="user.specialty.placeOf"/></th>
                            <th><fmt:message key="user.specialty.document"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${account.userProfile.knowledgies}" var="knowledge">
                            <tr>
                                <td>
                                    <fmt:message key="page.date.from"/> ${knowledge.begin.text}
                                    <c:if test='${not empty knowledge.end.text}'>
                                        <br/>
                                        <fmt:message key="page.date.to"/> ${knowledge.end.text}
                                    </c:if>
                                </td>
                                <td>${knowledge.specialist}</td>
                                <td>${knowledge.location}</td>
                                <td>${knowledge.document}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
            <c:if test='${fn:length(account.userProfile.practices) > 0}'>
                <div class="table_inf">
                    <h2 class="people_title"><fmt:message key="user.experience"/>:</h2>
                    <table class="experience">
                        <thead>
                        <tr>
                            <th><fmt:message key="page.date.period"/></th>
                            <th><fmt:message key="content.description"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${account.userProfile.practices}" var="practice">
                            <tr>
                                <td>
                                    <fmt:message key="page.date.from"/> ${practice.begin.text}
                                    <c:if test='${not empty practice.end.text}'>
                                        <br/>
                                        <fmt:message key="page.date.to"/> ${practice.end.text}
                                    </c:if>
                                </td>
                                <td>${practice.desc}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </article>
    </div>
    <!-- #content-->
</div>
<!-- #container-->

<aside id="sideRight" filter="read_only">
    <tiles:insertDefinition name="categoryTree"/>
    <tiles:insertDefinition name="regionFilter"/>
</aside>
<!-- #sideRight -->




