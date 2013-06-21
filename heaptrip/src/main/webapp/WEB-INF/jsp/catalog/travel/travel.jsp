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


<div id="container">
			<div id="contents">

				<article id="article" class="deteil">
					<div class="date">15.01.13<span>Путешествие</span><span class="for_frends">Для друзей</span></div>
					<div class="inf">
						<div class="left">
							<h2><a href="/">Из Рима в Барселону</a></h2>
							<h2 class="chernovik"><a href="/">Черновик Из Рима в Барселону</a></h2>
							<div>Автор:<span>ST. Peters Line (4,7)</span></div>
							<div>Категория:<span>Морская прогулка</span></div>
							<div>Регион:<span>Италия</span></div>						
						</div>
						<div class="right">
							<div>Доступно для:</div>
							<ul>
								<li class="ru"></li>
								<li class="yk"></li>
								<li class="fr"></li>
								<li class="du"></li>
							</ul>
						</div>
					</div>
					<nav id="travel_nav">
						<input type="button" value="Редактировать" class="button">
    					<ul><!--
    					    --><li><a href="${infoUrl}" class="active">Информация<span></span></a></li><!--
    					    --><li><a href="${mapsUrl}">Маршрут<span></span></a></li><!--
    					    --><li><a href="${photosUrl}">Фото<span></span></a></li><!--
    					    --><li><a href="${participantsUrl}">Участники<span></span></a></li><!--
    					    --><li><a href="${postsUrl}">Посты<span></span></a></li><!--
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

