(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .controller('BadgesDialogController', BadgesDialogController);

    BadgesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Badges'];

    function BadgesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Badges) {
        var vm = this;

        vm.badges = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.badges.id !== null) {
                Badges.update(vm.badges, onSaveSuccess, onSaveError);
            } else {
                Badges.save(vm.badges, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('meetFitApp:badgesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
