(function() {
    'use strict';
    angular
        .module('budgetApp')
        .factory('Divisione', Divisione);

    Divisione.$inject = ['$resource'];

    function Divisione ($resource) {
        var resourceUrl =  'api/divisiones/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
