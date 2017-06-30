(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('ContoContabileDeleteController',ContoContabileDeleteController);

    ContoContabileDeleteController.$inject = ['$uibModalInstance', 'entity', 'ContoContabile'];

    function ContoContabileDeleteController($uibModalInstance, entity, ContoContabile) {
        var vm = this;

        vm.contoContabile = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ContoContabile.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
