var ImportOrderController = function($scope, $http, $rootScope) {
	var self = this;
	$scope.showErrorPage = false;
	$scope.jobErrors = {};
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
		} else {
			$scope.showErrorPage = false;
		}
		
	});


	// save function
	$scope.importOrders = function() {
		$http.post('api/job/importOrder?fromDate=').success(function(data) {
			if (data.errorMsg) {
				$rootScope.errorMessage = "Error: " + data.errorMsg;
				$rootScope.errorsExist = true;
			} else {
				$scope.jobList.splice(0,0,data[0]);
			} 
		});
	};
	
	$scope.refreshList = function () {
		self.getJobData().then(function(dataResponse) {
			$scope.jobList = dataResponse.data;
			if (dataResponse.data.errorMsg) {
				$rootScope.errorMessage = "Error: " + dataResponse.data.errorMsg;
				$rootScope.errorsExist = true;
			}
		});
	};
	
	$scope.showErrorList = function(id) {
		$http.get("api/job/errors/" + id).success(function(data) {
			$scope.jobErrors = data;
			$scope.showErrorPage = true;
		});
		return false;
	};
	
	$scope.hideError = function() {
		$scope.showErrorPage = false;
	};

	$scope.stopJob = function(id) {
		$http.delete("api/job/" + id).success(function(data) {
			$scope.refreshList();
		});
		return false;
	};

	
};

