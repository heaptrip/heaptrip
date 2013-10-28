<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<script type="text/javascript">

	function getSelectedRegionsIds(){
		var idArr = [];
		$('#region .tree').each(function(){
	   		$(this).find('li').each(function(){
	        	var current = $(this);	        	
	        	if(current[0].className === 'jstree-leaf jstree-last' ||
	        	   current[0].className === 'jstree-last jstree-leaf' ||
	        	   current[0].className === 'jstree-leaf'){
	        		idArr.push(current[0].id);
	        	}	  
	    	});
		});
		return (idArr.length > 0 ? idArr : []);
	};
	
	function stringMarker(term,path){
		var newPath = '';
		var upperPath =  path.toUpperCase().split('');
		var pathArr =  path.split('');
		var upperTerm = term.toUpperCase();
		var tmpTerm = ''; 
		for(var i=0;i<upperPath.length;i++){
			tmpTerm = tmpTerm + upperPath[i];
			if(tmpTerm==upperTerm){
				newPath = newPath.substring(0,newPath.length - upperTerm.length) 
				+ '<span style="font-weight:bold">' 
				+ path.substring(i-upperTerm.length,i+1) 
				+ '</span>';
				tmpTerm = '';
			}else{
				newPath = newPath + pathArr[i];
				if(tmpTerm.length == upperTerm.length)
					tmpTerm = tmpTerm.substring(1,tmpTerm.length);
			}
		}
		return newPath;
	}
	
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
	
	$(window).bind("onPageReady", function(e, paramsJson) {
		



				var url = 'rest/get_region_hierarchy';
 
    			var callbackSuccess = function(dataArr) {

                    if(!dataArr) return;

                    for(var i=0;i< dataArr.length;i++){

                        var data = dataArr[i];

    			    	var arr = [];
    				    var country = data.country;
    				    var area = data.area;
    				    var city = data.city;
				
    				    if(country)	arr.push({id:country.id,data:country.data});
    				    if(area) arr.push({id:area.id,data:area.data});
    				    if(city) arr.push( {id:city.id,data:city.data});

    				    create_tree(arr);
                    }
        		};
    
    			var callbackError = function(error) {
        			alert(error);
    			};

                var requestParams = {"regionIds":paramsJson.rg};


                if ($.getParamFromURL().uid )
                    requestParams.uid = $.getParamFromURL().uid;

    			$.postJSON(url, requestParams, callbackSuccess, callbackError);


			
			$.handInitParamToURL({rg : paramsJson.rg});
			$("#region input[type=text]").val('');

	});
	
	
	$(document).ready(function() {
		
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
        						
        						var newPath =stringMarker(request.term,item.path);
								
        						if(newPath==item.path){
        							newPath =stringMarker(item.data,item.path);
        						}

                        		return {
                            		label: newPath,
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
  				
  				open: function(event, ui){
  			       $("ul.ui-autocomplete li a").each(function(){
  			        var htmlString = $(this).html().replace(/&lt;/g, '<');
  			        htmlString = htmlString.replace(/&gt;/g, '>');
  			        $(this).html(htmlString);
  			        });
  			     },
  				
				select: function( event, ui ) {
    				var regId = ui.item.value;
    				var regIds = getSelectedRegionsIds();
    				regIds.push(regId);
    				$.handParamToURL({rg : regIds.join()});
    				return false;
  				}
			});

		if($('#region .tree').length){
			$('#region .tree').jstree({
				'plugins' : [ 'themes', 'ui','add_del','crrm',"html_data" ]
			})
			.bind("delete_node.jstree", function() {		
				$.handParamToURL({rg : getSelectedRegionsIds().length > 0 ? getSelectedRegionsIds().join(): null});
    		});
		}

		$(".ui-autocomplete .ui-menu-item").unbind();
		$(".ui-autocomplete a.ui-corner-all").unbind();
		
		$(".ui-autocomplete a.ui-corner-all").click(function (e) {
			$("#region input[type=text]").val('');
			return false;
		});
	
	});

</script>

<div id="region" class="filtr">

    <c:choose>
        <c:when test="${not empty param.uid}">
            <div class="zag"><fmt:message key="content.region" /></div>
        </c:when>
        <c:otherwise>
            <div class="zag"><fmt:message key="wgt.region.select" /></div>
        </c:otherwise>
    </c:choose>

	<div class="content" style="display: block;">
		<div class="search">
			<input type="text" name="text_search"> <input type="button" name="go_region_search" value="">
		</div>
		<div class="tree"></div>
		<tiles:insertAttribute name="region_filter_btn" />
	</div>
</div>