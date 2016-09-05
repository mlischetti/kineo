var Professional = ['$resource', 'context', function ($resource, context) {
    return $resource(context + '/api/professionals/:id', {id: '@id'},
        {
            'save': {method: 'POST', params: {}, format: 'json', isArray: false},
            'get': {method: 'GET', params: {}, format: 'json', isArray: false},
            'update': {method: 'PUT', params: {id: '@id'}, format: 'json', isArray: false},
            'delete': {method: 'DELETE', params: {id: '@id'}, format: 'json', isArray: false}
        });
}];

var MedicalCompany = ['$resource', 'context', function ($resource, context) {
    return $resource(context + '/api/medical-insurances/companies/:id', {id: '@id'},
        {
            'save': {method: 'POST', params: {}, format: 'json', isArray: false},
            'get': {method: 'GET', params: {}, format: 'json', isArray: false},
            'update': {method: 'PUT', params: {id: '@id'}, format: 'json', isArray: false},
            'delete': {method: 'DELETE', params: {id: '@id'}, format: 'json', isArray: false}
        });
}];

var MedicalInsurancePlan = ['$resource', 'context', function ($resource, context) {
    return $resource(context + '/api/medical-insurances/plans/:id', {id: '@id'},
        {
            'save': {method: 'POST', params: {}, format: 'json', isArray: false},
            'get': {method: 'GET', params: {}, format: 'json', isArray: false},
            'update': {method: 'PUT', params: {id: '@id'}, format: 'json', isArray: false},
            'delete': {method: 'DELETE', params: {id: '@id'}, format: 'json', isArray: false}
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

var Appointment = ['$resource', 'context', function ($resource, context) {
    return $resource(context + '/api/appointments/:id', {id: '@id'},
        {
            'save': {method: 'POST', params: {}, format: 'json', isArray: false},
            'update': {method: 'PUT', params: {id: '@id'}, format: 'json', isArray: false}
        });
}];

//Catalogs
var DocumentTypes = ['$resource', 'context', function ($resource, context) {
    return $resource(context + '/api/catalogs/document-types', {}, {
        'get': {method: 'GET', params: {}, isArray: true}
    });
}];

var AppointmentSummaries = ['$resource', 'context', function ($resource, context) {
    return $resource(context + '/api/catalogs/appointment-summaries', {}, {
        'get': {method: 'GET', params: {}, isArray: true}
    });
}];

var ProfessionalCategories = ['$resource', 'context', function ($resource, context) {
    return $resource(context + '/api/catalogs/professional-categories', {}, {
        'get': {method: 'GET', params: {}, isArray: true}
    });
}];

/** Services **/
app.factory('Professional', Professional);
app.factory('MedicalCompany', MedicalCompany);
app.factory('MedicalInsurancePlan', MedicalInsurancePlan);
app.factory('Patient', Patient);
app.factory('Appointment', Appointment);
app.factory('DocumentTypes', DocumentTypes);
app.factory('AppointmentSummaries', AppointmentSummaries);
app.factory('ProfessionalCategories', ProfessionalCategories);
