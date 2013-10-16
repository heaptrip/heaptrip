<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div id="container">
			<div id="contents">

				<article id="article" class="deteil">
					<div class="inf">
						<div class="left">
							<h2 class="people_title"><fmt:message key="profile.title"/></h2>
						</div>
						<div class="right">
							<a href="/" class="button" func="2"><fmt:message key="page.action.edit"/></a>
						</div>
					
						<div class="profile">
							<div class="my_avatar"><img src="<c:url value="/rest/image?imageId=${account.image}"/>"><a href="/" class="button"><fmt:message key="page.action.uploadPhoto"/></a></div>
							<div class="my_inf">
								<div class="my_name">${account.name}<span>(${account.rating.value})</span></div>
								<div class="my_location"><span><fmt:message key="user.place"/>: </span>todo</div>
								<div class="my_date"><span><fmt:message key="user.birthday"/>: </span>10.10.1980</div>
								<div class="my_lang">
									<fmt:message key="user.languages"/>:
									<ul>
										<li class="ru"><fmt:message key="locale.ru"/></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="description">
						todo.
					</div>
					<div class="table_inf">
						<h2 class="people_title"><fmt:message key="user.knowledge"/>:</h2>
						<table>
							<thead><tr>
								<th><fmt:message key="page.date.period"/></th>
								<th><fmt:message key="user.specialty"/></th>
								<th><fmt:message key="user.specialty.placeOf"/></th>
								<th><fmt:message key="user.specialty.document"/></th>
								<th><fmt:message key="page.action"/></th>
								</tr></thead>
							<tbody>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="table_inf">
						<h2 class="people_title"><fmt:message key="user.experience"/></h2>
						<table class="experience">
							<thead><tr>
								<th><fmt:message key="page.date.period"/></th>
								<th><fmt:message key="content.description"/></th>
								<th><fmt:message key="page.action"/></th>
							</tr></thead>
							<tbody>
								<tr>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
				</article>

			</div><!-- #content-->
		</div><!-- #container-->

		<aside id="sideRight">
			<tiles:insertDefinition name="categoryTreeWithBtn" />
	<tiles:insertDefinition name="regionFilterWithBtn" />
		</aside><!-- #sideRight -->




