(function() {
    'use strict';
    angular
        .module('budgetApp')
        .factory('Responsabile', Responsabile);

    Responsabile.$inject = ['$resource'];

    function Responsabile ($resource) {
        var resourceUrl =  'api/responsabiles/:id';

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
