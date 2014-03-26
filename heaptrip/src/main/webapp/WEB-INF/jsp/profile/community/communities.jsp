<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<script id="communityTemplate" type="text/x-jsrender">

    <%--<article id="article">--%>
    <%--<div class="date">{{>created.text}}--%>
    <%--<span><fmt:message key="trip.list.title"/></span>--%>
    <%--</div>--%>
    <%--<div class="inf">--%>
    <%--<div class="left">--%>
    <%--<h2><a href="<c:url value="/travel_info.html?id={{>id}}"/>">{{>name}}</a></h2>--%>


    <%--<div class="tags">--%>


    <%--<a href="<c:url value="/pf-profile.html?guid={{>owner.id}}"/>">{{>owner.name}}<span>({{>owner.rating.value}})</span></a>--%>
    <%--</div>--%>


    <%--</div>--%>
    <%--<div class="right">--%>
    <%--{{if begin}}--%>
    <%--<div>--%>
    <%--<fmt:message key="page.date.period"/>:--%>
    <%--<span class="date">--%>
    <%--<fmt:message key="page.date.from"/> {{>begin.text}} <fmt:message key="page.date.to"/> {{>end.text)}}--%>
    <%--</span>--%>
    <%--</div>--%>
    <%--{{/if}}--%>
    <%--<div>--%>
    <%--<fmt:message key="content.place"/>:--%>
    <%--{{for regions}}--%>
    <%--<a onclick="$.handParamToURL({rg:'{{>id}}', ct : null, skip : null ,limit : null})">{{>data}}</a>--%>
    <%--{{/for}}--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--<div class="description">--%>
    <%--{{:summary}}--%>
    <%--</div>--%>
    <%--<div>--%>
    <%--<div class="tags">--%>
    <%--{{for categories}}--%>
    <%--<a onclick="$.handParamToURL({ct:'{{>id}}', rg : null, skip : null ,limit : null})">{{>data}}</a>--%>
    <%--{{/for}}--%>
    <%--</div>--%>
    <%--{{if price}}--%>
    <%--<div class="price">{{>price}}<fmt:message key="locale.currency"/></div>--%>
    <%--{{/if}}--%>

    <%--</div>--%>
    <%--<div>--%>
    <%--<div class="views"><fmt:message key="content.views"/>:<span>{{>views}}</span></div>--%>
    <%--<div class="comments"><fmt:message key="content.comments"/>:<span>{{>comments}}</span></div>--%>
    <%--<div class="wertung"><fmt:message key="content.wertung"/>:--%>
    <%--<div class="stars star{{>rating.stars}}"></div>--%>
    <%--<span>({{>rating.count}})</span></div>--%>
    <%--</div>--%>
    <%--</article>--%>


    <ul>
        <li class="participants_li community_func12">
            <div class="list_user_img"><img src="/1_small.jpg"></div>
            <div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div>
        </li>
    </ul>


</script>


<script type="text/javascript">

    $(window).bind("onPageReady", function (e, paramsJson) {
        getCommunitiesList(paramsJson);
    });

    var getCommunitiesList = function (paramsJson) {

        var url = 'rest/trips';

        var communitiesCriteria = {
            skip: paramsJson.skip ? paramsJson.skip : 0,
            limit: paramsJson.limit,
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

            $("#user_communities").html($("#communityTemplate").render(data.trips));
            $("#working_communities").html($("#communityTemplate").render(data.trips));
            $("#member_communities").html($("#communityTemplate").render(data.trips));
            $("#subscriber_communities").html($("#communityTemplate").render(data.trips));


            /*$('#paginator1').smartpaginator({
             totalrecords: 100,
             skip: paramsJson.skip
             });

             $('#paginator2').smartpaginator({
             totalrecords: 100,
             skip: paramsJson.skip
             });   */

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

        <article id="article" class="deteil edit">

            <c:if test='${not empty principal && empty catcher}'>
                <div class="inf">
                    <div class="right">
                        <a href="<c:url value="/community_modify_info.html"/>" class="button"><fmt:message
                                key="page.action.add"/></a>
                    </div>
                </div>
            </c:if>

            <div class="description">

                <div id="list_user_1" class="community" style="border-bottom: 1px solid #E2E6E5;">
                    <div class="list_user_inf people_title">
                        <c:choose>
                            <c:when test="${not empty catcher}">
                                <fmt:message key="user.owner"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="user.i.owner"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <span id="user_communities"></span>
                </div>

                <div id="list_user_2" class="community"  style="border-bottom: 1px solid #E2E6E5;">
                    <div class="list_user_inf people_title">
                        <c:choose>
                            <c:when test="${not empty catcher}">
                                <fmt:message key="user.employee"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="user.i.employee"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <%--<ul>--%>
                    <%--<li class="participants_li community_func13">--%>
                    <%--<div class="list_user_img"><img src="/1_small.jpg"></div>--%>
                    <%--<div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div>--%>
                    <%--</li>--%>
                    <%--</ul>--%>
                    <span id="working_communities"></span>
                </div>


                <%--<div class="pagination_mini">--%>
                <%--<div id="paginator2"></div>--%>
                <%--</div>--%>

                <div id="list_user_3" class="community"  style="border-bottom: 1px solid #E2E6E5;">
                    <div class="list_user_inf people_title">
                        <c:choose>
                            <c:when test="${not empty catcher}">
                                <fmt:message key="user.member"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="user.i.member"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <span id="member_communities"></span>
                    <%--<ul>--%>
                    <%--<li class="participants_li community_func14">--%>
                    <%--<div class="list_user_img"><img src="/1_small.jpg"></div>--%>
                    <%--<div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div>--%>
                    <%--</li>--%>
                    <%--</ul>--%>
                </div>

                <div id="list_user_4" class="community">
                    <div class="list_user_inf people_title">
                        <c:choose>
                            <c:when test="${not empty catcher}">
                                <fmt:message key="user.subscriber"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="user.i.subscriber"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <%--<ul>--%>
                    <%--<li class="participants_li community_func15">--%>
                    <%--<div class="list_user_img"><img src="/1_small.jpg"></div>--%>
                    <%--<div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div>--%>
                    <%--</li>--%>
                    <%--</ul>--%>
                    <span id="subscriber_communities"></span>
                </div>

                <%--<div class="pagination_mini">--%>
                <%--<div id="paginator1"></div>--%>
                <%--</div>--%>
            </div>
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




