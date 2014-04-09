<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div class="description">
	${trip.description}
</div>

<div class="table_inf">
	<table>
		<thead>
			<tr>
				<th><fmt:message key="trip.status" /></th>
				<th><fmt:message key="trip.period" /></th>
				<th><fmt:message key="trip.cost" /></th>
				<th><fmt:message key="trip.minmax" /></th>
				<th><fmt:message key="trip.enrolled" /></th>
				<th><fmt:message key="page.action" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${trip.route.map}" var="map">
				<tr>
				<td><fmt:message key="trip.status.${scheduleItem.status.value}"  /> ${scheduleItem.status.text}</td>
				<td><fmt:message key="page.date.from" /> ${scheduleItem.begin.text}<br /><fmt:message key="page.date.to" /> ${scheduleItem.end.text}</td>
				<td>${scheduleItem.price.value} ${scheduleItem.price.currency}</td>
				<td>${scheduleItem.min} / ${scheduleItem.max}</td>
				<td>${scheduleItem.members}</td>
				<td><a class="button">todo</a></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="dop_inf">
	<div class="views">
		<fmt:message key="content.views" />
		:<span>${trip.views}</span>
	</div>
	<div class="comments">
		<fmt:message key="content.comments" />
		:<span>${trip.comments}</span>
	</div>
	<div class="wertung">
		<fmt:message key="content.wertung" />:
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
	</div><a class="button"><fmt:message key="content.toFavorit" /></a></div>
</article>

<br>

<tiles:insertDefinition name="comments" />




