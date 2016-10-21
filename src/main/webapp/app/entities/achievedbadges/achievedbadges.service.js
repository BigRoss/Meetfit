(function() {
    'use strict';
    angular
        .module('meetFitApp')
        .factory('Achievedbadges', Achievedbadges);

    Achievedbadges.$inject = ['$resource'];

    function Achievedbadges ($resource) {
        var resourceUrl =  'api/achievedbadges/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
