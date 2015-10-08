angular.module('sterlingApp', ['components'])
.controller("VersionController", function($scope, $http){    
    $http.get('version').
       success(function(data) {
           $scope.versionInfo = data;
       }).error(function (data) {
       	alert ("Error getting version!");
       });
    
   });