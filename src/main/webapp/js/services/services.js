var Doctors = ['$resource', 'context', function ($resource, context) {
    return $resource(context + '/api/doctors', {}, {
        query: {method: 'GET', params: {limit: 'limit', offset: 'offset'}, isArray: false}
    });
}];

var Doctor = ['$resource', 'context', function ($resource, context) {
    return $resource(context + '/api/doctors/:id', {id: '@id'},
        {
            'save': {method: 'POST', params: {}, format: 'json', isArray: false},
            'get': {method: 'GET', params: {}, format: 'json', isArray: false},
            'update': {method: 'PUT', params: {id: '@id'}, format: 'json', isArray: false},
            'delete': {method: 'DELETE', params: {id: '@id'}, format: 'json', isArray: false}
        });
}];

var MedicalCompanies = ['$resource', 'context', function ($resource, context) {
    return $resource(context + '/api/medical-insurances/companies', {}, {
        query: {method: 'GET', params: {limit: 'limit', offset: 'offset'}, isArray: false}
    });
}];

var MedicalCompany = ['$resource', 'context', function($resource, context) {
   return $resource (context + '/api/medical-insurances/companies/:id', {id : '@id'},
       {
           'save': {method: 'POST', params: {}, format: 'json', isArray: false},
           'get': {method: 'GET', params: {}, format: 'json', isArray: false},
           'update': {method: 'PUT', params: {id: '@id'}, format: 'json', isArray: false},
           'delete': {method: 'DELETE', params: {id: '@id'}, format: 'json', isArray: false}
       });
}];

var MedicalInsurancePlans = ['$resource', 'context', function ($resource, context) {
    return $resource(context + '/api/medical-insurances/plans', {}, {
        query: {method: 'GET', params: {limit: 'limit', offset: 'offset'}, isArray: false}
    });
}];

var MedicalInsurancePlan = ['$resource', 'context', function($resource, context) {
    return $resource (context + '/api/medical-insurances/plans/:id', {id : '@id'},
        {
            'save': {method: 'POST', params: {}, format: 'json', isArray: false},
            'get': {method: 'GET', params: {}, format: 'json', isArray: false},
            'update': {method: 'PUT', params: {id: '@id'}, format: 'json', isArray: false},
            'delete': {method: 'DELETE', params: {id: '@id'}, format: 'json', isArray: false}
        });
}];

var Patients = ['$resource', 'context', function ($resource, context) {
    return $resource(context + '/api/patients', {}, {
        query: {method: 'GET', params: {limit: 'limit', offset: 'offset'}, isArray: false}
    });
}];

var Patient = ['$resource', 'context', function ($resource, context) {
    return $resource(context + '/api/patients/:id', {id: '@id'},
        {
            'save': {method: 'POST', params: {}, format: 'json', isArray: false},
            'get': {method: 'GET', params: {}, format: 'json', isArray: false},
            'update': {method: 'PUT', params: {id: '@id'}, format: 'json', isArray: false},
            'delete': {method: 'DELETE', params: {id: '@id'}, format: 'json', isArray: false}
        });
}];

//Catalogs
var DocumentTypes = ['$resource', 'context', function ($resource, context) {
    return $resource(context + '/api/catalogs/document-types', {}, {
        'get': {method: 'GET', params: {}, isArray: true}
    });
}];

/** Services **/
app.factory('Doctors', Doctors);
app.factory('Doctors', Doctors);
app.factory('Doctor', Doctor);
app.factory('MedicalCompanies', MedicalCompanies);
app.factory('MedicalCompany', MedicalCompany);
app.factory('MedicalInsurancePlans', MedicalInsurancePlans);
app.factory('MedicalInsurancePlan', MedicalInsurancePlan);
app.factory('Patients', Patients);
app.factory('Patient', Patient);
app.factory('DocumentTypes', DocumentTypes);