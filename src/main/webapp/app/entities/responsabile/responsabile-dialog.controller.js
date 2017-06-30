(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('ResponsabileDialogController', ResponsabileDialogController);

    ResponsabileDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Responsabile', 'User'];

    function ResponsabileDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Responsabile, User) {
        var vm = this;

        vm.responsabile = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.responsabile.id !== null) {
                Responsabile.update(vm.responsabile, onSaveSuccess, onSaveError);
            } else {
                Responsabile.save(vm.responsabile, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('budgetApp:responsabileUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
