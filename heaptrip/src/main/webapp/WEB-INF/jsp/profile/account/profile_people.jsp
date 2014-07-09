<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script id="friendTemplate" type="text/x-jsrender">
    <li class="participants_li func_refusal_friendship" id="{{>id}}">
        <div class="list_user_img">{{if image}}<img src="../rest/image/small/{{>image.id}}">{{/if}}</div><div class="list_user_name"><a href="../pf/profile?guid={{>id}}">{{>name}}</a></div>
    </li>
</script>

<script id="publisherTemplate" type="text/x-jsrender">
    <li class="participants_li func_unsubscribe_publisher" id="{{>id}}">
        <div class="list_user_img">{{if image}}<img src="../rest/image/small/{{>image.id}}">{{/if}}</div><div class="list_user_name"><a href="../pf/profile?guid={{>id}}">{{>name}}</a></div>
    </li>
</script>

<script id="searchPeopleTemplate" type="text/x-jsrender">
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

        var friendSuccess = function (data) {
            if(data.users && data.users.length > 0)
                $("#list_user_1").show();
            else
                $("#list_user_1").hide();

            $("#friends").html($("#friendTemplate").render(data.users));

            if ($('.func_refusal_friendship').length) {
                var commands = Array(Array(locale.people.menu.RefusalFriendship, '_user','refusal_friendship'));
                participants_menu('.func_refusal_friendship', commands);
            }

            $('.func_refusal_friendship .participants_menu a').click(function (e) {
                var user = $(this).parents('.participants_li');

                var url = '../rest/security/refusal_of_friendship';

                var callbackSuccess = function (data) {
                    $(user).remove();
                };

                var callbackError = function (error) {
                    $("#error_message #msg").text(error);
                };

                $.postJSON(url, user.attr('id'), callbackSuccess, callbackError);
            });

        };

        var publisherSuccess = function (data) {
            if(data.users && data.users.length > 0)
                $("#list_user_2").show();
            else
                $("#list_user_2").hide();

            $("#publishers").html($("#publisherTemplate").render(data.users));

            if ($('.func_unsubscribe_publisher').length) {
                var commands = Array(Array(locale.people.menu.Unsubscribe, '_user','unsubscribe_from_publisher'));
                participants_menu('.func_unsubscribe_publisher', commands);
            }

            $('.func_unsubscribe_publisher .participants_menu a').click(function (e) {
                var user = $(this).parents('.participants_li');

                var url = '../rest/security/unsubscribe_from_publisher';

                var callbackSuccess = function (data) {
                    $(user).remove();
                };

                var callbackError = function (error) {
                    $("#error_message #msg").text(error);
                };

                $.postJSON(url, user.attr('id'), callbackSuccess, callbackError);
            });

        };

        var  searchPeopleSuccess = function (data) {

            $("#list_user_5").show();

            $("#searchPeople").html($("#searchPeopleTemplate").render(data.users));

            if ($('.func_search_people').length) {
                var commands = Array(
                        Array(locale.people.menu.SendRequestFriendship, '_search','request_friendship'),
                        Array(locale.people.menu.AddPublisher, '_search','add_publisher')
                );
                participants_menu('.func_search_people', commands);
            }

            $('.func_search_people .participants_menu a').click(function (e) {
                var user = $(this).parents('.participants_li');

                var url = '../rest/security/' + $(this).attr('name');

                var callbackSuccess = function (data) {
                    $(user).remove();
                };

                var callbackError = function (error) {
                    $("#error_message #msg").text(error);
                };

                $.postJSON(url, user.attr('id'), callbackSuccess, callbackError);
            })

        };

        criteria.friendsCriteria = {

            query: paramsJson.term,
            accountType: {
                checkMode: 'IN',
                ids: ['com.heaptrip.domain.entity.account.user.User']

            },
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

        criteria.publishersCriteria = {

            query: paramsJson.term,
            accountType: {
                checkMode: 'IN',
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
            criteria.searchPeopleCriteria = {
                query: paramsJson.term,
                accountType: {
                    checkMode: 'IN',
                    ids: ['com.heaptrip.domain.entity.account.user.User']

                },
                categories: {
                    checkMode: "IN",
                    ids: paramsJson.ct ? paramsJson.ct.split(',') : null
                },
                regions: {
                    checkMode: "IN",
                    ids: paramsJson.rg ? paramsJson.rg.split(',') : null
                },
                friends: {
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

        var url = '../rest/find_people';

        var callbackSuccess = function (data) {
            if(data.userFriends){
                friendSuccess(data.userFriends);
            }
            if(data.userPublishers){
                publisherSuccess(data.userPublishers);
            }
            if(data.searchPeople){
                searchPeopleSuccess(data.searchPeople)
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

                <div id="list_user_5" class="list_user" style="display: none;">
                    <div class="list_user_inf people_title"><fmt:message key="page.action.searchResults"/></div>
                    <ul id="searchPeople"></ul>
                </div>

                <div id="list_user_1" class="list_user" style="display: none;">
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
                    <ul id="friends"></ul>
                </div>

                <div id="list_user_2" class="list_user" style="display: none;">
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
                    <ul id="publishers"></ul>
                </div>
            </div>
        </article>
    </div>
    <!-- #content-->
</div>
<!-- #container-->

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
    <tiles:insertDefinition name="categoryTree"/>
    <tiles:insertDefinition name="regionFilter"/>
</aside>
<!-- #sideRight -->
