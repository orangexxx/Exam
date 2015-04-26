(function() {
	var LIMIT_TIME = 25*60;

	function convertToStr(value, length) {
		var str = value.toString();
		if (str.length >= length) {
			return str;
		}
		var zeroCount = length - str.length;
		var result = "";
		for (var i =0 ;i < zeroCount; i++ ) {
			result +="0";
		}
		return result + str;
	}

	function startCountDown(startTime) {
		var me = this;
		var interval = setInterval(function () {
			var currTime = Math.floor((new Date()).getTime()/1000);
			var remainTime = LIMIT_TIME-(currTime - startTime);
			if (remainTime < 0) {
				alert("时间到，测评结束！");
				clearInterval(interval);
				me.exam();
			} else {
				me.$apply(function () {
					me.minite = convertToStr(Math.floor(remainTime/60), 2);
					me.second = convertToStr(remainTime%60, 2);
				});
				
			}

		}, 1000);
	}

	var appControllers = angular.module("appControllers", []);

	function validateUserID(id) {
		var reg = new RegExp('^\\d{17}[\\d|X|x]$');
		if (id == null || id === "") {
			alert("身份证号不能为空！");
			return false;
		} else if (id.length !== 18 || !reg.test(id)) {
			alert("身份证号格式不正确!");
			return false;
		}
		return true;
	}

	function validateUserName (name) {
		if (name == null || name === "") {
			alert("姓名不能为空！");
			return false;
		}
		return true;
	}

	function validatePassword(pwd) {
		if (pwd == null || pwd === "") {
			alert("密码不能为空！");
			return false;
		}
		return true;
	}

	function validateDepartment(dep) {
		if (dep == null || dep === "") {
			alert("支行不能为空！");
			return false;
		}
		return true;
	}

	function validataPasswordConfirm(password1, password2) {
		if (password1 !== password2) {
			alert("密码不一致！");
			return false;
		}
		return true;
	}

	function shuffle (questions) {
		questions.forEach(function (item) {
			if (item.strict !== true) {
				item.anwsers.sort(function (pre, next) {
					return Math.random() - 0.5;
				});
			}
		});
		questions.sort(function (pre, next) {
			return Math.random() - 0.5;
		});
	}

	appControllers.controller("loginCtrl", ["$scope", "$http", "$location", function($scope, $http, $location) {
		$scope.departments = window.departments;

		$scope.go = function(url) {
			$location.url(url);
			document.documentElement.scrollTop = document.body.scrollTop =0;
		};

		$scope.login = function() {
			if (validateUserID($scope.user.userID) && validatePassword($scope.user.password)) {
				$http.get("/Exam/login?userID=" + $scope.user.userID + "&password=" + $scope.user.password).
				success(function(data) {
					if (data.code === 1) {
						window.customPath.isStartEnable = true;
						$scope.go("/start");
					}
					alert(data.msg);
				});
			}
		};

	}]);

	appControllers.controller("registryCtrl", ["$scope", "$http", "$location", function($scope, $http, $location) {
		$scope.departments = window.departments;

		$scope.go = function(url) {
			$location.url(url);
			document.documentElement.scrollTop = document.body.scrollTop =0;
		};

		$scope.registry = function() {
			if (validateUserID($scope.user.userID) && validateUserName($scope.user.userName) && validatePassword($scope.user.password) && validataPasswordConfirm($scope.user.password, $scope.user.password2) && validateDepartment($scope.user.department)) {
				$http.get("/Exam/registry?userID=" + $scope.user.userID + "&password=" + $scope.user.password + "&userName=" + $scope.user.userName + "&department=" + $scope.user.department).
				success(function(data) {
					if (data.code === 1) {
						$scope.go("/login");
					}
					alert(data.msg);
				});
			}
		};
	}]);

	appControllers.controller("modifyCtrl", ["$scope", "$http", "$location", function($scope, $http, $location) {
		$scope.departments = window.departments;

		$scope.go = function(url) {
			$location.url(url);
			document.documentElement.scrollTop = document.body.scrollTop =0;
		};

		$scope.modify = function() {
			if (validateUserID($scope.user.userID) && validateUserName($scope.user.userName) && validatePassword($scope.user.old_password) && validatePassword($scope.user.new_password) && validataPasswordConfirm($scope.user.new_password, $scope.user.new_password2) && validateDepartment($scope.user.department)) {
				$http.get("/Exam/modify?userID=" + $scope.user.userID + "&userName=" + $scope.user.userName + "&new_password=" + $scope.user.new_password + "&old_password=" + $scope.user.old_password + "&department=" + $scope.user.department).
				success(function(data) {
					if (data.code === 1) {
						$scope.go("/login");
					}
					alert(data.msg);
				});
			}
		};
	}]);

	appControllers.controller("startCtrl", ["$scope", "$http", "$location", function($scope, $http, $location) {
		$scope.go = function(url) {
			$location.url(url);
			document.documentElement.scrollTop = document.body.scrollTop =0;
		};

		$scope.start = function() {
			$http.get("/Exam/start").
			success(function (data) {
				if (data.code === 1) {
					window.customPath.isExamEnable = true;
					$scope.go("/exam");
				}
			});
			
		};
	}]);

	appControllers.controller("examCtrl", ["$scope", "$http", "$location", function($scope, $http, $location) {
		$scope.go = function(url) {
			$location.url(url);
			document.documentElement.scrollTop = document.body.scrollTop =0;
		};
		$http.get("json/exam.json").success(function(data) {
			$scope.questions = data;
			shuffle($scope.questions);
		});

		$scope.exam = function() {
			var result = "";
			$scope.questions.sort(function (pre, next) {
				return pre.id - next.id;
			});
			$scope.questions.forEach(function(item) {
				if (item.result !== undefined) {
					result += item.result;
				} else {
					result += 'x';
				}
			});

			$http.get("/Exam/exam?result=" + result).
			success(function(data) {
				alert(data.msg);
				if (data.code === 1) {
					window.customPath.isCompleteEnable = true;
					$scope.go("/complete");
				} else {
					$scope.go("/login");
				}
				
			});
		};

		$scope.minite = '25';
		$scope.second = '00';
		startCountDown.call($scope, Math.floor((new Date()).getTime()/1000));

	}]);

})();