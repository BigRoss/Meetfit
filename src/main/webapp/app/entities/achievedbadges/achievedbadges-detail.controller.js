(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .controller('AchievedbadgesDetailController', AchievedbadgesDetailController);

    AchievedbadgesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Achievedbadges', 'Badges', 'User'];

    function AchievedbadgesDetailController($scope, $rootScope, $stateParams, previousState, entity, Achievedbadges, Badges, User) {
        var vm = this;

        vm.achievedbadges = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('meetFitApp:achievedbadgesUpdate', function(event, result) {
            vm.achievedbadges = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
