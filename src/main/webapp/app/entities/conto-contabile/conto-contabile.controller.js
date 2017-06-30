(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('ContoContabileController', ContoContabileController);

    ContoContabileController.$inject = ['ContoContabile'];

    function ContoContabileController(ContoContabile) {

        var vm = this;

        vm.contoContabiles = [];

        loadAll();

        function loadAll() {
            ContoContabile.query(function(result) {
                vm.contoContabiles = result;
                vm.searchQuery = null;
            });
        }
    }
})();
