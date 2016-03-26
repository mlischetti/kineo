var MainController = ['$scope', '$rootScope', '$state', '$sessionStorage', 'context', function ($scope, $rootScope, $state, $sessionStorage, context) {

    $scope.$storage = $sessionStorage;

    $scope.context = context;

    $rootScope.$state = $state;

}];