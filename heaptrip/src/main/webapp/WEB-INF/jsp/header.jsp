<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<a id="logo"></a>

<tiles:insertAttribute name="account" />


<script type="text/javascript">
 
	var onLocaleChange = function(locale) {
		var newUrl = $.param.querystring( window.location.href, 'locale='+ locale );
		window.location = newUrl;
	};
	
</script>

<c:set var="curr_locale"><fmt:message key="locale.name"/></c:set> 

<div id="language">
	<div id="language_now">
		<span id="language_show"></span>
		<span class='<fmt:message key="locale.name"/>'></span>
		<fmt:message key="locale.current" />
	</div>
	<ul>
	 <c:if test="${curr_locale!='ru'}">
		<li><a onClick = "onLocaleChange('ru')" href='#'><span class="ru"></span> <fmt:message key="locale.ru" /></a></li>
	 </c:if>
	  <c:if test="${curr_locale!='en'}">
		<li><a onClick = "onLocaleChange('en')" href='#'><span class="en"></span> <fmt:message key="locale.en" /></a></li>
	 </c:if>
	  <c:if test="${curr_locale!='du'}">
		<li><a onClick = "onLocaleChange('du')" href='#'><span class="du"></span> <fmt:message key="locale.du" /></a></li>
	 </c:if>
	  <c:if test="${curr_locale!='fr'}">
		<li><a onClick = "onLocaleChange('fr')" href='#'><span class="fr"></span> <fmt:message key="locale.fr" /></a></li>
	 </c:if>
	  <c:if test="${curr_locale!='yk'}">
		<li><a onClick = "onLocaleChange('yk')" href='#'><span class="yk"></span> <fmt:message key="locale.yk" /></a></li>
	 </c:if>
	  <c:if test="${curr_locale!='sw'}">
		<li><a onClick = "onLocaleChange('sw')" href='#'><span class="sw"></span> <fmt:message key="locale.sw" /></a></li>
	 </c:if>
	 
	</ul>
</div>

<tiles:insertAttribute name="search" />
