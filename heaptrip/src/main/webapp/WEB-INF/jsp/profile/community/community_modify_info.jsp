<%@ page import="com.heaptrip.domain.entity.LangEnum" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script type="text/javascript">

    <c:forEach items="${trip.categories}" var="category" varStatus="stat">
    <c:set var="categoryIds" value="${categoriesIds },${category.id}" />
    </c:forEach>

    <c:forEach items="${trip.regions}" var="region" varStatus="stat">
    <c:set var="regionIds" value="${regionIds },${region.id}" />
    </c:forEach>


    //$(document).ready(function () {
    <%--var ct = "${fn:substring(categoryIds,1,1000)}";--%>
    <%--var rg = "${fn:substring(regionIds,1,1000)}";--%>
    //$.handParamToURL({
    //    ct: ct,
    //    rg: rg
    //});
    //});

    var onCommunitySubmit = function (btn) {

        $(btn).prop('disabled', true);

        var jsonData = {
            id: window.catcher ? window.catcher.id : null
        };

        jsonData.name = $("#community_name").val();
//        jsonData.email = $("#community_email").val();
//        jsonData.typeAccount = $('.select_selected').attr('value');
        jsonData.typeAccount = "CLUB";

        var accountProfile = {};

        accountProfile.desc = $("#community_desc").val();


        var paramsJson = $.getParamFromURL();

        if (paramsJson.ct) {
            accountProfile.categories = [];
            var categoryIds = paramsJson.ct.split(',');
            $.each(categoryIds, function (index, id) {
                accountProfile.categories.push({
                    id: id
                });
            });
        }

        if (paramsJson.rg) {
            accountProfile.regions = [];
            var regionIds = paramsJson.rg.split(',');
            $.each(regionIds, function (index, id) {
                accountProfile.regions.push({
                    id: id
                });
            });
        }

        if ($("#location").attr('reg_id')) {
            $.extend(accountProfile, {location: {id: $("#location").attr('reg_id')}});
        }

        $.extend(jsonData, {accountProfile: accountProfile});

        var url = null;

        if (jsonData.id)
            url = '../rest/security/community_update';
        else
            url = '../rest/security/community/registration';

        var callbackSuccess = function (data) {
            if (jsonData.id)
                window.location = '../pf/communities';
            else {
                var domain = $("#community_email").val().replace(/.*@/, "");
                window.location = 'confirmation?domain=' + domain;
            }
        };

        var callbackError = function (error) {
            $(btn).prop('disabled', true);
            $("#error_message #msg").html(error + '<br/><br/>');
        };

        $.postJSON(url, jsonData, callbackSuccess, callbackError);

    };

</script>


<div id="container">
    <div id="contents">
        <article id="article" class="deteil edit">
            <div class="inf">
                <div class="left">
                    <h2 class="people_title"><fmt:message key="community.title"/></h2></h2>
                </div>
                <div class="right">
                    <a onClick="onCommunitySubmit(this)" class="button"><fmt:message key="page.action.save"/></a>
                </div>

                <div id="error_message">
                    <span id="msg" class="error_message"></span>
                </div>

                <div class="accountProfile">
                    <div class="my_avatar"><img src="<c:url value="../rest/image/medium/${account.image.id}"/>"><a
                            class="button"><fmt:message key="page.action.uploadPhoto"/></a></div>
                    <div class="my_inf">
                        <div class="my_name">
                            <input id="community_name" type="text" value="${account.name}"
                                   alt="<fmt:message key="community.name"/>">
                        </div>
                        <%--// TODO dikma изменение email только в настройках через подтверждение--%>
                        <%--<div class="my_name">--%>
                            <%--<input id="community_email" type="text" value="${account.email}"--%>
                                   <%--alt="<fmt:message key="accountProfile.email"/>">--%>
                        <%--</div>--%>

                        <%--// TODO dikma изменение типа сообщества необходимо обсудить, т.к. в перспективе они будут иметь разный функционал--%>
                        <%--<div class="my_location"><span><fmt:message key="account.type"/>: </span>--%>
                            <%--<div class="select" id="community_type2">--%>
                                <%--<c:choose>--%>
                                    <%--<c:when test='${empty account.typeAccount}'>--%>
                                        <%--<div class="select_selected" value="CLUB"><fmt:message key="account.type.club"/></div>--%>
                                    <%--</c:when>--%>
                                    <%--<c:otherwise>--%>
                                        <%--<div class="select_selected" value="${ account.typeAccount }"><fmt:message--%>
                                                <%--key="account.type.club"/></div>--%>
                                    <%--</c:otherwise>--%>
                                <%--</c:choose>--%>
                                <%--<ul style="display: none;">--%>
                                    <%--<li><a href="/" value="CLUB"><fmt:message key="account.type.club"/></a></li>--%>
                                    <%--<li><a href="/" value="COMPANY"><fmt:message key="account.type.company"/></a></li>--%>
                                    <%--<li><a href="/" value="AGENCY"><fmt:message key="account.type.agency"/></a></li>--%>
                                <%--</ul>--%>
                            <%--</div>--%>
                        <%--</div>--%>

                        <div class="my_location"><span><fmt:message key="user.place"/>: </span>
                            <input id="location"
                                   reg_id="${account.accountProfile.location.id}"
                                   value="${account.accountProfile.location.data}"
                                   type="text"/>
                        </div>


                    </div>
                </div>

            </div>
            <div class="description">
                <textarea id="community_desc"
                          alt="<fmt:message key="content.description"/>:">${account.accountProfile.desc}</textarea>
            </div>


        </article>

    </div>

</div>


<aside id="sideRight">
    <tiles:insertDefinition name="categoryTree"/>
    <tiles:insertDefinition name="regionFilter"/>
</aside>





