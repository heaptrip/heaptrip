$(document).ready(function () {
    // календарь
    if ($(".datepicker").length) {
        $(".datepicker").datepicker();
    }

    // выбор валюты

    $(document).on('click', '.currency span', function (e) {
        if ($(this).next().is(':hidden')) {
            $(this).next().slideDown(200);
        } else {
            $(this).next().slideUp(200);
        }
    });

    $(document).on('click', '.currency li', function (e) {
        $(this).parents('.currency').find('span').html($(this).html());
        $(this).parent().slideUp(200);
    });

    if ($('div.albom .edit').length) {
        edit_img_albom($('div.albom .edit'));
    }

    if ($('.tabs_content .edit').length) {
        edit_img_albom($('.tabs_content .edit'));
    }

    if ($('.edit_img').length) {
        $('.edit_img').click(function () {
            if ($('.edit_img').val() == 'Редактировать') {
                $('.edit_img').val('Сохранить');
                $('#article').addClass('edit');
                var name_img = $('.tabs span.activ').text();
                $('.tabs span.activ').html('<input type="text" value="' + name_img + '">');
                var text_img = $('#article .description').text();
                $('#article .description').html('<textarea>' + text_img + '</textarea>');
            } else {
                $('.edit_img').val('Редактировать');
                $('#article').removeClass('edit');
                var name_img = $('.tabs span.activ input').val();
                $('.tabs span.activ').text(name_img);
                var text_img = $('#article .description textarea').val();
                $('#article .description').html(text_img);
            }
        });
    }


    if ($('.participants_func').length) {
        var commands = Array('Сообщение', 'Организатор', 'Переписка', 'Удалить');
        participants_menu('.participants_func', commands);
    }

    if ($('.participants_is').length) {
        var commands = Array('Принять', 'Отказать');
        participants_menu('.participants_is', commands);
    }

    if ($('.people_func').length) {
        var commands = Array('Переписка', 'Удалить');
        participants_menu('.people_func', commands);
    }

    if ($('.people_func_otpis').length) {
        var commands = Array('Отписаться');
        participants_menu('.people_func_otpis', commands);
    }

    if ($('.options_fb').length) {
        var commands = Array('Отвязать', '<nobr>Использовать аватарку</nobr>');
        participants_menu('.options_fb', commands, 'fb');
    }

    if ($('.options_tv').length) {
        var commands = Array('Отвязать', '<nobr>Использовать аватарку</nobr>');
        participants_menu('.options_tv', commands, 'tv');
    }

    if ($('.options_vk').length) {
        var commands = Array('Отвязать', '<nobr>Использовать аватарку</nobr>');
        participants_menu('.options_vk', commands, 'vk');
    }

    if ($('.options_od').length) {
        var commands = Array('Отвязать', '<nobr>Использовать аватарку</nobr>');
        participants_menu('.options_od', commands, 'od');
    }

    if ($('.soc_list').length) {
        $('.soc_list li').bind('click', function (e) {
            var new_ac = $(this);
            switch (new_ac.attr('class')) {
                case 'fb':
                    $('.list_user_soc ul:not(.soc_list)').append('<li class="participants_li options_fb"><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li>');
                    var commands = Array('Отвязать', '<nobr>Использовать аватарку</nobr>');
                    participants_menu('.options_fb', commands, 'fb');
                    break;
                case 'od':
                    $('.list_user_soc ul:not(.soc_list)').append('<li class="participants_li options_od"><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li>');
                    var commands = Array('Отвязать', '<nobr>Использовать аватарку</nobr>');
                    participants_menu('.options_od', commands, 'od');
                    break;
                case 'tv':
                    $('.list_user_soc ul:not(.soc_list)').append('<li class="participants_li options_tv"><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li>');
                    var commands = Array('Отвязать', '<nobr>Использовать аватарку</nobr>');
                    participants_menu('.options_tv', commands, 'tv');
                    break;
                case 'vk':
                    $('.list_user_soc ul:not(.soc_list)').append('<li class="participants_li options_vk"><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li>');
                    var commands = Array('Отвязать', '<nobr>Использовать аватарку</nobr>');
                    participants_menu('.options_vk', commands, 'vk');
                    break;
            }
        });
    }


    if ($('.participants_invite').length) {
        participants_invite('.participants_invite');
    }

    if ($('.participants_add').length) {
        $('.participants_add').bind('click', function (e) {
            participants_add(this);
        });
    }

    if ($('.posts_find').length) {
        posts_add();
    }

    if ($('#people').length) {
        frend_add();
    }

    /*if($('#community').length){
     community_add();
     }  */

    if ($('.price a.button').length) {
        $(document).on('click', '.price a.button', function (e) {
            $(this).parents('article').remove();
        });
    }

    if ($('.add_list_lang').length) {
        $('.add_lang').click(function (e) {
            if ($(this).next().is(':hidden')) {
                $(this).next().slideDown(100);
            } else {
                $(this).next().slideUp(100);
            }
            return false;
        });

        //$(document).on('click','.inf .right ul li:not(.add_list_lang,.add_list_lang li)',function(e){
        //});


        $(document).on('click', '.add_list_lang li', function (e) {
            $('.inf .right ul li').removeClass('activ_lang')
            $('.inf .right ul li.add_list_lang').before('<li class="activ_lang">' + $(this).html() + '</li>');
            $(this).remove();
            $('.add_lang').next().slideUp(100);
            return false;
        });

        $('.del_lang').click(function (e) {
            if (confirm("Вы действительно хотите удалить этот язык?")) {
                var activ_lang = $(this).parents('ul').find('li.activ_lang');
                $('.inf .right ul li').removeClass('activ_lang')
                $('.add_list_lang ul').append('<li>' + activ_lang.html() + '</li>');
                activ_lang.remove();
                $(this).parent().next().addClass('activ_lang');
            }
            return false;
        });
    }

    $(".my_location input[type=text]")
        // don't navigate away from the field on tab when selecting an item
        .bind("keydown", function (event) {
            if (event.keyCode === $.ui.keyCode.TAB &&
                $(this).data("ui-autocomplete").menu.active) {
                event.preventDefault();
            }
        })
        .autocomplete({
            source: function (request, response) {

                var url = 'rest/search_regions';

                var callbackSuccess = function (data) {
                    response(
                        $.map(data, function (item) {
                            var newPath = stringMarker(request.term, item.path);
                            if (newPath == item.path) {
                                newPath = stringMarker(item.data, item.path);
                            }
                            return {
                                label: newPath,
                                text: item.data,
                                value: item.id
                            };
                        }));
                };

                var callbackError = function (error) {
                    alert(error);
                };

                $.postJSON(url, request.term, callbackSuccess, callbackError);

            },
            search: function () {
                // custom minLength
                var term = extractLast(this.value);
                if (term.length < 2) {
                    return false;
                }
            },
            focus: function () {
                // prevent value inserted on focus
                return false;
            },
            open: function (event, ui) {
                $("ul.ui-autocomplete li a").each(function () {
                    var htmlString = $(this).html().replace(/&lt;/g, '<');
                    htmlString = htmlString.replace(/&gt;/g, '>');
                    $(this).html(htmlString);
                });
            },
            select: function (event, ui) {
                var regId = ui.item.value;
                $(this).val(ui.item.text);
                $(this).attr("reg_id", regId);
                return false;
            }
        }
    );

    if ($('.my_lang_edit').length) {
        $(document).on('click', '.my_inf .my_lang_edit ul li span', function (e) {
            $(this).parent().remove();
        });

        $('.my_lang_edit .add_lang').click(function (e) {
            if ($(this).next().is(':hidden')) {
                $(this).next().slideDown(100);
            } else {
                $(this).next().slideUp(100);
            }
            return false;
        });

        $('.my_lang_edit .my_add_lang li').click(function (e) {
            var cl = $(this).find('a').attr('class');
            var lang = $(this).find('a').text();
            $('.my_add_lang').before('<li class="' + cl + '">' + lang + '<span></span></li>');
            $(this).remove();
            if (!$('.my_lang_edit .my_add_lang li').length) {
                $('.my_lang_edit .my_add_lang').css('display', 'none');
            }
        });
    }
});


function participants_add(button) {
    $(button).text('Отменить');
    $(button).unbind();
    var block_select = $(button).parents('.list_user');
    $(button).click(function () {
        $(button).text('Пригласить');
        block_select.find('.participants_invite').css({
            'display': '-moz-inline-stack',
            'display': 'inline-block',
        });
        block_select.find('.participants_find').remove();
        $('.list_user.search').remove();
        var block = block_select;
        while (block.next().length) {
            block = block.next();
            block.css('display', 'block');
        }
        $('#pagination').css('display', 'block');
        $(button).unbind();
        $(button).click(function (e) {
            participants_add(this);
        });
    });
    block_select.find('.participants_invite').css('display', 'none');
    $('#pagination').css('display', 'none');
    var i = 0;
    var block = block_select;
    while (block.next().length) {
        block = block.next();
        block.css('display', 'none');
    }
    block_select.find('.list_user_button').append('<div class="participants_find"><input type="text" alt="....."></div>');
    alt_input('.list_user_button');
    block_select.after('<div class="list_user search"><ul></ul></div>');
    $(".participants_find input[type=text]").bind("keydown",function (event) {
        if (event.keyCode === $.ui.keyCode.TAB && $(this).data("ui-autocomplete").menu.active) {
            event.preventDefault();
        }
    }).autocomplete({
            source: function (request, response) {
                // request.term;  это введенные символы для поиска которые должны уйти на сервер
                $.getJSON("user_serch.json", {
                    term: extractLast(request.term)
                }, function (data) {
                    create_list_user_serch(data, block_select);
                });
            },
            search: function () {
                // custom minLength
                var term = extractLast(this.value);
                if (term.length < 2) {
                    return false;
                }
            },
            focus: function () {
                // prevent value inserted on focus
                return false;
            },
            select: function (event, ui) {
                console.log(ui.item.value);
                $(".participants_find input[type=text]").val('');
                return false;
            }
        });
}


function create_list_community_serch2(data, block_select, conteiner) {
    var l = data.length;
    var str = '';
    for (var i = 0; i < l; i++) {
        str += '<li class="participants_li" id="community_id_' + data[i].id + '"><div class="list_user_img"><img src="/' + data[i].avatar + '"></div><div class="list_user_name"><a href="/">' + data[i].name + '</a></div><!--<a class="add_is">&#43;</a>--></li>';
    }
    $(conteiner).html(str);
    $(document).on('click', conteiner + ' .add_is', function (e) {
        var li = $(this).parents('li');
        li.addClass("participants_func");
        $(this).remove();
        if (block_select) {
            $(block_select).find('ul').not('.participants_menu ul').append(li);
            var commands = Array('Сообщение', 'Организатор', 'Переписка', 'Удалить');
            participants_menu('.participants_func', commands);
        } else {
            $(li).remove();
        }
    });
}

function create_list_community_serch(data, block_select, conteiner) {
    var l = data.length;
    var str = '';
    for (var i = 0; i < l; i++) {
        str += '<li class="participants_li" id="community_id_' + data[i].id + '"><div class="list_user_img"><img src="/' + data[i].avatar + '"></div><div class="list_user_name"><a href="/">' + data[i].name + '</a></div><!--<a class="add_is">&#43;</a>--></li>';
    }
    $(conteiner).html(str);
    $(document).on('click', conteiner + ' .add_is', function (e) {
        var li = $(this).parents('li');
        li.addClass("participants_func");
        $(this).remove();
        if (block_select) {
            $(block_select).find('ul').not('.participants_menu ul').append(li);
            var commands = Array('Сообщение', 'Организатор', 'Переписка', 'Удалить');
            participants_menu('.participants_func', commands);
        } else {
            $(li).remove();
        }
    });
}

// анимация контекстного меню в участниках
function participants_menu(name, commands, clas) {
    var commands_l = commands.length;
    var commands_str = '';
    for (var i = 0; i < commands_l; i++) {
        if (typeof(commands[i]) === 'object') {
            commands_str += '<li><a class="punkt" func="' + commands[i][1] + '">' + commands[i][0] + '</a></li>';
        } else {
            commands_str += '<li><a>' + commands[i] + '</a></li>';
        }

    }
    var dop_pic = '';
    if (clas) {
        dop_pic = '<span class="dop_pic ' + clas + '"></span>';
    }

    $(name).append('<div class="participants_menu"><span class="participants_menu_show"></span>' + dop_pic + '<div class="participants_list_menu"><ul>' + commands_str + '</ul></div></div>');
//  $(name+' .participants_menu a').click(function(e){
//    var user=$(this).parents('.participants_li');
//    console.log($(this).text());
//    switch ($(this).text()) {
//      case 'Сообщение':
//        break;
//      case 'Организатор':
//        break;
//      case 'Переписка':
//           window.location = "/profile/chat.html"
//        break;
//      case 'Удалить':
//          participants_del_user(user);
//        break;
//      case 'Принять':
//
//        break;
//      case 'Отказать':
//
//        break;
//      case 'Отвязать':
//          $(user).remove();
//        break;
//      case 'Отписаться':
//          participants_otpis_user(user);
//        break;
//      default:
//          //
//        break;
//    }
//
//
//  });

    if (clas) {
        $(name).find('.participants_menu').css('display', 'block');
    }
    $(name).hover(function (e) {
            $(this).find('.participants_menu').css('display', 'block');
        }, function (e) {
            if (!clas) {
                $(this).find('.participants_menu').css('display', 'none');
            }
            $(this).find('.participants_menu div').css('display', 'none');
            $(this).find('.participants_menu .participants_menu_show').css("background", "url('images/participants_func.jpg') right top no-repeat");
        }
    );
    $(name + ' .participants_menu_show, ' + name + ' .dop_pic').click(function (e) {
        var menu = $(name + ' .participants_list_menu');
        if (menu.is(':hidden')) {
            menu.css('display', 'block');
            $(this).parent().find('.participants_menu_show').css("background", "url('images/participants_func_a.jpg') right top no-repeat");
        } else {
            menu.css('display', 'none');
            $(this).parent().find('.participants_menu_show').css("background", "url('images/participants_func.jpg') right top no-repeat");
        }
    });
}

function participants_invite(name) {
    $(name).append('<div class="participants_invite_show"><span>Введите E-mail:</span><input type="text" class="invite_mail"><a class="button" class="go_invite">Отправить</a></div>');
    $(name + ' a.participants_invite_button').click(function (e) {
        var menu = $(this).next();
        if (menu.is(':hidden')) {
            menu.css('display', 'block');
            $(this).css("background", "url('images/participants_invite_a.jpg') right top no-repeat");
        } else {
            menu.css('display', 'none');
            $(this).css("background", "url('images/participants_invite.jpg') right top no-repeat");
        }
    });
}

function participants_del_user(user) {
    if ($('.list_user.search ul').length) {
        $('.list_user.search ul').append(user);
        user.find('.participants_menu').remove();
        user.removeClass('participants_func');
        user.append('<a class="add_is">&#43;</a>');
    } else {
        user.remove();
    }
}

function participants_otpis_user(user) {
    if ($('.list_user.search ul').length) {
        $('.list_user.search ul').append(user);
        user.find('.participants_menu').remove();
        user.removeClass('participants_func');
        user.append('<a class="add_is">&#43;</a>');
    } else {
        user.remove();
    }
}

function posts_add(button) {

    $(".posts_find input[type=text]").bind("keydown",function (event) {
        if (event.keyCode === $.ui.keyCode.TAB && $(this).data("ui-autocomplete").menu.active) {
            event.preventDefault();
        }
    }).autocomplete({
            source: function (request, response) {
                // request.term;  это введенные символы для поиска которые должны уйти на сервер
                $.getJSON("list_posts.json", {
                    term: extractLast(request.term)
                }, function (data) {
                    create_list_posts_serch(data);
                });
            },
            search: function () {
                // custom minLength
                var term = extractLast(this.value);
                if (term.length < 2) {
                    return false;
                }
            },
            focus: function () {
                // prevent value inserted on focus
                return false;
            },
            select: function (event, ui) {
                //console.log(ui.item.value);
                //$(".participants_find input[type=text]").val('');
                return false;
            }
        });
}

function create_list_posts_serch(data, block_select) {
    //console.log(data);
    var l = data.length;
    var str = '';//data[i].id
    for (var i = 0; i < l; i++) {
        str += '<li id="' + data[i].id + '"><div class="list_posts_img"><img src="/' + data[i].img + '"></div><div class="list_posts_inf"><span>' + data[i].date + '</span>' + data[i].autor + ' (' + data[i].rating + ')<div class="list_posts_name"><a href="/">' + data[i].name + '</a></div></div><a class="add_is">&#43;</a></li>';
    }
    $('.list_posts_add ul').html(str);
    $(document).on('click', '.add_is', function (e) {
        var id = $(this).parents('li').attr('id');
        for (var i = 0; i < l; i++) {
            if (id == data[i].id) {
                //console.log(data[i]);
                str = '';
                str = '<article id="article"><div class="date">' + data[i].date + '<span>Событие</span></div><div class="inf"><div class="left"><h2><a href="/">' + data[i].name + '</a></h2>' + data[i].autor + '<span>(' + data[i].rating + ')</span></div><div class="right"><div>Период:<span class="date">с ' + data[i].date1 + ' по ' + data[i].date2 + '</span></div><div>Место:<span class="location">' + data[i].loc + '</span></div></div></div><div class="description"><img src="' + data[i].img_post + '" width="300" align="left">Описание: ' + data[i].descr + '</div><div>';
                str += '<div class="tags">';
                var tags_l = data[i].tags.length;
                for (var j = 0; j < tags_l; j++) {
                    str += '<a href="#">' + data[i].tags[j] + '</a>';
                }
                str += '</div>';
                str += '<div class="price"><a class="button">Удалить пост</a></div></div><div><div class="views">Просмотров:<span>' + data[i].count_view + '</span></div><div class="comments">Коментариев:<span>' + data[i].comments + '</span></div><div class="wertung">Рейтинг:<div class="stars ' + data[i].stars + '"></div><span>(' + data[i].rating_post + ')</span></div></div></article>';
                $('.list_posts_button').before('<article id="article">' + str + '</article>');
                $(this).parents('li').remove();
            }
        }
    });


}

function frend_add(button) {
    $("#people input[name=text_search]").bind("keydown",function (event) {
        if (event.keyCode === $.ui.keyCode.TAB && $(this).data("ui-autocomplete").menu.active) {
            event.preventDefault();
        }
    }).autocomplete({
            source: function (request, response) {
                // request.term;  это введенные символы для поиска которые должны уйти на сервер
                $.getJSON("/user_serch.json", {
                    term: extractLast(request.term)
                }, function (data) {

                    if (!$('.description .list_user.search').length) {
                        $('.description').append('<div class="list_user search"><ul></ul></div>');
                    }
                    create_list_community_serch2(data['resFind'], false, '.list_user.search ul');
                    var commands = Array(Array('Друзья', '20'), Array('Подписан', '21'));
                    participants_menu('.list_user.search ul li', commands);
                    create_list_community_serch2(data['vladeyu'], false, '#list_user_1 ul');
                    var commands = Array(Array('Переписка', '25'), Array('Удалить', '24'));
                    participants_menu('#list_user_1 ul li', commands);
                    create_list_community_serch2(data['rabotayu'], false, '#list_user_2 ul');
                    var commands = Array(Array('Отписаться', '24'));
                    participants_menu('#list_user_2 ul li', commands);
                });
            },
            search: function () {
                // custom minLength
                var term = extractLast(this.value);
                if (term.length < 2) {
                    return false;
                }
            },
            focus: function () {
                // prevent value inserted on focus
                return false;
            },
            select: function (event, ui) {
                console.log(ui.item.value);
                $("#people input[name=text_search]").val('');
                return false;
            }
        });
}

//function community_add(button){
//  $( "#community input[name=text_search]" ).bind( "keydown", function( event ) {
//    if ( event.keyCode === $.ui.keyCode.TAB && $( this ).data( "ui-autocomplete" ).menu.active ) {
//      event.preventDefault();
//    }
//  }).autocomplete({
//    source: function( request, response ) {
//      // request.term;  это введенные символы для поиска которые должны уйти на сервер
//      $.getJSON( "/community_serch.json",{
//            term: extractLast( request.term )
//          }, function(data){
//
//        if(!$('.description .list_user.search').length){
//          $('.description').append('<div class="list_user search"><ul></ul></div>');
//        }
//        console.log(data);
//        create_list_community_serch(data['resFind'],false,'.list_user.search ul');
//        var commands=Array(Array('Я владею','20'),Array('Я работаю','21'),Array('Я учавствую','22'),Array('Я подписан','23'));
//        participants_menu('.list_user.search ul li',commands);
//        create_list_community_serch(data['vladeyu'],false,'#list_user_1 ul');
//        var commands=Array(Array('Закрыть','24'));
//        participants_menu('#list_user_1 ul li',commands);
//        create_list_community_serch(data['rabotayu'],false,'#list_user_2 ul');
//        var commands=Array(Array('Уволиться','24'));
//        participants_menu('#list_user_2 ul li',commands);
//        create_list_community_serch(data['uchavstvuyu'],false,'#list_user_3 ul');
//        var commands=Array(Array('Выйти','24'));
//        participants_menu('#list_user_3 ul li',commands);
//        create_list_community_serch(data['podpisan'],false,'#list_user_4 ul');
//        var commands=Array(Array('Отписаться','24'));
//        participants_menu('#list_user_4 ul li',commands);
//
//      });
//    },
//    search: function() {
//      // custom minLength
//      var term = extractLast( this.value );
//      if ( term.length < 2 ) {
//        return false;
//      }
//    },
//    focus: function() {
//      // prevent value inserted on focus
//      return false;
//    },
//    select: function( event, ui ) {
//      console.log(ui.item.value);
//      $("#community input[name=text_search]").val('');
//      return false;
//    }
//  });
//}