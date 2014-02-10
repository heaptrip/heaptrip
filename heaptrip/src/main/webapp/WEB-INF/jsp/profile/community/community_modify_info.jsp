<%@ page import="com.heaptrip.domain.entity.LangEnum" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="langValues" value="<%=LangEnum.getValues()%>"/>

<c:choose>
    <c:when test="${empty param.ul}">
        <c:set var="currLocale">
            <fmt:message key="locale.name"/>
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


    $(document).ready(function () {
        var ct = "${fn:substring(categoryIds,1,1000)}";
        var rg = "${fn:substring(regionIds,1,1000)}";
        $.handParamToURL({
            ct: ct,
            rg: rg
        });
    });

    var onCommunitySubmit = function (btn) {

        $(btn).prop('disabled', true);

        var jsonData = {
            id: $.getParamFromURL().uid ? $.getParamFromURL().uid : null
        };

        jsonData.name = $("#community_name").val();
        jsonData.email = $("#community_email").val();
        jsonData.typeAccount = $("#community_type option:selected")[0].getAttribute('key');

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

        var url = null;

        if (jsonData.id)
            url = 'rest/security/community_update';
        else
            url = 'rest/security/community_save';

        var callbackSuccess = function (data) {
            window.location = 'profile.html?guid=' + $.getParamFromURL().guid;
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
                    <div class="my_avatar"><img src="<c:url value="/rest/image?imageId=${community.image.id}"/>"><a
                            href="/" class="button"><fmt:message key="page.action.uploadPhoto"/></a></div>

                    <a id="cke_46" class="cke_button cke_button__addimgserver cke_button_off " "="" href="javascript:void('Загрузить изображение')" title="Загрузить изображение" tabindex="-1" hidefocus="true" role="button" aria-labelledby="cke_46_label" aria-haspopup="false" onkeydown="return CKEDITOR.tools.callFunction(128,event);" onfocus="return CKEDITOR.tools.callFunction(129,event);" onmousedown="return CKEDITOR.tools.callFunction(130,event);" onclick="CKEDITOR.tools.callFunction(131,this);return false;"><span class="cke_button_icon cke_button__addimgserver_icon" style="background-image:url(http://localhost:8080/heaptrip/js/lib/ckeditor/plugins/addImgServer/icons/addImgServer.png?t=CAPD);background-position:0 0px;">&nbsp;</span><span id="cke_46_label" class="cke_button_label cke_button__addimgserver_label">Загрузить изображение</span></a>


                    <div class="my_inf">
                        <div class="my_name">
                            <input id="community_name" type="text" value="${community.name}"
                                   alt="<fmt:message key="community.name"/>">
                        </div>
                        <div class="my_name">
                            <input id="community_email" type="text" value="${community.email}"
                                   alt="<fmt:message key="accountProfile.email"/>">
                        </div>


                        <div class="my_location"><span><fmt:message key="account.type"/>: </span>
                            <select id="community_type">
                                <option key="CLUB"><fmt:message key="account.type.club"/></option>
                                <option key="COMPANY"><fmt:message key="account.type.company"/></option>
                                <option key="AGENCY"><fmt:message key="account.type.agency"/></option>
                            </select>
                        </div>



                        <div class="my_location"><span><fmt:message key="user.place"/>: </span>
                            <input id="location"
                                   reg_id="${community.accountProfile.location.id}"
                                   value="${community.accountProfile.location.data}"
                                   type="text"/>
                        </div>


                    </div>
                </div>

            </div>
            <div class="description">
                <textarea id="community_desc"
                          alt="<fmt:message key="content.description"/>:">${community.accountProfile.desc}</textarea>
            </div>


        </article>

    </div>

</div>


<aside id="sideRight">
    <tiles:insertDefinition name="categoryTree"/>
    <tiles:insertDefinition name="regionFilter"/>
</aside>




