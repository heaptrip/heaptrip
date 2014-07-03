var map, mapData, vectorLayer;

var getMap = function () {
    return map;
}

var readMapData = function () {
    var geoJSON = new OpenLayers.Format.GeoJSON();
    return geoJSON.write(vectorLayer.features);
}

var showMapData = function () {

    var mapDataGeoJSON

    if ($('#map_data').text() && $('#map_data').text().length > 0) {
        mapDataGeoJSON = $.parseJSON($('#map_data').text());
    }

    if (mapDataGeoJSON && mapDataGeoJSON.features.length > 0) {
        vectorLayer.removeAllFeatures();
        var geoJSON = new OpenLayers.Format.GeoJSON();
        vectorLayer.addFeatures(geoJSON.read(mapDataGeoJSON));
        getMap().zoomToExtent(vectorLayer.getDataExtent())
    } else {
        var bounds = new OpenLayers.Bounds();
        bounds.extend(new OpenLayers.LonLat(29.4, 60.0));
        bounds.extend(new OpenLayers.LonLat(30.9, 59.8));
        bounds.toBBOX()
        bounds.transform(new OpenLayers.Projection("EPSG:4326"), getMap().getProjectionObject());
        getMap().zoomToExtent(bounds, true);
    }
}

$(document).ready(function () {

    var mapCanvas = $('#map_canvas');
    mapCanvas.width(mapCanvas.parent().width());
    mapCanvas.height(mapCanvas.parent().height());

    OpenLayers.Lang.setCode("ru");

    vectorLayer = new OpenLayers.Layer.Vector("Map Layer");

    map = new OpenLayers.Map('map_canvas', {

        layers: [
            new OpenLayers.Layer.OSM(
                "Open Street Map"
            ) ,
            new OpenLayers.Layer.Google(
                "Google Physical",
                {type: google.maps.MapTypeId.TERRAIN}
            ),
            new OpenLayers.Layer.Google(
                "Google Streets"
            ),
            new OpenLayers.Layer.Google(
                "Google Hybrid",
                {type: google.maps.MapTypeId.HYBRID}
            ),
            new OpenLayers.Layer.Google(
                "Google Satellite",
                {type: google.maps.MapTypeId.SATELLITE}
            ), vectorLayer
        ]
    });

    getMap().addControl(new OpenLayers.Control.LayerSwitcher());

    getMap().addControl(
        new OpenLayers.Control.MousePosition({
            prefix: '<a target="_blank" ' +
                'href="http://spatialreference.org/ref/epsg/4326/">' +
                'EPSG:4326</a> coordinates: ',
            separator: ' | ',
            numDigits: 2,
            emptyString: 'Mouse is not over map.'
        })
    );

    if (window.location.href.indexOf('modify') > 0) {

        var editor = new OpenLayers.Editor(getMap(), {
            activeControls: [ 'DragFeature', 'SelectFeature', 'Separator', 'DeleteFeature', 'DrawHole', 'TransformFeature', 'ModifyFeature', 'Separator', 'DrawText', 'Separator'],
            featureTypes: ['regular', 'polygon', 'path', 'point'],
            editLayer: vectorLayer
        });

        editor.startEditMode();
    }

    showMapData();


});

