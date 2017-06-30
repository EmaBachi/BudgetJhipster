(function() {
    'use strict';

    angular
        .module('budgetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('divisione', {
            parent: 'entity',
            url: '/divisione',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'budgetApp.divisione.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/divisione/divisiones.html',
                    controller: 'DivisioneController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('divisione');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('divisione-detail', {
            parent: 'divisione',
            url: '/divisione/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'budgetApp.divisione.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/divisione/divisione-detail.html',
                    controller: 'DivisioneDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('divisione');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Divisione', function($stateParams, Divisione) {
                    return Divisione.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'divisione',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('divisione-detail.edit', {
            parent: 'divisione-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/divisione/divisione-dialog.html',
                    controller: 'DivisioneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Divisione', function(Divisione) {
                            return Divisione.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('divisione.new', {
            parent: 'divisione',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/divisione/divisione-dialog.html',
                    controller: 'DivisioneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('divisione', null, { reload: 'divisione' });
                }, function() {
                    $state.go('divisione');
                });
            }]
        })
        .state('divisione.edit', {
            parent: 'divisione',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/divisione/divisione-dialog.html',
                    controller: 'DivisioneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Divisione', function(Divisione) {
                            return Divisione.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('divisione', null, { reload: 'divisione' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('divisione.delete', {
            parent: 'divisione',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/divisione/divisione-delete-dialog.html',
                    controller: 'DivisioneDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Divisione', function(Divisione) {
                            return Divisione.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('divisione', null, { reload: 'divisione' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
