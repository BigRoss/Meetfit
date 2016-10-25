(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .controller('FitnesseventDetailController', FitnesseventDetailController);

    FitnesseventDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Fitnessevent', 'Attendingevent', 'User'];

    function FitnesseventDetailController($scope, $rootScope, $stateParams, previousState, entity, Fitnessevent, Attendingevent, User) {
        var vm = this;

        vm.fitnessevent = entity;
        vm.previousState = previousState.name;
        vm.confirmJoin = confirmJoin;

        function confirmJoin (id)
        {
            Attendingevent.attend(vm.fitnessevent, goodAlert, badAlert);
            $uibModalInstance.close(true);
        }

        function goodAlert(result)
        {
            //alert('yay: ' + result);
            console.log(result);
        }

        function badAlert(result)
        {
            //alert('bad: ' +result);
        }

        var unsubscribe = $rootScope.$on('meetFitApp:fitnesseventUpdate', function(event, result) {
            vm.fitnessevent = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
