<!doctype html>
<html ng-app="sterlingApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"></meta>
<script src="https://ajax.googleapis.com/ajax/libs/webfont/1.4.7/webfont.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular.min.js" ></script>
<script src="js/components.js"></script>
<script src="js/version.js"></script>
<script src="js/settings.js"></script>
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
	<div class="app-container">
		<div class="app">
			<div class="header-actions">
				<h2>Mozu IBM Sterling Application</h2>
				<a class="help-icon-link"
					href="http://mozu.github.io/IntegrationDocuments" target="_helpTab">
					<img class="help-icon" src="images/help-icon.png" alt="Help"></img>
				</a>
			</div>
			<div class="tab-container">
				<!--  <div
					style="position: absolute; z-index: 10; width: 99%; height: 92%; background-color: rgba(255, 255, 255, .5);"
					id="progressIndicator">
					<div class="loadingicon"></div>
				</div>
 -->
				<div
					style="margin-left: -15px; margin-top: -10px; position: absolute; z-index: 10; width: 99%; height: 92%; overflow: auto; background-color: rgba(255, 255, 255, .8); display: none"
					id="serverError">
					<div class="row" style="height: 20%; width: 100%; overflow: auto;">
						<div class="col-md-12">
							<div class="notification error">
								<div class="icon" onclick="closeError()"></div>
								<div id="serverErrorMessage" class="message"></div>
							</div>
						</div>
					</div>
				</div>
				<tabs> 
				    <pane title="Information"><div ng-include="'html/info.html'"></div></pane> 
				    <pane title="Settings"><div ng-include="'html/settings.html'"></div></pane> 
				</tabs>
				<div class="footer-actions">
				    <div id="buttons" data-bind="visible:showButtons" style="display: block;float: right;">
                        <a class="button primary-action" ng-click="cancelSetting()"
                            id="cancelBtn" style="margin-left: 10px; background-color: black; border-color: black;">Cancel</a>&nbsp;
                        <a class="button primary-action" ng-click="saveSetting()">Save</a> 
                    </div>
				
					<div class="branding-text">Developed by Volusion, Inc. All
						rights reserved.</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
