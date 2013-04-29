//
// var url = 'http://url';
// var jsonData = {};
//
// var callbackSuccess = function(data, jqXHR) {
// 	alert(data);
// };
//
// var callbackError = function(message, data, jqXHR) {
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
				success : function(response, textStatus, jqXHR) {
					
					if (response.status && response.status == 'success') {
						callbackSuccess(response.data, jqXHR);
					} else {
						callbackError(response.message,response.data,jqXHR);
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

					try {
						// errorThrown = $(jqXHR.responseText).find('#message')[0].textContent;
						// без $('<div></div>').append(... возникает Exception т.к. JQ пытается захерачить всю страницу :)
						errorThrown = $('<div></div>').append(jqXHR.responseText).find('#message')[0].textContent;
						
						
						
					} catch (e) {
						alert("For correct processing errors, use Exception Handler Controller");
					}
	
					callbackError(errorThrown, null, jqXHR);
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


var localHref = function (paramsJson){
	var url = window.location.href + '#';
	var newUrl = $.param.fragment( url, paramsJson );
	window.location = newUrl;
};


/*$(window).bind( "onPageReady", function(e,paramsJson){
	if(!$.isEmptyObject(localUrlParams)){
		console.log(paramsJson);
	}	
}); */

$(function(){

	$(window).bind( "hashchange", function(event) {
		var localUrlParams = $.deparam.fragment(window.location.href);
			$(window).trigger("onPageReady",localUrlParams);
	});
	
	$(window).trigger("hashchange");
	
});




//var paramsObj = {page:2,r:["g",'H']};



//var newUrl = $.param.querystring( url, paramsObj );

// Build URL, merging params_obj into url query string.

// newUrl is set to "http://example.com/file.php?a=7&b=2&c=8#c=3&d=4"





