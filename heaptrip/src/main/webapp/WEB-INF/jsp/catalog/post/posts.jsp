<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<script id="postTemplate" type="text/x-jsrender">

    <article id="article">
        <div class="date">{{>created.text}}
            <span><fmt:message key="post.list.title"/></span>
        </div>
        <div class="inf">
            <div class="left">
                <h2><a href="<c:url value="/post.html?id={{>id}}"/>">{{>name}}</a></h2>


                <div class="tags">


                    <a href="<c:url value="/pf-profile.html?guid={{>owner.id}}"/>">{{>owner.name}}<span>({{>owner.rating.value}})</span></a>
                </div>


            </div>
            <div class="right">
                <div>
                    <fmt:message key="content.place"/>:
                    {{for regions}}
                    <a onclick="$.handParamToURL({rg:'{{>id}}', ct : null, skip : null ,limit : null})">{{>data}}</a>
                    {{/for}}
                </div>
            </div>
        </div>
        <div class="description">
            {{:summary}}
        </div>
        <div>
            <div class="tags">
                {{for categories}}
                <a onclick="$.handParamToURL({ct:'{{>id}}', rg : null, skip : null ,limit : null})">{{>data}}</a>
                {{/for}}
            </div>

        </div>
        <div>
            <div class="views"><fmt:message key="content.views"/>:<span>{{>views}}</span></div>
            <div class="comments"><fmt:message key="content.comments"/>:<span>{{>comments}}</span></div>
            <div class="wertung"><fmt:message key="content.wertung"/>:
                <div class="stars star{{>rating.stars}}"></div>
                <span>({{>rating.count}})</span></div>
        </div>
    </article>

</script>

<div id="container">
    <div id="contents"></div>
    <tiles:insertDefinition name="pagination"/>
    <div id="paginator"></div>
</div>

<aside id="sideRight">
    <tiles:insertDefinition name="categoryTreeWithBtn"/>
    <tiles:insertDefinition name="regionFilterWithBtn"/>
</aside>

<script type="text/javascript">

    $(window).bind("onPageReady", function (e, paramsJson) {
        getPostsList(paramsJson);
    });

    var getPostsList = function (paramsJson) {

        var url = 'rest/posts';

        var postCriteria = {
            skip: paramsJson.paginator  ? paramsJson.paginator.skip : 0,
            limit: paramsJson.paginator ? paramsJson.paginator.limit : null,
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
            $("#contents").html($("#postTemplate").render(data.posts));
            $('#paginator').smartpaginator({
                totalrecords: data.count,
                skip: paramsJson.paginator ? paramsJson.paginator.skip : 0
            });
        };

        var callbackError = function (error) {
            $.alert(error);
        };

        $.postJSON(url, postCriteria, callbackSuccess, callbackError);

    };

</script>
