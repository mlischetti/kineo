const DATE_FORMAT = "DD/MM/YYYY";
const TIME_FORMAT = "HH:mm";

app.controller('AppointmentController', function ($scope, Appointment) {
    const now = moment();

    $scope.appointments = [];

    $scope.searchRequest = {
        since: now.format(DATE_FORMAT),
        until: now.add(1, 'day').format(DATE_FORMAT),
        professional: null,
        patient: null
    };

    $scope.search = function() {
        var params = {};
        if(isNotEmpty($scope.searchRequest.since)) {
            params.since = moment($scope.searchRequest.since, DATE_FORMAT).format(API_DATE_FORMAT);
        }
        if(isNotEmpty($scope.searchRequest.until)) {
            params.until = moment($scope.searchRequest.until, DATE_FORMAT).format(API_DATE_FORMAT);
        }
        if(isNotEmpty($scope.searchRequest.professional)) {
            params.professional = $scope.searchRequest.professional;
        }
        if(isNotEmpty($scope.searchRequest.patient)) {
            params.patient = $scope.searchRequest.patient;
        }
        Appointment.get(params, function (response) {
            $scope.appointments = response.items;
        }, function (error) {
            console.log("Error on getting appointments. Error: " + error);
            toastr.error("Error al buscar calendario", 'Error');
        });
    };

    function isNotEmpty(variable) {
        return (typeof variable !== 'undefined') && variable !== null;
    }
});

app.controller('AddAppointmentController', function ($scope, $window, Professional, Patient, AppointmentServices, Appointment) {
    const now = moment();

    $scope.professionals = [];
    $scope.patients = [];
    $scope.services = [];
    $scope.appointment = {
        start_time: null,
        date: now.format(DATE_FORMAT),
        time: now.format(TIME_FORMAT),
        service: null,
        professional_id: null,
        patient_id: null
    };

    Professional.get({limit: 100, offset: 0}, function (response) {
        $scope.professionals = response.items;
    }, function (error) {
        console.log("Error on retrieve professionals. Error: " + error);
    });

    Patient.get({limit: 100, offset: 0}, function (response) {
        $scope.patients = response.items;
    }, function (error) {
        console.log("Error on retrieve patients. Error: " + error);
    });


    AppointmentServices.get({}, function (response) {
        $scope.services = response;
    }, function (error) {
        console.log("Error on retrieve services. Error: " + error);
    });

    $('#clockpicker').clockpicker({'default': 'now', 'autoclose': true});

    $scope.selectedProfessional = function (selected) {
        if (selected) {
            $scope.appointment.professional_id = selected.originalObject.id;
        } else {
            $scope.appointment.professional_id = null;
        }
    };

    $scope.selectedPatient = function (selected) {
        if (selected) {
            $scope.appointment.patient_id = selected.originalObject.id;
        } else {
            $scope.appointment.patient_id = null;
        }
    };

    $scope.saveAppointment = function () {
        $scope.appointment.start_time =  moment($scope.appointment.date + ' ' + $scope.appointment.time, DATE_FORMAT + ' ' + TIME_FORMAT).format();
        Appointment.save($scope.appointment, function (response) {
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