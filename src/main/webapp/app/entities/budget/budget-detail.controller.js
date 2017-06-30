(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('BudgetDetailController', BudgetDetailController);

    BudgetDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Budget', 'Commessa', 'Conto'];

    function BudgetDetailController($scope, $rootScope, $stateParams, previousState, entity, Budget, Commessa, Conto) {
        var vm = this;

        vm.budget = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('budgetApp:budgetUpdate', function(event, result) {
            vm.budget = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
