<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<c:set var="tripId" value='${param.id}'/>

<script type="text/javascript">
    var tripId = '${tripId}';

    var onFavoriteSubmit = function (btn) {

        $(btn).prop('disabled', true);

        var url = '../rest/add_favorite';

        var callbackSuccess = function (data) {
            $(btn).remove();
            $.alert("Success");
        };

        var callbackError = function (error) {
            $(btn).prop('disabled', false);
            $.alert("ERROR");
        };

        $.postJSON(url, tripId, callbackSuccess, callbackError);
    };

    $(document).ready(function () {

        $('.request_participant_btn').click(function () {
            var btn = $(this);
            $.doAuthenticationUserAction(function () {

                var url = '../rest/security/trip/send_request_trip_participant';

                var params = {};
                params.userId = window.principal.id;
                params.tripId = $.getParamFromURL().id;
                params.scheduleId = $(btn).closest('[name="schedule_item"]')[0].id;

                var callbackSuccess = function (data) {
                    // TODO : voronenko refresh
                    window.location = window.location.href
                };

                var callbackError = function (error) {
                    $.alert(error)
                };

                $.postJSON(url, params, callbackSuccess, callbackError);

            });
        });
    });
</script>

<div class="description">
    ${trip.description}
</div>

<c:if test="${not empty trip.schedule}">

    <div class="table_inf">
        <table>
            <thead>
            <tr>
                <th><fmt:message key="trip.status"/></th>
                <th><fmt:message key="trip.period"/></th>
                <th><fmt:message key="trip.cost"/></th>
                <th><fmt:message key="trip.minmax"/></th>
                <th><fmt:message key="trip.enrolled"/></th>
                <th><fmt:message key="page.action"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${trip.schedule}" var="scheduleItem">
                <tr id="${scheduleItem.id}" name="schedule_item">
                    <td><fmt:message key="trip.status.${scheduleItem.status.value}"/> ${scheduleItem.status.text}</td>
                    <td><fmt:message key="page.date.from"/> ${scheduleItem.begin.text}<br/><fmt:message
                            key="page.date.to"/> ${scheduleItem.end.text}</td>
                    <td>${scheduleItem.price.value} ${scheduleItem.price.currency}</td>
                    <td>${scheduleItem.min} / ${scheduleItem.max}</td>
                    <td>${scheduleItem.members}</td>
                    <td><a class="button request_participant_btn">send request trip participant</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>
<div class="dop_inf">
    <div class="views">
        <fmt:message key="content.views"/>
        :<span>${trip.views}</span>
    </div>
    <div class="comments">
        <fmt:message key="content.comments"/>
        :<span>${trip.comments}</span>
    </div>
    <div class="wertung">
        <fmt:message key="content.wertung"/>:
        <c:choose>
            <c:when test="${not trip.rating.locked}">
                <div contentType="TRIP" class="stars star${trip.rating.stars} activ">
                    <input type="hidden" value="${trip.rating.stars}">
                </div>
            </c:when>
            <c:otherwise>
                <div class="stars star${trip.rating.stars}"></div>
            </c:otherwise>
        </c:choose>
        <span>(${trip.rating.count})</span>
    </div>
    <c:if test='${not empty principal && trip.enableFavorite}'>
        <a onClick="onFavoriteSubmit(this)" class="button"><fmt:message key="content.toFavorit"/></a>
    </c:if>
</div>
</article>

<br>

<tiles:insertDefinition name="comments"/>




