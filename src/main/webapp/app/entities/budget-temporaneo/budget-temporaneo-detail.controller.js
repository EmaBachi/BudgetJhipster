(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('BudgetTemporaneoDetailController', BudgetTemporaneoDetailController);

    BudgetTemporaneoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BudgetTemporaneo', 'Commessa', 'ContoContabile'];

    function BudgetTemporaneoDetailController($scope, $rootScope, $stateParams, previousState, entity, BudgetTemporaneo, Commessa, ContoContabile) {
        var vm = this;

        vm.budgetTemporaneo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('budgetApp:budgetTemporaneoUpdate', function(event, result) {
            vm.budgetTemporaneo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
