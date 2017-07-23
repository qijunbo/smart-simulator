'use strict';

/** device services */

var timeServices = angular.module('timeServices', [ 'ngResource' ]);

timeServices.factory('numberService', [ '$resource', function($resource) {
	return $resource(appContext + "/time/number/:time", {}, {
		get : {
			method : 'GET',
			cache : false,
			isArray : false
		}

	});
} ]);

timeServices.factory('stringService', [ '$resource', function($resource) {
	return $resource(appContext + "/time/string/:time", {}, {
		get : {
			method : 'GET',
			cache : false,
			isArray : false
		}

	});
} ]);
 
 