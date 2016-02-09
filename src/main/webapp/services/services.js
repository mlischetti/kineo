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
        }
    );
}];