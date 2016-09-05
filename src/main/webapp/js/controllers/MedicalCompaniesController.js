app.controller('MedicalCompaniesController', function ($scope, $route, $window, MedicalCompany) {
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
    $scope.companyId = -1;

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
            $scope.companyId = response.id;
            $scope.company = {};
            $('#addMedicalCompanySuccessModal').modal('show');
        }, function (error) {
            // error callback
            console.log("Error on creating new company. Error: " + error);
            $scope.company = {};
        });
    };

    $scope.closeAddMedicalCompanySuccessModal = function(companyId) {
        $('#addMedicalCompanySuccessModal').modal('hide');
        if(companyId != null) {
            $window.location.href = '#/medical-insurances/companies/' + companyId;
        } else {
            $window.location.reload();
        }
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
            $scope.company = {};
            $window.location.reload();
        }, function (error) {
            console.log("Error on delete company: " + companyId + ". Error: " + error);
            $scope.company = {};
        });
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
            $scope.company = {};
            $window.location.reload();
        }, function (error) {
            // error callback
            console.log("Error on updating company: " + company.id + ". Error: " + error);
            $scope.company = {};
        });
    };
});

app.controller('MedicalCompanyDetailsController', function ($scope, $route, MedicalCompany) {
    console.log("Current company: " + $route.current.params.id);
    $scope.company = MedicalCompany.get({id: $route.current.params.id});
});
