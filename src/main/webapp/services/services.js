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