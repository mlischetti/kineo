app.controller('AppointmentController', function ($scope, $rootScope, $state, context, Doctor, Patient, AppointmentSummaries) {
    $scope.context = context;
    $rootScope.$state = $state;

    $scope.doctors = [];
    Doctor.get({limit: 100, offset: 0}, function (response) {
        console.log("Getting doctors - Offset: " + response.paging.offset + ", limit: " + response.paging.limit + ", total:" + response.paging.total);
        $scope.doctors = response.items;
    }, function (error) {
        // error callback
        console.log("Error on retrieve doctors. Error: " + error);
    });

    $scope.patients = [];
    Patient.get({limit: 100, offset: 0}, function (response) {
        console.log("Getting patients - Offset: " + response.paging.offset + ", limit: " + response.paging.limit + ", total:" + response.paging.total);
        $scope.patients = response.items;
    }, function (error) {
        // error callback
        console.log("Error on retrieve patients. Error: " + error);
    });

    $scope.summaries = [];
    AppointmentSummaries.get({}, function (response) {
        console.log("Getting summaries");
        $scope.summaries = response;
    }, function (error) {
        // error callback
        console.log("Error on retrieve document types. Error: " + error);
    });
});

app.controller('AddAppointmentController', function ($scope, Appointment) {
    $scope.appointment = {};

    $scope.addAppointment = function () {
        console.log("Creating new appointment");
        var appointment = $scope.appointment;
        var now = new Date();
        appointment.start_time = moment.utc(now).format(API_DATETIME_FORMAT);
        Appointment.save(appointment, function (response) {
            //success callback
            console.log("New appointment: " + response.id + " created");
            $scope.appointment = {};
            $('#addAppointmentSuccessModal').modal('show');
        }, function (error) {
            // error callback
            console.log("Error on creating new appointment. Error: " + error);
        });
    };
});