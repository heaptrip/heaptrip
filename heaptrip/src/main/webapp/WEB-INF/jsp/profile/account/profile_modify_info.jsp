<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div id="container">
			<div id="contents">

				<article id="article" class="deteil edit">
					<div class="inf">
						<div class="left">
							<h2 class="people_title">Мой профиль</h2>
						</div>
						<div class="right">
							<a href="/" class="button" func="3">Сохранить</a>
						</div>
					
						<div class="profile">
							<div class="my_avatar"><img src="/albom/photo1_small.jpg"><a href="/" class="button">Загрузить аватар</a></div>
							<div class="my_inf">
								<div class="my_name">
									<input type="text" value="Alexandr Alexeev Alexeevich" alt="Имя">
								</div>
								<div class="my_location"><span>Местоположение: </span><input type="text"></div>
								<div class="my_date"><span>Дата рождения: </span><input type="text" class="datepicker"></div>
								<div class="my_lang my_lang_edit">
									Я владею:
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
						<textarea alt="Обо мне:">Барселона расположена на северо-востоке Иберийского полуострова на побережье Средиземного моря на плато шириной в 5 км, границы которого с юга составляют горная гряда Кольсерола (кат. Collserola) и река Льобрегат, а на севере — река Бесос. Пиренеи находятся приблизительно в 120 км к северу от города.
Прибрежные горы Кольсерола создают слегка скруглённый фон города. Высота самой высшей точки — горы Тибидабо составляет 512 м, над ней возвышается заметная издалека башня-антенна Кольсерола высотой 288,4 м. Самой высокой точкой в черте города является холм Мон-Табер (кат. Mont Taber) высотой 12 м, на нём расположен Барселонский собор.
Барселона лежит на холмах, давших название городским кварталам: Кармель (кат. Carmel, 267 м), Монтерольс (кат. Monterols, 121 м), Пучет (кат. Putxet, 181 м), Ровира (кат. Rovira, 261 м) и Пейра (кат. Peira, 133 м). С горы Монжуик высотой 173 м в юго-западной части города открывается великолепный вид на порт Барселоны. На Монжуике расположена крепость XVII—XVIII веков, взявшая на себя оборонные функции разрушенной цитадели Сьютаделья (кат. Ciutadella), в то время как на месте последней разбили парк. В настоящее время в крепости размещается Военный музей. Помимо крепости на Монжуике находятся олимпийские объекты, учреждения культуры и знаменитые сады.</textarea>
					</div>
					<div class="table_inf">
						<h2 class="people_title">Мои знания</h2>
						<table>
							<thead><tr>
								<th>Период</th>
								<th>Специальность</th>
								<th>Место прохождения</th>
								<th>№ документа</th>
								<th>Действие</th>
							</tr></thead>
							<tbody>
								<tr>
									<td>с <input type="text" class="datepicker"><br/>по <input type="text" class="datepicker"></td>
									<td><input type="text"></td>
									<td><input type="text"></td>
									<td><input type="text"></td>
									<td><a class="button" func="4">Удалить</a></td>
								</tr>
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




