<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">

	$(function(){
    	$("#regionFilterSave").click(function(){
        	if(window.user)
            	alert('Region filter save for ' + window.user.name + ' clicked!');
        	else
            	alert( 'You mast authorize for save!');
    	});
	});

</script>


		<div class="for_button">
			<input id="regionFilterSave" type="button" class="button" value="<fmt:message key="page.action.save" />">
		</div>
	