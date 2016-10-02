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
    $scope.professional = {
        id: null,
        category: null,
        last_name: null,
        first_name: null,
        date_of_birth: null,
        email: null,
        phone: null,
        document_type: null,
        document_number: null
    };

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
        Professional.get({id: $route.current.params.id}, function (response) {
            $scope.professional.id = response.id;
            $scope.professional.category = response.category;
            $scope.professional.last_name = response.last_name;
            $scope.professional.first_name = response.first_name;
            $scope.professional.date_of_birth = response.date_of_birth;
            $scope.professional.email = response.email;
            $scope.professional.phone = response.phone;
            $scope.professional.document_type = response.document_type;
            $scope.professional.document_number = response.document_number;
        }, function (error) {
            console.log("Error on retrieve professional. Error: " + error);
        });
    }

    $scope.saveProfessional = function () {
        $scope.professional.date_of_birth = moment($scope.professional.date_of_birth).format(API_DATE_FORMAT);
        if($scope.mode == 'edit') {
            console.log("Updating professional: " + $scope.professional.id);
            Professional.update($scope.professional, function (response) {
                console.log("Updated professional: " + $scope.professional.id + ". Response: " + response);
                $scope.professional = {};
                toastr.success('Profesional exitosamente modificado!');
                $window.location.href = '#/professionals/';
            }, function (error) {
                console.log("Error on updating professional: " + $scope.professional.id + ". Error: " + error);
                $scope.professional = {};
                toastr.error('Error al modificar el profesional.', 'Error');
            });
        } else {
            console.log("Creating new professional");
            Professional.save($scope.professional, function (response) {
                console.log("New professional: " + response.id + " created");
                $scope.professional = {};
                toastr.success('Profesional exitosamente creado!');
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
    $scope.professional = {};
    Professional.get({id: $route.current.params.id}, function (response){
        $scope.professional = response;
    }, function (error) {
        console.log("Error on retrieve professional. Error: " + error);
    });
});