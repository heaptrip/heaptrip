<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<script type="text/javascript">

    $(window).bind("onPageReady", function(e, paramsJson) {
        getTripsList(paramsJson);
    });

    var getTripsList = function(paramsJson) {

        var url = 'rest/trips';

        var tripCriteria = {
            skip : paramsJson.skip ? paramsJson.skip : 0,
            limit : paramsJson.limit,
            categories : {
                checkMode : "IN",
                ids : paramsJson.ct ? paramsJson.ct.split(',') : null
            },
            regions : {
                checkMode : "IN",
                ids : paramsJson.rg ? paramsJson.rg.split(',') : null
            }
        };

        var callbackSuccess = function(data) {

            $('#paginator1').smartpaginator({
                totalrecords : 100 ,
                skip : paramsJson.skip
            });

            $('#paginator2').smartpaginator({
                totalrecords : 100,
                skip : paramsJson.skip
            });

        };

        var callbackError = function(error) {
            alert(error);
        };

        $.postJSON(url, tripCriteria, callbackSuccess, callbackError);

    };

</script>

<tiles:insertDefinition name="pagination" />


<div id="container">
    <div id="contents">

        <article id="article" class="deteil edit">
            <div class="description">
                <div id="list_user_1" class="community">
                    <div class="list_user_inf people_title">
                        <c:choose>
                            <c:when test="${not empty owner.name}">
                                <fmt:message key="user.owner"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="user.i.owner"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <ul>
						<li class="participants_li community_func12"><div class="list_user_img"><img src="/1_small.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li>
                    </ul>
                </div>
                <div id="list_user_2" class="community">
                    <div class="list_user_inf people_title">
                        <c:choose>
                            <c:when test="${not empty owner.name}">
                                <fmt:message key="user.employee"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="user.i.employee"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <ul>
						<li class="participants_li community_func13"><div class="list_user_img"><img src="/1_small.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li>
					</ul>
                </div>
                <div id="list_user_3" class="community">
                    <div class="list_user_inf people_title">
                        <c:choose>
                            <c:when test="${not empty owner.name}">
                                <fmt:message key="user.member"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="user.i.member"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <ul>
						<li class="participants_li community_func14"><div class="list_user_img"><img src="/1_small.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li>
                    </ul>
                </div>
                <div id="list_user_4" class="community">
                    <div class="list_user_inf people_title">
                        <c:choose>
                            <c:when test="${not empty owner.name}">
                                <fmt:message key="user.subscriber"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="user.i.subscriber"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <ul>
					    <li class="participants_li community_func15"><div class="list_user_img"><img src="/1_small.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li>
                    </ul>
                </div>
            </div>

        </article>


        <div id="pagination" class="pagination_mini">
            <div id="paginator1"></div>
        </div>

        <div id="pagination2" class="pagination_mini">
            <div id="paginator2"></div>
        </div>

    </div>
    <!-- #content-->
</div>
<!-- #container-->

<aside id="sideRight">
    <tiles:insertDefinition name="categoryTree"/>
    <tiles:insertDefinition name="regionFilter"/>
</aside>
<!-- #sideRight -->




