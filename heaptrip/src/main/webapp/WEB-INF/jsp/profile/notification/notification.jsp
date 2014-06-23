<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<script id="notificationsTemplate" type="text/x-jsrender">
    {{if status == 'NEW'}}
        <li id="{{>id}}" onclick="onNotificationView($(this))">
    {{else}}
        <li id="{{>id}}" class="old">
    {{/if}}
            <div class="list_alert_img">
                {{if accountFrom && accountFrom.image && accountFrom.image.id}}
                <img src="<c:url value="/rest/image/small/{{>accountFrom.image.id}}"/>">
                    {{else}}
                <img src="<c:url value="/images/user_mail.png"/>">
                    {{/if}}
            </div>
            <div class="list_alert_inf">
                <span>{{>created.text}}</span>
                <div class="list_alert_name"><a>{{:text}}</a></div>
                {{if isAwaitingAction == 'true'}}
                    <a key='REJECTED' onclick="onNotificationClick(event,$(this))" class="button">Reject</a>
                    <a key='ACCEPTED' onclick="onNotificationClick(event,$(this))" class="button">Accept</a>
                {{else}}
                    {{>status}}
                {{/if}}
            </div>
        </li>
</script>


<script type="text/javascript">

    var changeNotificationStatus = function (notificationId, status) {

        var url = '../rest/security/notification/change_status';

        var callbackSuccess = function (data) {
            $('#' + notificationId).toggleClass("old")
        };

        var callbackError = function (error) {
            $.alert(error);
        };

        var params = { notificationId: notificationId, status: status};

        $.postJSON(url, params, callbackSuccess, callbackError);


    }

    var onNotificationView = function (msg) {

        var notificationId = msg[0].id;
        if ($('#' + notificationId).find('.button').length > 0) return;
        changeNotificationStatus(notificationId, 'READED');
        //alert(' I View Notification notificationId : ' + notificationId);

    }

    var onNotificationClick = function (e, btn) {
        var notificationId = btn.parent().parent()[0].id;
        var action = btn.attr('key');
        //alert('notificationId : ' + notificationId + ' action : ' + action);
        changeNotificationStatus(notificationId, action);
        if (e.stopPropagation)
            e.stopPropagation();
        else
            e.cancelBubble = true;


    }

    var onTabClick = function (tabHeader, tabId) {
        $('#travel_nav > ul  > li > a').removeClass('active')
        $(tabHeader).addClass('active');
        var showTabId = tabId;
        var hideTabId = (showTabId == 'tab1') ? 'tab2' : 'tab1';

        if (showTabId == 'tab1') {
            getUserNotification($.getParamFromURL());
        } else {
            getCommunityNotification($.getParamFromURL());
        }

        $("#" + hideTabId).hide();
        $("#" + showTabId).show();
    }

    $(window).bind("onPageReady", function (e, paramsJson) {
        if ($.getParamFromURL().tb == 'cmt')
            getCommunityNotification(paramsJson);
        else
            getUserNotification(paramsJson);
    });

    var getUserNotification = function (params) {

        var url = '../rest/security/notification/user';

        var criteria = {
            accountId: window.principal.id,
            skip: params.paginator1 ? params.paginator1.skip : 0,
            limit: params.paginator1 ? params.paginator1.limit : null
        };

        var callbackSuccess = function (data) {
            $("#notification_user_list").html($("#notificationsTemplate").render(data.notifications));

            $('#paginator1').smartpaginator({
                totalrecords: data.count,
                skip: params.paginator1 ? params.paginator1.skip : null
            });
        };

        var callbackError = function (error) {
            $.alert(error);
        };

        $.postJSON(url, criteria, callbackSuccess, callbackError);

    };

    var getCommunityNotification = function (params) {

        var url = '../rest/security/notification/community';

        var criteria = {
            userId: window.principal.id,
            skip: params.paginator2 ? params.paginator2.skip : 0,
            limit: params.paginator2 ? params.paginator2.limit : null
        };

        var callbackSuccess = function (data) {

            $("#notification_community_list").html($("#notificationsTemplate").render(data.notifications));

            $('#paginator2').smartpaginator({
                totalrecords: data.count,
                skip: params.paginator2 ? params.paginator2.skip : null
            });
        };

        var callbackError = function (error) {
            $.alert(error);
        };

        $.postJSON(url, criteria, callbackSuccess, callbackError);

    };


</script>

<tiles:insertDefinition name="pagination"/>

<div id="container">
    <div id="contents">
        <nav id="travel_nav" class="travel_nav">
            <ul>
                <li><a onClick="onTabClick(this,'tab1')"
                       class="active"><fmt:message
                        key="account.type.user"/>
                    (${notificationServiceWrapper.getUnreadNotificationFromUsers()})<span></span></a></li>

                <li><a onClick="onTabClick(this,'tab2')"><fmt:message
                        key="community.title"/>
                    (${notificationServiceWrapper.getUnreadNotificationFromCommunities()})<span></span></a></li>
            </ul>
        </nav>


        <div id="tab1" style="display:${fn:contains(param.tb, 'usr') || empty param.tb ? 'cmt':'none'}">
            <div class="list_alert">
                <%--<div class="select">--%>
                <%--<div class="select_selected">BY NEW</div>--%>
                <%--<ul>--%>
                <%--<li><a href="/">BY NEW</a></li>--%>
                <%--<li><a href="/">BY OLD</a></li>--%>
                <%--</ul>--%>
                <%--</div>--%>
                <ul id="notification_user_list"></ul>
            </div>
            <div class="pagination_mini">
                <div id="paginator1"></div>
            </div>
        </div>

        <div id="tab2" style="display:${fn:contains(param.tb, 'cmt') ? 'true':'none' }">
            <div class="list_alert">
                <%--<div class="select">--%>
                <%--<div class="select_selected">BY NEW</div>--%>
                <%--<ul>--%>
                <%--<li><a href="/">BY NEW</a></li>--%>
                <%--<li><a href="/">BY OLD</a></li>--%>
                <%--</ul>--%>
                <%--</div>--%>
                <ul id="notification_community_list"></ul>
            </div>
            <div class="pagination_mini">
                <div id="paginator2"></div>
            </div>
        </div>
        <!-- #content-->
    </div>
    <!-- #container-->
</div>


<aside id="sideRight">
</aside>
