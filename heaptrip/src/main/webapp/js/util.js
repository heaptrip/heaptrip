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
	$
			.extend({
				postJSON : function(url, jsonData, callbackSuccess,
						callbackError, callbackFinally) {
					var config = {
						url : url,
						type : "POST",
						data : $.isPlainObject(jsonData) ? JSON
								.stringify(jsonData) : jsonData,
						dataType : "json",
						contentType : "application/json; charset=utf-8",
						// A function to be called if the request succeeds.
						// The function gets passed three arguments:
						// function(PlainObject data, String textStatus, jqXHR
						// jqXHR)
						// The data returned from the server, formatted
						// according to the
						// dataType parameter;
						// a string describing the status;
						// and the jqXHR
						success : function(response, textStatus, jqXHR) {

							if (response.status && response.status == 'success') {
								callbackSuccess(response.data, jqXHR);
							} else {
								callbackError(response.message, response.data,
										jqXHR);
							}

						},
						// A function to be called if the request fails.
						// The function receives three arguments:
						// function( jqXHR jqXHR, String textStatus, String
						// errorThrown
						// )
						// The jqXHR object,
						// a string describing the type of error that occurred
						// and an
						// optional exception object,
						// if one occurred. Possible values for the second
						// argument
						// (besides null) are "timeout", "error", "abort", and
						// "parsererror".
						// When an HTTP error occurs, errorThrown receives the
						// textual
						// portion of the HTTP status,
						// such as "Not Found" or "Internal Server Error."
						error : function(jqXHR, textStatus, errorThrown) {

							try {
								// errorThrown =
								// $(jqXHR.responseText).find('#message')[0].textContent;
								// без $('<div></div>').append(... возникает
								// Exception т.к. JQ пытается захерачить всю
								// страницу :)
								errorThrown = $('<div></div>').append(
										jqXHR.responseText).find('#message')[0].textContent;

							} catch (e) {
								alert("For correct processing errors, use Exception Handler Controller");
							}

							callbackError(errorThrown, null, jqXHR);
						},
						// A function to be called when the request finishes
						// (after
						// success and error callbacks are executed).
						// The function gets passed two arguments:
						// function( jqXHR jqXHR, String textStatus )
						// The jqXHR object
						// and a string categorizing the status of the
						// request ("success", "notmodified", "error",
						// "timeout",
						// "abort", or "parsererror").
						complete : callbackFinally
					};

					$.ajaxSetup(config);
					$.ajax();
				}
			});
})(jQuery);


// Добавляет в url параметры paramsJson и если window.delayLoadingMap[] пуста вызывает $(window).bind("onPageReady")
(function($) {
	$.extend({
		handParamToURL : function(paramsJson) {
			var url = window.location.href;
			var newUrl = $.param.fragment(url, paramsJson);
			window.location = newUrl;
			window.isInit = false;
		}
	});
})(jQuery);

// Добавляет в url параметры paramsJson и не вызывает обработчит
(function($) {
	$.extend({
		putLOCALParamToURL : function(paramsJson) {
			var url = window.location.href;
			var newUrl = $.param.fragment(url, paramsJson);
			window.location = newUrl;
			window.isInit = true;
		}
	});
})(jQuery);

// Возвращает все параметры из url включая #
(function($) {
	$.extend({
		getParamFromURL : function() {
			var url = window.location.href;
			return $.extend({}, $.deparam.fragment(url), $.deparam
					.querystring(url));
		}
	});
})(jQuery);

// Регистрирует ключи при наличее которых $(window).bind("onPageReady") не вызывается
(function($) {
    $.extend({
        delayLoading : function(key) {
            if(!window.delayLoadingMap)
                window.delayLoadingMap = {};
            window.delayLoadingMap[key]=true;
        }
    });
})(jQuery);

// Удаляет ключь запрещающий загрузку, и при отсутствии других ключей вызывает $(window).bind("onPageReady")
(function($) {
    $.extend({
        allowLoading : function(key,paramsJson) {
            if(window.delayLoadingMap){
                if(window.delayLoadingMap[key])
                delete window.delayLoadingMap[key];
            }
            if($.isEmptyObject(window.delayLoadingMap)){
                $.handParamToURL(paramsJson);
                $(window).trigger("hashchange");
            }else{
                $.putLOCALParamToURL(paramsJson);
            }
        }
    });
})(jQuery);

$(function() {
	$(window).bind("hashchange", function(event) {
		var localUrlParams = $.deparam.fragment(window.location.href);
		if (!window.isInit && $.isEmptyObject(window.delayLoadingMap)) {
			window.isInit = false;
			$(window).trigger("onPageReady", localUrlParams);
		}
	});
	$(window).trigger("hashchange");
});

var onLocaleChange = function(locale) {
	$.putGETParamToURL('locale', locale);
};

(function($) {
	$.extend({
        putGETParamToURL : function(name, value) {
			var newUrl = $.param.querystring(window.location.href, name + '='
					+ value);
			window.location = newUrl;
		}
	});
})(jQuery);

(function($) {
	$.extend({
				alertNoAuthenticationUser : function() {
					$.alert('Вы не залогинены.'
									+ ' Пожалуйста '
									+ '<a onClick="$.putGETParamToURL(\'need_login\',\'true\')">войдите</a>'
									+ ' для завершения действия.');
				}
			});
})(jQuery);

(function($) {
	$.extend({
		alert : function(text) {
			$('<div>' + text + '</div>').dialog();
		}
	});
})(jQuery);

(function($) {
	$.extend({
		doAuthenticationUserAction : function(callBackFunction) {
			if (window.user)
				callBackFunction();
			else
				$.alertNoAuthenticationUser();
		}
	});
})(jQuery);

// ---------- ajaxCall() --------

var addContentRating = function(value) {
	$.doAuthenticationUserAction(function() {
		$('.stars').removeClass().addClass('stars star' + value);
		$('.stars').unbind('click',null);
		$('.stars').unbind('mousemove',null);
		
		var url = 'rest/security/add_content_rating';

		var contentRating = {
			contentType : $(".stars")[0].getAttribute('contentType'),
			contentId : $.getParamFromURL().id,
			stars : value
		};

		var callbackSuccess = function(data) {
			if(!data)return;
			$('.stars').removeClass().addClass('stars star' + data.stars);
			$(".wertung > span").text('(' + data.count + ')');	
		};

		var callbackError = function(error) {
			$.alert(error);
		};

		$.postJSON(url, contentRating, callbackSuccess, callbackError);
	});
};


