<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<script id="scheduleParticipantsTemplate" type="text/x-jsrender">

    <div id="{{>schedule.id}}" class="list_user">
        <div class="list_user_inf"><span class="list_user_date">{{>schedule.begin.text}} - {{>schedule.end.text}}</span></div>
        <div class="list_user_button"><input type="button" class="button" value="ADD"></div>
    </div>

</script>


<script type="text/javascript">


    $(window).bind("onPageReady", function (e, paramsJson) {
        getTripScheduleParticipants(paramsJson);
    });

    var getTripScheduleParticipants = function (paramsJson) {

        var criteria = {
            skip: paramsJson.paginator ? paramsJson.paginator.skip : 0,
            limit: paramsJson.paginator ? paramsJson.paginator.limit : null,
            id: paramsJson.id
        };

        var url = 'rest/trip/schedule_participants';

        var callbackSuccess = function (data) {
            $("#scheduleParticipants").html($("#scheduleParticipantsTemplate").render(data.schedules));
            $('#paginator').smartpaginator({
                totalrecords: data.count,
                skip: paramsJson.paginator ? paramsJson.paginator.skip : 0
            });
        };

        var callbackError = function (error) {
            alert(error);
        };

        $.postJSON(url, criteria, callbackSuccess, callbackError);

    };


</script>


<div class="description">

    <span id="scheduleParticipants"></span>

</div>

</article>

<div class="list_posts_button"><input type="button" class="button" value="Search">

    <div class="posts_find"><input type="text" alt="..."></div>
</div>
<div class="list_posts_add">
    <ul></ul>
</div>


<tiles:insertDefinition name="pagination"/>
<div id="paginator"></div>

<%--<div class="description">--%>


<%--<div id="list_user_1" class="list_user">--%>
<%--<div class="list_user_inf"><span class="list_user_date">19.01.13 - 29.01.13</span>Планируется</div>--%>
<%--<ul><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a><span>Организатор</span></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a><span>Организатор</span></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----></ul>--%>
<%--<div class="list_user_button"><input type="button" class="button" value="Пригласить"><input type="button" class="button" value="Учавствовать"></div>--%>
<%--</div>--%>


<%--<div id="list_user_2" class="list_user">--%>
<%--<div class="list_user_inf"><span class="list_user_date">19.01.13 - 29.01.13</span><span class="red">Отменено</span> (в связи с погодными условиями)</div>--%>
<%--<ul><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a><span>Организатор</span></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a><span>Организатор</span></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----></ul>--%>
<%--<div class="list_user_button"><input type="button" class="button" value="Пригласить"><input type="button" class="button" value="Учавствовать"></div>--%>
<%--</div>--%>
<%--<div id="list_user_3" class="list_user">--%>
<%--<div class="list_user_inf"><span class="list_user_date">19.01.13 - 29.01.13</span>Завершено</div>--%>
<%--<ul><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a><span>Организатор</span></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a><span>Организатор</span></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Alexandr Alexeev Alexeevich</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user2.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----><li><div class="list_user_img"><img src="/avatar/user1.jpg"></div><div class="list_user_name"><a href="/">Anna Alexeeva Alexeevskaya</a></div></li><!----%>
<%----></ul>--%>
<%--<div class="list_user_button"><input type="button" class="button" value="Пригласить"><input type="button" class="button" value="Учавствовать"></div>--%>
<%--</div>--%>

<%--</div>--%>

<%--</article>--%>

<%--<div class="list_posts_button"><input type="button" class="button" value="Поиск"><div class="posts_find"><input type="text" alt="....."></div></div>--%>
<%--<div class="list_posts_add">--%>
<%--<ul></ul>--%>
<%--</div>--%>
