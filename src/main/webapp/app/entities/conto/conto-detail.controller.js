(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('ContoDetailController', ContoDetailController);

    ContoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Conto'];

    function ContoDetailController($scope, $rootScope, $stateParams, previousState, entity, Conto) {
        var vm = this;

        vm.conto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('budgetApp:contoUpdate', function(event, result) {
            vm.conto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
