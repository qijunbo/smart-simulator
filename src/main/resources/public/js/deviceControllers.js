'use strict';

/* device controllers */

var deviceControllers = angular.module('deviceControllers', []);

deviceControllers.controller('deviceInitController', [ '$scope',   'chargePointService', 'chargePointIdService',
		function deviceInitController($scope, chargePointService, chargePointIdService) {
			// when user click the new button, try to input the charge point
			// information
			$scope.appContext = appContext;
			$scope.onNewButtonClick = function() {
				$scope.editDialog = "hidden";
				$scope.newDialog = "";

				$scope.chargepoint = {
					"version" : "ocpp15",
					"connectorNumber" : 1 ,
					"centralURL" : "http://op.spie.ievep.net/ws/OcppGateway"
				};
			};

			// when user click the edit button, going to edit, but not yet save.
			$scope.onEditClick = function(item) {
				$scope.chargepoint = item;
				$scope.editDialog = "";
				$scope.newDialog = "hidden";
			};

			// when user click the save button, save data.
			$scope.saveChargePoint = function( ) {
				// alert( JSON.stringify($scope.chargepoint) );
				// console.log( JSON.stringify($scope.chargepoint) );

				chargePointService.update({}, $scope.chargepoint, function success(response) {
					console.log("save ChargePoint Success:" + JSON.stringify(response));
				}, function error(errorResponse) {
					alert("Connot connect to server.");
					console.log("Error:" + JSON.stringify(errorResponse));
				});
			};


			// when user click the new button
			$scope.newChargePoint = function( ) {
				// alert( JSON.stringify($scope.chargepoint) );
				// console.log( JSON.stringify($scope.chargepoint) );
				chargePointService.save({}, $scope.chargepoint, function success(response) {
					console.log("save ChargePoint Success:" + JSON.stringify(response));
						$scope.cps.push(response);
				}, function error(errorResponse) {
					alert("Connot connect to server.");
					console.log("Error:" + JSON.stringify(errorResponse));
				});
			};
			 
			$scope.remove = function( item ) {
				// alert( JSON.stringify(item) );
				// console.log( JSON.stringify(item) );
 
				chargePointIdService.delete({id:item.id}, {}, function success(response) {
					console.log("remove ChargePoint Success:" + JSON.stringify(item));
			 		var index = $scope.cps.indexOf(item);
				    $scope.cps.splice(index, 1); 

				}, function error(errorResponse) {
					alert("Connot connect to server.");
					console.log("Error:" + JSON.stringify(errorResponse));
				});
			};
			

		} ]);

deviceControllers.controller('listChargePointController', [ '$scope', 'chargePointService', 
		function listChargePointController($scope, chargePointService) {
			$scope.cps = [];
			chargePointService.get( {}, {}, function success(response) {

				//console.log("listChargePoint Success:" + JSON.stringify(response));
				$scope.cps = response;

			}, function error(errorResponse) {
				alert("Connot connect to server.");
				console.log("Error:" + JSON.stringify(errorResponse));
			});

		} ]);


deviceControllers.controller('timeController', [ '$scope',  '$timeout',
		function timeController($scope, $timeout) {
			var updateClock = function() {
				$scope.clock = new Date();
				$timeout(function() {
					updateClock();
				}, 1000);
			};
			updateClock();

		} ]);
