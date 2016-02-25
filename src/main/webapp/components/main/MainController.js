var MainController = ['$scope', '$rootScope', '$state', '$sessionStorage', 'context', function ($scope, $rootScope, $state, $sessionStorage, context) {

    $scope.$storage = $sessionStorage;

    $scope.context = context;

    $scope.footerText = '© ' + new Date().getFullYear() + ' Kineo, Appointment app';

    $rootScope.$state = $state;

}];