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

                <c:if test='${not empty catcher && profileServiceWrapper.isUserOwnsCommunity(principal.id,catcher.id)}'>

                <div class="right">
                    <a href="<c:url value="../pf/community_modify_info?guid=${param.guid}"/>" class="button"><fmt:message
                            key="page.action.edit"/></a>
                </div>

   </c:if>



                <div class="accountProfile">
                    <div class="my_avatar"><img src="<c:url value="../rest/image/medium/${account.image.id}"/>">

<c:if test='${not empty catcher && profileServiceWrapper.isUserOwnsCommunity(principal.id,catcher.id)}'>
                        <a class="button"><fmt:message key="page.action.uploadPhoto"/></a>
</c:if>

                    </div>
                    <div class="my_inf">
                        <div class="my_name">${account.name}<span>(${account.rating.value})</span></div>
                        <div class="my_location"><span><fmt:message
                                key="user.place"/>: </span>${account.accountProfile.location.data}</div>


                    </div>
                </div>
            </div>
            <div class="description">
                ${account.accountProfile.desc}
            </div>


        </article>
    </div>
    <!-- #content-->
</div>
<!-- #container-->

<aside id="sideRight" filter="read_only">
    <tiles:insertDefinition name="categoryTree"/>
    <tiles:insertDefinition name="regionFilter"/>
</aside>
<!-- #sideRight -->




