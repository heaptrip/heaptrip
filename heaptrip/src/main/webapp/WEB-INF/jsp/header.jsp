<%@page import="com.heaptrip.domain.entity.LangEnum"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<c:set var="curr_locale"><fmt:message key="locale.name"/></c:set>

<a id="logo"></a>

<tiles:insertAttribute name="account" />





 

<c:set var="langValues" value="<%=LangEnum.getValues()%>"/>

<div id="language">
	<div id="language_now">
		<span id="language_show"></span>
		<span class='<fmt:message key="locale.name"/>'></span>
		<fmt:message key="locale.current" />
	</div>
	<ul>
	
	<c:forEach items="${langValues}" var="langValue">
   		<c:if test="${curr_locale ne langValue}">
		<li><a onClick = "onLocaleChange('${langValue}')" ><span class="${langValue}"></span> <fmt:message key="locale.${langValue}" /></a></li>
	 </c:if>
   		
	</c:forEach>
	 
	</ul>
</div>

<tiles:insertAttribute name="search" />
