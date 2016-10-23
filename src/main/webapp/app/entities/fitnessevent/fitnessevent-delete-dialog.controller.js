(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .controller('FitnesseventDeleteController',FitnesseventDeleteController);

    FitnesseventDeleteController.$inject = ['$uibModalInstance', 'entity', 'Fitnessevent'];

    function FitnesseventDeleteController($uibModalInstance, entity, Fitnessevent) {
        var vm = this;

        vm.fitnessevent = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Fitnessevent.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
