<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

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
								<h2><a href="/">${trip.name}</a></h2>
							</c:if>
							<c:if test = "${trip.status.value == 'DRAFT'}">
								<h2 class="chernovik"><a href="/"><fmt:message key="content.draft" /> ${trip.name}</a></h2>
							</c:if>

							<div><fmt:message key="content.author" />:<span>${trip.owner.name} (${trip.owner.rating})</span></div>
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
									<li class="${lang}"></li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<nav id="travel_nav">
						<input type="button" value="<fmt:message key="page.action.edit" />" class="button">
    					<ul><!--
    					    --><li><a href="${infoUrl}" class="active"><fmt:message key="content.information" /><span></span></a></li><!--
    					    --><li><a href="${mapsUrl}"><fmt:message key="trip.route" /><span></span></a></li><!--
    					    --><li><a href="${photosUrl}"><fmt:message key="content.photo" /><span></span></a></li><!--
    					    --><li><a href="${participantsUrl}"><fmt:message key="content.participants" /><span></span></a></li><!--
    					    --><li><a href="${postsUrl}"><fmt:message key="post.list.title" /><span></span></a></li><!--
    					--></ul>
					</nav>
					
					
<tiles:insertAttribute name="travel_content" />
				
			</div><!-- #content-->
		</div><!-- #container-->

		<aside id="sideRight">
			<div id="widget1" class="widget">
				<div class="zag">Ближайшие путешествия</div>
	    		<ul>
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">Путешествие 0111</a></div>
	    		    	<div class="date">с 12.03.2013 по 23.03.13</div>
	    		    </li>
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">Путешествие 0111</a></div>
	    		    	<div class="date">с 12.03.2013 по 23.03.13</div>
	    		    </li>	    		    
   				</ul>				
    		</div>
			<div id="widget2" class="widget">
				<div class="zag">Обсуждаемые путешествия</div>
	    		<ul>
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">Путешествие 0111</a><span class="comments">12</span></div>
	    		    </li>
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">Путешествие 0111</a><span class="comments">12</span></div>
	    		    </li>	    		    
   				</ul>				
    		</div>
			<div id="widget3" class="widget">
				<div class="zag">Похожие путешествия</div>
	    		<ul>
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">Путешествие 0111</a><span class="comments">12</span></div>
	    		    </li>
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">Путешествие 0111</a><span class="comments">12</span></div>
	    		    </li>	    		    
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">Путешествие 0111</a><span class="comments">12</span></div>
	    		    </li>
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">Путешествие 0111</a><span class="comments">12</span></div>
	    		    </li>	    		    
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">Путешествие 0111</a><span class="comments">12</span></div>
	    		    </li>
	    		    <li>
	    		    	<div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
	    		    	<div class="name_post"><a href="/">Путешествие 0111</a><span class="comments">12</span></div>
	    		    </li>	    		    
   				</ul>				
    		</div>      				
		</aside><!-- #sideRight -->

