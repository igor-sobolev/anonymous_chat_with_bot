/**
 * Created by PiCy on 2/3/2016.
 */
$(document).ready(function(){

    $("#drag").draggable();
    $("#sortable").sortable();
    $("#sortable").disableSelection();
    $("#datepicker").datepicker({ monthNames:
        ["Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август",
            "Сентябрь","Октябрь","Ноябрь","Декабрь"],
        dayNamesMin: ["Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"]});

});