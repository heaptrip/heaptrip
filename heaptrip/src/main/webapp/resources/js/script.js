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

	$('.comment_content').hover(
		function(e){
			$(this).css('border-color','#DEE5EB');
		},
		function(e){
			$(this).css('border-color','#FFFFFF');	
		}
	);

	$('.comment_content').bind('click',function(e){
		$(this).unbind('click');
		$(this).parent().append('<div class="comment_new"><textarea noresize></textarea><input type="button" value="Написать" class="button"></div>');
	});

	$(document).on('focus','.comment_new textarea',function(e){
		$(this).animate({ height:"56px"}, 500);
	});

	if($('.tabs').length){
		setTimeout(create_tabs,200);
	}

	if($('div.albom .edit').length){
		edit_img_albom($('div.albom .edit'));
	}

});

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

function edit_img_albom(data){
	var list_block_img=data.find('li a.my');
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
			return false;
		});
	});
}