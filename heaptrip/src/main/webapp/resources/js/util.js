(function ($) {
    $.extend({
        postJSON: function (url, jsonData, callbackSuccess, callbackError, callbackFinally) {
            var config = {
                url: url,
                type: "POST",
                data: jsonData ? JSON.stringify(jsonData) : null,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: callbackSuccess,
                error: function( jqXHR, textStatus, errorThrown) {
                	callbackError(errorThrown);
                },
                complete: callbackFinally
            };
            
            $.ajaxSetup(config);
            $.ajax();
        }
    });
})(jQuery);