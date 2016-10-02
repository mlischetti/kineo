app.controller('MedicalCompaniesController', function ($scope, $window, MedicalCompany) {
    $scope.companies = [];
    $scope.company = {};

    MedicalCompany.get({limit: 100, offset: 0}, function (response) {
        $scope.companies = response.items;
    }, function (error) {
        console.log("Error on retrieve medical insurances companies. Error: " + error);
    });

    $scope.showDeleteMedicalCompanyModal = function (company) {
        $scope.company = company;
        $('#deleteCompanyModal').modal('show');
    };
    $scope.deleteMedicalCompany = function (companyId) {
        console.log("Trying to delete company: " + companyId);

        MedicalCompany.delete({id: companyId}, function (response) {
            console.log("Deleted company: " + companyId + ". Response: " + response);
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
    $scope.company = {
        id: null,
        name: null
    };
    $scope.mode = $route.current.mode;

    if($scope.mode == 'edit') {
        $scope.company = MedicalCompany.get({id: $route.current.params.id});

        MedicalCompany.get({id: $route.current.params.id}, function(response) {
            $scope.company.id = response.id;
            $scope.name = response.name;
        }, function (error) {
            console.log("Error on retrieve medical insurances company. Error: " + error);
        });
    }

    $scope.saveCompany = function () {
        if($scope.mode == 'edit') {
            console.log("Updating company: " + $scope.company.id);
            MedicalCompany.update($scope.company, function (response) {
                console.log("Updated company: " + $scope.company.id + ". Response: " + response);
                $scope.company = {};
                toastr.success('Compan&iacute;a exitosamente modificado!');
                $window.location.href = '#/medical-insurances/companies/';
            }, function (error) {
                $scope.company = {};
                console.log("Error on updating company: " + $scope.company.id + ". Error: " + error);
                toastr.error('Error al modificar la Compan&iacute;a.', 'Error');
            });
        } else {
            console.log("Creating new company");
            MedicalCompany.save($scope.company, function (response) {
                $scope.company = {};
                console.log("New company: " + response.id + " created");
                toastr.success('Compan&iacute;a exitosamente creada!');
                $window.location.href = '#/medical-insurances/companies/';
            }, function (error) {
                $scope.company = {};
                console.log("Error on creating new company. Error: " + error);
                toastr.error('Error al crear la Compan&iacute;a.', 'Error');
            });
        }
    };
});

app.controller('ViewMedicalCompanyController', function ($scope, $route, MedicalCompany) {
    $scope.company = {};
    MedicalCompany.get({id: $route.current.params.id}, function(response) {
       $scope.company = response;
    }, function (error) {
        console.log("Error on retrieve medical insurances company. Error: " + error);
    });
});
