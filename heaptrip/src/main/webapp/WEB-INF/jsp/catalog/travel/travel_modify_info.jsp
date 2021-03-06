<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<c:set var="tripId"
       value='${param.id}'/>

<c:url var="photosUrl" value="/ct/travel_photos">
    <c:param name='id' value="${tripId}"/>
</c:url>
<c:url var="participantsUrl" value="/ct/travel_participants">
    <c:param name='id' value="${tripId}"/>
</c:url>
<c:url var="postsUrl" value="/ct/travel_posts">
    <c:param name='id' value="${tripId}"/>
</c:url>


<c:choose>
    <c:when test="${empty param.ul}">
        <%--<c:choose>--%>
        <%--<c:when test="${not empty trip.locale}">--%>
        <%--<c:set var="currLocale" value="${trip.locale}"/>--%>
        <%--</c:when>--%>
        <%--<c:otherwise>--%>
        <c:set var="currLocale" value="${curr_locale}"/>
        <%--</c:otherwise>--%>
        <%--</c:choose>--%>
    </c:when>
    <c:otherwise>
        <c:set var="currLocale">${param.ul}</c:set>
    </c:otherwise>
</c:choose>

<c:set var="tripId" value='${param.id}'/>

<c:forEach items="${trip.categories}" var="category" varStatus="stat">
    <c:set var="categoryIds" value="${categoriesIds },${category.id}"/>
</c:forEach>

<c:forEach items="${trip.regions}" var="region" varStatus="stat">
    <c:set var="regionIds" value="${regionIds },${region.id}"/>
</c:forEach>

<c:set var="isForFrends" value="${trip.status.value eq 'PUBLISHED_FRIENDS'}"/>
<c:set var="isDraft" value="${trip.status.value eq 'DRAFT'}"/>

<script type="text/javascript">

    var tripId = '${tripId}';
    var lang = '${currLocale}';

    var ct = "${fn:substring(categoryIds,1,1000)}";
    var rg = "${fn:substring(regionIds,1,1000)}";

    $.putLOCALParamToURL({
        ct: ct,
        rg: rg
    });

    var onTripRemoveSubmit = function (btn) {

        $(btn).prop('disabled', true);

        var url = '../rest/security/travel_remove';

        custom_alert = {

        }

        custom_alert.success = function (message) {
            $.alert(
                    '<div class="alert alert-success" role="alert"><span>' + message + ' </span>' +
                            '<a href=<c:url value="/my/travels"/> class="alert-link">Go to <fmt:message key="accountProfile.my"/></a></div>')
        }

        custom_alert.danger = function (message) {
            $.alert(
                    '<div class="alert alert-danger" role="alert"><span>' + message + ' </span>' +
                            '<a href=<c:url value="/my/travels"/> class="alert-link">Go to <fmt:message key="accountProfile.my"/></a></div>')
        }

        var callbackSuccess = function (data) {
            $(btn).prop('disabled', false);
            custom_alert.success(locale.action.successEdit);
        };

        var callbackError = function (error) {
            $(btn).prop('disabled', false);
            custom_alert.danger(error);
        };

        $.postJSON(url, tripId, callbackSuccess, callbackError);
    };

    var onTripSubmit = function (btn) {

        $(btn).prop('disabled', true);

        var jsonData = (tripId ? {
            id: tripId
        } : {});

        jsonData.owner = {id: window.catcher ? window.catcher.id : window.principal.id};

        var statusValue = null;
        if ($('#is_draft').is(':checked'))
            statusValue = 'DRAFT';
        else if ($('#is_for_frends').is(':checked'))
            statusValue = 'PUBLISHED_FRIENDS';
        else
            statusValue = 'PUBLISHED_ALL';

        jsonData.locale = lang;
        jsonData.name = $("#name_post").val();
        jsonData.summary = CKEDITOR.instances['desc_post'].getData(); // $("#desc_post").val();
        jsonData.description = CKEDITOR.instances['desc_full_post'].getData(); // $("#desc_full_post").val();
        $.extend(jsonData, {status: {value: statusValue}});
        $.extend(jsonData, {route: {text: $("#desc_rout_post").val(), map: readMapData()}});

        var paramsJson = $.getParamFromURL();

        if (paramsJson.ct) {
            jsonData.categories = [];
            var categoryIds = paramsJson.ct.split(',');
            $.each(categoryIds, function (index, id) {
                jsonData.categories.push({
                    id: id
                });
            });
        }

        if (paramsJson.rg) {
            jsonData.regions = [];
            var regionIds = paramsJson.rg.split(',');
            $.each(regionIds, function (index, id) {
                jsonData.regions.push({
                    id: id
                });
            });
        }

        var schedule = [];

        $('#schedule_table > tbody  > tr')
                .each(
                function (iTR, tr) {
                    var item = (tr.id ? {id: tr.id } : {});


                    $(this)
                            .children('td')
                            .each(
                            function (iTD, td) {
                                var cellInps = $(this)
                                        .children('input');

                                switch (iTD) {
                                    case 0:
                                        item.begin = {};
                                        if ($("#" + cellInps[0].id)
                                                .datepicker(
                                                        'getDate'))
                                            item.begin.value = $(
                                                    "#"
                                                            + cellInps[0].id)
                                                    .datepicker(
                                                            'getDate')
                                                    .getTime();
                                        item.end = {};
                                        if ($("#" + cellInps[1].id)
                                                .datepicker(
                                                        'getDate'))
                                            item.end.value = $(
                                                    "#"
                                                            + cellInps[1].id)
                                                    .datepicker(
                                                            'getDate')
                                                    .getTime();
                                        break;
                                    case 1:
                                        item.price = {};
                                        item.price.currency = cellInps[0]
                                                .getAttribute('key');
                                        item.price.value = cellInps[0].value;
                                        break;
                                    case 2:
                                        item.min = cellInps[0].value;
                                        break;
                                    case 3:
                                        item.max = cellInps[0].value;
                                        break;
                                    default:
                                        break;
                                }
                            });

                    item.members = $(tr).attr('members');

                    schedule.push(item);

                });

        jsonData.schedule = schedule;


        var url = (tripId ? '../rest/security/travel_update'
                : '../rest/security/travel_save');

        var callbackSuccess = function (data) {
            //var domain =  $("#email").val().replace(/.*@/, "");
            //window.location = 'confirmation?domain=' + domain;
            if (!tripId) {
                $.putGETParamToURL("id", data.tripId);
            }
            $(btn).prop('disabled', false);
            $(".error_message").html('<p class="green">' + locale.action.successEdit + '</p>');
        };

        var callbackError = function (error) {
            $(btn).prop('disabled', false);
            $(".error_message").html('<p class="red">' + error + '</p>');
        };

        $.postJSON(url, jsonData, callbackSuccess, callbackError);

    };

    var onEditTabClick = function (tabHeader, tabId) {
        $('#travel_nav > ul  > li > a').removeClass('active')
        $(tabHeader).addClass('active');
        var showTabId = tabId;
        var hideTabId = (showTabId == 'tab1') ? 'tab2' : 'tab1';
        $("#" + hideTabId).hide();
        $("#" + showTabId).show();

        $('#map_canvas').width($('#map_canvas').parent().parent().parent().width());

        //$('#map_canvas').width(626);

        //$('#map_canvas').height(450);

    }

</script>

<div id="container">
<div id="contents">

<article id="article" class="deteil edit">
<div class="date">
    ${trip.created.text}<span><fmt:message key="trip.title"/></span>
</div>
<div class="inf">
    <div class="left">
        <ul>
            <li><input id="is_draft" type="checkbox" ${isDraft ? "checked=true": "" }><label><fmt:message
                    key="content.draft"/></label></li>
            <li><input id="is_for_frends" type="checkbox"
            ${isForFrends ? "checked=true": "" }><label><fmt:message
                    key="content.forFrends"/></label></li>
        </ul>
    </div>

    <div class="right">
        <div>
            <fmt:message key="content.available"/>
            :
        </div>
        <c:choose>
            <c:when test="${empty tripId}">
                <ul>
                    <li><a onClick="$.putGETParamToURL('ul','${currLocale}')"
                           class="${currLocale} lang"></a></li>
                    <li class="add_list_lang"><a class="add_lang lang" href="/"></a>
                        <ul>
                            <c:forEach items="${lang_values}" var="langValue">
                                <c:if test="${currLocale ne langValue}">
                                    <li><a
                                            onClick="$.putGETParamToURL('ul','${langValue}')"
                                            class="${langValue} lang"></a></li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </li>
                </ul>
            </c:when>
            <c:otherwise>
                <ul>
                    <li class="del_list_lang"><a class="del_lang lang" href="/"></a></li>
                    <!-- add trip.langs + ul -->

                    <c:forEach items="${trip.langs}" var="lang">
                        <c:choose>
                            <c:when test="${currLocale eq lang}">
                                <li class="activ_lang">
                            </c:when>
                            <c:otherwise>
                                <li>
                            </c:otherwise>
                        </c:choose>
                        <a onClick="$.putGETParamToURL('ul','${lang}')"
                           class="${lang} lang"></a>
                        </li>
                    </c:forEach>

                    <c:set var="joinLangValues" value="${fn:join(trip.langs, ',')}"/>

                    <c:choose>
                        <c:when test="${fn:contains(joinLangValues, param.ul) ne true}">
                            <li class="activ_lang"><a
                            onClick="$.putGETParamToURL('ul','${param.ul}')"
                            class="${param.ul} lang"></a>
                        </c:when>
                        <c:otherwise>
                            <li class="add_list_lang"><a class="add_lang lang"
                                                         href="/"></a>
                                <ul>

                                    <c:forEach items="${lang_values}" var="langValue">
                                        <c:if
                                                test="${fn:contains(joinLangValues, langValue) ne true}">
                                            <li><a
                                                    onClick="$.putGETParamToURL('ul','${langValue}')"
                                                    class="${langValue} lang"></a></li>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:otherwise>
                    </c:choose>

                </ul>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<input type="text" id="name_post" value="${trip.name}"
       alt="<fmt:message key="content.name" />:">

<div class="error_message"></div>


<nav id="travel_nav" class="travel_nav">
    <a onClick="onTripSubmit(this)" class="button"><fmt:message
            key="page.action.save"/></a>
    <ul>

        <li><a onClick="onEditTabClick(this,'tab1')"
               class='${fn:contains(param.tb, "info") || empty param.tb ? "active":"" }'><fmt:message
                key="content.information"/><span></span></a></li>

        <li><a onClick="onEditTabClick(this,'tab2')" class='${fn:contains(param.tb, "map") ? "active":"" }'><fmt:message
                key="trip.route"/><span></span></a></li>

        <li><a href="${photosUrl}" class='${fn:contains(url, "photo") ? "active":"" }'><fmt:message
                key="content.photo"/><span></span></a></li>

        <li><a href="${participantsUrl}" class='${fn:contains(url, "participant") ? "active":"" }'><fmt:message
                key="content.participants"/><span></span></a></li>


        <li><a href="${postsUrl}" class='${fn:contains(url, "post") ? "active":"" }'><fmt:message
                key="post.list.title"/><span></span></a></li>


    </ul>
</nav>


<div id="tab1" style='display:${fn:contains(param.tb, "info") || empty param.tb ? "true":"none" }'>


    <br/>

    <textarea id="desc_post" class="ckeditor" name="editor1"
              alt="<fmt:message key="content.shortDescription" />:">${trip.summary}</textarea>
    <textarea id="desc_full_post" class="ckeditor" name="editor2"
              alt="<fmt:message key="content.fullDescription" />:">${trip.description}</textarea>

    <div class="table_inf">
        <table id=schedule_table>
            <thead>
            <tr>
                <th><fmt:message key="trip.period"/></th>
                <th class="price_th"><fmt:message key="trip.cost"/></th>
                <th><fmt:message key="content.participants.min"/></th>
                <th><fmt:message key="content.participants.max"/></th>
                <th><fmt:message key="page.action"/></th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty tripId}">
                    <tr>
                        <td><fmt:message key="page.date.from"/> <input
                                type="text" class="datepicker"> <br/> <fmt:message
                                key="page.date.to"/> <input type="text" class="datepicker"></td>
                        <td class="price_td"><input type="text"
                                                    key="<fmt:message key="currency.name" />">

                            <div class="currency">
                                <span><fmt:message key="currency.current"/></span>
                                <ul>
                                    <li key="RUB"><fmt:message key="currency.RUB"/></li>
                                    <li key="USD"><fmt:message key="currency.USD"/></li>
                                    <li key="EUR"><fmt:message key="currency.EUR"/></li>
                                </ul>
                            </div>
                        </td>
                        <td><input type="text"></td>
                        <td><input type="text"></td>
                        <td><a class="button" func="4"> <fmt:message
                                key="page.action.delete"/>
                        </a></td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${trip.schedule}" var="scheduleItem">
                        <tr id="${scheduleItem.id}" members="${scheduleItem.members}">
                            <td><fmt:message key="page.date.from"/> <input
                                    value="${scheduleItem.begin.text}" type="text"
                                    class="datepicker"> <br/> <fmt:message
                                    key="page.date.to"/> <input
                                    value="${scheduleItem.end.text}" type="text"
                                    class="datepicker"></td>
                            <td class="price_td">
                                <c:choose>
                                <c:when test="${empty scheduleItem.price.value}">
                                <input type="text"
                                       key="<fmt:message key="currency.name" />">

                                <div class="currency">
                                    <span><fmt:message key="currency.current"/></span>
                                    </c:when>
                                    <c:otherwise>
                                    <input value="${scheduleItem.price.value}"
                                           key="${scheduleItem.price.currency}" type="text">

                                    <div class="currency">
															<span><fmt:message
                                                                    key="currency.${scheduleItem.price.currency}"/></span>
                                        </c:otherwise>
                                        </c:choose>
                                        <ul>
                                            <li key="RUB"><fmt:message key="currency.RUB"/></li>
                                            <li key="USD"><fmt:message key="currency.USD"/></li>
                                            <li key="EUR"><fmt:message key="currency.EUR"/></li>
                                        </ul>
                                    </div>
                            </td>
                            <td><input value="${scheduleItem.min}" type="text"></td>
                            <td><input value="${scheduleItem.max}" type="text"></td>
                            <td><a class="button" func="4"> <fmt:message
                                    key="page.action.delete"/>
                            </a></td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
        <a class="button" func="5"><fmt:message key="page.action.add"/></a>
    </div>

</div>

<div id="tab2" style='display:${fn:contains(param.tb, "map") ? "true":"none" }'>

    <div class="tabs tabs_interactiv">
        <ul>
            <li><span class="activ"><fmt:message key="content.webMaps"/></span>

                <div class="tabs_content" style="height:450px; width:626px">
                    <div id="map_canvas" class="smallmap"></div>
                    <div id="map_data" style="display: none;">${trip.route.map}</div>
                </div>
            </li>
            <li><span><fmt:message key="content.maps"/></span>

                <div class="tabs_content">
                    <ul><!--
    										-->
                        <li><a href="/map"><img src="/map/map1.jpg"></a></li>
                        <!--
                                                                    -->
                        <li><a href="/map"><img src="/map/map1.jpg"></a></li>
                        <!--
                                                                    -->
                        <li><a href="/map"><img src="/map/map1.jpg"></a></li>
                        <!--
                                                                    -->
                        <li><a href="/map"><img src="/map/map1.jpg"></a></li>
                        <!--
                                                                    -->
                        <li><a href="/map"><img src="/map/map1.jpg"></a></li>
                        <!--
                                                                    -->
                        <li><a href="/map"><img src="/map/map1.jpg"></a></li>
                        <!--
                                                                -->
                    </ul>
                </div>
            </li>
            <!--
                                    --></ul>
    </div>
    <div class="description">
        <textarea id="desc_rout_post"
                  alt="<fmt:message key="content.routeDescription" />:">${trip.route.text}</textarea>
    </div>
</div>

</article>

<!-- c:if test="${not empty param.id}" -->

<div class="del_article">
    <a onclick="onTripRemoveSubmit($(this))" class="button"><fmt:message key="trip.action.delete"/></a>

    <p/>

    <div id="delete_alert_placeholder"></div>
</div>

<!-- /c:if -->

</article>


</div>
<!-- #content-->


</div>
<!-- #container-->

<aside id="sideRight">
    <tiles:insertDefinition name="categoryTree"/>
    <tiles:insertDefinition name="regionFilter"/>
</aside>


<!-- #sideRight -->


