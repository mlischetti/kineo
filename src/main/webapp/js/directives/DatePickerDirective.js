app.directive('datePicker', function () {
    return function (scope, element) {
        $(element).datepicker({});
    };
});