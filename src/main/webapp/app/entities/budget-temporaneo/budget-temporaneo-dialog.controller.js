(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('BudgetTemporaneoDialogController', BudgetTemporaneoDialogController);

    BudgetTemporaneoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BudgetTemporaneo', 'Commessa', 'ContoContabile'];

    function BudgetTemporaneoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BudgetTemporaneo, Commessa, ContoContabile) {
        var vm = this;

        vm.budgetTemporaneo = entity;
        vm.clear = clear;
        vm.save = save;
        vm.commessas = Commessa.query();
        vm.contocontabiles = ContoContabile.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.budgetTemporaneo.id !== null) {
                BudgetTemporaneo.update(vm.budgetTemporaneo, onSaveSuccess, onSaveError);
            } else {
                BudgetTemporaneo.save(vm.budgetTemporaneo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('budgetApp:budgetTemporaneoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
