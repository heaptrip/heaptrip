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

});