(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('ContoDialogController', ContoDialogController);

    ContoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Conto'];

    function ContoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Conto) {
        var vm = this;

        vm.conto = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.conto.id !== null) {
                Conto.update(vm.conto, onSaveSuccess, onSaveError);
            } else {
                Conto.save(vm.conto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('budgetApp:contoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
