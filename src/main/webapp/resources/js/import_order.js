var ImportOrderController = function($scope, $http, $rootScope) {

	this.getJobData = function() {
		// $http() returns a $promise that we can add handlers with .then()
		return $http({
			method : 'GET',
			url : 'api/job/importOrder',
		});
	}

	
	this.getJobData().then(function(dataResponse) {
		$scope.jobList = dataResponse.data;
		if (dataResponse.data.errorMsg) {
			$rootScope.errorMessage = "Error: " + dataResponse.data.errorMsg;
			$rootScope.errorsExist = true;
		}
	});


	// save function
	$scope.importOrders = function() {
		alert('Importing!');
		$http.post('api/job/importOrder', $scope.settings).success(function(data) {
			$scope.jobList = data
			if (data.errorMsg) {
				$rootScope.errorMessage = "Error: " + data.errorMsg;
				$rootScope.errorsExist = true;
			} 
		});
	}
	
	$scope.refreshList = function () {
		alert('Refreshing!');
	}
	
};

