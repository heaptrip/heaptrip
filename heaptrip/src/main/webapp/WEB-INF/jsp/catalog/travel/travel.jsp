<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<c:set var="tripId"
	value='${param.id}' />

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

<c:url var="tripEditUrl" value="travel_modify_info.html">
	<c:param name='id' value="${tripId}" />
	<c:param name='tb' value='${fn:contains(url,"info") ? "info":"map" }' />
	
	
</c:url>



<div id="container">
			<div id="contents">
				<article id="article" class="deteil">
					<div class="date">${trip.created.text}
						<span><fmt:message key="trip.title" /></span>
						<c:if test = "${trip.status.value == 'PUBLISHED_FRIENDS'}">
							<span class="for_frends"><fmt:message key="content.forFrends" /></span>
						</c:if>
					</div>
					<div class="inf">
						<div class="left">
							
							<c:if test = "${trip.status.value != 'DRAFT'}">
								<h2>${trip.name}</h2>
							</c:if>
							<c:if test = "${trip.status.value == 'DRAFT'}">
								<h2 class="chernovik"><a href="/"><fmt:message key="content.draft" /> ${trip.name}</a></h2>
							</c:if>

							<div><fmt:message key="content.author" />:<a href="<c:url value="/pf-profile.html?guid=${trip.owner.id}"/>"><span >${trip.owner.name} (${trip.owner.rating.value})</span></a></div>
							<div><fmt:message key="content.category" />:
								<c:forEach items="${trip.categories}" var="category">
									<span>${category.data}</span>
								</c:forEach>
							</div>
							<div><fmt:message key="content.region" />:
								<c:forEach items="${trip.regions}" var="region">
									<span>${region.data}</span>
								</c:forEach>
							</div>						
						</div>
						<div class="right">
							<div><fmt:message key="content.available" />:</div>
							<ul>
								<c:forEach items="${trip.langs}" var="lang">
									<li class="${lang}">
									<a onClick="$.putGETParamToURL('ul','${lang}')" class="${lang} lang" ></a>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<nav id="travel_nav">

                    <c:if test='${principal.id eq trip.owner.id }'>
						<input type="button" onClick="window.location = '${tripEditUrl}'" value="<fmt:message key="page.action.edit" />" class="button">
                    </c:if>
						
    					<ul><!--
    					    --><li><a href="${infoUrl}" class='${fn:contains(url, "info") ? "active":"" }'><fmt:message key="content.information" /><span></span></a></li><!--
    					    --><li><a href="${mapsUrl}" class='${fn:contains(url, "map") ? "active":"" }'><fmt:message key="trip.route" /><span></span></a></li><!--
    					    --><li><a href="${photosUrl}" class='${fn:contains(url, "photo") ? "active":"" }'><fmt:message key="content.photo" /><span></span></a></li><!--
    					    --><li><a href="${participantsUrl}" class='${fn:contains(url, "participant") ? "active":"" }'><fmt:message key="content.participants" /><span></span></a></li><!--
    					    --><li><a href="${postsUrl}" class='${fn:contains(url, "post") ? "active":"" }'><fmt:message key="post.list.title" /><span></span></a></li><!--
    					--></ul>
					</nav>
					
					
<tiles:insertAttribute name="travel_content" />
				
			</div><!-- #content-->
		</div><!-- #container-->

		<aside id="sideRight">
			<div id="widget1" class="widget">
				<div class="zag">TODO</div>
	    		<ul>
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">todo</a></div>
	    		    	<div class="date">todo 23.03.13</div>
	    		    </li>
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">todo</a></div>
	    		    	<div class="date">todo 23.03.13</div>
	    		    </li>	    		    
   				</ul>				
    		</div>
			<div id="widget2" class="widget">
				<div class="zag">TODO</div>
	    		<ul>
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">todo 0111</a><span class="comments">12</span></div>
	    		    </li>
	    		  <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">todo 0111</a><span class="comments">12</span></div>
	    		    </li>    		    
   				</ul>				
    		</div>
			<div id="widget3" class="widget">
				<div class="zag">TODO</div>
	    		<ul>
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">todo 0111</a><span class="comments">12</span></div>
	    		    </li>
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">todo 0111</a><span class="comments">12</span></div>
	    		    </li>
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">todo 0111</a><span class="comments">12</span></div>
	    		    </li>
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">todo 0111</a><span class="comments">12</span></div>
	    		    </li>
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">todo 0111</a><span class="comments">12</span></div>
	    		    </li>
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">todo 0111</a><span class="comments">12</span></div>
	    		    </li>	    		    
   				</ul>				
    		</div>      				
		</aside><!-- #sideRight -->

