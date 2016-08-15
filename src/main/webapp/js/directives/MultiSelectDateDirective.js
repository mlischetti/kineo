app.directive('multiSelectDate', function ($filter) {
    return {
        restrict: 'E',
        require: '?ngModel',
        templateUrl: 'components/_partials/multi_select_date.html',
        link: function (scope, element, attrs, ngModel) {
            //Options
            var options = {};
            options.readonly = attrs.readonly || false;
            options.format = attrs.format || 'yyyy-MM-dd';
            options.yearOrder = (attrs.yearOrder && attrs.yearOrder === 'asc') ? false : true; // year order: 'asc' or 'desc', default: desc
            options.endYear = attrs.endYear || new Date().getFullYear(); // default: this year
            options.startYear = attrs.startYear || new Date().getFullYear() - 100; // default: this year - 100

            scope.readonly = options.readonly;

            if (!ngModel) return;

            // GET FROM NG MODEL AND PUT IT IN LOCAL SCOPE
            ngModel.$render = function () {
                scope.date = {
                    day: $filter('date')(ngModel.$viewValue, 'dd'),
                    month: $filter('date')(ngModel.$viewValue, 'MM'),
                    year: $filter('date')(ngModel.$viewValue, 'yyyy')
                };
            };

            scope.yearOrder = options.yearOrder;

            // INIT YEARS, MONTHS AND DAYS NUMBER
            scope.selects = {
                days: function () {
                    // Get number of days based on month + year
                    // (January = 31, February = 28, April = 30, February 2000 = 29) or 31 if no month selected yet
                    var nbDays = new Date(scope.date.year, scope.date.month, 0).getDate() || 31;
                    var daysList = [];
                    for (var i = 1; i <= nbDays; i++) {
                        var iS = i.toString();
                        daysList.push((iS.length < 2) ? '0' + iS : iS); // Adds a leading 0 if single digit
                    }
                    return daysList;
                },
                months: function () {
                    var monthList = [];
                    for (var i = 1; i <= 12; i++) {
                        var iS = i.toString();
                        monthList.push((iS.length < 2) ? '0' + iS : iS); // Adds a leading 0 if single digit
                    }
                    return monthList;
                },
                years: function () {
                    var yearsList = [];
                    for (var i = options.endYear; i >= options.startYear; i--) {
                        yearsList.push(i.toString());
                    }
                    return yearsList;
                }
            };

            // WATCH FOR scope.date CHANGES
            scope.$watch('date', function (date) {

                // IF REQUIRED
                if (attrs.required) {
                    // VALIDATION RULES
                    var yearIsValid = !!date.year && parseInt(date.year) <= options.endYear && parseInt(date.year) >= options.startYear;
                    var monthIsValid = !!date.month;
                    var dayIsValid = !!date.day;

                    //console.log(yearIsValid, monthIsValid, dayIsValid);

                    // SET INPUT VALIDITY
                    ngModel.$setValidity('required', yearIsValid || monthIsValid || dayIsValid ? true : false);
                    ngModel.$setValidity('yearRequired', yearIsValid ? true : false);
                    ngModel.$setValidity('monthRequired', monthIsValid ? true : false);
                    ngModel.$setValidity('dayRequired', dayIsValid ? true : false);

                    // UPDATE NG MODEL
                    if (yearIsValid && monthIsValid && dayIsValid) {
                        ngModel.$setViewValue(new Date(date.year, date.month - 1, date.day));
                    }
                }
                // IF NOT REQUIRED (still need the 3 values filled to update the model)
                else if (date.year && date.month && date.day) {
                    ngModel.$setViewValue(new Date(date.year, date.month - 1, date.day));
                }

                // IF NOT REQUIRED (still need the 3 values filled to update the model)
                else if (date.year && date.month && date.day) {
                    ngModel.$setViewValue(new Date(date.year, date.month - 1, date.day));
                }
            }, true);
        }
    }
});