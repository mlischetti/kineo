var DoctorController = ['$scope', '$window', '$state', 'Doctors', 'Doctor', 'DocumentTypes',
    function ($scope, $window, $state, Doctors, Doctor, DocumentTypes) {
        $scope.$on('$viewContentLoaded', function (event) {
            $('html, body').animate({scrollTop: $("#doctors").offset().top}, 1000);
        });

        $scope.doctors = [];
        Doctors.get({limit: 100, offset: 0}, function(response) {
            console.log("Getting doctors - Offset: " + response.paging.offset + ", limit: "  + response.paging.limit + ", total:" + response.paging.total);
            $scope.doctors = response.items;
        }, function(error){
            // error callback
            console.log("Error on retrieve doctors. Error: " + error);
        });

        $scope.documentTypes = [];
        DocumentTypes.get({}, function(response) {
            console.log("Getting document types");
            $scope.documentTypes = response;
        }, function(error){
            // error callback
            console.log("Error on retrieve document types. Error: " + error);
        });

        $scope.deleteDoctor = function (doctorId) {
            console.log("Trying to delete doctor: " + doctorId);
            Doctor.delete({id:doctorId}, function(response) {
                console.log("Deleted doctor: " + doctorId);
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

var DoctorDetailsController = ['$scope', '$rootScope', '$stateParams', 'Doctor', 'DocumentTypes',
    function ($scope, $rootScope, $stateParams, Doctor, DocumentTypes) {
        var currentId = $stateParams.id;

        console.log("Current doctor: " + currentId);
        $scope.currentDoctor = Doctor.get($stateParams);

        $scope.documentTypes = [];
        DocumentTypes.get({}, function(response) {
            console.log("Getting document types");
            $scope.documentTypes = response;
        }, function(error){
            // error callback
            console.log("Error on retrieve document types. Error: " + error);
        });

        $scope.saveDoctor = function () {
            var doctor = $scope.currentDoctor;
            console.log("Updating doctor: " + doctor.id);
            Doctor.update(doctor, function (response) {
                //success callback
                console.log("Updated doctor: " + doctor.id);
                $('#editDoctorSuccessModal').modal('show');
            }, function(error) {
                // error callback
                console.log("Error on updating doctor: " + doctor.id + ". Error: " + error);
            });
        };
    }];

var AddDoctorController = ['$scope', '$rootScope', '$stateParams', 'Doctor',
    function ($scope, $rootScope, $stateParams, Doctor) {
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