<%@ page import="com.heaptrip.domain.entity.LangEnum"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="langValues" value="<%=LangEnum.getValues()%>" />

<c:choose>
    <c:when test="${empty param.ul}">
        <c:set var="currLocale">
            <fmt:message key="locale.name" />
        </c:set>
    </c:when>
    <c:otherwise>
        <c:set var="currLocale">${param.ul}</c:set>
    </c:otherwise>
</c:choose>

<script type="text/javascript">

    <c:forEach items="${trip.categories}" var="category" varStatus="stat">
    <c:set var="categoryIds" value="${categoriesIds },${category.id}" />
    </c:forEach>

    <c:forEach items="${trip.regions}" var="region" varStatus="stat">
    <c:set var="regionIds" value="${regionIds },${region.id}" />
    </c:forEach>


    $(document).ready(function() {
        var ct = "${fn:substring(categoryIds,1,1000)}";
        var rg = "${fn:substring(regionIds,1,1000)}";
        $.handParamToURL({
            ct : ct,
            rg : rg
        });
    });

    var onAccountSubmit = function(btn) {

        $(btn).prop('disabled', true);

        var jsonData = {
            id : $.getParamFromURL().uid ? $.getParamFromURL().uid : null
        };

        jsonData.name = $("#my_name").val();

        var accountProfile = {};

        accountProfile.desc = $("#my_desc").val();

        accountProfile.langs = [];
        $('.my_lang.my_lang_edit > ul > li').each(
                function(ili, li) {
                    var className = $(li).attr('class');
                    if(className !== 'my_add_lang')
                    accountProfile.langs.push(className);
        });

        var paramsJson = $.getParamFromURL();

        if (paramsJson.ct) {
            accountProfile.categories = [];
            var categoryIds = paramsJson.ct.split(',');
            $.each(categoryIds, function(index, id) {
                accountProfile.categories.push({
                    id : id
                });
            });
        }

        if (paramsJson.rg) {
            accountProfile.regions = [];
            var regionIds = paramsJson.rg.split(',');
            $.each(regionIds, function(index, id) {
                accountProfile.regions.push({
                    id : id
                });
            });
        }

        if ($("#location").attr('reg_id')) {
             $.extend(accountProfile,{location : {id:$("#location").attr('reg_id')}});
        }

        var userProfile = {};

        if ($('#birthday').datepicker('getDate'))
            $.extend(userProfile,{birthday : {value:$('#birthday').datepicker('getDate').getTime()}});

        $.extend(jsonData, {accountProfile:accountProfile});
        $.extend(jsonData, {userProfile:userProfile});
        /*
        var schedule = [];

        $('#schedule_table > tbody  > tr')
                .each(
                function(iTR, tr) {
                    var item = (tr.id ? {
                        id : tr.id
                    } : {});
                    $(this)
                            .children('td')
                            .each(
                            function(iTD, td) {
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

                    schedule.push(item);

                });

        jsonData.schedule = schedule;
        */
        var url =  'rest/security/account_update' ;


        var callbackSuccess = function(data) {
            window.location = 'profile.html?uid=' + $.getParamFromURL().uid;
        };

        var callbackError = function(error) {
            $(btn).prop('disabled', true);
            $("#error_message #msg").text(error);
        };

        $.postJSON(url, jsonData, callbackSuccess, callbackError);

    };

</script>


<div id="container">
    <div id="contents">
        <article id="article" class="deteil edit">
            <div class="inf">
                <div class="left">
                    <h2 class="people_title"><fmt:message key="accountProfile.title"/></h2></h2>
                </div>
                <div class="right">
                    <a onClick="onAccountSubmit(this)" class="button"><fmt:message key="page.action.save"/></a>
                </div>

                <div id="error_message">
                    <span id="msg" class="error_message"></span>
                </div>

                <div class="accountProfile">
                    <div class="my_avatar"><img src="<c:url value="/rest/image?imageId=${account.image.id}"/>"><a
                            href="/" class="button"><fmt:message key="page.action.uploadPhoto"/></a></div>
                    <div class="my_inf">
                        <div class="my_name">
                            <input id="my_name" type="text" value="${account.name}" alt="<fmt:message key="user.firstName"/>">
                        </div>
                        <div class="my_location"><span><fmt:message key="user.place"/>: </span><input id="location" reg_id="${account.accountProfile.location.id}" value="${account.accountProfile.location.data}" type="text"></div>
                        <div class="my_date"><span><fmt:message key="user.birthday"/>:
                            </span><input id="birthday" value="${account.userProfile.birthday.text}" type="text" class="datepicker">
                        </div>
                        <div class="my_lang my_lang_edit">
                            <fmt:message key="user.languages"/>:
                            <ul>
                                <c:forEach items="${account.accountProfile.langs}" var="lang" varStatus="stat">
                                    <li class="${lang}"><fmt:message key="locale.${lang}"/></li>
                                </c:forEach>
                                <li class="my_add_lang">
                                    <a class="add_lang lang"></a>
                                    <div>
                                        <ul>
                                            <c:set var="joinLangValues" value="${fn:join(account.accountProfile.langs, ',')}" />
                                            <c:forEach items="${langValues}" var="langValue">
                                                <c:if test="${fn:contains(joinLangValues, langValue) ne true}">
                                                    <li><a class="${langValue}"><fmt:message key="locale.${langValue}"/></a></li>
                                                </c:if>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

            </div>
            <div class="description">
                <textarea id="my_desc" alt="<fmt:message key="user.aboutMe"/>:">${account.accountProfile.desc}</textarea>
            </div>


            <div class="table_inf">
                <h2 class="people_title"><fmt:message key="user.knowledge"/>:</h2>
                <table id="knowledge_table">
                    <thead>
                    <tr>
                        <th><fmt:message key="page.date.period"/></th>
                        <th><fmt:message key="user.specialty"/></th>
                        <th><fmt:message key="user.specialty.placeOf"/></th>
                        <th><fmt:message key="user.specialty.document"/></th>
                        <th><fmt:message key="page.action"/></th>
                    </tr>
                    </thead>
                    <tbody>


                    <tr>
                        <td><fmt:message key="page.date.from"/> <input type="text" class="datepicker"><br/><fmt:message
                                key="page.date.to"/> <input type="text" class="datepicker"></td>
                        <td><input type="text"></td>
                        <td><input type="text"></td>
                        <td><input type="text"></td>
                        <td><a class="button" func="4"><fmt:message key="page.action.delete"/></a></td>
                    </tr>


                    </tbody>
                </table>
                <a class="button" func="10"><fmt:message key="page.action.add" /></a>
            </div>




            <div class="table_inf">
                <h2 class="people_title"><fmt:message key="user.experience"/></h2>
                <table class="experience">
                    <thead><tr>
                        <th><fmt:message key="page.date.period"/></th>
                        <th><fmt:message key="content.description"/></th>
                        <th><fmt:message key="page.action"/></th>
                    </tr></thead>
                    <tbody>
                    <tr>
                        <td></td>
                        <td></td>
                        <td><a class="button" func="4"><fmt:message key="page.action.delete"/></a></td>
                    </tr>
                    </tbody>
                </table>
                <a class="button" func="11"><fmt:message key="page.action.add"/></a>
            </div>

        </article>

    </div>

</div>


<aside id="sideRight">
    <tiles:insertDefinition name="categoryTree"/>
    <tiles:insertDefinition name="regionFilter"/>
</aside>





