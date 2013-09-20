<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

    <script type="text/javascript">

    	$(function(){
        	$("#categoryFilterSubmit").click(function(){
            	var checked_ids = getSelectedCategories();
            	$.handParamToURL({ct : checked_ids.join()});
        	});
    	});
   
    	$(function(){
        	$("#categoryFilterSave").click(function(){
        		$.doAuthenticationUserAction(function(){
        			alert('Category filter save for ' + window.user.name + ' clicked!');
            	});
        	});
    	});
  
    </script>


            <div class="for_button">
                <input type="button" id="categoryFilterSubmit" class="button" value="<fmt:message key="page.action.search" />">
                <input type="button" id="categoryFilterSave" class="button" value="<fmt:message key="page.action.save" />">               
            </div>
       