(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .controller('AchievedbadgesDeleteController',AchievedbadgesDeleteController);

    AchievedbadgesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Achievedbadges'];

    function AchievedbadgesDeleteController($uibModalInstance, entity, Achievedbadges) {
        var vm = this;

        vm.achievedbadges = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Achievedbadges.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
