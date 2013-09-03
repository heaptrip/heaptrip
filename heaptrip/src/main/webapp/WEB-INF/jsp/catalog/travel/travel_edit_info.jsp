<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:forEach items="${trip.categories}" var="category" varStatus="stat">
  <c:set var="categoryIds" value="${categoriesIds },${category.id}" />
</c:forEach>

<c:forEach items="${trip.regions}" var="region" varStatus="stat">
  <c:set var="regionIds" value="${regionIds },${region.id}" />
</c:forEach>

<c:set var="isForFrends"
	value="${trip.status.value eq 'PUBLISHED_FRIENDS'}" />
	
<c:set var="isDraft"
	value="${trip.status.value eq 'DRAFT'}" />

<script type="text/javascript">

	$(document).ready(function() {
		var ct = "${fn:substring(categoryIds,1,1000)}";
		var rg = "${fn:substring(regionIds,1,1000)}";
		$.handParamToURL({ct:ct,rg:rg});
	});
	
</script>

<div id="container">
	<div id="contents">
		<article id="article" class="deteil edit">
			<div class="date">${trip.created.text}<span><fmt:message key="trip.title" /></span></div>
				<div class="inf">
					<div class="left">
						<ul>
							<li><input type="checkbox" checked= "${isDraft}"><label><fmt:message key="content.draft" /></label></li>
							<li><input type="checkbox" checked = "${isForFrends}"><label><fmt:message key="content.forFrends" /></label></li>
						</ul>					
					</div>
					<div class="right">
						<div><fmt:message key="content.available" />:</div>
							<ul><!--
			 					--><li class="del_list_lang"><a class="del_lang lang" href="/"></a></li><!--
								--><li class="activ_lang"><a class="ru lang" href="/"></a></li><!--
								--><li><a class="yk lang" href="/"></a></li><!--
								--><li><a class="fr lang" href="/"></a></li><!--
								--><li class="add_list_lang"><a class="add_lang lang" href="/"></a>
									<ul>
										<li><a class="en lang" href="/"></a></li>
										<li><a class="du lang" href="/"></a></li>
										<li><a class="sw lang" href="/"></a></li>
									</ul>
								</li><!--
							--></ul>
							
						</div>
					</div>
					<input type="text" id="name_post" value="${trip.name}" alt="<fmt:message key="content.name" />:">
					<nav id="travel_nav">
						<a href="/travel.html" class="button"><fmt:message key="page.action.save" /></a>
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
						<table>
							<thead><tr>
								<th><fmt:message key="trip.period" /></th>
								<th class="price_th"><fmt:message key="trip.cost" /></th>
								<th><fmt:message key="content.participants.min" /></th>
								<th><fmt:message key="content.participants.max" /></th>
								<th><fmt:message key="trip.action" /></th>
							</tr></thead>
							<tbody>
								<tr>
									<td>с <input type="text" class="datepicker"><br/>по <input type="text" class="datepicker"></td>
									<td class="price_td">
										<input type="text"><div class="currency"><span>РУБ</span>
											<ul>
												<li>РУБ</li>
												<li>US</li>
												<li>EURO</li>
											</ul>
										</div>
									</td>
									<td><input type="text"></td>
									<td><input type="text"></td>
									<td><a class="button" func="4">Удалить</a></td>
								</tr>
								<tr>
									<td>с <input type="text" class="datepicker"><br/>по <input type="text" class="datepicker"></td>
									<td class="price_td">
										<input type="text"><div class="currency"><span>РУБ</span>
											<ul>
												<li>РУБ</li>
												<li>US</li>
												<li>EURO</li>
											</ul>
										</div>
									</td>
									<td><input type="text"></td>
									<td><input type="text"></td>
									<td><a class="button" func="4">Удалить</a></td>
								</tr>
							</tbody>
						</table>
						<a class="button" func="5">Добавить</a>
					</div>
					<div class="del_article"><a class="button">Удалить путешествие</a></div>
				</article>
			</div><!-- #content-->
		</div><!-- #container-->
		

<aside id="sideRight">

	<tiles:insertDefinition name="categoryTree" />
	<tiles:insertDefinition name="regionFilter" />

</aside><!-- #sideRight -->
		

