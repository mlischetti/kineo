var PatientController = ['$scope', '$window', '$state', 'Patients', 'Patient', 'MedicalInsurancePlans',
    function ($scope, $window, $state, Patients, Patient, MedicalInsurancePlans) {
    $scope.$on('$viewContentLoaded', function (event) {
        $('html, body').animate({scrollTop: $("#patients").offset().top}, 1000);
    });

    $scope.plans = [];
    $scope.patients = [];

    Patients.get({limit: 20, offset: 0}, function(response) {
        console.log("Getting patients - Offset: " + response.paging.offset + ", limit: "
            + response.paging.limit + ", total:" + response.paging.total);
        $scope.patients = response.items;
    }, function(error){
        // error callback
        console.log("Error on retrieve patients. Error: " + error);
    });

    MedicalInsurancePlans.get({limit: 100, offset: 0}, function(response) {
        console.log("Getting medical insurances plans - Offset: " + response.paging.offset + ", limit: "  + response.paging.limit
            + ", total:" + response.paging.total);
        $scope.plans = response.items;
    }, function(error){
        // error callback
        console.log("Error on retrieve medical insurances plans. Error: " + error);
    });

    $scope.deletePatient = function (patientId) {
        console.log("Trying to delete patient: " + patientId);
        Patient.delete({id:patientId}, function(response) {
            console.log("Deleted patient: " + patientId);
            $window.location.reload();
        }, function(error) {
            console.log("Error on delete patient: " + patientId + ". Error: " + error);
        });
    };

    $scope.clearSearch = function() {
        console.log("Clear search filter");
        $scope.search = null;
    }
}];

var PatientDetailsController = ['$scope', '$rootScope', '$stateParams', 'Patient', 'MedicalInsurancePlans',
    function ($scope, $rootScope, $stateParams, Patient, MedicalInsurancePlans) {
    var currentId = $stateParams.id;

    $scope.plans = [];

    MedicalInsurancePlans.get({limit: 100, offset: 0}, function(response) {
        console.log("Getting medical insurances plans - Offset: " + response.paging.offset + ", limit: "  + response.paging.limit
            + ", total:" + response.paging.total);
        $scope.plans = response.items;
    }, function(error){
        // error callback
        console.log("Error on retrieve medical insurances plans. Error: " + error);
    });

    console.log("Current patient: " + currentId);

    $scope.currentPatient = Patient.get($stateParams);

    $scope.savePatient = function () {
        var patientToCreate = createPatient($scope.currentPatient);
        console.log("Updating patient: " + patientToCreate.id);
        Patient.update(patientToCreate, function (response) {
            //success callback
            console.log("Updated patient: " + patientToCreate.id);
            $('#editPatientSuccessModal').modal('show');
        }, function(error) {
            // error callback
            console.log("Error on updating patient: " + patientToCreate.id + ". Error: " + error);
        });
    };

    function createPatient(currentPatient) {
        var patient = new Object();
        patient.id = currentPatient.id;
        patient.first_name = currentPatient.first_name;
        patient.last_name = currentPatient.first_name;
        patient.email = currentPatient.email;
        patient.phone = currentPatient.phone;
        patient.medical_insurance_plan_id = currentPatient.medical_insurance_plan.id;
        patient.affiliate_id = currentPatient.affiliate_id;
        return patient;
    }
}];

var AddPatientController = ['$scope', '$rootScope', '$stateParams', 'Patient', function ($scope, $rootScope, $stateParams, Patient) {
    $scope.patient = {};
    $scope.newPatientId = -1;

    $scope.addPatient = function () {
        console.log("Creating new patient");
        Patient.save($scope.patient, function(response){
            //success callback
            console.log("New patient: " + response.id + " created");
            $scope.newPatientId = response.id;
            $scope.patient = {};
            $('#addPatientSuccessModal').modal('show');
        }, function(error) {
            // error callback
            console.log("Error on creating new patient. Error: " + error);
        });
    };
}];