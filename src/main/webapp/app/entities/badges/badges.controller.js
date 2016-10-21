(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .controller('BadgesController', BadgesController);

    BadgesController.$inject = ['$scope', '$state', 'Badges'];

    function BadgesController ($scope, $state, Badges) {
        var vm = this;
        
        vm.badges = [];

        loadAll();

        function loadAll() {
            Badges.query(function(result) {
                vm.badges = result;
            });
        }
    }
})();
