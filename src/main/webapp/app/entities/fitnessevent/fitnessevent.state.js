(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('fitnessevent', {
            parent: 'entity',
            url: '/fitnessevent',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Fitnessevents'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fitnessevent/fitnessevents.html',
                    controller: 'FitnesseventController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('fitnessevent-detail', {
            parent: 'entity',
            url: '/fitnessevent/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Fitnessevent'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fitnessevent/fitnessevent-detail.html',
                    controller: 'FitnesseventDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Fitnessevent', function($stateParams, Fitnessevent) {
                    return Fitnessevent.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'fitnessevent',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('fitnessevent-detail.edit', {
            parent: 'fitnessevent-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fitnessevent/fitnessevent-dialog.html',
                    controller: 'FitnesseventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Fitnessevent', function(Fitnessevent) {
                            return Fitnessevent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fitnessevent.new', {
            parent: 'fitnessevent',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fitnessevent/fitnessevent-dialog.html',
                    controller: 'FitnesseventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                location: null,
                                starttime: null,
                                endtime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('fitnessevent', null, { reload: 'fitnessevent' });
                }, function() {
                    $state.go('fitnessevent');
                });
            }]
        })
        .state('fitnessevent.edit', {
            parent: 'fitnessevent',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fitnessevent/fitnessevent-dialog.html',
                    controller: 'FitnesseventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Fitnessevent', function(Fitnessevent) {
                            return Fitnessevent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fitnessevent', null, { reload: 'fitnessevent' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fitnessevent.delete', {
            parent: 'fitnessevent',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fitnessevent/fitnessevent-delete-dialog.html',
                    controller: 'FitnesseventDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Fitnessevent', function(Fitnessevent) {
                            return Fitnessevent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fitnessevent', null, { reload: 'fitnessevent' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fitnessevent.join', {
            parent: 'fitnessevent',
            url: '/{id}/join',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fitnessevent/fitnessevent-join-dialog.html',
                    controller: 'FitnesseventJoinController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Fitnessevent', function(Fitnessevent) {
                            return Fitnessevent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fitnessevent', null, { reload: 'fitnessevent' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
