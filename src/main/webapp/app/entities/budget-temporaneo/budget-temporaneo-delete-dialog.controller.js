(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('BudgetTemporaneoDeleteController',BudgetTemporaneoDeleteController);

    BudgetTemporaneoDeleteController.$inject = ['$uibModalInstance', 'entity', 'BudgetTemporaneo'];

    function BudgetTemporaneoDeleteController($uibModalInstance, entity, BudgetTemporaneo) {
        var vm = this;

        vm.budgetTemporaneo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BudgetTemporaneo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
