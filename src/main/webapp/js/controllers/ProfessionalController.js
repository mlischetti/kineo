const API_DATETIME_FORMAT = "YYYY-MM-DD";

app.controller('ProfessionalController', function ($scope, $window, Professional, DocumentTypes, ProfessionalCategories) {
    $scope.$on('$viewContentLoaded', function (event) {
        $('html, body').animate({scrollTop: $("#professionals").offset().top}, 1000);
    });

    $scope.professionals = [];
    Professional.get({limit: 100, offset: 0}, function (response) {
        console.log("Getting professionals - Offset: " + response.paging.offset + ", limit: " + response.paging.limit + ", total:" + response.paging.total);
        $scope.professionals = response.items;
    }, function (error) {
        // error callback
        console.log("Error on retrieve professionals. Error: " + error);
    });

    $scope.categories = [];
    ProfessionalCategories.get({}, function (response) {
        console.log("Getting professional categories");
        $scope.categories = response;
    }, function (error) {
        // error callback
        console.log("Error on retrieve professional categories. Error: " + error);
    });

    $scope.documentTypes = [];
    DocumentTypes.get({}, function (response) {
        console.log("Getting document types");
        $scope.documentTypes = response;
    }, function (error) {
        // error callback
        console.log("Error on retrieve document types. Error: " + error);
    });

    //Create
    $scope.professional = {};
    $scope.professionalId = -1;

    $scope.showCreateProfessionalModal = function () {
        $scope.professional = {};
        $('#addProfessionalModal').modal('show');
    };
    $scope.addProfessional = function () {
        console.log("Creating new professional");
        var professional = $scope.professional;
        professional.date_of_birth = moment(professional.date_of_birth).format(API_DATETIME_FORMAT);
        Professional.save(professional, function (response) {
            //success callback
            console.log("New professional: " + response.id + " created");
            $scope.professionalId = response.id;
            $scope.professional = {};
            $('#addProfessionalSuccessModal').modal('show');
        }, function (error) {
            // error callback
            console.log("Error on creating new professional. Error: " + error);
            $scope.professional = {};
        });
    };
    $scope.closeAddProfessionalSuccessModal = function(professionalId) {
        $('#addProfessionalSuccessModal').modal('hide');
        if(professionalId != null) {
            $window.location.href = '#/professionals/' + professionalId;
        } else {
            $window.location.reload();
        }
    };

    //Delete
    $scope.showDeleteProfessionalModal = function (professional) {
        $scope.professional = professional;
        $('#deleteProfessionalModal').modal('show');
    };
    $scope.deleteProfessional = function (professionalId) {
        console.log("Trying to delete professional: " + professionalId);
        Professional.delete({id: professionalId}, function (response) {
            console.log("Deleted professional: " + professionalId);
            $scope.professional = {};
            $window.location.reload();
        }, function (error) {
            console.log("Error on professional: " + professionalId + ". Error: " + error);
            $scope.professional = {};
        });
    };

    //Edit
    $scope.showEditProfessional = function (professional) {
        $scope.professional = {};
        angular.copy(professional, $scope.professional);
        $('#editProfessionalModal').modal('show');
    };
    $scope.saveProfessional = function () {
        var professional = $scope.professional;
        console.log("Updating professional: " + professional.id);
        professional.date_of_birth = moment(professional.date_of_birth).format(API_DATETIME_FORMAT);
        Professional.update(professional, function (response) {
            //success callback
            console.log("Updated professional: " + professional.id);
            $scope.professional = {};
            $window.location.reload();
        }, function (error) {
            // error callback
            console.log("Error on updating professional: " + professional.id + ". Error: " + error);
            $scope.professional = {};
        });
    };

    $scope.clearSearch = function () {
        $scope.search = {};
    };
});

app.controller('ProfessionalDetailsController', function ($scope, $route, Professional) {
    console.log("Current professional: " + $route.current.params.id);
    $scope.professional = Professional.get({id: $route.current.params.id});
});