(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .controller('FitnesseventController', FitnesseventController);

    FitnesseventController.$inject = ['$scope', '$state', 'Fitnessevent'];

    function FitnesseventController ($scope, $state, Fitnessevent) {
        var vm = this;

        vm.fitnessevents = [];

        $scope.reverse = true;
        $scope.propertyName = 'starttime';
        $scope.sortBy = function(propertyName) {
            $scope.reverse = ($scope.propertyName === propertyName) ? !$scope.reverse : false;
            $scope.propertyName = propertyName;
        };

        loadAll();

        function loadAll() {
            Fitnessevent.query(function(result) {
                vm.fitnessevents = result;
            });
        }
    }
})();
