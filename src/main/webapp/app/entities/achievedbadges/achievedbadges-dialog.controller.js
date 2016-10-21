(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .controller('AchievedbadgesDialogController', AchievedbadgesDialogController);

    AchievedbadgesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Achievedbadges', 'Badges', 'User'];

    function AchievedbadgesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Achievedbadges, Badges, User) {
        var vm = this;

        vm.achievedbadges = entity;
        vm.clear = clear;
        vm.save = save;
        vm.badges = Badges.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.achievedbadges.id !== null) {
                Achievedbadges.update(vm.achievedbadges, onSaveSuccess, onSaveError);
            } else {
                Achievedbadges.save(vm.achievedbadges, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('meetFitApp:achievedbadgesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
