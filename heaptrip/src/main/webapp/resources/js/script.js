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

	$('.comment_answer .answer').bind('click',function(e){
		$(this).css('display','none');
		$(this).parent().find('.comment_new').css('display','block');
	});

	$('.comment_new textarea').bind('focus',function(e){
		$(this).animate({ height:"56px"}, 500);
	});

	if($('.tabs').length){
		var tabs=$('.tabs');
		activ_tab(tabs);
		tabs.find('span').bind('click',function(e){
			if($(this).next().is(':hidden')){
				tabs.find('span.activ').next().css('display','none');
				tabs.find('span.activ').removeClass('activ');
				$(this).addClass('activ');
				activ_tab(tabs);
			}
		});
	}

	if($('div.albom .edit').length){
		edit_img_albom($('div.albom .edit'));
	}

});

function activ_tab(tabs){
	var span=tabs.find('span.activ');
	var div=span.next();
	div.css('display','block');
	var h_tab=div.height();
	if(h_tab==0){
		h_tab=350;
	}
	tabs.css('margin-bottom',h_tab);
}

function edit_img_albom(data){
	var list_block_img=data.find('li div.albom_img');
	$.each(list_block_img, function(index, value) {
		$(value).append('<span class="check_img check_disabled"><span>');
		$(value).find('span.check_img').bind('click',function(e){
			if($(this).hasClass('check_disabled')){
				$(this).addClass('check_enabled');
				$(this).removeClass('check_disabled');
			}else{
				$(this).addClass('check_disabled');
				$(this).removeClass('check_enabled');				
			}
		});
	});
}