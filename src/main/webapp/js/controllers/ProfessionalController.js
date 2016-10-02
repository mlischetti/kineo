const API_DATE_FORMAT = "YYYY-MM-DD";

app.controller('ProfessionalController', function ($scope, $window, Professional) {
    $scope.professionals = [];
    $scope.professional = {};

    Professional.get({limit: 100, offset: 0}, function (response) {
        $scope.professionals = response.items;
    }, function (error) {
        console.log("Error on retrieve professionals. Error: " + error);
    });

    $scope.showDeleteProfessionalModal = function (professional) {
        $scope.professional = professional;
        $('#deleteProfessionalModal').modal('show');
    };

    $scope.deleteProfessional = function (professionalId) {
        console.log("Trying to delete professional: " + professionalId);
        Professional.delete({id: professionalId}, function (response) {
            console.log("Deleted professional: " + professionalId + ". Response:" + response);
            $scope.professional = {};
            toastr.success('Profesional exitosamente eliminado!');
            $window.location.href = '#/professionals/';
        }, function (error) {
            console.log("Error on professional: " + professionalId + ". Error: " + error);
            $scope.professional = {};
            toastr.error('Error al eliminar el profesional.', 'Error');
        });
    };

    $scope.clearSearch = function () {
        $scope.search = {};
    };
});

app.controller('AddEditProfessionalController', function ($scope, $route, $window, Professional, DocumentTypes, ProfessionalCategories) {
    $scope.categories = [];
    $scope.documentTypes = [];
    $scope.professional = {};

    ProfessionalCategories.get({}, function (response) {
        console.log("Getting professional categories");
        $scope.categories = response;
    }, function (error) {
        console.log("Error on retrieve professional categories. Error: " + error);
    });


    DocumentTypes.get({}, function (response) {
        console.log("Getting document types");
        $scope.documentTypes = response;
    }, function (error) {
        console.log("Error on retrieve document types. Error: " + error);
    });

    $scope.mode = $route.current.mode;

    if($scope.mode == 'edit') {
        $scope.professional = Professional.get({id: $route.current.params.id});
    }

    $scope.saveProfessional = function () {
        const professional = $scope.professional;
        if($scope.mode == 'edit') {
            console.log("Updating professional: " + professional.id);
            professional.date_of_birth = moment(professional.date_of_birth).format(API_DATE_FORMAT);
            Professional.update(professional, function (response) {
                console.log("Updated professional: " + professional.id + ". Response: " + response);
                $scope.professional = {};
                toastr.success('Profesional exitosamente modificado!');
                //$window.location.href = '#/professionals/' + professional.id;
                $window.location.href = '#/professionals/';
            }, function (error) {
                console.log("Error on updating professional: " + professional.id + ". Error: " + error);
                $scope.professional = {};
                toastr.error('Error al modificar el profesional.', 'Error');
            });
        } else {
            console.log("Creating new professional");
            professional.date_of_birth = moment(professional.date_of_birth).format(API_DATE_FORMAT);
            Professional.save(professional, function (response) {
                console.log("New professional: " + response.id + " created");
                $scope.professional = {};
                toastr.success('Profesional exitosamente creado!');
                //$window.location.href = '#/professionals/' + response.id;
                $window.location.href = '#/professionals/';
            }, function (error) {
                console.log("Error on creating new professional. Error: " + error);
                $scope.professional = {};
                toastr.error('Error al crear el profesional.', 'Error');
            });
        }
    };
});

app.controller('ViewProfessionalController', function ($scope, $route, Professional) {
    console.log("Current professional: " + $route.current.params.id);
    $scope.professional = Professional.get({id: $route.current.params.id});
});