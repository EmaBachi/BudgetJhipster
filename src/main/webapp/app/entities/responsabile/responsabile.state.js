(function() {
    'use strict';

    angular
        .module('budgetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('responsabile', {
            parent: 'entity',
            url: '/responsabile',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'budgetApp.responsabile.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/responsabile/responsabiles.html',
                    controller: 'ResponsabileController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('responsabile');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('responsabile-detail', {
            parent: 'responsabile',
            url: '/responsabile/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'budgetApp.responsabile.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/responsabile/responsabile-detail.html',
                    controller: 'ResponsabileDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('responsabile');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Responsabile', function($stateParams, Responsabile) {
                    return Responsabile.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'responsabile',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('responsabile-detail.edit', {
            parent: 'responsabile-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/responsabile/responsabile-dialog.html',
                    controller: 'ResponsabileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Responsabile', function(Responsabile) {
                            return Responsabile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('responsabile.new', {
            parent: 'responsabile',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/responsabile/responsabile-dialog.html',
                    controller: 'ResponsabileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                cognome: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('responsabile', null, { reload: 'responsabile' });
                }, function() {
                    $state.go('responsabile');
                });
            }]
        })
        .state('responsabile.edit', {
            parent: 'responsabile',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/responsabile/responsabile-dialog.html',
                    controller: 'ResponsabileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Responsabile', function(Responsabile) {
                            return Responsabile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('responsabile', null, { reload: 'responsabile' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('responsabile.delete', {
            parent: 'responsabile',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/responsabile/responsabile-delete-dialog.html',
                    controller: 'ResponsabileDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Responsabile', function(Responsabile) {
                            return Responsabile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('responsabile', null, { reload: 'responsabile' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
