<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script id="tripTemplate" type="text/x-jsrender">

    <article id="article">
        <div class="date">{{>created.text}}
            <span><fmt:message key="trip.list.title"/></span>
        </div>
        <div class="inf">
            <div class="left">
                <h2><a href="<c:url value="/ct/travel_info?id={{>id}}"/>">{{>name}}</a></h2>

                <div class="tags">
                    <a href="<c:url value="/pf/{{if owner.typeAccount == 'USER'}}profile{{else}}community{{/if}}?guid={{>owner.id}}"/>">{{>owner.name}}<span>({{>owner.rating.value}})</span></a>
                </div>
            </div>
            <div class="right">
                <%--Если, это мои избранные--%>
                <c:if test='${empty catcher && mode eq "FAVORITE"}'>
                    <a onclick="onRemoveFavoriteSubmit('{{>id}}', $(this))" class="button"><fmt:message
                            key="page.action.delete"/></a>
                </c:if>
                {{if begin.text}}
                <div>
                    <fmt:message key="page.date.period"/>:
						<span class="date">
                            <fmt:message key="page.date.from"/>
                            {{>begin.text}}
                            {{if end.text}}<fmt:message key="page.date.to"/> {{>end.text)}}
                            {{/if}}
                        </span>
                </div>
                {{/if}}
                {{if regions}}
                <div>
                    <fmt:message key="content.place"/>:
                    {{for regions}}
                    <a onclick="$.handParamToURL({rg:'{{>id}}', ct : null, skip : null ,limit : null})">{{>data}}</a>
                    {{/for}}
                </div>
                {{/if}}
            </div>
        </div>
        <div class="description">
            {{:summary}}
        </div>
        <div>
            {{if categories}}
            <div class="tags">
                {{for categories}}
                <a onclick="$.handParamToURL({ct:'{{>id}}', rg : null, skip : null ,limit : null})">{{>data}}</a>
                {{/for}}
            </div>
            {{/if}}
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
    <div id="contents">

        <c:if test='${not empty principal}'>
            <%--Если, это мой--%>
            <c:if test='${empty catcher && fn:contains(url, "/my")}'>
                <article id="article" class="deteil">
                    <div class="inf">
                        <div class="right">
                            <a href="<c:url value="/ct/travel_modify_info"/>"
                               class="button"><fmt:message
                                    key="page.action.add"/></a>
                        </div>
                    </div>
                </article>
            </c:if>
            <%--Если, это моего сообщества--%>
            <c:if test="${not empty catcher && catcher.typeAccount ne 'USER' && profileServiceWrapper.isUserOwnsCommunity(principal.id,catcher.id)}">
                <article id="article" class="deteil">
                    <div class="inf">
                        <div class="right">
                            <a href="<c:url value="/ct/travel_modify_info?guid=${catcher.id}"/>"
                               class="button"><fmt:message
                                    key="page.action.add"/></a>
                        </div>
                    </div>
                </article>
            </c:if>
        </c:if>


        <span id="contents_span"></span>
    </div>
    <tiles:insertDefinition name="pagination"/>
    <div id="paginator"></div>
</div>

<aside id="sideRight">
    <c:if test="${mode eq 'CONTENT' }">
        <tiles:insertDefinition name="categoryTreeWithBtn"/>
        <tiles:insertDefinition name="regionFilterWithBtn"/>
    </c:if>
</aside>

<script type="text/javascript">

    $(window).bind("onPageReady", function (e, paramsJson) {
        getTripsList(paramsJson);
    });

    var getTripsList = function (paramsJson) {

        var tripCriteria = {
            skip: paramsJson.paginator ? paramsJson.paginator.skip : 0,
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

        var url = '../rest/trips';


        if (window.principal) {
            tripCriteria.userId = window.principal.id;
            if (window.mode == 'MY') {
                url = '../rest/my/trips';
                tripCriteria.relation = 'OWN';
            } else if (window.mode == 'FAVORITE') {
                url = '../rest/my/trips';
                tripCriteria.relation = 'FAVORITES';
            }
        }

        if (window.catcher) {
            url = '../rest/foreign/trips';
            tripCriteria.accountId = window.catcher.id;
            tripCriteria.relation = 'OWN';
        }

        var callbackSuccess = function (data) {
            $("#contents_span").html($("#tripTemplate").render(data.trips));

            // $.alert(data.count)

            $('#paginator').smartpaginator({
                totalrecords: data.count,
                skip: paramsJson.paginator ? paramsJson.paginator.skip : 0
            });
        };

        var callbackError = function (error) {
            $.alert(error);
        };

        $.postJSON(url, tripCriteria, callbackSuccess, callbackError);

    };

    var onRemoveFavoriteSubmit = function (tripId, btn) {

        $(btn).prop('disabled', true);

        var url = '../rest/remove_favorite';

        var callbackSuccess = function (data) {
            location.reload();
            $.alert("Success");
        };

        var callbackError = function (error) {
            $.alert("ERROR");
        };

        $.postJSON(url, tripId, callbackSuccess, callbackError);
    };


</script>
