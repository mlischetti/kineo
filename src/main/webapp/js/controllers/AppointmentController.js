app.controller('AppointmentController', function ($scope, Appointment) {
    const DATE_FORMAT = "DD/MM/YYYY";
    var now = moment().utcOffset("-03:00");

    $scope.since = now.format(DATE_FORMAT);
    $scope.until = now.format(DATE_FORMAT);
    $scope.search = function() {
        console.log('since:' + $scope.since);
        console.log('until:' + $scope.until);
    };
    $scope.appointments = [];


});

app.controller('AddAppointmentController', function ($scope, $window, Professional, Patient, AppointmentSummaries, Appointment) {
    const DATE_FORMAT = "DD/MM/YYYY";
    const TIME_FORMAT = "HH:mm";

    $scope.professionals = [];
    Professional.get({limit: 100, offset: 0}, function (response) {
        console.log("Getting professionals - Offset: " + response.paging.offset + ", limit: " + response.paging.limit + ", total:" + response.paging.total);
        $scope.professionals = response.items;
    }, function (error) {
        // error callback
        console.log("Error on retrieve professionals. Error: " + error);
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

    //$scope.$broadcast('angucomplete-alt:clearInput');
    var now = moment().utcOffset("-03:00");

    $scope.date_string = now.format(DATE_FORMAT);

    $('#datepicker').datepicker({
        format: 'dd/mm/yyyy',
        todayHighlight: true,
        todayBtn: 'linked',
        autoclose: true,
        keyboardNavigation:true
    });

    $('#datepicker').datepicker('update', $scope.date_string);

    $scope.time_string = now.format(TIME_FORMAT);
    $('#clockpicker').clockpicker({
        'default': 'now',
        'autoclose': true
    });

    $scope.appointment = {};

    $scope.selectedProfessional = function (selected) {
        if (selected) {
            console.log("Selected professional_id: " + selected.originalObject.id);
            $scope.appointment.professional_id = selected.originalObject.id;
        } else {
            console.log('cleared professional_id');
        }
    };

    $scope.selectedPatient = function (selected) {
        if (selected) {
            console.log("Selected patient_id: " + selected.originalObject.id);
            $scope.appointment.patient_id = selected.originalObject.id;
        } else {
            console.log('cleared patient_id');
        }
    };

    $scope.saveAppointment = function () {
        console.log("Creating new appointment");
        var appointment = $scope.appointment;
        var now = new Date();
        appointment.start_time = moment.utc(now).format(API_DATETIME_FORMAT);
        Appointment.save(appointment, function (response) {
            console.log("New appointment: " + response.id + " created");
            $scope.appointment = {};
            toastr.success('Turno exitosamente creado!');
            $window.location.href = '#/appointments/';
        }, function (error) {
            console.log("Error on creating new appointment. Error: " + error);
            $scope.appointment = {};
            toastr.error('Error al crear el turno.', 'Error');
        });
    };
});