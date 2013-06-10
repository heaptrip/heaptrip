<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<script type="text/javascript">

	$.dateFormat = function (dateObject) {
	    var d = new Date(dateObject);
	    var day = d.getDate();
	    var month = d.getMonth();
	    var year = d.getFullYear();
	    var date = day + "." + month + "." + year;
	
	    return date;
	};

	$.views.helpers({
		toDate: function( msDat ) {
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
				<h2><a href="/">{{>name}}</a></h2>TODO:owner<span>(4,7)</span>
			</div>
			<div class="right">
				<div><fmt:message key="page.date.period" />:<span class="date"><fmt:message key="page.date.from" /> {{>~toDate(created)}} <fmt:message key="page.date.to" /> {{>~toDate(created)}}</span></div>
				<div><fmt:message key="content.place" />:<span class="location">TODO:place</span></div>
			</div>
		</div>
		<div class="description">
			<img src="<c:url value="/image.html?imageId={{>image}}"/>" width="300" align="left">
				{{>summary}}
		</div>
		<div>
			<div class="tags">
				{{for categories}}
					<a href="#ct={{>id}}">{{>data}}</a>
				{{/for}}
			</div>
			<div class="price">TODO:price <fmt:message key="locale.currency" /></div>
		</div>
		<div>
			<div class="views"><fmt:message key="content.views" />:<span>111</span></div>
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

<script type="text/javascript">

	$(window).bind("onPageReady", function(e, paramsJson) {
		getTripsList(paramsJson);
	});

	var getTripsList = function(paramsJson) {

		var url = 'rest/trips';

		var tripCriteria = {
			skip : paramsJson.skip,
			limit : paramsJson.limit,
			categoryIds: paramsJson.ct ? paramsJson.ct.split(',') : null
		};

		var callbackSuccess = function(data) {
			$("#contents").html($("#tripTemplate").render(data.trips));
			$('#paginator').smartpaginator({
				totalrecords : data.count,
				skip : paramsJson.skip,
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
