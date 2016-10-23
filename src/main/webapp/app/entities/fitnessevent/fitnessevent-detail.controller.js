(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .controller('FitnesseventDetailController', FitnesseventDetailController);

    FitnesseventDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Fitnessevent', 'User'];

    function FitnesseventDetailController($scope, $rootScope, $stateParams, previousState, entity, Fitnessevent, User) {
        var vm = this;

        vm.fitnessevent = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('meetFitApp:fitnesseventUpdate', function(event, result) {
            vm.fitnessevent = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
