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



    var onAccountSubmit = function() {

        var jsonData = {
            id : $.getParamFromURL().uid ? $.getParamFromURL().uid : null
        };

        jsonData.name = $("#my_name").val();

        var profile = {};

        profile.desc = $("#my_desc").val();

        var paramsJson = $.getParamFromURL();

        if (paramsJson.ct) {
            profile.categories = [];
            var categoryIds = paramsJson.ct.split(',');
            $.each(categoryIds, function(index, id) {
                profile.categories.push({
                    id : id
                });
            });
        }

        if (paramsJson.rg) {
            profile.regions = [];
            var regionIds = paramsJson.rg.split(',');
            $.each(regionIds, function(index, id) {
                profile.regions.push({
                    id : id
                });
            });
        }


        $.extend(jsonData, {profile:profile});
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
            //var domain =  $("#email").val().replace(/.*@/, "");
            //window.location = 'confirmation.html?domain=' + domain;
            alert("Success");
        };

        var callbackError = function(error) {
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
                    <h2 class="people_title"><fmt:message key="profile.title"/></h2></h2>
                </div>
                <div class="right">
                    <a onClick="onAccountSubmit()" class="button"><fmt:message key="page.action.save"/></a>
                </div>

                <div id="error_message">
                    <span id="msg" class="error_message"></span>
                </div>

                <div class="profile">
                    <div class="my_avatar"><img src="<c:url value="/rest/image?imageId=${account.image.id}"/>"><a
                            href="/" class="button"><fmt:message key="page.action.uploadPhoto"/></a></div>
                    <div class="my_inf">
                        <div class="my_name">
                            <input id="my_name" type="text" value="${account.name}" alt="<fmt:message key="user.firstName"/>">
                        </div>
                        <div class="my_location"><span><fmt:message key="user.place"/>: </span><input type="text"></div>
                        <div class="my_date"><span><fmt:message key="user.birthday"/>: </span><input type="text"
                                                                                                     class="datepicker">
                        </div>
                        <div class="my_lang my_lang_edit">
                            <fmt:message key="user.languages"/>:
                            <ul>
                                <li class="ru">Русский<span></span></li>
                                <li class="en">Английский<span></span></li>
                                <li class="du">Немецкий<span></span></li>
                                <li class="fr">Французский<span></span></li>
                                <li class="my_add_lang">
                                    <a class="add_lang lang"></a>

                                    <div>
                                        <ul>
                                            <li><a class="en">English</a></li>
                                            <li><a class="du">Dutish</a></li>
                                            <li><a class="fr">Franch</a></li>
                                            <li><a class="yk">Украiнскi</a></li>
                                            <li><a class="sw">Sweden</a></li>
                                        </ul>
                                    </div>
                                </li>

                            </ul>
                        </div>
                    </div>
                </div>

            </div>
            <div class="description">
                <textarea id="my_desc" alt="<fmt:message key="user.aboutMe"/>:">${account.profile.desc}</textarea>
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





