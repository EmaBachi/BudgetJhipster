(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('DivisioneDialogController', DivisioneDialogController);

    DivisioneDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Divisione', 'Responsabile'];

    function DivisioneDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Divisione, Responsabile) {
        var vm = this;

        vm.divisione = entity;
        vm.clear = clear;
        vm.save = save;
        vm.responsabiles = Responsabile.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.divisione.id !== null) {
                Divisione.update(vm.divisione, onSaveSuccess, onSaveError);
            } else {
                Divisione.save(vm.divisione, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('budgetApp:divisioneUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
