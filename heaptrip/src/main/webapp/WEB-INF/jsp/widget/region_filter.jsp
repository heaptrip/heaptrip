<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">

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

   </script>
   

				<div id="region" class="filtr">
				<div class="zag"><fmt:message key="wgt.region.select" /></div>
				<div class="content">
					<div class="search">
						<input type="text" name="text_search">
						<input type="button" name="go_region_search" value="">
					</div>				
					<div class="tree"></div>
	    			<div class="for_button">
	    				<input type="button" class="button" value="<fmt:message key="page.action.save" />">
	    			</div>					
				</div>
    		</div>