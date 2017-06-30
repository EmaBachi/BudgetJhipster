(function() {
    'use strict';

    angular
        .module('budgetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('conto', {
            parent: 'entity',
            url: '/conto',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'budgetApp.conto.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/conto/contos.html',
                    controller: 'ContoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('conto');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('conto-detail', {
            parent: 'conto',
            url: '/conto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'budgetApp.conto.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/conto/conto-detail.html',
                    controller: 'ContoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('conto');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Conto', function($stateParams, Conto) {
                    return Conto.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'conto',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('conto-detail.edit', {
            parent: 'conto-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conto/conto-dialog.html',
                    controller: 'ContoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Conto', function(Conto) {
                            return Conto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('conto.new', {
            parent: 'conto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conto/conto-dialog.html',
                    controller: 'ContoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                codice: null,
                                nome: null,
                                descrizione: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('conto', null, { reload: 'conto' });
                }, function() {
                    $state.go('conto');
                });
            }]
        })
        .state('conto.edit', {
            parent: 'conto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conto/conto-dialog.html',
                    controller: 'ContoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Conto', function(Conto) {
                            return Conto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('conto', null, { reload: 'conto' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('conto.delete', {
            parent: 'conto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conto/conto-delete-dialog.html',
                    controller: 'ContoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Conto', function(Conto) {
                            return Conto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('conto', null, { reload: 'conto' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
