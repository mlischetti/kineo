app.controller('MedicalInsurancesPlansController', function ($scope, $window, MedicalCompany, MedicalInsurancePlan) {
    $scope.companies = [];
    MedicalCompany.get({limit: 100, offset: 0}, function (response) {
        console.log("Getting medical insurances companies - Offset: " + response.paging.offset + ", limit: " + response.paging.limit
            + ", total:" + response.paging.total);
        $scope.companies = response.items;
    }, function (error) {
        // error callback
        console.log("Error on retrieve medical insurances companies. Error: " + error);
    });

    $scope.plans = [];
    MedicalInsurancePlan.get({limit: 100, offset: 0}, function (response) {
        console.log("Getting medical insurances plans - Offset: " + response.paging.offset + ", limit: " + response.paging.limit
            + ", total:" + response.paging.total);
        $scope.plans = response.items;
    }, function (error) {
        // error callback
        console.log("Error on retrieve medical insurances plans. Error: " + error);
    });

    $scope.plan = {};
    $scope.planId = -1;

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
            $scope.planId = response.id;
            $scope.plan = {};
            $('#addMedicalInsurancePlanSuccessModal').modal('show');
        }, function (error) {
            // error callback
            console.log("Error on creating new plan. Error: " + error);
            $scope.plan = {};
        });
    };

    $scope.closeAddMedicalInsurancePlanSuccessModal = function(planId) {
        $('#addMedicalInsurancePlanSuccessModal').modal('hide');
        if(planId != null) {
            $window.location.href = '#/medical-insurances/plans/' + planId;
        } else {
            $window.location.reload();
        }
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
            $scope.plan = {};
            $window.location.reload();
        }, function (error) {
            console.log("Error on delete plan: " + planId + ". Error: " + error);
            $scope.plan = {};
        });
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
            $scope.plan = {};
            $window.location.reload();
        }, function (error) {
            // error callback
            console.log("Error on updating plan: " + planToUpdate.id + ". Error: " + error);
            $scope.plan = {};
        });
    };

    //Search & Filter
    $scope.clearSearch = function () {
        $scope.search = {};
    };
});

app.controller('MedicalInsurancePlanDetailsController', function ($scope, $route, MedicalCompanies, MedicalInsurancePlan) {
    console.log("Current plan: " + $route.current.params.id);
    $scope.plan = MedicalInsurancePlan.get({id: $route.current.params.id});
});