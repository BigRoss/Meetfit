(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .controller('FitnesseventController', FitnesseventController);

    FitnesseventController.$inject = ['$scope', '$state', 'Fitnessevent'];

    function FitnesseventController ($scope, $state, Fitnessevent) {
        var vm = this;
        
        vm.fitnessevents = [];

        loadAll();

        function loadAll() {
            Fitnessevent.query(function(result) {
                vm.fitnessevents = result;
            });
        }
    }
})();
