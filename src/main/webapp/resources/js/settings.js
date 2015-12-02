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
		$scope.resetSettings();
		$rootScope.isConnected = $scope.initialSetting.isConnected;
		if (dataResponse.data.errorMsg) {
			$rootScope.errorMessage = "Error: " + dataResponse.data.errorMsg;
			$rootScope.errorsExist = true;
		}
	});


	// save function
	$scope.saveSetting = function() {
		$http.post('api/config', $scope.settings).success(function(data) {
			$scope.initialSetting = data;
			$scope.resetSettings();
			$rootScope.isConnected = $scope.initialSetting.isConnected;
			if (data.errorMsg) {
				$rootScope.errorMessage = "Error: " + data.errorMsg;
				$rootScope.errorsExist = true;
			} else {
				alert("Settings successfully saved!");
			}
		});
	}
	
	$scope.importLocations = function () {
		$http.post('api/config/locationImport', $scope.settings).success(function(data) {
			$scope.initialSetting = data;
			$scope.resetSettings();
			$rootScope.isConnected = $scope.initialSetting.isConnected;
			if (data.errorMsg) {
				$rootScope.errorMessage = "Error: " + data.errorMsg;
				$rootScope.errorsExist = true;
			} else {
				alert("Locations imported and mapped successfully!");
			}
		});
	}
	

	$scope.resetSettings = function() {
		$scope.settings = angular.copy($scope.initialSetting);
		$scope.settingForm.$setPristine(true);
	}

	$scope.buttonEnabled = function() {
		return $scope.settingForm.$dirty;
	}

};

