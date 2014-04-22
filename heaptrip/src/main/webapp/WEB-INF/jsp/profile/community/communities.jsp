<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script id="userCommunitiesTemplate" type="text/x-jsrender">
    <li class="participants_li community_func_user" id="{{>id}}">
        <div class="list_user_img">{{if image}}<img src="rest/image/small/{{>image.id}}">{{/if}}</div><div class="list_user_name"><a href="pf-community.html?guid={{>id}}">{{>name}}</a></div>
    </li>
</script>

<script id="workingCommunitiesTemplate" type="text/x-jsrender">
    <li class="participants_li community_func_working" id="{{>id}}">
        <div class="list_user_img">{{if image}}<img src="rest/image/small/{{>image.id}}">{{/if}}</div><div class="list_user_name"><a href="pf-community.html?guid={{>id}}">{{>name}}</a></div>
    </li>
</script>

<script id="memberCommunitiesTemplate" type="text/x-jsrender">
    <li class="participants_li community_func_member" id="{{>id}}">
        <div class="list_user_img">{{if image}}<img src="rest/image/small/{{>image.id}}">{{/if}}</div><div class="list_user_name"><a href="pf-community.html?guid={{>id}}">{{>name}}</a></div>
    </li>
</script>

<script id="subscriberCommunitiesTemplate" type="text/x-jsrender">
    <li class="participants_li community_func_subscriber" id="{{>id}}">
        <div class="list_user_img">{{if image}}<img src="rest/image/small/{{>image.id}}">{{/if}}</div><div class="list_user_name"><a href="pf-community.html?guid={{>id}}">{{>name}}</a></div>
    </li>
</script>

<script id="searchCommunitiesTemplate" type="text/x-jsrender">
    <li class="participants_li community_func_search" id="{{>id}}">
        <div class="list_user_img">{{if image}}<img src="rest/image/small/{{>image.id}}">{{/if}}</div><div class="list_user_name"><a href="pf-community.html?guid={{>id}}">{{>name}}</a></div>
    </li>
</script>


<script type="text/javascript">

$(document).ready(function () {

    $("#community input[name=text_search]").autocomplete({
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

    var userCommunitiesSuccess = function (data) {
        if(data.communities && data.communities.length > 0)
            $("#list_user_1").show();
        else
            $("#list_user_1").hide();

        $("#user_communities").html($("#userCommunitiesTemplate").render(data.communities));

        if ($('.community_func_user').length) {
            var commands = Array(Array('Close', '_user','close_item'));
            participants_menu('.community_func_user', commands);
        }

        $('.community_func_user .participants_menu a').click(function (e) {
            var community = $(this).parents('.participants_li');

            var url = 'rest/security/refusal_of_community';

            var callbackSuccess = function (data) {
                $(community).remove();
            };

            var callbackError = function (error) {
                $("#error_message #msg").text(error);
            };

            $.postJSON(url, community.attr('id'), callbackSuccess, callbackError);
        });

    };

    var employerCommunitiesSuccess = function (data) {

        if(data.communities && data.communities.length > 0)
            $("#list_user_2").show();
        else
            $("#list_user_2").hide();

        $("#working_communities").html($("#workingCommunitiesTemplate").render(data.communities));

        if ($('.community_func_working').length) {
            var commands = Array(Array('Resign', '_working',"resign_item"));
            participants_menu('.community_func_working', commands);
        }

        $('.community_func_working .participants_menu a').click(function (e) {
            var community = $(this).parents('.participants_li');

            var url = 'rest/security/resign_from_community';

            var callbackSuccess = function (data) {
                $(community).remove();
            };

            var callbackError = function (error) {
                $("#error_message #msg").text(error);
            };

            $.postJSON(url, community.attr('id'), callbackSuccess, callbackError);
        });

    };

    var memberCommunitiesSuccess = function (data) {

        if(data.communities && data.communities.length > 0)
            $("#list_user_3").show();
        else
            $("#list_user_3").hide();

        $("#member_communities").html($("#memberCommunitiesTemplate").render(data.communities));
        if ($('.community_func_member').length) {
            var commands = Array(Array('Exit', '_member','exit_item'));
            participants_menu('.community_func_member', commands);
        }
        $('.community_func_member .participants_menu a').click(function (e) {
            var community = $(this).parents('.participants_li');

            var url = 'rest/security/out_of_community';

            var callbackSuccess = function (data) {
                $(community).remove();
            };

            var callbackError = function (error) {
                $("#error_message #msg").text(error);
            };

            $.postJSON(url, community.attr('id'), callbackSuccess, callbackError);
        });

    };

    var publisherCommunitiesSuccess = function (data) {

        if(data.communities && data.communities.length > 0)
            $("#list_user_4").show();
        else
            $("#list_user_4").hide();

        $("#subscriber_communities").html($("#subscriberCommunitiesTemplate").render(data.communities));

        if ($('.community_func_subscriber').length) {
            var commands = Array(Array('Unsubscribe', '_subscriber','unsubscribe_item'));
            participants_menu('.community_func_subscriber', commands);
        }

        $('.community_func_subscriber .participants_menu a').click(function (e) {
            var community = $(this).parents('.participants_li');

            var url = 'rest/security/unsubscribe_from_community';

            var callbackSuccess = function (data) {
                $(community).remove();
            };

            var callbackError = function (error) {
                $("#error_message #msg").text(error);
            };

            $.postJSON(url, community.attr('id'), callbackSuccess, callbackError);
        })

    };

    var  searchCommunitiesSuccess = function (data) {

        $("#list_user_5").show();

        $("#search_communities").html($("#searchCommunitiesTemplate").render(data.communities));

        if ($('.community_func_search').length) {
            var commands = Array(
                    Array('sendRequestOwner', '_search','request_owner'),
                    Array('sendRequestEmployee', '_search','request_employee'),
                    Array('sendRequestMember', '_search','request_member'),
                    Array('sendRequestSubscriber', '_search','request_subscriber')
            );
            participants_menu('.community_func_search', commands);
        }

        $('.community_func_search .participants_menu a').click(function (e) {
            var community = $(this).parents('.participants_li');

            var jsonData = {
                id: community.attr('id'),
                request: $(this).attr('name')
            };

            var url = 'rest/security/send_request_community';

            var callbackSuccess = function (data) {
                $(community).remove();
            };

            var callbackError = function (error) {
                $("#error_message #msg").text(error);
            };

            $.postJSON(url, jsonData, callbackSuccess, callbackError);
        })

    };

    criteria.userCommunitiesCriteria = {

        query: paramsJson.term,
        accountType: {
            checkMode: 'NOT_IN',
            ids: ['com.heaptrip.domain.entity.account.user.User']

        },
        owners: {
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

    criteria.employerCommunitiesCriteria = {

        query: paramsJson.term,
        accountType: {
            checkMode: 'NOT_IN',
            ids: ['com.heaptrip.domain.entity.account.user.User']

        },
        staff: {
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

    criteria.memberCommunitiesCriteria = {

        query: paramsJson.term,
        accountType: {
            checkMode: 'NOT_IN',
            ids: ['com.heaptrip.domain.entity.account.user.User']

        },
        members: {
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

    criteria.publisherCommunitiesCriteria = {

        query: paramsJson.term,
        accountType: {
            checkMode: 'NOT_IN',
            ids: ['com.heaptrip.domain.entity.account.user.User']

        },
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


    if (paramsJson.term || paramsJson.ct || paramsJson.rg) {
        criteria.searchCommunitiesCriteria = {
            query: paramsJson.term,
            categories: {
                checkMode: "IN",
                ids: paramsJson.ct ? paramsJson.ct.split(',') : null
            },
            regions: {
                checkMode: "IN",
                ids: paramsJson.rg ? paramsJson.rg.split(',') : null
            },
            owners: {
                checkMode: 'NOT_IN',
                ids: [window.catcher ? window.catcher.id : window.principal.id]
            },
            staff: {
                checkMode: 'NOT_IN',
                ids: [window.catcher ? window.catcher.id : window.principal.id]
            },
            members: {
                checkMode: 'NOT_IN',
                ids: [window.catcher ? window.catcher.id : window.principal.id]
            },
            publishers: {
                checkMode: 'NOT_IN',
                ids: [window.catcher ? window.catcher.id : window.principal.id]
            }
        };
    }else{
        $("#list_user_5").hide();
    }


    var url = 'rest/communities';

    var callbackSuccess = function (data) {
        if(data.userCommunities){
            userCommunitiesSuccess(data.userCommunities);
        }
        if(data.employerCommunities){
            employerCommunitiesSuccess(data.employerCommunities);
        }
        if(data.memberCommunities){
            memberCommunitiesSuccess(data.memberCommunities);
        }
        if(data.publisherCommunities){
            publisherCommunitiesSuccess(data.publisherCommunities)
        }
        if(data.searchCommunities){
            searchCommunitiesSuccess(data.searchCommunities)
        }
    };

    var callbackError = function (error) {
        alert(error);
    };

    $.postJSON(url, criteria, callbackSuccess, callbackError);
});


</script>

<tiles:insertDefinition name="pagination"/>

<div id="container">
    <div id="contents">

        <article id="article" class="deteil edit">

            <c:if test='${not empty principal && empty catcher}'>
                <div class="inf">
                    <div class="right">
                        <a href="<c:url value="/community_modify_info.html"/>" class="button"><fmt:message
                                key="page.action.create"/></a>
                    </div>
                </div>
            </c:if>

            <div class="description">

                <div id="list_user_5" class="community" style="display: none;">
                    <div class="list_user_inf people_title"><fmt:message key="page.action.searchResults"/></div>
                    <ul id="search_communities"></ul>
                </div>

                <div id="list_user_1" class="community" style="display: none;">
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
                    <ul id="user_communities"></ul>
                </div>

                <div id="list_user_2" class="community" style="display: none;">
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
                    <ul id="working_communities"></ul>
                </div>
                <div id="list_user_3" class="community" style="display: none;">
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
                    <ul id="member_communities"></ul>
                </div>

                <div id="list_user_4" class="community" style="display: none;">
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
                    <ul id="subscriber_communities"></ul>
                </div>


            </div>
        </article>
    </div>
    <!-- #content-->
</div>
<!-- #container-->

<aside id="sideRight" filter="empty">
    <div id="community" class="filtr open">
        <div class="zag"><fmt:message key="page.action.searchCommunity"/></div>
        <div class="content">
            <ul>
                <li><input type="checkbox"><label><fmt:message key="account.type.club"/></label></li>
                <li><input type="checkbox"><label><fmt:message key="account.type.company"/></label></li>
                <li><input type="checkbox"><label><fmt:message key="account.type.agency"/></label></li>
            </ul>
            <div class="search">
                <input type="text" name="text_search">
                <input type="button" name="go_user_search" value="">
            </div>
        </div>
    </div>
    <tiles:insertDefinition name="categoryTreeWithBtn"/>
    <tiles:insertDefinition name="regionFilterWithBtn"/>
</aside>
<!-- #sideRight -->




