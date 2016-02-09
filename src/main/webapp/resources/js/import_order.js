var ImportOrderController = function($scope, $http, $rootScope, $interval) {
	var self = this;
	$scope.showErrorPage = false;
	$scope.jobErrors = {};
	$scope.orderDate = null;
	$scope.jobList = {};
	$scope.runningStates= ["STARTING", "STARTED"];
	
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
		$http.post('api/job/importOrder?orderDate='+$scope.orderDate).success(function(data) {
			if (data.errorMsg) {
				$rootScope.errorMessage = "Error: " + data.errorMsg;
				$rootScope.errorsExist = true;
			} else {
				$scope.jobList.splice(0,0,data[0]);
				$scope.setStatusInterval();
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

	// interval timer for refreshing running job status.
	var stop;
    $scope.setStatusInterval = function() {
        // Don't start a new timer if it's already running
        if ( angular.isDefined(stop) ) return;
        
		var jobIds = "";
		for (var i = 0; i < $scope.jobList.length; i++) {
			if ($scope.runningStates.indexOf($scope.jobList[i].batchStatus) > -1) {
				if (jobIds.length > 0) {
					jobIds = jobIds + ",";
				}
				jobIds = jobIds + $scope.jobList[i].id;
			}
		}

        stop = $interval(function() {
        	$http.get("api/job/status/?ids=" + jobIds).success(function(data) {
				var isProdRunning = false;
				var currentJobList = $scope.jobList;
				for (var i = 0; i < data.length; i++) {
					if ($scope.runningStates.indexOf(data[i].batchStatus) > -1) {
						isProdRunning = true;
						for (var j = 0; j < $scope.jobList.length; j++) {
							if (currentJobList[j].id == data[i].id) {
								currentJobList[j] = data[i];
								break;
							}
							
						}
					}
				}
				
				if (!isProdRunning) {
					$scope.stopInterval();
				}
        	});
        }, 5000);
    };

    $scope.stopInterval = function() {
        if (angular.isDefined(stop)) {
          $interval.cancel(stop);
          stop = undefined;
        }
    };


    $scope.$on('$destroy', function() {
      // Make sure that the interval is destroyed too
      $scope.stopInterval();
    });

};

