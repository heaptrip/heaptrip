<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

    <script type="text/javascript">

    	$(window).bind("onPageReady", function(e, paramsJson) {

    		 if (!$("#category .tree").jstree.isLoad) {
    	
        		var url = 'rest/categories';

        		var callbackSuccess = function(data) {
        			
                	$('#category .tree').jstree({
                    	json_data : {
                        data : data
                    	},
                    "plugins" : [ "themes", "json_data", "checkbox" ]
                   
                	}).bind("loaded.jstree", function() {
                   
                    	var checked_ids = [];
                    	$("#category .tree").jstree("get_checked", null, true).each(function () {
                        	checked_ids.push(this.id);
                    	});
                    	if(checked_ids.length > 0)
                    		$.handInitParamToURL({ct : checked_ids.join()});
                    	$("#category .tree").jstree.isLoad = true;
                   
                	});
                	/*.bind("change_state.jstree", function(node, uncheck) {
                    	var checked_ids = [];
                    	$("#category .tree").jstree("get_checked", null, true).each(function () {
                            checked_ids.push(this.id);
                        });
                    	$.handInitParamToURL({ct : checked_ids.join()});
                	});*/
            	};
        

        	var callbackError = function(error) {
            	alert(error);
        	};

        	$.postJSON(url, {categoryIds : paramsJson.ct ? paramsJson.ct.split(',') : null}, callbackSuccess, callbackError);
       
    	 	}
      
    	});
   
    	$(function(){
        	$("#categoryFilterSubmit").click(function(){
            	var checked_ids = [];
            	$("#category .tree").jstree("get_checked", null, true).each(function () {
                    checked_ids.push(this.id);
                });
            	//if(checked_ids.length > 0)
            		$.handParamToURL({ct : checked_ids.join()});
        	});
    	});
   
    	$(function(){
        	$("#categoryFilterSave").click(function(){
            	if(window.user)
                	alert('Category filter save for ' + window.user.name + ' clicked!');
            	else
                	alert( 'You mast authorize for save!');
        	});
    	});
   
    </script>

    <div id="category" class="filtr">
        <div class="zag"><fmt:message key="wgt.category.select" /></div>
        <div class="content">
        <div class="tree"></div>
            <div class="for_button">
                <input type="button" id="categoryFilterSubmit" class="button" value="<fmt:message key="page.action.search" />">
                <input type="button" id="categoryFilterSave" class="button" value="<fmt:message key="page.action.save" />">               
            </div>
        </div>
    </div>