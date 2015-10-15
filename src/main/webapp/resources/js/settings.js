var SettingsController = function($scope, $http, $rootScope) {
	var todoList = this;
	$http.get('api/config').success(function(data) {
		$scope.settings = data;
	}).error(function(data) {
		alert("Error getting settings!");
	});

	// save function
	$rootScope.saveSetting = function() {
		$http.post ('api/config', $scope.settings).success(function(data) {
			alert("Settings successfully saved!");
		}).error(function(data) {
			alert("Error getting settings!");
		});
	}

	// cancel function
	$rootScope.cancelSetting = function() {
		alert('Cancel could be happening!');
	}
};
