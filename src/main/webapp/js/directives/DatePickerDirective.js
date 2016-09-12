app.directive('datePicker', function () {
    return function (scope, element) {
        $(element).datepicker({
            format: 'dd/mm/yyyy',
            todayHighlight: true,
            todayBtn: 'linked',
            autoclose: true,
            keyboardNavigation:true
        });
    };
});