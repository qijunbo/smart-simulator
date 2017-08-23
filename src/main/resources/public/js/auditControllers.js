'use strict';

/* Controllers */

var auditControllers = angular.module('auditControllers', []);

auditControllers.controller('auditController', [ '$scope',  '$routeParams', 'auditService', 'chargePointSerialService',  '$timeout', 'auditmoreService',
		function auditController($scope, $routeParams, auditService, chargePointSerialService, $timeout, auditmoreService) {
	
			$scope.audits = [];
			$scope.deviceSerial = $routeParams.serial;
			console.log($routeParams.serial);

			//get the current charge point information by deviceSerial.
			chargePointSerialService.get({serial: $routeParams.serial }, {}, function success(response) {
				//console.log("get ChargePoint Success:" + JSON.stringify(response));
				$scope.chargepoint = response;

			}, function error(errorResponse) {
				alert("Connot connect to server.");
				console.log("Error:" + JSON.stringify(errorResponse));
			});


			// periodically call the API to get the recent logs if any.
			var updateClock = function() {

				var length = $scope.audits.length;
				//console.log("Audit:" + JSON.stringify( $scope.audits[0]  ));
		 		//console.log($scope.deviceSerial);
		 		var time = 0;
				//console.log( "time " + time );
				//console.log(JSON.stringify($scope.audits));
				if( length > 0 ){
					time = $scope.audits[0].time; 
					//console.log(time);
					if(time == null){
						time = 0;
					}
				}
				var current = new Date(time);
				var formatedDate = current.getFullYear() + '-' + (current.getMonth() + 1) + '-' +  current.getDate()  + ' ' + current.getHours() + ":" + current.getMinutes() + ":" + current.getSeconds ();
				console.log( "time " + formatedDate );

				auditmoreService.get({serial: $scope.deviceSerial,  date: time  }, {}, function success(response) {
					//console.log("refresh audit Success:" + JSON.stringify(response));
					console.log("response array length: " + response.length);
					if(response.length > 1){
						$scope.audits =  response.concat($scope.audits);  // $scope.audits.concat( );
					}
					if( $scope.audits.length > 50 ){
						$scope.audits = $scope.audits.slice(0, 49);
					}
					//console.log($scope.audits.length);
				}, function error(errorResponse) {
					//alert("Connot connect to server.");
					console.log("Error:" + JSON.stringify(errorResponse));
				});
				 
				$timeout(function() {
					updateClock();
				}, 3000);
			};

			updateClock();


			$scope.deleteLogs = function(){
				//alert($routeParams.serial);
				auditService.delete({serial: $routeParams.serial }, {}, function success(response) {
					console.log($scope.audits);
					console.log("array left:" + JSON.stringify($scope.audits));
					$scope.audits = [];
					console.log($scope.audits);
					console.log("array left:" + JSON.stringify($scope.audits));
					console.log("remove log Success:" + JSON.stringify(response));
					
				}, function error(errorResponse) {
					alert("Connot connect to server.");
					console.log("Error:" + JSON.stringify(errorResponse));
				});
			};

		} ]);

auditControllers.controller('sysauditController', [ '$scope', 'sysauditService', 'chargePointSerialService',  '$timeout', 'sysauditRemoveService',
	function sysauditController($scope, sysauditService, chargePointSerialService, $timeout, sysauditRemoveService) {
		$scope.audits = [];

		$scope.removeLogOlderThan = function(days){
			alert(days);
			sysauditRemoveService.delete({"day" : days }, {}, function success(response) {
				console.log(response + " logs removed.");
				$scope.audits = [];
			}, function error(errorResponse) {
				alert("Connot connect to server.");
				console.log("Error:" + JSON.stringify(errorResponse));
			});
		};

		// periodically call the API to get the recent logs if any.
		var updateClock = function() {

			var length = $scope.audits.length;
			//console.log("Audit:" + JSON.stringify( $scope.audits[0]  ));
	 		//console.log($scope.deviceSerial);
	 		var time = 0;
			//console.log( "time " + time );
			//console.log(JSON.stringify($scope.audits));
			if( length > 0 ){
				time = $scope.audits[0].time; 
				//console.log(time);
				if(time == null){
					time = 0;
				}
			}
			var current = new Date(time);
			var formatedDate = current.getFullYear() + '-' + (current.getMonth() + 1) + '-' +  current.getDate()  + ' ' + current.getHours() + ":" + current.getMinutes() + ":" + current.getSeconds ();
			console.log( "time " + formatedDate );

			sysauditService.get({date: time  }, {}, function success(response) {
				//console.log("refresh audit Success:" + JSON.stringify(response));
				console.log("response array length: " + response.length);
				if(response.length > 1){
					$scope.audits =  response.concat($scope.audits);  // $scope.audits.concat( );
				}
				if( $scope.audits.length > 50 ){
					$scope.audits = $scope.audits.slice(0, 49);
				}
				//console.log($scope.audits.length);
			}, function error(errorResponse) {
				//alert("Connot connect to server.");
				console.log("Error:" + JSON.stringify(errorResponse));
			});
			 
			$timeout(function() {
				updateClock();
			}, 3000);
		};

		updateClock();

	} ]);			
 
/** this refreshController can not works will with auditControllers, 
 *  because they are not in the same life cycle, and they want manipulate the same $scope.
 *  Currently, I cannot find a better solution  than merge the tow controllers into one controller.
 */

/*
auditControllers.controller('refreshController', [ '$scope',  '$timeout', 'auditmoreService',
		function refreshController($scope, $timeout, auditmoreService) {
			var updateClock = function() {

				var length = $scope.audits.length;
				//console.log("Audit:" + JSON.stringify( $scope.audits[0]  ));
		 		//console.log($scope.deviceSerial);
		 		var time = 0;
				console.log( "time " + time );
				//console.log(JSON.stringify($scope.audits));
				if( length > 0 ){
					time = $scope.audits[0].time; 
					//console.log(time);
					if(time == null){
						time = 0;
					}
				}
				//var formatedDate = time.getFullYear() + '-' + (time.getMonth() + 1) + '-' +  time.getDate()  + ' ' + time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds ();
				
				auditmoreService.get({serial: $scope.deviceSerial,  date: time  }, {}, function success(response) {
					//console.log("refresh audit Success:" + JSON.stringify(response));
					console.log("response array length: " + response.length);
					if(response.length > 1){
						$scope.audits =  $scope.audits.concat( response);
					}
					if( $scope.audits.length > 50 ){
						$scope.audits = $scope.audits.slice(0, 49);
					}
					//console.log($scope.audits.length);
				}, function error(errorResponse) {
					//alert("Connot connect to server.");
					console.log("Error:" + JSON.stringify(errorResponse));
				});
				 
				$timeout(function() {
					updateClock();
				}, 3000);
			};
			updateClock();

		} ]);

*/