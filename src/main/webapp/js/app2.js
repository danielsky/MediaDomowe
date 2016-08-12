'use strict';


var app = angular.module('houseMedia', ['ngRoute',"ui.materialize",'googlechart']);

app.config(
    function($routeProvider, $translateProvider) {
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

app.service("PradMockService", function(){
	var prad = [{
        reading: 100
    },{
        reading: 200
    },{
        reading: 250
    },{
        reading: 290
    }];
	return{
		getPrad : function(){
			return prad;
		}
	}
})

app.service("PradService", function($http, $q){
	return{
		getPrad : function(){
			var deffered = $q.defer();
                            $http({
                                method: 'POST',
                                url: 'api/formularz.php',
                                data: {newRow: newFormRow}
                            }).success(function (d) {
                            	console.log(d);
                            	if(d){
                            		tablica.push(newFormRow);
                            	} else{
                            		alert("problem z dodaniem");
                            	}
                                deffered.resolve(d);
                            }).error(function(){
                            	deffered.reject;
                            });
                        return deffered.promise;
		}
	}
})

app.controller("Logowanie", function($scope, Uzytkownik, $location,$rootScope){

	$scope.user = {
		userName : "",
		password : "",
		status : true
	}

	$scope.zaloguj = function(){
		Uzytkownik.setUser($scope.user).then(function(data){
			var newUser = Uzytkownik.getUser();
			alert("Nowy Uzytkownik:" + newUser.userName +
				" nowe haslo: " + newUser.password );
			$rootScope.user = newUser;
			$location.path("/glowna");
		});
	}
})


app.controller("pradCtrl",function($scope, PradMockService){
	$scope.dane = PradService.getPrad();

	$scope.getData = function () {
  		return $scope.dane;
	}
    
})

app.controller("tabelaCtrl",function($scope, $filter){
	$scope.dane = [
		{name:"jan", age : "12"},
		{name: "asia", age :"15"},
		{name: "tomasz", age : "90"},
		{name: "maciek", age : "33"},
		{name: "ignacy", age : "20"}
	]

	$scope.pageSize = 2;
	$scope.search = '';
	$scope.currentPage = 1;

	$scope.getData = function () {
  		return $filter('filter')($scope.dane, $scope.search)
	}
    $scope.numberOfPages=function(){
    	return Math.ceil($scope.getData().length/$scope.pageSize);                
	}
    
})

app.controller("menuCtrl",function($rootScope, Uzytkownik, $scope){
	$rootScope.user = Uzytkownik.getUser();

	$scope.checkUser = function(){
		var newUser = Uzytkownik.getUser();
		if (newUser.status){
			return true;
		} else{
			return false;
		}
	}

})

app.controller("Formularz", function($translate, $scope, FileUploader, FormService){

	$scope.formularz = {
		name :'',
		id : '',
		price : '',
		descriprion : '',
		photo : ''
	}
	$scope.customer = {
		name :'Mark',
		street : 'Aleja solidarnosci',
		
	}

	 var uploader = $scope.uploader = new FileUploader({
            url: 'api/upload.php'
        });



    uploader.onSuccessItem = function(fileItem, response, status, headers) {
        
 		$scope.formularz.photo = fileItem._file.name
        console.info('onSuccessItem', fileItem, response, status, headers);
    };

	 $scope.$watch('check', function(newVal) {
		console.log(isNaN(parseInt(newVal)))

		if(isNaN(parseInt(newVal))  && newVal!="" && newVal!=undefined ){

			document.getElementById("checked").style.color= "red";
			document.getElementById("checked").style.display = "block";

		} else if(newVal=="" || newVal==undefined){

			document.getElementById("checked").style.display = "none";

		}else
		{
			document.getElementById("checked").style.color= "green";
			document.getElementById("checked").style.display = "block";
		}
	
    });
	 var wyczyscFormuarz = function(){
	 	$scope.formularz = {
			name :'',
			id : '',
			price : '',
			descriprion : '',
			photo : ''
		}
	 }

	 $scope.zapiszFormularz = function(){
	 	FormService.setTablica($scope.formularz).then(function(dane){
	 		console.log(dane)
	 		console.log(FormService.getTablica())
	 		wyczyscFormuarz();
	 	});
	 }

	 
	 $scope.wyswietlIntfo = function(){
	 	alert("dziala")
	 }
	 $scope.changeLanguage = function(language){
		$translate.use(language);
	 }
	 $scope.client ={
	 	name :'John',
	 	age : 20
	 }
	 $scope.dane = [
		{name:"jan", age : "12"},
		{name: "asia", age :"15"},
		{name: "tomasz", age : "90"},
		{name: "maciek", age : "33"},
		{name: "ignacy", age : "20"}
	]


});

app.controller("ChartCtrl", function($scope){
 $scope.myChartObject = {};
    
    $scope.myChartObject.type = "BarChart";
    
    $scope.onions = [
        {v: "Onions"},
        {v: 3},
    ];

    $scope.myChartObject.data = {"cols": [
        {id: "t", label: "Topping", type: "string"},
        {id: "s", label: "Slices", type: "number"}
    ], "rows": [
        {c: [
            {v: "Mushrooms"},
            {v: 3},
        ]},
        {c: $scope.onions},
        {c: [
            {v: "Olives"},
            {v: 31}
        ]},
        {c: [
            {v: "Zucchini"},
            {v: 1},
        ]},
        {c: [
            {v: "Pepperoni"},
            {v: 2},
        ]}
    ]};

    $scope.myChartObject.options = {
        'title': 'How Much Pizza I Ate Last Night'
    };
})

app.directive("pierwsza",function(){
	return{
		template :'<div> <h1>Hello {{city}} {{nazwisko}}</h1></div>',
		restrict :'AE',
		
		scope : {
			nazwisko: '@'
		},
		link: function($scope, iElement, iAttrs, ctrl) {
			iElement.html("dskjfskjfs")
			console.log($scope);
      		console.log(iElement);
      		console.log(iAttrs);
      			console.log(ctrl);
    	}
	}
})

app.filter('startFrom', function() {
    return function(input, start) {
        start = +start; //parse to int
        return input.slice(start);
    }
});

app.filter('exact', function(){
  return function(items, match){
    var matching = [], matches, falsely = true;

    // Return the items unchanged if all filtering attributes are falsy
    angular.forEach(match, function(value, key){
      falsely = falsely && !value;
    });
    if(falsely){
      return items;
    }

    angular.forEach(items, function(item){ // e.g. { title: "ball" }
      matches = true;
      angular.forEach(match, function(value, key){ // e.g. 'all', 'title'
        if(!!value){ // do not compare if value is empty
          matches = matches && (item[key] === value);  
        }
      });
      if(matches){
        matching.push(item);  
      }
    });
    return matching;
  }
});
 