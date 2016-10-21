(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .controller('AchievedbadgesController', AchievedbadgesController);

    AchievedbadgesController.$inject = ['$scope', '$state', 'Achievedbadges'];

    function AchievedbadgesController ($scope, $state, Achievedbadges) {
        var vm = this;
        
        vm.achievedbadges = [];

        loadAll();

        function loadAll() {
            Achievedbadges.query(function(result) {
                vm.achievedbadges = result;
            });
        }
    }
})();
