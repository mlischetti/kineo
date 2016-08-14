var app = angular.module('kineo', ['ui.router', 'ui.router.stateHelper', 'ngAnimate', 'ngCookies', 'ngResource', 'ngStorage']);


/** Start of Configurable constants **/
app.constant('context', '/kineo');
/** End of Configurable constants **/

app.config(['stateHelperProvider', '$urlRouterProvider', '$urlMatcherFactoryProvider', function (stateHelperProvider, $urlRouterProvider, $urlMatcherFactoryProvider) {

    $urlRouterProvider.otherwise("/");

    $urlMatcherFactoryProvider.strictMode(false)

    stateHelperProvider.state({
        name: "calendars",
        url: "/",
        templateUrl: "components/calendars/calendars.html",
        controller: "MainController"
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

/** Controllers **/
app.controller('MainController', MainController);
app.controller('DoctorController', DoctorController);
app.controller('DoctorDetailsController', DoctorDetailsController);
app.controller('AddDoctorController', AddDoctorController);
app.controller('MedicalInsurancesController', MedicalInsurancesController);
app.controller('MedicalCompanyDetailsController', MedicalCompanyDetailsController);
app.controller('AddMedicalCompanyController', AddMedicalCompanyController);
app.controller('MedicalInsurancePlanDetailsController', MedicalInsurancePlanDetailsController);
app.controller('AddMedicalInsurancePlanController', AddMedicalInsurancePlanController);
app.controller('PatientController', PatientController);
app.controller('PatientDetailsController', PatientDetailsController);
app.controller('AddPatientController', AddPatientController);
/*
 app.controller('DashboardController', DashboardController);
 app.controller('SearchController', SearchController);*/

/** Services **/
app.factory('Doctors', Doctors);
app.factory('Doctor', Doctor);
app.factory('MedicalCompanies', MedicalCompanies);
app.factory('MedicalCompany', MedicalCompany);
app.factory('MedicalInsurancePlans', MedicalInsurancePlans);
app.factory('MedicalInsurancePlan', MedicalInsurancePlan);
app.factory('Patients', Patients);
app.factory('Patient', Patient);
app.factory('DocumentTypes', DocumentTypes);

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
app.directive('datePicker', DatePickerDirective);
app.directive('multiSelectDate', MultiSelectDateDirective);