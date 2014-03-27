<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>				

<c:choose>
	<c:when test="${empty isMotherCommentSet}">
		<ul class="comments_mother">
     </c:when>
     <c:otherwise>
	<ul>
    </c:otherwise>
</c:choose>

 <c:set var="isMotherCommentSet" value="true" scope="request"/>

	<c:forEach var="comment" items="${comments}">
	    <c:set var="comments" value="${comment.children}" scope="request"/>
	    <li id="${comment.id}">
 	    	<div class="comment_content">
				<div class="comment_info">
					<span class="connemt_name">${comment.author.name}</span><span
						class="connemt_date">${comment.created.text}</span>
				</div>
				<div class="comment_avatar">
					<img src="<c:url value="/rest/image/small/${comment.author.image.id}"/>">
				</div>
				<div class="comment_text">${comment.text}</div>
			</div>
	        <c:if test="${fn:length(comment.children) > 0}">
	            <jsp:include page="/WEB-INF/jsp/widget/comments_items.jsp"/>
	        </c:if>
	    </li>
	</c:forEach>
</ul>