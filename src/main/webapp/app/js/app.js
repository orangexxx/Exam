(function(){

	var LOGIN = "/login";
	var REGISTRY = "/registry";
	var MODIFY = "/modify";
	var START = "/start";
	var EXAM = "/exam";
	var COMPLETE = "/complete";

	function getPathFromURL (url) {
		var reg = /.*#(\/\w+)$/;
		var result = url.match(reg);
		if (result !== null) {
			return result[1];
		}
		return "";
	}
	var app = angular.module("app", ["ngRoute", "appControllers"]);

	app.config(["$routeProvider", function($routeProvider) {
		$routeProvider.
		when("/login", {
			templateUrl : "views/login.html",
			controller : "loginCtrl"
		}).
		when("/registry", {
			templateUrl : "views/registry.html",
			controller : "registryCtrl"
		}).
		when("/modify", {
			templateUrl : "views/modify.html",
			controller : "modifyCtrl"
		}).
		when("/start", {
			templateUrl : "views/start.html",
			controller :  "startCtrl"
		}).
		when("/exam", {
			templateUrl : "views/exam.html",
			controller : "examCtrl"
		}).
		when("/complete", {
			templateUrl : "views/complete.html"
		}).
		otherwise({
			redirectTo: "/login"
		});
	}]);

	app.run(["$rootScope", "$location", function ($rootScope, $location) {
		$rootScope.$on('$locationChangeStart', function (event, currURL, preURL) {
			if (window.customPath === undefined) {
				window.customPath = {};
			}
			var currPath = getPathFromURL(currURL);
			var prePath = getPathFromURL(preURL);
			var customPath = window.customPath;

			if ((START === currPath && !customPath.isStartEnable) ||
				(EXAM === currPath && !customPath.isExamEnable) || 
				(COMPLETE === currPath && !customPath.isCompleteEnable)) {
				event.preventDefault();
				$location.url(LOGIN);
			}
			
		});

		$rootScope.$on('$locationChangeSuccess', function (event, currURL, preURL) {
			window.customPath = {};
		});
	}]);
})();