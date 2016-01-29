<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"></meta>
<script
	src="https://ajax.googleapis.com/ajax/libs/webfont/1.4.7/webfont.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular-route.js"></script>
<script src="js/components.js"></script>
<script src="js/version.js"></script>
<script src="js/settings.js"></script>
<script src="js/import_order.js"></script>
<script src="js/app.js"></script>

<!-- <link rel="stylesheet" href="css/bootstrap.css"></link> -->
<link rel="stylesheet" href="css/Site.css"></link>
<script>
	WebFont.load({
		google : {
			families : [ "Source Sans Pro:200,300,regular,700" ]
		}
	});
</script>
<link href="css/Site.css" rel="stylesheet" />
</head>
<body>
	<div class="app-container" ng-app="sterlingApp">
		<div class="app">
			<div class="header-actions">
				<h2>Mozu IBM Sterling Application</h2>
				<a class="help-icon-link"
					href="http://mozu.github.io/IntegrationDocuments" target="_helpTab">
					<img class="help-icon" src="images/help-icon.png" alt="Help"></img>
				</a>
			</div>
			<div class="tab-container" ng-controller="tabController">
				<div data-ng-if="loading"
					style="position: absolute; z-index: 10; width: 99%; height: 92%; background-color: rgba(255, 255, 255, .5);"
					id="progressIndicator">
					<div class="loadingicon"></div>
				</div>

				<div
					style="margin-left: -15px; margin-top: -10px; position: absolute; z-index: 10; width: 99%; height: 92%; overflow: auto; background-color: rgba(255, 255, 255, .8);"
					data-ng-if="errorsExist" id="serverError">
					<div class="row" style="height: 20%; width: 100%; overflow: auto;">
						<div class="col-md-12">
							<div class="notification error">
								<div class="icon" ng-click="closeError()"></div>
								<div id="serverErrorMessage">{{errorMessage}}</div>
							</div>
						</div>
					</div>
				</div>
                <div class="tab-container">
                    <div class="tabs">
                     <a ng-repeat="t in tabs" href="{{t.route}}" ng-class="{active:t.active}" ng-show="!t.check_conn || (t.check_conn && isConnected)" class="button tab" ng-click="selectTab(t)">{{t.heading}}</a>
                    </div>
                     
                    <div class="tab-view">
                        <form name="settingForm" id="settingForm" ng-controller="SettingsController">
	                        <div class="tab-section" ng-view>
	                        </div>
		                    <div id="buttons" ng-show="showBtns"
		                        style="display: block; float: right;">
		                        <button class="mz-button primary" ng-disabled="!buttonEnabled()"
		                            ng-click="saveSetting()">Save</button>
		                        <button class="mz-button" ng-disabled="!buttonEnabled()"
		                            ng-click="resetSettings()">Reset</button>
		                    </div>
	                        
                        </form>
                   </div>
				
				<div class="footer-actions">

					<div class="branding-text">Developed by Volusion, Inc. All
						rights reserved.</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
