(function() {
    'use strict';
    angular
        .module('budgetApp')
        .factory('BudgetTemporaneo', BudgetTemporaneo);

    BudgetTemporaneo.$inject = ['$resource'];

    function BudgetTemporaneo ($resource) {
        var resourceUrl =  'api/budget-temporaneos/:id';

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
