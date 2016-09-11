app.controller('MedicalCompaniesController', function ($scope, $window, MedicalCompany) {
    $scope.companies = [];
    MedicalCompany.get({limit: 100, offset: 0}, function (response) {
        console.log("Getting medical insurances companies - Offset: " + response.paging.offset + ", limit: " + response.paging.limit
            + ", total:" + response.paging.total);
        $scope.companies = response.items;
    }, function (error) {
        // error callback
        console.log("Error on retrieve medical insurances companies. Error: " + error);
    });

    $scope.company = {};

    //Delete
    $scope.showDeleteMedicalCompanyModal = function (company) {
        $scope.company = company;
        $('#deleteCompanyModal').modal('show');
    };
    $scope.deleteMedicalCompany = function (companyId) {
        console.log("Trying to delete company: " + companyId);

        MedicalCompany.delete({id: companyId}, function (response) {
            console.log("Deleted company: " + companyId);
            $scope.company = {};
            toastr.success('Compan&iacute;a exitosamente eliminado!');
            $window.location.href = '#/medical-insurances/companies/';
        }, function (error) {
            console.log("Error on delete company: " + companyId + ". Error: " + error);
            $scope.company = {};
            toastr.error('Error al eliminar la compan&iacute;a.', 'Error');
        });
    };
});

app.controller('AddEditMedicalCompanyController', function ($scope, $route, $window, MedicalCompany) {
    console.log($route.current.mode + " MedicalCompany");
    $scope.mode = $route.current.mode;
    $scope.company = {};
    if($scope.mode == 'edit') {
        $scope.company = MedicalCompany.get({id: $route.current.params.id});
    }

    $scope.saveCompany = function () {
        var company = $scope.company;
        if($scope.mode == 'edit') {
            console.log("Updating company: " + company.id);
            MedicalCompany.update(company, function (response) {
                console.log("Updated company: " + company.id);
                $scope.company = {};
                toastr.success('Compan&iacute;a exitosamente modificado!');
                $window.location.href = '#/medical-insurances/companies/' + company.id;
            }, function (error) {
                $scope.company = {};
                console.log("Error on updating company: " + company.id + ". Error: " + error);
                toastr.error('Error al modificar la Compan&iacute;a.', 'Error');
            });
        } else {
            console.log("Creating new company");
            MedicalCompany.save(company, function (response) {
                $scope.company = {};
                console.log("New company: " + response.id + " created");
                toastr.success('Compan&iacute;a exitosamente creada!');
                $window.location.href = '#/medical-insurances/companies/' + response.id;
            }, function (error) {
                $scope.company = {};
                console.log("Error on creating new company. Error: " + error);
                toastr.error('Error al crear la Compan&iacute;a.', 'Error');
            });
        }
    };
});

app.controller('ViewMedicalCompanyController', function ($scope, $route, MedicalCompany) {
    console.log("Current company: " + $route.current.params.id);
    $scope.company = MedicalCompany.get({id: $route.current.params.id});
});
