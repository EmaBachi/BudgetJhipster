(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('CommessaDialogController', CommessaDialogController);

    CommessaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Commessa', 'Divisione'];

    function CommessaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Commessa, Divisione) {
        var vm = this;

        vm.commessa = entity;
        vm.clear = clear;
        vm.save = save;
        vm.divisiones = Divisione.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.commessa.id !== null) {
                Commessa.update(vm.commessa, onSaveSuccess, onSaveError);
            } else {
                Commessa.save(vm.commessa, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('budgetApp:commessaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
