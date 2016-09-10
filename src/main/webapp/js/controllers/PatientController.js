app.controller('PatientsController', function ($scope, $window, Patient, MedicalInsurancePlan, DocumentTypes) {
    $scope.$on('$viewContentLoaded', function (event) {
        $('html, body').animate({scrollTop: $("#patients").offset().top}, 1000);
    });

    $scope.patients = [];
    Patient.get({limit: 20, offset: 0}, function (response) {
        console.log("Getting patients - Offset: " + response.paging.offset + ", limit: "
            + response.paging.limit + ", total:" + response.paging.total);
        $scope.patients = response.items;
    }, function (error) {
        console.log("Error on retrieve patients. Error: " + error);
    });

    $scope.plans = [];
    MedicalInsurancePlan.get({limit: 100, offset: 0}, function (response) {
        console.log("Getting medical insurances plans - Offset: " + response.paging.offset + ", limit: " + response.paging.limit
            + ", total:" + response.paging.total);
        $scope.plans = response.items;
    }, function (error) {
        console.log("Error on retrieve medical insurances plans. Error: " + error);
    });

    $scope.documentTypes = [];
    DocumentTypes.get({}, function (response) {
        console.log("Getting document types");
        $scope.documentTypes = response;
    }, function (error) {
        console.log("Error on retrieve document types. Error: " + error);
    });

    $scope.patient = {};

    //Delete
    $scope.showDeletePatientModal = function (patient) {
        $scope.patient = patient;
        $('#deletePatientModal').modal('show');
    };
    $scope.deletePatient = function (patientId) {
        console.log("Trying to delete patient: " + patientId);
        Patient.delete({id: patientId}, function (response) {
            console.log("Deleted patient: " + patientId);
            $scope.patient = {};
            toastr.success('Paciente exitosamente eliminado!');
            $window.location.href = '#/patients/';
        }, function (error) {
            console.log("Error on delete patient: " + patientId + ". Error: " + error);
            $scope.patient = {};
            toastr.error('Error al eliminar el paciente.', 'Error');
        });
    };

    //Search & Filter
    $scope.clearSearch = function () {
        $scope.search = {};
    };
});

app.controller('AddPatientController', function ($scope, $window, Patient, MedicalInsurancePlan, DocumentTypes) {
    $scope.plans = [];
    MedicalInsurancePlan.get({limit: 100, offset: 0}, function (response) {
        console.log("Getting medical insurances plans - Offset: " + response.paging.offset + ", limit: " + response.paging.limit
            + ", total:" + response.paging.total);
        $scope.plans = response.items;
    }, function (error) {
        console.log("Error on retrieve medical insurances plans. Error: " + error);
    });

    $scope.documentTypes = [];
    DocumentTypes.get({}, function (response) {
        console.log("Getting document types");
        $scope.documentTypes = response;
    }, function (error) {
        console.log("Error on retrieve document types. Error: " + error);
    });

    $scope.patient = {};

    $scope.addPatient = function () {
        console.log("Creating new patient");
        var patient = $scope.patient;
        patient.date_of_birth = moment(patient.date_of_birth).format(API_DATETIME_FORMAT);
        Patient.save(patient, function (response) {
            console.log("New patient: " + response.id + " created");
            $scope.patient = {};
            toastr.success('Paciente exitosamente creado!');
            $window.location.href = '#/patients/' + response.id;
        }, function (error) {
            $scope.patient = {};
            console.log("Error on creating new patient. Error: " + error);
            toastr.error('Error al crear el paciente.', 'Error');
        });
    };
});

app.controller('EditPatientController', function ($scope, $route, $window, Patient, MedicalInsurancePlan, DocumentTypes) {
    $scope.plans = [];
    MedicalInsurancePlan.get({limit: 100, offset: 0}, function (response) {
        console.log("Getting medical insurances plans - Offset: " + response.paging.offset + ", limit: " + response.paging.limit
            + ", total:" + response.paging.total);
        $scope.plans = response.items;
    }, function (error) {
        console.log("Error on retrieve medical insurances plans. Error: " + error);
    });

    $scope.documentTypes = [];
    DocumentTypes.get({}, function (response) {
        console.log("Getting document types");
        $scope.documentTypes = response;
    }, function (error) {
        console.log("Error on retrieve document types. Error: " + error);
    });

    $scope.patient = Patient.get({id: $route.current.params.id});

    $scope.savePatient = function () {
        var patient = $scope.patient;
        patient.date_of_birth = moment(patient.date_of_birth).format(API_DATETIME_FORMAT);
        patient.medical_insurance_plan_id = patient.medical_insurance_plan.id;
        console.log("Updating patient: " + patient.id);
        Patient.update(patient, function (response) {
            console.log("Updated patient: " + patient.id);
            $scope.patient = {};
            toastr.success('Paciente exitosamente modificado!');
            $window.location.href = '#/patients/' + patient.id;
        }, function (error) {
            $scope.patient = {};
            console.log("Error on updating patient: " + patient.id + ". Error: " + error);
            toastr.error('Error al editar el paciente.', 'Error');
        });
    };
});

app.controller('ViewPatientController', function ($scope, $route, Patient) {
    console.log("Current patient: " + $route.current.params.id);
    $scope.patient = Patient.get({id: $route.current.params.id});
});