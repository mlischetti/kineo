app.controller('PatientController', function ($scope, $window, Patients, Patient, MedicalInsurancePlans, DocumentTypes) {
    $scope.$on('$viewContentLoaded', function (event) {
        $('html, body').animate({scrollTop: $("#patients").offset().top}, 1000);
    });

    $scope.patients = [];
    Patients.get({limit: 20, offset: 0}, function (response) {
        console.log("Getting patients - Offset: " + response.paging.offset + ", limit: "
            + response.paging.limit + ", total:" + response.paging.total);
        $scope.patients = response.items;
    }, function (error) {
        // error callback
        console.log("Error on retrieve patients. Error: " + error);
    });

    $scope.plans = [];
    MedicalInsurancePlans.get({limit: 100, offset: 0}, function (response) {
        console.log("Getting medical insurances plans - Offset: " + response.paging.offset + ", limit: " + response.paging.limit
            + ", total:" + response.paging.total);
        $scope.plans = response.items;
    }, function (error) {
        // error callback
        console.log("Error on retrieve medical insurances plans. Error: " + error);
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
    $scope.patient = {};

    $scope.patientId = -1;
    $scope.showCreatePatientModal = function () {
        $scope.patient = {};
        $('#addPatientModal').modal('show');
    };
    $scope.addPatient = function () {
        console.log("Creating new patient");
        var patient = $scope.patient;
        patient.date_of_birth = moment(patient.date_of_birth).format(API_DATETIME_FORMAT);
        Patient.save(patient, function (response) {
            //success callback
            console.log("New patient: " + response.id + " created");
            $scope.patientId = response.id;
            $scope.patient = {};
            $('#addPatientSuccessModal').modal('show');
        }, function (error) {
            // error callback
            console.log("Error on creating new patient. Error: " + error);
            $scope.patient = {};
        });
    };
    $scope.closeAddPatientSuccessModal = function(patientId) {
        $('#addPatientSuccessModal').modal('hide');
        if(patientId != null) {
            $window.location.href = '#/patients/' + patientId;
        } else {
            $window.location.reload();
        }
    };

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
            $window.location.reload();
        }, function (error) {
            console.log("Error on delete patient: " + patientId + ". Error: " + error);
            $scope.patient = {};
        });
    };

    //Edit
    $scope.showEditPatientModal = function (patient) {
        $scope.patient = {};
        angular.copy(patient, $scope.patient);
        $('#editPatientModal').modal('show');
    };
    $scope.savePatient = function () {
        var patient = $scope.patient;
        patient.date_of_birth = moment(patient.date_of_birth).format(API_DATETIME_FORMAT);
        patient.medical_insurance_plan_id = patient.medical_insurance_plan.id;
        console.log("Updating patient: " + patient.id);
        Patient.update(patient, function (response) {
            //success callback
            console.log("Updated patient: " + patient.id);
            $scope.patient = {};
            $window.location.reload();
        }, function (error) {
            // error callback
            console.log("Error on updating patient: " + patient.id + ". Error: " + error);
            $scope.patient = {};
        });
    };

    //Search & Filter
    $scope.clearSearch = function () {
        $scope.search = {};
    };
});

app.controller('PatientDetailsController', function ($scope, $route, Patient) {
    console.log("Current patient: " + $route.current.params.id);
    $scope.patient = Patient.get({id: $route.current.params.id});
});