app.controller("loginController",function ($scope,loginService,$controller) {

    // 1.实现对basedController的继承
    $controller("baseController",{$scope:$scope})

    //变量
    $scope.loginUser={}
    //查找当前所登录的用户
    $scope.findLoginUser=function () {
        loginService.findLoginUser().success(function (response) {
            $scope.loginUser = response;
        })
    }
})