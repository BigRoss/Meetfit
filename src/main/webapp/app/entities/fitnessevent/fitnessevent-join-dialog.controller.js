(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .controller('FitnesseventJoinController',FitnesseventJoinController);

    FitnesseventJoinController.$inject = ['$uibModalInstance', '$scope', '$stateParams', 'entity', 'Fitnessevent','Attendingevent', 'User'];

    function FitnesseventJoinController ($uibModalInstance, $scope, $stateParams, entity, Fitnessevent, Attendingevent, User) {
        var vm = this;

        vm.users = User.query();
        vm.fitnessevent = entity;
        vm.clear = clear;
        vm.confirmJoin = confirmJoin;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

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
    }
})();
