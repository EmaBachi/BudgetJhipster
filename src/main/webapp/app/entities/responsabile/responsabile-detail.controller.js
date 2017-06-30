(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('ResponsabileDetailController', ResponsabileDetailController);

    ResponsabileDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Responsabile', 'User'];

    function ResponsabileDetailController($scope, $rootScope, $stateParams, previousState, entity, Responsabile, User) {
        var vm = this;

        vm.responsabile = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('budgetApp:responsabileUpdate', function(event, result) {
            vm.responsabile = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
