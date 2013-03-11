//
// var url = 'http://url';
// var jsonData = {};
//
// var callbackSuccess = function(data, jqXHR) {
// 	alert(data);
// };
//
// var callbackError = function(errorThrown, textStatus, jqXHR) {
// 	alert(error);
// };
//
// var callbackFinally = function() {
//
// };
//
// $.postJSON(url, jsonData, callbackSuccess, callbackError, callbackFinally);

(function($) {
	$.extend({
		postJSON : function(url, jsonData, callbackSuccess, callbackError,
				callbackFinally) {
			var config = {
				url : url,
				type : "POST",
				data : jsonData ? JSON.stringify(jsonData) : null,
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				// A function to be called if the request succeeds.
				// The function gets passed three arguments:
				// function(PlainObject data, String textStatus, jqXHR jqXHR)
				// The data returned from the server, formatted according to the
				// dataType parameter;
				// a string describing the status;
				// and the jqXHR
				success : function(data, textStatus, jqXHR) {
					alert('success   ' + textStatus);
					if (data.status && data.status == 'success') {
						callbackSuccess(data, jqXHR);
					} else {
						callbackError(data.message, data.status, jqXHR);
					}

				},
				// A function to be called if the request fails.
				// The function receives three arguments:
				// function( jqXHR jqXHR, String textStatus, String errorThrown
				// )
				// The jqXHR object,
				// a string describing the type of error that occurred and an
				// optional exception object,
				// if one occurred. Possible values for the second argument
				// (besides null) are "timeout", "error", "abort", and
				// "parsererror".
				// When an HTTP error occurs, errorThrown receives the textual
				// portion of the HTTP status,
				// such as "Not Found" or "Internal Server Error."
				error : function(jqXHR, textStatus, errorThrown) {
					callbackError(errorThrown, textStatus, jqXHR);
				},
				// A function to be called when the request finishes (after
				// success and error callbacks are executed).
				// The function gets passed two arguments:
				// function( jqXHR jqXHR, String textStatus )
				// The jqXHR object
				// and a string categorizing the status of the
				// request ("success", "notmodified", "error", "timeout",
				// "abort", or "parsererror").
				complete : callbackFinally
			};

			$.ajaxSetup(config);
			$.ajax();
		}
	});
})(jQuery);
