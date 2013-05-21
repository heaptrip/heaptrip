$(document).ready(function() {
	if($('.filtr').length){
		$('.filtr .zag').append('<span></span>');
		$('.filtr .zag span').bind('click',function(e){
			var slide=$(this).parents(".filtr");
			if($(slide).find(".content").is(":hidden")){
				$(slide).find(".content").slideDown();
        $(this).css('background','url("./resources/images/show_filtr.png") no-repeat');
			}else{
				$(slide).find(".content").slideUp();
        $(this).css('background','url("./resources/images/hide_filtr.png") no-repeat');
			}
		});		
	}
	
	/*if($('#category .tree').length){
		$('#category .tree').jstree({ 
			'plugins' : [ 'themes', 'html_data', 'checkbox' ]
		});
	}*/
	
    function split( val ) {
      return val.split( /,\s*/ );
    }
    function extractLast( term ) {
      return split( term ).pop();
    }
 
    $( "#region input[type=text]" )
      // don't navigate away from the field on tab when selecting an item
      .bind( "keydown", function( event ) {
        if ( event.keyCode === $.ui.keyCode.TAB &&
            $( this ).data( "ui-autocomplete" ).menu.active ) {
          event.preventDefault();
        }
      })
      .autocomplete({
        source: function( request, response ) {
          $.getJSON( "list.json", {
            term: extractLast( request.term )
          }, response );
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
          $("#region form input[type=text]").val('');
          var m=Array('wwww','rrrr','tttt');
          var n=Array('wwww','mmmm','dddd');
          var t=Array('wwww','mmmm','ssss');
          create_tree(m);
          create_tree(n);
          create_tree(t);
          return false;
        }
      });



	if($('#region .tree').length){
		$('#region .tree').jstree({
			'plugins' : [ 'themes', 'ui','add_del','crrm',"html_data" ]
		});

    
    $(".ui-autocomplete .ui-menu-item").unbind();
    $(".ui-autocomplete a.ui-corner-all").unbind();
		$(".ui-autocomplete a.ui-corner-all").click(function (e) {
      //alert($(this).html());
			$("#region .search input[type=text]").val('');
      return false;
		});


	}

});

function fnShowProps(obj, objName){
    var result = "";
    for (var i in obj) // обращение к свойствам объекта по индексу
        result += objName + "." + i + " = " + obj[i] + "<br />\n";
    console.log(result);
}

function create_tree(n){
  var i=0;
  while(n[i]){
    if(!$("#region .tree #"+n[i]).length){
      $("#region .tree").jstree("create", "#"+n[i-1], "last", {attr:{id:n[i]},data: n[i]},false,true);  
    }
  i++;
  }
  return false;
}