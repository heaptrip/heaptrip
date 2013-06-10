$(document).ready(function() {
	// календарь
	if($(".datepicker" ).length){
      	$(".datepicker" ).datepicker();
  	}

  	// выбор валюты

	$(document).on('click','.currency span',function(e){
		if($(this).next().is(':hidden')){
  			$(this).next().slideDown(200);
  		}else{
  			$(this).next().slideUp(200);
  		}
  	});

  	$(document).on('click','.currency li',function(e){
  		$(this).parents('.currency').find('span').html($(this).html());
  		$(this).parent().slideUp(200);
  	});

	$(document).on('click','.del_per',function(e){
		$(this).parents('tr').remove();
	});

  	if($('.add_table_zap').length){
  		$('.add_table_zap').click(function(e){
  			var new_zap='<tr><td>с <input type="text" class="datepicker"><br/>по <input type="text" class="datepicker"></td><td class="price_td"><input type="text"><div class="currency"><span>РУБ</span><ul><li>РУБ</li><li>US</li><li>EURO</li><ul></div></td><td><input type="text"></td><td><input type="text"></td><td><a class="button del_per">Удалить</a></td></tr>';
  			$('article.edit .table_inf tbody').append(new_zap);
  			$(".datepicker" ).datepicker();
  		});
  	}

  	if($('#contents').length){
  		alt_input('#contents');
  	}

	if($('div.albom .edit').length){
		edit_img_albom($('div.albom .edit'));
	}

	if($('.tabs_content .edit').length){
		edit_img_albom($('.tabs_content .edit'));
	}

  	if($('.edit_img').length){
  		$('.edit_img').click(function(){
  			if($('.edit_img').val()=='Редактировать'){
  				$('.edit_img').val('Сохранить');
  				$('#article').addClass('edit');
  				var name_img=$('.tabs span.activ').text();
  				$('.tabs span.activ').html('<input type="text" value="'+name_img+'">');
  				var text_img=$('#article .description').text();
  				$('#article .description').html('<textarea>'+text_img+'</textarea>');
  			}else{
  				$('.edit_img').val('Редактировать');
  				$('#article').removeClass('edit');
  				var name_img=$('.tabs span.activ input').val();
  				$('.tabs span.activ').text(name_img);
  				var text_img=$('#article .description textarea').val();
  				$('#article .description').html(text_img);  				
  			}
  		});
  	}  


    if($('.participants_func').length){
      var commands=Array('Сообщение','Организатор','Переписка','Удалить');
      participants_menu('.participants_func',commands);
    }

    if($('.participants_is').length){
      var commands=Array('Принять','Отказать');
      participants_menu('.participants_is',commands);
    }

    if($('.participants_invite').length){
      participants_invite('.participants_invite');
    }

    if($('.participants_add').length){
      $('.participants_add').bind('click',function(e){
        participants_add(this);
      });
    }

    if($('.posts_find').length){
      posts_add();
    }


});


function participants_add(button){
  $(button).text('Отменить');
  $(button).unbind();
  var block_select=$(button).parents('.list_user');
  $(button).click(function(){
    $(button).text('Пригласить');
    block_select.find('.participants_invite').css({
      'display': '-moz-inline-stack',
      'display': 'inline-block',
    });
    block_select.find('.participants_find').remove();
    $('.list_user.search').remove();
    var block=block_select;
    while(block.next().length){
      block=block.next();
      block.css('display','block');
    }
    $('#pagination').css('display','block');
    $(button).unbind();
    $(button).click(function(e){
      participants_add(this);
    });
  });
  block_select.find('.participants_invite').css('display','none');
  $('#pagination').css('display','none');
  var i=0;
  var block=block_select;
  while(block.next().length){
    block=block.next();
    block.css('display','none');
  }
  block_select.find('.list_user_button').append('<div class="participants_find"><input type="text" alt="....."></div>');
  alt_input('.list_user_button');
  block_select.after('<div class="list_user search"><ul></ul></div>');
  $( ".participants_find input[type=text]" ).bind( "keydown", function( event ) {
    if ( event.keyCode === $.ui.keyCode.TAB && $( this ).data( "ui-autocomplete" ).menu.active ) {
      event.preventDefault();
    }
  }).autocomplete({
    source: function( request, response ) {
      // request.term;  это введенные символы для поиска которые должны уйти на сервер
      $.getJSON( "user_serch.json",{
            term: extractLast( request.term )
          }, function(data){
        create_list_user_serch(data,block_select);
      });
    },
    search: function() {
      // custom minLength
      var term = extractLast( this.value );
      if ( term.length < 2 ) {
        return false;
      }
    },
    focus: function() {
      // prevent value inserted on focus
      return false;
    },
    select: function( event, ui ) {
      console.log(ui.item.value);
      $(".participants_find input[type=text]").val('');
      return false;
    }
  });
}


function create_list_user_serch(data,block_select){
  //console.log(data);
  var l=data.length;
  var str='';
  for (var i = 0; i < l; i++) {
    str+='<li class="participants_li" id="user_id_'+data[i].id+'"><div class="list_user_img"><img src="/avatar/'+data[i].avatar+'"></div><div class="list_user_name"><a href="/">'+data[i].name+'</a></div><a class="add_is">&#43;</a></li>';
  }
  $('.list_user.search ul').html(str);
  $(document).on('click','.list_user .add_is',function(e){
    var li=$(this).parents('li');
    li.addClass("participants_func");
    $(this).remove();
    $(block_select).find('ul').not('.participants_menu ul').append(li);
    var commands=Array('Сообщение','Организатор','Переписка','Удалить');
    participants_menu('.participants_func',commands);
  });
}

function alt_input(name){
    var input=$(name+' input[type=text], '+name+' textarea');
    $.each(input, function(index, value) {
        var alt=$(value).attr('alt');
        if(alt){
            $(value).val(alt);
            $(value).bind('focus',function(e){
                if($(this).val()==alt){
                    $(this).val('');
                }
            });
            $(value).bind('blur',function(e){
                var v=$(this).val();
                if((v=='')||(v=='alt')){
                    $(value).val(alt);
                }
            });
        }
    });    
}  	

// анимация контекстного меню в участниках
function participants_menu(name,commands){
  var commands_l=commands.length;
  var commands_str='';
  for (var i = 0; i < commands_l; i++) {
    commands_str+='<li><a>'+commands[i]+'</a></li>';
  }
  $(name).append('<div class="participants_menu"><span class="participants_menu_show"></span><div><ul>'+commands_str+'</ul></div></div>');
  $(name+' .participants_menu a').click(function(e){
    var user=$(this).parents('.participants_li');
    console.log($(this).text());
    switch ($(this).text()) {
      case 'Сообщение':
          
        break;
      case 'Организатор':
        
        break;
      case 'Переписка':
        
        break;
      case 'Удалить':
          participants_del_user(user);
        break;
      case 'Принять':
        
        break;
      case 'Отказать':
        
        break;
      default:
          //
        break;
    }


  });
  $(name).hover(function(e){
        $(this).find('.participants_menu').css('display','block');
      },function(e){
        $(this).find('.participants_menu').css('display','none');
        $(this).find('.participants_menu div').css('display','none');
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
}

// 
function participants_invite(name){
  $(name).append('<div class="participants_invite_show"><span>Введите E-mail:</span><input type="text" class="invite_mail"><a class="button" class="go_invite">Отправить</a></div>');
  $(name+' a.participants_invite_button').click(function(e){
      var menu=$(this).next();
      if(menu.is(':hidden')){
        menu.css('display','block');
        $(this).css("background","url('/images/participants_invite_a.jpg') right top no-repeat");
      }else{
        menu.css('display','none');
        $(this).css("background","url('/images/participants_invite.jpg') right top no-repeat");
      }    
  });
}

function participants_del_user(user){
  if($('.list_user.search ul').length){
    $('.list_user.search ul').append(user); 
    user.find('.participants_menu').remove();
    user.removeClass('participants_func');
    user.append('<a class="add_is">&#43;</a>');
  }else{
    user.remove();
  }
  
}

function posts_add(button){

  $( ".posts_find input[type=text]" ).bind( "keydown", function( event ) {
    if ( event.keyCode === $.ui.keyCode.TAB && $( this ).data( "ui-autocomplete" ).menu.active ) {
      event.preventDefault();
    }
  }).autocomplete({
    source: function( request, response ) {
      // request.term;  это введенные символы для поиска которые должны уйти на сервер
      $.getJSON( "list_posts.json",{
            term: extractLast( request.term )
          }, function(data){
        create_list_posts_serch(data);
      });
    },
    search: function() {
      // custom minLength
      var term = extractLast( this.value );
      if ( term.length < 2 ) {
        return false;
      }
    },
    focus: function() {
      // prevent value inserted on focus
      return false;
    },
    select: function( event, ui ) {
      //console.log(ui.item.value);
      //$(".participants_find input[type=text]").val('');
      return false;
    }
  });
}


function create_list_posts_serch(data,block_select){
  //console.log(data);
  var l=data.length;
  var str='';//data[i].id
  for (var i = 0; i < l; i++) {
    str+='<li id="'+data[i].id+'"><div class="list_posts_img"><img src="/'+data[i].img+'"></div><div class="list_posts_inf"><span>'+data[i].date+'</span>'+data[i].autor+' ('+data[i].rating+')<div class="list_posts_name"><a href="/">'+data[i].name+'</a></div></div><a class="add_is">&#43;</a></li>';
  }
  $('.list_posts_add ul').html(str);
  /*$(document).on('click','.list_user .add_is',function(e){
    var li=$(this).parents('li');
    li.addClass("participants_func");
    $(this).remove();
    $(block_select).find('ul').not('.participants_menu ul').append(li);
    var commands=Array('Сообщение','Организатор','Переписка','Удалить');
    participants_menu('.participants_func',commands);
  });*/
}