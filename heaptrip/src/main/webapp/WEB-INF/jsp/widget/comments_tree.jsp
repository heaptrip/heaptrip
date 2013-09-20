<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	
	<script type="text/javascript">

		var createNewComment = function(commentBtn){

			$.doAuthenticationUserAction(function(){
				
				var text = $(commentBtn).parent().children('textarea')[0].value;
				
				if(text.length == 0){
					return;
				}
				
				var parent = $(commentBtn).parent().parent()[0].id;
				var target = $.getParamFromURL().id;
				
				var jsonData = {
					target:target,
					parent:parent ? parent : null,
					text:text,
					targetClass:'TRIP'
					// TODO: вычислить тип скорее всего из url
				};
				
			
			var url = 'rest/security/comment_save';

			var callbackSuccess = function(data) {
				//alert(data);
				// TODO:
				window.location = window.location.href;
				
			};

			var callbackError = function(error) {
				//$("#error_message #msg").text(error);
				alert(error);
			};

			$.postJSON(url, jsonData, callbackSuccess, callbackError);
			
			});
		}; 
	

    </script>
				

	<div class="comments_post">
		<jsp:include page="/WEB-INF/jsp/widget/comments_items.jsp"/>
		
		<div class="comment_new">
			<textarea id="newComment" noresize></textarea>
			<input type="button" onClick="createNewComment(this)" value="<fmt:message key="page.action.write" />" class="button">
		</div>
	
</div>
   