app.controller("registerController",function ($scope,registerService,$controller) {

    // 1.实现对basedController的继承
    $controller("baseController",{$scope:$scope})

    //变量
    $scope.register={};
    $scope.seller={};
    //实现注册
    $scope.registerSeller=function () {
        registerService.registerSeller($scope.register).success(function (repsonse) {
            alert(repsonse.message);
            alert(repsonse.come);
            if (repsonse.come === 1) {
                window.location.href = "shoplogin.html";
            }
        })
    }

    //查找登录的用户名
    $scope.findSeller=function () {
        registerService.findSeller().success(function (response) {
            $scope.seller = response;
        })
    }
})