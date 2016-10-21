(function() {
    'use strict';
    angular
        .module('meetFitApp')
        .factory('Badges', Badges);

    Badges.$inject = ['$resource'];

    function Badges ($resource) {
        var resourceUrl =  'api/badges/:id';

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
