<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div class="description">
	${trip.description}
</div>
<div class="table_inf">
	<table>
		<thead>
			<tr>
				<th><fmt:message key="trip.status" /></th>
				<th><fmt:message key="trip.period" /></th>
				<th><fmt:message key="trip.cost" /></th>
				<th><fmt:message key="trip.minmax" /></th>
				<th><fmt:message key="trip.enrolled" /></th>
				<th><fmt:message key="trip.action" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${trip.schedule}" var="scheduleItem">
				<tr>
				<td><fmt:message key="trip.status.${scheduleItem.status}" /></td>
				<td><fmt:message key="page.date.from" /> ${scheduleItem.begin.text}<br /><fmt:message key="page.date.to" /> ${scheduleItem.end.text}</td>
				<td>${scheduleItem.price.value} ${scheduleItem.price.value}</td>
				<td>${scheduleItem.min} / ${scheduleItem.max}</td>
				<td>${scheduleItem.members}</td>
				<td><a class="button">todo</a></td>
			</tr>
			</c:forEach>
			
		</tbody>
	</table>
</div>
<div class="dop_inf">
	<div class="views">
		<fmt:message key="content.views" />
		:<span>234</span>
	</div>
	<div class="comments">
		<fmt:message key="content.comments" />
		:<span>24</span>
	</div>
	<div class="wertung">
		<fmt:message key="content.wertung" />
		:
		<div class="stars star0 activ">
			<input type="hidden" value="0">
		</div>
		<span>(196)</span>
	</div><a class="button"><fmt:message key="content.toFavorit" /></a></div>
</article>
<div class="comments_post">
	<ul class="comments_mother">
		<li>
			<div class="comment_content">
				<div class="comment_info">
					<span class="connemt_name">Alexey Alexeev</span><span
						class="connemt_date">12.04.13</span>
				</div>
				<div class="comment_avatar">
					<img src="/avatar/user1.jpg">
				</div>
				<div class="comment_text">Митя! Сделай
					милость, попроси как-нибудь помягче
					свою жену, чтобы она, сидя с нами в
					ложе, потише восхищалась платьями
					Сары Бернар. В прошлый спектакль она
					до того громко шептала, что я не
					слышал, о чём говорилось на сцене.
					Попроси её, но помягче. Премного
					обяжешь.</div>
			</div>
			<ul>
				<li>
					<div class="comment_content">
						<div class="comment_info">
							<span class="connemt_name">Anna Anitova</span><span
								class="connemt_date">12.04.13</span>
						</div>
						<div class="comment_avatar">
							<img src="/avatar/user2.jpg">
						</div>
						<div class="comment_text">Митя! Сделай
							милость, попроси как-нибудь помягче
							свою жену, чтобы она, сидя с нами в
							ложе, потише восхищалась платьями
							Сары Бернар. В прошлый спектакль
							она до того громко шептала, что я не
							слышал, о чём говорилось на сцене.
							Попроси её, но помягче. Премного
							обяжешь.</div>
					</div>
					<ul>
						<li>
							<div class="comment_content">
								<div class="comment_info">
									<span class="connemt_name">Alexey Alexeev</span><span
										class="connemt_date">12.04.13</span>
								</div>
								<div class="comment_avatar">
									<img src="/avatar/user1.jpg">
								</div>
								<div class="comment_text">Митя! Сделай
									милость, попроси как-нибудь
									помягче свою жену, чтобы она, сидя
									с нами в ложе, потише восхищалась
									платьями Сары Бернар. В прошлый
									спектакль она до того громко
									шептала, что я не слышал, о чём
									говорилось на сцене. Попроси её, но
									помягче. Премного обяжешь. Митя!
									Сделай милость, попроси как-нибудь
									помягче свою жену, чтобы она, сидя
									с нами в ложе, потише восхищалась
									платьями Сары Бернар. В прошлый
									спектакль она до того громко
									шептала, что я не слышал, о чём
									говорилось на сцене. Попроси её, но
									помягче. Премного обяжешь.</div>
							</div>
						</li>
					</ul>
				</li>
			</ul>
		</li>
		<li>
			<div class="comment_content">
				<div class="comment_info">
					<span class="connemt_name">Anna Anitova</span><span
						class="connemt_date">12.04.13</span>
				</div>
				<div class="comment_avatar">
					<img src="/avatar/user2.jpg">
				</div>
				<div class="comment_text">Митя! Сделай
					милость, попроси как-нибудь помягче
					свою жену, чтобы она, сидя с нами в
					ложе, потише восхищалась платьями
					Сары Бернар.</div>
			</div>
		</li>
		<li>
			<div class="comment_content">
				<div class="comment_info">
					<span class="connemt_name">Anna Anitova</span><span
						class="connemt_date">12.04.13</span>
				</div>
				<div class="comment_avatar">
					<img src="/avatar/user2.jpg">
				</div>
				<div class="comment_text">Митя! Сделай
					милость, попроси как-нибудь помягче
					свою жену, чтобы она, сидя с нами в
					ложе, потише восхищалась платьями
					Сары Бернар. В прошлый спектакль она
					до того громко шептала, что я не
					слышал, о чём говорилось на сцене.
					Попроси её, но помягче. Премного
					обяжешь.</div>
			</div>
		</li>

		<!--/////////////////////////////////////////////-->
	</ul>
	<div class="comment_new">
		<textarea noresize></textarea>
		<input type="button" value="<fmt:message key="page.action.write" />"
			class="button">
	</div>



</div>

