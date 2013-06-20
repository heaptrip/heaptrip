<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<h1>Travel <b>(id=<%= request.getParameter("id") %>)</b> </h1>

<a href='<c:url value="travel_info.html"/>'>info</a>
<a href='<c:url value="travel_maps.html"/>'>maps</a>
<a href='<c:url value="travel_photos.html"/>'>photos</a>
<a href='<c:url value="travel_participants.html"/>'>participants</a>
<a href='<c:url value="travel_posts.html"/>'>posts</a>

<tiles:insertAttribute name="travel_content" />