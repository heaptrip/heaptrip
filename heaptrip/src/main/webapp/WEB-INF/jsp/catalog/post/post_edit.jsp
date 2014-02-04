<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="postId" value='${param.id}'/>

<c:forEach items="${post.categories}" var="category" varStatus="stat">
    <c:set var="categoryIds" value="${categoryIds },${category.id}"/>
</c:forEach>

<c:forEach items="${post.regions}" var="region" varStatus="stat">
    <c:set var="regionIds" value="${regionIds },${region.id}"/>
</c:forEach>

<c:set var="isForFrends" value="${post.status.value eq 'PUBLISHED_FRIENDS'}"/>

<c:set var="isDraft" value="${post.status.value eq 'DRAFT'}"/>

<c:set var="currLocale">
    <fmt:message key="locale.name"/>
</c:set>

<script type="text/javascript">

    var postId = '${postId}';
    var lang = '${currLocale}';

    $(document).ready(function () {
        var ct = "${fn:substring(categoryIds,1,1000)}";
        var rg = "${fn:substring(regionIds,1,1000)}";
        $.handParamToURL({
            ct: ct,
            rg: rg
        });
    });

    var onPostSubmit = function (btn) {
        $(btn).prop('disabled', true);

        var jsonData = (postId ? {
            id: postId
        } : {});

        var statusValue = null;
        if ($('#is_draft').is(':checked'))
            statusValue = 'DRAFT';
        else if ($('#is_for_frends').is(':checked'))
            statusValue = 'PUBLISHED_FRIENDS';
        else
            statusValue = 'PUBLISHED_ALL';

        jsonData.locale = lang;
        jsonData.name = $("#name_post").val();
        jsonData.summary = $("#desc_post").val();
        jsonData.description = $("#desc_full_post").val();
        $.extend(jsonData, {status: {value: statusValue}});

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

        var url = (postId ? 'rest/security/post_update'
                : 'rest/security/post_save');

        var callbackSuccess = function (data) {
            $(btn).prop('disabled', false);
            $(".error_message").html('<p class="green">' + locale.action.successEdit + '</p>');
        };

        var callbackError = function (error) {
            $(btn).prop('disabled', false);
            $(".error_message").html('<p class="red">' + error + '</p>');
        };

        $.postJSON(url, jsonData, callbackSuccess, callbackError);
    }
</script>

<div id="container">
    <div id="contents">

        <article id="article" class="deteil edit">
            <div class="date">
                ${post.created.text}<span><fmt:message key="post.title"/></span>
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
            </div>

            <input type="text" id="name_post" value="${post.name}" alt="<fmt:message key="content.name" />:">

            <div class="error_message"></div>

            <nav id="travel_nav">
                <a onClick="onPostSubmit(this)" class="button"><fmt:message key="page.action.save"/></a>
            </nav>

            <textarea id="desc_post" class="ckeditor" name="editor1"
                      alt="<fmt:message key="content.shortDescription" />:">${post.summary}</textarea>
            <textarea id="desc_full_post" class="ckeditor" name="editor2"
                      alt="<fmt:message key="content.fullDescription" />:">${post.description}</textarea>

            <c:if test="${not empty param.id}">

                <div class="del_article">
                    <a class="button"><fmt:message key="trip.action.delete"/></a>
                </div>
            </c:if>

        </article>
    </div>
    <!-- #content-->
</div>
<!-- #container-->

<aside id="sideRight">
    <tiles:insertDefinition name="categoryTree"/>
    <tiles:insertDefinition name="regionFilter"/>
</aside>