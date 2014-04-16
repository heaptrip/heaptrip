<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<script id="userCommunitiesTemplate" type="text/x-jsrender">
    <li class="participants_li community_func_user">
        <div class="list_user_img">{{if image}}<img src="rest/image/small/{{>image.id}}">{{/if}}</div><div class="list_user_name"><a href="pf-community.html?guid={{>id}}">{{>name}}</a></div>
    </li>
</script>

<script id="workingCommunitiesTemplate" type="text/x-jsrender">
    <li class="participants_li community_func_working">
        <div class="list_user_img">{{if image}}<img src="rest/image/small/{{>image.id}}">{{/if}}</div><div class="list_user_name"><a href="pf-community.html?guid={{>id}}">{{>name}}</a></div>
    </li>
</script>

<script id="memberCommunitiesTemplate" type="text/x-jsrender">
    <li class="participants_li community_func_member">
        <div class="list_user_img">{{if image}}<img src="rest/image/small/{{>image.id}}">{{/if}}</div><div class="list_user_name"><a href="pf-community.html?guid={{>id}}">{{>name}}</a></div>
    </li>
</script>

<script id="subscriberCommunitiesTemplate" type="text/x-jsrender">
    <li class="participants_li community_func_subscriber">
        <div class="list_user_img">{{if image}}<img src="rest/image/small/{{>image.id}}">{{/if}}</div><div class="list_user_name"><a href="pf-community.html?guid={{>id}}">{{>name}}</a></div>
    </li>
</script>

<script id="searchCommunitiesTemplate" type="text/x-jsrender">
    <li class="participants_li community_func_search">
        <div class="list_user_img">{{if image}}<img src="rest/image/small/{{>image.id}}">{{/if}}</div><div class="list_user_name"><a href="pf-community.html?guid={{>id}}">{{>name}}</a></div>
    </li>
</script>


<script type="text/javascript">

$(document).ready(function () {

    $("#community input[name=text_search]")

    .bind("keydown",function (event) {
        if (event.keyCode === $.ui.keyCode.TAB && $(this).data("ui-autocomplete").menu.active) {
            event.preventDefault();
        }
    })
    .autocomplete({
        source: function (request, response) {

            $.handParamToURL({term:request.term});


        }       ,
        search: function () {
            var term = extractLast(this.value);
            if (term.length < 3) {
                return false;
            }
        },
        focus: function () {
         return false;
        },
        select: function (event, ui) {
            console.log(ui.item.value);
            $("#community input[name=text_search]").val('');
        return false;
        }
    });

});


$(window).bind("onPageReady", function (e, paramsJson) {

           if(paramsJson.term)
    alert('term' +  paramsJson.term )

    getCommunitiesList(paramsJson);
    getEmployerList(paramsJson);
    getMemberList(paramsJson);
    getPublisherList(paramsJson);
    getSearchList(paramsJson);
});

var getCommunitiesList = function (paramsJson) {




    var url = 'rest/communities';

    var communitiesCriteria = {

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

    var callbackSuccess = function (data) {

        $("#user_communities").html($("#userCommunitiesTemplate").render(data.accounts));

        if ($('.community_func_user').length) {
            var commands = Array(Array('Close', '_user'));
            participants_menu('.community_func_user', commands);
        }

        $('.community_func_user .participants_menu a').click(function (e) {
            var community = $(this).parents('.participants_li');
            alert('community_func_user_call');
            $(community).remove();

        });

        /*$('#paginator1').smartpaginator({
         totalrecords: 100,
         skip: paramsJson.skip
         }); */
    };

    var callbackError = function (error) {
        alert(error);
    };

    $.postJSON(url, communitiesCriteria, callbackSuccess, callbackError);

};

var getEmployerList = function (paramsJson) {
    var url = 'rest/communities';

    var employersCriteria = {

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

    var callbackSuccess = function (data) {

        $("#working_communities").html($("#workingCommunitiesTemplate").render(data.accounts));

        if ($('.community_func_working').length) {
            var commands = Array(Array('Resign', '_working'));
            participants_menu('.community_func_working', commands);
        }

        $('.community_func_working .participants_menu a').click(function (e) {
            var community = $(this).parents('.participants_li');
            alert('community_func_working_call');
            $(community).remove();

        });

    };

    var callbackError = function (error) {
        alert(error);
    };

    $.postJSON(url, employersCriteria, callbackSuccess, callbackError);
};

var getMemberList = function (paramsJson) {
    var url = 'rest/communities';

    var membersCriteria = {

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

    var callbackSuccess = function (data) {
        $("#member_communities").html($("#memberCommunitiesTemplate").render(data.accounts));

        if ($('.community_func_member').length) {
            var commands = Array(Array('Exit', '_member'));
            participants_menu('.community_func_member', commands);
        }

        $('.community_func_member .participants_menu a').click(function (e) {
            var community = $(this).parents('.participants_li');
            alert('community_func_member_call');
            $(community).remove();

        });

    };

    var callbackError = function (error) {
        alert(error);
    };

    $.postJSON(url, membersCriteria, callbackSuccess, callbackError);
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
        $("#subscriber_communities").html($("#subscriberCommunitiesTemplate").render(data.accounts));


        if ($('.community_func_subscriber').length) {
            var commands = Array(Array('Unsubscribe', '_subscriber'));
            participants_menu('.community_func_subscriber', commands);
        }

        $('.community_func_subscriber .participants_menu a').click(function (e) {
            var community = $(this).parents('.participants_li');
            alert('community_func_subscriber_call');
            $(community).remove();

        })

    };

    var callbackError = function (error) {
        alert(error);
    };

    $.postJSON(url, publishersCriteria, callbackSuccess, callbackError);
};

var getSearchList = function (paramsJson) {
    var url = 'rest/communities';

    var publishersCriteria = {

        /*publishers: {
            checkMode: 'IN',
            ids: [window.catcher ? window.catcher.id : window.principal.id]
        }, */
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
        $("#search_communities").html($("#searchCommunitiesTemplate").render(data.accounts));


        if ($('.community_func_search').length) {
            var commands = Array(Array('todo', '_search'));
            participants_menu('.community_func_search', commands);
        }

        $('.community_func_search .participants_menu a').click(function (e) {
            var community = $(this).parents('.participants_li');
            alert('community_func_search_call');
            $(community).remove();

        })

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

            <c:if test='${not empty principal && empty catcher}'>
                <div class="inf">
                    <div class="right">
                        <a href="<c:url value="/community_modify_info.html"/>" class="button"><fmt:message
                                key="page.action.create"/></a>
                    </div>
                </div>
            </c:if>

            <div class="description">

                <div id="list_user_1" class="community" >
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

                <div id="list_user_2" class="community" >
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
                <div id="list_user_3" class="community" >
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

                <div id="list_user_4" class="community">
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

                <div id="list_user_5" class="community">
                    <div class="list_user_inf people_title">
                        SEARCH
                    </div>
                    <ul id="search_communities"></ul>
                </div>

            </div>
        </article>
    </div>
    <!-- #content-->
</div>
<!-- #container-->

<aside id="sideRight">
    <tiles:insertDefinition name="categoryTree"/>
    <tiles:insertDefinition name="regionFilter"/>
    <div id="community" class="filtr open">
        <div class="zag">Community search</div>
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


</aside>
<!-- #sideRight -->




