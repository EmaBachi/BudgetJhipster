(function() {
    'use strict';

    angular
        .module('budgetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('conto-contabile', {
            parent: 'entity',
            url: '/conto-contabile',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'budgetApp.contoContabile.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/conto-contabile/conto-contabiles.html',
                    controller: 'ContoContabileController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contoContabile');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('conto-contabile-detail', {
            parent: 'conto-contabile',
            url: '/conto-contabile/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'budgetApp.contoContabile.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/conto-contabile/conto-contabile-detail.html',
                    controller: 'ContoContabileDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contoContabile');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ContoContabile', function($stateParams, ContoContabile) {
                    return ContoContabile.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'conto-contabile',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('conto-contabile-detail.edit', {
            parent: 'conto-contabile-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conto-contabile/conto-contabile-dialog.html',
                    controller: 'ContoContabileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ContoContabile', function(ContoContabile) {
                            return ContoContabile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('conto-contabile.new', {
            parent: 'conto-contabile',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conto-contabile/conto-contabile-dialog.html',
                    controller: 'ContoContabileDialogController',
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
                    $state.go('conto-contabile', null, { reload: 'conto-contabile' });
                }, function() {
                    $state.go('conto-contabile');
                });
            }]
        })
        .state('conto-contabile.edit', {
            parent: 'conto-contabile',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conto-contabile/conto-contabile-dialog.html',
                    controller: 'ContoContabileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ContoContabile', function(ContoContabile) {
                            return ContoContabile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('conto-contabile', null, { reload: 'conto-contabile' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('conto-contabile.delete', {
            parent: 'conto-contabile',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/conto-contabile/conto-contabile-delete-dialog.html',
                    controller: 'ContoContabileDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ContoContabile', function(ContoContabile) {
                            return ContoContabile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('conto-contabile', null, { reload: 'conto-contabile' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
