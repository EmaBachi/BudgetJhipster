(function() {
    'use strict';

    angular
        .module('budgetApp')
        .controller('CommessaDetailController', CommessaDetailController);

    CommessaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Commessa', 'Divisione'];

    function CommessaDetailController($scope, $rootScope, $stateParams, previousState, entity, Commessa, Divisione) {
        var vm = this;

        vm.commessa = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('budgetApp:commessaUpdate', function(event, result) {
            vm.commessa = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
