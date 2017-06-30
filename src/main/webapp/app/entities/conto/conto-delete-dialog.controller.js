(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('ContoDeleteController',ContoDeleteController);

    ContoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Conto'];

    function ContoDeleteController($uibModalInstance, entity, Conto) {
        var vm = this;

        vm.conto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Conto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
