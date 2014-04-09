<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<script id="tripTemplate" type="text/x-jsrender">

    <article id="article">
        <div class="date">{{>created.text}}
            <span><fmt:message key="trip.list.title"/></span>
        </div>
        <div class="inf">
            <div class="left">
                <h2><a href="<c:url value="/travel_info.html?id={{>id}}"/>">{{>name}}</a></h2>

                <div class="tags">

                    <a href="<c:url value="/pf-{{if owner.typeAccount == 'USER'}}profile{{else}}community{{/if}}.html?guid={{>owner.id}}"/>">{{>owner.name}}<span>({{>owner.rating.value}})</span></a>
                </div>
            </div>
            <div class="right">
                {{if begin}}
                <div>
                    <fmt:message key="page.date.period"/>:
						<span class="date">
							<fmt:message key="page.date.from"/> {{>begin.text}} <fmt:message key="page.date.to"/> {{>end.text)}}
						</span>
                </div>
                {{/if}}
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
            {{if price}}
            <div class="price">{{>price}}<fmt:message key="currency.current"/></div>
            {{/if}}

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
        getContentsList(paramsJson);
    });

    var getContentsList = function (paramsJson) {

        var contentCriteria = {
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

        var url = 'rest/news';


        if (window.principal != null && window.principal.id != null) {
            contentCriteria.userId = window.principal.id;
            if (window.mode == 'MY') {
                url = 'rest/my/news';
                contentCriteria.relation = 'OWN';
            } else if (window.mode == 'FAVORITE') {
                url = 'rest/my/news';
                contentCriteria.relation = 'FAVORITES';
            }
        }

        if (window.catcher != null && window.catcher.id != null) {
            url = 'rest/foreign/news';
            contentCriteria.accountId = window.catcher.id;
            contentCriteria.relation = 'OWN';
        }

        var callbackSuccess = function (data) {
            $("#contents").html($("#tripTemplate").render(data.contents));
            $('#paginator').smartpaginator({
                totalrecords: data.count,
                skip: paramsJson.skip
            });
        };

        var callbackError = function (error) {
            alert(error);
        };

        $.postJSON(url, contentCriteria, callbackSuccess, callbackError);
    };

</script>
