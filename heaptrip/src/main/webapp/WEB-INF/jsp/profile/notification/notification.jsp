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
            <span>15.01.13</span>

            <div class="list_alert_name"><a href="/">В Финляндию и Швецию</a></div>
            <a href="/" class="button">Отклонить</a><a href="/" class="button">Принять</a>
        </div>
    </li>

</script>


<script type="text/javascript">

    $(window).bind("onPageReady", function (e, paramsJson) {
        getCommunitiesList(paramsJson);
    });

    var getCommunitiesList = function (paramsJson) {

        var url = 'rest/security/notifications';

        var criteria = {

            /*owners: {
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
             }  */
        };

        var callbackSuccess = function (data) {

            $("#notification_list").html($("#notificationsTemplate").render(data.notifications));

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
        <div class="list_alert">

            <div class="select">
                <div class="select_selected">BY NEW</div>
                <ul>
                    <li><a href="/">BY NEW</a></li>
                    <li><a href="/">BY OLD</a></li>
                </ul>
            </div>
            <ul id="notification_list">

                <ul>


        </div>
        <!-- #content-->
    </div>
    <!-- #container-->

    <aside id="sideRight">

    </aside>
