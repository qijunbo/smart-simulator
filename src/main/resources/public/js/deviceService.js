'use strict';

/** device services */

var deviceServices = angular.module('deviceServices', ['ngResource']);

deviceServices.factory('chargePointIdService', ['$resource',
    function($resource) {
		
        return $resource(appContext + "/chargepoint/:id", {}, {
            get: {method: 'GET', cache: false, isArray: false},
            delete: {method: 'DELETE', cache: false, isArray: false}
        });
    }]);


deviceServices.factory('chargePointSerialService', ['$resource',
    function($resource) {
        
        return $resource(appContext + "/chargepoint/serial/:serial", {}, {
            get: {method: 'GET', cache: false, isArray: false},
        });
    }]);

 
deviceServices.factory('chargePointService', ['$resource',
  function($resource) {
	 
	  return $resource( appContext + "/chargepoint", {}, {
		  get: {method: 'GET', cache: false, isArray: true} ,
		  save: {method: 'POST', cache: false, isArray: false},
		  update: {method: 'PUT', cache: false, isArray: false}
					 
	  });
  }]);
