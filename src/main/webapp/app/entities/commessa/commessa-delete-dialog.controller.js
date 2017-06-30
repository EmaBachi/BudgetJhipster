(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('CommessaDeleteController',CommessaDeleteController);

    CommessaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Commessa'];

    function CommessaDeleteController($uibModalInstance, entity, Commessa) {
        var vm = this;

        vm.commessa = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Commessa.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
