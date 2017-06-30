(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('ContoContabileDetailController', ContoContabileDetailController);

    ContoContabileDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ContoContabile'];

    function ContoContabileDetailController($scope, $rootScope, $stateParams, previousState, entity, ContoContabile) {
        var vm = this;

        vm.contoContabile = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('budgetApp:contoContabileUpdate', function(event, result) {
            vm.contoContabile = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
