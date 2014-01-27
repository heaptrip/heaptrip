<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<footer id="footer">

	<ul>
		<li>
			<ul>
				<li><a href="<c:url value="/travels.html"/>" class="zag"><fmt:message key="trip.list.title" /></a></li>
				<li><a href="/"><fmt:message key="trip.rank.upcoming" /></a></li>
				<li><a href="/"><fmt:message key="trip.rank.current" /></a></li>
				<li><a href="/"><fmt:message key="trip.rank.past" /></a></li>
			</ul>
		</li>
		<li>
			<ul>
				<li><a href="<c:url value="/posts.html"/>" class="zag"><fmt:message key="post.list.title" /></a></li>
				<li><a href="/"><fmt:message key="post.rank.last" /></a></li>
				<li><a href="/"><fmt:message key="post.rank.best" /></a></li>
			</ul>
		</li>
		<li>
			<ul>
				<li><a href="/" class="zag"><fmt:message key="question.list.title" /></a></li>
				<li><a href="/"><fmt:message key="question.rank.last" /></a></li>
				<li><a href="/"><fmt:message key="question.rank.closed" /></a></li>
				<li><a href="/"><fmt:message key="question.rank.noanswer" /></a></li>
			</ul>
		</li>
		<li>
			<ul>
				<li><a href="/" class="zag"><fmt:message key="event.list.title" /></a></li>
				<li><a href="/"><fmt:message key="event.rank.upcoming" /></a></li>
				<li><a href="/"><fmt:message key="event.rank.current" /></a></li>
				<li><a href="/"><fmt:message key="event.rank.past" /></a></li>
			</ul>
		</li>
		<li>
			<ul>
				<li><a href="/" class="zag"><fmt:message key="info.title" /></a></li>
				<li><a href="/"><fmt:message key="info.about" /></a></li>
				<li><a href="/"><fmt:message key="info.regulations" /></a></li>
				<li><a href="/"><fmt:message key="info.agreement" /></a></li>
				<li><a href="/"><fmt:message key="info.statistics" /></a></li>
			</ul>
		</li>
		<li>
			<ul>
				<li><a href="/" class="zag"><fmt:message key="service.title" /></a></li>
				<li><a href="/"><fmt:message key="service.promotion" /></a></li>
				<li><a href="/"><fmt:message key="service.forclubs" /></a></li>
				<li><a href="/"><fmt:message key="service.forcompanies" /></a></li>
				<li><a href="/"><fmt:message key="service.fortravelagents" /></a></li>
			</ul>
		</li>
	</ul>

</footer>
