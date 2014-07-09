<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script id="ownerTemplate" type="text/x-jsrender">
    <li class="participants_li func_search_people" id="{{>id}}">
        <div class="list_user_img">{{if image}}<img src="../rest/image/small/{{>image.id}}">{{/if}}</div><div class="list_user_name"><a href="../pf/profile?guid={{>id}}">{{>name}}</a></div>
    </li>
</script>

<script id="employeeTemplate" type="text/x-jsrender">
    <li class="participants_li func_search_people" id="{{>id}}">
        <div class="list_user_img">{{if image}}<img src="../rest/image/small/{{>image.id}}">{{/if}}</div><div class="list_user_name"><a href="../pf/profile?guid={{>id}}">{{>name}}</a></div>
    </li>
</script>

<script id="memberTemplate" type="text/x-jsrender">
    <li class="participants_li func_search_people" id="{{>id}}">
        <div class="list_user_img">{{if image}}<img src="../rest/image/small/{{>image.id}}">{{/if}}</div><div class="list_user_name"><a href="../pf/profile?guid={{>id}}">{{>name}}</a></div>
    </li>
</script>

<script id="subscriberTemplate" type="text/x-jsrender">
    <li class="participants_li func_search_people" id="{{>id}}">
        <div class="list_user_img">{{if image}}<img src="../rest/image/small/{{>image.id}}">{{/if}}</div><div class="list_user_name"><a href="../pf/profile?guid={{>id}}">{{>name}}</a></div>
    </li>
</script>

<script type="text/javascript">

$(document).ready(function () {
    $("#people input[name=text_search]").autocomplete({
        deferRequestBy: 200,
        minLength: 0,
        search: function () {
            var term = extractLast(this.value);
            if (term.length == 0)
                $.handParamToURL({term: null});
            if (term.length > 1)
                $.handParamToURL({term: term});
            return false;
        }
    });
});

$(window).bind("onPageReady", function (e, paramsJson) {

    var criteria = {};

    var ownerSuccess = function (data) {
        if(data.users && data.users.length > 0)
            $("#list_user_owner").show();
        else
            $("#list_user_owner").hide();

        $("#owners").html($("#ownerTemplate").render(data.users));
    };

    var employeeSuccess = function (data) {
        if(data.users && data.users.length > 0)
            $("#list_user_employee").show();
        else
            $("#list_user_employee").hide();

        $("#employees").html($("#employeeTemplate").render(data.users));
    };

    var memberSuccess = function (data) {
        if(data.users && data.users.length > 0)
            $("#list_user_member").show();
        else
            $("#list_user_member").hide();

        $("#members").html($("#memberTemplate").render(data.users));
    };

    var subscribersSuccess = function (data) {
        if(data.users && data.users.length > 0)
            $("#list_user_subscriber").show();
        else
            $("#list_user_subscriber").hide();

        $("#subscribers").html($("#subscriberTemplate").render(data.users));

//        if ($('.func_search_people').length) {
//            var commands = Array(
//                    Array('sendRequestFriendship', '_search','request_friendship'),
//                    Array('addPublisher', '_search','add_publisher')
//            );
//            participants_menu('.func_search_people', commands);
//        }
//
//        $('.func_search_people .participants_menu a').click(function (e) {
//            var user = $(this).parents('.participants_li');
//
//            var url = '../rest/security/' + $(this).attr('name');
//
//            var callbackSuccess = function (data) {
//                $(user).remove();
//            };
//
//            var callbackError = function (error) {
//                $("#error_message #msg").text(error);
//            };
//
//            $.postJSON(url, user.attr('id'), callbackSuccess, callbackError);
//        })
    };

    criteria.ownersCriteria = {
        fromId: '1'
    };

    criteria.employeesCriteria = {
        fromId: '1'
    };

    criteria.membersCriteria = {
        fromId: '1'
    };

    criteria.subscribersCriteria = {
        fromId: '1'
    };

    var url = '../rest/find_community_people';

    var callbackSuccess = function (data) {
        if(data.owners){
            ownerSuccess(data.owners);
        }
        if(data.employees){
            employeeSuccess(data.employees);
        }
        if(data.members){
            memberSuccess(data.members);
        }
        if(data.subscribers){
            subscribersSuccess(data.subscribers);
        }
    };

    var callbackError = function (error) {
        $.alert(error);
    };

    $.postJSON(url, criteria, callbackSuccess, callbackError);
});
</script>

<tiles:insertDefinition name="pagination"/>

<div id="container">
    <div id="contents">
        <article id="article" class="deteil edit">
            <div class="description">

                <div id="list_user_search" class="list_user" style="display: none;">
                    <div class="list_user_inf people_title"><fmt:message key="page.action.searchResults"/></div>
                    <ul id="searchPeople"></ul>
                </div>

                <div id="list_user_owner" class="list_user" style="display: none;">
                    <div class="list_user_inf people_title">
                        <c:choose>
                            <c:when test="${not empty catcher}">
                                <fmt:message key="user.owner"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="user.i.owner"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <ul id="owners"></ul>
                </div>

                <div id="list_user_employee" class="list_user" style="display: none;">
                    <div class="list_user_inf people_title">
                        <c:choose>
                            <c:when test="${not empty catcher}">
                                <fmt:message key="user.employee"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="user.i.employee"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <ul id="employees"></ul>
                </div>

                <div id="list_user_member" class="list_user" style="display: none;">
                    <div class="list_user_inf people_title">
                        <c:choose>
                            <c:when test="${not empty catcher}">
                                <fmt:message key="user.member"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="user.i.member"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <ul id="members"></ul>
                </div>

                <div id="list_user_subscriber" class="list_user" style="display: none;">
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
                    <ul id="subscribers"></ul>
                </div>
            </div>
        </article>
    </div>
</div>

<aside id="sideRight" filter="empty">
    <div id="people" class="filtr open">
        <div class="zag"><fmt:message key="page.action.searchPeople"/></div>
        <div class="content">
            <div class="search">
                <input type="text" name="text_search">
                <input type="button" name="go_user_search" value="">
            </div>
        </div>
    </div>
</aside>