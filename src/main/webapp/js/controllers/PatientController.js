app.controller('PatientsController', function ($scope, $window, Patient) {
    $scope.patients = [];
    $scope.patient = {};

    Patient.get({limit: 20, offset: 0}, function (response) {
        $scope.patients = response.items;
    }, function (error) {
        console.log("Error on retrieve patients. Error: " + error);
    });

    //Delete
    $scope.showDeletePatientModal = function (patient) {
        $scope.patient = patient;
        $('#deletePatientModal').modal('show');
    };
    $scope.deletePatient = function (patientId) {
        console.log("Trying to delete patient: " + patientId);
        Patient.delete({id: patientId}, function (response) {
            console.log("Deleted patient: " + patientId + ". Response: " + response);
            $scope.patient = {};
            toastr.success('Paciente exitosamente eliminado!');
            $window.location.href = '#/patients/';
        }, function (error) {
            console.log("Error on delete patient: " + patientId + ". Error: " + error);
            $scope.patient = {};
            toastr.error('Error al eliminar el paciente.', 'Error');
        });
    };

    //Search & Filter
    $scope.clearSearch = function () {
        $scope.search = {};
    };
});

app.controller('AddEditPatientController', function ($scope, $route, $window, Patient, MedicalInsurancePlan, DocumentTypes) {
    $scope.plans = [];
    $scope.documentTypes = [];
    $scope.patient = {
        id: null,
        last_name: null,
        first_name: null,
        date_of_birth: null,
        email: null,
        phone: null,
        document_type: null,
        document_number: null,
        affiliate_id: null,
        medical_insurance_plan_id: null
    };

    MedicalInsurancePlan.get({limit: 100, offset: 0}, function (response) {
        $scope.plans = response.items;
    }, function (error) {
        console.log("Error on retrieve medical insurances plans. Error: " + error);
    });

    DocumentTypes.get({}, function (response) {
        console.log("Getting document types");
        $scope.documentTypes = response;
    }, function (error) {
        console.log("Error on retrieve document types. Error: " + error);
    });

    $scope.mode = $route.current.mode;

    if($scope.mode == 'edit') {
        Patient.get({id: $route.current.params.id}, function(response) {
            $scope.patient.id = response.id;
            $scope.patient.last_name = response.last_name;
            $scope.patient.first_name = response.first_name;
            $scope.patient.date_of_birth = response.date_of_birth;
            $scope.patient.email = response.email;
            $scope.patient.phone = response.phone;
            $scope.patient.document_type = response.document_type;
            $scope.patient.document_number = response.document_number;
            if(typeof response.medical_insurance_plan !== 'undefined' && response.medical_insurance_plan !== null){
                $scope.patient.medical_insurance_plan_id = response.medical_insurance_plan.id;
            }
            $scope.patient.affiliate_id = response.affiliate_id;
        }, function(error) {
            console.log("Error on retrieve patient. Error: " + error);
        });
    }

    $scope.savePatient = function () {
        $scope.patient.date_of_birth = moment($scope.patient.date_of_birth).format(API_DATE_FORMAT);
        if($scope.mode == 'edit') {
            console.log("Updating patient: " + $scope.patient.id);
            Patient.update($scope.patient, function (response) {
                console.log("Updated patient: " + $scope.patient.id + ". Response:" + response);
                $scope.patient = {};
                toastr.success('Paciente exitosamente modificado!');
                $window.location.href = '#/patients/';
            }, function (error) {
                $scope.patient = {};
                console.log("Error on updating patient: " + $scope.patient.id + ". Error: " + error);
                toastr.error('Error al modificar el paciente.', 'Error');
            });
        } else {
            console.log("Creating patient");
            Patient.save($scope.patient, function (response) {
                console.log("New patient: " + response.id + " created");
                $scope.patient = {};
                toastr.success('Paciente exitosamente creado!');
                $window.location.href = '#/patients/';
            }, function (error) {
                $scope.patient = {};
                console.log("Error on creating new patient. Error: " + error);
                toastr.error('Error al crear el paciente.', 'Error');
            });
        }
    };
});

app.controller('ViewPatientController', function ($scope, $route, Patient) {
    $scope.patient = {};
    Patient.get({id: $route.current.params.id}, function(response) {
        $scope.patient = response;
    }, function(error) {
        console.log("Error on retrieve patient. Error: " + error);
    });
});