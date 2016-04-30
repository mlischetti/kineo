var MedicalInsurancesController = ['$scope', '$window', '$state', 'MedicalCompanies', 'MedicalCompany', 'MedicalInsurancePlans', 'MedicalInsurancePlan', function ($scope, $window, $state, MedicalCompanies, MedicalCompany, MedicalInsurancePlans, MedicalInsurancePlan) {
    $scope.$on('$viewContentLoaded', function (event) {
        $('html, body').animate({scrollTop: $("#medical-insurances").offset().top}, 1000);
    });
    $scope.plans = [];
    $scope.companies = [];

    var loadMedicalCompanies = function() {
        MedicalCompanies.get({limit: 100, offset: 0}, function(response) {
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
        console.log("content selected: " + content);
        if(content == 'companies') {
            loadMedicalCompanies();
        } else {
            loadMedicalInsurancePlans();
        }
        $scope.content = content;
    };

    $scope.deleteMedicalCompany = function (companyId) {
        console.log("Trying to delete company: " + companyId);
        MedicalCompany.delete({id:companyId}, function(response) {
            console.log("Deleted company: " + companyId);
            $scope.viewContent('companies');
        }, function (error) {
            console.log("Error on delete company: " + companyId + ". Error: " + error);
        })
    };

    $scope.deleteMedicalInsurancePlan = function (planId) {
        console.log("Trying to delete plan: " + planId);
        MedicalInsurancePlan.delete({id:planId}, function(response) {
            console.log("Deleted plan: " + planId);
            $scope.viewContent('plans');
        }, function (error) {
            console.log("Error on delete plan: " + planId + ". Error: " + error);
        })
    };

    $scope.viewContent('companies');
}];

var MedicalCompanyDetailsController = ['$scope', '$rootScope', '$stateParams', 'MedicalCompany', function ($scope, $rootScope, $stateParams, MedicalCompany) {
    var currentId = $stateParams.id;
    console.log("Current company: " + currentId);
    $scope.currentCompany = MedicalCompany.get($stateParams);

    $scope.saveCompany = function(){
        var company = $scope.currentCompany;
        console.log("Updating company: " + company.id);
        MedicalCompany.update(company, function (response) {
            //success callback
            console.log("Updated company: " + company.id);
            $('#editCompanySuccessModal').modal('show');
        }, function(error) {
            // error callback
            console.log("Error on updating company: " + company.id + ". Error: " + error);
        });
    };
}];

var AddMedicalCompanyController = ['$scope', '$rootScope', '$stateParams', 'MedicalCompany', function ($scope, $rootScope, $stateParams, MedicalCompany) {
    $scope.company = {};
    $scope.newCompanyId = -1;

    $scope.addMedicalCompany = function() {
        MedicalCompany.save($scope.company, function(response){
            //success callback
            console.log("New company: " + response.id + " created");
            $scope.newCompanyId = response.id;
            $scope.company = {};
            $('#addMedicalCompanySuccessModal').modal('show');
        }, function(error){
            // error callback
            console.log("Error on creating new company. Error: " + error);
        });
    }
}];

var MedicalInsurancePlanDetailsController = ['$scope', '$rootScope', '$stateParams', 'MedicalCompanies','MedicalInsurancePlan', function ($scope, $rootScope, $stateParams, MedicalCompanies, MedicalInsurancePlan) {
    $scope.companies = [];
    var currentId = $stateParams.id;
    console.log("Current plan: " + currentId);

    $scope.currentPlan = MedicalInsurancePlan.get($stateParams);

    MedicalCompanies.get({limit: 100, offset: 0}, function (response) {
        console.log("Getting medical insurances companies - Offset: " + response.paging.offset + ", limit: " + response.paging.limit
            + ", total:" + response.paging.total);
        $scope.companies = response.items;
    }, function (error) {
        // error callback
        console.log("Error on retrieve medical insurances companies. Error: " + error);
    });

    $scope.savePlan = function () {
        var plan = $scope.currentPlan;
        var planToUpdate = {id: plan.id, plan: plan.plan, company_id: plan.company.id};
        console.log("Updating plan: " + planToUpdate.id);
        MedicalInsurancePlan.update(planToUpdate, function (response) {
            //success callback
            console.log("Updated plan: " + planToUpdate.id);
            $('#editMedicalInsurancePlanSuccessModal').modal('show');
        }, function (error) {
            // error callback
            console.log("Error on updating plan: " + planToUpdate.id + ". Error: " + error);
        });
    };
}];

var AddMedicalInsurancePlanController = ['$scope', '$rootScope', '$stateParams', 'MedicalInsurancePlan', function ($scope, $rootScope, $stateParams, MedicalInsurancePlan) {
    $scope.plan = {};
    $scope.newPlanId = -1;

    $scope.addPlan = function() {
        var planToCreate = {plan: $scope.plan.plan, company_id: $scope.plan.company.id};
        console.log("Creating new plan");
        MedicalInsurancePlan.save(planToCreate, function(response){
            //success callback
            console.log("New plan: " + response.id + " created");
            $scope.newPlanId = response.id;
            $scope.plan = {};
            $('#addMedicalInsurancePlanSuccessModal').modal('show');
        }, function(error){
            // error callback
            console.log("Error on creating new plan. Error: " + error);
        });
    }
}];