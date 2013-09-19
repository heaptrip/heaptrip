<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	
	  <script type="text/javascript">

    	$(function(){
        	$("#createNewComment").click(function(){
            	if(window.user)
                	alert('createNewComment');
            	else
            		$.alertNoAuthenticationUser();
        	});
    	});
  
    </script>
				

	<div class="comments_post">
		<jsp:include page="/WEB-INF/jsp/widget/comments_items.jsp"/>
	<div class="comment_new">

	<textarea noresize></textarea>
		<input id="createNewComment" type="button" value="<fmt:message key="page.action.write" />" class="button">
	</div>
	
</div>
   