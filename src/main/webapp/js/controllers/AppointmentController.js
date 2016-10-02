const DATE_FORMAT = "DD/MM/YYYY";
const TIME_FORMAT = "HH:mm";

app.controller('AppointmentController', function ($scope, $window, Appointment) {
    const now = moment();

    $scope.appointments = [];
    $scope.appointment = {};

    $scope.searchRequest = {
        status: 'CONFIRM',
        since: now.format(DATE_FORMAT),
        until: now.add(7, 'day').format(DATE_FORMAT),
        professional: null,
        patient: null
    };

    $scope.search = function() {
        const params = {};
        params.status = $scope.searchRequest.status;
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

    $scope.showDeleteAppointmentModal = function (appointment) {
        $scope.appointment = appointment;
        $('#deleteAppointmentModal').modal('show');
    };
    $scope.deleteAppointment = function (appointmentId) {
        console.log("Trying to delete appointment: " + appointmentId);
        Appointment.delete({id: appointmentId}, function (response) {
            console.log("Deleted appointment: " + appointmentId + ". Response: " + response);
            $scope.appointment = {};
            toastr.success('Turno exitosamente eliminado!');
            $window.location.href = '#/appointments/';
        }, function (error) {
            console.log("Error on delete appointment: " + appointmentId + ". Error: " + error);
            $scope.appointment = {};
            toastr.error('Error al eliminar el turno.', 'Error');
        });
    };

    $scope.search();
});

app.controller('AddEditAppointmentController', function ($scope, $route, $window, Professional, Patient, AppointmentServices, Appointment) {
    const now = moment();

    $scope.professionals = [];
    $scope.patients = [];
    $scope.services = [];
    $scope.appointment = {
        id: null,
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

    $scope.mode = $route.current.mode;

    if($scope.mode == 'edit') {
        Appointment.get({id: $route.current.params.id}, function(response) {
            const startTime = moment(response.start_time);
            $scope.appointment.id = response.id;
            $scope.appointment.date = startTime.format(DATE_FORMAT);
            $scope.appointment.time = startTime.format(TIME_FORMAT);
            $scope.appointment.service = response.service;
            $scope.appointment.professional_id = response.professional.id;
            $scope.appointment.patient_id = response.patient.id;

            $scope.$broadcast('angucomplete-alt:changeInput', 'professional', response.professional);
            $scope.$broadcast('angucomplete-alt:changeInput', 'patient', response.patient);
        }, function(error) {
            console.log("Error on retrieve appointment. Error: " + error);
        });
    }

    $('#clockpicker').clockpicker({'default': $scope.appointment.time, 'autoclose': true});

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
        if($scope.mode == 'edit') {
            console.log("Updating appointment: " + $scope.appointment.id);
            Appointment.update($scope.appointment, function (response) {
                console.log("Updated appointment: " + $scope.appointment.id + ". Response:" + response);
                $scope.appointment = {};
                toastr.success('Turno exitosamente modificado!');
                $window.location.href = '#/appointments/';
            }, function (error) {
                $scope.appointment = {};
                console.log("Error on updating appointment: " + $scope.appointment.id + ". Error: " + error);
                toastr.error('Error al modificar el turno.', 'Error');
            });
        } else {
            console.log("Creating new appointment");
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
        }
    };
});