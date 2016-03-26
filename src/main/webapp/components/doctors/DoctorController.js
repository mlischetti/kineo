var DoctorController = ['$scope', '$state', 'Doctors', function ($scope, $state, Doctors) {
    $scope.$on('$viewContentLoaded', function (event) {
        $('html, body').animate({
            scrollTop: $("#doctors").offset().top
        }, 1000);
    });

    $scope.doctors = Doctors.get({limit: 10, offset: 0});
    $scope.doctors.$promise.then(function (result) {
        $scope.doctors = result.items;
    });
    $scope.newDoctorId = 4;
}];

var DoctorDetailsController = ['$scope', '$rootScope', '$stateParams', 'Doctor', function ($scope, $rootScope, $stateParams, Doctor) {

    var currentId = $stateParams.id;
    var nextId = parseInt($stateParams.id) + 1;
    var prevId = parseInt($stateParams.id) - 1;

    Doctor.get({id: prevId}, function (result) {
        $scope.prevDoctor = result;
    });
    Doctor.get({id: nextId}, function (result) {
        $scope.nextDoctor = result;
    });

    $scope.currentDoctor = Doctor.get($stateParams);

    $scope.saveDoctor = function () {
        var doctor = $scope.currentDoctor;
        Doctor.update(doctor).$promise.then(function (resp) {
            //success callback
            console.log("updatedDoctorId: " + resp.id);
        }, function(error) {
            // error callback
            console.log("Error on saveDoctor: " + doctor.id);
        });
    }
}];

var AddDoctorController = ['$scope', '$rootScope', '$stateParams', '$timeout', 'Doctor', function ($scope, $rootScope, $stateParams, $timeout, Doctor) {
    $scope.doctor = {};
    $scope.newDoctorId = -1;
    $scope.message = "Initial";

    $scope.addDoctor = function () {
        $scope.message = "on AddDoctor";
        Doctor.save($scope.doctor).$promise.then(function (response) {
            $scope.message = "on Success callback";
            //success callback
            console.log("newDoctorId: " + response.id);
            //$scope.newDoctorId = response.id;
            $timeout(function() {
                $scope.newDoctorId = response.id;
                $scope.message = "on timeout";
                console.log("$scope.newDoctorId: " + $scope.newDoctorId);
            });
            //$scope.$apply(function() {
            //    $scope.newDoctorId = response.id;
            //    console.log("$scope.newDoctorId: " + $scope.newDoctorId);
            //});
        }, function (error) {
            // error callback
            console.log(error);
        });

    };

    $scope.addCounter = 0;

    $scope.incrementCounter = function() {
        $scope.addCounter = $scope.addCounter + 1;
    };
}];