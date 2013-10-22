(function ($) {
    $.fn
        .extend({
            smartpaginator: function (options) {

                var settings = $.extend({
                    totalrecords: 0,
                    recordsperpage: 10,//pagingSET.recordsperpage,
                    length: 5,
                    next: '>',
                    prev: '<',
                    text: locale.pagingText,
                    go: locale.pagingGo,
                    initval: 1,
                    skip: 0,
                    onchange: null
                }, options);

                return this
                    .each(function () {

                        if (settings.skip != 0) {
                            settings.initval = (settings.skip
                                / settings.recordsperpage | 0) + 1;
                        }
                        var currentPage = 0;
                        var startPage = 0;
                        var totalpages = parseInt(settings.totalrecords
                            / settings.recordsperpage);
                        if (settings.totalrecords
                            % settings.recordsperpage > 0)
                            totalpages++;
                        var initialized = false;
                        var container = $(this).addClass('pagination');
                        container.find('a').remove();
                        container.find('input').remove();
                        container.find('ul').remove();
                        container.find('div').remove();
                        container.find('span').remove();
                        var list = $('<ul/>');
                        var text = $('<div/>').attr('id',
                                'pagination_name').text(
                                settings.text + ':').addClass('btn').css({
                                width: 70
                            });
                        var btnPrev = $('<div/>')
                            .attr('id', 'pagination_prev')
                            .text(settings.prev)
                            .click(
                            function () {
                                if ($(this).hasClass(
                                    'disabled'))
                                    return false;
                                currentPage = parseInt(list
                                    .find('li a.active')
                                    .text()) - 1;
                                navigate(--currentPage);
                            }).addClass('btn');
                        var btnNext = $('<div/>')
                            .attr('id', 'pagination_next')
                            .text(settings.next)
                            .click(
                            function () {
                                if ($(this).hasClass(
                                    'disabled'))
                                    return false;
                                currentPage = parseInt(list
                                    .find('li a.active')
                                    .text());
                                navigate(currentPage);
                            }).addClass('btn');
                        var inputPage = $('<input/>')
                            .attr('type', 'text')
                            .keypress(
                            function (e) {
                                if (e.which == 13) {
                                    if (inputPage.val() == '')
                                        return false;
                                    else {
                                        currentPage = parseInt(inputPage
                                            .val()) - 1;
                                        navigate(currentPage);
                                    }
                                }
                            })
                            .keydown(
                            function (e) {
                                if (isTextSelected(inputPage))
                                    inputPage.val('');
                                if (e.which >= 48
                                    && e.which < 58) {
                                    var value = parseInt(inputPage
                                        .val()
                                        + (e.which - 48));
                                    if (!(value > 0 && value <= totalpages))
                                        e.preventDefault();
                                } else if (!(e.which == 8 || e.which == 46)) {
                                    e.preventDefault();
                                }

                            }).addClass('btn');

                        var btnGo = $('<a/>')
                            .text(settings.go)
                            .addClass('btn')
                            .click(
                            function () {
                                if (inputPage.val() == '')
                                    return false;
                                else {
                                    currentPage = parseInt(inputPage
                                        .val()) - 1;
                                    navigate(currentPage);
                                }
                            }).addClass('btn');


                        if (totalpages > 0) {
                            container
                                .append(text)
                                .append(btnPrev)
                                .append(list)
                                .append(btnNext)
                                //.append(inputPage)
                                //.append(btnGo)
                                .append($('<div/>').addClass('short'));
                        } else {

                            var empty = $('<div/>').text(locale.pagingEmpty);

                            container
                                .append(empty).append($('<div/>').addClass('short'));
                        }

                        buildNavigation(startPage);
                        if (settings.initval == 0)
                            settings.initval = 1;
                        currentPage = settings.initval - 1;
                        navigate(currentPage);
                        initialized = true;

                        function showLabels(pageIndex) {
                            container.find('span').remove();
                            var upper = (pageIndex + 1)
                                * settings.recordsperpage;
                            if (upper > settings.totalrecords)
                                upper = settings.totalrecords;
                            container
                                .append(
                                    $('<span/>')
                                        .append(
                                            $('<b/>')
                                                .text(
                                                    pageIndex
                                                        * settings.recordsperpage
                                                        + 1)))
                                .append($('<span/>').text('-'))
                                .append(
                                    $('<span/>').append(
                                        $('<b/>').text(
                                            upper)))
                                .append($('<span/>').text(locale.pagingOf))
                                .append(
                                    $('<span/>')
                                        .append(
                                            $('<b/>')
                                                .text(
                                                    settings.totalrecords)));
                        }

                        function buildNavigation(startPage) {

                            list.find('li').remove();
                            if (settings.totalrecords <= settings.recordsperpage)
                                return;
                            for (var i = startPage; i < startPage
                                + settings.length; i++) {
                                if (i == totalpages)
                                    break;
                                list
                                    .append($('<li/>')
                                        .append(
                                            $('<a>')
                                                .attr(
                                                    'id',
                                                    (i + 1))
                                                .addClass(
                                                    'normal')
                                                .attr(
                                                    'href',
                                                    'javascript:void(0)')
                                                .text(
                                                    i + 1))
                                        .click(
                                        function () {
                                            currentPage = startPage
                                                + $(
                                                this)
                                                .closest(
                                                    'li')
                                                .prevAll().length;
                                            navigate(currentPage);
                                        }));
                            }

                            showLabels(startPage);
                            inputPage.val((startPage + 1));
                            list.find('li a').removeClass('active');
                            list.find('li').removeClass('active');
                            list.find('li:eq(0) a').addClass('active');
                            list.find('li:eq(0) a').parent().addClass(
                                'active');
                            if (totalpages < settings.length) {
                                list.css({
                                    width: totalpages * 20
                                });
                            } else {
                                list.css({
                                    width: settings.length * 20
                                });
                            }
                            showRequiredButtons(startPage);
                        }

                        function navigate(topage) {

                            var index = topage;
                            var mid = settings.length / 2;
                            if (settings.length % 2 > 0)
                                mid = (settings.length + 1) / 2;
                            var startIndex = 0;
                            if (topage >= 0 && topage < totalpages) {
                                if (topage >= mid) {
                                    if (totalpages - topage > mid)
                                        startIndex = topage - (mid - 1);
                                    else if (totalpages > settings.length)
                                        startIndex = totalpages
                                            - settings.length;
                                }
                                buildNavigation(startIndex);
                                showLabels(currentPage);
                                list.find('li a').removeClass('active');
                                list.find('li').removeClass('active');
                                inputPage.val(currentPage + 1);
                                list.find(
                                        'li a[id="' + (index + 1)
                                            + '"]').addClass(
                                        'active');
                                list.find(
                                        'li a[id="' + (index + 1)
                                            + '"]').parent()
                                    .addClass('active');

                                var recordStartIndex = currentPage
                                    * settings.recordsperpage;
                                var recordsEndIndex = recordStartIndex
                                    + settings.recordsperpage;
                                if (recordsEndIndex > settings.totalrecords)
                                    recordsEndIndex = settings.totalrecords
                                        % recordsEndIndex;
                                if (initialized) {
                                    if (settings.onchange != null) {
                                        settings.onchange(
                                            (currentPage + 1),
                                            recordStartIndex,
                                            recordsEndIndex);
                                    } else {
                                        $.handParamToURL({
                                            skip: recordStartIndex,
                                            limit: recordsEndIndex - recordStartIndex
                                        });
                                    }
                                }

                                showRequiredButtons();
                            }
                        }

                        function showRequiredButtons() {

                            if (totalpages < settings.length) {
                                if (currentPage > 0) {
                                    btnPrev.css('display', '');
                                } else {
                                    btnPrev.css('display', 'none');
                                }
                                if (currentPage == totalpages - 1) {
                                    btnNext.css('display', 'none');
                                } else {
                                    btnNext.css('display', '');
                                }

                            }
                        }

                        function isTextSelected(el) {

                            var startPos = el.get(0).selectionStart;
                            var endPos = el.get(0).selectionEnd;
                            var doc = document.selection;
                            if (doc
                                && doc.createRange().text.length != 0) {
                                return true;
                            } else if (!doc
                                && el.val().substring(startPos,
                                endPos).length != 0) {
                                return true;
                            }
                            return false;
                        }
                    });
            }
        });

})(jQuery);