<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<div id="catalog_tabs">
	<tiles:insertAttribute name="catalog_tabs" />
</div>

<section id="middle">
		<div id="container">
			<div id="contents">
			<table width="100%" border="0" cellspacing="3" cellpadding="10">

	<tr>
		<td bgcolor="#66CCFF">
			<div id="catalog_content">
				<tiles:insertAttribute name="catalog_content" />
			</div>
		</td>
		<td bgcolor="#66CC99" width="300px" valign="top">
			<div id="catalog_right_bar">
				<tiles:insertAttribute name="catalog_right_bar" />
			</div>
		</td>
	</tr>

</table>
				
			</div><!-- #content-->
		</div><!-- #container-->
	</section><!-- #middle-->


