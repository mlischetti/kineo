var app = angular.module('kineo', ['ngRoute', 'ngResource']);


/** Start of Configurable constants **/
app.constant('context', '/kineo');
/** End of Configurable constants **/
app.config(function ($routeProvider) {
    $routeProvider
        .when("/appointments", {
            name: "appointments",
            templateUrl: "components/appointments/appointments.html",
            controller: "AppointmentController"
        })
        .when("/doctors", {
            name: "doctors",
            templateUrl: "components/doctors/doctors.html",
            controller: "DoctorController"
        })
        .when("/doctors/:id", {
            name: "doctorDetails",
            templateUrl: "components/doctors/doctor_details.html",
            controller: "DoctorDetailsController"
        })
        .when("/medical-insurances/companies", {
            name: "medical-companies",
            templateUrl: "components/medical_insurances/medical_companies.html",
            controller: "MedicalCompaniesController"
        })
        .when("/medical-insurances/plans", {
            name: "medical-insurances-plans",
            templateUrl: "components/medical_insurances/medical_insurances_plans.html",
            controller: "MedicalInsurancesPlansController"
        })
        .when("/medical-insurances/companies/:id", {
            name: "appointments",
            templateUrl: "components/medical_insurances/medical_company_details.html",
            controller: "MedicalCompanyDetailsController"
        })
        .when("/medical-insurances/plans/:id", {
            name: "appointments",
            templateUrl: "components/medical_insurances/medical_insurance_plan_details.html",
            controller: "MedicalInsurancePlanDetailsController"
        })
        .when("/patients", {
            name: "patients",
            templateUrl: "components/patients/patients.html",
            controller: "PatientController"
        })
        .when("/patients/:id", {
            name: "patientDetails",
            templateUrl: "components/patients/patient_details.html",
            controller: "PatientDetailsController"
        })
        .otherwise({redirectTo:'/appointments'});
});

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