var MedicalInsurancesController = ['$scope', '$window', '$state', 'MedicalInsuranceCompanies', function ($scope, $window, $state, MedicalInsuranceCompanies) {

    $scope.$on('$viewContentLoaded', function (event) {
        $('html, body').animate({scrollTop: $("#medical-insurances").offset().top}, 1000);
    });

    $scope.plans = [];
    $scope.companies = [];
    $scope.content = "companies";

    MedicalInsuranceCompanies.get({limit: 100, offset: 0}, function(response) {
        console.log("Getting medical insurances companies - Offset: " + response.paging.offset + ", limit: "  + response.paging.limit
            + ", total:" + response.paging.total);
        $scope.companies = response.items;
    }, function(error){
        // error callback
        console.log("Error on retrieve medical insurances companies. Error: " + error);
    });

    $scope.viewContent = function(content) {
        console.log("content selected:" + content);
        $scope.content = content;
    };
}];