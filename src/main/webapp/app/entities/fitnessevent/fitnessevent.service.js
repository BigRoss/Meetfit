(function() {
    'use strict';
    angular
        .module('meetFitApp')
        .factory('Fitnessevent', Fitnessevent);

    Fitnessevent.$inject = ['$resource', 'DateUtils'];

    function Fitnessevent ($resource, DateUtils) {
        var resourceUrl =  'api/fitnessevents/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.starttime = DateUtils.convertDateTimeFromServer(data.starttime);
                        data.endtime = DateUtils.convertDateTimeFromServer(data.endtime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
