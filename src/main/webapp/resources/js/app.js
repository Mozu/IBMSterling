var moduleName = 'sterlingApp';
var sterlingApp = angular.module(moduleName, [ 'components', 'ngRoute' ])
		.controller("VersionController", VersionController).controller(
				"SettingsController", SettingsController);

sterlingApp.controller("tabController", function($rootScope, $scope) {

	$scope.tabs = [ {
		heading : "Information",
		route : "#/info",
		active : true
	}, {
		heading : "Setting",
		route : "#/connection",
		active : false
	}, ];

	$scope.selectTab = function(tab) {
		angular.forEach($scope.tabs, function(tab2) {
			tab2.active = false;
		});
		tab.active = true
	};
	
	$scope.closeError = function() {
		$rootScope.errorsExist = false;
	};

});

sterlingApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider
	// route to show our basic form (/form)
	.when('/info', {
		templateUrl : 'html/info.html',
	})

	.when('/setting', {
		templateUrl : 'html/settings.html',
	})

	// nested states
	// each of these sections will have their own view
	// url will be nested (/form/profile)
	.when('/connection', {
		templateUrl : 'html/setting_connection.html',
	})

	// url will be /form/interests
	.when('/site', {
		templateUrl : 'html/setting_site.html'
	})

	.otherwise({
		redirectTo : '/info'
	});

	// catch all route
	// send users to the form page
} ]);

sterlingApp
		.service(
				'HttpInterceptor',
				[
						'$q',
						'$rootScope',
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
								response : function(response) {
									ajaxIndicatorCount--;
									updateStatus();
									return response;
								},
								responseError : function(response) {
									ajaxIndicatorCount--;
									updateStatus();
									$rootScope.errorMessage = "Error: ";
									if (response.status >= 200
											&& response.status <= 300)
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
						} ]).config([ '$httpProvider', function($httpProvider) {
			$httpProvider.interceptors.push('HttpInterceptor'); // Push the
																// interceptor
			// here
		} ]);

// sterlingApp.run(['$log', '$rootScope', '$route', function ($log, $rootScope,
// $route) {
// // nothing ---> This initializes the initial route.
// }]);
