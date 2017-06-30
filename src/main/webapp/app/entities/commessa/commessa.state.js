(function() {
    'use strict';

    angular
        .module('budgetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('commessa', {
            parent: 'entity',
            url: '/commessa',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'budgetApp.commessa.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/commessa/commessas.html',
                    controller: 'CommessaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('commessa');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('commessa-detail', {
            parent: 'commessa',
            url: '/commessa/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'budgetApp.commessa.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/commessa/commessa-detail.html',
                    controller: 'CommessaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('commessa');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Commessa', function($stateParams, Commessa) {
                    return Commessa.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'commessa',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('commessa-detail.edit', {
            parent: 'commessa-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commessa/commessa-dialog.html',
                    controller: 'CommessaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Commessa', function(Commessa) {
                            return Commessa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('commessa.new', {
            parent: 'commessa',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commessa/commessa-dialog.html',
                    controller: 'CommessaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                codice: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('commessa', null, { reload: 'commessa' });
                }, function() {
                    $state.go('commessa');
                });
            }]
        })
        .state('commessa.edit', {
            parent: 'commessa',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commessa/commessa-dialog.html',
                    controller: 'CommessaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Commessa', function(Commessa) {
                            return Commessa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('commessa', null, { reload: 'commessa' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('commessa.delete', {
            parent: 'commessa',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/commessa/commessa-delete-dialog.html',
                    controller: 'CommessaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Commessa', function(Commessa) {
                            return Commessa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('commessa', null, { reload: 'commessa' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
