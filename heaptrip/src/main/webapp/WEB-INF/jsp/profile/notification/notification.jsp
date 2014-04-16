<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<script id="userCommunitiesTemplate" type="text/x-jsrender">
    <li class="participants_li community_func_user">
        <div class="list_user_img">{{if image}}<img src="rest/image/small/{{>image.id}}">{{/if}}</div><div class="list_user_name"><a href="pf-community.html?guid={{>id}}">{{>name}}</a></div>
    </li>
</script>



<script type="text/javascript">

    $(window).bind("onPageReady", function (e, paramsJson) {
        getCommunitiesList(paramsJson);
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


</script>

<tiles:insertDefinition name="pagination"/>

<div id="container">
    <div id="contents">
        <div class="list_alert">

            <div class="select">
                <div class="select_selected">Сначала новые</div>
                <ul>
                    <li><a href="/">Сначала новые</a></li>
                    <li><a href="/">Сначала старые</a></li>
                </ul>
            </div>
            <ul>
                <li id="345">
                    <div class="list_alert_img">
                        <img src="/1_small.jpg">
                    </div>
                    <div class="list_alert_inf">
                        <span>15.01.13</span>
                        <div class="list_alert_name"><a href="/">В Финляндию и Швецию</a></div>
                        <a href="/" class="button">Отклонить</a><a href="/" class="button">Принять</a>
                    </div>
                </li>
            <ul>




        <%--<article id="article" class="deteil edit">--%>

            <%--<div class="description">--%>

                <%--<div id="list_user_1" class="community" style="border-bottom: 1px solid #E2E6E5;">--%>
                    <%--<div class="list_user_inf people_title">--%>
                        <%--<c:choose>--%>
                            <%--<c:when test="${not empty catcher}">--%>
                                <%--<fmt:message key="user.owner"/>--%>
                            <%--</c:when>--%>
                            <%--<c:otherwise>--%>
                                <%--<fmt:message key="user.i.owner"/>--%>
                            <%--</c:otherwise>--%>
                        <%--</c:choose>--%>
                    <%--</div>--%>
                    <%--<ul id="user_communities"></ul>--%>
                <%--</div>--%>

                <%--<div id="list_user_2" class="community" style="border-bottom: 1px solid #E2E6E5;">--%>
                    <%--<div class="list_user_inf people_title">--%>
                        <%--<c:choose>--%>
                            <%--<c:when test="${not empty catcher}">--%>
                                <%--<fmt:message key="user.employee"/>--%>
                            <%--</c:when>--%>
                            <%--<c:otherwise>--%>
                                <%--<fmt:message key="user.i.employee"/>--%>
                            <%--</c:otherwise>--%>
                        <%--</c:choose>--%>
                    <%--</div>--%>
                    <%--<ul id="working_communities"></ul>--%>
                <%--</div>--%>
                <%--<div id="list_user_3" class="community" style="border-bottom: 1px solid #E2E6E5;">--%>
                    <%--<div class="list_user_inf people_title">--%>
                        <%--<c:choose>--%>
                            <%--<c:when test="${not empty catcher}">--%>
                                <%--<fmt:message key="user.member"/>--%>
                            <%--</c:when>--%>
                            <%--<c:otherwise>--%>
                                <%--<fmt:message key="user.i.member"/>--%>
                            <%--</c:otherwise>--%>
                        <%--</c:choose>--%>
                    <%--</div>--%>
                    <%--<ul id="member_communities"></ul>--%>
                <%--</div>--%>

                <%--<div id="list_user_4" class="community">--%>
                    <%--<div class="list_user_inf people_title">--%>
                        <%--<c:choose>--%>
                            <%--<c:when test="${not empty catcher}">--%>
                                <%--<fmt:message key="user.subscriber"/>--%>
                            <%--</c:when>--%>
                            <%--<c:otherwise>--%>
                                <%--<fmt:message key="user.i.subscriber"/>--%>
                            <%--</c:otherwise>--%>
                        <%--</c:choose>--%>
                    <%--</div>--%>
                    <%--<ul id="subscriber_communities"></ul>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</article>--%>
    </div>
    <!-- #content-->
</div>
<!-- #container-->

<aside id="sideRight">

</aside>
<!-- #sideRight -->