$(document).ready(function() {
	if($('#account').length){
		$('#account_name').bind('click',function(){
			if($('#account ul').is(':hidden')){
				$('#account ul').slideDown(100);
			} else {
    			$('#account ul').slideUp(100);
  			}
		});
	}
	if($('#language').length){
		$('#language_now').bind('click',function(){
			if($('#language ul').is(':hidden')){
				$('#language ul').slideDown(100);
			} else {
    			$('#language ul').slideUp(100);
  			}
		});
	}
	$(document).click(function(e) {
		if ($(e.target).closest("#account").length) return;
    	if ($(e.target).closest("#language").length) return;
    	$('#account ul').slideUp(100);
    	$('#language ul').slideUp(100);
    	e.stopPropagation();
  	});

	// кастомизация чекбокса
	var checkbox=$('input[type=checkbox]');
	$.each(checkbox, function(index, value) {      
		$(value).css('opacity','0');
		$(value).css({
			'position':'relative',
			'margin-left': '-'+$(value).css('width')
		});
		$(value).before('<span class="chek check_false" style="width: '+$(value).css('width')+';height: '+$(value).css('height')+';"></span>');
		$(value).click(function(e){
			if(!$(this).is(':checked')){
				$(this).prev(".chek").addClass('check_false');
				$(this).prev(".chek").removeClass('check_true');
			}else{
				$(this).prev(".chek").addClass('check_true');
				$(this).prev(".chek").removeClass('check_false');
			}
		})
	});

	// .comment_content:hover
	$('.comment_content').hover(
		function(e){
			$(this).css('border-color','#DEE5EB');
		},
		function(e){
			$(this).css('border-color','#FFFFFF');	
		}
	);

	// добавляет поле для комментирования
	$('.comment_content').bind('click',function(e){
		//$(this).unbind('click');
		$('.comments_mother .comment_new').remove();
		$(this).parent().append('<div class="comment_new"><textarea noresize></textarea><input type="button" value="Ответить" class="button"></div>');
	});

	// раздвижное текстовое поле по клику
	$(document).on('focus','.comment_new textarea',function(e){
		$(this).animate({ height:"56px"}, 500);
	});

	if($('.tabs').length){
		setTimeout(create_tabs,200);
	}

  	// звездочки рейтинга
	if($(".stars" ).length){
      	if($(".stars" ).hasClass("activ")){
   			var delta=0;
   			var value=0;
   			$(".stars").bind('mousemove',function(e){
   				delta=e.pageX-$(this).offset().left*1;
   				value=Math.ceil(delta/11);
   				$(this).removeClass();
   				$(this).addClass("stars star"+value+" activ");
   			});
 			$(document).on('mouseout','.stars',function(e){
 				value=$(this).find('input').val();
 				$(this).removeClass();
   				$(this).addClass("stars star"+value+" activ");
   			});
      		$(".stars" ).bind('click',function(e){
      			var delta=0;
      			var value=0;
      			delta=e.pageX-$(this).offset().left*1;
      			value=Math.ceil(delta/11);
      			console.log(value);
      			$(this).removeClass();
      			$(this).addClass("stars star"+value+" activ");
      			$(this).find('input').val(value);
      		});
      	}
  	}

  	if($('.full_img').length){
  		$('.full_img').css('cursor','pointer');
  		$('.full_img').click(function(e){
  			view_img(this);	
  		})
  	}

  	if($('.participants_func').length){
  		var commands=Array('Сообщение','Организатор','Переписка','Удалить');
  		participants_menu('.participants_func',commands);
  	}

  	if($('.participants_is').length){
  		var commands=Array('Принять','Отказать');
  		participants_menu('.participants_is',commands);
  	}

  	if($('.add_lang').length){
  		
  	}

});

    function split( val ) {
      return val.split( /,\s*/ );
    }
    function extractLast( term ) {
      return split( term ).pop();
    }


// анимация контекстного меню в участниках
function participants_menu(name,commands){
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
  			$(this).css("background","url('./resources/images/participants_func_a.jpg') right top no-repeat");
  		}else{
  			menu.css('display','none');
  			$(this).css("background","url('./resources/images/participants_func.jpg') right top no-repeat");
  		}
  	});
}

// создание закладок
function create_tabs(){
	var tabs=$('.tabs');
	var tabs_int=$('.tabs_interactiv');
	activ_tab(tabs);
	tabs_int.find('span').bind('click',function(e){
		if($(this).next().is(':hidden')){
			tabs.find('span.activ').next().css('display','none');
			tabs.find('span.activ').removeClass('activ');
			$(this).addClass('activ');
			activ_tab(tabs);
		}
	});
}

// активная закладка
function activ_tab(tabs){
	var span=tabs.find('span.activ');
	var div=span.next();
	div.css('display','block');
	var h_tab=div.height();
	console.log(h_tab);
	if(h_tab==0){
		h_tab=350;
	}
	tabs.css('margin-bottom',h_tab);
}

// чекбоксы на изображениях
function edit_img_albom(data){
	var list_block_img=data.find('.my');
	$.each(list_block_img, function(index, value) {
		$(value).append('<span class="check_img check_disabled"></span>');
		$(value).find('span.check_img').bind('click',function(e){
			if($(this).hasClass('check_disabled')){
				$(this).addClass('check_enabled');
				$(this).removeClass('check_disabled');
			}else{
				$(this).addClass('check_disabled');
				$(this).removeClass('check_enabled');				
			}
			return false;
		});
	});
}

function view_img(element,w_){
	var src=$(element).parent().find('img').attr('src');
	var new_src=src.replace(/\.jpg/g,'_full\.jpg');
	var img_new = new Image();
	img_new.onload = function() {
		var x=width=$(window).width()/2;
    	var y=height=$(window).height()/2+$(document).scrollTop();
		var w=this.width;
	    var h=this.height;
		var w_=$(window).width();
		var h_=$(window).height();
		if(w>=w_){
			var k=h/w;
			w=w_-40;
			h=w*k;
		}
		if(h>=h_){
			var k=w/h;
			h=h_-40;
			w=h*k;
		}
		if($('.show_img').length){
			$('.show_img').remove();
		}
		$('body').append('<div class="show_img"><img src="'+new_src+'" /></div>');
		$('.show_img img').css({
			'width': '1px',
			'height': '1px',
			'position' : 'absolute',
			'left' : x+'px',
			'top' : y+'px',
			'border': '2px solid #555555',
			'-webkit-border-radius': '7px',
			'border-radius': '7px',
			'behavior': 'url("/PIE/PIE.htc")',
			'z-index':'999999',
			'cursor':'pointer'
		});
		$('.show_img img').click(function(){
			$('.show_img').remove();
		});
		$('.show_img img').animate({
			'width': w+'px',
    		'height': h+'px',
			'left': (x*1-w/2)*1+'px',
    		'top': (y*1-h/2)*1+'px',
    	},200 );
	}
	img_new.src = new_src;
}