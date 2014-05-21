var locale = {};

locale.name = 'ru';

locale.pagingText = 'cтраница';
locale.pagingGo = 'выб.';
locale.pagingOf = 'из';
locale.pagingEmpty = 'нет записей, удовлетворяющих вашим условиям';


locale.action = {};
locale.action.successEdit = 'Операция изменения данных успешно завершена';

locale.menu = {};
locale.menu.addTripParticipant = 'Добавить участника';
locale.menu.setTripOrganazer = 'Назначить организатором';
locale.menu.cancelTripOrganazer = 'Отменить роль организатор';

locale.participant = {};
locale.participant.status = {};
locale.participant.status.OK = 'Участвует';
locale.participant.status.REQUEST = 'Отправлено приглашение';
locale.participant.status.INVITE = 'Отправлено приглашение';
locale.participant.status.organizer = 'Организатор';
locale.participant.btn = {};
locale.participant.btn.add = 'Добавить участника';
locale.participant.btn.request = 'Отправить запрос на участие';


jQuery(function ($) {
    $.datepicker.regional[locale.name] = {
        closeText: 'Закрыть',
        prevText: '&#x3c;Пред',
        nextText: 'След&#x3e;',
        currentText: 'Сегодня',
        monthNames: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь',
            'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
        monthNamesShort: ['Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июн',
            'Июл', 'Авг', 'Сен', 'Окт', 'Ноя', 'Дек'],
        dayNames: ['воскресенье', 'понедельник', 'вторник', 'среда', 'четверг', 'пятница', 'суббота'],
        dayNamesShort: ['вск', 'пнд', 'втр', 'срд', 'чтв', 'птн', 'сбт'],
        dayNamesMin: ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
        dateFormat: 'dd.mm.y',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: ''};
    $.datepicker.setDefaults($.datepicker.regional[locale.name]);
});
