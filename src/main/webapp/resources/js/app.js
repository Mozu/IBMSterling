var moduleName = 'sterlingApp';
angular.module(moduleName, [ 'components' ]);

angular.module(moduleName).controller("VersionController", VersionController);
angular.module(moduleName).controller("SettingsController", SettingsController);

angular.module(moduleName).service('HttpInterceptor', ['$q', '$rootScope',
function($q, $rootScope) {
	var ajaxIndicatorCount = 0;
    function isLoading() {
        return ajaxIndicatorCount > 0;
    }
 
    function updateStatus() {
    	$rootScope.loading = isLoading();
    }
	return {
		request : function(config) {
			ajaxIndicatorCount++;
			updateStatus();
			return config;
		},
        response: function (response) {
        	ajaxIndicatorCount--;
            updateStatus();
            return response;
        },
        responseError : function(response) {
        	ajaxIndicatorCount--;
            updateStatus();
            $rootScope.errorMessage = "Error: ";
			if (response.status >= 200 && response.status <= 300)
				return;
			if (response.responseJSON != null)
				$rootScope.errorMessage += response.responseJSON.message;
			else if (response.responseText != null)
				$rootScope.errorMessage += response.responseText;
			else {
				$rootScope.errorMessage += response.statusText;
			}
			$rootScope.errorsExist = true;

			return $q.reject(response);
		}
	};
}]).config([ '$httpProvider', function($httpProvider) {
	$httpProvider.interceptors.push('HttpInterceptor'); // Push the interceptor
	// here
} ]);


