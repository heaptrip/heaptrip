<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div id="catalog_tabs">
	<tiles:insertAttribute name="catalog_tabs" />
</div>

<section id="middle">
		<div id="container">
				<tiles:insertAttribute name="catalog_content" />
				<div id="pagination">
				<div id="pagination_name">Страница:</div>
				<div id="pagination_prev"><</div>
				<ul>
					<li><a>1</a></li>
					<li class="active"><a>2</a></li>
					<li><a>3</a></li>
					<li><a>4</a></li>
					<li><a>5</a></li>
				</ul>
				<div id="pagination_next">></div>
			</div>
		</div><!-- #container-->
		<tiles:insertAttribute name="catalog_right_bar" />
</section><!-- #middle-->