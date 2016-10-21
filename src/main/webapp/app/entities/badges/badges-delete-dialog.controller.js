(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .controller('BadgesDeleteController',BadgesDeleteController);

    BadgesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Badges'];

    function BadgesDeleteController($uibModalInstance, entity, Badges) {
        var vm = this;

        vm.badges = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Badges.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
