var SettingsController = function($scope, $http, $rootScope) {

	$scope.initialSetting = {};
	this.getData = function() {
		// $http() returns a $promise that we can add handlers with .then()
		return $http({
			method : 'GET',
			url : 'api/config',
		});
	}

	
	this.getData().then(function(dataResponse) {
		$scope.initialSetting = dataResponse.data;
		$rootScope.resetSettings();
		if (dataResponse.data.errorMsg) {
			$rootScope.errorMessage = "Error: " + dataResponse.data.errorMsg;
			$rootScope.errorsExist = true;
		}
	});


	// save function
	$rootScope.saveSetting = function() {
		$http.post('api/config', $scope.settings).success(function(data) {
			$scope.initialSetting = data;
			$rootScope.resetSettings();
			if (data.errorMsg) {
				$rootScope.errorMessage = "Error: " + errorMsg;
				$rootScope.errorsExist = true;
			} else {
				alert("Settings successfully saved!");
			}
		});
	}

	$rootScope.resetSettings = function() {
		$scope.settings = angular.copy($scope.initialSetting);
		$scope.settingForm.$setPristine(true);
	}

	$rootScope.buttonEnabled = function() {
		return $scope.settingForm.$dirty;
	}

};

