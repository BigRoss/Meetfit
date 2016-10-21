(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('badges', {
            parent: 'entity',
            url: '/badges',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Badges'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/badges/badges.html',
                    controller: 'BadgesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('badges-detail', {
            parent: 'entity',
            url: '/badges/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Badges'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/badges/badges-detail.html',
                    controller: 'BadgesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Badges', function($stateParams, Badges) {
                    return Badges.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'badges',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('badges-detail.edit', {
            parent: 'badges-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/badges/badges-dialog.html',
                    controller: 'BadgesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Badges', function(Badges) {
                            return Badges.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('badges.new', {
            parent: 'badges',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/badges/badges-dialog.html',
                    controller: 'BadgesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                points: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('badges', null, { reload: 'badges' });
                }, function() {
                    $state.go('badges');
                });
            }]
        })
        .state('badges.edit', {
            parent: 'badges',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/badges/badges-dialog.html',
                    controller: 'BadgesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Badges', function(Badges) {
                            return Badges.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('badges', null, { reload: 'badges' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('badges.delete', {
            parent: 'badges',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/badges/badges-delete-dialog.html',
                    controller: 'BadgesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Badges', function(Badges) {
                            return Badges.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('badges', null, { reload: 'badges' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
