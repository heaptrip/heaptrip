<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<script id="tripTemplate" type="text/x-jsrender">

<article id="article">
					<div class="date">15.01.13<span><fmt:message key="trip.list.title" /></span></div>
					<div class="inf">
						<div class="left">
							<h2><a href="/">{{>name}}</a></h2>
							Нева тревел<span>(4,7)</span>
						</div>
						<div class="right">
							<div>Период:<span class="date">с 19.01.13 по 29.01.13</span></div>
							<div>Место:<span class="location">Италия</span></div>
						</div>
					</div>
					<div class="description"><img src="1.jpg" width="300" align="left">Барселона расположена на северо-востоке Иберийского полуострова на побережье Средиземного моря на плато шириной в 5 км, границы которого с юга составляют горная гряда Кольсерола (кат. Collserola) и река Льобрегат, а на севере — река Бесос. Пиренеи находятся приблизительно в 120 км к северу от города.
Прибрежные горы Кольсерола создают слегка скруглённый фон города. Высота самой высшей точки — горы Тибидабо составляет 512 м, над ней возвышается заметная издалека башня-антенна Кольсерола высотой 288,4 м. Самой высокой точкой в черте города является холм Мон-Табер (кат. Mont Taber) высотой 12 м, на нём расположен Барселонский собор.
Барселона лежит на холмах, давших название городским кварталам: Кармель (кат. Carmel, 267 м), Монтерольс (кат. Monterols, 121 м), Пучет (кат. Putxet, 181 м), Ровира (кат. Rovira, 261 м) и Пейра (кат. Peira, 133 м). С горы Монжуик высотой 173 м в юго-западной части города открывается великолепный вид на порт Барселоны. На Монжуике расположена крепость XVII—XVIII веков, взявшая на себя оборонные функции разрушенной цитадели Сьютаделья (кат. Ciutadella), в то время как на месте последней разбили парк. В настоящее время в крепости размещается Военный музей. Помимо крепости на Монжуике находятся олимпийские объекты, учреждения культуры и знаменитые сады.</div>
					<div>
						<div class="tags"><a href="#">Морская прогулка</a><a href="#">Шоппинг</a><a href="#">Пляжный отдых</a></div>
						<div class="price">99999 р.</div>
					</div>
					<div>
						<div class="views">Просмотров:<span>234</span></div>
						<div class="comments">Коментариев:<span>24</span></div>
						<div class="wertung">Рейтинг:<div class="stars star2"></div><span>(196)</span></div>
					</div>
				</article>
</script>

<div id="container">
	<div id="contents"></div>
	<!-- #content-->
	<tiles:insertDefinition name="pagination" />
</div>
<!-- #container-->


<script type="text/javascript">
	var getTripsList = function(paramsJson) {

		var url = 'rest/trips';

		var tripCriteria = {
			skip : paramsJson.skip,
			limit : paramsJson.limit
		};

		var callbackSuccess = function(data) {
			$("#contents").html($("#tripTemplate").render(data.trips));
		};

		var callbackError = function(error) {
			alert(error);
		};

		$.postJSON(url, tripCriteria, callbackSuccess, callbackError);

	};

	$(window).bind("onPageReady", function(e, paramsJson) {
		getTripsList(paramsJson);
	});
</script>
