<%@ page import="com.heaptrip.domain.entity.LangEnum"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<c:set var="langValues" value="<%=LangEnum.getValues()%>"/>

<c:set var="currLocale"><fmt:message key="locale.name"/></c:set> 


<c:set var="tripId"	value='${param.id}' />

<c:forEach items="${trip.categories}" var="category" varStatus="stat">
  <c:set var="categoryIds" value="${categoriesIds },${category.id}" />
</c:forEach>

<c:forEach items="${trip.regions}" var="region" varStatus="stat">
  <c:set var="regionIds" value="${regionIds },${region.id}" />
</c:forEach>

<c:set var="isForFrends" value="${trip.status.value eq 'PUBLISHED_FRIENDS'}" />
<c:set var="isDraft" value="${trip.status.value eq 'DRAFT'}" />

<script type="text/javascript">

	$(document).ready(function() {
		var ct = "${fn:substring(categoryIds,1,1000)}";
		var rg = "${fn:substring(regionIds,1,1000)}";
		$.handParamToURL({ct:ct,rg:rg});
	});
	
	var onTripSubmit = function() {

		// tripInfo
		var jsonData = {
			description : $("#desc_full_post").val(),
			summary : $("#desc_post").val()
		};

		var schedule = []; 

		$('#schedule_table > tbody  > tr').each(function(iTR,tr) {  
		    var item = {}; 
		    $(this).children('td').each(function(iTD,td) {
		        var cellInps =  $(this).children('input');
		        
		        switch (iTD) {
		  		case 0:
		    		item.begin = {};
		            item.begin.value =  $("#"+ cellInps[0].id).datepicker('getDate').getTime();
		            item.end = {};
		            item.end.value =  $("#"+ cellInps[1].id).datepicker('getDate').getTime();
		    	break;
		  		case 1:
		  		    item.price = {};
		  		    item.price.currency = cellInps[0].getAttribute('key');
		  		    item.price.value = cellInps[0].value;
		  		break;
		  		case 2:
		  		    item.min = cellInps[0].value;
		  		break;
		  		case 3:
		  		    item.max = cellInps[0].value;
		  		break;
		  		default:break;
		        }
		        
		       
		    });
		    
		    schedule.push(item);
		    
		});

		jsonData.schedule = schedule;
				
		 console.log( jsonData );
		
		
		
		var url = 'rest/tripSubmit';

	

		var callbackSuccess = function(data) {
			//var domain =  $("#email").val().replace(/.*@/, ""); 
			//window.location = 'confirmation.html?domain=' + domain;
		};

		var callbackError = function(error) {
			//$("#error_message #msg").text(error);
		};

		$.postJSON(url, jsonData, callbackSuccess, callbackError);

	};
	
</script>

<div id="container">
	<div id="contents">
		<article id="article" class="deteil edit">
			<div class="date">${trip.created.text}<span><fmt:message key="trip.title" /></span></div>
				<div class="inf">
					<div class="left">
						<ul>
							<li><input type="checkbox" checked="${isDraft}"><label><fmt:message key="content.draft" /></label></li>
							<li><input type="checkbox" checked="${isForFrends}"><label><fmt:message key="content.forFrends" /></label></li>
						</ul>					
					</div>
	
					<div class="right">
						<div><fmt:message key="content.available" />:</div>
							<c:choose>
								<c:when test="${empty tripId}">
									<ul>
			 							<li class="del_list_lang"><a class="del_lang lang" href="/"></a></li>
			 					   			<li><a class="${currLocale} lang" href="/"></a></li>
										<li class="add_list_lang"><a class="add_lang lang" href="/"></a>
											<ul>	
												<c:forEach items="${langValues}" var="langValue">
													<c:if test="${currLocale ne langValue}">
   														<li><a class="${langValue} lang" href="/"></a></li>
   													</c:if>	
												</c:forEach>
											</ul>
										</li>
									</ul>
								</c:when>
								<c:otherwise>
									<ul>
			 							<li class="del_list_lang"><a class="del_lang lang" href="/"></a></li>
			 						   		<c:forEach items="${trip.langs}" var="lang">
												<c:choose>
													<c:when test="${currLocale eq lang}">
														<li class="activ_lang">
													</c:when>
													<c:otherwise>
														<li>
													</c:otherwise>
    												</c:choose>
    														<a class="${lang} lang" href="/"></a>
    													</li>											
											</c:forEach>
			 					   		<li class="add_list_lang"><a class="add_lang lang" href="/"></a>
											<ul>
												<c:set var="joinLangValues" value="${fn:join(trip.langs, ',')}"/>
												<c:forEach items="${langValues}" var="langValue">
													<c:if test="${fn:contains(joinLangValues, langValue) ne true}">
   														<li><a class="${langValue} lang" href="/"></a></li>
   													</c:if>
												</c:forEach>
											</ul>
										</li>
									</ul>
      							</c:otherwise>
    					</c:choose>	
					</div>
				</div>
				<input type="text" id="name_post" value="${trip.name}" alt="<fmt:message key="content.name" />:">
				<nav id="travel_nav">
					<a onClick="onTripSubmit()" class="button"><fmt:message key="page.action.save" /></a>
    				<ul><!--
    				    --><li><a href="/travel_edit_info.html" class="active"><fmt:message key="content.information" /><span></span></a></li><!--
    				    --><li><a href="/travel_edit_maps.html"><fmt:message key="trip.route" /><span></span></a></li><!--
    				    --><li><a href="/travel_edit_photo.html"><fmt:message key="content.photo" /><span></span></a></li><!--
    				    --><li><a href="/travel_edit_participants.html"><fmt:message key="content.participants" /><span></span></a></li><!--
    				    --><li><a href="/travel_edit_posts.html"><fmt:message key="post.list.title" /><span></span></a></li><!--
    				--></ul>
				</nav>					
				<div class="description">
					<div id="img_load"><img src="<c:url value="/rest/image?imageId=${trip.image}"/>" width="300"></div>
					<div id="img_load_button">
						<a href="/" class="button"><fmt:message key="page.action.uploadPhoto" /></a>
						<a href="/" class="button"><fmt:message key="page.action.albumSelect" /></a>
					</div>
				</div>	
					<textarea id="desc_post"  alt="<fmt:message key="content.shortDescription" />:">${trip.summary}</textarea>
					<textarea id="desc_full_post" alt="<fmt:message key="content.fullDescription" />:">${trip.description}</textarea>
				<div class="table_inf">
					<table id=schedule_table>
						<thead><tr>
							<th><fmt:message key="trip.period" /></th>
							<th class="price_th"><fmt:message key="trip.cost" /></th>
							<th><fmt:message key="content.participants.min" /></th>
							<th><fmt:message key="content.participants.max" /></th>
							<th><fmt:message key="page.action" /></th>
						</tr></thead>
						<tbody>
							<c:choose>
								<c:when test="${empty tripId}">
      								<tr>
										<td>
											<fmt:message key="page.date.from" />
										 	<input type="text" class="datepicker">
										 	<br/>
										 	<fmt:message key="page.date.to" /> 
										 	<input type="text" class="datepicker"></td>
										<td class="price_td">
											<input type="text" key="<fmt:message key="currency.name" />">
											<div class="currency"><span><fmt:message key="currency.current" /></span>
												<ul>
													<li key="RUB"><fmt:message key="currency.RUB" /></li>
													<li key="USD"><fmt:message key="currency.USD" /></li>
													<li key="EUR"><fmt:message key="currency.EUR" /></li>
												</ul>
											</div>
										</td>
										<td><input type="text"></td>
										<td><input type="text"></td>
										<td>
											<a class="button" func="4">
											<fmt:message key="page.action.delete" />
											</a>
										</td>
									</tr>
								</c:when>
      							<c:otherwise>
      								<c:forEach items="${trip.schedule}" var="scheduleItem">
      						      		<tr>
											<td>
												<fmt:message key="page.date.from" />
											 	<input value="${scheduleItem.begin.text}" type="text" class="datepicker">
											 	<br/>
											 	<fmt:message key="page.date.to" /> 
											 	<input value="${scheduleItem.end.text}" type="text" class="datepicker"></td>
											<td class="price_td">
												<c:choose>
													<c:when test="${empty scheduleItem.price.value}">
														<input type="text" key="<fmt:message key="currency.name" />">
														<div class="currency"><span><fmt:message key="currency.current" /></span>
      												</c:when>
      												<c:otherwise><input value="${scheduleItem.price.value}" key="${scheduleItem.price.currency}" type="text">
														<div class="currency"><span><fmt:message key="currency.${scheduleItem.price.currency}" /></span>
      												</c:otherwise>
    											</c:choose>
															<ul>
																<li key="RUB"><fmt:message key="currency.RUB" /></li>
																<li key="USD"><fmt:message key="currency.USD" /></li>
																<li key="EUR"><fmt:message key="currency.EUR" /></li>
															</ul>
														</div>
											</td>
											<td><input value="${scheduleItem.min}" type="text"></td>
											<td><input value="${scheduleItem.max}" type="text"></td>
											<td>
												<a class="button" func="4">
												<fmt:message key="page.action.delete" />
												</a>
											</td>
										</tr>
									</c:forEach>
      							</c:otherwise>
    						</c:choose>
 						</tbody>
					</table>
				<a class="button" func="5"><fmt:message key="page.action.add" /></a>
			</div>
			<div class="del_article"><a class="button"><fmt:message key="trip.action.delete" /></a></div>
		</article>
	</div><!-- #content-->
</div><!-- #container-->
		

<aside id="sideRight">
	<tiles:insertDefinition name="categoryTree" />
	<tiles:insertDefinition name="regionFilter" />
</aside><!-- #sideRight -->
		

