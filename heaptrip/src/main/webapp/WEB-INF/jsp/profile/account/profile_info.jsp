<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<div id="container">
    <div id="contents">

        <article id="article" class="deteil">
            <div class="inf">
                <div class="left">
                    <h2 class="people_title"><fmt:message key="accountProfile.title"/></h2>
                </div>

                <c:if test='${not empty principal && empty catcher}'>

                    <div class="right">
                        <a href="<c:url value="/profile_modify_info.html?guid=${param.guid}"/>"
                           class="button"><fmt:message
                                key="page.action.edit"/></a>
                    </div>

                </c:if>


                <div class="accountProfile">
                    <div class="my_avatar"><img src="<c:url value="/rest/image/medium/${account.image.id}"/>">

                        <c:if test='${not empty principal && empty catcher}'>
                            <a class="button"><fmt:message key="page.action.uploadPhoto"/></a>
                        </c:if>

                    </div>
                    <div class="my_inf">
                        <div class="my_name">${account.name}<span>(${account.rating.value})</span></div>
                        <div class="my_location"><span><fmt:message
                                key="user.place"/>: </span>${account.accountProfile.location.data}</div>
                        <div class="my_date"><span><fmt:message
                                key="user.birthday"/>: </span>${account.userProfile.birthday.text}</div>
                        <div class="my_lang">
                            <fmt:message key="user.languages"/>:
                            <ul>
                                <c:forEach items="${account.accountProfile.langs}" var="lang" varStatus="stat">
                                    <li class="${lang}"><fmt:message key="locale.${lang}"/></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test='${not empty account.accountProfile.desc}'>
                <div class="description">
                    <h2 class="people_title"><fmt:message key="user.aboutMe"/>:</h2>
                    ${account.accountProfile.desc}
                </div>
            </c:if>
            <c:if test='${fn:length(account.userProfile.knowledgies) > 0}'>
            <div class="table_inf">
                <h2 class="people_title"><fmt:message key="user.knowledge"/>:</h2>
                <table>
                    <thead>
                    <tr>
                        <th><fmt:message key="page.date.period"/></th>
                        <th><fmt:message key="user.specialty"/></th>
                        <th><fmt:message key="user.specialty.placeOf"/></th>
                        <th><fmt:message key="user.specialty.document"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${account.userProfile.knowledgies}" var="knowledge">
                        <tr>
                            <td>
                                <fmt:message key="page.date.from"/> ${knowledge.begin.text}
                                <br/>
                                <fmt:message key="page.date.to"/> ${knowledge.end.text}
                            </td>
                            <td>${knowledge.specialist}</td>
                            <td>${knowledge.location}</td>
                            <td>${knowledge.document}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            </c:if>
            <c:if test='${fn:length(account.userProfile.practices) > 0}'>
            <div class="table_inf">
                <h2 class="people_title"><fmt:message key="user.experience"/>:</h2>
                <table class="experience">
                    <thead>
                    <tr>
                        <th><fmt:message key="page.date.period"/></th>
                        <th><fmt:message key="content.description"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${account.userProfile.practices}" var="practice">
                        <tr>
                            <td>
                                <fmt:message key="page.date.from"/> ${practice.begin.text}
                                <br/>
                                <fmt:message key="page.date.to"/> ${practice.end.text}
                            </td>
                            <td>${practice.desc}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            </c:if>
        </article>
    </div>
    <!-- #content-->
</div>
<!-- #container-->

<aside id="sideRight">
    <tiles:insertDefinition name="categoryTree"/>
    <tiles:insertDefinition name="regionFilter"/>
</aside>
<!-- #sideRight -->




