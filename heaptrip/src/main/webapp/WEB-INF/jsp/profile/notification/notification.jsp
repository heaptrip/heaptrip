<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<script id="notificationsTemplate" type="text/x-jsrender">
    <li id="{{>id}}">
        <div class="list_alert_img">
            <img src="/TODO.jpg">
        </div>
        <div class="list_alert_inf">
            <span>{{>created.text}}</span>
            <div class="list_alert_name"><a href="/">{{>text}}</a></div>
            <a href="/" class="button">OK</a><a href="/" class="button">CANCEL</a>
        </div>
    </li>
</script>


<script type="text/javascript">

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

    var getUserNotification = function (paramsJson) {

        var url = 'rest/security/notification/user';

        var criteria = {
            accountId: window.principal.id
        };

        var callbackSuccess = function (data) {
            $("#notification_user_list").html($("#notificationsTemplate").render(data.notifications));
            /*$('#paginator1').smartpaginator({
             totalrecords: 100,
             skip: paramsJson.skip
             }); */
        };

        var callbackError = function (error) {
            alert(error);
        };

        $.postJSON(url, criteria, callbackSuccess, callbackError);

    };

    var getCommunityNotification = function (paramsJson) {

        var url = 'rest/security/notification/community';

        var criteria = {
            userId: window.principal.id
        };

        var callbackSuccess = function (data) {

            $("#notification_community_list").html($("#notificationsTemplate").render(data.notifications));

            /*$('#paginator1').smartpaginator({
             totalrecords: 100,
             skip: paramsJson.skip
             }); */
        };

        var callbackError = function (error) {
            alert(error);
        };

        $.postJSON(url, criteria, callbackSuccess, callbackError);

    };


</script>

<tiles:insertDefinition name="pagination"/>

<div id="container">
    <div id="contents">
        <nav id="travel_nav">
            <ul>
                <li><a onClick="onTabClick(this,'tab1')"
                       class="active"><fmt:message
                        key="account.type.user"/><span></span></a></li>

                <li><a onClick="onTabClick(this,'tab2')"><fmt:message
                        key="community.title"/><span></span></a></li>
            </ul>
        </nav>


        <div id="tab1" style="display:${fn:contains(param.tb, 'usr') || empty param.tb ? 'cmt':'none'}">
            <div class="list_alert">
                <div class="select">
                    <div class="select_selected">BY NEW</div>
                    <ul>
                        <li><a href="/">BY NEW</a></li>
                        <li><a href="/">BY OLD</a></li>
                    </ul>
                </div>
                <ul id="notification_user_list"></ul>
            </div>
        </div>

        <div id="tab2" style="display:${fn:contains(param.tb, 'cmt') ? 'true':'none' }">
            <div class="list_alert">
                <div class="select">
                    <div class="select_selected">BY NEW</div>
                    <ul>
                        <li><a href="/">BY NEW</a></li>
                        <li><a href="/">BY OLD</a></li>
                    </ul>
                </div>
                <ul id="notification_community_list"></ul>
            </div>
        </div>
        <!-- #content-->
    </div>
    <!-- #container-->

    <aside id="sideRight">

    </aside>
