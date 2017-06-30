(function() {
    'use strict';
    angular
        .module('budgetApp')
        .factory('Commessa', Commessa);

    Commessa.$inject = ['$resource'];

    function Commessa ($resource) {
        var resourceUrl =  'api/commessas/:id';

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
