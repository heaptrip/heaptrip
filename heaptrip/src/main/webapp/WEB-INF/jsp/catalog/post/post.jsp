<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="postId" value='${param.id}'/>

<c:url var="postEditUrl" value="post_modify_info.html">
    <c:param name='id' value="${postId}"/>
</c:url>

<div id="container">
    <div id="contents">

        <article id="article" class="deteil">
            <div class="date">${post.created.text}
                <span><fmt:message key="post.title"/></span>
                <c:if test="${post.status.value == 'PUBLISHED_FRIENDS'}">
                    <span class="for_frends"><fmt:message key="content.forFrends"/></span>
                </c:if>
            </div>
            <div class="inf">
                <div class="left">
                    <c:if test="${post.status.value != 'DRAFT'}">
                        <h2>${post.name}</h2>
                    </c:if>
                    <c:if test="${post.status.value == 'DRAFT'}">
                        <h2 class="chernovik"><fmt:message key="content.draft"/> ${post.name}</h2>
                    </c:if>

                    <div><fmt:message key="content.author"/>:<a
                            href="<c:url value="/pf-profile.html?guid=${post.owner.id}"/>"><span>${post.owner.name} (${post.owner.rating.value})</span></a>
                    </div>
                    <div><fmt:message key="content.category"/>:
                        <c:forEach items="${post.categories}" var="category">
                            <span>${category.data}</span>
                        </c:forEach>
                    </div>
                    <div><fmt:message key="content.region"/>:
                        <c:forEach items="${post.regions}" var="region">
                            <span>${region.data}</span>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <nav id="travel_nav">

            <c:if test='${principal.id eq post.owner.id }'>
                <input type="button" onClick="window.location = '${postEditUrl}'"
                       value="<fmt:message key="page.action.edit" />" class="button">
            </c:if>
            </nav>

            <div class="description">${post.description}</div>

            <div class="dop_inf">
                <div class="views">
                    <fmt:message key="content.views"/>
                    :<span>${post.views}</span>
                </div>
                <div class="comments">
                    <fmt:message key="content.comments"/>
                    :<span>${post.comments}</span>
                </div>
                <div class="wertung">
                    <fmt:message key="content.wertung"/>:
                    <c:choose>
                        <c:when test="${not post.rating.locked}">
                            <div contentType="POST" class="stars star${post.rating.stars} activ">
                                <input type="hidden" value="${post.rating.stars}">
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="stars star${post.rating.stars}"></div>
                        </c:otherwise>
                    </c:choose>
                    <span>(${post.rating.count})</span>
                </div>
                <a class="button"><fmt:message key="content.toFavorit"/></a>
            </div>
        </article>

        <tiles:insertDefinition name="comments"/>

    </div>
</div>

<!-- TODO -->
<aside id="sideRight">
    <div id="widget2" class="widget">
        <div class="zag">Похожие посты</div>
        <ul>
            <li>
                <div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
                <div class="name_post"><a href="/">Пост 0111</a><span class="comments">12</span></div>
            </li>
            <li>
                <div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
                <div class="name_post"><a href="/">Пост 0111</a><span class="comments">12</span></div>
            </li>
        </ul>
    </div>
    <div id="widget3" class="widget">
        <div class="zag">Обсуждаемые посты</div>
        <ul>
            <li>
                <div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
                <div class="name_post"><a href="/">Пост 0111</a><span class="comments">12</span></div>
            </li>
            <li>
                <div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
                <div class="name_post"><a href="/">Пост 0111</a><span class="comments">12</span></div>
            </li>
            <li>
                <div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
                <div class="name_post"><a href="/">Пост 0111</a><span class="comments">12</span></div>
            </li>
            <li>
                <div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
                <div class="name_post"><a href="/">Пост 0111</a><span class="comments">12</span></div>
            </li>
            <li>
                <div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
                <div class="name_post"><a href="/">Пост 0111</a><span class="comments">12</span></div>
            </li>
            <li>
                <div class="autor"><a href="/">Alexander Alexeev</a> (4,7)</div>
                <div class="name_post"><a href="/">Пост 0111</a><span class="comments">12</span></div>
            </li>
        </ul>
    </div>
</aside>
<!-- #sideRight -->