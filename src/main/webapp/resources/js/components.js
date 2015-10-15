angular.module('components', [])
 
  .directive('tabs', function() {
    return {
      restrict: 'E',
      transclude: true,
      scope: {},
      controller: function($scope, $element) {
        var panes = $scope.panes = [];
 
        $scope.select = function(pane) {
          angular.forEach(panes, function(pane2) {
            pane2.selected = false;
          });
          pane.selected = true;
        }
 
        this.addPane = function(pane) {
          if (panes.length == 0) $scope.select(pane);
          panes.push(pane);
        }
      },
      template:
        '<div class="tab-container">' +
          '<div class="tabs">' +
              '<a href="" ng-repeat="pane in panes" ng-class="{active:pane.selected}" class="button tab" ng-click="select(pane)">{{pane.title}}</a>' +
          '</div>' + 
        '<div class="tab-view" ng-transclude></div>',
      replace: true
    };
  })
 
  .directive('pane', function() {
    return {
      require: '^tabs',
      restrict: 'E',
      transclude: true,
      scope: { title: '@' },
      link: function(scope, element, attrs, tabsController) {
        tabsController.addPane(scope);
      },
      template:
        '<div class="tab-section" ng-hide="!selected" ng-transclude>' +
        '</div>',
      replace: true
    };
  })