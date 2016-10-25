(function() {
    'use strict';
    angular
        .module('meetFitApp')
        .factory('Attendingevent', Attendingevent)

    Attendingevent.$inject = ['$resource', 'DateUtils'];

    function Attendingevent ($resource, DateUtils) {
        //var resourceUrl =  'api/fitnessevents/attend';
        var resourceUrl =  'api/attend';

        return $resource(resourceUrl, {}, {
            'attend': { method:'POST' },
            'get': { method:'GET', transformResponse: function (data) {
                return data + "123456789";
            } }
        });
    }
})();
