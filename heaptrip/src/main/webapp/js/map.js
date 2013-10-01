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

$(window).bind("onPageReady", function(e, paramsJson) {
	initializeMap();
	$('#google_map_canvas').onShow(function() {
		initializeMap();
	});
});

var initializeMap = function() {
	var mapCanvas = $('#google_map_canvas');
	mapCanvas.width('98%');
	mapCanvas.height(400);
	var mapOptions = {
		center : new google.maps.LatLng(59.5, 30.2),
		zoom : 8,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	var map = new google.maps.Map(mapCanvas[0], mapOptions);
};
