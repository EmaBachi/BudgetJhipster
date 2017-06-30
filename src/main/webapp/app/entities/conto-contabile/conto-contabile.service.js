(function() {
    'use strict';
    angular
        .module('budgetApp')
        .factory('ContoContabile', ContoContabile);

    ContoContabile.$inject = ['$resource'];

    function ContoContabile ($resource) {
        var resourceUrl =  'api/conto-contabiles/:id';

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
