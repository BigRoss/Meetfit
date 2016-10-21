(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .controller('BadgesDetailController', BadgesDetailController);

    BadgesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Badges'];

    function BadgesDetailController($scope, $rootScope, $stateParams, previousState, entity, Badges) {
        var vm = this;

        vm.badges = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('meetFitApp:badgesUpdate', function(event, result) {
            vm.badges = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
