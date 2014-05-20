<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertDefinition name="pagination"/>


<script id="scheduleParticipantsTemplate" type="text/x-jsrender">
    <div id="{{>schedule.id}}" class="list_user">
        <div class="list_user_inf"><span class="list_user_date">{{>schedule.begin.text}} - {{>schedule.end.text}}</span>todo
            status
        </div>

        {{if participants.length > 0 }}
        <ul>
            {{for participants}}
            <%--<li><div class="list_user_img"><img src="rest/image/small/{{>account.image.id}}"></div>--%>
            <%--<div class="participants_li func_manage_people"  >--%>
            <%--<a href="pf-profile.html?guid={{>account.id}}">{{>account.name}}</a>--%>

            <%--<span>({{>status}})</span></div></li>--%>

            <li
            {{if isOrganizer}}
            class="participants_li func_organizer_manage_people"
            {{else}}
            class="participants_li func_manage_people"
            {{/if}}
            memberId="{{>memberId}}"
            userId="{{>account.id}}"
            >
            <div class=" list_user_img">{{if account.image}}<img src="rest/image/small/{{>account.image.id}}">{{/if}}
            </div>
            <div class="list_user_name"><a href="pf-profile.html?guid={{>account.id}}">{{>account.name}}
                {{if isOrganizer}} ({{:~locale.participant.status.organizer}}) {{/if}} </a></div>
            <span style="font-size:10px">{{:~locale.participant.status[status]}}</span>

            </li>


            {{/for}}
        </ul>
        {{/if}}

        <div class="list_user_button"><input type="button" class="button" value="ADD"></div>
    </div>
</script>


<script id="searchPeopleTemplate" type="text/x-jsrender">
    <li class="participants_li func_search_people" id="{{>id}}">
        <div class="list_user_img">{{if image}}<img src="rest/image/small/{{>image.id}}">{{/if}}</div>
        <div class="list_user_name"><a href="pf-profile.html?guid={{>id}}">{{>name}}</a></div>
    </li>
</script>


<script type="text/javascript">
$(document).ready(function () {
    $(".posts_find input[type=text]").autocomplete({
        deferRequestBy: 200,
        minLength: 0,
        search: function () {
            var term = extractLast(this.value);
            if (term.length == 0)
                searchPeople({term: null});
            if (term.length > 1)
                searchPeople({term: term});
            return false;
        }
    });
});


var searchPeople = function (paramsJson) {


    var renderPeople = function () {
        if ($('.func_search_people').length) {
            var commands = Array(
                    Array(locale.menu.addTripParticipant, '_search', 'send_invite_trip_participant')
            );
            participants_menu('.func_search_people', commands);
        }

        $('.func_search_people .participants_menu a').click(function (e) {
            var user = $(this).parents('.participants_li');

            var url = 'rest/security/trip/' + $(this).attr('name');

            var params = {};
            params.userId = user.attr('id');
            params.tripId = $.getParamFromURL().id;
            // TODO : voronenko not first element, do search
            params.scheduleId = $('.list_user')[0].id;

            var callbackSuccess = function (data) {
                //$(user).remove();
                // TODO : voronenko refresh
                window.location = window.location.href
            };

            var callbackError = function (error) {
                $("#error_message #msg").text(error);
            };

            $.postJSON(url, params, callbackSuccess, callbackError);
        })
    }


    var criteria = {};

    var friendSuccess = function (data) {
        if (data.users && data.users.length > 0)
            $("#list_user_1").show();
        else
            $("#list_user_1").hide();

        $("#friends").html($("#searchPeopleTemplate").render(data.users));

        renderPeople();
    };

    var publisherSuccess = function (data) {
        if (data.users && data.users.length > 0)
            $("#list_user_2").show();
        else
            $("#list_user_2").hide();

        $("#publishers").html($("#searchPeopleTemplate").render(data.users));
        renderPeople();


    };

    var searchPeopleSuccess = function (data) {

        $("#list_user_5").show();

        $("#searchPeople").html($("#searchPeopleTemplate").render(data.users));

        renderPeople();

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
        }
    };

    if (paramsJson.term) {
        criteria.searchPeopleCriteria = {
            query: paramsJson.term,
            accountType: {
                checkMode: 'IN',
                ids: ['com.heaptrip.domain.entity.account.user.User']

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
    } else {
        $("#list_user_5").hide();
    }

    var url = 'rest/people';

    var callbackSuccess = function (data) {
        if (data.userFriends) {
            friendSuccess(data.userFriends);
        }
        if (data.userPublishers) {
            publisherSuccess(data.userPublishers);
        }
        if (data.searchPeople) {
            searchPeopleSuccess(data.searchPeople)
        }
    };

    var callbackError = function (error) {
        alert(error);
    };

    $.postJSON(url, criteria, callbackSuccess, callbackError);
}


$(window).bind("onPageReady", function (e, paramsJson) {
    getTripScheduleParticipants(paramsJson);
});

var getTripScheduleParticipants = function (paramsJson) {


    var renderScheduleParticipants = function () {

        var setTripParticipantOrganizer = function (e, isOrganizer) {
            var participant = $(this).parents('.participants_li');
            var url = 'rest/security/trip/' + $(this).attr('name');
            var params = {};
            params.memberId = participant.attr('memberId');
            params.isOrganizer = "" + isOrganizer;
            var callbackSuccess = function (data) {
                // TODO : voronenko refresh
                window.location = window.location.href
            };
            var callbackError = function (error) {
                $("#error_message #msg").text(error);
            };

            $.postJSON(url, params, callbackSuccess, callbackError);
        }


        if ($('.func_organizer_manage_people').length) {
            var commands = Array(
                    Array(locale.menu.cancelTripOrganazer, 'cancelParticipantOrganizer', 'set_trip_participant_organizer')
            );
            participants_menu('.func_organizer_manage_people', commands);
        }

        if ($('.func_manage_people').length) {
            var commands = Array(
                    Array(locale.menu.setTripOrganazer, 'setParticipantOrganizer', 'set_trip_participant_organizer')
            );
            participants_menu('.func_manage_people', commands);
        }

        $('.func_manage_people .participants_menu a').click(function (e) {
            setTripParticipantOrganizer(e, true);
        })

        $('.func_organizer_manage_people .participants_menu a').click(function (e) {
            setTripParticipantOrganizer(e, false);
        })

    }

    var criteria = {
        // skip: paramsJson.paginator ? paramsJson.paginator.skip : 0,
        // limit: paramsJson.paginator ? paramsJson.paginator.limit : null,
        tripId: paramsJson.id
    };

    var url = 'rest/trip/schedule_participants';

    var callbackSuccess = function (data) {
        $("#scheduleParticipants").html($("#scheduleParticipantsTemplate").render(data.schedules, {locale: locale}));
        $('#paginator').smartpaginator({
            totalrecords: data.count,
            skip: paramsJson.paginator ? paramsJson.paginator.skip : 0
        });

        renderScheduleParticipants();
    };

    var callbackError = function (error) {
        alert(error);
    };

    $.postJSON(url, criteria, callbackSuccess, callbackError);

};


</script>

<div class="description">

<article id="article" class="deteil edit">


        <span id="scheduleParticipants"></span>


        <div class="list_posts_button"><input type="button" class="button" value="Search">

            <div class="posts_find"><input type="text" alt="..."></div>
        </div>


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
        <%--</div>--%>


</article>


    </div>

<div id="paginator"></div>

<%--<div class="description">--%>


<%--<div id="list_user_1" class="list_user">--%>
<%--<div class="list_user_inf"><span class="list_user_date">19.01.13 - 29.01.13</span>Планируется</div>--%>
<%--<ul><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a><span>Организатор</span></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a><span>Организатор</span></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----></ul>--%>
<%--<div class="list_user_button"><input type="button" class="button" value="Пригласить"><input type="button" class="button" value="Учавствовать"></div>--%>
<%--</div>--%>


<%--<div id="list_user_2" class="list_user">--%>
<%--<div class="list_user_inf"><span class="list_user_date">19.01.13 - 29.01.13</span><span class="red">Отменено</span> (в связи с погодными условиями)</div>--%>
<%--<ul><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a><span>Организатор</span></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a><span>Организатор</span></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----></ul>--%>
<%--<div class="list_user_button"><input type="button" class="button" value="Пригласить"><input type="button" class="button" value="Учавствовать"></div>--%>
<%--</div>--%>
<%--<div id="list_user_3" class="list_user">--%>
<%--<div class="list_user_inf"><span class="list_user_date">19.01.13 - 29.01.13</span>Завершено</div>--%>
<%--<ul><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a><span>Организатор</span></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a><span>Организатор</span></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----></ul>--%>
<%--<div class="list_user_button"><input type="button" class="button" value="Пригласить"><input type="button" class="button" value="Учавствовать"></div>--%>
<%--</div>--%>

<%--</div>--%>

<%--</article>--%>

<%--<div class="list_posts_button"><input type="button" class="button" value="Поиск"><div class="posts_find"><input type="text" alt="....."></div></div>--%>
<%--<div class="list_posts_add">--%>
<%--<ul></ul>--%>
<%--</div>--%>
