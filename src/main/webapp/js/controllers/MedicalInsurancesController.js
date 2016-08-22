app.controller('MedicalInsurancesController', function ($scope, $window, MedicalCompanies, MedicalCompany, MedicalInsurancePlans, MedicalInsurancePlan) {
    $scope.$on('$viewContentLoaded', function (event) {
        $('html, body').animate({scrollTop: $("#medical-insurances").offset().top}, 1000);
    });

    $scope.companies = [];
    var loadMedicalCompanies = function () {
        MedicalCompanies.get({limit: 100, offset: 0}, function (response) {
            console.log("Getting medical insurances companies - Offset: " + response.paging.offset + ", limit: " + response.paging.limit
                + ", total:" + response.paging.total);
            $scope.companies = response.items;
        }, function (error) {
            // error callback
            console.log("Error on retrieve medical insurances companies. Error: " + error);
        });
    };

    $scope.plans = [];
    var loadMedicalInsurancePlans = function () {
        MedicalInsurancePlans.get({limit: 100, offset: 0}, function (response) {
            console.log("Getting medical insurances plans - Offset: " + response.paging.offset + ", limit: " + response.paging.limit
                + ", total:" + response.paging.total);
            $scope.plans = response.items;
        }, function (error) {
            // error callback
            console.log("Error on retrieve medical insurances plans. Error: " + error);
        });
    };

    $scope.viewContent = function (content) {
        console.log("content selected: " + content);
        if (content == 'companies') {
            loadMedicalCompanies();
        } else {
            loadMedicalInsurancePlans();
        }
        $scope.content = content;
    };

    $scope.viewContent('companies');

    $scope.company = {};

    $scope.newCompanyId = -1;
    $scope.showCreateMedicalCompanyModal = function () {
        $scope.company = {};
        $('#addMedicalCompanyModal').modal('show');
    };
    $scope.addMedicalCompany = function () {
        console.log("Creating new company");
        var company = $scope.company;
        MedicalCompany.save(company, function (response) {
            //success callback
            console.log("New company: " + response.id + " created");
            $scope.newCompanyId = response.id;
            $('#addMedicalCompanySuccessModal').modal('show');
        }, function (error) {
            // error callback
            console.log("Error on creating new company. Error: " + error);
        });
        $scope.company = {};
    };

    //Delete
    $scope.showDeleteMedicalCompanyModal = function (company) {
        $scope.company = company;
        $('#deleteCompanyModal').modal('show');
    };
    $scope.deleteMedicalCompany = function (companyId) {
        console.log("Trying to delete company: " + companyId);
        MedicalCompany.delete({id: companyId}, function (response) {
            console.log("Deleted company: " + companyId);
        }, function (error) {
            console.log("Error on delete company: " + companyId + ". Error: " + error);
        });
        $scope.company = {};
        $window.location.reload();
    };

    //Edit
    $scope.showEditMedicalCompanyModal = function (company) {
        $scope.company = {};
        angular.copy(company, $scope.company);
        $('#editCompanyModal').modal('show');
    };
    $scope.saveCompany = function () {
        var company = $scope.company;
        console.log("Updating company: " + company.id);
        MedicalCompany.update(company, function (response) {
            //success callback
            console.log("Updated company: " + company.id);
        }, function (error) {
            // error callback
            console.log("Error on updating company: " + company.id + ". Error: " + error);
        });
        $scope.company = {};
        $window.location.reload();
    };

    $scope.plan = {};

    $scope.newPlanId = -1;
    $scope.showCreateMedicalInsurancePlanModal = function () {
        $scope.plan = {};
        $('#addMedicalInsurancePlanModal').modal('show');
    };
    $scope.addPlan = function () {
        var planToCreate = {plan: $scope.plan.plan, company_id: $scope.plan.company.id};
        console.log("Creating new plan");
        MedicalInsurancePlan.save(planToCreate, function (response) {
            //success callback
            console.log("New plan: " + response.id + " created");
            $scope.newPlanId = response.id;
            $('#addMedicalInsurancePlanSuccessModal').modal('show');
        }, function (error) {
            // error callback
            console.log("Error on creating new plan. Error: " + error);
        });
        $scope.plan = {};
    };

    //Delete
    $scope.showDeleteMedicalInsurancePlanModal = function (plan) {
        $scope.plan = plan;
        $('#deleteMedicalInsurancePlanModal').modal('show');
    };
    $scope.deleteMedicalInsurancePlan = function (planId) {
        console.log("Trying to delete plan: " + planId);
        MedicalInsurancePlan.delete({id: planId}, function (response) {
            console.log("Deleted plan: " + planId);
        }, function (error) {
            console.log("Error on delete plan: " + planId + ". Error: " + error);
        });
        $scope.plan = {};
        $window.location.reload();
    };

    //Edit
    $scope.showEditMedicalInsurancePlanModal = function (plan) {
        $scope.plan = {};
        angular.copy(plan, $scope.plan);
        $('#editMedicalInsurancePlanModal').modal('show');
    };
    $scope.savePlan = function () {
        var plan = $scope.plan;
        var planToUpdate = {id: plan.id, plan: plan.plan, company_id: plan.company.id};
        console.log("Updating plan: " + planToUpdate.id);
        MedicalInsurancePlan.update(planToUpdate, function (response) {
            //success callback
            console.log("Updated plan: " + planToUpdate.id);
        }, function (error) {
            // error callback
            console.log("Error on updating plan: " + planToUpdate.id + ". Error: " + error);
        });
        $scope.plan = {};
        $window.location.reload();
    };
});

app.controller('MedicalCompanyDetailsController', function ($scope, $stateParams, MedicalCompany) {
    var currentId = $stateParams.id;
    console.log("Current company: " + currentId);
    $scope.company = MedicalCompany.get($stateParams);
});

app.controller('MedicalInsurancePlanDetailsController', function ($scope, $stateParams, MedicalCompanies, MedicalInsurancePlan) {
    var currentId = $stateParams.id;
    console.log("Current plan: " + currentId);
    $scope.plan = MedicalInsurancePlan.get($stateParams);
});