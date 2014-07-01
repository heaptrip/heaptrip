/*(function($) {
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
var infowindow;
var markers = [];
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
		initializeMap(this);
	});
});

var initializeMap = function(el) {
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
    var newControlDiv = document.createElement('div');
    var newControls = new newControl(newControlDiv, map);
    newControlDiv.index = 1;
    map.controls[google.maps.ControlPosition.TOP_RIGHT].push(newControlDiv);
    newPoly(flightPlanCoordinates);
    google.maps.event.addListener(map, 'rightclick', function(event) {
        if($('#line').is(':checked')){
            delLatLng(event);
        }
    });
    google.maps.event.addListener(map, 'click', function(event) {
        if($('#line').is(':checked')){
            addLatLng(event);
        }else{
            addMarker(event.latLng);
        }
    });
    google.maps.event.addListener(poly, 'click', function(event) {
        if(!$('#line').is(':checked')){
            addMarker(event.latLng);
        }
    });
};

function addMarker(location) {
    var marker = new google.maps.Marker({
        position: location,
        map: map,
        draggable: true,
        content: '',
    });
    markers.push(marker);
    google.maps.event.addListener(marker, 'click',function(event) {
        var contentString='<pre>'+marker.content+'</pre>';
        var infowindow = new google.maps.InfoWindow({
            content: contentString
        });
        if(marker.content!=''){
            infowindow.open(map,marker);
        }
    } );
    google.maps.event.addListener(marker, 'rightclick',function(event) {marker.setVisible(false); } );
    google.maps.event.addListener(marker, 'dblclick',function(event) {
        var contentString='<textarea class="markerContent">'+marker.content+'</textarea>';
        var infowindow = new google.maps.InfoWindow({
            content: contentString
        });
        google.maps.event.addListener(infowindow, 'closeclick', function(event) {
            marker.content=$('.markerContent').val();
        } );
        infowindow.open(map,marker);
    } );
}

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

function codeAddress() {
    var address = 'Санкт';
    geocoder.geocode( { 'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            map.setCenter(results[0].geometry.location);
            var marker = new google.maps.Marker({
                map: map,
                position: results[0].geometry.location
            });
        } else {
            alert('Geocode was not successful for the following reason: ' + status);
        }
    });
}

function newControl(controlDiv, map) {
    controlDiv.style.padding = '5px';

    var controlUI = document.createElement('div');
    controlUI.style.backgroundColor = 'white';
    controlUI.style.borderStyle = 'solid';
    controlUI.style.borderWidth = '1px';
    controlUI.style.borderColor = '#eee';
    controlUI.style.cursor = 'pointer';
    controlUI.style.textAlign = 'center';
    controlUI.title = '';
    controlDiv.appendChild(controlUI);

    var controlText = document.createElement('div');
    controlText.style.fontFamily = 'Arial,sans-serif';
    controlText.style.fontSize = '12px';
    controlText.style.paddingLeft = '4px';
    controlText.style.paddingRight = '4px';
    controlText.innerHTML = '<input type="radio" checked="checked" name="edit" id="line"/><lable for="line">Пути</lable><input type="radio" name="edit" id="metka"/><lable for="metka">Метки</lable>';
    controlUI.appendChild(controlText);
}  */


//$(window).bind("onPageReady", function(e, paramsJson) {

    $(document).ready(function () {

        var mapCanvas = $('#map_canvas');
        mapCanvas.width($('#map_canvas').parent().width());
        mapCanvas.height($('#map_canvas').parent().height());
        $('#map_canvas').show();

        map = new OpenLayers.Map("map_canvas");
        map.addLayer(new OpenLayers.Layer.OSM());
        map.zoomToMaxExtent();
    });


//});

