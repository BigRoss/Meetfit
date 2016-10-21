(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('achievedbadges', {
            parent: 'entity',
            url: '/achievedbadges',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Achievedbadges'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/achievedbadges/achievedbadges.html',
                    controller: 'AchievedbadgesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('achievedbadges-detail', {
            parent: 'entity',
            url: '/achievedbadges/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Achievedbadges'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/achievedbadges/achievedbadges-detail.html',
                    controller: 'AchievedbadgesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Achievedbadges', function($stateParams, Achievedbadges) {
                    return Achievedbadges.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'achievedbadges',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('achievedbadges-detail.edit', {
            parent: 'achievedbadges-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/achievedbadges/achievedbadges-dialog.html',
                    controller: 'AchievedbadgesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Achievedbadges', function(Achievedbadges) {
                            return Achievedbadges.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('achievedbadges.new', {
            parent: 'achievedbadges',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/achievedbadges/achievedbadges-dialog.html',
                    controller: 'AchievedbadgesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('achievedbadges', null, { reload: 'achievedbadges' });
                }, function() {
                    $state.go('achievedbadges');
                });
            }]
        })
        .state('achievedbadges.edit', {
            parent: 'achievedbadges',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/achievedbadges/achievedbadges-dialog.html',
                    controller: 'AchievedbadgesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Achievedbadges', function(Achievedbadges) {
                            return Achievedbadges.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('achievedbadges', null, { reload: 'achievedbadges' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('achievedbadges.delete', {
            parent: 'achievedbadges',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/achievedbadges/achievedbadges-delete-dialog.html',
                    controller: 'AchievedbadgesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Achievedbadges', function(Achievedbadges) {
                            return Achievedbadges.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('achievedbadges', null, { reload: 'achievedbadges' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
