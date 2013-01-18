<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>
	<fmt:message key="entity.posts" />
</h1>

<a style="font-size: 16pt" href="<c:url value="/post/edit.html"/>">Add</a>

<c:if test="${not empty posts}">
	<table>
		<tr>
			<th>No</th>
			<th>Name/Date</th>
			<th></th>
			<th></th>
		</tr>
		<%
			int i = 0;
		%>
		<c:forEach items="${posts}" var="post">
			<tr>
				<td><%=++i%>.</td>
				<td><a href="<c:url value="/post.html?id=${post.id}"/>">${post.name} <fmt:formatDate
							value="${post.dateCreate}" pattern="MM.dd.yyyy HH:mm" /></a></td>
				<td><a href="<c:url value="/post/edit.html?id=${post.id}"/>">edit</a></td>
				<td><a href="<c:url value="/post/remove.html?id=${post.id}"/>">remove</a></td>
			</tr>
		</c:forEach>
	</table>
</c:if>