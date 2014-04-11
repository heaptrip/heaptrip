<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<script id="peopleTemplate" type="text/x-jsrender">
    <ul>
        <li class="participants_li people_func">
            <div class="list_user_img">
                {{if image}}
                <img src="rest/image/small/{{>image.id}}">
                {{/if}}
            </div>
            <div class="list_user_name"><a href="pf-people.html?guid={{>id}}">{{>name}}</a></div>
        </li>
    </ul>
</script>


<script type="text/javascript">

    $(window).bind("onPageReady", function (e, paramsJson) {
        getFriendList(paramsJson);
        getPublisherList(paramsJson);
    });

    var getFriendList = function (paramsJson) {
        var url = 'rest/communities';

        var friendsCriteria = {

            friends: {
                checkMode: 'IN',
                ids: [window.catcher ? window.catcher.id : window.principal.id]
            },
            categories: {
                checkMode: "IN",
                ids: paramsJson.ct ? paramsJson.ct.split(',') : null
            },
            regions: {
                checkMode: "IN",
                ids: paramsJson.rg ? paramsJson.rg.split(',') : null
            }
        };

        var callbackSuccess = function (data) {
            $("#myFriends").html($("#peopleTemplate").render(data.accounts));
        };

        var callbackError = function (error) {
            alert(error);
        };

        $.postJSON(url, friendsCriteria, callbackSuccess, callbackError);
    };

    var getPublisherList = function (paramsJson) {
        var url = 'rest/communities';

        var publishersCriteria = {

            publishers: {
                checkMode: 'IN',
                ids: [window.catcher ? window.catcher.id : window.principal.id]
            },
            categories: {
                checkMode: "IN",
                ids: paramsJson.ct ? paramsJson.ct.split(',') : null
            },
            regions: {
                checkMode: "IN",
                ids: paramsJson.rg ? paramsJson.rg.split(',') : null
            }
        };

        var callbackSuccess = function (data) {
            $("#myPublishers").html($("#peopleTemplate").render(data.accounts));
        };

        var callbackError = function (error) {
            alert(error);
        };

        $.postJSON(url, publishersCriteria, callbackSuccess, callbackError);
    };

</script>

<tiles:insertDefinition name="pagination"/>

<div id="container">
    <div id="contents">

        <article id="article" class="deteil edit">

            <%--<c:if test='${not empty principal && empty catcher}'>--%>
                <%--<div class="inf">--%>
                    <%--<div class="right">--%>
                        <%--<a href="<c:url value="/community_modify_info.html"/>" class="button"><fmt:message--%>
                                <%--key="page.action.add"/></a>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</c:if>--%>

            <div class="description">
                <div id="list_user_1" class="list_user edit" style="border-bottom: 1px solid #E2E6E5;">
                    <div class="list_user_inf people_title">
                        <c:choose>
                            <c:when test="${not empty catcher}">
                                <fmt:message key="user.friends"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="user.i.friends"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <span id="myFriends"></span>
                </div>

                <div id="list_user_2" class="list_user" style="border-bottom: 1px solid #E2E6E5;">
                    <div class="list_user_inf people_title">
                        <c:choose>
                            <c:when test="${not empty catcher}">
                                <fmt:message key="user.subscriber"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="user.i.subscriber"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <span id="myPublishers"></span>
                </div>
            </div>
        </article>
    </div>
</div>

<aside id="sideRight">
    <tiles:insertDefinition name="categoryTree"/>
    <tiles:insertDefinition name="regionFilter"/>
</aside>




