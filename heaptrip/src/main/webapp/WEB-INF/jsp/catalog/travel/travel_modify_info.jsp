<%@ page import="com.heaptrip.domain.entity.LangEnum"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<c:set var="langValues" value="<%=LangEnum.getValues()%>" />

<c:choose>
	<c:when test="${empty param.ul}">
		<c:set var="currLocale">
			<fmt:message key="locale.name" />
		</c:set>
	</c:when>
	<c:otherwise>
		<c:set var="currLocale">${param.ul}</c:set>
	</c:otherwise>
</c:choose>


<c:set var="tripId" value='${param.id}' />

<c:forEach items="${trip.categories}" var="category" varStatus="stat">
	<c:set var="categoryIds" value="${categoriesIds },${category.id}" />
</c:forEach>

<c:forEach items="${trip.regions}" var="region" varStatus="stat">
	<c:set var="regionIds" value="${regionIds },${region.id}" />
</c:forEach>

<c:set var="isForFrends" value="${trip.status.value eq 'PUBLISHED_FRIENDS'}" />
<c:set var="isDraft" value="${trip.status.value eq 'DRAFT'}" />

<script type="text/javascript">
	var tripId = '${tripId}';
	var locale = '${currLocale}';

	//$("#is_draft")

	$(document).ready(function() {
		var ct = "${fn:substring(categoryIds,1,1000)}";
		var rg = "${fn:substring(regionIds,1,1000)}";
		$.handParamToURL({
			ct : ct,
			rg : rg
		});
	});

	var onTripSubmit = function() {

		var jsonData = (tripId ? {
			id : tripId
		} : {});

		var statusValue = null;
		if($('#is_draft').is(':checked'))
			statusValue = 'DRAFT';
		else if($('#is_for_frends').is(':checked'))
			statusValue = 'PUBLISHED_FRIENDS';
		else
			statusValue = 'PUBLISHED_ALL';
		
		jsonData.locale = locale;
		jsonData.name = $("#name_post").val();
		jsonData.summary = $("#desc_post").val();
		jsonData.description = $("#desc_full_post").val();
		$.extend(jsonData, {status:{value: statusValue}});
		$.extend(jsonData, {route:{text: $("#desc_rout_post").val()}});

		var paramsJson = $.getParamFromURL();

		if (paramsJson.ct) {
			jsonData.categories = [];
			var categoryIds = paramsJson.ct.split(',');
			$.each(categoryIds, function(index, id) {
				jsonData.categories.push({
					id : id
				});
			});
		}

		if (paramsJson.rg) {
			jsonData.regions = [];
			var regionIds = paramsJson.rg.split(',');
			$.each(regionIds, function(index, id) {
				jsonData.regions.push({
					id : id
				});
			});
		}

		var schedule = [];

		$('#schedule_table > tbody  > tr')
				.each(
						function(iTR, tr) {
							var item = (tr.id ? {
								id : tr.id
							} : {});
							$(this)
									.children('td')
									.each(
											function(iTD, td) {
												var cellInps = $(this)
														.children('input');

												switch (iTD) {
												case 0:
													item.begin = {};
													if ($("#" + cellInps[0].id)
															.datepicker(
																	'getDate'))
														item.begin.value = $(
																"#"
																		+ cellInps[0].id)
																.datepicker(
																		'getDate')
																.getTime();
													item.end = {};
													if ($("#" + cellInps[1].id)
															.datepicker(
																	'getDate'))
														item.end.value = $(
																"#"
																		+ cellInps[1].id)
																.datepicker(
																		'getDate')
																.getTime();
													break;
												case 1:
													item.price = {};
													item.price.currency = cellInps[0]
															.getAttribute('key');
													item.price.value = cellInps[0].value;
													break;
												case 2:
													item.min = cellInps[0].value;
													break;
												case 3:
													item.max = cellInps[0].value;
													break;
												default:
													break;
												}
											});

							schedule.push(item);

						});

		jsonData.schedule = schedule;

		var url = (tripId ? 'rest/security/travel_update'
				: 'rest/security/travel_save');

		var callbackSuccess = function(data) {
			//var domain =  $("#email").val().replace(/.*@/, ""); 
			//window.location = 'confirmation.html?domain=' + domain;
			alert("Success");
		};

		var callbackError = function(error) {
			$("#error_message #msg").text(error);
		};

		$.postJSON(url, jsonData, callbackSuccess, callbackError);

	};

	var onEditTabClick = function(tabHeader,tabId) {
		$('#travel_nav > ul  > li > a').removeClass('active')
		$(tabHeader).addClass('active');
		var showTabId = tabId;
		hideTabId = (showTabId == 'tab1') ? 'tab2' : 'tab1';
		$("#" + hideTabId).hide();
		$("#" + showTabId).show();
	}
	
</script>

<div id="container">
	<div id="contents">
		<article id="article" class="deteil edit">
			<div class="date">
				${trip.created.text}<span><fmt:message key="trip.title" /></span>
			</div>
			<div class="inf">
				<div class="left">
					<ul>
						<li><input id="is_draft" type="checkbox" ${isDraft ? "checked=true": "" }><label><fmt:message
									key="content.draft" /></label></li>
						<li><input id="is_for_frends" type="checkbox"
							${isForFrends ? "checked=true": "" }><label><fmt:message
									key="content.forFrends" /></label></li>
					</ul>
				</div>

				<div class="right">
					<div>
						<fmt:message key="content.available" />
						:
					</div>
					<c:choose>
						<c:when test="${empty tripId}">
							<ul>
								<li><a onClick="$.handGETParamToURL('ul','${currLocale}')"
									class="${currLocale} lang"></a></li>
								<li class="add_list_lang"><a class="add_lang lang" href="/"></a>
									<ul>
										<c:forEach items="${langValues}" var="langValue">
											<c:if test="${currLocale ne langValue}">
												<li><a
													onClick="$.handGETParamToURL('ul','${langValue}')"
													class="${langValue} lang"></a></li>
											</c:if>
										</c:forEach>
									</ul></li>
							</ul>
						</c:when>
						<c:otherwise>
							<ul>
								<li class="del_list_lang"><a class="del_lang lang" href="/"></a></li>
								<!-- add trip.langs + ul -->

								<c:forEach items="${trip.langs}" var="lang">
									<c:choose>
										<c:when test="${currLocale eq lang}">
											<li class="activ_lang">
										</c:when>
										<c:otherwise>
											<li>
										</c:otherwise>
									</c:choose>
									<a onClick="$.handGETParamToURL('ul','${lang}')"
										class="${lang} lang"></a>
									</li>
								</c:forEach>

								<c:set var="joinLangValues" value="${fn:join(trip.langs, ',')}" />

								<c:choose>
									<c:when test="${fn:contains(joinLangValues, param.ul) ne true}">
										<li class="activ_lang"><a
											onClick="$.handGETParamToURL('ul','${param.ul}')"
											class="${param.ul} lang"></a>
									</c:when>
									<c:otherwise>
										<li class="add_list_lang"><a class="add_lang lang"
											href="/"></a>
											<ul>

												<c:forEach items="${langValues}" var="langValue">
													<c:if
														test="${fn:contains(joinLangValues, langValue) ne true}">
														<li><a
															onClick="$.handGETParamToURL('ul','${langValue}')"
															class="${langValue} lang"></a></li>
													</c:if>
												</c:forEach>
											</ul></li>
									</c:otherwise>
								</c:choose>

							</ul>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<input type="text" id="name_post" value="${trip.name}"
				alt="<fmt:message key="content.name" />:">

			<div id="error_message">
				<span id="msg" class="error_message"></span>
			</div>

			<nav id="travel_nav">
				<a onClick="onTripSubmit()" class="button"><fmt:message
						key="page.action.save" /></a>
				<ul>
					<!--
    				    -->
					<li><a onClick="onEditTabClick(this,'tab1')" class='${fn:contains(param.tb, "info") || empty param.tb ? "active":"" }'><fmt:message
								key="content.information" /><span></span></a></li>
					<!--
    				    -->
					<li><a onClick="onEditTabClick(this,'tab2')" class='${fn:contains(param.tb, "map") ? "active":"" }'><fmt:message
								key="trip.route" /><span></span></a></li>
					<!--
    				    -->
					<li><a href="/travel_edit_photo.html"><fmt:message
								key="content.photo" /><span></span></a></li>
					<!--
    				    -->
					<li><a href="/travel_edit_participants.html"><fmt:message
								key="content.participants" /><span></span></a></li>
					<!--
    				    -->
					<li><a href="/travel_edit_posts.html"><fmt:message
								key="post.list.title" /><span></span></a></li>
					<!--
    				-->
				</ul>
			</nav>


			<div id="tab1" style='display:${fn:contains(param.tb, "info") || empty param.tb ? "true":"none" }'>


	<br/>

				<textarea id="desc_post"
					alt="<fmt:message key="content.shortDescription" />:">${trip.summary}</textarea>
				<textarea id="desc_full_post"
					alt="<fmt:message key="content.fullDescription" />:">${trip.description}</textarea>

				<div class="table_inf">
					<table id=schedule_table>
						<thead>
							<tr>
								<th><fmt:message key="trip.period" /></th>
								<th class="price_th"><fmt:message key="trip.cost" /></th>
								<th><fmt:message key="content.participants.min" /></th>
								<th><fmt:message key="content.participants.max" /></th>
								<th><fmt:message key="page.action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${empty tripId}">
									<tr>
										<td><fmt:message key="page.date.from" /> <input
											type="text" class="datepicker"> <br /> <fmt:message
												key="page.date.to" /> <input type="text" class="datepicker"></td>
										<td class="price_td"><input type="text"
											key="<fmt:message key="currency.name" />">
											<div class="currency">
												<span><fmt:message key="currency.current" /></span>
												<ul>
													<li key="RUB"><fmt:message key="currency.RUB" /></li>
													<li key="USD"><fmt:message key="currency.USD" /></li>
													<li key="EUR"><fmt:message key="currency.EUR" /></li>
												</ul>
											</div></td>
										<td><input type="text"></td>
										<td><input type="text"></td>
										<td><a class="button" func="4"> <fmt:message
													key="page.action.delete" />
										</a></td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${trip.schedule}" var="scheduleItem">
										<tr id="${scheduleItem.id}">
											<td><fmt:message key="page.date.from" /> <input
												value="${scheduleItem.begin.text}" type="text"
												class="datepicker"> <br /> <fmt:message
													key="page.date.to" /> <input
												value="${scheduleItem.end.text}" type="text"
												class="datepicker"></td>
											<td class="price_td"><c:choose>
													<c:when test="${empty scheduleItem.price.value}">
														<input type="text"
															key="<fmt:message key="currency.name" />">
														<div class="currency">
															<span><fmt:message key="currency.current" /></span>
													</c:when>
													<c:otherwise>
														<input value="${scheduleItem.price.value}"
															key="${scheduleItem.price.currency}" type="text">
														<div class="currency">
															<span><fmt:message
																	key="currency.${scheduleItem.price.currency}" /></span>
													</c:otherwise>
												</c:choose>
												<ul>
													<li key="RUB"><fmt:message key="currency.RUB" /></li>
													<li key="USD"><fmt:message key="currency.USD" /></li>
													<li key="EUR"><fmt:message key="currency.EUR" /></li>
												</ul>
												</div></td>
											<td><input value="${scheduleItem.min}" type="text"></td>
											<td><input value="${scheduleItem.max}" type="text"></td>
											<td><a class="button" func="4"> <fmt:message
														key="page.action.delete" />
											</a></td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					<a class="button" func="5"><fmt:message key="page.action.add" /></a>
				</div>

			</div>

			<div id="tab2" style='display:${fn:contains(param.tb, "map") ? "true":"none" }'>
			

					<div class="tabs tabs_interactiv">
						<ul><!--
						
    						--><li><span class="activ"><fmt:message key="content.googleMaps" /></span>
    								<div class="tabs_content">
    									<div id = "google_map_canvas" style="height:100%; width:100%;">
    									
    								</div>
    								</div>
    							</li><!--
    						--><li><span><fmt:message key="content.maps" /></span>
    								<div class="tabs_content">
    									<ul><!--
    										--><li><a href="/map.html"><img src="/map/map1.jpg"></a></li><!--
    										--><li><a href="/map.html"><img src="/map/map1.jpg"></a></li><!--
    										--><li><a href="/map.html"><img src="/map/map1.jpg"></a></li><!--
    										--><li><a href="/map.html"><img src="/map/map1.jpg"></a></li><!--
    										--><li><a href="/map.html"><img src="/map/map1.jpg"></a></li><!--
    										--><li><a href="/map.html"><img src="/map/map1.jpg"></a></li><!--
    									--><ul>
    								</div>
    							</li><!--
						--></ul>
					</div>
    <div class="description">
			<textarea id="desc_rout_post" alt="<fmt:message key="content.routeDescription" />:">${trip.route.text}</textarea>
			</div>
			</div>
		
			</article>
		
		<c:if test="${not empty param.id}">
		
			<div class="del_article">
				<a class="button"><fmt:message key="trip.action.delete" /></a>
			</div>
		</c:if>	
			
		</article>
	</div>
	<!-- #content-->
</div>
<!-- #container-->


<aside id="sideRight">
	<tiles:insertDefinition name="categoryTree" />
	<tiles:insertDefinition name="regionFilter" />
</aside>
<!-- #sideRight -->


