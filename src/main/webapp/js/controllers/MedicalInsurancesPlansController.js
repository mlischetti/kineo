app.controller('MedicalInsurancesPlansController', function ($scope, $window, MedicalInsurancePlan) {
    $scope.plans = [];
    $scope.plan = {};

    MedicalInsurancePlan.get({limit: 100, offset: 0}, function (response) {
        $scope.plans = response.items;
    }, function (error) {
        console.log("Error on retrieve medical insurances plans. Error: " + error);
    });

    $scope.showDeleteMedicalInsurancePlanModal = function (plan) {
        $scope.plan = plan;
        $('#deleteMedicalInsurancePlanModal').modal('show');
    };

    $scope.deleteMedicalInsurancePlan = function (planId) {
        console.log("Trying to delete plan: " + planId);
        MedicalInsurancePlan.delete({id: planId}, function (response) {
            console.log("Deleted plan: " + planId +  ". Response: " + response);
            $scope.plan = {};
            toastr.success('Plan exitosamente eliminado!');
            $window.location.href = '#/medical-insurances/plans/';
        }, function (error) {
            console.log("Error on delete plan: " + planId + ". Error: " + error);
            $scope.plan = {};
            toastr.error('Error al eliminar el plan.', 'Error');
        });
    };

    //Search & Filter
    $scope.clearSearch = function () {
        $scope.search = {};
    };
});

app.controller('AddEditMedicalInsurancePlanController', function ($scope, $route, $window, MedicalCompany, MedicalInsurancePlan) {
    $scope.companies = [];
    $scope.plan = {
        id: null,
        company_id: null,
        plan: null
    };

    MedicalCompany.get({limit: 100, offset: 0}, function (response) {
        $scope.companies = response.items;
    }, function (error) {
        console.log("Error on retrieve medical insurances companies. Error: " + error);
    });

    $scope.mode = $route.current.mode;

    if($scope.mode == 'edit') {
        MedicalInsurancePlan.get({id: $route.current.params.id}, function (response) {
            $scope.plan.id = response.id;
            $scope.plan.company_id = response.company.id;
            $scope.plan.plan = response.plan;
        }, function (error) {
            console.log("Error on retrieve medical insurances plan. Error: " + error);
        });
    }

    $scope.savePlan = function () {
        if($scope.mode == 'edit') {
            console.log("Updating plan: " + $scope.plan.id);
            MedicalInsurancePlan.update($scope.plan, function (response) {
                console.log("Updated plan: " + $scope.plan.id + ". Response: " + response);
                $scope.plan = {};
                toastr.success('Plan exitosamente modificado!');
                $window.location.href = '#/medical-insurances/plans/';
            }, function (error) {
                console.log("Error on updating plan: " + $scope.plan.id + ". Error: " + error);
                $scope.plan = {};
                toastr.error('Error al editar el plan.', 'Error');
            });
        } else {
            console.log("Creating new plan");
            MedicalInsurancePlan.save($scope.plan, function (response) {
                $scope.plan = {};
                console.log("New plan: " + response.id + " created");
                toastr.success('Plan exitosamente creado!');
                $window.location.href = '#/medical-insurances/plans/';
            }, function (error) {
                console.log("Error on creating new plan. Error: " + error);
                $scope.plan = {};
                toastr.error('Error al crear el plan.', 'Error');
            });
        }
    };
});

app.controller('ViewMedicalInsurancePlanController', function ($scope, $route, MedicalInsurancePlan) {
    $scope.plan = {};
    MedicalInsurancePlan.get({id: $route.current.params.id}, function (response) {
        $scope.plan = response;
    }, function (error) {
        console.log("Error on retrieve medical insurances plan. Error: " + error);
    });
});