var DoctorController = ['$scope', '$window', '$state', 'Doctors', 'Doctor', function ($scope, $window, $state, Doctors, Doctor) {

    $scope.$on('$viewContentLoaded', function (event) {
        $('html, body').animate({scrollTop: $("#doctors").offset().top}, 1000);
    });

    $scope.doctors = [];

    Doctors.get({limit: 10, offset: 0}, function(response) {
        console.log("Getting doctors - Offset: " + response.paging.offset + ", limit: "  + response.paging.limit + ", total:" + response.paging.total);
        $scope.doctors = response.items;
    }, function(error){
        // error callback
        console.log("Error on retrieve doctors. Error: " + error);
    });

    $scope.deleteDoctor = function (doctorId) {
        console.log("Trying to delete doctor: " + doctorId);
        var doctors = $scope.doctors;
        Doctor.delete({id:doctorId}, function(response) {
            console.log("Delete doctor: " + doctorId);
            $window.location.reload();
        }, function(error) {
            console.log("Error on delete doctor: " + doctorId + ". Error: " + error);
        });
    };

    $scope.clearSearch = function() {
        console.log("Clear search filter");
        $scope.search = null;
    }
}];

var DoctorDetailsController = ['$scope', '$rootScope', '$stateParams', 'Doctor', function ($scope, $rootScope, $stateParams, Doctor) {
    var currentId = $stateParams.id;
    $scope.currentDoctor = Doctor.get($stateParams);

    $scope.saveDoctor = function () {
        var doctor = $scope.currentDoctor;
        console.log("Updating doctor: " + doctor.id);
        Doctor.update(doctor, function (response) {
            //success callback
            console.log("Updated doctor: " + doctor.id);
            $('#editDoctorSuccessModal').modal('show');
        }, function(error) {
            // error callback
            console.log("Error on updating doctor" + doctor.id + ". Error: " + error);
        });
    };
}];

var AddDoctorController = ['$scope', '$rootScope', '$stateParams', '$timeout', 'Doctor', function ($scope, $rootScope, $stateParams, $timeout, Doctor) {
    $scope.doctor = {};
    $scope.newDoctorId = -1;

    $scope.addDoctor = function () {
        console.log("Creating new doctor");
        Doctor.save($scope.doctor, function(response){
            //success callback
            console.log("New doctor: " + response.id + " created");
            $scope.newDoctorId = response.id;
            $('#addDoctorSuccessModal').modal('show');
        }, function(error) {
            // error callback
            console.log("Error on creating new doctor. Error: " + error);
        });
    };
}];