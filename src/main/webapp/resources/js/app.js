var moduleName = 'sterlingApp';
var sterlingApp = angular.module(moduleName, [ 'components', 'ngRoute' ])
		.controller("VersionController", VersionController)
		.controller("SettingsController", SettingsController)
		.controller ("ImportOrderController", ImportOrderController);

sterlingApp.controller("tabController", function($rootScope, $scope) {
	$scope.showBtns = false;
	
	$scope.tabs = [ {
		heading : "Information",
		route : "#/info",
		active : true,
		check_conn: false,
		show_button: false
	}, {
		heading : "Connection",
		route : "#/connection",
		active : false,
		check_conn: false,
		show_button: true
	}, {
		heading : "Site Mapping",
		route : "#/site",
		active : false,
		check_conn: true,
		show_button: true
	}, {
		heading : "Locations",
		route : "#/location",
		active : false,
		check_conn: true,
		show_button: true
	}, {
		heading : "Shipping",
		route : "#/shipping",
		active : false,
		check_conn: true,
		show_button: true
	}, {
		heading : "Import",
		route : "#/import",
		active : false,
		check_conn: true,
		show_button: false
	}];

	$scope.selectTab = function(tab) {
		angular.forEach($scope.tabs, function(tab2) {
			tab2.active = false;
		});
		tab.active = true;
		$scope.showBtns = tab.show_button; 
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

	.when('/location', {
		templateUrl : 'html/setting_location.html'
	})

	.when('/shipping', {
		templateUrl : 'html/setting_shipping.html'
	})
	
	.when('/import', {
		templateUrl : 'html/import.html'
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
									// skip status updates for running jobs.
									if (config.url == null
											|| config.url.indexOf("/status") == -1) {
										ajaxIndicatorCount++;
										updateStatus();
										
									}
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

