(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('DivisioneDetailController', DivisioneDetailController);

    DivisioneDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Divisione', 'Responsabile'];

    function DivisioneDetailController($scope, $rootScope, $stateParams, previousState, entity, Divisione, Responsabile) {
        var vm = this;

        vm.divisione = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('budgetApp:divisioneUpdate', function(event, result) {
            vm.divisione = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
