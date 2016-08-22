const API_DATETIME_FORMAT = "YYYY-MM-DD";

app.controller('DoctorController', function ($scope, $window, Doctors, Doctor, DocumentTypes) {
    $scope.$on('$viewContentLoaded', function (event) {
        $('html, body').animate({scrollTop: $("#doctors").offset().top}, 1000);
    });

    $scope.doctors = [];
    Doctors.get({limit: 100, offset: 0}, function (response) {
        console.log("Getting doctors - Offset: " + response.paging.offset + ", limit: " + response.paging.limit + ", total:" + response.paging.total);
        $scope.doctors = response.items;
    }, function (error) {
        // error callback
        console.log("Error on retrieve doctors. Error: " + error);
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
    $scope.doctor = {};

    $scope.newDoctorId = -1;
    $scope.showCreateDoctorModal = function () {
        $scope.doctor = {};
        $('#addDoctorModal').modal('show');
    };
    $scope.addDoctor = function () {
        console.log("Creating new doctor");
        var doctor = $scope.doctor;
        doctor.date_of_birth = moment(doctor.date_of_birth).format(API_DATETIME_FORMAT);
        Doctor.save(doctor, function (response) {
            //success callback
            console.log("New doctor: " + response.id + " created");
            $scope.newDoctorId = response.id;
            $('#addDoctorSuccessModal').modal('show');
        }, function (error) {
            // error callback
            console.log("Error on creating new doctor. Error: " + error);
        });
        $scope.doctor = {};
    };

    //Delete
    $scope.showDeleteDoctorModal = function (doctor) {
        $scope.doctor = doctor;
        $('#deleteDoctorModal').modal('show');
    };
    $scope.deleteDoctor = function (doctorId) {
        console.log("Trying to delete doctor: " + doctorId);
        Doctor.delete({id: doctorId}, function (response) {
            console.log("Deleted doctor: " + doctorId);
        }, function (error) {
            console.log("Error on delete doctor: " + doctorId + ". Error: " + error);
        });
        $scope.doctor = {};
        $window.location.reload();
    };

    //Edit
    $scope.showEditDoctor = function (doctor) {
        $scope.doctor = {};
        angular.copy(doctor, $scope.doctor);
        $('#editDoctorModal').modal('show');
    };
    $scope.saveDoctor = function () {
        var doctor = $scope.doctor;
        console.log("Updating doctor: " + doctor.id);
        doctor.date_of_birth = moment(doctor.date_of_birth).format(API_DATETIME_FORMAT);
        Doctor.update(doctor, function (response) {
            //success callback
            console.log("Updated doctor: " + doctor.id);
            $('#editDoctorSuccessModal').modal('show');
        }, function (error) {
            // error callback
            console.log("Error on updating doctor: " + doctor.id + ". Error: " + error);
        });
        $scope.doctor = {};
        $window.location.reload();
    };

    $scope.clearSearch = function () {
        console.log("Clear search filter");
        $scope.search = null;
    }
});

app.controller('DoctorDetailsController', function ($scope, $stateParams, Doctor, DocumentTypes) {
    var currentId = $stateParams.id;

    console.log("Current doctor: " + currentId);
    $scope.doctor = Doctor.get($stateParams);
});