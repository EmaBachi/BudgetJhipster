(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('ResponsabileDeleteController',ResponsabileDeleteController);

    ResponsabileDeleteController.$inject = ['$uibModalInstance', 'entity', 'Responsabile'];

    function ResponsabileDeleteController($uibModalInstance, entity, Responsabile) {
        var vm = this;

        vm.responsabile = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Responsabile.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
