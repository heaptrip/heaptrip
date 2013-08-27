<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">

	function getAllRegionsIds(){
		var idArr = [];
		$('#region .tree').each(function(){
	   		$(this).find('li').each(function(){
	        	var current = $(this);	        	
	        	if(current[0].className === 'jstree-leaf jstree-last' ||
	        			current[0].className === 'jstree-last jstree-leaf'	)
	        		idArr.push(current[0].id);
	    	});
		});
		return (idArr.length > 0 ? idArr : null);
	};

	function create_tree(n){
		var i=0;
		while(n[i]){
			if(!$("#region .tree #"+n[i].id).length){
				$("#region .tree").jstree("create", "#"+(n[i-1] ? n[i-1].id:n[i].id), "last", {attr:{id:n[i].id},data: n[i].data},false,true)
				.delete_node;
			}
			i++;
		}
		return false;
	}
	
	$(document).ready(function() {
		
		// TODO: УБРАТЬ
		$.handInitParamToURL({rg:null})
	
		$("#region input[type=text]")
			.bind("keydown", function( event ) {
  				if ( event.keyCode === $.ui.keyCode.TAB && $( this ).data( "ui-autocomplete" ).menu.active ) {
    				event.preventDefault();
  				}
			})
			.autocomplete({
				source: function( request, response ) {
					
        			var url = 'rest/search_regions';
        			
        			var callbackSuccess = function(data) {
        				response(
        					$.map( data, function( item ) {
                        		return {
                            		label:  item.data + " " + "(" + item.path + ")",
                            		value: item.id
                        		};
                    		}));
            		};
        
        			var callbackError = function(error) {
            			alert(error);
        			};

        			$.postJSON(url, request.term, callbackSuccess, callbackError);	
				
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
    		
    				var regId = ui.item.value;
    				
				var url = 'rest/get_region_hierarchy';
        			
        			var callbackSuccess = function(data) {
        				
        				var arr = [];
        				
        				var country = data.country;
        				var area = data.area;
        				var city = data.city;
   				
        				if(country)	arr.push({id:country.id,data:country.data});
        				if(area) arr.push({id:area.id,data:area.data});
        				if(city) arr.push( {id:city.id,data:city.data});
   
        				create_tree(arr);
        				
        				$.handParamToURL({rg : getAllRegionsIds().join()});
            		};
        
        			var callbackError = function(error) {
            			alert(error);
        			};

        			$.postJSON(url, regId, callbackSuccess, callbackError);	
			
    				$("#region form input[type=text]").val('');

    				return false;
  				}
			});

		if($('#region .tree').length){
			$('#region .tree').jstree({
				'plugins' : [ 'themes', 'ui','add_del','crrm',"html_data" ]
			})
			.bind("delete_node.jstree", function() {		
				$.handParamToURL({rg : getAllRegionsIds() ? getAllRegionsIds().join(): null});
    		});
		}

		$(".ui-autocomplete .ui-menu-item").unbind();
		$(".ui-autocomplete a.ui-corner-all").unbind();
		
		$(".ui-autocomplete a.ui-corner-all").click(function (e) {
			$("#region .search input[type=text]").val('');
			return false;
		});
	
	});

</script>

<div id="region" class="filtr">
	<div class="zag">
		<fmt:message key="wgt.region.select" />
	</div>
	<div class="content">
		<div class="search">
			<input type="text" name="text_search"> <input type="button" name="go_region_search" value="">
		</div>
		<div class="tree"></div>
		<div class="for_button">
			<input type="button" class="button" value="<fmt:message key="page.action.save" />">
		</div>
	</div>
</div>