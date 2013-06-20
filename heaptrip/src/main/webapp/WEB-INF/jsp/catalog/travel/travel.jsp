<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<c:set var="tripId"
	value='${param.id}' />

<h1>Travel <b>(id=${tripId})</b> </h1>


<c:url var="infoUrl" value="travel_info.html">
	<c:param name='id' value="${tripId}" />
</c:url>
<c:url var="mapsUrl" value="travel_maps.html">
	<c:param name='id' value="${tripId}" />
</c:url>
<c:url var="photosUrl" value="travel_photos.html">
	<c:param name='id' value="${tripId}" />
</c:url>
<c:url var="participantsUrl" value="travel_participants.html">
	<c:param name='id' value="${tripId}" />
</c:url>
<c:url var="postsUrl" value="travel_posts.html">
	<c:param name='id' value="${tripId}" />
</c:url>

<a href="${infoUrl}">info</a>
<a href="${mapsUrl}">maps</a>
<a href="${photosUrl}">photos</a>
<a href="${participantsUrl}">participants</a>
<a href="${postsUrl}">posts</a>

<tiles:insertAttribute name="travel_content" />