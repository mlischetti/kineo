var MedicalInsurancesController = ['$scope', '$window', '$state', 'MedicalInsuranceCompanies', 'MedicalInsurancePlans',
    function ($scope, $window, $state, MedicalInsuranceCompanies, MedicalInsurancePlans) {

    $scope.$on('$viewContentLoaded', function (event) {
        $('html, body').animate({scrollTop: $("#medical-insurances").offset().top}, 1000);
    });

    $scope.plans = [];
    $scope.companies = [];
    $scope.content = "companies";

    var loadMedicalInsuranceCompanies = function() {
        MedicalInsuranceCompanies.get({limit: 100, offset: 0}, function(response) {
            console.log("Getting medical insurances companies - Offset: " + response.paging.offset + ", limit: "  + response.paging.limit
                + ", total:" + response.paging.total);
            $scope.companies = response.items;
        }, function(error){
            // error callback
            console.log("Error on retrieve medical insurances companies. Error: " + error);
        });
    };

    var loadMedicalInsurancePlans = function() {
        MedicalInsurancePlans.get({limit: 100, offset: 0}, function(response) {
            console.log("Getting medical insurances plans - Offset: " + response.paging.offset + ", limit: "  + response.paging.limit
                + ", total:" + response.paging.total);
            $scope.plans = response.items;
        }, function(error){
            // error callback
            console.log("Error on retrieve medical insurances plans. Error: " + error);
        });
    };

    $scope.viewContent = function(content) {
        console.log("content selected:" + content);
        if(content == 'companies') {
            loadMedicalInsuranceCompanies();
        } else {
            loadMedicalInsurancePlans();
        }
        $scope.content = content;
    };

    loadMedicalInsuranceCompanies();

}];