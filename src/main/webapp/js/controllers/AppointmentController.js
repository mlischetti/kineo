app.controller('AppointmentController', function ($scope, Doctor, Patient, AppointmentSummaries, Appointment) {
    const DATE_FORMAT = "DD/MM/YYYY";
    const TIME_FORMAT = "HH:mm";

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

    $scope.appointment = {};
    $scope.time_string = '';

    $scope.showAddAppointmentModal = function() {
        $scope.appointment = {};
        var now = moment().utcOffset("-03:00");

        $scope.date_string = now.format(DATE_FORMAT);
        $('#datepicker').datepicker('update', $scope.date_string);

        $scope.time_string = now.format(TIME_FORMAT);
        console.log("$scope.time_string: " + $scope.time_string);
        //$('#clockpicker').clockpicker({
        //    'default': 'now'
        //});
        //ver:https://eonasdan.github.io/bootstrap-datetimepicker/
        $('#addAppointmentModal').modal('show');
    };

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
            $scope.appointment = {};
        });
    };
    $scope.closeAppointmentsSuccessModal = function () {
        $('#addAppointmentSuccessModal').modal('hide');
    };
});