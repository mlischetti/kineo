var app = angular.module('kineo', ['ui.router', 'ui.router.stateHelper', 'ngAnimate', 'ngResource']);


/** Start of Configurable constants **/
app.constant('context', '/kineo');
/** End of Configurable constants **/

app.config(['stateHelperProvider', '$urlRouterProvider', '$urlMatcherFactoryProvider', function (stateHelperProvider, $urlRouterProvider, $urlMatcherFactoryProvider) {

    $urlRouterProvider.otherwise("/");

    $urlMatcherFactoryProvider.strictMode(false)

    stateHelperProvider.state({
        name: "appointments",
        url: "/",
        templateUrl: "components/appointments/appointments.html",
        controller: "AppointmentController"
    }).state({
        name: "doctors",
        url: "/doctors",
        templateUrl: "components/doctors/doctors.html",
        controller: "DoctorController"
    }).state({
        name: "doctorDetails",
        url: "/doctors/:id",
        templateUrl: "components/doctors/doctor_details.html",
        controller: "DoctorDetailsController"
    }).state({
        name: "medical-insurances",
        url: "/medical-insurances",
        templateUrl: "components/medical_insurances/medical_insurances.html",
        controller: "MedicalInsurancesController"
    }).state({
        name: "medicalCompanyDetails",
        url: "/medical-insurances/companies/:id",
        templateUrl: "components/medical_insurances/medical_company_details.html",
        controller: "MedicalCompanyDetailsController"
    }).state({
        name: "medicalInsurancePlanDetails",
        url: "/medical-insurances/plans/:id",
        templateUrl: "components/medical_insurances/medical_insurance_plan_details.html",
        controller: "MedicalInsurancePlanDetailsController"
    }).state({
        name: "patients",
        url: "/patients",
        templateUrl: "components/patients/patients.html",
        controller: "PatientController"
    }).state({
        name: "patientDetails",
        url: "/patients/:id",
        templateUrl: "components/patients/patient_details.html",
        controller: "PatientDetailsController"
    });
}]);

/** Directives **/
app.directive('scrollToTarget', function () {
    return function (scope, element) {
        element.bind('click', function () {
            angular.element('html, body').stop().animate({
                scrollTop: angular.element(angular.element(element).attr('href')).offset().top - 20
            }, 1500);
            return false;
        });
    };
});
