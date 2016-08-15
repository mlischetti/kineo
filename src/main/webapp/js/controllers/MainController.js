app.controller('MainController', function ($scope, $rootScope, $state, $sessionStorage, context) {
    $scope.$storage = $sessionStorage;
    $scope.context = context;
    $rootScope.$state = $state;

});