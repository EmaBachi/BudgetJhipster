(function() {
    'use strict';

    angular
        .module('budgetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('budget-temporaneo', {
            parent: 'entity',
            url: '/budget-temporaneo',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'budgetApp.budgetTemporaneo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/budget-temporaneo/budget-temporaneos.html',
                    controller: 'BudgetTemporaneoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('budgetTemporaneo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('budget-temporaneo-detail', {
            parent: 'budget-temporaneo',
            url: '/budget-temporaneo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'budgetApp.budgetTemporaneo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/budget-temporaneo/budget-temporaneo-detail.html',
                    controller: 'BudgetTemporaneoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('budgetTemporaneo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BudgetTemporaneo', function($stateParams, BudgetTemporaneo) {
                    return BudgetTemporaneo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'budget-temporaneo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('budget-temporaneo-detail.edit', {
            parent: 'budget-temporaneo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/budget-temporaneo/budget-temporaneo-dialog.html',
                    controller: 'BudgetTemporaneoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BudgetTemporaneo', function(BudgetTemporaneo) {
                            return BudgetTemporaneo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('budget-temporaneo.new', {
            parent: 'budget-temporaneo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/budget-temporaneo/budget-temporaneo-dialog.html',
                    controller: 'BudgetTemporaneoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                gennaio: null,
                                febbraio: null,
                                marzo: null,
                                aprile: null,
                                maggio: null,
                                giugno: null,
                                luglio: null,
                                agosto: null,
                                settembre: null,
                                ottobre: null,
                                novembre: null,
                                dicembre: null,
                                totale: null,
                                mensilizzare: null,
                                divisione: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('budget-temporaneo', null, { reload: 'budget-temporaneo' });
                }, function() {
                    $state.go('budget-temporaneo');
                });
            }]
        })
        .state('budget-temporaneo.edit', {
            parent: 'budget-temporaneo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/budget-temporaneo/budget-temporaneo-dialog.html',
                    controller: 'BudgetTemporaneoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BudgetTemporaneo', function(BudgetTemporaneo) {
                            return BudgetTemporaneo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('budget-temporaneo', null, { reload: 'budget-temporaneo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('budget-temporaneo.delete', {
            parent: 'budget-temporaneo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/budget-temporaneo/budget-temporaneo-delete-dialog.html',
                    controller: 'BudgetTemporaneoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BudgetTemporaneo', function(BudgetTemporaneo) {
                            return BudgetTemporaneo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('budget-temporaneo', null, { reload: 'budget-temporaneo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
