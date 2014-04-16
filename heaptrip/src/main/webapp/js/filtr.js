$(document).ready(function() {
	if($('.filtr').length){
		$('.filtr .zag').append('<span></span>');
		$('.filtr .zag span').bind('click',function(e){
			var slide=$(this).parents(".filtr");
			if($(slide).find(".content").is(":hidden")){
				$(slide).find(".content").slideDown();
        $(this).css('background','url("../images/show_filtr.png") no-repeat');
			}else{
				$(slide).find(".content").slideUp();
        $(this).css('background','url("../images/hide_filtr.png") no-repeat');
			}
		});		
	}
});
	
    