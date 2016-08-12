'use strict';


var app = angular.module('houseMedia', ['ngRoute']);
//var app = angular.module('houseMedia', ['ngRoute','ui.materialize','googlechart']);

app.config(
    function($routeProvider) {
	    $routeProvider.when('/', {
	       templateUrl: 'view/prad.html'
	    });
        $routeProvider.when('/prad', {
	       templateUrl: 'view/prad.html'
	    });
	    $routeProvider.when('/gaz', {
	       templateUrl: 'view/gaz.html'
	    });
	     $routeProvider.when('/woda', {
	       templateUrl: 'view/woda.html'
	    });

	   $routeProvider.when('/prad-wykres', {
	        templateUrl: 'view/prad-wykres.html'
	    });

	   $routeProvider.when('/gaz-wykres', {
	        templateUrl: 'view/gaz-wykres.html'
	    });
	   $routeProvider.when('/woda-wykres', {
	        templateUrl: 'view/woda-wykres.html',
	    });
    }
)

app.service("PradService", function($http, $q){
	return{
		getPrad : function(){
            var deffered = $q.defer();
            $http({
                method: 'GET',
                url: 'api/prad'
            }).success(function (d) {
                console.log(d);
                deffered.resolve(d);
            }).error(function(){
                deffered.reject;
            });
            return deffered.promise;
        }
	}
})


app.controller("pradCtrl",function($scope, PradService){

    PradService.getPrad().then(function(dane){
        $scope.prad = dane;
    });

/*
	$scope.dane = PradMockService.getPrad();

	$scope.getData = function () {
  		return $scope.dane;
	}*/
    
});

app.controller("gazCtrl",function($scope){
	$scope.dane = []
    
});

app.controller("wodaCtrl",function($scope){
	$scope.dane = []
    
});
