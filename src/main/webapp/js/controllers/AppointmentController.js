const DATE_FORMAT = "DD/MM/YYYY";
app.controller('AppointmentController', function ($scope, Appointment, Professional) {

    $scope.professionals = [];
    Professional.get({limit: 100, offset: 0}, function (response) {
        console.log("Getting professionals - Offset: " + response.paging.offset + ", limit: " + response.paging.limit + ", total:" + response.paging.total);
        $scope.professionals = response.items;
    }, function (error) {
        console.log("Error on retrieve professionals. Error: " + error);
    });

    var now = moment().utcOffset("-03:00");

    $scope.searchRequest = {};
    $scope.searchRequest.since = now.format(DATE_FORMAT);
    $scope.searchRequest.until = now.add(1, 'day').format(DATE_FORMAT);
    $scope.appointments = [];

    $scope.selectedProfessional = function (selected) {
        if (selected) {
            console.log("Selected professional_id: " + selected.originalObject.id);
            $scope.searchRequest.professionalId = selected.originalObject.id;
        } else {
            console.log('cleared professional_id');
            $scope.searchRequest.professionalId = null;
        }
    };

    $scope.search = function() {
        var params = new Object();
        if(isNotEmpty($scope.searchRequest.since)) {
            params.since = moment($scope.searchRequest.since, DATE_FORMAT).format(API_DATETIME_FORMAT);
        }
        if(isNotEmpty($scope.searchRequest.until)) {
            params.until = moment($scope.searchRequest.until, DATE_FORMAT).format(API_DATETIME_FORMAT);
        }
        if(isNotEmpty($scope.searchRequest.professionalId)) {
            params.professionalId = $scope.searchRequest.professionalId;
        }
        if(isNotEmpty($scope.searchRequest.patient)) {
            params.patient = $scope.searchRequest.patient;
        }
        Appointment.get(params, function (response) {
            $scope.appointments = response.items;
        }, function (error) {
            console.log("Error on retrieve appointments. Error: " + error);
        });
    };

    function isNotEmpty(variable) {
        return (typeof variable !== 'undefined') && variable !== null;
    };
});

app.controller('AddAppointmentController', function ($scope, $window, Professional, Patient, AppointmentServices, Appointment) {
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

    $scope.services = [];
    AppointmentServices.get({}, function (response) {
        console.log("Getting services");
        $scope.services = response;
    }, function (error) {
        // error callback
        console.log("Error on retrieve services. Error: " + error);
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
            $scope.appointment.professional_id = null;
            console.log('cleared professional_id');
        }
    };

    $scope.selectedPatient = function (selected) {
        if (selected) {
            console.log("Selected patient_id: " + selected.originalObject.id);
            $scope.appointment.patient_id = selected.originalObject.id;
        } else {
            $scope.appointment.patient_id = null;
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