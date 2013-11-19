(function($) {
	$.fn.extend({
		onShow : function(callback, unbind) {
			return this.each(function() {
				var obj = this;
				var bindopt = (unbind == undefined) ? true : unbind;
				if ($.isFunction(callback)) {
					if ($(this).is(':hidden')) {
						var checkVis = function() {
							if ($(obj).is(':visible')) {
								callback.call();
								if (bindopt) {
									$('body').unbind('click keyup keydown',
											checkVis);
								}
							}
						}
						$('body').bind('click keyup keydown', checkVis);
					} else {
						callback.call();
					}
				}
			});
		}
	});
})(jQuery);

var geocoder;
var map;
var poly;
var infoWindow;
var polyOptions = {
    fillColor: '#00ff00',
    fillOpacity: 1,
    strokeWeight: 3,
    clickable: true,
    editable: true,
    zIndex: 1
}
var flightPlanCoordinates = null;

flightPlanCoordinates = [
    new google.maps.LatLng(59.92749568088279, 30.3662109375),
    new google.maps.LatLng(59.65109171169264, 31.080322265625),
    new google.maps.LatLng(59.74532608213611,31.849365234375),
    new google.maps.LatLng(59.92199002450385, 32.34375)
];

$(window).bind("onPageReady", function(e, paramsJson) {
	initializeMap();
	$('#google_map_canvas').onShow(function() {
		initializeMap();
	});
});

var initializeMap = function() {
    var mapCanvas = $('#google_map_canvas');
    mapCanvas.width('98%');
    mapCanvas.height(300);

    geocoder = new google.maps.Geocoder();
    var mapOptions = {
        zoom: 8,
        center: new google.maps.LatLng(59.9, 30.4),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(mapCanvas[0],mapOptions);
    newPoly(flightPlanCoordinates);
    google.maps.event.addListener(map, 'click', addLatLng);
    google.maps.event.addListener(map, 'rightclick', delLatLng);
};

function newPoly(path){
    if(path){
        polyOptions.path=path;
    }
    poly = new google.maps.Polyline(polyOptions);
    poly.setMap(map);
}

function addLatLng(event) {
    var path = poly.getPath();
    path.push(event.latLng);
}

function delLatLng(event) {
    var path = poly.getPath();
    var listCoord=path.getArray();
    var countCoord=listCoord.length;
    var flightPlanCoordinates=Array();
    for (i = 0; i < (countCoord-1); i++) {
        flightPlanCoordinates[i]=new google.maps.LatLng(listCoord[i].ob, listCoord[i].pb);
    }
    poly.setPath(flightPlanCoordinates);
}

function coordLatLng(event) {
    var path = poly.getPath();
    console.table(path.getArray());
}