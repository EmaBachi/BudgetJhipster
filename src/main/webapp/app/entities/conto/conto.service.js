(function() {
    'use strict';
    angular
        .module('budgetApp')
        .factory('Conto', Conto);

    Conto.$inject = ['$resource'];

    function Conto ($resource) {
        var resourceUrl =  'api/contos/:id';

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
