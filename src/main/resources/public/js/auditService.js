'use strict';

/** audit services  */
var auditServices = angular.module('auditServices', ['ngResource']);

auditServices.factory('auditService', ['$resource', 
 	function($resource) {
        return $resource(appContext + "/audit/device/:serial", {}, {
            get: {method: 'GET', cache: false, isArray: true},
            delete: {method: 'DELETE', cache: false, isArray: false}
        });
    }]);

auditServices.factory('auditmoreService', ['$resource', 
	function($resource) {
    	return $resource(appContext + "/audit/device/:serial/time/:date", {}, {
        	get: {method: 'GET', cache: false, isArray: true}
    	});
	}]);

auditServices.factory('sysauditService', ['$resource', 
    function($resource) {
        return $resource(appContext + "/audit/time/:date", {}, {
            get: {method: 'GET', cache: false, isArray: true}
        });
    }]);
auditServices.factory('sysauditRemoveService', ['$resource', 
    function($resource) {
        return $resource(appContext + "/audit/day/:day", {}, {
            delete: {method: 'DELETE', cache: false, isArray: false}
        });
    }]);