(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .controller('FitnesseventJoinController',FitnesseventJoinController);

    FitnesseventJoinController.$inject = ['$uibModalInstance', '$scope', '$stateParams', 'entity', 'Fitnessevent', 'User'];

    function FitnesseventJoinController ($uibModalInstance, $scope, $stateParams, entity, Fitnessevent, User) {
        var vm = this;

        vm.users = User.query();
        vm.fitnessevent = entity;
        vm.clear = clear;
        vm.confirmJoin = confirmJoin;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmJoin (id) {

            $uibModalInstance.close(true);
        }
    }
})();
