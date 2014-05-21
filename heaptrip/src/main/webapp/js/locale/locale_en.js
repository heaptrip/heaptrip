
var locale = {};

locale.name = 'en';

locale.pagingText = 'page';
locale.pagingGo = 'go';
locale.pagingOf = 'of';
locale.pagingEmpty = 'there are no records that meet your criteria';

locale.action = {};
locale.action.successEdit = 'Change operation is completed successfully';

locale.menu = {};
locale.menu.addTripParticipant = 'Add trip member';
locale.menu.setTripOrganazer = 'Make an organizer';
locale.menu.cancelTripOrganazer = 'Cancel organizer';

locale.participant = {};
locale.participant.status = {};
locale.participant.status.OK = 'Participates';
locale.participant.status.REQUEST = 'An invitation';
locale.participant.status.INVITE = 'An invitation'
locale.participant.status.organizer = 'Organizer';
locale.participant.btn = {};
locale.participant.btn.add = 'Add participant';
locale.participant.btn.request = 'Send request participant';


/* English/UK initialisation for the jQuery UI date picker plugin. */
/* Written by Stuart. */
jQuery(function($){
        $.datepicker.regional[locale.name] = {
                closeText: 'Done',
                prevText: 'Prev',
                nextText: 'Next',
                currentText: 'Today',
                monthNames: ['January','February','March','April','May','June',
                'July','August','September','October','November','December'],
                monthNamesShort: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                dayNames: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
                dayNamesShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
                dayNamesMin: ['Su','Mo','Tu','We','Th','Fr','Sa'],
                weekHeader: 'Wk',
                dateFormat: 'dd/mm/y',
                firstDay: 1,
                isRTL: false,
                showMonthAfterYear: false,
                yearSuffix: ''};
        $.datepicker.setDefaults($.datepicker.regional[locale.name]);
});
