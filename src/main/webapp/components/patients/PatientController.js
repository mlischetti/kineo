var PatientController = ['$scope', '$window', '$state', 'Patients', 'Patient', function ($scope, $window, $state, Patients, Patient) {
    $scope.$on('$viewContentLoaded', function (event) {
        $('html, body').animate({scrollTop: $("#patients").offset().top}, 1000);
    });

    $scope.patients = [];

    Patients.get({limit: 20, offset: 0}, function(response) {
        console.log("Getting patients - Offset: " + response.paging.offset + ", limit: "
            + response.paging.limit + ", total:" + response.paging.total);
        $scope.patients = response.items;
    }, function(error){
        // error callback
        console.log("Error on retrieve patients. Error: " + error);
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

var PatientDetailsController = ['$scope', '$rootScope', '$stateParams', 'Patient', function ($scope, $rootScope, $stateParams, Patient) {
    var currentId = $stateParams.id;
    console.log("Current patient: " + currentId);
    $scope.currentPatient = Patient.get($stateParams);

    $scope.savePatient = function () {
        var patient = $scope.currentPatient;
        console.log("Updating patient: " + patient.id);
        Doctor.update(doctor, function (response) {
            //success callback
            console.log("Updated patient: " + patient.id);
            $('#editPatientSuccessModal').modal('show');
        }, function(error) {
            // error callback
            console.log("Error on updating patient: " + patient.id + ". Error: " + error);
        });
    };
}];

var AddDoctorController = ['$scope', '$rootScope', '$stateParams', 'Doctor', function ($scope, $rootScope, $stateParams, Doctor) {
    $scope.doctor = {};
    $scope.newDoctorId = -1;

    $scope.addDoctor = function () {
        console.log("Creating new doctor");
        Doctor.save($scope.doctor, function(response){
            //success callback
            console.log("New doctor: " + response.id + " created");
            $scope.newDoctorId = response.id;
            $scope.doctor = {};
            $('#addDoctorSuccessModal').modal('show');
        }, function(error) {
            // error callback
            console.log("Error on creating new doctor. Error: " + error);
        });
    };
}];