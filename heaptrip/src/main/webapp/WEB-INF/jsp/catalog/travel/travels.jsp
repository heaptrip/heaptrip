<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<script type="text/javascript">
	$.dateFormat = function(dateObject) {
		var d = new Date(dateObject);
		var day = d.getDate();
		var month = d.getMonth();
		var year = d.getFullYear();
		var date = day + "." + month + "." + year;

		return date;
	};

	$.views.helpers({
		toDate : function(msDat) {
			return $.dateFormat(new Date(msDat));
		}	
	});
</script>

<script id="tripTemplate" type="text/x-jsrender">

	<article id="article">
		<div class="date">{{>~toDate(created)}}
			<span><fmt:message key="trip.list.title" /></span>
		</div>
		<div class="inf">
			<div class="left">
				<h2><a href="<c:url value="/travel_info.html?id={{>id}}"/>">{{>name}}</a></h2>{{>owner.name}}<span>({{>owner.rating}})</span>
			</div>
			<div class="right">
				<div>
					<fmt:message key="page.date.period" />:
					<span class="date">
						<fmt:message key="page.date.from" /> {{>~toDate(begin)}} <fmt:message key="page.date.to" /> {{>~toDate(end)}}
					</span>
				</div>
				<div>
					<fmt:message key="content.place" />:
						{{for regions}}
							<span class="location">{{>data}}</span>
						{{/for}}
				</div>
			</div>
		</div>
		<div class="description">
			{{if image}}
				<img src="<c:url value="/rest/image?imageId={{>image}}"/>" width="300" align="left">
			{{/if}}
				{{>summary}}
		</div>
		<div>
			<div class="tags">
				{{for categories}}
					<a onclick="$.handParamToURL({ct:{{>id}}, skip : null ,limit : null})">{{>data}}</a>
				{{/for}}
			</div>
			{{if price}}
				<div class="price">{{>price}}<fmt:message key="locale.currency" /></div>
			{{/if}}
			
		</div>
		<div>
			<div class="views"><fmt:message key="content.views" />:<span>{{>views}}</span></div>
			<div class="comments"><fmt:message key="content.comments" />:<span>{{>comments}}</span></div>
			<div class="wertung"><fmt:message key="content.wertung" />:<div class="stars star2"></div><span>({{>rating}})</span></div>
		</div>
	</article>

</script>

<div id="container">
	<div id="contents"></div>
	<!-- #content-->
	<tiles:insertDefinition name="pagination" />
</div>
<!-- #container-->

<aside id="sideRight">

	<tiles:insertDefinition name="categoryTree" />
	<tiles:insertDefinition name="regionFilter" />

</aside><!-- #sideRight -->

<script type="text/javascript">
	$(window).bind("onPageReady", function(e, paramsJson) {
		getTripsList(paramsJson);
	});

	var getTripsList = function(paramsJson) {

		var recordsperpage = 4;
		
		var url = 'rest/trips';

		var tripCriteria = {
			skip : paramsJson.skip ? paramsJson.skip : 0,
			limit : paramsJson.limit ? paramsJson.limit : recordsperpage,
			categoryIds : paramsJson.ct ? paramsJson.ct.split(',') : null
		};

		var callbackSuccess = function(data) {
			$("#contents").html($("#tripTemplate").render(data.trips));
			$('#paginator').smartpaginator({
				totalrecords : data.count,
				skip : paramsJson.skip,
				recordsperpage:recordsperpage,
				onchange : function onChange(pageIndex, skip, limit) {
					$.handParamToURL({
						skip : skip,
						limit : limit
					});
				}
			});
		};

		var callbackError = function(error) {
			alert(error);
		};

		$.postJSON(url, tripCriteria, callbackSuccess, callbackError);

	};
</script>
