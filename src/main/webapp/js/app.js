var app = angular.module('kineo', ['ngRoute', 'ngResource', 'angucomplete-alt']);

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
        .when("/professionals", {
            name: "professionals",
            templateUrl: "components/professionals/professionals.html",
            controller: "ProfessionalController"
        })
        .when("/professionals/:id", {
            name: "professionalDetails",
            templateUrl: "components/professionals/professional_details.html",
            controller: "ProfessionalDetailsController"
        })
        .when("/medical-insurances/companies", {
            name: "medical-companies",
            state: 'medical-companies',
            templateUrl: "components/medical_insurances/medical_companies.html",
            controller: "MedicalCompaniesController"
        })
        .when("/medical-insurances/company-new", {
            name: "medical-company-new",
            state: 'medical-companies',
            mode: "add",
            templateUrl: "components/medical_insurances/add_edit_medical_company.html",
            controller: "AddEditMedicalCompanyController"
        })
        .when("/medical-insurances/company-edit/:id", {
            name: "medical-company-edit",
            state: 'medical-companies',
            mode: "edit",
            templateUrl: "components/medical_insurances/add_edit_medical_company.html",
            controller: "AddEditMedicalCompanyController"
        })
        .when("/medical-insurances/companies/:id", {
            name: "medical-company-view",
            state: 'medical-companies',
            templateUrl: "components/medical_insurances/medical_company.html",
            controller: "ViewMedicalCompanyController"
        })
        .when("/medical-insurances/plans", {
            name: "medical-plans",
            state: 'medical-plans',
            templateUrl: "components/medical_insurances/medical_insurances_plans.html",
            controller: "MedicalInsurancesPlansController"
        })
        .when("/medical-insurances/plan-new", {
            name: "medical-plan-new",
            state: 'medical-plans',
            mode: "add",
            templateUrl: "components/medical_insurances/add_edit_medical_insurance_plan.html",
            controller: "AddEditMedicalInsurancePlanController"
        })
        .when("/medical-insurances/plan-edit/:id", {
            name: "medical-plan-edit",
            state: 'medical-plans',
            mode: "edit",
            templateUrl: "components/medical_insurances/add_edit_medical_insurance_plan.html",
            controller: "AddEditMedicalInsurancePlanController"
        })
        .when("/medical-insurances/plans/:id", {
            name: "medical-plan-view",
            state: 'medical-plans',
            templateUrl: "components/medical_insurances/medical_insurance_plan.html",
            controller: "ViewMedicalInsurancePlanController"
        })
        .when("/patients", {
            name: "patients",
            state: 'patients',
            templateUrl: "components/patients/patients.html",
            controller: "PatientsController"
        })
        .when("/patient-new", {
            name: "patient-new",
            state: 'patients',
            mode: "add",
            templateUrl: "components/patients/add_edit_patient.html",
            controller: "AddEditPatientController"
        })
        .when("/patient-edit/:id", {
            name: "patient-edit",
            state: 'patients',
            mode: "edit",
            templateUrl: "components/patients/add_edit_patient.html",
            controller: "AddEditPatientController"
        })
        .when("/patients/:id", {
            name: "patient-view",
            state: 'patients',
            templateUrl: "components/patients/patient.html",
            controller: "ViewPatientController"
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

app.filter('capitalize', function() {
    return function(input) {
        return (!!input) ? input.charAt(0).toUpperCase() + input.substr(1).toLowerCase() : '';
    }
});