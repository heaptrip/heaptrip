<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

    <script type="text/javascript">

    
    	var getSelectedCategories = function (){
    		var checked_ids = [];
			$("#category .tree").jstree("get_checked", null, true)
				.each(function () {
    				if(this.parentElement.parentElement.className.indexOf('jstree-checked') == -1)
    	            	checked_ids.push(this.id);
			});
			return checked_ids;
    	};
    
    	var selectCategories = function(categoryIdArr){
     		if(!$("#category .tree").jstree.isLoad)
    			return;	
    		$('#category .tree').jstree("uncheck_all"); 
    		$.each(categoryIdArr , function(index, val){
				$('#category .tree').jstree("check_node", "#" + val.replace(/\./g, "\\."));
				var checked_ids = getSelectedCategories();
        
        		if(checked_ids.length > 0)
        		$.handInitParamToURL({ct : checked_ids.join()});
			});
    	};
    
    	$(window).bind("onPageReady", function(e, paramsJson) {
    		if(paramsJson.ct){
    			selectCategories(paramsJson.ct.split(','));
			}
    	});
    
    	$(document).ready(function() {

        		var url = 'rest/categories';

        		var callbackSuccess = function(data) {
        			
                	$('#category .tree').jstree({
                    	json_data : {
                        data : data.categories
                    	},
                    "plugins" : [ "themes", "json_data", "checkbox" ]
                   
                	}).bind("loaded.jstree", function() {		
                    		$("#category .tree").jstree.isLoad = true;
                    		var paramsJson = $.getParamFromURL();
                    		if(paramsJson.ct){
                    			selectCategories(paramsJson.ct.split(','));
                    		}else{
                    			selectCategories(data.userCategories);
                    		}
                	})
                	.bind("change_state.jstree", function(node, uncheck) {
                		if($("#categoryFilterSubmit").length == 0){
                			var checked_ids = getSelectedCategories();
                    		$.handInitParamToURL({ct : checked_ids.join()});
                    	}
                	});              	
            	};
        
        	var callbackError = function(error) {
            	alert(error);
        	};

            var uid =  $.getParamFromURL().uid ? $.getParamFromURL().uid : null;

        	$.postJSON(url, uid , callbackSuccess, callbackError);
    	});
  
  
    </script>

    <div id="category" class="filtr">
        <div class="zag"><fmt:message key="wgt.category.select" /></div>
        <div class="content" style="display: block;">
        <div class="tree"></div>
            <tiles:insertAttribute name="category_tree_btn" />
        </div>
    </div>