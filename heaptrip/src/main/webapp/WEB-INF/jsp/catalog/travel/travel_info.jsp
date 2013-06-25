<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

	
										
					<div class="description"><img src="1.jpg" width="300" align="left">
					
					${trip.description}
					
					</div>
					<div class="table_inf">
						<table>
							<thead><tr>
								<th>Статус</th>
								<th>Период проведения</th>
								<th>Стоимость участия</th>
								<th>min / max</th>
								<th>Записалось участников</th>
								<th>Действие</th>
							</tr></thead>
							<tbody>
								<tr>
									<td>Планируется</td>
									<td>с 10.10.10<br/>по 10.10.10</td>
									<td>10,000 р.</td>
									<td>15 / 50</td>
									<td>12</td>
									<td><a class="button">Отказаться</a></td>
								</tr>
								<tr>
									<td>Планируется</td>
									<td>с 10.10.10<br/>по 10.10.10</td>
									<td>10,000 р.</td>
									<td>15 / 50</td>
									<td>12</td>
									<td><a class="button">Принять</a><a class="button">Отклонить</a></td>
								</tr>
								<tr>
									<td>Планируется</td>
									<td>с 10.10.10<br/>по 10.10.10</td>
									<td>10,000 р.</td>
									<td>15 / 50</td>
									<td>12</td>
									<td><a class="button">Заявка</a></td>
								</tr>
								<tr>
									<td class="warning">Отменено</td>
									<td>с 10.10.10<br/>по 10.10.10</td>
									<td>10,000 р.</td>
									<td>15 / 50</td>
									<td>12</td>
									<td></td>
								</tr>
								<tr>
									<td>Начато</td>
									<td>с 10.10.10<br/>по 10.10.10</td>
									<td>10,000 р.</td>
									<td>15 / 50</td>
									<td>12</td>
									<td></td>
								</tr>
								<tr>
									<td class="warning">Прервано</td>
									<td>с 10.10.10<br/>по 10.10.10</td>
									<td>10,000 р.</td>
									<td>15 / 50</td>
									<td>12</td>
									<td></td>
								</tr>
								<tr>
									<td>Завершено</td>
									<td>с 10.10.10<br/>по 10.10.10</td>
									<td>10,000 р.</td>
									<td>15 / 50</td>
									<td>12</td>
									<td><a class="button">Фото</a></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="dop_inf">
						<div class="views">Просмотров:<span>234</span></div>
						<div class="comments">Коментариев:<span>24</span></div>
						<div class="wertung">Рейтинг:<div class="stars star0 activ"><input type="hidden" value="0"></div><span>(196)</span></div>
						<a class="button">В избранное</a>
					</div>
				</article>
				<div class="comments_post">
					<ul class="comments_mother">
						<!--/////////////////////////////////////////////-->

						<li>
							<div class="comment_content">
								<div class="comment_info"><span class="connemt_name">Alexey Alexeev</span><span class="connemt_date">12.04.13</span></div>
								<div class="comment_avatar"><img src="/avatar/user1.jpg"></div>
								<div class="comment_text">Митя! Сделай милость, попроси как-нибудь помягче свою жену, чтобы она, сидя с нами в ложе, потише восхищалась платьями Сары Бернар. В прошлый спектакль она до того громко шептала, что я не слышал, о чём говорилось на сцене. Попроси её, но помягче. Премного обяжешь.</div>
							</div>
							<ul>
								<li>
									<div class="comment_content">
										<div class="comment_info"><span class="connemt_name">Anna Anitova</span><span class="connemt_date">12.04.13</span></div>
										<div class="comment_avatar"><img src="/avatar/user2.jpg"></div>
										<div class="comment_text">Митя! Сделай милость, попроси как-нибудь помягче свою жену, чтобы она, сидя с нами в ложе, потише восхищалась 	платьями Сары Бернар. В прошлый спектакль она до того громко шептала, что я не слышал, о чём говорилось на сцене. Попроси её, но помягче. 	Премного обяжешь.</div>
									</div>
									<ul>
										<li>
											<div class="comment_content">
												<div class="comment_info"><span class="connemt_name">Alexey Alexeev</span><span class="connemt_date">12.04.13</span></div>
												<div class="comment_avatar"><img src="/avatar/user1.jpg"></div>
												<div class="comment_text">Митя! Сделай милость, попроси как-нибудь помягче свою жену, чтобы она, сидя с нами в ложе, потише 		восхищалась 	платьями Сары Бернар. В прошлый спектакль она до того громко шептала, что я не слышал, о чём говорилось на сцене. 		Попроси её, но помягче. 	Премного обяжешь. Митя! Сделай милость, попроси как-нибудь помягче свою жену, чтобы она, сидя с нами в ложе, потише 		восхищалась 	платьями Сары Бернар. В прошлый спектакль она до того громко шептала, что я не слышал, о чём говорилось на сцене. 		Попроси её, но помягче. 	Премного обяжешь.</div>
											</div>												
										</li>
									</ul>
								</li>
							</ul>
						</li>
						<li>
							<div class="comment_content">
								<div class="comment_info"><span class="connemt_name">Anna Anitova</span><span class="connemt_date">12.04.13</span></div>
								<div class="comment_avatar"><img src="/avatar/user2.jpg"></div>
								<div class="comment_text">Митя! Сделай милость, попроси как-нибудь помягче свою жену, чтобы она, сидя с нами в ложе, потише восхищалась платьями Сары Бернар. </div>
							</div>
						</li>
						<li>
							<div class="comment_content">
								<div class="comment_info"><span class="connemt_name">Anna Anitova</span><span class="connemt_date">12.04.13</span></div>
								<div class="comment_avatar"><img src="/avatar/user2.jpg"></div>
								<div class="comment_text">Митя! Сделай милость, попроси как-нибудь помягче свою жену, чтобы она, сидя с нами в ложе, потише восхищалась платьями Сары Бернар. В прошлый спектакль она до того громко шептала, что я не слышал, о чём говорилось на сцене. Попроси её, но помягче. Премного обяжешь.</div>
							</div>	
						</li>

						<!--/////////////////////////////////////////////-->
					</ul>
					<div class="comment_new">
						<textarea noresize></textarea>
						<input type="button" value="Написать" class="button">
					</div>
					
					
					
				</div>

