app.controller('MedicalInsurancesPlansController', function ($scope, $window, MedicalInsurancePlan) {
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
    MedicalCompany.get({limit: 100, offset: 0}, function (response) {
        console.log("Getting medical insurances companies - Offset: " + response.paging.offset + ", limit: " + response.paging.limit
            + ", total:" + response.paging.total);
        $scope.companies = response.items;
    }, function (error) {
        // error callback
        console.log("Error on retrieve medical insurances companies. Error: " + error);
    });

    console.log($route.current.mode + " Plan");
    $scope.mode = $route.current.mode;

    $scope.plan = {};
    if($scope.mode == 'edit') {
        $scope.plan = MedicalInsurancePlan.get({id: $route.current.params.id});
    }

    $scope.savePlan = function () {
        var plan = {id: $scope.plan.id, plan: $scope.plan.plan, company_id: $scope.plan.company.id};

        if($scope.mode == 'edit') {
            console.log("Updating plan: " + plan.id);
            MedicalInsurancePlan.update(plan, function (response) {
                console.log("Updated plan: " + plan.id);
                $scope.plan = {};
                toastr.success('Plan exitosamente modificado!');
                $window.location.href = '#/medical-insurances/plans/' + plan.id;
            }, function (error) {
                console.log("Error on updating plan: " + plan.id + ". Error: " + error);
                $scope.plan = {};
                toastr.error('Error al editar el plan.', 'Error');
            });
        } else {
            console.log("Creating new plan");
            MedicalInsurancePlan.save(plan, function (response) {
                $scope.plan = {};
                console.log("New plan: " + response.id + " created");
                toastr.success('Plan exitosamente creado!');
                $window.location.href = '#/medical-insurances/plans/' + response.id;
            }, function (error) {
                console.log("Error on creating new plan. Error: " + error);
                $scope.plan = {};
                toastr.error('Error al crear el plan.', 'Error');
            });
        }
    };
});

app.controller('ViewMedicalInsurancePlanController', function ($scope, $route, MedicalInsurancePlan) {
    console.log("Current plan: " + $route.current.params.id);
    $scope.plan = MedicalInsurancePlan.get({id: $route.current.params.id});
});