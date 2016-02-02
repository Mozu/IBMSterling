var ImportOrderController = function($scope, $http, $rootScope) {
	var self = this;
	$scope.showErrors = false;
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
		$http.post('api/job/importOrder?fromDate=').success(function(data) {
			$scope.jobList.push(data);
			if (data.errorMsg) {
				$rootScope.errorMessage = "Error: " + data.errorMsg;
				$rootScope.errorsExist = true;
			} 
		});
	}
	
	$scope.refreshList = function () {
		self.getJobData().then(function(dataResponse) {
			$scope.jobList = dataResponse.data;
			if (dataResponse.data.errorMsg) {
				$rootScope.errorMessage = "Error: " + dataResponse.data.errorMsg;
				$rootScope.errorsExist = true;
			}
		});
	}
	
	$scope.showError = function(id) {
		$http.get("api/job/errors/" + id).success(function(data) {
			jobErrors = data;
			$scope.showErrors = true;
		});
		
	};
	
	$scope.hideError = function() {
		$scope.showErrors = false;
	};

	$scope.stopJob = function(id) {
		$http.delete("api/job/" + id).success(function(data) {
			$scope.refreshList();
		});
	};

	
};

