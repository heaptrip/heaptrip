$(document).ready(function () {


    $(".my_avatar a").click(function () {
        uploader.show(
            function (files) {

                $('.my_avatar img').attr('src', files[0].url);

                var url = 'rest/security/change_image';

                var methodParams = {
                    imageId: files[0].id,
                    accountId: $.getParamFromURL().guid ? $.getParamFromURL().guid : window.principal.id
                };

                var callbackSuccess = function (data) {
                };

                var callbackError = function (error) {
                    alert(error);
                };

                $.postJSON(url, methodParams, callbackSuccess, callbackError);
            },
            {
                imageType: 'ACCOUNT_IMAGE',
                targetId: $.getParamFromURL().guid ? $.getParamFromURL().guid : window.principal.id
            }
        )
    });


    if ($('.global_menu').length) {
        $('.global_menu').bind('click', function () {
            if (!window.principal) {
                window.location = 'ct-tidings.html';
                return;
            }
            if ($('.global_menu ul').is(':hidden')) {
                $('.global_menu ul').slideDown(100);
            } else {
                $('.global_menu ul').slideUp(100);
            }
        });
    }

    if ($('#account').length) {
        $('#account_name').bind('click', function () {
            if ($('#account ul').is(':hidden')) {
                $('#account ul').slideDown(100);
            } else {
                $('#account ul').slideUp(100);
            }
        });
    }

    if ($('#language').length) {
        $('#language_now').bind('click', function () {
            if ($('#language ul').is(':hidden')) {
                $('#language ul').slideDown(100);
            } else {
                $('#language ul').slideUp(100);
            }
        });
    }
    if ($('.select').length) {
        $('.select_selected').bind('click', function () {
            var ul = $(this).parent().find('ul');
            if (ul.is(':hidden')) {
                ul.slideDown(100);
            } else {
                ul.slideUp(100);
            }
        });

        $('.select li a').click(function (e) {
            $(this).parents('.select').find('.select_selected').text($(this).text());
            $(this).parents('ul').slideUp(100);
            return false;
        });

    }

    $(document).click(function (e) {
        if ($(e.target).closest("#account").length) return;
        if ($(e.target).closest("#language").length) return;
        if ($(e.target).closest(".select").length) return;
        if ($(e.target).closest(".global_menu>ul").length) return;
        if ($(e.target).closest(".global_menu").length) return;
        $('#account ul').slideUp(100);
        $('#language ul').slideUp(100);
        $('.select ul').slideUp(100);
        $('.global_menu>ul').slideUp(100);
        e.stopPropagation();
    });

    // кастомизация чекбокса
    var checkbox = $('input[type=checkbox]');
    $.each(checkbox, function (index, value) {
        $(value).css('opacity', '0');
        $(value).css({
            'position': 'relative',
            'margin-left': '-' + $(value).css('width')
        });
        if ($(value).is(':checked')) {
            $(value).before('<span class="chek check_true"></span>');
        } else {
            $(value).before('<span class="chek check_false"></span>');
        }
        $(value).click(function (e) {
            if (!$(this).is(':checked')) {
                $(this).prev(".chek").addClass('check_false');
                $(this).prev(".chek").removeClass('check_true');
            } else {
                $(this).prev(".chek").addClass('check_true');
                $(this).prev(".chek").removeClass('check_false');
            }
        })
    });

    // .comment_content:hover
    $('.comment_content').hover(
        function (e) {
            $(this).css('border-color', '#DEE5EB');
        },
        function (e) {
            $(this).css('border-color', '#FFFFFF');
        }
    );

    // добавляет поле для комментирования
    $('.comment_content').bind('click', function (e) {
        //$(this).unbind('click');
        $('.comments_mother .comment_new').remove();
        $(this).parent().append('<div class="comment_new"><textarea noresize></textarea><input type="button" onClick="createNewComment(this)" value="Ответить" class="button"></div>');
    });

    // раздвижное текстовое поле по клику
    $(document).on('focus', '.comment_new textarea', function (e) {
        $(this).animate({ height: "56px"}, 500);
    });

    if ($('.tabs').length) {
        setTimeout(create_tabs, 200);
    }

    // звездочки рейтинга
    if ($(".stars").length) {
        if ($(".stars").hasClass("activ")) {
            var delta = 0;
            var value = 0;
            $(".stars").bind('mousemove', function (e) {
                delta = e.pageX - $(this).offset().left * 1;
                value = Math.ceil(delta / 11);
                $(this).removeClass();
                $(this).addClass("stars star" + value + " activ");
            });
            $(document).on('mouseout', '.stars', function (e) {
                value = $(this).find('input').val();
                $(this).removeClass();
                $(this).addClass("stars star" + value + " activ");
            });
            $(".stars").bind('click', function (e) {
                var delta = 0;
                var value = 0;
                delta = e.pageX - $(this).offset().left * 1;
                value = Math.ceil(delta / 11);
                addContentRating(value);
                $(this).removeClass();
                $(this).addClass("stars star" + value + " activ");
                $(this).find('input').val(value);
            });
        }
    }

    if ($('.full_img').length) {
        $('.full_img').css('cursor', 'pointer');
        $('.full_img').click(function (e) {
            view_img(this);
        })
    }

    if ($('.prev_img').length) {
        $('.prev_img').css('cursor', 'pointer');
        $('.prev_img').click(function (e) {
            alert('<');
        })
    }

    if ($('.next_img').length) {
        $('.next_img').css('cursor', 'pointer');
        $('.next_img').click(function (e) {
            alert('>');
        })
    }

    if ($('.participants_func').length) {
        var commands = Array('Сообщение', 'Организатор', 'Переписка', 'Удалить');
        participants_menu('.participants_func', commands);
    }

    // так не хорошо
    if ($('.participants_is').length) {
        var commands = Array('Принять', 'Отказать');
        participants_menu('.participants_is', commands);
    }

//    // надо вот так
//    if ($('.community_func12').length) {
//        var commands = Array(Array('Закрыть', '12'));
//        participants_menu('.community_func12', commands);
//    }

//    if ($('.community_func13').length) {
//        var commands = Array(Array('Уволиться', '13'));
//        participants_menu('.community_func13', commands);
//    }
//
//    if ($('.community_func14').length) {
//        var commands = Array(Array('Выйти', '14'));
//        participants_menu('.community_func14', commands);
//    }
//
//    if ($('.community_func15').length) {
//        var commands = Array(Array('Отписаться', '15'));
//        participants_menu('.community_func15', commands);
//    }

    if ($('#contents').length) {
        alt_input('#contents');
    }

    $(document).on('click', '.button[func],.punkt[func]', function (e) {
        func_button($(this).attr('func'), this);
        return false;
    });

    if ($('.rating_questions').length) {

        $(document).on('click', '.rating_questions span', function (e) {
            var data = $(this).html();
            data = data.slice(1) * 1 + 1;
            if ($(this).hasClass('plus_questions')) {
                $(this).html('+' + data);
            } else {
                $(this).html('-' + data);
            }
            return false;
        });

        $(document).on('click', '.plus_questions', function (e) {
        });
        $(document).on('click', '.minus_questions', function (e) {
        });

        $(document).on('click', '.this_otvet', function (e) {
            var otvet = $(this);
            otvet.parents('.comment_content').find('.comment_text').css('font-weight', 'bold');
            otvet.html('Это ответ');
            otvet.addClass('otvet_green');
            otvet.unbind();
        });


    }


});

function split(val) {
    return val.split(/,\s*/);
}
function extractLast(term) {
    return split(term).pop();
}


// анимация контекстного меню в участниках
/*function participants_menu(name,commands){
 var commands_l=commands.length;
 var commands_str='';
 for (var i = 0; i < commands_l; i++) {
 commands_str+='<li><a>'+commands[i]+'</a></li>';
 }
 $(name).append('<div class="participants_menu"><span class="participants_menu_show"></span><ul>'+commands_str+'</ul></div>');
 $(name).hover(function(e){
 $(this).find('.participants_menu').css('display','block');
 },function(e){
 $(this).find('.participants_menu').css('display','none');
 $(this).find('.participants_menu ul').css('display','none');
 $(this).find('.participants_menu span').css("background","url('/images/participants_func.jpg') right top no-repeat");
 }
 );
 $(name+' .participants_menu_show').click(function(e){
 var menu=$(this).next();
 if(menu.is(':hidden')){
 menu.css('display','block');
 $(this).css("background","url('/images/participants_func_a.jpg') right top no-repeat");
 }else{
 menu.css('display','none');
 $(this).css("background","url('/images/participants_func.jpg') right top no-repeat");
 }
 });
 }*/

// создание закладок
function create_tabs() {
    var tabs = $('.tabs');
    var tabs_int = $('.tabs_interactiv');
    activ_tab(tabs);
    tabs_int.find('span').bind('click', function (e) {
        if ($(this).next().is(':hidden')) {
            tabs.find('span.activ').next().css('display', 'none');
            tabs.find('span.activ').removeClass('activ');
            $(this).addClass('activ');
            activ_tab(tabs);
        }
    });
}

// активная закладка
function activ_tab(tabs) {
    var span = tabs.find('span.activ');
    var div = span.next();
    div.css('display', 'block');
    var h_tab = div.height();
    if (h_tab == 0) {
        h_tab = 350;
    }
    tabs.css('margin-bottom', h_tab);
}

// чекбоксы на изображениях
function edit_img_albom(data) {
    var list_block_img = data.find('.my');
    $.each(list_block_img, function (index, value) {
        $(value).append('<span class="check_img check_disabled"></span>');
        $(value).find('span.check_img').bind('click', function (e) {
            if ($(this).hasClass('check_disabled')) {
                $(this).addClass('check_enabled');
                $(this).removeClass('check_disabled');
            } else {
                $(this).addClass('check_disabled');
                $(this).removeClass('check_enabled');
            }
            return false;
        });
    });
}

function view_img(element, w_) {
    var src = $(element).parent().find('img').attr('src');
    var new_src = src.replace(/\.jpg/g, '_full\.jpg');
    var img_new = new Image();
    img_new.onload = function () {
        var x = width = $(window).width() / 2;
        var y = height = $(window).height() / 2 + $(document).scrollTop();
        var w = this.width;
        var h = this.height;
        var w_ = $(window).width();
        var h_ = $(window).height();
        if (w >= w_) {
            var k = h / w;
            w = w_ - 40;
            h = w * k;
        }
        if (h >= h_) {
            var k = w / h;
            h = h_ - 40;
            w = h * k;
        }
        if ($('.show_img').length) {
            $('.show_img').remove();
        }
        $('body').append('<div class="show_img"><img src="' + new_src + '" /><span class="full_img_close"></span></div>');
        $('.show_img').css({
            'width': '1px',
            'height': '1px',
            'position': 'absolute',
            'left': x + 'px',
            'top': y + 'px',
        });
        $('.show_img img').css({
            'width': '1px',
            'height': '1px',
            'position': 'absolute',
            'left': '0px',
            'top': '0px',
            'border': '2px solid #555555',
            '-webkit-border-radius': '7px',
            'border-radius': '7px',
            'behavior': 'url("/PIE/PIE.htc")',
            'z-index': '999999',
            'cursor': 'pointer'
        });
        $('.show_img img,.full_img_close').click(function () {
            $('.show_img').remove();
        });
        $('.show_img').animate({
            'width': w + 'px',
            'height': h + 'px',
            'left': (x * 1 - w / 2) * 1 + 'px',
            'top': (y * 1 - h / 2) * 1 + 'px',
        }, 200);
        $('.show_img img').animate({
            'width': w + 'px',
            'height': h + 'px',
        }, 200);
    }
    img_new.src = new_src;
}

function alt_input(name) {
    var input = $(name + ' input[type=text], ' + name + ' textarea');
    $.each(input, function (index, value) {
        var alt = $(value).attr('alt');
        if (alt) {
            if ($(value).val() == '') {
                $(value).val(alt);
            }
            $(value).bind('focus', function (e) {
                if ($(this).val() == alt) {
                    $(this).val('');
                }
            });
            $(value).bind('blur', function (e) {
                var v = $(this).val();
                if ((v == '') || (v == 'alt')) {
                    $(value).val(alt);
                }
            });
        }
    });
}

function func_button(id_func, is_button) {
    switch (id_func) {
        case '2':
            window.location = "/profile/edit.html"
            break;
        case '3':
            window.location = "/profile/index.html"
            break;
        case '4':
            $(is_button).parents('tr').remove();
            break;
        case '5':
            var new_zap = '<tr><td>с <input type="text" class="datepicker"><br/>по <input type="text" class="datepicker"></td><td class="price_td"><input type="text"><div class="currency">' + $($('.currency')[0]).html() + '</div></td><td><input type="text"></td><td><input type="text"></td><td><a class="button" func="4">Удалить</a></td></tr>';
            $('article.edit .table_inf tbody').append(new_zap);
            $(".datepicker").datepicker();
            break;
        case '6':
            // принять
            break;
        case '7':
            // отклонить
            var button = $(is_button);
            button.parents('.table_inf').find('.table_comment').remove();
            button.parents('tr').after('<tr class="table_comment"><td colspan="6"><div class="comment_new"><textarea noresize></textarea><input type="button" value="Отправить" class="button"></div></td></tr>');
            button.parents('.table_inf').find('.table_comment input[type="button"]').click(function () {
                button.parents('.table_inf').find('.table_comment').remove();
                button.parents('td').html('<a class="button" title="Отправить заявку на участие">Заявка</a>');
            });
            break;
        case '8':
            // заявка
            break;
        case '9':
            // Отказаться
            break;
        case '10':
            var new_zap = '<tr><td>с <input type="text" class="datepicker"><br>по <input type="text" class="datepicker"></td><td><input type="text"></td><td><input type="text"></td><td><input type="text"></td><td><a class="button" func="4">Удалить</a></td></tr>';
            $(is_button).parent().find('tbody').append(new_zap);
            $(".datepicker").datepicker();
            break;
        case '11':
            var new_zap = '<tr><td>с <input type="text" class="datepicker"><br>по <input type="text" class="datepicker"></td><td><textarea></textarea></td><td><a class="button" func="4">Удалить</a></td></tr>';
            $(is_button).parent().find('tbody').append(new_zap);
            $(".datepicker").datepicker();
            break;
        case '12':
            $(is_button).parents('li').remove();
            //community_close(is_button);
            break;
        case '13':
            $(is_button).parents('li').remove();
            //community_resign(is_button);
            break;
        case '14':
            $(is_button).parents('li').remove();
            //community_escape(is_button);
            break;
        case '15':
            $(is_button).parents('li').remove();
            //community_unsubscribe(is_button);
            break;
        case '16':
            alert('ok');
            //community_unsubscribe(is_button);
            break;
        case '17':
            alert('ok');
            //community_unsubscribe(is_button);
            break;
        case '18':
            alert('ok');
            //community_unsubscribe(is_button);
            break;
        case '19':
            alert('ok');
            //community_unsubscribe(is_button);
            break;
        case '20':
            alert('20');
            var el = $(is_button).parents('li.participants_li');
            el.find('.participants_menu').remove();
            $('#list_user_1>ul').append(el);
            break;
        case '21':
            alert('21');
            var el = $(is_button).parents('li.participants_li');
            el.find('.participants_menu').remove();
            $('#list_user_2>ul').append(el);
            break;
        case '22':
            alert('22');
            var el = $(is_button).parents('li.participants_li');
            el.find('.participants_menu').remove();
            $('#list_user_3>ul').append(el);
            break;
        case '23':
            alert('23');
            var el = $(is_button).parents('li.participants_li');
            el.find('.participants_menu').remove();
            $('#list_user_4>ul').append(el);
            break;
        case '24':
            alert('24');
            $(is_button).parents('li.participants_li').remove();
            break;
        case '25':
            alert('25');
            window.location = "/profile/chat.html"
            break;
        default:
            //
            break;
    }


}

