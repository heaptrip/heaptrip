<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div id="container">
			<div id="contents">

				<article id="article" class="deteil">
					<div class="inf">
						<div class="left">
							<h2 class="people_title">Мой профиль</h2>
						</div>
						<div class="right">
							<a href="/" class="button" func="2">Редактировать</a>
						</div>
					
						<div class="profile">
							<div class="my_avatar"><img src="/albom/photo1_small.jpg"><a href="/" class="button">Загрузить аватар</a></div>
							<div class="my_inf">
								<div class="my_name">Alexandr Alexeev Alexeevich<span>(4,7)</span></div>
								<div class="my_location"><span>Местоположение: </span>Санкт-Петербург</div>
								<div class="my_date"><span>Дата рождения: </span>10.10.1980</div>
								<div class="my_lang">
									Я владею:
									<ul>
										<li class="ru">Русский</li>
										<li class="en">Английский</li>
										<li class="du">Немецкий</li>
										<li class="fr">Французский</li>
									</ul>
								</div>
							</div>
						</div>

					</div>
					<div class="description">
						Барселона расположена на северо-востоке Иберийского полуострова на побережье Средиземного моря на плато шириной в 5 км, границы которого с юга составляют горная гряда Кольсерола (кат. Collserola) и река Льобрегат, а на севере — река Бесос. Пиренеи находятся приблизительно в 120 км к северу от города.
Прибрежные горы Кольсерола создают слегка скруглённый фон города. Высота самой высшей точки — горы Тибидабо составляет 512 м, над ней возвышается заметная издалека башня-антенна Кольсерола высотой 288,4 м. Самой высокой точкой в черте города является холм Мон-Табер (кат. Mont Taber) высотой 12 м, на нём расположен Барселонский собор.
Барселона лежит на холмах, давших название городским кварталам: Кармель (кат. Carmel, 267 м), Монтерольс (кат. Monterols, 121 м), Пучет (кат. Putxet, 181 м), Ровира (кат. Rovira, 261 м) и Пейра (кат. Peira, 133 м). С горы Монжуик высотой 173 м в юго-западной части города открывается великолепный вид на порт Барселоны. На Монжуике расположена крепость XVII—XVIII веков, взявшая на себя оборонные функции разрушенной цитадели Сьютаделья (кат. Ciutadella), в то время как на месте последней разбили парк. В настоящее время в крепости размещается Военный музей. Помимо крепости на Монжуике находятся олимпийские объекты, учреждения культуры и знаменитые сады.
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
						<h2 class="people_title">Мой опыт</h2>
						<table class="experience">
							<thead><tr>
								<th>Период</th>
								<th>Описание</th>
								<th>Действие</th>
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




