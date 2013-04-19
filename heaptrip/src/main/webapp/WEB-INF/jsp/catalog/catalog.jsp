<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div id="catalog_tabs">
	<tiles:insertAttribute name="catalog_tabs" />
</div>

<section id="middle">
	
			<tiles:insertAttribute name="catalog_content" />
				
		<tiles:insertAttribute name="catalog_right_bar" />
</section><!-- #middle-->