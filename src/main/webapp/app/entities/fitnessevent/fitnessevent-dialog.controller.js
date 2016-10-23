(function() {
    'use strict';

    angular
        .module('meetFitApp')
        .controller('FitnesseventDialogController', FitnesseventDialogController);

    FitnesseventDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Fitnessevent', 'User'];

    function FitnesseventDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Fitnessevent, User) {
        var vm = this;

        vm.fitnessevent = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.fitnessevent.id !== null) {
                Fitnessevent.update(vm.fitnessevent, onSaveSuccess, onSaveError);
            } else {
                Fitnessevent.save(vm.fitnessevent, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('meetFitApp:fitnesseventUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.starttime = false;
        vm.datePickerOpenStatus.endtime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
