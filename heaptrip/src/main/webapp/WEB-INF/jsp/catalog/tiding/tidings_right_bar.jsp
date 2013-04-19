<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<aside id="sideRight">

<tiles:insertDefinition name="categoryTree" />

<tiles:insertDefinition name="regionFilter" />

	
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
		</aside><!-- #sideRight -->
