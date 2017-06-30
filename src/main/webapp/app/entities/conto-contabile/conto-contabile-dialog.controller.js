(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('ContoContabileDialogController', ContoContabileDialogController);

    ContoContabileDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ContoContabile'];

    function ContoContabileDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ContoContabile) {
        var vm = this;

        vm.contoContabile = entity;
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
            if (vm.contoContabile.id !== null) {
                ContoContabile.update(vm.contoContabile, onSaveSuccess, onSaveError);
            } else {
                ContoContabile.save(vm.contoContabile, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('budgetApp:contoContabileUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
