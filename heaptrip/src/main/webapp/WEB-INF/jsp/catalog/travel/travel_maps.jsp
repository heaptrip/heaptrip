<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="tabs tabs_interactiv">
    <ul><!--
						
    						-->
        <li><span class="activ"><fmt:message key="content.webMaps"/></span>

            <div class="tabs_content">
                <div id="map_canvas" class="smallmap" style="height:450px;"></div>
                <div id="map_data" style="display: none;">${trip.route.map}</div>
            </div>
        </li>
        <!--
                                    -->
        <li><span><fmt:message key="content.maps"/></span>

            <div class="tabs_content">
                <ul><!--
    										-->
                    <li><a href="/map"><img src="/map/map1.jpg"></a></li>
                    <!--
                                                                -->
                    <li><a href="/map"><img src="/map/map1.jpg"></a></li>
                    <!--
                                                                -->
                    <li><a href="/map"><img src="/map/map1.jpg"></a></li>
                    <!--
                                                                -->
                    <li><a href="/map"><img src="/map/map1.jpg"></a></li>
                    <!--
                                                                -->
                    <li><a href="/map"><img src="/map/map1.jpg"></a></li>
                    <!--
                                                                -->
                    <li><a href="/map"><img src="/map/map1.jpg"></a></li>
                    <!--
                                                            -->
                </ul>
            </div>
        </li>
        <!--
                                --></ul>
</div>

<div class="description">${trip.route.text}</div>


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
        <fmt:message key="content.wertung"/>
        :
        <div class="stars star${trip.rating.stars} activ">
            <input type="hidden" value="${trip.rating.stars}">
        </div>
        <span>(${trip.rating.count})</span>
    </div>
    <a class="button"><fmt:message key="content.toFavorit"/></a></div>
</article>

<tiles:insertDefinition name="comments"/>