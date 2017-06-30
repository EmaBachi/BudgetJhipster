(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('DivisioneDeleteController',DivisioneDeleteController);

    DivisioneDeleteController.$inject = ['$uibModalInstance', 'entity', 'Divisione'];

    function DivisioneDeleteController($uibModalInstance, entity, Divisione) {
        var vm = this;

        vm.divisione = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Divisione.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
