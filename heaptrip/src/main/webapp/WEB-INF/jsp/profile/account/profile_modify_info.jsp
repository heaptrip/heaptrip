<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div id="container">
			<div id="contents">
				<article id="article" class="deteil edit">
					<div class="inf">
						<div class="left">
							<h2 class="people_title"><fmt:message key="profile.title"/></h2></h2>
						</div>
						<div class="right">
							<a href="/" class="button"><fmt:message key="page.action.save"/></a>
						</div>
					
						<div class="profile">
							<div class="my_avatar"><img src="<c:url value="/rest/image?imageId=${account.image.id}"/>"><a href="/" class="button"><fmt:message key="page.action.uploadPhoto"/></a></div>
							<div class="my_inf">
								<div class="my_name">
									<input type="text" value="${account.name}" alt="<fmt:message key="user.firstName"/>">
								</div>
								<div class="my_location"><span><fmt:message key="user.place"/>: </span><input type="text"></div>
								<div class="my_date"><span><fmt:message key="user.birthday"/>: </span><input type="text" class="datepicker"></div>
								<div class="my_lang my_lang_edit">
									<fmt:message key="user.languages"/>:
									<ul><!--
										--><li class="ru">Русский<span></span></li><!--
										--><li class="en">Английский<span></span></li><!--
										--><li class="du">Немецкий<span></span></li><!--
										--><li class="fr">Французский<span></span></li><!--
										--><li class="my_add_lang">
											<a class="add_lang lang"></a>
											<div>
											<ul>
												<li><a class="en">English</a></li>
												<li><a class="du">Dutish</a></li>
												<li><a class="fr">Franch</a></li>
												<li><a class="yk">Украiнскi</a></li>
												<li><a class="sw">Sweden</a></li>
											</ul>											
											</div>
										</li><!--
									--></ul>
								</div>
							</div>
						</div>

					</div>
					<div class="description">
						<textarea alt="<fmt:message key="user.aboutMe"/>:">TODO</textarea>
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
									<td>с <input type="text" class="datepicker"><br/>по <input type="text" class="datepicker"></td>
									<td><input type="text"></td>
									<td><input type="text"></td>
									<td><input type="text"></td>
									<td><a class="button" func="4">Удалить</a></td>
								</tr>
							</tbody>
						</table>
						<a class="button" func="10">Добавить</a>
					</div>
					
					
					
					
					
					<div class="table_inf">
						<h2 class="people_title">Мой опыт</h2>
						<table class="experience">
							<thead><tr>
								<th>Период</th>
								<th>Описание</th>
								<th>Действие</th>
							</tr></thead>
							<tbody>
								<tr>
									<td>с <input type="text" class="datepicker"><br/>по <input type="text" class="datepicker"></td>
									<td><textarea></textarea></td>
									<td><a class="button" func="4">Удалить</a></td>
								</tr>
								<tr>
									<td>с <input type="text" class="datepicker"><br/>по <input type="text" class="datepicker"></td>
									<td><textarea></textarea></td>
									<td><a class="button" func="4">Удалить</a></td>
								</tr>
							</tbody>
						</table>
						<a class="button" func="11">Добавить</a>
					</div>
				</article>

			</div><!-- #content-->
		</div><!-- #container-->

		<aside id="sideRight">
			<tiles:insertDefinition name="categoryTreeWithBtn" />
	<tiles:insertDefinition name="regionFilterWithBtn" />
		</aside><!-- #sideRight -->




