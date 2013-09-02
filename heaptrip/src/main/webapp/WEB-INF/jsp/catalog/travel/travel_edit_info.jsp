<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div id="container">
			<div id="contents">

				<article id="article" class="deteil edit">
					<div class="date">15.01.13<span>Путешествие</span></div>
					<div class="inf">
						<div class="left">
							<ul>
								<li><input type="checkbox"><label>Черновик</label></li>
								<li><input type="checkbox"><label>Для всех</label></li>
								<li><input type="checkbox"><label>Для друзей</label></li>
							</ul>					
						</div>
						<div class="right">
							<div>Доступно для:</div>
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
					<input type="text" id="name_post" alt="Название:">
					<nav id="travel_nav">
						<a href="/travel.html" class="button">Сохранить</a>
    					<ul><!--
    					    --><li><a href="/travel.html" class="active">Информация<span></span></a></li><!--
    					    --><li><a href="/route.html">Маршрут<span></span></a></li><!--
    					    --><li><a href="/photo.html">Фото<span></span></a></li><!--
    					    --><li><a href="/participants.html">Участники<span></span></a></li><!--
    					    --><li><a href="/posts_user.html">Посты<span></span></a></li><!--
    					--></ul>
					</nav>					
					<div class="description">
						<div id="img_load"><img src="1.jpg" width="300"></div>
						<div id="img_load_button">
							<a href="/" class="button">Загрузить фото</a>
							<a href="/" class="button">Выбрать из альбома</a>
						</div>
					</div>
					<textarea id="desc_post" alt="Короткое описание:"></textarea>
					<textarea id="desc_full_post" alt="Полное описание:"></textarea>
					<div class="table_inf">
						<table>
							<thead><tr>
								<th>Период проведения</th>
								<th class="price_th">Стоимость участия</th>
								<th>Минимум участников</th>
								<th>Максимум участников</th>
								<th>Действие</th>
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
		

